package com.deadfikus.kaizokucraft.core.entity.mob;

import com.deadfikus.kaizokucraft.Constants;
import com.deadfikus.kaizokucraft.core.quest.QuestBase;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCapProvider;
import com.deadfikus.kaizokucraft.core.teams.IEntityInTeam;
import com.deadfikus.kaizokucraft.core.teams.KaizokuTeamSerializable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class PirateEntity extends KaizokuEntity {

    public PirateEntity(EntityType<? extends PirateEntity> t, World world) {
        super(t, world);

    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.33d)
                .add(Attributes.ATTACK_DAMAGE, 10d);
    }

    protected void registerGoals() {
    }


    protected void initTeam() {
        Vector3d point1 = position().add(Constants.Teams.KaizokuSearchTeammatesRange);
        Vector3d point2 = position().subtract(Constants.Teams.KaizokuSearchTeammatesRange);
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(point1, point2);
        List<PirateEntity> entities = getCommandSenderWorld().getEntitiesOfClass(PirateEntity.class, axisAlignedBB, entity -> true);
        int otherTeamId;
        for (PirateEntity entity : entities) {
            otherTeamId = entity.getTeamId();
            if (otherTeamId != -1) {
                this.entityData.set(TEAM_ID, otherTeamId);
                entity.getKaizokuTeam().addMember(uuid);
                return;
            }
        }
        KaizokuTeamSerializable newTeam = OverworldTeamsCapProvider.getOverworldCap(getCommandSenderWorld()).createTeamWithSingleMember(uuid, true);
        this.entityData.set(TEAM_ID, newTeam.getId());
    }

    @Override
    public boolean isPirate() {
        return true;
    }

    @Override
    public boolean isMarine() {
        return false;
    }

    @Override
    public boolean isTeammate(IEntityInTeam other) {
        return other.isPirate();
    }

    @Override
    public boolean isEnemy(IEntityInTeam other) {
        return !other.isPirate();
    }

    @Override
    protected void initBalance() {
        setBalance(100);
    }

    @Override
    public List<QuestBase> questsAvailableFor(PlayerEntity player) {
        return null;
    }
}

