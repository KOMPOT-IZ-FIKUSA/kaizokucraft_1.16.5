package com.deadfikus.kaizokucraft.core.item;

import net.minecraft.item.Item;

public class BulletItemBase extends Item {
    public final boolean isKairoseki;
    public final boolean isFire;

    public BulletItemBase(Properties properties, boolean isKairoseki, boolean isFire) {
        super(properties);
        this.isKairoseki = isKairoseki;
        this.isFire = isFire;
    }
}
