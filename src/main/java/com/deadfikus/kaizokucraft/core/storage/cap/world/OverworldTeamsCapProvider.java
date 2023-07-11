package com.deadfikus.kaizokucraft.core.storage.cap.world;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OverworldTeamsCapProvider implements ICapabilitySerializable<CompoundNBT> {

    @CapabilityInject(OverworldTeamsCap.class)
    public static final Capability<OverworldTeamsCap> WORLD_CAP = null;
    private LazyOptional<OverworldTeamsCap> instance = LazyOptional.of(() -> WORLD_CAP.getDefaultInstance());

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction direction) {
        return capability == WORLD_CAP ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) WORLD_CAP.getStorage().writeNBT(WORLD_CAP, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        WORLD_CAP.getStorage().readNBT(WORLD_CAP, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
    }

    public static OverworldTeamsCap getOverworldCap(@Nonnull World anyWorld) {
        if (anyWorld.dimension() == World.OVERWORLD) {
            return anyWorld.getCapability(WORLD_CAP).orElse(new OverworldTeamsCap());
        } else {
            return anyWorld.getServer().overworld().getCapability(WORLD_CAP).orElse(new OverworldTeamsCap());
        }
    }
}
