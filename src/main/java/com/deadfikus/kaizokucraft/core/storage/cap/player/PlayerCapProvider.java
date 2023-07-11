package com.deadfikus.kaizokucraft.core.storage.cap.player;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerCapProvider implements ICapabilitySerializable<CompoundNBT> {
    @CapabilityInject(IPlayerCap.class)
    public static final Capability<IPlayerCap> PLAYER_CAP = null;
    private LazyOptional<IPlayerCap> instance = LazyOptional.of(() -> PLAYER_CAP.getDefaultInstance());

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction direction) {
        return capability == PLAYER_CAP ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) PLAYER_CAP.getStorage().writeNBT(PLAYER_CAP, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        PLAYER_CAP.getStorage().readNBT(PLAYER_CAP, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
    }
}
