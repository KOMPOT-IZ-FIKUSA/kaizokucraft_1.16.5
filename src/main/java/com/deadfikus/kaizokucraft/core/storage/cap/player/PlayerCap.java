package com.deadfikus.kaizokucraft.core.storage.cap.player;

import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import com.deadfikus.kaizokucraft.core.ability.base.*;
import com.deadfikus.kaizokucraft.core.network.PacketHandler;
import com.deadfikus.kaizokucraft.core.network.packet.toserver.SAbilityTurnOff;
import com.deadfikus.kaizokucraft.core.network.packet.toserver.SAbilityTurnOnRequest;
import com.deadfikus.kaizokucraft.core.quest.QuestBase;
import com.deadfikus.kaizokucraft.core.quest.QuestClass;
import com.deadfikus.kaizokucraft.core.storage.CyborgProfile;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCapProvider;
import com.deadfikus.kaizokucraft.core.teams.KaizokuTeamSerializable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayerCap implements INBTSerializable<CompoundNBT>, IPlayerCap {

    public int worldDataDirtiness = -1;
    private int balance = 0;
    private int hakiAttackXp = 0;
    private int hakiObservationXp = 0;
    private int hakiKingXp = 0;
    private ModEnums.Fruit fruit = ModEnums.Fruit.NONE;

    @Nullable
    public CyborgProfile cyborgProfile = new CyborgProfile();
    public final ArrayList<QuestBase> quests = new ArrayList<>();
    public ArrayList<Ability> abilities = new ArrayList<>();
    private int[] hotbarAbilityIndices = new int[5];

    private int teamId = -1;

    private boolean isDirty_ = true; // server field
    private boolean isVeryDirty_ = true; // server field
    long lastUpdateServerClock = System.currentTimeMillis(); // server field


    public void updateDirty() {
        if (isVeryDirty_) {
            return;
        }
        if (cyborgProfile != null && cyborgProfile.isDirty) {
            this.setVeryDirty();
        }
        for (QuestBase quest : quests) {
            if (quest.isDirty) {
                this.setDirty();
            }
        }
        for (Ability ability : abilities) {
            if (ability.isDirty) {
                this.setVeryDirty();
            }
        }
    }

    public boolean shouldBeUpdated() {
        updateDirty();
        long now = System.currentTimeMillis();
        return isVeryDirty_ && lastUpdateServerClock + 100 < now
                || isDirty_ && lastUpdateServerClock + 1000 < now ||
                lastUpdateServerClock + 5000 < now;
    }

    private void setAllNotDirty() {
        this.isVeryDirty_ = false;
        this.isDirty_ = false;
        if (cyborgProfile != null)
            this.cyborgProfile.isDirty = false;
        this.abilities.forEach(a -> a.isDirty = false);
        this.quests.forEach(q -> q.isDirty = false);
    }

    public void setUpdated() {
        setAllNotDirty();
        this.lastUpdateServerClock = System.currentTimeMillis();
    }

    public PlayerCap() {
        Arrays.fill(hotbarAbilityIndices, -1);
    }

    public boolean isDirty() {
        return this.isDirty_;
    }

    public boolean isVeryDirty() {
        return this.isVeryDirty_;
    }

    public void setDirty() {
        this.isDirty_ = true;
    }

    public void setVeryDirty() {
        this.isVeryDirty_ = true;
    }


    public void putAbilityToHotbar(int slot, int abilityIndex) {
        if (abilityIndex > abilities.size() || abilities.get(abilityIndex) instanceof PassiveAbility) {
            throw new IllegalArgumentException();
        }
        hotbarAbilityIndices[slot] = abilityIndex;
        setVeryDirty();
    }


    public int getBalance() {
        return this.balance;
    }

    public void setBalance(int b) {
        this.balance = Math.max(0, b);
        this.setDirty();
    }

    public int getHakiObservationXp() {
        return this.hakiObservationXp;
    }

    public void setHakiObservationXp(int xp) {
        this.hakiObservationXp = Math.max(0, xp);
        this.setDirty();
    }

    public int getHakiAttackXp() {
        return this.hakiAttackXp;
    }

    public void setHakiAttackXp(int xp) {
        this.hakiAttackXp = Math.max(0, xp);
        this.setDirty();
    }


    public void setTeam(int teamId) {
        setVeryDirty();
    }

    public KaizokuTeamSerializable getKaizokuTeam(World anyWorld) {
        return OverworldTeamsCapProvider.getOverworldCap(anyWorld).getTeamById(teamId);
    }

    public boolean hasFruit() {
        return this.fruit == ModEnums.Fruit.NONE;
    }

    public ModEnums.Fruit getFruit() {
        return this.fruit;
    }


    public boolean isCyborg() {
        return cyborgProfile != null && cyborgProfile.hasFridge();
    }

    public void addAbility(Ability ability) {
        this.abilities.add(ability);
        this.setVeryDirty();
    }

    public void removeAbility(AbilityEnum ability) {
        Ability ability1;
        for (int i = 0; i < abilities.size(); i++) {
            ability1 = abilities.get(i);
            if (ability1.getClass().equals(ability.class_)) {
                abilities.remove(ability1);
                for (int j = 0; j < hotbarAbilityIndices.length; j++) {
                    if (hotbarAbilityIndices[j] == i) {
                        hotbarAbilityIndices[j] = -1;
                    }
                }
                this.setVeryDirty();
                ModMain.logDebug("Removed ability " + ability1.getDescriptionData().name);
                return;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void tryToggleAbility(int hotbarIndex, LivingEntity user) {
        int inventoryAbilityIndex = hotbarAbilityIndices[hotbarIndex];
        if (inventoryAbilityIndex == -1)
            return;
        if (inventoryAbilityIndex >= abilities.size() || inventoryAbilityIndex < 0) {
            ModMain.logError("Trying to toggle ability with index " + inventoryAbilityIndex + " in inventory with " + abilities.size() + " abilities");
            return;
        }
        Ability ability = abilities.get(inventoryAbilityIndex);
        if (ability.getCurrentPhase() == ModEnums.AbilityPhase.READY) {
            PacketHandler.NETWORK.sendToServer(new SAbilityTurnOnRequest(inventoryAbilityIndex));
        } else {
            PacketHandler.NETWORK.sendToServer(new SAbilityTurnOff(inventoryAbilityIndex));
        }
    }

    public void onPlayerTick(PlayerEntity player) {
        for (QuestBase quest : quests) {
            quest.onOwnerTick(player);
        }
        for (Ability ability: abilities) {
            ability.onUserTick(player);
        }
    }

    @Override
    public PlayerCap getData() {
        return this;
    }

    @Override
    public void setData(PlayerCap other) {
        this.deserializeNBT(other.serializeNBT());
    }

    public static PlayerCap get(PlayerEntity player) {
        return player.getCapability(PlayerCapProvider.PLAYER_CAP).orElse(new PlayerCap()).getData();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("balance", balance);
        nbt.putInt("hakiAttackXp", hakiAttackXp);
        nbt.putInt("hakiKingXp", hakiKingXp);
        nbt.putInt("teamId", teamId);
        nbt.putInt("hakiObservationXp", hakiObservationXp);
        nbt.putShort("fruit", fruit.getI());
        if (cyborgProfile != null)
            nbt.put("cyborgProfile", cyborgProfile.serializeNBT());
        for (int i = 0; i < quests.size(); i++) {
            nbt.putShort("questsClasses" + i, QuestClass.get(quests.get(i).getClass()).getI());
            nbt.put("quests" + i, quests.get(i).serializeNBT());
        }
        for (int i = 0; i < abilities.size(); i++) {
            nbt.putShort("abilitiesClasses" + i, AbilityEnum.get(abilities.get(i).getClass()).getI());
            nbt.put("abilities" + i, abilities.get(i).serializeNBT());
        }
        for (int i = 0; i < hotbarAbilityIndices.length; i++) {
            nbt.putInt("hotbarAbilityIndices" + i, hotbarAbilityIndices[i]);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("balance"))
            balance = nbt.getInt("balance");
        if (nbt.contains("hakiAttackXp"))
            hakiAttackXp = nbt.getInt("hakiAttackXp");
        if (nbt.contains("hakiObservationXp"))
            hakiObservationXp = nbt.getInt("hakiObservationXp");
        if (nbt.contains("hakiKingXp"))
            hakiKingXp = nbt.getInt("hakiKingXp");
        if (nbt.contains("teamId"))
            teamId = nbt.getInt("teamId");
        if (nbt.contains("fruit"))
            fruit = ModEnums.Fruit.values[nbt.getShort("fruit")];
        if (nbt.contains("cyborgProfile"))
            cyborgProfile = CyborgProfile.deserializeNBT_(nbt.getCompound("cyborgProfile"));

        int i = 0;
        quests.clear();
        while (nbt.contains("quests" + i)) {
            try {
                QuestBase quest = QuestClass.values[nbt.getShort("questsClasses" + i)].class_.newInstance();
                quest.deserializeNBT(nbt.getCompound("quests" + i));
                quests.add(quest);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            i++;
        }

        i = 0;
        abilities.clear();
        while (nbt.contains("abilities" + i)) {
            CompoundNBT abilityData = nbt.getCompound("abilities" + i);
            AbilityEnum abilityEnum = AbilityEnum.values[nbt.getShort("abilitiesClasses" + i)];
            try {
                Ability instance = abilityEnum.initAbility(abilityData);
                abilities.add(instance);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            i++;
        }

        i = 0;
        while (nbt.contains("hotbarAbilityIndices" + i)) {
            hotbarAbilityIndices[i] = nbt.getInt("hotbarAbilityIndices" + i);
            i++;
        }
    }
}
