package com.deadfikus.kaizokucraft.core.mixin;

import com.deadfikus.kaizokucraft.core.entity.projectile.BulletEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamagingProjectileEntity.class)
public abstract class ProjectileMixin extends Entity {

    public ProjectileMixin(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    boolean disableOneAddParticleExecution = false;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particles/IParticleData;DDDDDD)V"))
    public void disableAddingParticlesPre(CallbackInfo ci) {
        if (BulletEntity.class.isInstance(this)) {
            if (!(((BulletEntity)(Object)this).isFireBullet)) {
                disableOneAddParticleExecution = true;
            }
        }
    }


    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particles/IParticleData;DDDDDD)V"))
    public void disableAddingParticles(World world, IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        if (!disableOneAddParticleExecution) {
            world.addParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
        } else {
            disableOneAddParticleExecution = false;
        }
    }



}
