package com.deadfikus.kaizokucraft.core.item;

import com.deadfikus.kaizokucraft.client.render.item.MarineCapModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.data.ItemModelProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class MarineCapItem extends ArmorItem {
    public MarineCapItem(Properties properties) {
        super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, properties);
    }

    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return (A) MarineCapModel.get();
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "textures/atlas/blocks.png";
    }


}
