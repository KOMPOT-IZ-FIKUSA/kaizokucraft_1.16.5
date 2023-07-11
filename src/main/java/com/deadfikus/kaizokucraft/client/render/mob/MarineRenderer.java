package com.deadfikus.kaizokucraft.client.render.mob;

import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.core.entity.mob.MarineEntity;
import com.deadfikus.kaizokucraft.core.entity.mob.PirateEntity;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MarineRenderer extends BipedRenderer<MarineEntity, MarineModel> {
    public static final ResourceLocation texture = new ResourceLocation(ModMain.MODID, "textures/entity/marine.png");

    public MarineRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new MarineModel(0, true), 0.5F);
        this.addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.5F), new BipedModel<>(1.0F)));
    }

    @Override
    public ResourceLocation getTextureLocation(MarineEntity entity) {
        return texture;
    }
}
