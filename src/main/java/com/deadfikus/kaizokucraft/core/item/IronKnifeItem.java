package com.deadfikus.kaizokucraft.core.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;

public class IronKnifeItem extends SwordItem  {


    public IronKnifeItem(IItemTier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }
    public IronKnifeItem(Properties properties) {
        super(ItemTier.IRON, 6, 1, properties);
    }
}
