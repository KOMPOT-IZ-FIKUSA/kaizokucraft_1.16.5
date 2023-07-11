package com.deadfikus.kaizokucraft.core.quest.reward;

import com.deadfikus.kaizokucraft.exceptions.ClientSideException;
import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class AbilityReward extends AbstractQuestReward {

    private AbilityEnum ability;

    public AbilityReward(AbilityEnum ability, ResourceLocation iconLocation) {
        super(iconLocation);
        this.ability = ability;
    }

    public AbilityReward() {
    }

    @Override
    public RewardEnum getEnum() {
        return RewardEnum.ABILITY_REWARD;
    }

    @Override
    public void rewardPlayer(PlayerEntity player) {
        if (player.getCommandSenderWorld().isClientSide) {
            throw new ClientSideException();
        }
        PlayerCap cap = PlayerCap.get(player);
        try {
            cap.abilities.add(ability.initAbility());
        } catch (InstantiationException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("super", super.serializeNBT());
        nbt.putInt("abilityEnum", ability.getI());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt.getCompound("super"));
        this.ability = AbilityEnum.values[nbt.getInt("abilityEnum")];
    }
}
