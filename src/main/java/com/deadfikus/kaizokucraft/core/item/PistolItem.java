package com.deadfikus.kaizokucraft.core.item;

import com.deadfikus.kaizokucraft.core.entity.projectile.BulletEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class PistolItem extends TieredItem {

    public PistolItem(Properties properties) {
        super(ItemTier.IRON, properties.stacksTo(1));

    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack instance = super.getDefaultInstance();
        CompoundNBT tag = instance.getTag() != null ? instance.getTag() : new CompoundNBT();
        tag.putInt("cooldown", 0);
        instance.setTag(tag);
        return instance;
    }

    @Override
    public void inventoryTick(ItemStack pistol, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(pistol, worldIn, entityIn, itemSlot, isSelected);
        CompoundNBT tag = pistol.getTag() != null ? pistol.getTag() : new CompoundNBT();
        if (tag.contains("cooldown")) {
            int cooldown = tag.getInt("cooldown");
            tag.putInt("cooldown", Math.max(0, cooldown - 1));
        } else {
            tag.putInt("cooldown", 20);
        }
        pistol.setTag(tag);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack pistol = playerIn.getItemInHand(handIn);
        CompoundNBT tag = pistol.getTag();
        if (tag == null) {
            tag = new CompoundNBT();
            tag.putInt("cooldown", 20);
            pistol.setTag(tag);
            return ActionResult.fail(pistol);
        }
        if (!tag.contains("cooldown")) {
            return ActionResult.fail(pistol);
        }
        if (tag.getInt("cooldown") > 0) {
            return ActionResult.fail(pistol);
        }
        if (!worldIn.isClientSide) {
            for (int slotIndex = 0; slotIndex < 36; slotIndex++) {
                ItemStack stackInSlot = playerIn.inventory.getItem(slotIndex);
                int punchLvl = 0;
                if (stackInSlot.getItem() instanceof BulletItemBase) {
                    for (INBT inbt : pistol.getEnchantmentTags()) {
                        if (inbt instanceof CompoundNBT) {
                            CompoundNBT nbt = (CompoundNBT) inbt;
                            if (nbt.contains("id")) {
                                String id = nbt.getString("id");
                                if (id.equals("minecraft:punch")) {
                                    punchLvl = nbt.getInt("lvl");
                                }
                            }
                        }
                    }
                    BulletItemBase item = (BulletItemBase) stackInSlot.getItem();
                    System.out.println(item.isFire);
                    Vector3d bulletMotion = playerIn.getLookAngle().scale(0.01);
                    BulletEntity bullet = BulletEntity.init(
                            worldIn,
                            item.isKairoseki,
                            item.isFire,
                            1 + punchLvl,
                            playerIn,
                            playerIn.getX(), playerIn.getEyeY() - 0.1d, playerIn.getZ(),
                            bulletMotion.x, bulletMotion.y, bulletMotion.z
                    );
                    worldIn.addFreshEntity(bullet);
                    if (!playerIn.isCreative()) {
                        stackInSlot.shrink(1);
                        pistol.setDamageValue(pistol.getDamageValue() - 1);
                    }
                    pistol.getTag().putInt("cooldown", 15);
                    return ActionResult.success(pistol);
                }
            }
        }
        return ActionResult.fail(pistol);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

}
