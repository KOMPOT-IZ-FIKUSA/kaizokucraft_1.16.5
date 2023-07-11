package com.deadfikus.kaizokucraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class QuestMenuScreen extends Screen {
    public QuestMenuScreen() {
        super(new StringTextComponent("gui.quests"));
    }

    private Image frame;
    private Image background;
    private ScrollWidget scroll;
    private static ResourceLocation frameLocation = new ResourceLocation("kaizokucraft:textures/gui/frame.png");
    private static ResourceLocation backgroundLocation = new ResourceLocation("kaizokucraft:textures/gui/quests_menu_back.png");
    private int centerX;
    private int centerY;
    private int height_16_9;
    private int width_16_9;
    private int leftEdge;
    private int topEdge;

    private int evenRound(float i) {
        int i2 = (int)i;
        return i2 % 2 == 0 ? i2 : i2 + 1;
    }

    @Override
    protected void init() {
        super.init();
        centerX = width / 2;
        centerY = height / 2;
        if ((float) width / (float) height >= 16f/9f) {
            height_16_9 = evenRound((float)height * 0.8f);
            width_16_9 = evenRound(height_16_9 * 16f / 9f);
        } else {
            width_16_9 = evenRound((float)width * 0.8f);
            height_16_9 = evenRound(width_16_9 * 9f / 16f);
        }
        leftEdge = centerX - width_16_9 / 2;
        topEdge = centerY - height_16_9 / 2;


        scroll = new ScrollWidget(0, 0, 20, 100, new StringTextComponent("scroll"));
        frame = new Image(leftEdge, topEdge, width_16_9, height_16_9, new StringTextComponent("frame"), frameLocation, 0, 0, 1, 9f/16f);
        background = new Image(leftEdge, topEdge, width_16_9, height_16_9, new StringTextComponent("background"), backgroundLocation, 0, 0, 1, 1);
        this.addWidget(frame);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float f1) {

        renderBackground(matrixStack);
        RenderSystem.enableBlend();
        background.render(matrixStack, mouseX, mouseY, f1);
        frame.render(matrixStack, mouseX, mouseY, f1);
        scroll.render(matrixStack, mouseX, mouseY, f1);
        super.render(matrixStack, mouseX, mouseY, f1); // superclass renders buttons by default
    }
}