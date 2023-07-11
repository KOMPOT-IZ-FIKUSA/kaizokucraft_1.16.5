package com.deadfikus.kaizokucraft.core.mixin;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.math.vector.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractGui.class)
public class AbstractGuiMixin {

    @Inject(method = "innerBlit(Lnet/minecraft/util/math/vector/Matrix4f;IIIIIFFFF)V", at = @At(value = "HEAD"), cancellable = true)
    private static void innerBlit(Matrix4f p_238461_0_, int p_238461_1_, int p_238461_2_, int p_238461_3_, int p_238461_4_, int p_238461_5_, float p_238461_6_, float p_238461_7_, float p_238461_8_, float p_238461_9_, CallbackInfo cir) {

    }

}
