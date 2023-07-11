package com.deadfikus.kaizokucraft.core.mixin;

import com.deadfikus.kaizokucraft.core.mixinhandlers.IDamageSourceAccess;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DamageSource.class)
public abstract class DamageSourceMixin implements IDamageSourceAccess {
    private boolean isKairoseki = false;

    @Override
    public DamageSource setKairoseki() {
        isKairoseki = true;
        return (DamageSource)(Object) this;
    }
}
