package com.deadfikus.kaizokucraft.client.render.projectille;

import com.deadfikus.kaizokucraft.core.entity.projectile.BulletEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelTransformComposition;
import net.minecraftforge.client.model.SimpleModelTransform;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.event.entity.EntityEvent;

import java.util.List;
import java.util.Random;

public class BulletRenderer extends EntityRenderer<BulletEntity> {

    static Random r = new Random();

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
        World world = bullet.getCommandSenderWorld();
        IBakedModel model = Minecraft.getInstance().getItemRenderer().getModel(bullet.getItem(), world, null);
        model = ModelLoader.instance().getBakedModel(new ResourceLocation("kaizokucraft:projectile/iron_bullet"), SimpleModelTransform.IDENTITY, RenderMaterial::sprite);
        Minecraft.getInstance().getItemRenderer().render(bullet.getItem().getStack(), ItemCameraTransforms.TransformType.NONE, false, matrixStack, iRenderTypeBuffer, p_225623_6_, 0, model);

        //if (true) return;

        List<BakedQuad> quads = model.getQuads(world.getBlockState(bullet.blockPosition()), null, r, EmptyModelData.INSTANCE);
        IVertexBuilder buffer = iRenderTypeBuffer.getBuffer(RenderType.cutout());
        matrixStack.pushPose();
        for (BakedQuad quad: quads) {
            buffer.addVertexData(matrixStack.last(), quad, 1, 1, 1, 1, p_225623_6_, 0);
        }
        matrixStack.popPose();
    }




}
