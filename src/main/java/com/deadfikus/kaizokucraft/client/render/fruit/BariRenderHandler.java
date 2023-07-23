package com.deadfikus.kaizokucraft.client.render.fruit;

import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.core.ability.bari.BarrierFist;
import com.deadfikus.kaizokucraft.core.ability.base.Ability;
import com.deadfikus.kaizokucraft.core.mixinhandlers.IAbstractClientPlayerAccess;
import com.deadfikus.kaizokucraft.core.mixinhandlers.IMatrixStackAccess;
import com.deadfikus.kaizokucraft.core.mixinhandlers.IPlayerRendererAccess;
import com.deadfikus.kaizokucraft.core.storage.EntityAbilityGetter;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.*;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BariRenderHandler {


    @SubscribeEvent
    public void onRenderFirstPersonHand(RenderArmEvent event) {
        if(event.getPlayer() == null){
            return;
        }
        boolean isExist = false;
        List<Ability> abl = EntityAbilityGetter.getHotbarAbilities(event.getPlayer());
        for (Ability it: abl) {
            if(it instanceof BarrierFist){
                if(it.getCurrentPhase() == ModEnums.AbilityPhase.WORKING){
                    isExist = true;
                }
            }
        }
        if(!isExist) return;

        EntityRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(event.getPlayer());

        if(renderer instanceof PlayerRenderer){
            ((IAbstractClientPlayerAccess)event.getPlayer()).setReturnBarrierTexture(true);
            PlayerModel model = ((PlayerRenderer) renderer).getModel();
            boolean isRight = event.getPlayer().getMainArm() == HandSide.RIGHT;
            ModelRenderer arm =  isRight ? model.rightArm : model.leftArm;
            ModelRenderer sleeve = isRight ? model.rightSleeve : model.leftSleeve;

            ((IPlayerRendererAccess)renderer).renderHand_(event.getPoseStack(), event.getMultiBufferSource(), 0xF00000, event.getPlayer(), arm, sleeve);
            ((IAbstractClientPlayerAccess)event.getPlayer()).setReturnBarrierTexture(false);
        }

    }

    @SubscribeEvent
    public void onRenderLivingThirdPerson(RenderLivingEvent.Post<LivingEntity, BipedModel<LivingEntity>> event) {

        if (event.getRenderer().getModel() instanceof BipedModel) {
            if(event.getEntity() == null){
                return;
            }
            boolean isExist = false;
            List<Ability> abl = EntityAbilityGetter.getHotbarAbilities(event.getEntity());
            for (Ability it: abl) {
                if(it instanceof BarrierFist){
                    if(it.getCurrentPhase() == ModEnums.AbilityPhase.WORKING){
                        isExist = true;
                    }
                }
            }
            if(!isExist){
                return;
            }
            HandSide side = event.getEntity().getMainArm();
            BipedModel<LivingEntity> model = event.getRenderer().getModel();
            RenderType renderType = model.renderType(new ResourceLocation("kaizokucraft:textures/entity/barrier_person.png"));
            ModelRenderer arm = model.leftArm;
            float trans = -0.02f;
            if(side == HandSide.RIGHT) {
                arm = model.rightArm;
                trans = 0.02f;
            }
            MatrixStack matrixStack = event.getMatrixStack();
            ((IMatrixStackAccess) event.getMatrixStack()).reAddLastPopped();
            matrixStack.scale(1.06f, 1.06f, 1.06f);
            matrixStack.translate(trans, 0, 0);


            arm.render(event.getMatrixStack(), event.getBuffers().getBuffer(renderType), event.getLight(), 0xFFFFFF);
            matrixStack.popPose();
        }
    }
}
