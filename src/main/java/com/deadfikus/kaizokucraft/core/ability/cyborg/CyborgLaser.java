package com.deadfikus.kaizokucraft.core.ability.cyborg;


import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import com.deadfikus.kaizokucraft.core.ability.base.LeverAbility;
import com.deadfikus.kaizokucraft.core.storage.CyborgProfile;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class CyborgLaser extends LeverAbility implements INBTSerializable<CompoundNBT> {
    public CyborgLaser() {
        super(AbilityEnum.CYBORG_LASER);
    }

    @Override
    protected void handleWorkStarted(LivingEntity user) {

    }

    @Override
    protected void handleWorkTick(LivingEntity user) {
        if (user instanceof ServerPlayerEntity) {
            PlayerCap playerData = PlayerCap.get((PlayerEntity) user);
            CyborgProfile cyborgProfile = playerData.cyborgProfile;
            if (cyborgProfile.getCola() > 0) {
                cyborgProfile.decreaseCola(1d/20d/10d);
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
    public void forceStop(LivingEntity user) {

    }
}
