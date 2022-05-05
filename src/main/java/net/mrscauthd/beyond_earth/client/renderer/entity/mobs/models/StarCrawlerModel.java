package net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.mrscauthd.beyond_earth.entities.mobs.StarCrawlerEntity;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class StarCrawlerModel extends EntityModel<StarCrawlerEntity> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("star_crawler"), "main");

    private final ModelPart body;
    private final ModelPart arm1g;
    private final ModelPart arm2g;
    private final ModelPart arm3g;
    private final ModelPart arm4g;

    public StarCrawlerModel(ModelPart root) {
        this.body = root.getChild("body");
        this.arm1g = root.getChild("arm1g");
        this.arm2g = root.getChild("arm2g");
        this.arm3g = root.getChild("arm3g");
        this.arm4g = root.getChild("arm4g");
    }

    @Override
    public void setAngles(StarCrawlerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // arm1.
        this.arm1g.getChild("Hand1").yaw = MathHelper.cos(limbAngle * 0.6662f) * limbDistance;
        this.arm2g.getChild("Hand2").yaw = MathHelper.cos(limbAngle * 0.6662f) * limbDistance;
        this.arm3g.getChild("Hand3").yaw = 80.115f + MathHelper.cos(limbAngle * 0.6662f) * limbDistance;
        this.arm4g.getChild("Hand4").yaw = -80.115f + MathHelper.cos(limbAngle * 0.6662f) * limbDistance;
        // arm.
        this.arm1g.yaw = MathHelper.cos(limbAngle * 0.6662f) * limbDistance;
        this.arm2g.yaw = MathHelper.cos(limbAngle * 0.6662f) * limbDistance;
        this.arm3g.yaw = MathHelper.cos(limbAngle * 0.6662f) * limbDistance;
        this.arm4g.yaw = MathHelper.cos(limbAngle * 0.6662f) * limbDistance;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertices, light, overlay);
        arm1g.render(matrices, vertices, light, overlay);
        arm2g.render(matrices, vertices, light, overlay);
        arm3g.render(matrices, vertices, light, overlay);
        arm4g.render(matrices, vertices, light, overlay);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        // Body.
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -13.0f, -8.0f, 16.0f, 10.0f, 16.0f, new Dilation(0.0f)).uv(0, 26).cuboid(-7.0f, -9.0f, -7.0f, 14.0f, 9.0f, 14.0f, new Dilation(0.0f)),
                ModelTransform.pivot(0.0f, 24.0f, 0.0f));

        ModelPartData arm1g = modelPartData.addChild("arm1g", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 18.3f, 6.75f));

        ModelPartData Arm1 = arm1g.addChild("Arm1", ModelPartBuilder.create().uv(48, 48).cuboid(-6.0f, -5.2f, 0.25f, 12.0f, 8.0f, 8.0f, new Dilation(0.0f)).uv(42, 26).cuboid(-5.0f, 2.8f, -1.75f, 10.0f, 3.0f, 9.0f, new Dilation(0.0f)),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        // cube_r1.
        Arm1.addChild("cube_r1", ModelPartBuilder.create().uv(48, 64).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-5.0f, 3.0f, 4.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r2.
        Arm1.addChild("cube_r2", ModelPartBuilder.create().uv(58, 64).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(5.0f, 3.0f, 3.25f, 0.0f, 0.0f, 0.3491f));

        ModelPartData Limb1 = arm1g.addChild("Limb1", ModelPartBuilder.create().uv(48, 0).cuboid(-5.0f, -4.3f, 0.25f, 10.0f, 7.0f, 9.0f, new Dilation(0.0f)).uv(0, 64).cuboid(-4.0f, 2.7f, -0.75f, 8.0f, 3.0f, 9.0f, new Dilation(0.0f)),
                ModelTransform.pivot(0.0f, 0.0f, 7.0f));

        // cube_r3.
        Limb1.addChild("cube_r3", ModelPartBuilder.create().uv(59, 38).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-4.0f, 3.0f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r4.
        Limb1.addChild("cube_r4", ModelPartBuilder.create().uv(57, 16).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(4.0f, 3.0f, 4.25f, 0.0f, 0.0f, 0.3491f));

        ModelPartData Hand1 = arm1g.addChild("Hand1", ModelPartBuilder.create().uv(0, 49).cuboid(-4.0f, -3.3f, 0.25f, 8.0f, 6.0f, 9.0f, new Dilation(0.0f)).uv(25, 55).cuboid(-3.0f, 2.7f, -0.75f, 6.0f, 3.0f, 9.0f, new Dilation(0.0f)),
                ModelTransform.pivot(0.0f, 0.1f, 15.0f));

        // cube_r5.
        Hand1.addChild("cube_r5", ModelPartBuilder.create().uv(39, 49).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-3.0f, 2.9f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r6.
        Hand1.addChild("cube_r6", ModelPartBuilder.create().uv(49, 38).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(3.0f, 2.9f, 4.25f, 0.0f, 0.0f, 0.3491f));

        ModelPartData arm2g = modelPartData.addChild("arm2g", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 18.4f, -7.75f));

        ModelPartData Arm2 = arm2g.addChild("Arm2", ModelPartBuilder.create().uv(48, 48).cuboid(-6.0f, -5.2f, 0.25f, 12.0f, 8.0f, 8.0f, new Dilation(0.0f)).uv(42, 26).cuboid(-5.0f, 2.8f, -1.75f, 10.0f, 3.0f, 9.0f, new Dilation(0.0f)),
                ModelTransform.of(0.0f, -0.1f, 1.0f, 0.0f, 3.1416f, 0.0f));

        // cube_r7.
        Arm2.addChild("cube_r7", ModelPartBuilder.create().uv(48, 64).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-5.0f, 3.0f, 4.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r8.
        Arm2.addChild("cube_r8", ModelPartBuilder.create().uv(58, 64).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(5.0f, 3.0f, 3.25f, 0.0f, 0.0f, 0.3491f));

        ModelPartData Limb2 = arm2g.addChild("Limb2", ModelPartBuilder.create().uv(48, 0).cuboid(-5.0f, -4.3f, 0.25f, 10.0f, 7.0f, 9.0f, new Dilation(0.0f)).uv(0, 64).cuboid(-4.0f, 2.7f, -0.75f, 8.0f, 3.0f, 9.0f, new Dilation(0.0f)),
                ModelTransform.of(0.0f, -0.1f, -6.0f, 0.0f, 3.1416f, 0.0f));

        // cube_r9.
        Limb2.addChild("cube_r9", ModelPartBuilder.create().uv(59, 38).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-4.0f, 3.0f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r10.
        Limb2.addChild("cube_r10", ModelPartBuilder.create().uv(57, 16).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(4.0f, 3.0f, 4.25f, 0.0f, 0.0f, 0.3491f));

        ModelPartData Hand2 = arm2g.addChild("Hand2", ModelPartBuilder.create().uv(0, 49).cuboid(-4.0f, -3.3f, -4.75f, 8.0f, 6.0f, 9.0f, new Dilation(0.0f)).uv(25, 55).cuboid(-3.0f, 2.7f, -3.75f, 6.0f, 3.0f, 9.0f, new Dilation(0.0f)),
                ModelTransform.pivot(0.0f, 0.0f, -19.0f));

        // cube_r11.
        Hand2.addChild("cube_r11", ModelPartBuilder.create().uv(39, 49).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-3.0f, 2.9f, 0.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r12.
        Hand2.addChild("cube_r12", ModelPartBuilder.create().uv(49, 38).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(3.0f, 2.9f, -0.75f, 0.0f, 0.0f, 0.3491f));

        ModelPartData arm3g = modelPartData.addChild("arm3g", ModelPartBuilder.create(), ModelTransform.pivot(-7.0f, 24.0f, 0.0f));

        ModelPartData Arm3 = arm3g.addChild("Arm3", ModelPartBuilder.create().uv(48, 48).cuboid(-6.0f, -5.2f, 0.25f, 12.0f, 8.0f, 8.0f, new Dilation(0.0f)).uv(42, 26).cuboid(-5.0f, 2.8f, -1.75f, 10.0f, 3.0f, 9.0f, new Dilation(0.0f)),
                ModelTransform.of(0.25f, -5.7f, 0.0f, 0.0f, -1.5708f, 0.0f));

        // cube_r13.
        Arm3.addChild("cube_r13", ModelPartBuilder.create().uv(48, 64).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-5.0f, 3.0f, 4.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r14.
        Arm3.addChild("cube_r14", ModelPartBuilder.create().uv(58, 64).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(5.0f, 3.0f, 3.25f, 0.0f, 0.0f, 0.3491f));

        ModelPartData Limb3 = arm3g.addChild("Limb3", ModelPartBuilder.create().uv(48, 0).cuboid(-5.0f, -4.3f, 0.25f, 10.0f, 7.0f, 9.0f, new Dilation(0.0f)).uv(0, 64).cuboid(-4.0f, 2.7f, -0.75f, 8.0f, 3.0f, 9.0f, new Dilation(0.0f)),
                ModelTransform.of(-6.75f, -5.7f, 0.0f, 0.0f, -1.5708f, 0.0f));

        // cube_r15.
        Limb3.addChild("cube_r15", ModelPartBuilder.create().uv(59, 38).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-4.0f, 3.0f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r16.
        Limb3.addChild("cube_r16", ModelPartBuilder.create().uv(57, 16).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(4.0f, 3.0f, 4.25f, 0.0f, 0.0f, 0.3491f));

        ModelPartData Hand3 = arm3g.addChild("Hand3", ModelPartBuilder.create().uv(0, 49).cuboid(-4.0f, -3.3f, 0.25f, 8.0f, 6.0f, 9.0f, new Dilation(0.0f)).uv(25, 55).cuboid(-3.0f, 2.7f, -0.75f, 6.0f, 3.0f, 9.0f, new Dilation(0.0f)),
                ModelTransform.of(-14.75f, -5.6f, 0.0f, 0.0f, -1.5708f, 0.0f));

        // cube_r17.
        Hand3.addChild("cube_r17", ModelPartBuilder.create().uv(39, 49).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-3.0f, 2.9f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r18.
        Hand3.addChild("cube_r18", ModelPartBuilder.create().uv(49, 38).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(3.0f, 2.9f, 4.25f, 0.0f, 0.0f, 0.3491f));

        ModelPartData arm4g = modelPartData.addChild("arm4g", ModelPartBuilder.create(), ModelTransform.pivot(8.0f, 24.0f, 0.0f));

        ModelPartData Arm4 = arm4g.addChild("Arm4", ModelPartBuilder.create().uv(48, 48).cuboid(-6.0f, -5.2f, 0.25f, 12.0f, 8.0f, 8.0f, new Dilation(0.0f)).uv(42, 26).cuboid(-5.0f, 2.8f, -1.75f, 10.0f, 3.0f, 9.0f, new Dilation(0.0f)),
                ModelTransform.of(-1.25f, -5.7f, 0.0f, 0.0f, 1.5708f, 0.0f));

        // cube_r19.
        Arm4.addChild("cube_r19", ModelPartBuilder.create().uv(48, 64).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-5.0f, 3.0f, 4.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r20.
        Arm4.addChild("cube_r20", ModelPartBuilder.create().uv(58, 64).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(5.0f, 3.0f, 3.25f, 0.0f, 0.0f, 0.3491f));

        ModelPartData Limb4 = arm4g.addChild("Limb4", ModelPartBuilder.create().uv(48, 0).cuboid(-5.0f, -4.3f, 0.25f, 10.0f, 7.0f, 9.0f, new Dilation(0.0f)).uv(0, 64).cuboid(-4.0f, 2.7f, -0.75f, 8.0f, 3.0f, 9.0f, new Dilation(0.0f)),
                ModelTransform.of(5.75f, -5.7f, 0.0f, 0.0f, 1.5708f, 0.0f));

        // cube_r21.
        Limb4.addChild("cube_r21", ModelPartBuilder.create().uv(59, 38).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-4.0f, 3.0f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r22.
        Limb4.addChild("cube_r22", ModelPartBuilder.create().uv(57, 16).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(4.0f, 3.0f, 4.25f, 0.0f, 0.0f, 0.3491f));

        ModelPartData Hand4 = arm4g.addChild("Hand4", ModelPartBuilder.create().uv(0, 49).cuboid(-4.0f, -3.3f, 0.25f, 8.0f, 6.0f, 9.0f, new Dilation(0.0f)).uv(25, 55).cuboid(-3.0f, 2.7f, -0.75f, 6.0f, 3.0f, 9.0f, new Dilation(0.0f)),
                ModelTransform.of(13.75f, -5.6f, 0.0f, 0.0f, 1.5708f, 0.0f));

        // cube_r23.
        Hand4.addChild("cube_r23", ModelPartBuilder.create().uv(39, 49).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-3.0f, 2.9f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r24.
        Hand4.addChild("cube_r24", ModelPartBuilder.create().uv(49, 38).cuboid(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(3.0f, 2.9f, 4.25f, 0.0f, 0.0f, 0.3491f));

        return TexturedModelData.of(modelData, 128, 128);
    }
}
