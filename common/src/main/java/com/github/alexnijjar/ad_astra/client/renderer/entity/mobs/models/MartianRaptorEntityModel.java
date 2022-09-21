package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models;

import com.github.alexnijjar.ad_astra.entities.mobs.MartianRaptorEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class MartianRaptorEntityModel extends EntityModel<MartianRaptorEntity> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("martian_raptor"), "main");

	private final ModelPart body;
	private final ModelPart leg1;
	private final ModelPart leg2;

	public MartianRaptorEntityModel(ModelPart root) {
		this.body = root.getChild("body");
		this.leg1 = root.getChild("leg1");
		this.leg2 = root.getChild("leg2");
	}

	@Override
	public void setAngles(MartianRaptorEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		float cooldown = entity.getMovementCooldownTicks();
		float f = 1.0f - MathHelper.abs(10 - 2 * cooldown) / 10.0f;

		this.body.getChild("head").getChild("bone2").getChild("mouth1").roll = MathHelper.lerp(f, 0.0f, -1.14906584f);
		this.body.getChild("head").getChild("bone2").getChild("mouth2").roll = -MathHelper.lerp(f, 0.0f, -1.14906584f);

		this.leg1.pitch = MathHelper.cos(limbAngle * 1.0f) * -1.0f * limbDistance;
		this.leg2.pitch = MathHelper.cos(limbAngle * 1.0f) * 1.0f * limbDistance;

		this.body.getChild("head").yaw = headYaw / (180f / (float) Math.PI);
		this.body.getChild("head").pitch = headPitch / (180f / (float) Math.PI);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertices, light, overlay);
		leg1.render(matrices, vertices, light, overlay);
		leg2.render(matrices, vertices, light, overlay);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData leg1 = modelPartData.addChild("leg1", ModelPartBuilder.create().uv(14, 69).cuboid(-2.0f, 11.7065f, -5.0813f, 5.0f, 3.0f, 6.0f, new Dilation(0.0f)), ModelTransform.pivot(6.0f, 9.0f, 4.0f));

		// cube_r1.
		leg1.addChild("cube_r1", ModelPartBuilder.create().uv(36, 64).cuboid(-2.5f, -7.0f, -2.5f, 4.0f, 14.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 7.7065f, 2.4187f, -0.6545f, 0.0f, 0.0f));

		// cube_r2.
		leg1.addChild("cube_r2", ModelPartBuilder.create().uv(0, 58).cuboid(-2.5f, -6.0f, -2.5f, 5.0f, 12.0f, 5.0f, new Dilation(0.0f)), ModelTransform.of(0.5f, -0.2935f, 2.4187f, 0.6545f, 0.0f, 0.0f));

		ModelPartData leg2 = modelPartData.addChild("leg2", ModelPartBuilder.create().uv(14, 69).mirrored().cuboid(-3.0f, 11.7065f, -5.0813f, 5.0f, 3.0f, 6.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.pivot(-6.0f, 9.0f, 4.0f));

		// cube_r3.
		leg2.addChild("cube_r3", ModelPartBuilder.create().uv(36, 64).mirrored().cuboid(-2.5f, -7.0f, -2.5f, 4.0f, 14.0f, 4.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.of(0.0f, 7.7065f, 2.4187f, -0.6545f, 0.0f, 0.0f));

		// cube_r4.
		leg2.addChild("cube_r4", ModelPartBuilder.create().uv(0, 58).mirrored().cuboid(-2.5f, -6.0f, -2.5f, 5.0f, 12.0f, 5.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.of(-0.5f, -0.2935f, 2.4187f, 0.6545f, 0.0f, 0.0f));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -24.0f, -6.0f, 8.0f, 10.0f, 15.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 24.0f, 0.0f));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, -19.0f, -3.0f));

		ModelPartData bone2 = head.addChild("bone2", ModelPartBuilder.create().uv(34, 34).cuboid(-5.0f, -4.0f, 0.0f, 10.0f, 9.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -1.0f, -8.0f, -0.4363f, 0.0f, 0.0f));

		ModelPartData bone = bone2.addChild("bone", ModelPartBuilder.create(), ModelTransform.of(0.25f, -6.75f, 6.0f, -0.5236f, 0.0f, 0.0f));

		// cube_r5.
		bone.addChild("cube_r5", ModelPartBuilder.create().uv(0, 42).cuboid(-6.0f, -5.5f, -4.5f, 10.0f, 11.0f, 5.0f, new Dilation(0.0f)), ModelTransform.of(-2.2012f, 0.0f, -0.1198f, -0.0436f, 0.6981f, 0.0f));

		// cube_r6.
		bone.addChild("cube_r6", ModelPartBuilder.create().uv(0, 42).mirrored().cuboid(-4.0f, -5.5f, -4.5f, 10.0f, 11.0f, 5.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.of(1.7988f, 0.0f, -0.1198f, -0.0436f, -0.6981f, 0.0f));

		ModelPartData mouth1 = bone2.addChild("mouth1", ModelPartBuilder.create(), ModelTransform.pivot(-2.1711f, 4.4542f, 3.0357f));

		// cube_r7.
		mouth1.addChild("cube_r7", ModelPartBuilder.create().uv(52, 71).cuboid(-1.5789f, -1.5855f, -2.25f, 3.0f, 6.0f, 5.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3491f));

		ModelPartData mouth2 = bone2.addChild("mouth2", ModelPartBuilder.create(), ModelTransform.pivot(1.8289f, 4.4542f, 3.0357f));

		// cube_r8.
		mouth2.addChild("cube_r8", ModelPartBuilder.create().uv(52, 71).mirrored().cuboid(-1.0789f, -1.4145f, -2.25f, 3.0f, 6.0f, 5.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.3491f));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, -19.0f, 9.0f));

		// cube_r9.
		tail.addChild("cube_r9", ModelPartBuilder.create().uv(20, 50).cuboid(-2.0f, -2.0f, -5.0f, 4.0f, 4.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 5.1366f, 17.1501f, 0.1309f, 0.0f, 0.0f));

		// cube_r10.
		tail.addChild("cube_r10", ModelPartBuilder.create().uv(31, 0).cuboid(-3.0f, -2.5f, -5.0f, 6.0f, 5.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 4.8909f, 9.0849f, -0.1309f, 0.0f, 0.0f));

		// cube_r11.
		tail.addChild("cube_r11", ModelPartBuilder.create().uv(63, 7).cuboid(-2.5f, -1.0f, -1.5f, 5.0f, 2.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 6.4973f, 16.5505f, 1.7017f, 0.0f, 0.0f));

		// cube_r12.
		tail.addChild("cube_r12", ModelPartBuilder.create().uv(61, 31).cuboid(-3.5f, -1.0f, -2.5f, 7.0f, 2.0f, 5.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 6.7473f, 12.5505f, 1.6144f, 0.0f, 0.0f));

		// cube_r13.
		tail.addChild("cube_r13", ModelPartBuilder.create().uv(24, 26).cuboid(-10.5f, -2.0f, -5.5f, 8.0f, 2.0f, 5.0f, new Dilation(0.0f)), ModelTransform.of(6.5f, 2.7065f, 5.4187f, 1.1345f, 0.0f, 0.0f));

		// cube_r14.
		tail.addChild("cube_r14", ModelPartBuilder.create().uv(53, 0).cuboid(-10.5f, -2.0f, -5.5f, 8.0f, 2.0f, 5.0f, new Dilation(0.0f)), ModelTransform.of(6.5f, 3.7065f, 9.4187f, 1.3526f, 0.0f, 0.0f));

		// cube_r15.
		tail.addChild("cube_r15", ModelPartBuilder.create().uv(0, 25).cuboid(-3.5f, -3.5f, -5.0f, 7.0f, 7.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 1.4079f, 1.7286f, -0.6545f, 0.0f, 0.0f));

		return TexturedModelData.of(modelData, 128, 128);
	}
}
