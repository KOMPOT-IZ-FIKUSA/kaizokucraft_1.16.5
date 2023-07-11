package com.deadfikus.kaizokucraft.core.storage.cap.player;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class PlayerCapStorage implements Capability.IStorage<IPlayerCap> {
    @Nullable
    @Override
    public CompoundNBT writeNBT(Capability<IPlayerCap> capability, IPlayerCap instance, Direction direction) {
        return instance.getData().serializeNBT();
    }

    @Override
    public void readNBT(Capability<IPlayerCap> capability, IPlayerCap instance, Direction direction, INBT inbt) {
        instance.getData().deserializeNBT((CompoundNBT) inbt);
    }
}
