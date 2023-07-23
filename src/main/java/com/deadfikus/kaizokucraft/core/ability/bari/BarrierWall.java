package com.deadfikus.kaizokucraft.core.ability.bari;

import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.core.KaizokuBlocks;
import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import com.deadfikus.kaizokucraft.core.ability.base.LeverAbility;
import com.deadfikus.kaizokucraft.exceptions.ClientSideException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;
import java.util.function.Predicate;

import static java.lang.Boolean.TRUE;
import static java.lang.Math.abs;
import static java.lang.Math.random;

public class BarrierWall extends LeverAbility implements INBTSerializable<CompoundNBT> {
    private final ArrayList<BlockPos> actBarriers;
    private int lastItemRight = -1;
    private int lastItemLeft = -1;
    public BarrierWall() {
        super(AbilityEnum.BARRIER_WALL);
        actBarriers = new ArrayList<>();
    }

    @Override
    public void setAvailable(LivingEntity user) {

    }

    @Override
    protected void handleWorkStarted(LivingEntity user) {

        World world = user.getCommandSenderWorld();
        Vector3d vec = user.getLookAngle();
        if(world.isClientSide)
            return;
        double b = Math.atan2(vec.z, vec.x);
        double a = Math.asin(vec.y);
        double r = 7;
        HashSet<BlockPos> set = new HashSet<>();
        double sigma = Math.PI/5, eps = 0.2 / r;
        for(double i = a - sigma/2; i < a + sigma/2; i += eps){
            for(double j = b - sigma; j < b + sigma; j += eps){
                double y = user.getEyeY() + Math.sin(i) * r;
                double x = user.getX() + Math.cos(i) * Math.cos(j) * r;
                double z = user.getZ() + Math.cos(i) * Math.sin(j) * r;
                BlockPos cur = new BlockPos(Math.round(x), Math.round(y), Math.round(z));
                set.add(cur);
            }
        }

        for (BlockPos pos: set) {
            if(!world.getBlockState(pos).isAir())
                continue;
            world.setBlockAndUpdate(pos, KaizokuBlocks.BARRIER_BLOCK.defaultBlockState());

            actBarriers.add(pos);
        }
        lastItemRight = Item.getId(user.getItemInHand(Hand.MAIN_HAND).getItem());
        lastItemLeft = Item.getId(user.getItemInHand(Hand.OFF_HAND).getItem());
    }

    @Override
    protected void handleWorkTick(LivingEntity user) {
        if(Item.getId(user.getItemInHand(Hand.MAIN_HAND).getItem()) != lastItemRight || lastItemLeft != Item.getId(user.getItemInHand(Hand.OFF_HAND).getItem())){
            forceStop(user);
        }
    }

    @Override
    protected void handleWorkEnded(LivingEntity user) {
        World world = user.getCommandSenderWorld();
        for (BlockPos pos : actBarriers)
            world.destroyBlock(pos, false);
        actBarriers.clear();
    }

    @Override
    public void onUserInteract(PlayerInteractEvent event) {
        if(getCurrentPhase() == ModEnums.AbilityPhase.WORKING)
            forceStop(event.getEntityLiving());

    }



    @Override
    protected int calculateCooldown(LivingEntity user) {
        return 120;
    }

    @Override
    public boolean turnOnAdditionalCondition(LivingEntity user) {
        return true;
    }

    @Override
    public boolean turnOffAdditionalCondition(LivingEntity user) {
        return true;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("super", super.serializeNBT());
        nbt.putInt("right", lastItemRight);
        nbt.putInt("left", lastItemLeft);
        for (int i = 0; i < actBarriers.size(); i++) {
            nbt.putInt("actBarriersX" + i, actBarriers.get(i).getX());
            nbt.putInt("actBarriersY" + i, actBarriers.get(i).getY());
            nbt.putInt("actBarriersZ" + i, actBarriers.get(i).getZ());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt.getCompound("super"));
        int i = 0;
        actBarriers.clear();
        lastItemLeft = nbt.getInt("left");
        lastItemRight = nbt.getInt("right");
        while (nbt.contains("actBarriersX" + i)) {
            int x, y, z;
            x = nbt.getInt("actBarriersX" + i);
            y = nbt.getInt("actBarriersY" + i);
            z = nbt.getInt("actBarriersZ" + i);
            actBarriers.add(new BlockPos(x, y, z));
            i++;
        }
    }

    @Override
    public void onClickBlock(PlayerInteractEvent.LeftClickBlock event) {

    }


    @Override
    public void forceStop(LivingEntity user) {
        super.forceStop(user);
        World world = user.getCommandSenderWorld();
        for (BlockPos pos : actBarriers)
            world.destroyBlock(pos, false);
        actBarriers.clear();
    }
}
