package com.deadfikus.kaizokucraft.core.network.packet.toclient;

import com.deadfikus.kaizokucraft.ModMain;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class COpenGui {
    public COpenGui() {
    }


    public static void encode(COpenGui packet, PacketBuffer buf) {}

    public static COpenGui decode(PacketBuffer buf) {
        return new COpenGui();
    }


    public static void handle(COpenGui packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().setPacketHandled(true);
            ModMain.PROXY.openClientGui(0);
        }
    }
}
