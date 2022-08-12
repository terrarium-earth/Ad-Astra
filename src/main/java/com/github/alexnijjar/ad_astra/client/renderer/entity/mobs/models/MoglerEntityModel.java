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
	private final ModelPart leg1;
	private final ModelPart leg2;
	private final ModelPart leg3;
	private final ModelPart leg4;

	public MoglerEntityModel(ModelPart root) {
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.leg1 = root.getChild("leg1");
		this.leg2 = root.getChild("leg2");
		this.leg3 = root.getChild("leg3");
		this.leg4 = root.getChild("leg4");
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
			} else if (zoglin != null) {
				i = zoglin.getMovementCooldownTicks();
			}
			float f = 1.0f - (float) MathHelper.abs(10 - 2 * i) / 10.0f;
			this.head.pitch = MathHelper.lerp(f, 0.0f, -1.14906584f);

			this.leg1.pitch = MathHelper.cos(limbAngle) * 1.2f * limbDistance;
			this.leg2.pitch = MathHelper.cos(limbAngle + (float) Math.PI) * 1.2f * limbDistance;
			this.leg3.pitch = this.leg1.pitch;
			this.leg4.pitch = this.leg2.pitch;
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
		leg1.render(matrices, vertices, light, overlay);
		leg2.render(matrices, vertices, light, overlay);
		leg3.render(matrices, vertices, light, overlay);
		leg4.render(matrices, vertices, light, overlay);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -15.0f, -13.0f, 16.0f, 9.0f, 32.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 24.0f, 0.0f));

		// cube_r1.
		body.addChild("cube_r1", ModelPartBuilder.create().uv(85, 30).cuboid(-9.0f, -20.0f, 12.0f, 18.0f, 9.0f, 11.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, -0.2182f, 0.0f, 0.0f));

		// cube_r2.
		body.addChild("cube_r2", ModelPartBuilder.create().uv(0, 41).cuboid(-10.0f, -18.0f, 5.0f, 20.0f, 9.0f, 16.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.1309f, 0.0f, 0.0f));

		// cube_r3.
		body.addChild("cube_r3", ModelPartBuilder.create().uv(58, 52).cuboid(-11.0f, -18.0f, 2.0f, 22.0f, 9.0f, 14.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.3927f, 0.0f, 0.0f));

		// cube_r4.
		body.addChild("cube_r4", ModelPartBuilder.create().uv(64, 0).cuboid(-12.0f, -22.0f, -4.0f, 24.0f, 11.0f, 11.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.5236f, 0.0f, 0.0f));

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 24.0f, 0.0f));

		// cube_r5.
		head.addChild("cube_r5", ModelPartBuilder.create().uv(0, 66).cuboid(-10.0f, -26.0f, -3.0f, 20.0f, 11.0f, 11.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.829f, 0.0f, 0.0f));

		// cube_r6.
		head.addChild("cube_r6", ModelPartBuilder.create().uv(0, 22).cuboid(-7.5f, -8.5f, -1.75f, 15.0f, 8.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -11.5f, -20.0f, 0.0f, 0.0f, 0.0f));

		// cube_r7.
		head.addChild("cube_r7", ModelPartBuilder.create().uv(62, 75).cuboid(-9.0f, -8.5f, -1.75f, 18.0f, 13.0f, 11.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -11.5f, -20.0f, -0.6545f, 0.0f, 0.0f));

		ModelPartData jaw2 = head.addChild("jaw2", ModelPartBuilder.create(), ModelTransform.of(0.0f, -11.5f, -20.0f, -0.6545f, 0.0f, 0.0f));

		// cube_r8.
		jaw2.addChild("cube_r8", ModelPartBuilder.create().uv(96, 99).cuboid(5.25f, -1.5f, -0.75f, 5.0f, 8.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5672f));

		ModelPartData jaw1 = head.addChild("jaw1", ModelPartBuilder.create(), ModelTransform.of(0.0f, -11.5f, -20.0f, -0.6545f, 0.0f, 0.0f));

		// cube_r9.
		jaw1.addChild("cube_r9", ModelPartBuilder.create().uv(0, 110).cuboid(-10.25f, -1.5f, -0.75f, 5.0f, 8.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.5672f));

		// leg1.
		modelPartData.addChild("leg1", ModelPartBuilder.create().uv(64, 99).cuboid(-3.5f, -3.5f, -4.5f, 7.0f, 13.0f, 9.0f, new Dilation(0.0f)), ModelTransform.pivot(-9.5f, 14.5f, -6.5f));

		// leg2.
		modelPartData.addChild("leg2", ModelPartBuilder.create().uv(32, 90).cuboid(-3.5f, -3.5f, -4.5f, 7.0f, 13.0f, 9.0f, new Dilation(0.0f)), ModelTransform.pivot(9.5f, 14.5f, -6.5f));

		// leg3.
		modelPartData.addChild("leg3", ModelPartBuilder.create().uv(0, 88).cuboid(-3.5f, -3.5f, -4.5f, 7.0f, 13.0f, 9.0f, new Dilation(0.0f)), ModelTransform.pivot(9.5f, 14.5f, 12.5f));

		// leg4.
		modelPartData.addChild("leg4", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5f, -3.5f, -4.5f, 7.0f, 13.0f, 9.0f, new Dilation(0.0f)), ModelTransform.pivot(-9.5f, 14.5f, 12.5f));

		return TexturedModelData.of(modelData, 256, 256);
	}
}
