package com.deadfikus.kaizokucraft.core.quest.list;

import com.deadfikus.kaizokucraft.core.ability.AbilityEnum;
import com.deadfikus.kaizokucraft.core.quest.QuestBase;
import com.deadfikus.kaizokucraft.core.quest.reward.AbilityReward;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.living.LivingEvent;

public class GeppoQuest extends QuestBase implements INBTSerializable<CompoundNBT> {
    long jumpClock = 0;
    double prevX;
    double prevY;
    double prevZ;
    boolean playerCloned = true;

    public GeppoQuest() {
        super(
                "quest.kaizokucraft.geppo.name",
                new AbilityReward(AbilityEnum.BARRIER_WALL, new ResourceLocation("kaizokucraft:textures/icon/coin.png")));
    }


    @Override
    public void onOwnerLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        double x1 = entity.position().x;
        double y1 = entity.position().y;
        double z1 = entity.position().z;

        if (playerCloned) {
            prevX = x1;
            prevY = y1;
            prevZ = z1;
            playerCloned = false;
        } else {
            if (jumpClock > System.currentTimeMillis() - 800) {
                double dist = Math.sqrt((prevX - x1) * (prevX - x1) + (prevY - y1) * (prevY - y1) + (prevZ - z1) * (prevZ - z1));
                this.setProgress(this.getProgress() + (float)dist / 20000D);
            }
        }
        jumpClock = System.currentTimeMillis();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("super", super.serializeNBT());
        nbt.putLong("jumpClock", jumpClock);
        nbt.putDouble("prevX", prevX);
        nbt.putDouble("prevY", prevY);
        nbt.putDouble("prevZ", prevZ);
        nbt.putBoolean("playerCloned", playerCloned);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt.getCompound("super"));
        if (nbt.contains("jumpClock"))
            jumpClock = nbt.getLong("jumpClock");
        if (nbt.contains("prevX"))
            prevX = nbt.getInt("prevX");
        if (nbt.contains("prevY"))
            prevY = nbt.getInt("prevY");
        if (nbt.contains("prevZ"))
            prevZ = nbt.getInt("prevZ");
        if (nbt.contains("playerCloned"))
            playerCloned = nbt.getBoolean("playerCloned");
    }
}
