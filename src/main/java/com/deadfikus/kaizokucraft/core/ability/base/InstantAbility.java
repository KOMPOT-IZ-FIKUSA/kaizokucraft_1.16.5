package com.deadfikus.kaizokucraft.core.ability.base;

import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class InstantAbility extends Ability implements INBTSerializable<CompoundNBT> {
    private int ticksToNextPhaseChange;

    public InstantAbility(AbilityEnum descriptionData) {
        super(descriptionData);
        this.ticksToNextPhaseChange = 0;
    }

    @Override
    public void setUnavailable(LivingEntity user) {
        if (getCurrentPhase() != ModEnums.AbilityPhase.UNAVAILABLE) {
            setPhase(ModEnums.AbilityPhase.UNAVAILABLE, user);
            ticksToNextPhaseChange = 0;
        }
    }

    protected final long getTicksToNextPhaseChange() {
        return this.ticksToNextPhaseChange;
    }


    private void updatePhaseTick(LivingEntity user) {
        if (ticksToNextPhaseChange == 0) {
            ModEnums.AbilityPhase phase = getCurrentPhase();
            if (phase == ModEnums.AbilityPhase.CHARGING) {
                setPhase(ModEnums.AbilityPhase.WORKING, user);
            } else if (phase == ModEnums.AbilityPhase.WORKING) {
                setPhase(ModEnums.AbilityPhase.LOADING, user);
                ticksToNextPhaseChange = calculateFullCooldown(user);
            } else if (phase == ModEnums.AbilityPhase.LOADING) {
                setPhase(ModEnums.AbilityPhase.READY, user);
            }
        } else {
            ticksToNextPhaseChange -= 1;
        }
    }

    @Override
    public final boolean tryTurnOn(LivingEntity user) {
        if (getCurrentPhase() == ModEnums.AbilityPhase.READY) {
            setPhase(ModEnums.AbilityPhase.CHARGING, user);
            ticksToNextPhaseChange = calculateChargeTime(user);
            return true;
        }
        return false;
    }


    @Override
    public final boolean tryTurnOff(LivingEntity user) {
        if (getCurrentPhase() == ModEnums.AbilityPhase.CHARGING) {
            setPhase(ModEnums.AbilityPhase.LOADING, user);
            ticksToNextPhaseChange = calculateCooldownAfterCharge(user);
            return true;
        }
        return false;
    }


    protected abstract void handleInstantAction(LivingEntity user);  // вызывается лишь на один тик

    protected abstract int calculateFullCooldown(LivingEntity user);

    protected abstract int calculateChargeTime(LivingEntity user);

    protected abstract int calculateCooldownAfterCharge(LivingEntity user);

    @Override
    public final void onUserTick(LivingEntity user) {
        updatePhaseTick(user);
        if (getCurrentPhase() == ModEnums.AbilityPhase.WORKING) {
            handleInstantAction(user);
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("super", super.serializeNBT());
        nbt.putInt("ticksToNextPhaseChange", ticksToNextPhaseChange);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt.getCompound("super"));
        if (nbt.contains("ticksToNextPhaseChange")) ticksToNextPhaseChange = nbt.getInt("ticksToNextPhaseChange");
    }
}