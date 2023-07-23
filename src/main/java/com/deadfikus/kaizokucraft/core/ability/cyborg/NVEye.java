package com.deadfikus.kaizokucraft.core.ability.cyborg;


import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import com.deadfikus.kaizokucraft.core.ability.base.LeverAbility;
import com.deadfikus.kaizokucraft.core.storage.CyborgProfile;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class NVEye extends LeverAbility implements INBTSerializable<CompoundNBT> {
    public NVEye() {
        super(AbilityEnum.NV_EYE);
    }

    @Override
    protected void handleWorkTick(LivingEntity user) {
        if (user instanceof ServerPlayerEntity) {
            PlayerCap playerData = PlayerCap.get((PlayerEntity) user);
            CyborgProfile cyborgProfile = playerData.cyborgProfile;
            if (cyborgProfile.getCola() > 0) {
                EffectInstance effect = new EffectInstance(Effects.NIGHT_VISION, 10, 1, false, false, false);
                user.addEffect(effect);
                cyborgProfile.decreaseCola(1d/20d/120d);
            } else {
                tryTurnOff(user);
            }
        }
    }

    @Override
    protected void handleWorkEnded(LivingEntity user) {

    }

    @Override
    protected int calculateCooldown(LivingEntity user) {
        return 0;
    }

    @Override
    protected void handleWorkStarted(LivingEntity user) {
        if (user instanceof ServerPlayerEntity) {
            PlayerCap playerData = PlayerCap.get((PlayerEntity) user);
            CyborgProfile cyborgProfile = playerData.cyborgProfile;
            cyborgProfile.decreaseCola(1d/240d);
        }
    }

    @Override
    public void setAvailable(LivingEntity user) {

    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("super", super.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt.getCompound("super"));
    }

    @Override
    public void onClickBlock(PlayerInteractEvent.LeftClickBlock event) {

    }

    @Override
    public void forceStop(LivingEntity user) {

    }
}
