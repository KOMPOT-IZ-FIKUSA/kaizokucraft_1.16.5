package com.deadfikus.kaizokucraft.client.render.animation;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class AnimatedBakedModel implements IBakedModel {
    protected final List<BakedQuad> quads;
    protected final boolean hasAmbientOcclusion;
    protected final boolean isGui3d;
    protected final boolean usesBlockLight;
    protected final TextureAtlasSprite particleIcon;
    protected final ItemCameraTransforms transforms;
    protected final ItemOverrideList overrides;


    public AnimatedBakedModel(List<BakedQuad> quads, boolean hasAmbientOcclusion, boolean isGui3d, boolean usesBlockLight,
                              TextureAtlasSprite particleIcon, ItemCameraTransforms transforms, ItemOverrideList overrides) {
        this.quads = quads;
        this.hasAmbientOcclusion = hasAmbientOcclusion;
        this.isGui3d = isGui3d;
        this.usesBlockLight = usesBlockLight;
        this.particleIcon = particleIcon;
        this.transforms = transforms;
        this.overrides = overrides;
    }

    public void setAnimationFrame(float[][] frame, VertexAnimationIndexContainer indexContainer) {
        for (int i = 0; i < quads.size(); i++) {
            BakedQuad quad = quads.get(i);
            int[] data = quad.getVertices();
            int[] positions = indexContainer.getPositionIndicesForQuad(i);
            for (int j = 0; j < 4; j++) {
                data[j * 8] =     Float.floatToRawIntBits(frame[positions[j]][0]);
                data[j * 8 + 1] = Float.floatToRawIntBits(frame[positions[j]][1]);
                data[j * 8 + 2] = Float.floatToRawIntBits(frame[positions[j]][2]);
            }
        }
    }

    public AnimatedBakedModel copy() {
        ArrayList<BakedQuad> newQuads = new ArrayList<>();
        this.quads.forEach(quad -> newQuads.add(BakedQuadUtils.copy(quad)));
        return new AnimatedBakedModel(newQuads, hasAmbientOcclusion, isGui3d, usesBlockLight, particleIcon, transforms, overrides);
    }

    @Nonnull
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, Random random) {
        return direction == null ? quads : new ArrayList<>();
    }

    @Nonnull
    public List<BakedQuad> getQuads() {
        return quads;
    }


    public boolean useAmbientOcclusion() {
        return this.hasAmbientOcclusion;
    }

    public boolean isGui3d() {
        return this.isGui3d;
    }

    public boolean usesBlockLight() {
        return this.usesBlockLight;
    }

    public boolean isCustomRenderer() {
        return false;
    }

    public TextureAtlasSprite getParticleIcon() {
        return this.particleIcon;
    }

    public ItemCameraTransforms getTransforms() {
        return this.transforms;
    }

    public ItemOverrideList getOverrides() {
        return this.overrides;
    }

    static final Direction[] directions = new Direction[]{Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH, null};
    static Random random = new Random();
    public static AnimatedBakedModel from(IBakedModel source) {
        List<BakedQuad> quads = new ArrayList<>();
        for (Direction d: directions) {
            List<BakedQuad> quads1 = source.getQuads(null, d, random);
            quads1.forEach(quad -> quads.add(new BakedQuad(quad.getVertices(), quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade())));
            quads.addAll(quads1);
        }
        return new AnimatedBakedModel(
                quads,
                source.useAmbientOcclusion(),
                source.isGui3d(),
                source.usesBlockLight(),
                source.getParticleIcon(),
                source.getTransforms(),
                source.getOverrides()
        );
    }
}
