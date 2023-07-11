package com.deadfikus.kaizokucraft.core.mixin;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "getTextureAtlas", at = @At("HEAD"))
    public void getTextureAtlas(ResourceLocation resourceLocation, CallbackInfoReturnable<Function<ResourceLocation, TextureAtlasSprite>> cir) {

    }


}
