package com.deadfikus.kaizokucraft.client.event;

import com.deadfikus.kaizokucraft.client.KeybindsRegister;
import com.deadfikus.kaizokucraft.client.gui.QuestMenuScreen;
import com.deadfikus.kaizokucraft.client.render.KaizokuMathHelper;
import com.deadfikus.kaizokucraft.client.render.animation.BakedQuadUtils;
import com.deadfikus.kaizokucraft.client.render.mob.MarineAdmiralModel;
import com.deadfikus.kaizokucraft.client.render.mob.MarineAdmiralRenderer;
import com.deadfikus.kaizokucraft.core.KaizokuItems;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ClientEventsHandler {
    long abilityUseClock = System.currentTimeMillis();

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (abilityUseClock + 100 < System.currentTimeMillis()) {
            for (int i = 0; i < 5; i++) {
                if (KeybindsRegister.abilityUses[i].getPressed()) {
                    PlayerCap playerCap = PlayerCap.get(Minecraft.getInstance().player);
                    playerCap.tryToggleAbility(i, Minecraft.getInstance().player);
                    abilityUseClock = System.currentTimeMillis();
                    break;
                }
            }
        }

        if (KeybindsRegister.questsMenu.getPressed()) {
            Minecraft.getInstance().setScreen(new QuestMenuScreen());
        }

    }

    List<BakedQuad> quads;

    @SubscribeEvent
    public void onRenderPost(RenderLivingEvent.Post event) {

        //if (quads == null) {
        //    quads = BakedQuadUtils.getFromBakedModel(Minecraft.getInstance().getModelManager().getModel(new ResourceLocation("kaizokucraft:entity/marine_admiral")));
        //}
//
        //if (event.getRenderer() instanceof PlayerRenderer) {
        //    IVertexBuilder vertexBuilder = event.getBuffers().getBuffer(RenderType.entityCutoutNoCull(AtlasTexture.LOCATION_BLOCKS));
        //    MatrixStack matrixStack = event.getMatrixStack();
        //    matrixStack.pushPose();
        //    matrixStack.scale(0.2f, 0.2f, 0.2f);
        //    for (BakedQuad quad : quads) {
        //        vertexBuilder.addVertexData(matrixStack.last(), quad, 1, 1, 1, 1, event.getLight(), OverlayTexture.NO_OVERLAY);
        //    }
        //    matrixStack.popPose();
        //}
    }

}
