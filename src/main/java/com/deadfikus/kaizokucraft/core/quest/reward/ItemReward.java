package com.deadfikus.kaizokucraft.core.quest.reward;

import com.deadfikus.kaizokucraft.exceptions.ClientSideException;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

public class ItemReward extends AbstractQuestReward {

    private ItemStack item;

    public ItemReward(ItemStack item) {
        super();
        this.item = item;
    }

    public ItemReward() {
    }

    @Override
    public RewardEnum getEnum() {
        return RewardEnum.ITEM_REWARD;
    }

    @Override
    public void rewardPlayer(PlayerEntity player) {

        if (player.getCommandSenderWorld().isClientSide) {
            throw new ClientSideException();
        }

        ServerWorld world = (ServerWorld) player.getCommandSenderWorld();

        boolean success = player.inventory.add(item);
        if (!success) {
            world.addFreshEntity(new ItemEntity(player.getCommandSenderWorld(), player.getX(), player.getY(), player.getZ() + 0.1f));
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("item", item.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        item = ItemStack.of(nbt.getCompound("item"));
    }
}
