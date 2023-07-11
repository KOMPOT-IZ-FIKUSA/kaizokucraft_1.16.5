package com.deadfikus.kaizokucraft.core.entity.projectile;

import com.deadfikus.kaizokucraft.core.KaizokuItems;
import com.deadfikus.kaizokucraft.core.entity.KaizokuEntityTypes;
import com.deadfikus.kaizokucraft.core.entity.mob.KaizokuEntity;
import com.deadfikus.kaizokucraft.core.mixinhandlers.IDamageSourceAccess;
import com.deadfikus.kaizokucraft.core.teams.IEntityInTeam;
import com.deadfikus.kaizokucraft.core.teams.KaizokuTeamSerializable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BulletEntity extends DamagingProjectileEntity implements IEntityAdditionalSpawnData {
    public boolean isKairosekiBullet = false;
    public boolean isFireBullet = false;
    public int knockbackStrength = 0;
    private int ticksAlive;

    private LivingEntity ownerLivingEntity;

    public BulletEntity(EntityType<? extends DamagingProjectileEntity> t, World world) {
        super(KaizokuEntityTypes.BULLET_PROJECTILE.get(), world);
    }

    public static BulletEntity init(World world) {
        return new BulletEntity(KaizokuEntityTypes.BULLET_PROJECTILE.get(), world);
    }
    public static BulletEntity init(
            World world, boolean isKairoseki, boolean isFire, int knockbackStrength, LivingEntity shooter,
            double posX, double posY, double posZ,
            double velX, double velY, double velZ
    ) {
        BulletEntity e = new BulletEntity(KaizokuEntityTypes.BULLET_PROJECTILE.get(), world);
        e.isKairosekiBullet = isKairoseki;
        e.isFireBullet = isFire;
        e.knockbackStrength = knockbackStrength;
        e.setOwner(shooter);
        e.shoot(velX, velY, velZ, MathHelper.sqrt(velX*velX + velY*velY + velZ * velZ), 1f);
        e.setPos(posX, posY, posZ);
        return e;
    }

    @Override
    public void shoot(double velX, double velY, double velZ, float magnitude, float accuracyLoss) {
        super.shoot(velX, velY, velZ, magnitude, accuracyLoss);
    }

    @Override
    public void setOwner(@Nullable Entity owner) {
        if (owner instanceof LivingEntity) {
            this.ownerLivingEntity = (LivingEntity) owner;
        }
        super.setOwner(owner);
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        ticksAlive++;
        if (ticksAlive > 250000) {
            this.remove();
        }
    }


    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
        Entity target = result.getEntity();
        if (target.getCommandSenderWorld().isClientSide) {
            return;
        }
        if (!(target instanceof LivingEntity)) {
            return;
        }
        LivingEntity target1 = (LivingEntity) target;
        if (    (ownerLivingEntity != null) &&
                (target1 instanceof KaizokuEntity || target1 instanceof PlayerEntity) &&
                (ownerLivingEntity instanceof KaizokuEntity || ownerLivingEntity instanceof PlayerEntity) ) {
            IEntityInTeam ownerTeam = (IEntityInTeam) getOwner();
            IEntityInTeam targetTeam = (IEntityInTeam) target1;
            if (ownerTeam.isTeammate(targetTeam)) {
                return;
            }
        }
        DamageSource source;
        if (ownerLivingEntity != null) {
            source = new IndirectEntityDamageSource("player.bullet", this, ownerLivingEntity);
        } else {
            source = new DamageSource("bullet");
        }
        if (this.isKairosekiBullet) {
            ((IDamageSourceAccess) source).setKairoseki();
        }
        if (this.isFireBullet) {
            target1.setSecondsOnFire(3);
        }
        source.setProjectile();
        Vector3d knockback = this.getDeltaMovement();
        knockback = knockback.normalize().scale(knockbackStrength);
        Vector3d velocity = target1.getDeltaMovement();
        target1.hurt(source, 7);
        target1.setDeltaMovement(velocity.add(knockback));
        this.remove();
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        this.remove();
    }

    public ItemStack getItem() {
        if (this.isKairosekiBullet) {
            if (this.isFireBullet) {
                return KaizokuItems.FIRE_KAIROSEKI_BULLET.getDefaultInstance();
            } else {
                return KaizokuItems.KAIROSEKI_BULLET.getDefaultInstance();
            }
        } else if (this.isFireBullet) {
            return KaizokuItems.FIRE_BULLET.getDefaultInstance();
        } else {
            return KaizokuItems.IRON_BULLET.getDefaultInstance();
        }
    }


    @Override
    public boolean isSilent() {
        return true;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected IParticleData getTrailParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    protected float getInertia() {
        return 0.95f;
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeBoolean(isKairosekiBullet);
        buffer.writeBoolean(isFireBullet);
    }

    @Override
    public void readSpawnData(PacketBuffer buffer) {
        isKairosekiBullet = buffer.readBoolean();
        isFireBullet = buffer.readBoolean();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }



}
