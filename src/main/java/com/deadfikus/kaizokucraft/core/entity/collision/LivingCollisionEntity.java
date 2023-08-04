package com.deadfikus.kaizokucraft.core.entity.collision;

import com.deadfikus.kaizokucraft.core.entity.KaizokuEntityTypes;
import com.deadfikus.kaizokucraft.core.entity.projectile.FlyingBarrierEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.ArrayList;

public class LivingCollisionEntity extends Entity implements IKaizokuCollisionEntity {


    public LivingCollisionEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }

    public static LivingCollisionEntity init(World world, Vector3d pos) {
        LivingCollisionEntity entity = new LivingCollisionEntity(KaizokuEntityTypes.LIVING_COLLISION.get(), world);
        entity.setPos(pos);
        return entity;
    }

    @Override
    public void baseTick() {
        super.baseTick();
    }

    public void setPos(Vector3d pos) {
        setPos(pos.x, pos.y, pos.z);
    }



    @Override
    public Entity getOwner() {
        return null;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {

    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isPickable() {
        return true;
    }
    @Override
    public EntitySize getDimensions(Pose ignored) {
        return new EntitySize(10, 10, false);
    }
}
