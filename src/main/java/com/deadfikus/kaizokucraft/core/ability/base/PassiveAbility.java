package com.deadfikus.kaizokucraft.core.ability.base;

import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class PassiveAbility extends Ability implements INBTSerializable<CompoundNBT> {
    public PassiveAbility(AbilityEnum descriptionData) {
        super(descriptionData);
    }

    public abstract void handleWorkTick(LivingEntity user);

    @Override
    public final void onUserTick(LivingEntity user) {
        if (getCurrentPhase() != ModEnums.AbilityPhase.UNAVAILABLE) {
            handleWorkTick(user);
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        return super.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
    }

    @Override
    public final boolean tryTurnOn(LivingEntity user) {
        return false;
    }

    @Override
    public final boolean tryTurnOff(LivingEntity user) {
        return false;
    }
}