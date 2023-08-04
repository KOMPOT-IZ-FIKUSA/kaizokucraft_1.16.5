package com.deadfikus.kaizokucraft.core.entity;

import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.core.entity.collision.LivingCollisionEntity;
import com.deadfikus.kaizokucraft.core.entity.mob.MarineAdmiralEntity;
import com.deadfikus.kaizokucraft.core.entity.mob.MarineEntity;
import com.deadfikus.kaizokucraft.core.entity.mob.PirateEntity;
import com.deadfikus.kaizokucraft.core.entity.projectile.BulletEntity;
import com.deadfikus.kaizokucraft.core.entity.projectile.FlyingBarrierEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class KaizokuEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            ModMain.MODID);

    public static final RegistryObject<EntityType<PirateEntity>> PIRATE = ENTITY_TYPES
            .register("pirate",
                    () -> EntityType.Builder.of(PirateEntity::new, EntityClassification.AMBIENT)
                            .build(new ResourceLocation(ModMain.MODID, "pirate").toString()));

    public static final RegistryObject<EntityType<MarineEntity>> MARINE = ENTITY_TYPES
            .register("marine",
                    () -> EntityType.Builder.of(MarineEntity::new, EntityClassification.AMBIENT)
                            .build(new ResourceLocation(ModMain.MODID, "marine").toString()));

    public static final RegistryObject<EntityType<MarineAdmiralEntity>> MARINE_ADMIRAL = ENTITY_TYPES
            .register("marine_admiral",
                    () -> EntityType.Builder.of(MarineAdmiralEntity::new, EntityClassification.AMBIENT)
                            .build(new ResourceLocation(ModMain.MODID, "marine_admiral").toString()));

    public static final RegistryObject<EntityType<BulletEntity>> BULLET_PROJECTILE = ENTITY_TYPES
            .register("bullet",
                    () -> EntityType.Builder.of(BulletEntity::new, EntityClassification.MISC)
                            .sized(0.2f, 0.2f)
                            .build(new ResourceLocation(ModMain.MODID, "bullet").toString()));

    public static final RegistryObject<EntityType<FlyingBarrierEntity>> FLYING_BARRIER_WALL = ENTITY_TYPES
            .register("flying_barrier_wall",
                    () -> EntityType.Builder.of(FlyingBarrierEntity::new, EntityClassification.MISC)
                            .build(new ResourceLocation(ModMain.MODID, "flying_barrier_wall").toString()));

    public static final RegistryObject<EntityType<LivingCollisionEntity>> LIVING_COLLISION = ENTITY_TYPES
            .register("living_collision",
                    () -> EntityType.Builder.of(LivingCollisionEntity::new, EntityClassification.MISC)
                            .build(new ResourceLocation(ModMain.MODID, "living_collision").toString()));


}
