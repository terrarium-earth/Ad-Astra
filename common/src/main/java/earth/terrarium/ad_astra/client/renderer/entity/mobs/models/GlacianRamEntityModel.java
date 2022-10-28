package earth.terrarium.ad_astra.client.renderer.entity.mobs.models;

import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.entities.mobs.GlacianRamEntity;
import earth.terrarium.ad_astra.util.ModIdentifier;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

public class GlacianRamEntityModel<T extends GlacianRamEntity> extends QuadrupedEntityModel<T> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("glacian_ram"), "main");
    private float headPitchModifier;

    public GlacianRamEntityModel(ModelPart modelPart) {
        super(modelPart, false, 0.0f, 4.0f, 2.0f, 2.0f, 24);
    }

    @SuppressWarnings("unused")
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(3, 19).cuboid(-3.5f, -4.8f, -2.9f, 7.0f, 6.0f, 7.0f, new Dilation(0.0f)).uv(50, 53).cuboid(1.5f, -6.8f, -0.9f, 4.0f, 2.0f, 2.0f, new Dilation(0.0f)).uv(32, 52).cuboid(3.5f, -6.8f, 1.1f, 2.0f, 2.0f, 2.0f, new Dilation(0.0f)).uv(51, 56).cuboid(-5.5f, -6.8f, 1.1f, 2.0f, 2.0f, 2.0f, new Dilation(0.0f)).uv(52, 18).cuboid(-5.5f, -6.8f, -0.9f, 4.0f, 2.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 13.8f, -1.9f, 0.0f, 3.1416f, 0.0f));

        ModelPartData ear_left = head.addChild("ear_left", ModelPartBuilder.create().uv(21, 38).cuboid(3.8985f, -0.5f, -1.5f, 4.0f, 1.0f, 3.0f, new Dilation(0.0f)), ModelTransform.of(-7.3249f, 2.0408f, 1.35f, 0.0f, 0.0f, -0.9599f));

        ModelPartData ear_right = head.addChild("ear_right", ModelPartBuilder.create().uv(21, 38).mirrored().cuboid(-7.8985f, -0.5f, -1.5f, 4.0f, 1.0f, 3.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.of(7.3249f, 2.0408f, 1.35f, 0.0f, 0.0f, 0.9599f));

        ModelPartData left_front_leg = modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(0, 54).cuboid(-1.5f, -1.5f, -0.5f, 3.0f, 7.0f, 3.0f, new Dilation(0.0f)), ModelTransform.pivot(2.5f, 19.5f, -0.5f));

        ModelPartData right_front_leg = modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(12, 30).cuboid(-1.5f, -1.5f, -1.5f, 3.0f, 7.0f, 3.0f, new Dilation(0.0f)), ModelTransform.pivot(-2.5f, 19.5f, 0.5f));

        ModelPartData left_hind_leg = modelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(0, 54).cuboid(0.5f, -1.5f, -0.5f, 3.0f, 7.0f, 3.0f, new Dilation(0.0f)), ModelTransform.pivot(1.0f, 19.5f, 8.5f));

        ModelPartData right_hind_leg = modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(12, 30).cuboid(-2.0f, -1.5f, -1.5f, 3.0f, 7.0f, 3.0f, new Dilation(0.0f)), ModelTransform.pivot(-2.5f, 19.5f, 9.5f));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 40).cuboid(-1.5f, -2.4f, -7.6f, 3.0f, 2.0f, 3.0f, new Dilation(0.0f)).uv(0, 40).cuboid(5.5f, 1.6f, -0.6f, 0.0f, 2.0f, 6.0f, new Dilation(0.0f)).uv(0, 0).cuboid(-5.5f, -6.4f, -0.6f, 11.0f, 8.0f, 10.0f, new Dilation(0.0f)).uv(27, 27).cuboid(-5.5f, -4.4f, -5.6f, 11.0f, 6.0f, 5.0f, new Dilation(0.0f)).uv(0, 40).cuboid(-5.5f, 1.6f, -0.6f, 0.0f, 2.0f, 6.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 18.4f, 6.6f, 0.0f, 3.1416f, 0.0f));

        ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(0, 40).cuboid(13.0f, -1.0f, 3.0f, 0.0f, 2.0f, 6.0f, new Dilation(0.0f)), ModelTransform.of(4.5f, 2.6f, -3.6f, 0.0f, -1.5708f, 0.0f));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void animateModel(T entity, float f, float g, float h) {
        super.animateModel(entity, f, g, h);
        this.head.pivotY = 6.0f + entity.getNeckAngle(h) * 9.0f;
        this.headPitchModifier = entity.getHeadAngle(h);
    }

    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        this.head.pitch = this.headPitchModifier;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.child) {
            matrices.push();
            float scale = 1.25f / 2.0f;
            matrices.scale(scale, scale, scale);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
            matrices.translate(0, 1.1, 0.25);
            this.getHeadParts().forEach(headPart -> headPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
            matrices.pop();

            matrices.push();
            float scale1 = 1.15f / 2.0f;
            matrices.scale(scale1, scale1, scale1);
            matrices.translate(0, 0.7, 0);
            this.getBodyParts().forEach(bodyPart -> bodyPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
            matrices.pop();
        } else {
            matrices.push();
            float scale = 1.25f;
            matrices.scale(scale, scale, scale);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
            matrices.translate(0, 0.1, 0.25);
            this.getHeadParts().forEach(headPart -> headPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
            matrices.pop();

            matrices.push();
            float scale1 = 1.15f;
            matrices.scale(scale1, scale1, scale1);
            matrices.translate(0, -0.3, 0);
            this.getBodyParts().forEach(bodyPart -> bodyPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
            matrices.pop();
        }
    }
}
