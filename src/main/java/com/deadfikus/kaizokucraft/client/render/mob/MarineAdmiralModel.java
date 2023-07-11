package com.deadfikus.kaizokucraft.client.render.mob;

import com.deadfikus.kaizokucraft.client.render.animation.BakedQuadUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.function.Function;

public class MarineAdmiralModel extends Model {

    public static List<BakedQuad> quads;

    public MarineAdmiralModel(Function<ResourceLocation, RenderType> p_i225947_1_) {
        super(p_i225947_1_);
        if (quads == null) {
            quads = BakedQuadUtils.getFromBakedModel(Minecraft.getInstance().getModelManager().getModel(new ResourceLocation("kaizokucraft:entity/marine_admiral")));
        }
    }

    public MarineAdmiralModel() {
        this(rl -> RenderType.cutout());
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int lightmapCoord, int overlayColor, float r, float g, float b, float a) {
        for (BakedQuad quad : quads) {
            vertexBuilder.addVertexData(matrixStack.last(), quad, r, g, b, a, lightmapCoord, overlayColor);
        }
    }
}
