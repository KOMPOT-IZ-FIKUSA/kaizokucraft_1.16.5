package com.deadfikus.kaizokucraft.core.teams;

import com.deadfikus.kaizokucraft.Constants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public class KaizokuTeamSerializable implements INBTSerializable<CompoundNBT> {


    private String name;
    private int id;
    private int piratePoints;
    private int marinePoints;
    private final ArrayList<UUID> members;

    public int getMembersCount() {
        return members.size();
    }

    public UUID getMemberUUID(int i) {
        return members.get(i);
    }

    public void addMember(UUID uuid) {
        members.add(uuid);
        setDirty();
    }

    public void removeMember(UUID uuid) {
        members.remove(uuid);
        setDirty();
    }

    private boolean isDirty;

    public void setDirty() {
        isDirty = true;
    }

    public void setClean() {
        isDirty = false;
    }

    public boolean isDirty() {
        return isDirty;
    }

    @Nullable
    private UUID capitan;


    private KaizokuTeamSerializable() {
        members = new ArrayList<>();
    }

    public KaizokuTeamSerializable(String name, int id, int piratePoints, int marinePoints, ArrayList<UUID> members, @Nullable UUID capitan) {
        this.name = name;
        this.id = id;
        this.piratePoints = piratePoints;
        this.marinePoints = marinePoints;
        this.members = members;
        this.capitan = capitan;
    }

    public static KaizokuTeamSerializable fromOneMember(String name, int id, UUID member, boolean setCapitan) {
        KaizokuTeamSerializable this_ = new KaizokuTeamSerializable();
        this_.name = name;
        this_.id = id;
        this_.piratePoints = Constants.Teams.InitScore;
        this_.marinePoints = Constants.Teams.InitScore;
        this_.members.add(member);
        if (setCapitan) this_.capitan = member;
        return this_;
    }

    public KaizokuTeamSerializable(CompoundNBT nbt) {
        members = new ArrayList<>();
        deserializeNBT(nbt);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return id == ((KaizokuTeamSerializable) obj).id;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("name", name);
        nbt.putInt("id", id);
        nbt.putInt("piratePoints", piratePoints);
        nbt.putInt("marinePoints", marinePoints);
        for (int i = 0; i < members.size(); i++) {
            nbt.putUUID("player" + i, members.get(i));
        }
        if (capitan != null) {
            nbt.putUUID("capitan", capitan);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.name = nbt.getString("name");
        this.id = nbt.getInt("id");
        this.piratePoints = nbt.getInt("piratePoints");
        this.marinePoints = nbt.getInt("marinePoints");
        int i = 0;
        while (nbt.hasUUID("player" + i)) {
            members.add(nbt.getUUID("player" + i));
            i++;
        }
        if (nbt.hasUUID("capitan")) {
            this.capitan = nbt.getUUID("capitan");
        }
    }

    @Override
    public String toString() {
        return "Team[id=" + id + " name=" + name + "]";
    }

}
