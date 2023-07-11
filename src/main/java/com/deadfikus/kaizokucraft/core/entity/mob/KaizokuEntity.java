package com.deadfikus.kaizokucraft.core.entity.mob;

import com.deadfikus.kaizokucraft.Constants;
import com.deadfikus.kaizokucraft.core.entity.goal.KaizokuMoveGoal;
import com.deadfikus.kaizokucraft.core.quest.IQuestMaster;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCapProvider;
import com.deadfikus.kaizokucraft.core.teams.IEntityInTeam;
import com.deadfikus.kaizokucraft.core.teams.KaizokuTeamSerializable;
import net.minecraft.entity.*;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public abstract class KaizokuEntity extends CreatureEntity implements IEntityInTeam, IQuestMaster {
    protected KaizokuMoveGoal moveGoal;
    public static DataParameter<Integer> TEAM_ID = EntityDataManager.defineId(KaizokuEntity.class, DataSerializers.INT);
    public static DataParameter<Integer> BALANCE_ID = EntityDataManager.defineId(KaizokuEntity.class, DataSerializers.INT);


    protected KaizokuEntity(EntityType<? extends KaizokuEntity> t, World world) {
        super(t, world);
        moveGoal = new KaizokuMoveGoal(this, 0.2);
        setRegularPos(new BlockPos(this.position()));
    }

    protected abstract void initTeam();

    @Override
    public void baseTick() {
        if (!getCommandSenderWorld().isClientSide) {
            if (getTeamId() == -1) initTeam();
            if (getBalance() == -1) initBalance();
        }
        super.baseTick();
    }

    protected abstract void initBalance();

    public int getBalance() {
        return entityData.get(BALANCE_ID);
    }

    public void setBalance(int value) {
        entityData.set(BALANCE_ID, value);
    }

    public int getTeamId() {
        return entityData.get(TEAM_ID);
    }

    public final void setRegularPos(BlockPos pos) {
        if (moveGoal != null) {
            moveGoal.setRegularPos(pos);
        }
    }

    @Override
    public KaizokuTeamSerializable getKaizokuTeam() {
        return OverworldTeamsCapProvider.getOverworldCap(this.getCommandSenderWorld()).getTeamById(getTeamId());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(TEAM_ID, -1);
        entityData.define(BALANCE_ID, -1);
    }
}
