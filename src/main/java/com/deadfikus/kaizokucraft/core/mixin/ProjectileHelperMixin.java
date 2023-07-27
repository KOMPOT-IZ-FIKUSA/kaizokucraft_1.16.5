package com.deadfikus.kaizokucraft.core.mixin;

import com.deadfikus.kaizokucraft.core.impact.ICustomCollider;
import com.deadfikus.kaizokucraft.core.mixinhandlers.IWorldAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mixin(ProjectileHelper.class)
public class ProjectileHelperMixin {


    private static ArrayList<ICustomCollider> lastStartedIteratingColliders = new ArrayList<>();

    @Redirect(method = "getEntityHitResult(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/vector/Vector3d;Lnet/minecraft/util/math/vector/Vector3d;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/function/Predicate;)Lnet/minecraft/util/math/EntityRayTraceResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/function/Predicate;)Ljava/util/List;"))
    private static List<Entity> onGetEntityHitResult2(World world, Entity entity, AxisAlignedBB box, Predicate<? super Entity> predicate) {
        if (lastStartedIteratingColliders.size() > 0) {
            lastStartedIteratingColliders.forEach(ICustomCollider::setEndIterateColliders);
            lastStartedIteratingColliders.clear();
        }
        List<Entity> result = world.getEntities(entity, box, predicate);
        ArrayList<Entity> result2 = new ArrayList<>();
        for (Entity entity1 : result) {
            if (entity1 instanceof ICustomCollider) {
                lastStartedIteratingColliders.add((ICustomCollider) entity1);
                ((ICustomCollider) entity1).setStartIterateColliders();

                for (int i = 0; i < ((ICustomCollider) entity1).getCollidersCount(); i++) {
                    if (!world.isClientSide) System.out.println(entity1.getBoundingBox()); // DEBUG
                    result2.add(entity1);
                }
                ((ICustomCollider) entity1).setStartIterateColliders(); // DEBUG
            } else {
                result2.add(entity1);
            }
        }
        return result2;
    }

    @Inject(method = "getEntityHitResult(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/vector/Vector3d;Lnet/minecraft/util/math/vector/Vector3d;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/function/Predicate;)Lnet/minecraft/util/math/EntityRayTraceResult;",
        at = @At("RETURN"))
    private static void onGetEntityHitResult2(World world, Entity entity, Vector3d vec1, Vector3d vec2, AxisAlignedBB box, Predicate<Entity> predicate, CallbackInfoReturnable<EntityRayTraceResult> cir) {
        if (lastStartedIteratingColliders.size() > 0) {
            lastStartedIteratingColliders.forEach(ICustomCollider::setEndIterateColliders);
            lastStartedIteratingColliders.clear();
        }
    }

}
