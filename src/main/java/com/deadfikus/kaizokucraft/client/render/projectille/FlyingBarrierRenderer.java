package com.deadfikus.kaizokucraft.client.render.projectille;

import com.deadfikus.kaizokucraft.core.entity.projectile.FlyingBarrierEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class FlyingBarrierRenderer extends EntityRenderer<FlyingBarrierEntity> {


    public FlyingBarrierRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(FlyingBarrierEntity barrier) {
        return null;
    }



    @Override
    public void render(FlyingBarrierEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }
}
