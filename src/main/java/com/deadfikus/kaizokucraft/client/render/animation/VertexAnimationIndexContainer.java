package com.deadfikus.kaizokucraft.client.render.animation;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.model.BakedQuad;

import java.util.ArrayList;
import java.util.Arrays;

public class VertexAnimationIndexContainer {
    private final int[][] quadsToPositionsIndices; // has length as same as quads. The second index is 4.
    private final IntPairList[] verticesToQuadsLocalVertexIndices; // has length as same as positions list. The position might connect to any count of quads.

    public VertexAnimationIndexContainer(float[][] positions, BakedQuad[] quads) {
        quadsToPositionsIndices = new int[quads.length][4];
        verticesToQuadsLocalVertexIndices = new IntPairList[positions.length];

        for (int quadIndex = 0; quadIndex < quads.length; quadIndex++) {
            int[] data = quads[quadIndex].getVertices();
            for (int quadLocalIndex = 0; quadLocalIndex < 4; quadLocalIndex++) {
                float x = Float.intBitsToFloat(data[quadLocalIndex * 8]);
                float y = Float.intBitsToFloat(data[quadLocalIndex * 8 + 1]);
                float z = Float.intBitsToFloat(data[quadLocalIndex * 8 + 2]);
                float minDifferenceValue = 10000;
                int minDifferenceIndex = 0;
                for (int positionIndex = 0; positionIndex < positions.length; positionIndex++) {
                    float difference =
                            Math.abs(x - positions[positionIndex][0]) +
                            Math.abs(y - positions[positionIndex][1]) +
                            Math.abs(z - positions[positionIndex][2]);
                    if (difference < minDifferenceValue) {
                        minDifferenceValue = difference;
                        minDifferenceIndex = positionIndex;
                    }
                }
                quadsToPositionsIndices[quadIndex][quadLocalIndex] = minDifferenceIndex;
                if (verticesToQuadsLocalVertexIndices[minDifferenceIndex] == null) {
                    verticesToQuadsLocalVertexIndices[minDifferenceIndex] = new IntPairList();
                }
                verticesToQuadsLocalVertexIndices[minDifferenceIndex].add(new Pair<>(quadIndex, quadLocalIndex));
            }
        }
    }

    public int[] getPositionIndicesForQuad(int quadIndex) {
        return Arrays.copyOf(quadsToPositionsIndices[quadIndex], 4);
    }

    private static class IntPairList extends ArrayList<Pair<Integer, Integer>> {

    }
}

