package com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs.models;

import com.github.alexnijjar.beyond_earth.entities.mobs.AlienEntity;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class AlienEntityModel extends EntityModel<AlienEntity> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("alien"), "main");

    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leg0;
    private final ModelPart leg1;
    private final ModelPart arms;
    private final ModelPart head2;

    public AlienEntityModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leg0 = root.getChild("leg0");
        this.leg1 = root.getChild("leg1");
        this.arms = root.getChild("arms");
        this.head2 = root.getChild("head2");
    }

    @Override
    public void setAngles(AlienEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw / (180f / (float) Math.PI);
        this.head.pitch = headPitch / (180f / (float) Math.PI);
        this.leg0.pitch = MathHelper.cos(limbAngle * 1.0f) * -1.0f * limbDistance;
        this.leg1.pitch = MathHelper.cos(limbAngle * 1.0f) * 1.0f * limbDistance;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.child) {
            matrices.scale(0.5f, 0.5f, 0.5f);
            matrices.translate(0, 1.5f, 0);
        }
        head.render(matrices, vertices, light, overlay);
        body.render(matrices, vertices, light, overlay);
        leg0.render(matrices, vertices, light, overlay);
        leg1.render(matrices, vertices, light, overlay);
        arms.render(matrices, vertices, light, overlay);
        head2.render(matrices, vertices, light, overlay);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        // head.
        modelPartData.addChild("head",
                ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-4.0f, -9.0f, -4.0f, 8.0f, 9.0f, 8.0f, new Dilation(0.0f)).mirrored(false).uv(32, 0).mirrored().cuboid(-4.5f, -19.0f, -4.5f, 9.0f, 10.0f, 9.0f, new Dilation(0.0f)).mirrored(false)
                        .uv(16, 64).mirrored().cuboid(-8.0f, -15.0f, -8.0f, 16.0f, 0.0f, 16.0f, new Dilation(0.0f)).mirrored(false).uv(70, 2).mirrored().cuboid(-6.0f, -16.0f, -6.0f, 12.0f, 0.0f, 12.0f, new Dilation(0.0f)).mirrored(false).uv(24, 0)
                        .mirrored().cuboid(-1.0f, -3.0f, -6.0f, 2.0f, 4.0f, 2.0f, new Dilation(0.0f)).mirrored(false),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        // body.
        modelPartData.addChild("body",
                ModelPartBuilder.create().uv(16, 20).mirrored().cuboid(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f, new Dilation(0.0f)).mirrored(false).uv(0, 38).mirrored().cuboid(-4.0f, 0.0f, -3.0f, 8.0f, 18.0f, 6.0f, new Dilation(0.5f)).mirrored(false),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        // leg0.
        modelPartData.addChild("leg0", ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.pivot(2.0f, 12.0f, 0.0f));

        // leg1.
        modelPartData.addChild("leg1", ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.pivot(-2.0f, 12.0f, 0.0f));

        // arms.
        modelPartData.addChild("arms", ModelPartBuilder.create().uv(40, 38).mirrored().cuboid(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f, new Dilation(0.0f)).mirrored(false).uv(44, 22).mirrored().cuboid(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, new Dilation(0.0f))
                .mirrored(false).uv(44, 22).mirrored().cuboid(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.of(0.0f, 2.0f, 0.0f, -0.7854f, 0.0f, 0.0f));

        // head2.
        modelPartData.addChild("head2", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, -10.0f, 0.0f));

        return TexturedModelData.of(modelData, 128, 128);
    }
}
