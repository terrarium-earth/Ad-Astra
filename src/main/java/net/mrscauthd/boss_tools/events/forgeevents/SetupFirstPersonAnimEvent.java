package net.mrscauthd.boss_tools.events.forgeevents;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.eventbus.api.Event;

public class SetupFirstPersonAnimEvent extends Event
{
    private final ModelPart model;
    private final PoseStack poseStack;
    private final VertexConsumer vertexConsumer;
    private final int p_104304_;
    private final int p_104305_;

    public SetupFirstPersonAnimEvent(ModelPart model, PoseStack poseStack, VertexConsumer vertexConsumer, int p_104304_, int p_104305_)
    {
        this.model = model;
        this.poseStack = poseStack;
        this.vertexConsumer = vertexConsumer;
        this.p_104304_ = p_104304_;
        this.p_104305_ = p_104305_;
    }

    public ModelPart getModel()
    {
        return model;
    }

    public PoseStack getPoseStack()
    {
        return poseStack;
    }

    public VertexConsumer getVertexConsumer()
    {
        return vertexConsumer;
    }

    public int getP_104304_()
    {
        return p_104304_;
    }

    public int getP_104305_()
    {
        return p_104305_;
    }
}