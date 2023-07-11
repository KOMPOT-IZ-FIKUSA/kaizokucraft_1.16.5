package com.deadfikus.kaizokucraft.core.network;

import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.core.network.packet.toclient.COpenGui;
import com.deadfikus.kaizokucraft.core.network.packet.toclient.COverworldTeamsDataSync;
import com.deadfikus.kaizokucraft.core.network.packet.toclient.CPlayerDataSync;
import com.deadfikus.kaizokucraft.core.network.packet.toserver.SAbilityTurnOff;
import com.deadfikus.kaizokucraft.core.network.packet.toserver.SAbilityTurnOnRequest;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ModMain.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );


    public void register() {
        int messageID = 0;
        NETWORK.registerMessage(messageID++, COpenGui.class, COpenGui::encode, COpenGui::decode, COpenGui::handle);
        NETWORK.registerMessage(messageID++, SAbilityTurnOnRequest.class, SAbilityTurnOnRequest::encode, SAbilityTurnOnRequest::decode, SAbilityTurnOnRequest::handle);
        NETWORK.registerMessage(messageID++, SAbilityTurnOff.class, SAbilityTurnOff::encode, SAbilityTurnOff::decode, SAbilityTurnOff::handle);
        NETWORK.registerMessage(messageID++, CPlayerDataSync.class, CPlayerDataSync::encode, CPlayerDataSync::decode, CPlayerDataSync::handle);
        NETWORK.registerMessage(messageID++, COverworldTeamsDataSync.class, COverworldTeamsDataSync::encode, COverworldTeamsDataSync::decode, COverworldTeamsDataSync::handle);
        NETWORK.registerMessage(messageID++, COverworldTeamsDataSync.class, COverworldTeamsDataSync::encode, COverworldTeamsDataSync::decode, COverworldTeamsDataSync::handle);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayerEntity player) {
        NETWORK.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }

}
