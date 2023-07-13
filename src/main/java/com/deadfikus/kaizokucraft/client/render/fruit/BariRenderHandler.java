package com.deadfikus.kaizokucraft.client.render.fruit;

import com.deadfikus.kaizokucraft.core.mixinhandlers.IMatrixStackAccess;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.client.renderer.entity.model.VillagerModel;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Deque;

public class BariRenderHandler {


    @SubscribeEvent
    public void onRenderFirstPersonHand(RenderHandEvent event) {
        if (event.getHand() == Hand.MAIN_HAND) {
        }
    }

    @SubscribeEvent
    public void onRenderLivingThirdPerson(RenderLivingEvent.Post<LivingEntity, BipedModel<LivingEntity>> event) {
        if (event.getRenderer().getModel() instanceof BipedModel) {
            BipedModel<LivingEntity> model = event.getRenderer().getModel();
            RenderType renderType = model.renderType(new ResourceLocation("kaizokucraft:textures/entity/barrier_person.png"));

            MatrixStack matrixStack = event.getMatrixStack();
            ((IMatrixStackAccess) event.getMatrixStack()).reAddLastPopped();
            matrixStack.scale(1.06f, 1.06f, 1.06f);
            matrixStack.translate(0.02, 0, 0);
            model.rightArm.render(event.getMatrixStack(), event.getBuffers().getBuffer(renderType), event.getLight(), 0xFFFFFF);
            matrixStack.popPose();
        }
    }
}
