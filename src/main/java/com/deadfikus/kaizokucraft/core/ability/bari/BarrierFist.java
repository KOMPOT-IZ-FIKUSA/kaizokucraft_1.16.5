package com.deadfikus.kaizokucraft.core.ability.bari;

import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import com.deadfikus.kaizokucraft.core.ability.base.LeverAbility;
import net.minecraft.entity.LivingEntity;

public class BarrierFist extends LeverAbility {
    public BarrierFist(AbilityEnum descriptionData) {
        super(descriptionData);
    }

    @Override
    public void setAvailable(LivingEntity user) {

    }

    @Override
    protected void handleWorkStarted(LivingEntity user) {

    }

    @Override
    protected void handleWorkTick(LivingEntity user) {

    }

    @Override
    protected void handleWorkEnded(LivingEntity user) {

    }

    @Override
    protected int calculateCooldown(LivingEntity user) {
        return 0;
    }
}
