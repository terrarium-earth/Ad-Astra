package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class MoglerEntityModel<T extends Entity> extends EntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("mogler"), "main");

    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart right_front_leg;
    private final ModelPart left_front_leg;
    private final ModelPart leg3;
    private final ModelPart left_back_leg;

	public MoglerEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.right_front_leg = root.getChild("right_front_leg");
        this.left_front_leg = root.getChild("left_front_leg");
        this.leg3 = root.getChild("right_back_leg");
        this.left_back_leg = root.getChild("left_back_leg");
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

		HoglinEntity hoglin = null;
		ZoglinEntity zoglin = null;

		if (entity instanceof HoglinEntity hog) {
			hoglin = hog;
		}

		if (entity instanceof ZoglinEntity zog) {
			zoglin = zog;
		}

		if (entity instanceof HoglinEntity || entity instanceof ZoglinEntity) {
			this.head.yaw = headYaw * ((float) Math.PI / 180.0f);
			int i = 0;
            if (hoglin != null) {
                i = hoglin.getMovementCooldownTicks();
            }
			float f = 1.0f - (float) MathHelper.abs(10 - 2 * i) / 10.0f;
			this.head.pitch = MathHelper.lerp(f, 0.0f, -1.14906584f);

            this.right_front_leg.pitch = MathHelper.cos(limbAngle) * 1.2f * limbDistance;
            this.left_front_leg.pitch = MathHelper.cos(limbAngle + (float) Math.PI) * 1.2f * limbDistance;
            this.leg3.pitch = this.right_front_leg.pitch;
            this.left_back_leg.pitch = this.left_front_leg.pitch;
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.child) {
            matrices.scale(0.5f, 0.5f, 0.5f);
            matrices.translate(0, 1.5f, 0);
        }

        body.render(matrices, vertices, light, overlay);
        head.render(matrices, vertices, light, overlay);
        right_front_leg.render(matrices, vertices, light, overlay);
        left_front_leg.render(matrices, vertices, light, overlay);
        leg3.render(matrices, vertices, light, overlay);
        left_back_leg.render(matrices, vertices, light, overlay);
	}

	@SuppressWarnings("unused")
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(33, 127).cuboid(-8.0F, 4.3819F, -6.3545F, 16.0F, 6.0F, 0.0F, new Dilation(0.0F))
                .uv(97, 68).cuboid(-9.0F, -7.3501F, -12.2827F, 18.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.1899F, -17.7795F));

        ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(57, 46).cuboid(-9.0F, -25.0F, -8.0F, 18.0F, 11.0F, 11.0F, new Dilation(0.0F))
                .uv(59, 106).cuboid(-8.0F, -14.0F, 2.0F, 16.0F, 6.0F, 4.0F, new Dilation(0.0F))
                .uv(56, 68).cuboid(-8.0F, -14.0F, -7.0F, 16.0F, 6.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 10.8101F, -11.2205F, -0.5236F, 0.0F, 0.0F));

        ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(0, 102).mirrored().cuboid(-2.0F, -2.5F, -2.5F, 7.0F, 5.0F, 9.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(4.4912F, 4.0149F, -10.184F, -0.4164F, -0.3272F, -0.6284F));

        ModelPartData cube_r3 = head.addChild("cube_r3", ModelPartBuilder.create().uv(0, 102).cuboid(-5.0F, -2.5F, -2.5F, 7.0F, 5.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-4.4912F, 4.0149F, -10.184F, -0.4164F, 0.3272F, 0.6284F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -0.4541F, -13.2739F, 10.0F, 6.0F, 28.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 11.4541F, -6.7261F));

        ModelPartData cube_r4 = body.addChild("cube_r4", ModelPartBuilder.create().uv(0, 34).cuboid(-11.0F, 0.5F, -13.0F, 22.0F, 11.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.1781F, 0.0F, 0.0F));

        ModelPartData cube_r5 = body.addChild("cube_r5", ModelPartBuilder.create().uv(48, 0).cuboid(-10.0F, -7.5F, -10.875F, 20.0F, 13.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.3963F, 0.0F, 0.0F));

        ModelPartData cube_r6 = body.addChild("cube_r6", ModelPartBuilder.create().uv(0, 57).cuboid(-9.0F, -15.5F, -8.0F, 18.0F, 14.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.4835F, 0.0F, 0.0F));

        ModelPartData cube_r7 = body.addChild("cube_r7", ModelPartBuilder.create().uv(66, 25).cuboid(-8.0F, -20.5F, -8.0F, 16.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.8326F, 0.0F, 0.0F));

        ModelPartData right_back_leg = modelPartData.addChild("right_back_leg", ModelPartBuilder.create().uv(36, 83).cuboid(-4.5F, -1.0F, -4.5F, 9.0F, 12.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.5F, 13.0F, 2.5F));

        ModelPartData right_front_leg = modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(99, 95).cuboid(-4.5F, -1.0F, -4.5F, 9.0F, 12.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.5F, 13.0F, -16.5F));

        ModelPartData left_back_leg = modelPartData.addChild("left_back_leg", ModelPartBuilder.create().uv(0, 81).cuboid(-4.5F, -1.0F, -4.5F, 9.0F, 12.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(7.5F, 13.0F, 2.5F));

        ModelPartData left_front_leg = modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(72, 83).cuboid(-4.5F, -1.0F, -4.5F, 9.0F, 12.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(7.5F, 13.0F, -16.5F));

		return TexturedModelData.of(modelData, 256, 256);
	}
}
