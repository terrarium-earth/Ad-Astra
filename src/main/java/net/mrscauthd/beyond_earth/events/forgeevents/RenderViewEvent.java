package net.mrscauthd.beyond_earth.events.forgeevents;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class RenderViewEvent extends Event {

    private final PoseStack poseStack;
    private float tick;

    public RenderViewEvent(PoseStack poseStack, float tick) {
        this.poseStack = poseStack;
        this.tick = tick;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public float getTick() {
        return tick;
    }
}