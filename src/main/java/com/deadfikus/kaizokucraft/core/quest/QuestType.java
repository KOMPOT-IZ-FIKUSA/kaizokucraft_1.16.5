package com.deadfikus.kaizokucraft.core.quest;

import com.deadfikus.kaizokucraft.core.entity.mob.MarineEntity;
import net.minecraft.entity.LivingEntity;

public enum QuestType {

    MARINE_1(1, MarineEntity.class);

    public int level;
    public Class<? extends IQuestMaster> master;

    QuestType(int level, Class<? extends IQuestMaster> master) {
        this.level = level;
        this.master = master;
    }

}
