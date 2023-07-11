package com.deadfikus.kaizokucraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Random;

public class ScrollWidget extends Widget {

    static ResourceLocation texture = new ResourceLocation("kaizokucraft:textures/gui/scroll.png");

    static float textureScrollX = 0;
    static float textureScrollWidth = 64 / 255f;
    static float textureScrollY = 0;
    static float textureScrollHeight = 1f;

    public ScrollWidget(int x, int y, int width, int height, ITextComponent text) {
        super(x, y, width, height, text);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float p_230430_4_) {
        if (visible) {
            GuiUtils.blit(matrixStack, texture, x, y, width, height, textureScrollX, textureScrollY, textureScrollWidth, textureScrollHeight);
        }
    }

}
