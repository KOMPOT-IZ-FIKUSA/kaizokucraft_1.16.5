package com.deadfikus.kaizokucraft.client.render.projectille;

import com.deadfikus.kaizokucraft.client.render.animation.BakedQuadUtils;
import com.deadfikus.kaizokucraft.core.entity.projectile.BulletEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.*;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelTransformComposition;
import net.minecraftforge.client.model.SimpleModelTransform;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.event.entity.EntityEvent;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Random;

public class BulletRenderer extends EntityRenderer<BulletEntity> {

    public BulletRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(BulletEntity p_110775_1_) {
        return null;
    }

    @Override
    public void render(BulletEntity bullet, float p_225623_2_, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int p_225623_6_) {
        super.render(bullet, p_225623_2_, p_225623_3_, matrixStack, iRenderTypeBuffer, p_225623_6_);
        RenderType renderType = Atlases.translucentCullBlockSheet();
        IBakedModel model = Minecraft.getInstance().getItemRenderer().getModel(bullet.getItemStack(), bullet.getCommandSenderWorld(), null);
        IVertexBuilder buffer = ItemRenderer.getFoilBufferDirect(iRenderTypeBuffer, renderType, true, false);
        List<BakedQuad> quads = BakedQuadUtils.getFromBakedModel(model);
        matrixStack.pushPose();
        float scale = 0.2f;
        matrixStack.scale(scale, scale, scale);
        matrixStack.translate(0, 0.1, 0);
        for (BakedQuad quad: quads) {
            buffer.addVertexData(matrixStack.last(), quad, 1f, 1f, 1f, 1f, p_225623_6_, 0x00FFFFFF);
        }
        matrixStack.popPose();
    }

}
