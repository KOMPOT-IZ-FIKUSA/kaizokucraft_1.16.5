package com.deadfikus.kaizokucraft.core;

import com.deadfikus.kaizokucraft.core.ability.AbilityEvent;
import com.deadfikus.kaizokucraft.core.ability.AbilityEventHandler;
import com.deadfikus.kaizokucraft.core.entity.KaizokuEntityTypes;
import com.deadfikus.kaizokucraft.core.entity.mob.MarineAdmiralEntity;
import com.deadfikus.kaizokucraft.core.entity.mob.MarineEntity;
import com.deadfikus.kaizokucraft.core.entity.mob.PirateEntity;
import com.deadfikus.kaizokucraft.core.entity.projectile.BulletEntity;
import com.deadfikus.kaizokucraft.core.network.PacketHandler;
import com.deadfikus.kaizokucraft.core.quest.QuestsEventsHandler;
import com.deadfikus.kaizokucraft.core.storage.cap.CapabilityEventHandler;
import com.deadfikus.kaizokucraft.core.storage.cap.player.IPlayerCap;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCapStorage;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CoreRegister {

    private static CoreRegister instance;
    private CoreRegister() {}
    public static CoreRegister getInstance() {
        if (instance == null)
            instance = new CoreRegister();
        return instance;
    }

    public static final PacketHandler packetHandler = new PacketHandler();


    private void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new AbilityEventHandler());
        MinecraftForge.EVENT_BUS.register(new QuestsEventsHandler());
        MinecraftForge.EVENT_BUS.register(new CoreEventHandler());
    }

    public void registerPacketHandler() {
        packetHandler.register();
    }

    public void registerEntities() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        KaizokuEntityTypes.ENTITY_TYPES.register(eventBus);
    }


    private void registerModEvents() {
        MinecraftForge.EVENT_BUS.register(AbilityEvent.PhaseChangedEvent.class);
        MinecraftForge.EVENT_BUS.register(AbilityEvent.ForceStopEvent.class);
    }

    public void onSetupRegister(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerCap.class, new PlayerCapStorage(), PlayerCap::new);
        CapabilityManager.INSTANCE.register(OverworldTeamsCap.class, new OverworldTeamsCap(), OverworldTeamsCap::new);
        MinecraftForge.EVENT_BUS.register(new CapabilityEventHandler());
    }

    public void onModInitRegister(IEventBus eventBus) {
        registerModEvents();
        registerEventHandlers();
        registerPacketHandler();
        registerEntities();
        eventBus.register(new ModInitEventsHandler());
    }

    private class ModInitEventsHandler {

        @SubscribeEvent
        public void registerEntityAttributes(EntityAttributeCreationEvent event) {
            event.put(KaizokuEntityTypes.MARINE.get(), MarineEntity.setCustomAttributes().build());
            event.put(KaizokuEntityTypes.MARINE_ADMIRAL.get(), MarineAdmiralEntity.setCustomAttributes().build());
            event.put(KaizokuEntityTypes.PIRATE.get(), PirateEntity.setCustomAttributes().build());
        }

        @SubscribeEvent
        public void registerItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(KaizokuItems.BARRIER_BLOCK);
            event.getRegistry().register(KaizokuItems.PISTOL);
            event.getRegistry().register(KaizokuItems.IRON_BULLET);
            event.getRegistry().register(KaizokuItems.KAIROSEKI_BULLET);
            event.getRegistry().register(KaizokuItems.FIRE_BULLET);
            event.getRegistry().register(KaizokuItems.FIRE_KAIROSEKI_BULLET);
            event.getRegistry().register(KaizokuItems.IRON_KNIFE);
            event.getRegistry().register(KaizokuItems.MARINE_CAP);
        }

        @SubscribeEvent
        public void onBlocksRegister(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(KaizokuBlocks.BARRIER_BLOCK);
        }
    }
}



