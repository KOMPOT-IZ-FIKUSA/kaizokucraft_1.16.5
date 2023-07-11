package com.deadfikus.kaizokucraft.core.quest.reward;

import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import net.minecraft.nbt.CompoundNBT;

public enum RewardEnum {
    ITEM_REWARD(ItemReward.class),
    ABILITY_REWARD(AbilityReward.class);

    private Class<? extends AbstractQuestReward> clazz;

    RewardEnum(Class<? extends AbstractQuestReward> clazz) {
        this.clazz = clazz;
    }
    public static final RewardEnum[] values = RewardEnum.values();

    public int getI() {
        for (short i = 0; i < values.length; i++) {
            if (values[i] == this) {
                return i;
            }
        }
        return -1;
    }

    public AbstractQuestReward initReward(CompoundNBT nbt) throws InstantiationException, IllegalAccessException {
        AbstractQuestReward reward = clazz.newInstance();
        reward.deserializeNBT(nbt);
        return reward;
    }
}
