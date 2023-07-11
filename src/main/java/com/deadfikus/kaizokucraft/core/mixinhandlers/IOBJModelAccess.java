package com.deadfikus.kaizokucraft.core.mixinhandlers;

import com.google.common.collect.Lists;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraftforge.client.model.obj.OBJModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;

public interface IOBJModelAccess {
    public List<Vector3f> getPositions();
    public List<Vector2f> getTexCoords();
    public List<Vector3f> getNormals();
    public List<Vector4f> getColors();
}
