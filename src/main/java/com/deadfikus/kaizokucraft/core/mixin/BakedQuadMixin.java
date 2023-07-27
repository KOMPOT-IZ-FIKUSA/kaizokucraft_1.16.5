package com.deadfikus.kaizokucraft.core.mixin;

import com.deadfikus.kaizokucraft.core.mixinhandlers.IBakedQuadAccess;
import net.minecraft.client.renderer.model.BakedQuad;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BakedQuad.class)
public class BakedQuadMixin implements IBakedQuadAccess {


    @Shadow @Final protected int[] vertices;

    @Override
    public void setPosition(int index, float x, float y, float z) {
        index = index * 8;
        vertices[index] = Float.floatToRawIntBits(x);
        vertices[index + 1] = Float.floatToRawIntBits(y);
        vertices[index + 2] = Float.floatToRawIntBits(z);
    }
}
