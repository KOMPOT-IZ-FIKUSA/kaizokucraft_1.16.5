package com.deadfikus.kaizokucraft.client.gui;

import com.deadfikus.kaizokucraft.client.event.RenderCustomVignetteEvent;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VignetteRenderer {

    public ResourceLocation blueBarrier = new ResourceLocation("kaizokucraft:textures/block/barrierblock.png");

    public VignetteRenderer() {

    }

    @SubscribeEvent
    public void renderCustomVignette(RenderCustomVignetteEvent event) {
        if (event.isCanceled()) return;
        ResourceLocation texture = null;
        switch (event.type) {
            case BARRIER_FIST:
                texture = blueBarrier;
                break;
        }
        MatrixStack matrixStack = event.matrixStack;
        //GuiUtils.blit(matrixStack, texture, 0, 0, 1920, 1080, 0, 0, 1, 1, event.opacity);
    }
}
