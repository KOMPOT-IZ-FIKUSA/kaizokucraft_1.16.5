package com.deadfikus.kaizokucraft.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.math.vector.Quaternion;

public class KaizokuClientMathHelper {

    public static void rotateAndTranslateToAnchor(MatrixStack matrixStack,
                                                  float axisX, float axisY, float axisZ, float rad,
                                                  float anchorX, float anchorY, float anchorZ) {
        float dsin = (float) (Math.sin(rad/2f) / Math.sqrt(axisX * axisX + axisY * axisY + axisZ * axisZ));
        Quaternion rotation = new Quaternion(dsin * axisX, dsin * axisY, dsin * axisZ, (float) Math.cos(rad/2f));
        Quaternion anchor = new Quaternion(anchorX, anchorY, anchorZ, 0);
        Quaternion conj = rotation.copy(); conj.conj();
        Quaternion translation = rotation.copy();
        translation.mul(anchor);
        translation.mul(conj);
        matrixStack.translate(-translation.i(), -translation.j(), -translation.k());
        matrixStack.mulPose(rotation);
    }

}
