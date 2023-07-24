package com.deadfikus.kaizokucraft.client;


import com.deadfikus.kaizokucraft.client.event.ClientEventsHandler;
import com.deadfikus.kaizokucraft.client.gui.VignetteRenderer;
import com.deadfikus.kaizokucraft.client.render.fruit.BariRenderHandler;
import com.deadfikus.kaizokucraft.client.render.mob.MarineAdmiralRenderer;
import com.deadfikus.kaizokucraft.client.render.mob.MarineRenderer;
import com.deadfikus.kaizokucraft.client.render.mob.PirateRenderer;
import com.deadfikus.kaizokucraft.client.render.projectille.BulletRenderer;
import com.deadfikus.kaizokucraft.client.render.projectille.FlyingBarrierRenderer;
import com.deadfikus.kaizokucraft.core.KaizokuBlocks;
import com.deadfikus.kaizokucraft.core.entity.KaizokuEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.DragonFireballRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.SpriteMap;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.SimpleModelTransform;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.function.Function;

public class ClientRegister {
    private static ClientRegister INSTANCE;

    private ClientRegister() {
    }

    public static ClientRegister getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClientRegister();
        }
        return INSTANCE;
    }

    private void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new ClientEventsHandler());
        MinecraftForge.EVENT_BUS.register(new VignetteRenderer());
        MinecraftForge.EVENT_BUS.register(new BariRenderHandler());

    }

    private void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(KaizokuEntityTypes.PIRATE.get(), PirateRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(KaizokuEntityTypes.MARINE.get(), MarineRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(KaizokuEntityTypes.MARINE_ADMIRAL.get(), MarineAdmiralRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(KaizokuEntityTypes.BULLET_PROJECTILE.get(), BulletRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(KaizokuEntityTypes.FLYING_BARRIER_WALL.get(), FlyingBarrierRenderer::new);

    }

    private void registerItemRenderers() {

    }

    public void onModInitRegister(IEventBus eventBus) {
        eventBus.register(new ModInitEventsHandler());
    }

    public void onSetupRegister() {
        getInstance().registerEventHandlers();
        getInstance().registerEntityRenderers();
        getInstance().registerItemRenderers();
        KeybindsRegister.register();
        RenderTypeLookup.setRenderLayer(KaizokuBlocks.BARRIER_BLOCK, RenderType.translucent());
    }

    private class ModInitEventsHandler {

        @SubscribeEvent
        public void onModelBake(ModelBakeEvent event) {

        }

        @SubscribeEvent
        public void onModelRegistry(ModelRegistryEvent event) {
            ModelLoader.addSpecialModel(new ResourceLocation("kaizokucraft:other/cube"));
            ModelLoader.addSpecialModel(new ResourceLocation("kaizokucraft:other/cloak"));
            ModelLoader.addSpecialModel(new ResourceLocation("kaizokucraft:entity/marine_admiral"));
        }
    }
}
