package com.deadfikus.kaizokucraft.core.ability;

import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.core.ability.base.Ability;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class AbilityEvent extends LivingEvent {
    public Ability ability;

    private AbilityEvent(LivingEntity e) {super(e);}

    public static class PhaseChangedEvent extends AbilityEvent {
        public final ModEnums.AbilityPhase previousPhase;
        public final ModEnums.AbilityPhase currentPhase;
        public PhaseChangedEvent(LivingEntity user, Ability ability) {
            super(user);
            this.previousPhase = ability.getPreviousPhase();
            this.currentPhase = ability.getCurrentPhase();
            this.ability = ability;
            ModMain.logDebug("AbilityEvent.PhaseChangedEvent", user.getName().getString(),
                    ability.getDescriptionData().name, previousPhase.name(), currentPhase.name());
        }
    }

    public static class ForceStopEvent extends AbilityEvent {
        public ForceStopEvent(LivingEntity user, Ability ability) {
            super(user);
            this.ability = ability;
            ModMain.logDebug("AbilityEvent.ForceStopEvent", user.getName().getString(), ability.getDescriptionData().name);
        }
    }
}
