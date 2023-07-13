package com.deadfikus.kaizokucraft.core.ability;

import com.deadfikus.kaizokucraft.ModEnums;
import com.deadfikus.kaizokucraft.ModEnums.AbilityGroup;
import com.deadfikus.kaizokucraft.ModEnums.Fruit;
import com.deadfikus.kaizokucraft.ModEnums.AbilityType;
import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.core.ability.bari.BarrierFist;
import com.deadfikus.kaizokucraft.core.ability.bari.BarrierWall;
import com.deadfikus.kaizokucraft.core.ability.base.Ability;
import com.deadfikus.kaizokucraft.core.ability.cyborg.CyborgLaser;
import com.deadfikus.kaizokucraft.core.ability.cyborg.NVEye;
import com.deadfikus.kaizokucraft.core.ability.cyborg.XRayEye;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;

public enum AbilityEnum {
    BARRIER_WALL(BarrierWall.class, "barrier_wall", AbilityGroup.FRUIT, Fruit.BARI, AbilityType.BUILDING),
    BARRIER_FIST(BarrierFist.class, "barrier_fist", AbilityGroup.FRUIT, Fruit.BARI, AbilityType.HIT),
    XRAY_EYE(XRayEye.class, "xray_eye", AbilityGroup.CYBORG, Fruit.NONE, AbilityType.BUF),
    NV_EYE(NVEye.class, "nv_eye", AbilityGroup.CYBORG, Fruit.NONE, AbilityType.BUF),
    CYBORG_LASER(CyborgLaser.class, "cyborg_laser", AbilityGroup.CYBORG, Fruit.NONE, AbilityType.PROJECTILE);
    public final Class<? extends Ability> class_;
    public final String name;
    public final String description;
    public final String ebanName;
    public final ModEnums.AbilityType type;
    public final ModEnums.AbilityGroup group;
    public final ModEnums.Fruit fruit;
    public static final AbilityEnum[] values = AbilityEnum.values();

    AbilityEnum(Class<? extends Ability> class_, String name, ModEnums.AbilityGroup group, ModEnums.Fruit fruit, ModEnums.AbilityType type) {
        this.class_ = class_;
        this.type = type;
        this.group = group;
        this.fruit = fruit;
        this.name = "ability.kaizokucraft." + name + ".name";
        this.description = "ability.kaizokucraft." + name + ".description";
        this.ebanName = "ability.kaizokucraft." + name + ".strangename";
    }

    public short getI() {
        for (short i = 0; i < values.length; i++) {
            if (values[i] == this) {
                return i;
            }
        }
        return -1;
    }

    public static AbilityEnum get(Class<? extends Ability> class_) {
        for (AbilityEnum out : values) {
            if (out.class_.equals(class_)) {
                return out;
            }
        }
        ModMain.logError("Unable to get AbilityClass for " + class_.getName());
        throw new IllegalArgumentException("Unable to get AbilityClass for " + class_.getName());
    }


    public Ability initAbility() throws InstantiationException, IllegalAccessException {
        Ability ability = class_.newInstance();
        return ability;
    }

    public static ArrayList<Class<? extends Ability>> getClasses() {
        ArrayList<Class<? extends Ability>> res = new ArrayList<>();
        for (AbilityEnum abl : values()) {
            res.add(abl.class_);
        }
        return res;
    }
}
