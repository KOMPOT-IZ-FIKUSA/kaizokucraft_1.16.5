package com.deadfikus.kaizokucraft.client.render.mob;

import com.deadfikus.kaizokucraft.core.entity.mob.MarineAdmiralEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.obj.OBJModel;

public class MarineAdmiralRenderer extends EntityRenderer<MarineAdmiralEntity> {

    private static MarineAdmiralModel model;

    @Override
    public void render(MarineAdmiralEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int lightmapCoord) {
        if (model == null) {
            model = new MarineAdmiralModel();
        }
        matrixStack.pushPose();
        matrixStack.scale(0.4f, 0.4f, 0.4f);
        model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(model.renderType(null)), lightmapCoord, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        matrixStack.popPose();
    }

    public MarineAdmiralRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    public ResourceLocation getTextureLocation(MarineAdmiralEntity p_110775_1_) {
        return new ResourceLocation("kaizokucraft:item/marine_cap");
    }
}
