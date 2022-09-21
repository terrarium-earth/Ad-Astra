package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models;

import com.github.alexnijjar.ad_astra.entities.mobs.CorruptedLunarianEntity;
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
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CorruptedLunarianEntityModel extends EntityModel<CorruptedLunarianEntity> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("corrupted_lunarian"), "main");

	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart leg0;
	private final ModelPart leg1;
	private final ModelPart arm1;
	private final ModelPart arm2;
	private final ModelPart monsterarm1;
	private final ModelPart monsterarm2;
	private final ModelPart monsterarm3;
	private final ModelPart monsterarm4;

	public CorruptedLunarianEntityModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.leg0 = root.getChild("leg0");
		this.leg1 = root.getChild("leg1");
		this.arm1 = root.getChild("arm1");
		this.arm2 = root.getChild("arm2");
		this.monsterarm1 = root.getChild("monsterarm1");
		this.monsterarm2 = root.getChild("monsterarm2");
		this.monsterarm3 = root.getChild("monsterarm3");
		this.monsterarm4 = root.getChild("monsterarm4");
	}

	@Override
	public void setAngles(CorruptedLunarianEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.arm2.yaw = 0.0f;
		this.arm1.yaw = 0.0f;
		this.arm2.roll = 0.0f;
		this.arm1.roll = 0.0f;
		this.arm2.pitch = 0.0f;
		this.arm1.pitch = 0.0f;

		this.arm2.roll -= MathHelper.cos(animationProgress * 0.04f) * 0.04f + 0.04f;
		this.arm1.roll += MathHelper.cos(animationProgress * 0.04f) * 0.04f + 0.04f;

		// base end

		this.head.yaw = headYaw / (180f / (float) Math.PI);
		this.head.pitch = headPitch / (180f / (float) Math.PI);
		this.leg0.pitch = MathHelper.cos(limbAngle * 1.0f) * -1.0f * limbDistance;
		this.leg1.pitch = MathHelper.cos(limbAngle * 1.0f) * 1.0f * limbDistance;
		this.monsterarm1.yaw = MathHelper.cos(limbAngle * 0.3662f + (float) Math.PI) * limbDistance / 2;
		this.monsterarm4.yaw = MathHelper.cos(limbAngle * 0.3662f + (float) Math.PI) * limbDistance / 2;
		this.monsterarm3.yaw = MathHelper.cos(limbAngle * 0.3662f + (float) Math.PI) * limbDistance / 2;
		this.monsterarm2.yaw = MathHelper.cos(limbAngle * 0.3662f + (float) Math.PI) * limbDistance / 2;
		this.arm1.pitch = 30f;
		this.arm2.pitch = 30f;

		this.arm2.pitch -= MathHelper.cos(animationProgress * 0.04f) * 0.04f + 0.04f;
		this.arm1.pitch += MathHelper.cos(animationProgress * 0.04f) * 0.04f + 0.04f;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertices, light, overlay);
		body.render(matrices, vertices, light, overlay);
		leg0.render(matrices, vertices, light, overlay);
		leg1.render(matrices, vertices, light, overlay);
		arm1.render(matrices, vertices, light, overlay);
		arm2.render(matrices, vertices, light, overlay);
		monsterarm1.render(matrices, vertices, light, overlay);
		monsterarm2.render(matrices, vertices, light, overlay);
		monsterarm3.render(matrices, vertices, light, overlay);
		monsterarm4.render(matrices, vertices, light, overlay);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-4.0f, -9.0f, -4.0f, 8.0f, 9.0f, 8.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.of(0.0f, 2.0f, -6.0f, -0.2182f, 0.0f, 0.0f));

		// cube_r1.
		head.addChild("cube_r1", ModelPartBuilder.create().uv(88, 59).mirrored().cuboid(-0.5095f, -2.211f, -0.6496f, 2.0f, 3.0f, 2.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.of(2.25f, 4.1027f, -5.534f, 1.0036f, 0.48f, -0.8727f));

		// cube_r2.
		head.addChild("cube_r2", ModelPartBuilder.create().uv(88, 59).cuboid(-1.7975f, -1.8508f, -0.9483f, 2.0f, 3.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-1.75f, 4.1027f, -5.534f, 1.0036f, -0.48f, 0.8727f));

		// cube_r3.
		head.addChild("cube_r3", ModelPartBuilder.create().uv(88, 54).cuboid(-1.5f, -1.75f, -0.75f, 3.0f, 3.0f, 1.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 1.8527f, -7.434f, -0.3927f, 0.0f, 0.0f));

		// head_r1.
		head.addChild("head_r1", ModelPartBuilder.create().uv(33, 0).mirrored().cuboid(-4.5f, -3.9804f, -4.3483f, 9.0f, 10.0f, 9.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.of(0.0f, -14.0f, 1.0f, -0.1745f, 0.0f, 0.0f));

		// head_r2.
		head.addChild("head_r2", ModelPartBuilder.create().uv(40, 53).mirrored().cuboid(-3.0f, 2.8551f, 0.3492f, 7.0f, 6.0f, 5.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.of(-0.5f, -5.0f, 0.0f, -1.0036f, 0.0f, 0.0f));

		// nose.
		ModelPartData nose = head.addChild("nose", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, -3.1927f, 0.7599f));

		// nose_r1.
		nose.addChild("nose_r1", ModelPartBuilder.create().uv(24, 0).mirrored().cuboid(-1.0f, -0.5896f, 1.4131f, 2.0f, 4.0f, 2.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.of(0.0f, -2.0f, -6.0f, -1.0036f, 0.0f, 0.0f));

		// body.
		modelPartData.addChild("body",
				ModelPartBuilder.create().uv(16, 20).mirrored().cuboid(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f, new Dilation(0.0f)).mirrored(false).uv(0, 38).mirrored().cuboid(-4.0f, 0.0f, -3.0f, 8.0f, 20.0f, 6.0f, new Dilation(0.5f)).mirrored(false),
				ModelTransform.of(0.0f, 0.0f, -3.0f, 0.2182f, 0.0f, 0.0f));

		// leg0.
		modelPartData.addChild("leg0", ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.pivot(2.0f, 12.0f, 0.0f));

		// leg1.
		modelPartData.addChild("leg1", ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.pivot(-2.0f, 12.0f, 0.0f));

		// arm1.
		modelPartData.addChild("arm1", ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(-8.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.of(0.0f, 4.0f, -1.5f, 0.0436f, 0.0f, 0.0f));

		// arm2.
		modelPartData.addChild("arm2", ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(4.0f, -2.0f, -5.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.pivot(0.0f, 7.0f, -1.5f));

		// monsterarm1.
		ModelPartData monsterarm1 = modelPartData.addChild("monsterarm1", ModelPartBuilder.create().uv(30, 46).cuboid(-17.0f, -1.0f, -1.0f, 17.0f, 2.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-4.0f, 0.0f, -2.0f, 0.0f, 0.0f, 0.9599f));

		// cube_r4.
		monsterarm1.addChild("cube_r4", ModelPartBuilder.create().uv(34, 46).cuboid(-15.0f, -1.0f, -1.0f, 15.0f, 2.0f, 2.0f, new Dilation(-0.1f)), ModelTransform.of(-16.25f, 0.0f, 0.75f, 0.0f, -1.1345f, 0.0f));

		// monsterarm2
		ModelPartData monsterarm2 = modelPartData.addChild("monsterarm2", ModelPartBuilder.create().uv(30, 46).cuboid(-17.0f, -1.0f, -1.0f, 17.0f, 2.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-3.0f, -0.25f, -3.0f, 0.0f, 0.0f, -0.3927f));

		// cube_r5.
		monsterarm2.addChild("cube_r5", ModelPartBuilder.create().uv(34, 46).cuboid(-15.0f, -1.0f, -1.0f, 15.0f, 2.0f, 2.0f, new Dilation(-0.1f)), ModelTransform.of(-16.25f, 0.0f, 0.75f, 0.0f, -1.1345f, 0.0f));

		// monsterarm3.
		ModelPartData monsterarm3 = modelPartData.addChild("monsterarm3", ModelPartBuilder.create().uv(30, 46).cuboid(-17.0f, -1.0f, -1.0f, 17.0f, 2.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(4.0f, 0.0f, -2.0f, 0.0f, 0.0f, 2.1817f));

		// cube_r6.
		monsterarm3.addChild("cube_r6", ModelPartBuilder.create().uv(34, 46).cuboid(-15.0f, -1.0f, -1.0f, 15.0f, 2.0f, 2.0f, new Dilation(-0.1f)), ModelTransform.of(-16.25f, 0.0f, 0.75f, 0.0f, -1.1345f, 0.0f));

		// monsterarm4.
		ModelPartData monsterarm4 = modelPartData.addChild("monsterarm4", ModelPartBuilder.create().uv(30, 46).cuboid(-17.0f, -1.0f, -1.0f, 17.0f, 2.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(3.0f, -0.25f, -3.0f, 0.0f, 0.0f, -2.7489f));

		// cube_r7.
		monsterarm4.addChild("cube_r7", ModelPartBuilder.create().uv(30, 46).cuboid(-15.0f, -1.0f, -1.0f, 15.0f, 2.0f, 2.0f, new Dilation(-0.1f)), ModelTransform.of(-16.25f, 0.0f, 0.75f, 0.0f, -1.1345f, 0.0f));

		return TexturedModelData.of(modelData, 96, 64);
	}
}
