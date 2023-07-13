package com.deadfikus.kaizokucraft.core.ability;

import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.core.ability.base.Ability;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;

public class AbilityEvent extends LivingEvent {
    public Ability ability;

    private AbilityEvent(LivingEntity e) {super(e);}

    public static class PhaseChangeEvent extends AbilityEvent {
        public final ModEnums.AbilityPhase previousPhase;
        public final ModEnums.AbilityPhase newPhase;
        public final boolean isCancelable;

        private PhaseChangeEvent(LivingEntity user, Ability ability, ModEnums.AbilityPhase previousPhase, ModEnums.AbilityPhase newPhase, boolean cancelable) {
            super(user);
            this.previousPhase = previousPhase;
            this.newPhase = newPhase;
            this.ability = ability;
            isCancelable = cancelable;
            ModMain.logDebug("AbilityEvent.PhaseChangedEvent", user.getName().getString(),
                    ability.getDescriptionData().name, previousPhase.name(), newPhase.name());
        }

        @Override
        public boolean isCancelable() {
            return isCancelable;
        }

        public static class Pre extends PhaseChangeEvent {
            public Pre(LivingEntity user, Ability ability, ModEnums.AbilityPhase newPhase, boolean cancelable) {
                super(user, ability, ability.getCurrentPhase(), newPhase, cancelable);
            }
        }

        public static class Post extends PhaseChangeEvent {
            public Post(LivingEntity user, Ability ability) {
                super(user, ability, ability.getPreviousPhase(), ability.getCurrentPhase(), false);
            }
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
