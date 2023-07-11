package com.deadfikus.kaizokucraft.client.render.mob;

import com.deadfikus.kaizokucraft.core.entity.mob.MarineEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;

public class MarineModel extends BipedModel<MarineEntity> {
    public MarineModel(float armorLayerSize, boolean texture64) {
        super(armorLayerSize, 0, 64, texture64 ? 64 : 32);
    }
}
