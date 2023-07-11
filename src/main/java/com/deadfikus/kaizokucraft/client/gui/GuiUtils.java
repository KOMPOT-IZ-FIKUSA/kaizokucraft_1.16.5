package com.deadfikus.kaizokucraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class GuiUtils {

    public static void blit(MatrixStack matrixStack, ResourceLocation texture, float x, float y, float width, float height, float u, float v, float uWidth, float vHeight) {
        Minecraft.getInstance().getTextureManager().bind(texture);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuilder();
        builder.begin(7, DefaultVertexFormats.POSITION_TEX);
        builder.vertex(matrixStack.last().pose(), x, y, 0).uv(u, v).endVertex();
        builder.vertex(matrixStack.last().pose(), x, y + height, 0).uv(u, v + vHeight).endVertex();
        builder.vertex(matrixStack.last().pose(), x + width, y + height, 0).uv(u + uWidth, v + vHeight).endVertex();
        builder.vertex(matrixStack.last().pose(), x + width, y, 0).uv(u + uWidth, v).endVertex();
        tessellator.end();
    }


}
