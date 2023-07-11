package com.deadfikus.kaizokucraft.client.render.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class MarineCapModel extends BipedModel<LivingEntity> {
    private MarineCapModel(float f) {
        super(RenderType::entityCutoutNoCull, f, 0.0F, 64, 32);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        head.addChild(new CapRenderer(this));
    }
    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int lightmapCoord, int overlayColor, float r, float g, float b, float a) {
        head.render(matrixStack, vertexBuilder, lightmapCoord, overlayColor, r, g, b, a);
    }

    private static MarineCapModel instance;
    public static MarineCapModel get() {
        if (instance == null) {
            instance = new MarineCapModel(1);
        }
        return instance;
    }
}

