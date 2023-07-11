package com.deadfikus.kaizokucraft.core.mixin;

import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireballEntity.class)
public class AbstractFireballEntityMixin {

    @Inject(method = "getItem", at = @At("RETURN"))
    public void getItem(CallbackInfoReturnable<ItemStack> cir) {
    }

}
