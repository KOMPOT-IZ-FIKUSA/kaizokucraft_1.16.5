package com.deadfikus.kaizokucraft.core.ability.base;

import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class LeverAbility extends Ability implements INBTSerializable<CompoundNBT> {

    private int ticksToNextPhaseChange;
    private int ticksWorked;
    protected boolean handleEndInNextTick;
    protected boolean handleStartInNextTick;

    public LeverAbility(AbilityEnum descriptionData) {
        super(descriptionData);
        this.handleEndInNextTick = false;
        this.ticksToNextPhaseChange = 0;
        this.ticksWorked = 0;
    }

    protected long getTicksToNextPhaseChange() {
        return this.ticksToNextPhaseChange;
    }

    @Override
    public void setUnavailable(LivingEntity user) {
        if (getCurrentPhase() != ModEnums.AbilityPhase.UNAVAILABLE) {
            setPhase(ModEnums.AbilityPhase.UNAVAILABLE, user);
            ticksToNextPhaseChange = 0;
        }
    }

    protected void updatePhaseTick(LivingEntity user) {
        System.out.println(ticksToNextPhaseChange);
        if (ticksToNextPhaseChange == 0) {
            ModEnums.AbilityPhase phase = getCurrentPhase();
            if (phase == ModEnums.AbilityPhase.LOADING) {
                setPhase(ModEnums.AbilityPhase.READY, user);
            }
        } else {
            ticksToNextPhaseChange -= 1;
        }
    }

    @Override
    public final boolean tryTurnOn(LivingEntity user) {
        if (getCurrentPhase() == ModEnums.AbilityPhase.READY) {
            setPhase(ModEnums.AbilityPhase.WORKING, user);
            handleStartInNextTick = true;
            return true;
        }
        return false;
    }

    @Override
    public final boolean tryTurnOff(LivingEntity user) {
        if (getCurrentPhase() == ModEnums.AbilityPhase.WORKING) {
            setPhase(ModEnums.AbilityPhase.LOADING, user);
            ticksToNextPhaseChange = calculateCooldown(user);
            handleEndInNextTick = true;
            return true;
        }
        return false;
    }

    protected abstract void handleWorkStarted(LivingEntity user);

    protected abstract void handleWorkTick(LivingEntity user);

    protected abstract void handleWorkEnded(LivingEntity user);

    protected abstract int calculateCooldown(LivingEntity user);

    public void forceStop(LivingEntity user){
        if(currentPhase == ModEnums.AbilityPhase.WORKING){
            previousPhase =  currentPhase;
            currentPhase = ModEnums.AbilityPhase.LOADING;
            ticksToNextPhaseChange = calculateCooldown(user);

        }
    }

    @Override
    public final void onUserTick(LivingEntity user) {
        updatePhaseTick(user);
        if (handleStartInNextTick) {
            handleWorkStarted(user);
            handleStartInNextTick = false;
            ModMain.logDebug("Ability", "handled " + user.getName().getString() + " turn on ability " + this.getDescriptionData().name);

        } else if (handleEndInNextTick) {
            handleWorkEnded(user);
            handleEndInNextTick = false;
            ModMain.logDebug("Ability", "handled " + user.getName().getString() + " turn off ability " + this.getDescriptionData().name);
        } else if (getCurrentPhase() == ModEnums.AbilityPhase.WORKING) {
            handleWorkTick(user);
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("super", super.serializeNBT());
        nbt.putInt("ticksToNextPhaseChange", ticksToNextPhaseChange);
        nbt.putInt("ticksWorked", ticksWorked);
        nbt.putBoolean("handleStartInNextTick", handleStartInNextTick);
        nbt.putBoolean("handleEndInNextTick", handleEndInNextTick);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt.getCompound("super"));
        if (nbt.contains("ticksToNextPhaseChange")) ticksToNextPhaseChange = nbt.getInt("ticksToNextPhaseChange");
        if (nbt.contains("ticksWorked")) ticksWorked = nbt.getInt("ticksWorked");
        if (nbt.contains("ticksWorked")) handleStartInNextTick = nbt.getBoolean("handleStartInNextTick");
        if (nbt.contains("handleEndInNextTick")) handleEndInNextTick = nbt.getBoolean("handleEndInNextTick");

    }
}
