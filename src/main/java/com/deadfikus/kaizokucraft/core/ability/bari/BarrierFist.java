package com.deadfikus.kaizokucraft.core.ability.bari;

import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.client.event.RenderCustomVignetteEvent;
import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import com.deadfikus.kaizokucraft.core.ability.base.LeverAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.Explosion;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class BarrierFist extends LeverAbility {
    public BarrierFist() {
        super(AbilityEnum.BARRIER_FIST);
    }

    @Override
    public void setAvailable(LivingEntity user) {

    }

    @Override
    protected void handleWorkStarted(LivingEntity user) {

    }

    @Override
    protected void handleWorkTick(LivingEntity user) {
        if (!user.getMainHandItem().isEmpty()) {
            forceStop(user);
        }
    }

    @Override
    protected void handleWorkEnded(LivingEntity user) {

    }

    @Override
    protected int calculateCooldown(LivingEntity user) {
        return 200;
    }

    @Override
    public boolean turnOnAdditionalCondition(LivingEntity user) {
        return user.getMainHandItem().isEmpty();
    }

    @Override
    public boolean turnOffAdditionalCondition(LivingEntity user) {
        return true;
    }

    @Override
    public void onUserDealsDamage(LivingDamageEvent event, LivingEntity user) {
        if (event.isCanceled()) return;
        if (currentPhase != ModEnums.AbilityPhase.WORKING) return;
        if (!tryTurnOff(user)) return;
        event.setAmount(event.getAmount() * 10);
    }

    @Override
    public void forceStop(LivingEntity user) {
        super.forceStop(user);
    }


    private float opacityMovingAverage = 0f;
    @Override
    public void onRenderGameOverlay(RenderGameOverlayEvent event, LivingEntity user) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.VIGNETTE) return;
        float opacity = currentPhase == ModEnums.AbilityPhase.WORKING ? 1f : 0f;
        opacityMovingAverage = opacityMovingAverage * 0.95f + opacity * 0.05f;
        MinecraftForge.EVENT_BUS.post(new RenderCustomVignetteEvent(RenderCustomVignetteEvent.VignetteType.BARRIER_FIST, opacityMovingAverage, event.getMatrixStack()));
    }

    @Override
    public  void onClickBlock(PlayerInteractEvent.LeftClickBlock event){
        if (event.isCanceled()) return;
        if (currentPhase != ModEnums.AbilityPhase.WORKING) return;
        if (!tryTurnOff(event.getEntityLiving())) return;

        event.getEntityLiving().getCommandSenderWorld().explode(event.getEntityLiving(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), 1.5F, false, Explosion.Mode.DESTROY);
    }


}
