package com.deadfikus.kaizokucraft.core;

import com.deadfikus.kaizokucraft.core.ability.bari.BarrierFist;
import com.deadfikus.kaizokucraft.core.ability.bari.BarrierWall;
import com.deadfikus.kaizokucraft.core.entity.mob.KaizokuEntity;
import com.deadfikus.kaizokucraft.core.entity.projectile.FlyingBarrierEntity;
import com.deadfikus.kaizokucraft.core.network.PacketHandler;
import com.deadfikus.kaizokucraft.core.network.packet.toclient.COverworldTeamsDataSync;
import com.deadfikus.kaizokucraft.core.network.packet.toclient.CPlayerDataSync;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCapProvider;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCap;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCapProvider;
import com.deadfikus.kaizokucraft.core.teams.KaizokuTeamSerializable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemTransformVec3f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class CoreEventHandler {


    @SubscribeEvent
    public void syncCapabilities(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayerEntity) {
            PlayerCap playerCap = PlayerCap.get(event.player);
            if (playerCap.shouldBeUpdated()) {
                PacketHandler.sendToPlayer(
                        new CPlayerDataSync(playerCap),
                        (ServerPlayerEntity)event.player);
                playerCap.setUpdated();
            }
            OverworldTeamsCap overworldTeamsCap = OverworldTeamsCapProvider.getOverworldCap(event.player.getCommandSenderWorld());
            overworldTeamsCap.updateDirtiness();
            if (overworldTeamsCap.getDirtiness() > playerCap.worldDataDirtiness) {
                playerCap.worldDataDirtiness = overworldTeamsCap.getDirtiness();
                PacketHandler.sendToPlayer(new COverworldTeamsDataSync(overworldTeamsCap.serializeNBT()), (ServerPlayerEntity) event.player);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player == null)
            return;
        PlayerCap playerCap = PlayerCap.get(player);
        playerCap.onPlayerTick(player);

        // test add some abilities
        if(playerCap.abilities.isEmpty()){
            playerCap.addAbility(new BarrierWall());
            playerCap.putAbilityToHotbar(0, 0);
        }
        if(playerCap.abilities.size() == 1){
            playerCap.addAbility(new BarrierFist());
            playerCap.putAbilityToHotbar(1, 1);
        }
    }

    @SubscribeEvent
    public void onPlayerPlacesBlock(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof ServerPlayerEntity) {
            World world = event.getEntity().getCommandSenderWorld();
            PlayerEntity player = (PlayerEntity) event.getEntity();
            world.addFreshEntity(FlyingBarrierEntity.init(world, player.getEyePosition(0), new Vector3d(0, 0, 0.1f), 2, 2, 2));
        }
    }




    @SubscribeEvent
    public void onKaizokuEntityDied(LivingDeathEvent event) {
        if (event.getEntity() instanceof KaizokuEntity) {
            KaizokuEntity entity = (KaizokuEntity) event.getEntity();
            KaizokuTeamSerializable team = entity.getKaizokuTeam();
            team.removeMember(entity.getUUID());
            if (team.getMembersCount() == 0) {
                OverworldTeamsCap cap = OverworldTeamsCapProvider.getOverworldCap(entity.level);
                cap.removeTeam(team);
            }
        }
    }


}
