package com.deadfikus.kaizokucraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class Image extends Widget implements IRenderable {

    final ResourceLocation texture;
    public float u0, v0, uWidth, vHeight;

    public Image(int x, int y, int width, int height, ITextComponent name, ResourceLocation texture, float u0, float v0, float uWidth, float vHeight) {
        super(x, y, width, height, name);
        this.texture = texture;
        this.u0 = u0;
        this.v0 = v0;
        this.uWidth = uWidth;
        this.vHeight = vHeight;
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float f1) {
        if (visible)
            GuiUtils.blit(matrixStack, texture, x, y, width, height, u0, v0, uWidth, vHeight);
    }
}
