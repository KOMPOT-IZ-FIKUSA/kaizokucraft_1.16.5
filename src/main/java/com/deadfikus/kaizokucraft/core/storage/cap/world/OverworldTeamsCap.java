package com.deadfikus.kaizokucraft.core.storage.cap.world;

import com.deadfikus.kaizokucraft.core.teams.KaizokuTeamSerializable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.UUID;

public class OverworldTeamsCap implements INBTSerializable<CompoundNBT>, Capability.IStorage<OverworldTeamsCap> {
    private final ArrayList<KaizokuTeamSerializable> teams = new ArrayList<>();
    int valueOfDirtiness = 0;

    public OverworldTeamsCap() {

    }

    public KaizokuTeamSerializable getTeamById(int id) {
        KaizokuTeamSerializable result;
        for (KaizokuTeamSerializable team : teams) {
            result = team;
            if (result.getId() == id) {
                return result;
            }
        }
        throw new IllegalArgumentException("Team with given id has not found");
    }

    public void updateDirtiness() {
        boolean increment = false;
        for (KaizokuTeamSerializable team : teams) {
            if (team.isDirty()) {
                team.setClean();
                increment = true;
            }
        }
        if (increment) {
            valueOfDirtiness += 1;
        }
    }

    public int getDirtiness() {
        return valueOfDirtiness;
    }

    public KaizokuTeamSerializable createTeamWithSingleMember(UUID singlePlayerId, boolean setCapitan) {
        int id = 0;
        if (teams.size() > 0) {
            id = teams.get(teams.size() - 1).getId() + 1;
        }
        KaizokuTeamSerializable team = KaizokuTeamSerializable.fromOneMember("kaizokucraft.team.lonelyplayer", id, singlePlayerId, setCapitan);
        teams.add(team);
        valueOfDirtiness += 1;
        return team;
    }

    public void removeTeam(KaizokuTeamSerializable team) {
        teams.remove(team);
        valueOfDirtiness += 1;
    }

    public int getTeamsCount() {
        return teams.size();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        for (int i = 0; i < teams.size(); i++) {
            nbt.put("team" + i, teams.get(i).serializeNBT());
        }
        nbt.putInt("dirtiness", valueOfDirtiness);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        teams.clear();
        int i = 0;
        while (nbt.contains("team" + i)) {
            teams.add(new KaizokuTeamSerializable(nbt.getCompound("team" + i)));
            i++;
        }
        valueOfDirtiness = nbt.getInt("dirtiness");
    }

    @Override
    public INBT writeNBT(Capability<OverworldTeamsCap> capability, OverworldTeamsCap iOverworldCap, Direction direction) {
        return ((OverworldTeamsCap) iOverworldCap).serializeNBT();
    }

    @Override
    public void readNBT(Capability<OverworldTeamsCap> capability, OverworldTeamsCap iOverworldCap, Direction direction, INBT inbt) {
        ((OverworldTeamsCap) iOverworldCap).deserializeNBT((CompoundNBT) inbt);
    }
}
