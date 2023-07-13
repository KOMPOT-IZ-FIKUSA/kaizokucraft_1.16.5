package com.deadfikus.kaizokucraft.core.ability.base;

import com.deadfikus.kaizokucraft.ModEnums.AbilityPhase;
import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class DurableAbility extends Ability implements INBTSerializable<CompoundNBT> {
    private int ticksToNextPhaseChange;
    protected boolean handleStartInNextTick;
    protected boolean handleEndInNextTick;

    private int workTime;

    public DurableAbility(AbilityEnum descriptionData) {
        super(descriptionData);
        this.handleStartInNextTick = false;
        this.handleEndInNextTick = false;
        this.ticksToNextPhaseChange = 0;
    }

    protected long getTicksToNextPhaseChange() {
        return this.ticksToNextPhaseChange;
    }


    @Override
    public void setUnavailable(LivingEntity user) {
        if (getCurrentPhase() != AbilityPhase.UNAVAILABLE) {
            setPhase(AbilityPhase.UNAVAILABLE, user);
            forceStop(user);
        }
    }


    private void updatePhase(LivingEntity user) {
        if (ticksToNextPhaseChange == 0) {
            AbilityPhase currentPhase = getCurrentPhase();
            if (currentPhase == AbilityPhase.CHARGING) {
                setPhase(AbilityPhase.WORKING, user);
                ticksToNextPhaseChange = calculateWorkTime(user);
                handleStartInNextTick = true;
            } else if (currentPhase == AbilityPhase.LOADING) {
                setPhase(AbilityPhase.READY, user);
                ticksToNextPhaseChange = 0;
            } else if (currentPhase == AbilityPhase.WORKING) {
                setPhase(AbilityPhase.LOADING, user);
                ticksToNextPhaseChange = calculateCooldownAfterWork(user, workTime);
                handleEndInNextTick = true;
            }
        } else {
            ticksToNextPhaseChange -= 1;
        }
    }

    @Override
    public final boolean tryTurnOn(LivingEntity user) {
        if (getCurrentPhase() == AbilityPhase.READY) {
            setPhase(AbilityPhase.CHARGING, user);
            ticksToNextPhaseChange = calculateChargeTime(user);
            return true;
        }
        return false;
    }

    @Override
    public final boolean tryTurnOff(LivingEntity user) {
        if (getCurrentPhase() == AbilityPhase.CHARGING) {
            setPhase(AbilityPhase.READY, user);
            ticksToNextPhaseChange = calculateCooldownAfterCharge(user);
            return true;
        } else if (getCurrentPhase() == AbilityPhase.WORKING) {
            setPhase(AbilityPhase.LOADING, user);
            ticksToNextPhaseChange = calculateCooldownAfterWork(user, workTime);
            handleEndInNextTick = true;
            return true;
        }
        return false;
    }

    protected abstract void handleWorkStarted(LivingEntity user);

    protected abstract void handleWorkTick(LivingEntity user);

    protected abstract void handleWorkEnded(LivingEntity user);

    protected abstract int calculateCooldownAfterWork(LivingEntity user, int workTime);

    protected abstract int calculateCooldownAfterCharge(LivingEntity user);

    protected abstract int calculateWorkTime(LivingEntity user);

    protected abstract int calculateChargeTime(LivingEntity user);


    @Override
    public final void onUserTick(LivingEntity user) {
        updatePhase(user);
        if (handleStartInNextTick) {
            handleWorkStarted(user);
            handleStartInNextTick = false;
            workTime = 0;
            ModMain.logDebug("Ability", "handled " + user.getName().getString() + " turn on ability " + this.getDescriptionData().name);
        } else if (handleEndInNextTick) {
            handleWorkEnded(user);
            handleEndInNextTick = false;
            ModMain.logDebug("Ability", "handled " + user.getName().getString() + " turn off ability " + this.getDescriptionData().name);
        } else if (getCurrentPhase() == AbilityPhase.WORKING) {
            handleWorkTick(user);
            workTime += 1;
        }
    }

    @Override
    public void forceStop(LivingEntity user) {
        if (currentPhase == AbilityPhase.UNAVAILABLE) {

        } else if (currentPhase == AbilityPhase.WORKING) {
            currentPhase = AbilityPhase.LOADING;
            previousPhase = AbilityPhase.WORKING;
            ticksToNextPhaseChange = calculateCooldownAfterWork(user, workTime);
        } else if (currentPhase == AbilityPhase.CHARGING) {
            currentPhase = AbilityPhase.LOADING;
            previousPhase = AbilityPhase.CHARGING;
            ticksToNextPhaseChange = calculateCooldownAfterCharge(user);
        }

    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("super", super.serializeNBT());
        nbt.putInt("ticksToNextPhaseChange", ticksToNextPhaseChange);
        nbt.putBoolean("handleStartInNextTick", handleStartInNextTick);
        nbt.putBoolean("handleEndInNextTick", handleEndInNextTick);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt.getCompound("super"));
        if (nbt.contains("ticksToNextPhaseChange")) ticksToNextPhaseChange = nbt.getInt("ticksToNextPhaseChange");
        if (nbt.contains("handleStartInNextTick")) handleStartInNextTick = nbt.getBoolean("handleStartInNextTick");
        if (nbt.contains("handleEndInNextTick")) handleEndInNextTick = nbt.getBoolean("handleEndInNextTick");
    }

}
