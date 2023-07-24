package com.deadfikus.kaizokucraft.core.entity.projectile;

import com.deadfikus.kaizokucraft.core.entity.KaizokuEntityTypes;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class FlyingBarrierEntity extends Entity implements IEntityAdditionalSpawnData {

    public float width = 1, height = 1, thickness = 1;
    private OrientedBoxDimensions dimensions = OrientedBoxDimensions.cube();


    public FlyingBarrierEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    public static FlyingBarrierEntity init(World world, Vector3d pos, Vector3d motion, float width, float height, float thickness) {
        FlyingBarrierEntity barrier = new FlyingBarrierEntity(KaizokuEntityTypes.FLYING_BARRIER_WALL.get(), world);
        barrier.setPos(pos);
        barrier.setDeltaMovement(motion);
        barrier.width = width;
        barrier.height = height;
        barrier.thickness = thickness;
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
        return new EntitySize((float) dimensions.getAxisAlignedMaxWidth(), (float) dimensions.getAxisAlignedHeight(), false);
    }

    @Override
    public void setDeltaMovement(Vector3d movement) {
        super.setDeltaMovement(movement);
        updateDimensions();
    }

    public void updateDimensions() {
        this.dimensions = KaizokuCoreMathHelper.getBoxDimensions(getDeltaMovement().normalize(), width, height, thickness);
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
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
            dimensions.deserializeNBT((CompoundNBT) dim);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT nbt) {
        nbt.putFloat("width", width);
        nbt.putFloat("height", height);
        nbt.putFloat("thickness", thickness);
        nbt.put("dimensions_", dimensions.serializeNBT());
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeFloat(width);
        buffer.writeFloat(height);
        buffer.writeFloat(thickness);
        buffer.writeNbt(dimensions.serializeNBT());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        width = additionalData.readFloat();
        height = additionalData.readFloat();
        thickness = additionalData.readFloat();
        CompoundNBT nbt = additionalData.readNbt();
        if (nbt != null)
            dimensions.deserializeNBT(nbt);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
