package com.deadfikus.kaizokucraft.core.ability.base;

import com.deadfikus.kaizokucraft.ModEnums.AbilityPhase;
import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import com.deadfikus.kaizokucraft.core.ability.AbilityEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;

public abstract class Ability implements INBTSerializable<CompoundNBT> {
    private AbilityEnum descriptionData;
    protected AbilityPhase currentPhase;
    protected AbilityPhase previousPhase;
    public boolean isDirty = false;


    public Ability(AbilityEnum descriptionData) {
        this.descriptionData = descriptionData;
        this.currentPhase = AbilityPhase.READY;
        this.previousPhase = AbilityPhase.LOADING;

    }

    public abstract void onUserTick(LivingEntity user);
    public AbilityPhase getCurrentPhase() {
        return currentPhase;
    }
    public AbilityPhase getPreviousPhase() {
        return previousPhase;
    }

    public abstract void setAvailable(LivingEntity user);
    public abstract void setUnavailable(LivingEntity user);
    public final AbilityEnum getDescriptionData() {return this.descriptionData;}
    protected void setPhase(AbilityPhase p, LivingEntity user) {
        if (p != currentPhase) {
            MinecraftForge.EVENT_BUS.post(new AbilityEvent.PhaseChangeEvent.Pre(user, this, p, false));
            this.previousPhase = currentPhase;
            this.currentPhase = p;
            this.isDirty = true;
            MinecraftForge.EVENT_BUS.post(new AbilityEvent.PhaseChangeEvent.Post(user, this));
        }
    }
    protected boolean trySetPhase(AbilityPhase p, LivingEntity user) {
        if (p == currentPhase) {
            return true;
        }
        Event e = new AbilityEvent.PhaseChangeEvent.Pre(user, this, p, true);
        MinecraftForge.EVENT_BUS.post(e);
        if (e.isCanceled()) return false;
        this.previousPhase = currentPhase;
        this.currentPhase = p;
        this.isDirty = true;
        MinecraftForge.EVENT_BUS.post(new AbilityEvent.PhaseChangeEvent.Post(user, this));
        return true;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putShort("descriptionData", descriptionData.getI());
        nbt.putShort("currentPhase", currentPhase.getI());
        nbt.putShort("previousPhase", previousPhase.getI());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("descriptionData"))
            descriptionData = AbilityEnum.values[nbt.getShort("descriptionData")];
        if (nbt.contains("currentPhase"))
            currentPhase = AbilityPhase.values[nbt.getShort("currentPhase")];
        if (nbt.contains("previousPhase"))
            previousPhase = AbilityPhase.values[nbt.getShort("previousPhase")];
    }

    public abstract boolean tryTurnOn(LivingEntity user);
    public abstract boolean tryTurnOff(LivingEntity user);

    public void forceStop(LivingEntity user) {
        MinecraftForge.EVENT_BUS.post(new AbilityEvent.ForceStopEvent(user, this));
    };

    public void onUserInteract(PlayerInteractEvent event) {}

    public void onUserDealsDamage(LivingDamageEvent event, LivingEntity user) {}

    public void onRenderGameOverlay(RenderGameOverlayEvent event, LivingEntity user) {

    }
}
