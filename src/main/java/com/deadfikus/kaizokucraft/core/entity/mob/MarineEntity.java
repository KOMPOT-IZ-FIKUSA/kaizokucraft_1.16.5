package com.deadfikus.kaizokucraft.core.entity.mob;

import com.deadfikus.kaizokucraft.Constants;
import com.deadfikus.kaizokucraft.core.KaizokuItems;
import com.deadfikus.kaizokucraft.core.item.MarineCapItem;
import com.deadfikus.kaizokucraft.core.quest.IQuestMaster;
import com.deadfikus.kaizokucraft.core.quest.QuestBase;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCapProvider;
import com.deadfikus.kaizokucraft.core.teams.IEntityInTeam;
import com.deadfikus.kaizokucraft.core.teams.KaizokuTeamSerializable;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import java.util.List;

public class MarineEntity extends KaizokuEntity {

    public MarineEntity(EntityType<? extends MarineEntity> t, World world) {
        super(t, world);
    }


    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
        super.populateDefaultEquipmentSlots(p_180481_1_);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.33d)
                .add(Attributes.ATTACK_DAMAGE, 10d);

    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.getItemBySlot(EquipmentSlotType.HEAD).getItem() != KaizokuItems.MARINE_CAP) {
            this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(KaizokuItems.MARINE_CAP));
            this.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(Items.IRON_LEGGINGS));
            this.setItemSlot(EquipmentSlotType.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
            this.setItemSlot(EquipmentSlotType.FEET, new ItemStack(Items.IRON_BOOTS));
            this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(KaizokuItems.PISTOL));
        }
    }

    protected void initTeam() {
        Vector3d point1 = position().add(Constants.Teams.KaizokuSearchTeammatesRange);
        Vector3d point2 = position().subtract(Constants.Teams.KaizokuSearchTeammatesRange);
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(point1, point2);
        List<MarineEntity> entities = getCommandSenderWorld().getEntitiesOfClass(MarineEntity.class, axisAlignedBB, entity -> true);
        int otherTeamId;
        for (MarineEntity entity : entities) {
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

    protected void registerGoals() {
    }

    @Override
    public boolean isPirate() {
        return false;
    }

    @Override
    public boolean isMarine() {
        return true;
    }

    @Override
    public boolean isTeammate(IEntityInTeam other) {
        return other.isMarine();
    }

    @Override
    public boolean isEnemy(IEntityInTeam other) {
        return other.isPirate();
    }

    @Override
    protected void initBalance() {
        setBalance(100);
    }


    @Override
    public List<QuestBase> questsAvailableFor(PlayerEntity player) {
        return null;
    }


    @Override
    public IPacket<?> getAddEntityPacket() {
        return super.getAddEntityPacket();
    }
}
