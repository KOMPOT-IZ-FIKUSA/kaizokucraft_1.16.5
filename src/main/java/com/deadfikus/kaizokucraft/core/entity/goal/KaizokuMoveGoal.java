package com.deadfikus.kaizokucraft.core.entity.goal;

import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.core.entity.mob.KaizokuEntity;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import com.deadfikus.kaizokucraft.core.teams.KaizokuTeamSerializable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

public class KaizokuMoveGoal extends Goal {
    KaizokuEntity entity;
    Weapon weaponInMainHand;
    Weapon weaponInOffHand;
    private BlockPos regularPos;
    int cooldownTicksLeft;
    double speed;

    public KaizokuMoveGoal(KaizokuEntity owner, double speed) {
        this.entity = owner;
        this.speed = speed;
    }

    public void setRegularPos(BlockPos pos) {
        this.regularPos = pos;
    }



    protected void updateWeaponState() {
        Item mainHandItem = entity.getMainHandItem().getItem();
        Item offHandItem = entity.getMainHandItem().getItem();
        if (mainHandItem == Items.AIR) weaponInMainHand = Weapon.NONE;
        if (offHandItem == Items.AIR) weaponInOffHand = Weapon.NONE;
    }

    private boolean hasAttackTarget() {
        return entity.getTarget() != null;
    }


    @Override
    public boolean canUse() {
        return false;
    }

    @Override
    public void tick() {
        updateWeaponState();
    }


}

