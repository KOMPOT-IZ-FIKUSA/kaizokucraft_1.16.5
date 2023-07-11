package com.deadfikus.kaizokucraft.client.render.item;

import com.deadfikus.kaizokucraft.client.render.animation.BakedQuadUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.SimpleModelTransform;

import java.util.List;

public class CapRenderer extends ModelRenderer {
    public static List<BakedQuad> cap;

    public CapRenderer(Model p_i1173_1_) {
        super(p_i1173_1_);
        if (cap == null) {
            IBakedModel model = ModelLoader.instance().getBakedModel(new ResourceLocation("kaizokucraft:item/marine_cap"), SimpleModelTransform.IDENTITY, ModelLoader.instance().getSpriteMap()::getSprite);
            cap = BakedQuadUtils.getFromBakedModel(model);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int lightmapCoord, int overlayColor, float r, float g, float b, float a) {
        if (!visible)
            return;
        for (BakedQuad quad : cap)
            vertexBuilder.addVertexData(matrixStack.last(), quad, r, g, b, a, lightmapCoord, overlayColor);
    }
}
