package com.deadfikus.kaizokucraft.core.mixin;

import com.deadfikus.kaizokucraft.core.mixinhandlers.IMatrixStackAccess;
import com.mojang.blaze3d.matrix.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Deque;

@Mixin(MatrixStack.class)
public class MatrixStackMixin implements IMatrixStackAccess {

    @Shadow @Final private Deque<MatrixStack.Entry> poseStack;
    public MatrixStack.Entry lastPopped = null;

    @Inject(method = "popPose", at = @At("HEAD"))
    public void onPopPose(CallbackInfo ci) {
        lastPopped = poseStack.getLast();
    }

    @Override
    public MatrixStack.Entry getLastPopped() {
        return lastPopped;
    }

    @Override
    public void reAddLastPopped() {
        this.poseStack.addLast(lastPopped);
    }
}
