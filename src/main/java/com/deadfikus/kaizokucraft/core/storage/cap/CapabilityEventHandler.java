package com.deadfikus.kaizokucraft.core.storage.cap;

import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.core.storage.cap.player.IPlayerCap;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCapProvider;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCapProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilityEventHandler {
    public static final ResourceLocation PLAYER_CAP_LOCATION = new ResourceLocation(ModMain.MODID, "player");
    public static final ResourceLocation WORLD_CAP_LOCATION = new ResourceLocation(ModMain.MODID, "world");

    @SubscribeEvent
    public void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(PLAYER_CAP_LOCATION, new PlayerCapProvider());
        }
    }
    @SubscribeEvent
    public void attachWorldCapability(AttachCapabilitiesEvent<World> event) {
        if (event.getObject().dimension() == World.OVERWORLD) {
            event.addCapability(WORLD_CAP_LOCATION, new OverworldTeamsCapProvider());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        IPlayerCap newCap = event.getPlayer().getCapability(PlayerCapProvider.PLAYER_CAP).orElse(new PlayerCap());
        IPlayerCap oldCap = event.getOriginal().getCapability(PlayerCapProvider.PLAYER_CAP).orElse(new PlayerCap());
        newCap.setData(oldCap.getData());

        if (event.getEntity() instanceof ServerPlayerEntity) {
            newCap.getData().setVeryDirty();
        }
    }
}
