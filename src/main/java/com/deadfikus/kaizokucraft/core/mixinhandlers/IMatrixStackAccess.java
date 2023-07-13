package com.deadfikus.kaizokucraft.core.mixinhandlers;

import com.mojang.blaze3d.matrix.MatrixStack;

import java.util.Deque;

public interface IMatrixStackAccess {
    MatrixStack.Entry getLastPopped();
    void reAddLastPopped();
}
