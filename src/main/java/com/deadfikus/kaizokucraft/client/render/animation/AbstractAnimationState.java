package com.deadfikus.kaizokucraft.client.render.animation;

import net.minecraft.client.renderer.model.BakedQuad;

public abstract class AbstractAnimationState {

    protected ObjectAnimationData data;
    protected AnimatedBakedModel model;
    protected VertexAnimationIndexContainer indexContainer;

    public AbstractAnimationState(ObjectAnimationData data, AnimatedBakedModel copiedModel) {
        this.data = data;
        this.model = copiedModel;
        BakedQuad[] quads = new BakedQuad[model.quads.size()];
        model.quads.toArray(quads);
        this.indexContainer = new VertexAnimationIndexContainer(data.getFrame(0), quads);
    }

    public final void tick() {
        model.setAnimationFrame(data.getFrame(getFrameIndex()), indexContainer);
    }

    public abstract int getFrameIndex();


}
