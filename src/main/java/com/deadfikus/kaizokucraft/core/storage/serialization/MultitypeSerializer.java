package com.deadfikus.kaizokucraft.core.storage.serialization;


import com.deadfikus.kaizokucraft.exceptions.DeserializationException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public class MultitypeSerializer {


    public static <Abstract extends INBTSerializable<CompoundNBT>> Abstract deserialize(CompoundNBT nbt, List<Class<? extends Abstract>> classes) {
        Class<? extends Abstract> clazz = classes.get(nbt.getInt("classId"));
        Abstract object = null;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new DeserializationException(e.getMessage());
        }
        object.deserializeNBT(nbt.getCompound("data"));
        return object;
    }

    public static <Abstract extends INBTSerializable<CompoundNBT>> CompoundNBT serialize(Abstract object, List<Class<? extends Abstract>> classes) {
        CompoundNBT result = new CompoundNBT();
        result.putInt("classId", getClassId(object, classes));
        result.put("data", object.serializeNBT());
        return result;
    }

    public static <Abstract extends INBTSerializable<CompoundNBT>> int getClassId(Abstract object, List<Class<? extends Abstract>> classes) {
        int i = 0;
        for (Class<? extends Abstract> clazz1 : classes) {
            if (object.getClass().equals(clazz1)) {
                return i;
            }
            i++;
        }
        throw new IllegalArgumentException(object.toString());
    }

}
