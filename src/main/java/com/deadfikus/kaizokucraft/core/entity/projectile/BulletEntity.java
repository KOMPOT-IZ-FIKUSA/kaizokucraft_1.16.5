package com.deadfikus.kaizokucraft.core.entity.projectile;

import com.deadfikus.kaizokucraft.core.KaizokuItems;
import com.deadfikus.kaizokucraft.core.entity.KaizokuEntityTypes;
import com.deadfikus.kaizokucraft.core.entity.mob.KaizokuEntity;
import com.deadfikus.kaizokucraft.core.mixinhandlers.IDamageSourceAccess;
import com.deadfikus.kaizokucraft.core.teams.IEntityInTeam;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class BulletEntity extends DamagingProjectileEntity implements IEntityAdditionalSpawnData {
    public boolean isKairosekiBullet = false;
    public boolean isFireBullet = false;
    public int knockbackStrength = 0;
    private int ticksAlive;

    public static Method endermanTeleport;


    private LivingEntity ownerLivingEntity;

    public BulletEntity(EntityType<? extends DamagingProjectileEntity> t, World world) {
        super(KaizokuEntityTypes.BULLET_PROJECTILE.get(), world);
    }

    public static BulletEntity init(World world, boolean isKairoseki, boolean isFire, int knockbackStrength, LivingEntity shooter, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        BulletEntity e = new BulletEntity(KaizokuEntityTypes.BULLET_PROJECTILE.get(), world);
        e.isKairosekiBullet = isKairoseki;
        e.isFireBullet = isFire;
        e.knockbackStrength = knockbackStrength;
        e.setOwner(shooter);
        e.shoot(velX, velY, velZ, MathHelper.sqrt(velX * velX + velY * velY + velZ * velZ), 1f);
        e.setPos(posX, posY, posZ);
        return e;
    }

    private void definePowerWithVelocity() {
        Vector3d velocity = getDeltaMovement();
        xPower = velocity.x * 1.25;
        yPower = velocity.y * 1.25;
        zPower = velocity.z * 1.25;
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
        if (!this.level.hasChunkAt(blockPosition())) {
            remove();
            return;
        }
        if (getDeltaMovement().length() < 1) {
            remove();
            return;
        }
        RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
        if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.onHit(raytraceresult);
        }
        this.checkInsideBlocks();

        ticksAlive++;
        Vector3d motion = this.getDeltaMovement();
        double newX = this.getX() + motion.x;
        double newY = this.getY() + motion.y;
        double newZ = this.getZ() + motion.z;
        ProjectileHelper.rotateTowardsMovement(this, 0.2F);
        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                this.level.addParticle(ParticleTypes.BUBBLE, newX - motion.x * 0.25D, newY - motion.y * 0.25D, newZ - motion.z * 0.25D, motion.x, motion.y, motion.z);
            }
        }
        Vector3d gravity = new Vector3d(0, getGravityAcceleration(), 0);
        this.setDeltaMovement(motion.scale(this.getInertia()).add(gravity));
        definePowerWithVelocity();
        if (isFireBullet && ticksAlive % 2 == 1) {
            addParticle(ParticleTypes.END_ROD, 0.3, 0.1);
        }
        if (ticksAlive % 2 == 0) {
            addParticle(ParticleTypes.SMOKE, 0.3, 0.15);
        }
        this.setPos(newX, newY, newZ);
    }

    private void addParticle(IParticleData type, double randomBlocksDelta, double inertia) {
        double dx = random.nextDouble() * randomBlocksDelta - randomBlocksDelta / 2;
        double dy = random.nextDouble() * randomBlocksDelta - randomBlocksDelta / 2;
        double dz = random.nextDouble() * randomBlocksDelta - randomBlocksDelta / 2;
        Vector3d motion = getDeltaMovement();
        this.level.addParticle(type, getX() + dx, getY() + dy, getZ() + dz, motion.x * inertia, motion.y * inertia, motion.z * inertia);
    }

    public void handleEndermanRayTraced(EndermanEntity enderman) {
        try {
            if (endermanTeleport == null) {
                endermanTeleport = EndermanEntity.class.getDeclaredMethod("teleport");
                endermanTeleport.setAccessible(true);
            }
            for (int i = 0; i < 64; i++) {
                boolean res = (Boolean)endermanTeleport.invoke(enderman);
                if (res) {
                    return;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
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
        if ((ownerLivingEntity != null) && (target1 instanceof KaizokuEntity || target1 instanceof PlayerEntity) && (ownerLivingEntity instanceof KaizokuEntity || ownerLivingEntity instanceof PlayerEntity)) {
            IEntityInTeam ownerTeam = (IEntityInTeam) getOwner();
            IEntityInTeam targetTeam = (IEntityInTeam) target1;
            if (ownerTeam.isTeammate(targetTeam)) {
                return;
            }
        }
        if (target instanceof EndermanEntity) {
            handleEndermanRayTraced((EndermanEntity) target1);
            return;
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
        if (this.isFireBullet && !target1.fireImmune()) {
            source.setIsFire();
        }
        source.setProjectile();
        if (target1.hurt(source, (float) calculateDamage())) {
            if (this.isFireBullet) {
                target1.setSecondsOnFire(1);
            }
            Vector3d knockback = this.getDeltaMovement();
            knockback = knockback.normalize().scale(knockbackStrength);
            Vector3d velocity = target1.getDeltaMovement();
            target1.setDeltaMovement(velocity.add(knockback));
        }
        this.remove();
    }

    public double calculateDamage() {
        return this.getDeltaMovement().length() * 2;
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
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    protected float getInertia() {
        return 0.96f;
    }

    protected float getGravityAcceleration() {
        return -0.004f;
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


    @Override
    public boolean canCollideWith(Entity p_241849_1_) {
        return true;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
        this.isFireBullet = nbt.getBoolean("isFireBullet");
        this.isKairosekiBullet = nbt.getBoolean("isKairosekiBullet");
        this.knockbackStrength = nbt.getInt("knockbackStrength");
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = super.serializeNBT();
        nbt.putBoolean("isFireBullet", isFireBullet);
        nbt.putBoolean("isKairosekiBullet", isKairosekiBullet);
        nbt.putInt("knockbackStrength", knockbackStrength);
        return nbt;
    }
}
