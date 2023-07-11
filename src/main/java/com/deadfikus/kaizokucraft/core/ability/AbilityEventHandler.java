package com.deadfikus.kaizokucraft.core.ability;

import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.core.network.ServerReceivedEvent;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AbilityEventHandler {
    @SubscribeEvent
    public void onServerLivingTick(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity().getCommandSenderWorld().isClientSide) {
            return;
        }
        if (event.getEntity() instanceof ServerPlayerEntity) {
            PlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
            PlayerCap data = PlayerCap.get(player);

            //EffectInstance effect = new EffectInstance(Effects.NIGHT_VISION, 10, 1, false, false, false);
            //player.addPotionEffect(effect);
        }
    }


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onClientLivingTick(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity().getCommandSenderWorld().isClientSide && event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            PlayerCap data = PlayerCap.get(player);
        }
    }

    @SubscribeEvent
    public void onAbilityPhaseChange(AbilityEvent.PhaseChangedEvent event) {
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity().getCommandSenderWorld().isClientSide) {
            return;
        }
        if (event.getEntity() instanceof ServerPlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            PlayerCap data = PlayerCap.get(player);
            data.cyborgProfile.setCola(0);
        }
    }
}
