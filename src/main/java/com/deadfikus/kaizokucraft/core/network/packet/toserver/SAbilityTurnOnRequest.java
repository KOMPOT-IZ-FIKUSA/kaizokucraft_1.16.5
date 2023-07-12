package com.deadfikus.kaizokucraft.core.network.packet.toserver;

import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.core.ability.base.Ability;
import com.deadfikus.kaizokucraft.core.network.PacketHandler;
import com.deadfikus.kaizokucraft.core.network.packet.toclient.COverworldTeamsDataSync;
import com.deadfikus.kaizokucraft.core.network.packet.toclient.CPlayerDataSync;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCap;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCapProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SAbilityTurnOnRequest {

    public int abilityInInventoryIndex;

    public SAbilityTurnOnRequest(int abilityInInventoryIndex) {
        this.abilityInInventoryIndex = abilityInInventoryIndex;
    }

    public static void encode(SAbilityTurnOnRequest packet, PacketBuffer buf) {
        buf.writeInt(packet.abilityInInventoryIndex);
    }

    public static SAbilityTurnOnRequest decode(PacketBuffer buf) {
        return new SAbilityTurnOnRequest(buf.readInt());
    }

    public static void handle(SAbilityTurnOnRequest packet, Supplier<NetworkEvent.Context> ctx) {

        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().setPacketHandled(true);
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                PlayerCap playerCap = PlayerCap.get(player);
                ArrayList<Ability> playerAbilities = playerCap.abilities;
                if (packet.abilityInInventoryIndex >= 0 && packet.abilityInInventoryIndex < playerAbilities.size()) {
                    Ability ability = playerAbilities.get(packet.abilityInInventoryIndex);
                    if (ability.getCurrentPhase() == ModEnums.AbilityPhase.READY) {
                        ability.tryTurnOn(player);
                    }
                }
                OverworldTeamsCap overworldCap = OverworldTeamsCapProvider.getOverworldCap(player.getCommandSenderWorld());
                if (overworldCap.getDirtiness() > playerCap.worldDataDirtiness) {
                    PacketHandler.sendToPlayer(
                            new COverworldTeamsDataSync(overworldCap.serializeNBT()),
                            player);
                    playerCap.worldDataDirtiness = overworldCap.getDirtiness();
                }
                PacketHandler.sendToPlayer(new CPlayerDataSync(playerCap), player);
                playerCap.setUpdated();
            }
        }
    }
}
