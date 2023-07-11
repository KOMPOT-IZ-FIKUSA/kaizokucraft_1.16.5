package com.deadfikus.kaizokucraft.core.quest;

import com.deadfikus.kaizokucraft.exceptions.DeserializationException;
import com.deadfikus.kaizokucraft.core.quest.reward.AbstractQuestReward;
import com.deadfikus.kaizokucraft.core.quest.reward.RewardEnum;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.living.LivingEvent;

public abstract class QuestBase implements INBTSerializable<CompoundNBT> {
    public String description;
    public AbstractQuestReward reward;
    private double progress;
    public boolean isDirty;


    public double getProgress() {
        return this.progress;
    }
    public void setProgress(double p) {
        this.progress = p;
        this.isDirty = true;
    }

    public QuestBase(String description, AbstractQuestReward reward) {
        this.description = description;
        this.reward = reward;
        this.progress = 0;
    }

    public void onOwnerTick(PlayerEntity player) {

    }

    public void onOwnerLivingJump(LivingEvent.LivingJumpEvent event) {

    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT out = new CompoundNBT();
        out.putString("description", description);
        out.putInt("rewardEnum", reward.getEnum().getI());
        out.put("reward", reward.serializeNBT());
        out.putDouble("progress", progress);
        return out;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("description")) {
            description = nbt.getString("description");
        }
        if (nbt.contains("rewardEnum") && nbt.contains("reward")) {
            try {
                reward = RewardEnum.values[nbt.getInt("rewardEnum")].initReward(nbt.getCompound("reward"));
            } catch (InstantiationException | IllegalAccessException e) {
                throw new DeserializationException("Cannot deserialize quest reward");
            }
        }
        if (nbt.contains("progress")) {
            progress = nbt.getDouble("progress");
        }
    }
}
