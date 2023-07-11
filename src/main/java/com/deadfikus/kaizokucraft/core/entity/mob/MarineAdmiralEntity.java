package com.deadfikus.kaizokucraft.core.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class MarineAdmiralEntity extends MarineEntity {

    public MarineAdmiralEntity(EntityType<? extends MarineAdmiralEntity> t, World world) {
        super(t, world);
    }

    @Override
    protected void initBalance() {
        setBalance(1000);
    }

    //@Override
    //public AxisAlignedBB getBoundingBox() {
    //    return new
    //}
//
    //@Override
    //protected AxisAlignedBB getBoundingBoxForPose(Pose p_213321_1_) {
    //    return super.getBoundingBoxForPose(p_213321_1_);
    //}
}
