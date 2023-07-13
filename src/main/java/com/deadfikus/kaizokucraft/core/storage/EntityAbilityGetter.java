package com.deadfikus.kaizokucraft.core.storage;

import com.deadfikus.kaizokucraft.core.ability.base.Ability;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityAbilityGetter {

    public static List<Ability> getHotbarAbilities(Entity entity) {
        if (entity instanceof PlayerEntity) {
            return PlayerCap.get((PlayerEntity)entity).getHotbarAbilities();
        }
        if (entity instanceof LivingEntity) {
            // TODO: some livingEntity ability get logic
        }
        return new ArrayList<>();
    }

}
