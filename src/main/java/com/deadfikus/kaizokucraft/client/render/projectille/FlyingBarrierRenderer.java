package com.deadfikus.kaizokucraft.client.render.projectille;

import com.deadfikus.kaizokucraft.client.render.animation.BakedQuadUtils;
import com.deadfikus.kaizokucraft.core.entity.projectile.FlyingBarrierEntity;
import com.deadfikus.kaizokucraft.core.math.OrientedBoxDimensions;
import com.deadfikus.kaizokucraft.core.mixinhandlers.IBakedQuadAccess;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class FlyingBarrierRenderer extends EntityRenderer<FlyingBarrierEntity> {

    static BakedQuad barrierPanel = null;

    public FlyingBarrierRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(FlyingBarrierEntity barrier) {
        return null;
    }


    @Override
    public void render(FlyingBarrierEntity barrier, float p_225623_2_, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int p_225623_6_) {
        super.render(barrier, p_225623_2_, p_225623_3_, matrixStack, iRenderTypeBuffer, p_225623_6_);
        if (barrierPanel == null) {
            barrierPanel = BakedQuadUtils.getFromBakedModel(Minecraft.getInstance().getModelManager().getModel(new ResourceLocation("kaizokucraft:entity/flying_barrier"))).get(0);
        }
        RenderType renderType = Atlases.translucentCullBlockSheet();
        IVertexBuilder buffer = ItemRenderer.getFoilBufferDirect(iRenderTypeBuffer, renderType, true, false);
        IBakedQuadAccess panel = (IBakedQuadAccess) barrierPanel;
        Vector3d[] vertices = barrier.getCustomDimensions().getVertices();
        float heightHalf = barrier.getBbHeight() / 2;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                Vector3d vertex = vertices[OrientedBoxDimensions.facesVerticesIndices[i][j]];
                panel.setPosition(j, (float)vertex.x, (float)vertex.y + heightHalf, (float)vertex.z);
            }
            buffer.addVertexData(matrixStack.last(), barrierPanel, 1, 1, 1, 1f, p_225623_6_, 0x00FFFFFF);
        }

    }
}
