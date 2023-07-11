package com.deadfikus.kaizokucraft.client.render.animation;

import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.data.EmptyModelData;

import java.util.List;
import java.util.Random;

public class BakedQuadUtils {
    private static final Random random = new Random();


    public static BakedQuad copy(BakedQuad source) {
        return new BakedQuad(source.getVertices(), source.getTintIndex(), source.getDirection(), source.getSprite(), source.isShade());
    }


    public static List<BakedQuad> getFromBakedModel(IBakedModel model) {
        List<BakedQuad> res = model.getQuads(null, null, random, EmptyModelData.INSTANCE);
        res.addAll(model.getQuads(null, Direction.DOWN, random, EmptyModelData.INSTANCE));
        res.addAll(model.getQuads(null, Direction.UP, random, EmptyModelData.INSTANCE));
        res.addAll(model.getQuads(null, Direction.NORTH, random, EmptyModelData.INSTANCE));
        res.addAll(model.getQuads(null, Direction.SOUTH, random, EmptyModelData.INSTANCE));
        res.addAll(model.getQuads(null, Direction.EAST, random, EmptyModelData.INSTANCE));
        res.addAll(model.getQuads(null, Direction.WEST, random, EmptyModelData.INSTANCE));
        return res;
    }
}
