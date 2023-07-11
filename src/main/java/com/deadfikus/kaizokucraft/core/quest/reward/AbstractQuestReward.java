package com.deadfikus.kaizokucraft.core.quest.reward;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class AbstractQuestReward implements INBTSerializable<CompoundNBT> {
    public abstract RewardEnum getEnum();

    public abstract void rewardPlayer(PlayerEntity player);

    public ResourceLocation iconLocation;

    public AbstractQuestReward(ResourceLocation iconLocation) {
        this.iconLocation = iconLocation;
    }

    public AbstractQuestReward() {
        iconLocation = new ResourceLocation("minecraft:textures/block/bedrock.png");
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("icon", iconLocation.toString());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.iconLocation = new ResourceLocation(nbt.getString("icon"));
    }
}
