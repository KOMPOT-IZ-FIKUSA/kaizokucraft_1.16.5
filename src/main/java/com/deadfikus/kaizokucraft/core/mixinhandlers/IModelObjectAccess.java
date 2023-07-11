package com.deadfikus.kaizokucraft.core.mixinhandlers;

import net.minecraftforge.client.model.obj.OBJModel;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.List;

public interface IModelObjectAccess {

    List getMeshes();
}
