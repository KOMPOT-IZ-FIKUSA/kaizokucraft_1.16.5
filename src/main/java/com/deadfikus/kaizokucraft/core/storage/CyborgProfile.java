package com.deadfikus.kaizokucraft.core.storage;

import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import com.deadfikus.kaizokucraft.core.ability.cyborg.CyborgLaser;
import com.deadfikus.kaizokucraft.core.ability.cyborg.NVEye;
import com.deadfikus.kaizokucraft.core.ability.cyborg.XRayEye;
import com.deadfikus.kaizokucraft.core.storage.cap.player.PlayerCap;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class CyborgProfile implements INBTSerializable<CompoundNBT> {
    private boolean hasXRayEye = false;
    private boolean hasNVEye = false;
    private boolean hasLaser = false;
    private boolean hasLongerArm = false;
    private boolean hasHookArm = false;
    private boolean hasKairosekiGlove = false;
    private boolean hasFridge = false;
    private boolean hasIronSkin = false;
    private boolean hasRocketFeet = false;
    private double cola = 0;
    public boolean isDirty = false;

    public boolean hasXRayEye() {return this.hasXRayEye;}
    public boolean hasNVEye() {return this.hasNVEye;}
    public boolean hasLaser() {return this.hasLaser;}
    public boolean hasLongerArm() {return this.hasLongerArm;}
    public boolean hasHookArm() {return this.hasHookArm;}
    public boolean hasKairosekiGlove() {return this.hasKairosekiGlove;}
    public boolean hasFridge() {return this.hasFridge;}
    public boolean hasIronSkin() {return this.hasIronSkin;}
    public boolean hasRocketFeet() {return this.hasRocketFeet;}
    public double getCola() {return this.cola;}
    public void setCola(double cola) {
        this.cola = Math.min(1, Math.max(0, cola));
        isDirty = true;
    }
    public void decreaseCola(double value) {
        this.cola = Math.min(1, Math.max(0, this.cola-value));
        isDirty = true;
    }
    public void increaseCola(double value) {
        this.cola = Math.min(1, Math.max(0, this.cola+value));
        isDirty = true;
    }

    public void setFridge() {
        this.hasFridge = true;
        this.isDirty = true;
    }

    public void setXRayEye(PlayerCap owner) {
        this.hasXRayEye = true;
        owner.addAbility(new XRayEye());
        this.isDirty = true;
    }
    public void removeXRayEye(PlayerCap owner) {
        this.hasXRayEye = false;
        owner.removeAbility(AbilityEnum.XRAY_EYE);
        this.isDirty = true;
    }

    public void setNVEye(PlayerCap owner) {
        this.hasNVEye = true;
        owner.addAbility(new NVEye());
        this.isDirty = true;
    }
    public void removeNVEye(PlayerCap owner) {
        this.hasNVEye = false;
        owner.removeAbility(AbilityEnum.NV_EYE);
        this.isDirty = true;
    }

    public void setLaser(PlayerCap owner) {
        this.hasLaser = true;
        owner.addAbility(new CyborgLaser());
        this.isDirty = true;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("hasXRayEye", hasXRayEye);
        nbt.putBoolean("hasNVEye", hasNVEye);
        nbt.putBoolean("hasLaser", hasLaser);
        nbt.putBoolean("hasLongerArm", hasLongerArm);
        nbt.putBoolean("hasHookArm", hasHookArm);
        nbt.putBoolean("hasKairosekiGlove", hasKairosekiGlove);
        nbt.putBoolean("hasFridge", hasFridge);
        nbt.putBoolean("hasIronSkin", hasIronSkin);
        nbt.putBoolean("hasRocketFeet", hasRocketFeet);
        nbt.putDouble("cola", cola);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("hasXRayEye")) hasXRayEye = nbt.getBoolean("hasXRayEye");
        if (nbt.contains("hasNVEye")) hasNVEye = nbt.getBoolean("hasNVEye");
        if (nbt.contains("hasLaser")) hasXRayEye = nbt.getBoolean("hasLaser");
        if (nbt.contains("hasLongerArm")) hasXRayEye = nbt.getBoolean("hasLongerArm");
        if (nbt.contains("hasHookArm")) hasXRayEye = nbt.getBoolean("hasHookArm");
        if (nbt.contains("hasKairosekiGlove")) hasXRayEye = nbt.getBoolean("hasKairosekiGlove");
        if (nbt.contains("hasFridge")) hasXRayEye = nbt.getBoolean("hasFridge");
        if (nbt.contains("hasIronSkin")) hasXRayEye = nbt.getBoolean("hasIronSkin");
        if (nbt.contains("hasRocketFeet")) hasXRayEye = nbt.getBoolean("hasRocketFeet");
        if (nbt.contains("cola")) cola = nbt.getDouble("cola");
    }

    public static CyborgProfile deserializeNBT_(CompoundNBT nbt) {
        CyborgProfile instance = new CyborgProfile();
        instance.deserializeNBT(nbt);
        return instance;
    }
}
