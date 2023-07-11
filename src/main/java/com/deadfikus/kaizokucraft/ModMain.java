package com.deadfikus.kaizokucraft;

import com.deadfikus.kaizokucraft.client.ClientRegister;
import com.deadfikus.kaizokucraft.client.KeybindsRegister;
import com.deadfikus.kaizokucraft.core.CoreRegister;
import com.deadfikus.kaizokucraft.core.KaizokuBlocks;
import com.deadfikus.kaizokucraft.core.ability.AbilityEvent;
import com.deadfikus.kaizokucraft.core.network.ServerReceivedEvent;
import com.deadfikus.kaizokucraft.core.storage.cap.CapabilityEventHandler;
import com.deadfikus.kaizokucraft.core.storage.cap.player.IPlayerCap;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCapStorage;
import com.deadfikus.kaizokucraft.core.storage.cap.world.OverworldTeamsCap;
import com.deadfikus.kaizokucraft.proxy.ClientProxy;
import com.deadfikus.kaizokucraft.proxy.IProxy;
import com.deadfikus.kaizokucraft.proxy.ServerProxy;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;


@Mod(ModMain.MODID)
public class ModMain {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "kaizokucraft";
    public static IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public ModMain() {
        MixinBootstrap.init();
        Mixins.addConfigurations("kaizokucraft.mixin.json");

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);

        CoreRegister.getInstance().onModInitRegister(eventBus);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientRegister.getInstance().onModInitRegister(eventBus);
        }

    }

    private void commonSetup(final FMLCommonSetupEvent event) {  // ls
        CoreRegister.getInstance().onSetupRegister(event);
    }

    private void clientSetup(final FMLClientSetupEvent event) {  // pc
        ClientRegister.getInstance().onSetupRegister();
    }

    public static void logDebug(String key, String... parameters) {
        StringBuilder out = new StringBuilder("[" + MODID + "] [Debug] [" + key + "]");
        out.append(" (");
        for (String p : parameters) {
            out.append(p).append(", ");
        }
        out.append(")");
        LOGGER.debug(out.toString());
    }

    public static void logWarning(String key, String... parameters) {
        StringBuilder out = new StringBuilder("[" + MODID + "] [Debug] [" + key + "]");
        out.append(" (");
        for (String p : parameters) {
            out.append(p).append(", ");
        }
        out.append(")");
        LOGGER.warn(out.toString());
    }

    public static void logError(String message) {
        LOGGER.error("[" + MODID + "] [Debug] " + message);
    }


}
