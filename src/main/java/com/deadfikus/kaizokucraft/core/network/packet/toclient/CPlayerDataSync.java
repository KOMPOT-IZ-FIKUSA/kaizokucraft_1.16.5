package com.deadfikus.kaizokucraft.core.network.packet.toclient;

import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CPlayerDataSync {
    CompoundNBT nbt;

    public CPlayerDataSync(PlayerCap data) {
        this.nbt = data.serializeNBT();
    }

    public CPlayerDataSync(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(CPlayerDataSync packet, PacketBuffer buf) {
        buf.writeNbt(packet.nbt);
    }

    public static CPlayerDataSync decode(PacketBuffer buf) {
        CompoundNBT nbt = buf.readNbt();
        return new CPlayerDataSync(nbt);
    }

    public static void handle(CPlayerDataSync packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().setPacketHandled(true);
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player == null) { return;}
            PlayerCap data = PlayerCap.get(player);
            data.deserializeNBT(packet.nbt);
        }
    }
}
