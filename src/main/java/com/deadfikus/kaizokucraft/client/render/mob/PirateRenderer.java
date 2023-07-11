package com.deadfikus.kaizokucraft.client.render.mob;

import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.core.entity.mob.PirateEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PirateRenderer extends LivingRenderer<PirateEntity, PlayerModel<PirateEntity>> {
    public static final ResourceLocation texture = new ResourceLocation(ModMain.MODID, "textures/entity/pirate.png");

    public PirateRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new PlayerModel<>(0, false), 0.5F);
        this.addLayer(new HeldItemLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(PirateEntity pirateEntity) {
        return texture;
    }
}
