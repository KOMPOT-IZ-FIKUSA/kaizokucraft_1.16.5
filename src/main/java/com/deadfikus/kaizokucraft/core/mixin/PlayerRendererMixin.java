package com.deadfikus.kaizokucraft.core.mixin;

import com.deadfikus.kaizokucraft.core.mixinhandlers.IPlayerRendererAccess;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin implements IPlayerRendererAccess {

    @Shadow protected abstract void renderHand(MatrixStack p_229145_1_, IRenderTypeBuffer p_229145_2_, int p_229145_3_, AbstractClientPlayerEntity p_229145_4_, ModelRenderer p_229145_5_, ModelRenderer p_229145_6_);

    @Override
    public void renderHand_(MatrixStack stackMatrix, IRenderTypeBuffer buffer, int huita_1, AbstractClientPlayerEntity player, ModelRenderer arm, ModelRenderer sleeve) {
        this.renderHand(stackMatrix, buffer, huita_1, player, arm, sleeve);
    }

//    @Inject(method = "renderHand", at = @At("HEAD"))
//    public void onRenderHand(MatrixStack stackMatrix, IRenderTypeBuffer buffer, int huita_1, AbstractClientPlayerEntity player, ModelRenderer arm, ModelRenderer sleeve, CallbackInfo ci){
//        System.out.println(huita_1);
//    }
}
