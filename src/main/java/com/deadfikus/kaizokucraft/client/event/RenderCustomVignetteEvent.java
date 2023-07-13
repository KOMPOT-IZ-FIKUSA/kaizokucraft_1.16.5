package com.deadfikus.kaizokucraft.client.event;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.Event;

public class RenderCustomVignetteEvent extends Event {

    public final MatrixStack matrixStack;
    public VignetteType type;
    public float opacity;

    public RenderCustomVignetteEvent(VignetteType type, float opacity, MatrixStack matrixStack) {
        this.type = type;
        this.opacity = opacity;
        this.matrixStack = matrixStack;
    }

    public enum VignetteType {
        BARRIER_FIST
    }

    @Override
    public boolean isCancelable() {
        return true;
    }
}
