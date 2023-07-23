package com.deadfikus.kaizokucraft.core.ability;

import com.deadfikus.kaizokucraft.core.ability.base.Ability;
import com.deadfikus.kaizokucraft.core.storage.EntityAbilityGetter;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
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
    public void onAbilityPhaseChange(AbilityEvent.PhaseChangeEvent event) {
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

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event){
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        PlayerCap data = PlayerCap.get(player);
        for (Ability abl: data.getHotbarAbilities()) {
            abl.onUserInteract(event);
        }
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (source instanceof EntityDamageSource) {
            EntityDamageSource source1 = (EntityDamageSource) source;
            Entity owner = source1.getEntity();
            if (owner instanceof LivingEntity) {
                for (Ability ability : EntityAbilityGetter.getHotbarAbilities(owner)) {
                    ability.onUserDealsDamage(event, (LivingEntity)owner);
                }
            }
        }
    }


    @SubscribeEvent
    public  void onClickBlock(PlayerInteractEvent.LeftClickBlock event){
        Entity owner = event.getEntityLiving();

        if (owner instanceof LivingEntity) {
            for (Ability ability : EntityAbilityGetter.getHotbarAbilities(owner)) {
                   ability.onClickBlock(event);
            }
        }
    }
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) return;
        PlayerCap data = PlayerCap.get(player);
        for (Ability abl: data.getHotbarAbilities()) {
            abl.onRenderGameOverlay(event, player);
        }

    }



}
