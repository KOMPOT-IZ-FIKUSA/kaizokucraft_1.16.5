package com.deadfikus.kaizokucraft.core;

import com.deadfikus.kaizokucraft.core.item.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class KaizokuItems {
    public static final Item BARRIER_BLOCK = new BlockItem(KaizokuBlocks.BARRIER_BLOCK, new Item.Properties()).setRegistryName("barrierblock");
    public static final Item PISTOL = new PistolItem(new Item.Properties()).setRegistryName("pistol");
    public static final Item IRON_BULLET = new IronBulletItem(new Item.Properties()).setRegistryName("iron_bullet");
    public static final Item KAIROSEKI_BULLET = new KairosekiBulletItem(new Item.Properties()).setRegistryName("kairoseki_bullet");
    public static final Item FIRE_BULLET = new FireBulletItem(new Item.Properties()).setRegistryName("fire_bullet");
    public static final Item FIRE_KAIROSEKI_BULLET = new FireKairosekiBulletItem(new Item.Properties()).setRegistryName("firekairoseki_bullet");
    public static final Item IRON_KNIFE = new IronKnifeItem(new Item.Properties()).setRegistryName("iron_knife");

    public static final MarineCapItem MARINE_CAP = (MarineCapItem) new MarineCapItem(new Item.Properties()).setRegistryName("marine_cap");
}
