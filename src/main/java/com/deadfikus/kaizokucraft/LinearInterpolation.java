package com.deadfikus.kaizokucraft;

import java.util.Arrays;

public class LinearInterpolation {
    float[] values;
    public boolean doCalculate = true;

    public LinearInterpolation(int ticks) {
        values = new float[ticks];
    }

    public float tick(float target) {
        if (!doCalculate) {return target;}
        float s = target;
        for (int i = values.length-1; i > 0; i--) {
            values[i] = values[i-1];
            s += values[i];
        }
        values[0] = target;
        return s / values.length;
    }

}
