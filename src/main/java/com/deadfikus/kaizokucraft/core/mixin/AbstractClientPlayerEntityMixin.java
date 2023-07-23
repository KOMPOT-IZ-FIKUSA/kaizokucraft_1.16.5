package com.deadfikus.kaizokucraft.core.mixin;

import com.deadfikus.kaizokucraft.core.mixinhandlers.IAbstractClientPlayerAccess;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin implements IAbstractClientPlayerAccess {
    boolean returnBarrierTexture = false;
    @Inject(method = "getSkinTextureLocation", at = @At("HEAD"), cancellable = true)
    public void getSkinTextureLocation(CallbackInfoReturnable<ResourceLocation> cir){
        if(returnBarrierTexture)
            cir.setReturnValue(new ResourceLocation("kaizokucraft:textures/entity/barrier_person.png"));
    }

    public void setReturnBarrierTexture(boolean value){
        returnBarrierTexture = value;
    }
}
