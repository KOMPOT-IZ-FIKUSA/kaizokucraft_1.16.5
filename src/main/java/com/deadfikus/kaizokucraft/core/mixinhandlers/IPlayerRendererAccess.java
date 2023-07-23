package com.deadfikus.kaizokucraft.core.mixinhandlers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ModelRenderer;

public interface IPlayerRendererAccess {
    void renderHand_(MatrixStack stackMatrix, IRenderTypeBuffer buffer, int huita_1, AbstractClientPlayerEntity player, ModelRenderer arm, ModelRenderer sleeve);
}
