package com.deadfikus.kaizokucraft.core.mixin;

import com.deadfikus.kaizokucraft.LinearInterpolation;
import com.deadfikus.kaizokucraft.core.KaizokuItems;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.data.Main;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedModel.class)
public class BipedModelMixin<T extends LivingEntity> {
    @Shadow
    public ModelRenderer rightArm;

    @Shadow
    public ModelRenderer head;

    @Shadow
    public ModelRenderer leftArm;

    LinearInterpolation interpolation0 = new LinearInterpolation(3);
    LinearInterpolation interpolation1 = new LinearInterpolation(3);
    LinearInterpolation interpolation2 = new LinearInterpolation(3);
    LinearInterpolation interpolation3 = new LinearInterpolation(3);
    LinearInterpolation interpolation4 = new LinearInterpolation(3);
    LinearInterpolation interpolation5 = new LinearInterpolation(3);

    private void setArmForPistol(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        Item itemInMainHand = entityIn.getMainHandItem().getItem();
        Item itemInOffHand = entityIn.getOffhandItem().getItem();
        float armYRot;
        if (entityIn instanceof PlayerEntity) {
            if (itemInMainHand == KaizokuItems.PISTOL) {
                this.rightArm.xRot = (float) Math.PI * (headPitch / 180 - 0.49f);
                armYRot = (float) Math.PI * netHeadYaw / 192f;
                this.rightArm.yRot = armYRot * 0.97f;
                if (entityIn instanceof PlayerEntity) {
                    if (headPitch > 30) {
                        headPitch = 30 + (headPitch - 30) / 3;
                    }
                    if (headPitch < -40) {
                        headPitch = -40 + (headPitch + 40) / 3;
                    }
                    if (itemInOffHand == Items.AIR) { // пистолет в правой, левая пустая
                        this.leftArm.zRot = 0;
                        this.leftArm.xRot = (float) Math.PI * (headPitch / 180 - 0.48f);
                        this.leftArm.yRot = armYRot * 0.5f + 0.9f;
                    } else if (itemInOffHand == KaizokuItems.PISTOL) { // пистолет в обоих руках
                        this.leftArm.xRot = (float) Math.PI * (headPitch / 180 - 0.49f);
                        armYRot = (float) Math.PI * netHeadYaw / 192f;
                        this.leftArm.yRot = armYRot * 0.97f;
                        this.leftArm.xRot -= Math.abs(armYRot);

                        this.rightArm.xRot = (float) Math.PI * (headPitch / 180 - 0.49f);
                        armYRot = (float) Math.PI * netHeadYaw / 192f;
                        this.rightArm.yRot = armYRot * 0.97f;
                        this.rightArm.xRot -= Math.abs(armYRot);
                    }
                }
            } else if (itemInOffHand == KaizokuItems.PISTOL) {
                this.leftArm.xRot = (float) Math.PI * (headPitch / 180 - 0.49f);
                armYRot = (float) Math.PI * netHeadYaw / 192f;
                this.leftArm.yRot = armYRot * 0.97f;
                if (entityIn instanceof PlayerEntity) {
                    if (headPitch > 30) {
                        headPitch = 30 + (headPitch - 30) / 3;
                    }
                    if (headPitch < -40) {
                        headPitch = -40 + (headPitch + 40) / 3;
                    }
                    if (itemInMainHand == Items.AIR) { // пистолет в левой, правая пустая
                        this.rightArm.zRot = 0;
                        this.rightArm.xRot = (float) Math.PI * (headPitch / 180 - 0.48f);
                        this.rightArm.yRot = armYRot * 0.5f - 0.9f;
                    }
                }
            }
        }
    }

    private void setArmForKnife(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        Item itemInMainHand = entityIn.getMainHandItem().getItem();
        Item itemInOffHand = entityIn.getOffhandItem().getItem();
        boolean hasAttackTarget = false;
        if (itemInMainHand == KaizokuItems.IRON_KNIFE || itemInMainHand == KaizokuItems.IRON_KNIFE) {
            if (entityIn instanceof PlayerEntity) {
                rightArm.xRot += 0.15;
            }
        } else if (itemInOffHand == KaizokuItems.IRON_KNIFE || itemInOffHand == KaizokuItems.IRON_KNIFE) {
            if (entityIn instanceof PlayerEntity) {
                rightArm.xRot += 0.15;
            }
        }
    }
}
