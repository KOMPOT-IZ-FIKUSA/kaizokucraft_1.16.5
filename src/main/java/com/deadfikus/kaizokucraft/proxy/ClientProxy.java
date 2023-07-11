package com.deadfikus.kaizokucraft.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;

public class ClientProxy implements IProxy {
    @Override
    public void openClientGui(int gui_id) {
        if (gui_id == 0) {
        }
    }
}
