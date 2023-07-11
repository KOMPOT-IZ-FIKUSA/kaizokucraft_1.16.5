package com.deadfikus.kaizokucraft.core.network.packet.toclient;

import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCap;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCapProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class COverworldTeamsDataSync {

    public CompoundNBT data;

    public COverworldTeamsDataSync(CompoundNBT nbt) {
        this.data = nbt;
    }

    public static void encode(COverworldTeamsDataSync packet, PacketBuffer buf) {
        buf.writeNbt(packet.data);
    }

    public static COverworldTeamsDataSync decode(PacketBuffer buf) {
        return new COverworldTeamsDataSync(buf.readNbt());
    }


    public static void handle(COverworldTeamsDataSync packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().setPacketHandled(true);
            if (Minecraft.getInstance().player != null) {
                if (Minecraft.getInstance().player.isAddedToWorld()) {
                    OverworldTeamsCap overworld = OverworldTeamsCapProvider.getOverworldCap(Minecraft.getInstance().player.getCommandSenderWorld());
                    overworld.deserializeNBT(packet.data);
                }
            }
        }
    }
}
