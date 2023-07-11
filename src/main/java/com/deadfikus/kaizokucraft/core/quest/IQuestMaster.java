package com.deadfikus.kaizokucraft.core.quest;

import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public interface IQuestMaster {

    List<QuestBase> questsAvailableFor(PlayerEntity player);

    public default boolean hasAvailableQuestsFor(PlayerEntity player) {
        return questsAvailableFor(player).size() > 0;
    }



}
