package com.deadfikus.kaizokucraft.core.ability.bari;

import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import com.deadfikus.kaizokucraft.core.ability.base.LeverAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class BarrierWall extends LeverAbility implements INBTSerializable<CompoundNBT> {
    public BarrierWall() {
        super(AbilityEnum.BARRIER_WALL);
    }

    @Override
    public void setAvailable(LivingEntity user) {

    }

    @Override
    protected void handleWorkStarted(LivingEntity user) {
        System.out.println("Barrier wall started work");
    }

    @Override
    protected void handleWorkTick(LivingEntity user) {

    }

    @Override
    protected void handleWorkEnded(LivingEntity user) {
        System.out.println("Barrier wall ended work");
    }

    @Override
    protected int calculateCooldown(LivingEntity user) {
        return 0;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("super", super.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt.getCompound("super"));
    }
}
