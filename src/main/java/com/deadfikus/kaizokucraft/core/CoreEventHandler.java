package com.deadfikus.kaizokucraft.core;

import com.deadfikus.kaizokucraft.core.entity.mob.KaizokuEntity;
import com.deadfikus.kaizokucraft.core.network.PacketHandler;
import com.deadfikus.kaizokucraft.core.network.packet.toclient.COverworldTeamsDataSync;
import com.deadfikus.kaizokucraft.core.network.packet.toclient.CPlayerDataSync;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCap;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCapProvider;
import com.deadfikus.kaizokucraft.core.teams.KaizokuTeamSerializable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemTransformVec3f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

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
            OverworldTeamsCap overworldTeamsCap = (OverworldTeamsCap) OverworldTeamsCapProvider.getOverworldCap(event.player.getCommandSenderWorld());
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
    }

    @SubscribeEvent
    public void onPlayerPlacesBlock(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof ServerPlayerEntity) {
            //World world = event.getEntity().getCommandSenderWorld();
            //Entity entity = new TestEntity(KaizokuEntityTypes.TEST.get(), world);
            //event.getWorld().addFreshEntity(entity);
        }
    }


    public static String string = "";

    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent event) {

    }

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        String msg = event.getMessage();
        if (msg.startsWith("conf")) {
            int i = 0;
            float[] values = new float[3];
            for (String s : msg.substring(5).split(" ")) {
                values[i++] = Float.parseFloat(s);
            }
            Vector3f vec = new Vector3f(values[0], values[1], values[2]);
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
                System.out.println("Removed");
            }
        }
    }


}
