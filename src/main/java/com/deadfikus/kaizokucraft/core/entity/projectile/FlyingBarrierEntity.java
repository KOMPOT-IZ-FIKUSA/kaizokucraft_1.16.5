package com.deadfikus.kaizokucraft.core.entity.projectile;

import com.deadfikus.kaizokucraft.core.entity.KaizokuEntityTypes;
import com.deadfikus.kaizokucraft.core.impact.ICustomCollider;
import com.deadfikus.kaizokucraft.core.impact.IImpactObject;
import com.deadfikus.kaizokucraft.core.impact.group.IKairosekiImpactGroup;
import com.deadfikus.kaizokucraft.core.math.KaizokuCoreMathHelper;
import com.deadfikus.kaizokucraft.core.math.OrientedBoxDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class FlyingBarrierEntity extends Entity implements IEntityAdditionalSpawnData, IImpactObject, ICustomCollider {

    public float width = 1, height = 1, thickness = 1;
    private OrientedBoxDimensions customDimensions = OrientedBoxDimensions.cube();

    public OrientedBoxDimensions getCustomDimensions() {
        return customDimensions;
    }

    public FlyingBarrierEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);


    }


    public static FlyingBarrierEntity init(World world, Vector3d pos, Vector3d motion, float width, float height, float thickness) {
        FlyingBarrierEntity barrier = new FlyingBarrierEntity(KaizokuEntityTypes.FLYING_BARRIER_WALL.get(), world);
        barrier.setPos(pos);
        barrier.width = width;
        barrier.height = height;
        barrier.thickness = thickness;
        barrier.setDeltaMovement(motion);
        return barrier;
        }




    @Override
    public void baseTick() {
        super.baseTick();
        this.setPos(position().add(getDeltaMovement()));

    }

    public void setPos(Vector3d pos) {
        setPos(pos.x, pos.y, pos.z);
    }


    @Override
    protected void defineSynchedData() {

    }



    @Override
    public EntitySize getDimensions(Pose ignored) {
        return new EntitySize((float) customDimensions.getAxisAlignedMaxWidth(), (float) customDimensions.getAxisAlignedHeight(), false);
    }

    @Override
    public void setDeltaMovement(Vector3d movement) {
        super.setDeltaMovement(movement);
        updateDimensions();
    }

    public void updateDimensions() {
        this.customDimensions = KaizokuCoreMathHelper.getBoxDimensions(getDeltaMovement().normalize(), width, height, thickness);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }



    @Override
    protected void readAdditionalSaveData(CompoundNBT nbt) {
        width = nbt.getFloat("width");
        height = nbt.getFloat("height");
        thickness = nbt.getFloat("thickness");
        INBT dim = nbt.get("dimensions_");
        if (dim instanceof CompoundNBT) {
            customDimensions.deserializeNBT((CompoundNBT) dim);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        nbt.putFloat("width", width);
        nbt.putFloat("height", height);
        nbt.putFloat("thickness", thickness);
        nbt.put("dimensions_", customDimensions.serializeNBT());
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeFloat(width);
        buffer.writeFloat(height);
        buffer.writeFloat(thickness);
        buffer.writeNbt(customDimensions.serializeNBT());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        width = additionalData.readFloat();
        height = additionalData.readFloat();
        thickness = additionalData.readFloat();
        CompoundNBT nbt = additionalData.readNbt();
        if (nbt != null)
            customDimensions.deserializeNBT(nbt);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isImpactLogicImplemented(IImpactObject other) {
        return false;
    }

    @Override
    public boolean isImpactGroupLogicImplemented(IImpactObject other) {
        return other instanceof IKairosekiImpactGroup;
    }

    @Override
    public void performImpact(IImpactObject other) {

    }

    @Override
    public void performGroupImpact(IImpactObject other) {
        if (other instanceof IKairosekiImpactGroup) {
            if (((IKairosekiImpactGroup)other).kairosekiPower() > 0) {
                onKairosekiTouch();
            }
        }
    }

    public void onKairosekiTouch() {
        remove();
    }

    @Override
    public int priority() {
        return 0;
    }


    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return true;
    }



    private int colliderIndex = -1;
    @Override
    public int getCollidersCount() {
        return 2;
    }

    @Override
    public void setStartIterateColliders() {
        colliderIndex = 0;
    }
    @Override
    public void setEndIterateColliders() {
        colliderIndex = -1;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        if (colliderIndex == -1) {
            return super.getBoundingBox();
        } else if (colliderIndex == 0) {
            colliderIndex += 1;
            AxisAlignedBB box = super.getBoundingBox();
            return new AxisAlignedBB(box.minX, box.minY, box.minZ, box.minX + 0.3, box.minY + 0.3, box.minZ + 0.3);
        } else if (colliderIndex == 1) {
            colliderIndex += 1;
            AxisAlignedBB box = super.getBoundingBox();
            return new AxisAlignedBB(box.maxX - 0.3, box.maxY - 0.3, box.maxZ - 0.3, box.maxX, box.maxY, box.maxZ);
        }
        return super.getBoundingBox();
    }
}
