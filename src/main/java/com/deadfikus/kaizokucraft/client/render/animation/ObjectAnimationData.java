package com.deadfikus.kaizokucraft.client.render.animation;

import com.deadfikus.kaizokucraft.exceptions.InvalidAnimationResourceException;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

public final class ObjectAnimationData {

    private static final HashMap<ResourceLocation, ObjectAnimationData> cache = new HashMap<>();

    private float[][][] values;

    public static ObjectAnimationData get(ResourceLocation animationLocation) {
        try {
            if (cache.containsKey(animationLocation)) {
                return cache.get(animationLocation);
            } else {
                ObjectAnimationData res = new ObjectAnimationData();
                InputStream inputStream = Minecraft.getInstance().getResourceManager().getResource(animationLocation).getInputStream();
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                int verticesCount = (bytes[0] & 0xff) * 256 + (bytes[1] & 0xff);
                if (((bytes.length - 2) / 4) % verticesCount != 0) {
                    Minecraft.crash(new CrashReport("Kaizokucraft animation loading error",
                            new InvalidAnimationResourceException("Cannot load animation for " + verticesCount + " vertices from " + bytes.length + " bytes")));
                }
                int framesCount = (bytes.length - 2) / verticesCount / 3 / 4;
                res.values = new float[framesCount][verticesCount][3];
                for (int frame = 0; frame < framesCount; frame++) {
                    for (int vertex = 0; vertex < verticesCount; vertex++) {
                        for (int d = 0; d < 3; d++) {
                            res.values[frame][vertex][d] = byteArrayToFloat(bytes, frame * verticesCount * 3 * 4 + vertex * 3 * 4 + d * 4 + 2);
                        }
                    }
                }
                cache.put(animationLocation, res);
                return res;
            }
        } catch (IOException exception) {
            Minecraft.crash(new CrashReport("error... wtf", exception));
            return null;
        }
    }

    static float byteArrayToFloat(byte[] bytes, int start) {
        int intBits = bytes[start + 3] << 24 | (bytes[start + 2] & 0xFF) << 16 | (bytes[start + 1] & 0xFF) << 8 | (bytes[start + 0] & 0xFF);
        return Float.intBitsToFloat(intBits);
    }

    public float[][] getFrame(int frame) {
        return values[frame];
    }
}

