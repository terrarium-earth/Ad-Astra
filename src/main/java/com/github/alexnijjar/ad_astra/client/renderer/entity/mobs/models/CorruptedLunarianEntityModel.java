package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models;

import com.github.alexnijjar.ad_astra.entities.mobs.CorruptedLunarianEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
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
		this.arm1 = root.getChild("arms").getChild("arm1");
        this.arm2 = root.getChild("arms").getChild("arm2");
        this.monsterarm1 = root.getChild("extra_arms").getChild("backarm1");
        this.monsterarm2 = root.getChild("extra_arms").getChild("backarm2");
        this.monsterarm3 = root.getChild("extra_arms").getChild("backarm3");
        this.monsterarm4 = root.getChild("extra_arms").getChild("backarm4");
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

	@SuppressWarnings("unused")
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 19).mirrored().cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 0).mirrored().cuboid(-4.5F, -18.0F, -4.5F, 9.0F, 10.0F, 9.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 20).mirrored().cuboid(-1.0F, -3.0F, -6.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData extra_arms = modelPartData.addChild("extra_arms", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 3.0F));

        ModelPartData backarm1 = extra_arms.addChild("backarm1", ModelPartBuilder.create(), ModelTransform.pivot(-1.2968F, 6.4078F, 0.3907F));

        ModelPartData body_r1 = backarm1.addChild("body_r1", ModelPartBuilder.create().uv(52, 0).cuboid(-11.5F, 0.0F, 9.5F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(36, 0).cuboid(-1.5F, 0.0F, -0.5F, 2.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.2968F, -0.4078F, 0.1093F, -0.6109F, -0.6109F, 0.0F));

        ModelPartData backarm2 = extra_arms.addChild("backarm2", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 6.0F, 1.0F));

        ModelPartData body_r2 = backarm2.addChild("body_r2", ModelPartBuilder.create().uv(52, 0).mirrored().cuboid(2.0F, 0.0F, 9.5F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(36, 0).cuboid(0.0F, 0.0F, -0.5F, 2.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 0.0F, -0.5F, -0.6109F, 0.6981F, 0.0F));

        ModelPartData backarm3 = extra_arms.addChild("backarm3", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 4.0F, 1.0F));

        ModelPartData body_r3 = backarm3.addChild("body_r3", ModelPartBuilder.create().uv(52, 0).cuboid(-11.5F, -2.0F, 9.5F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(36, 0).cuboid(-1.5F, -2.0F, -0.5F, 2.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -0.5F, 0.6109F, -0.6109F, 0.0F));

        ModelPartData backarm4 = extra_arms.addChild("backarm4", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 4.0F, 0.0F));

        ModelPartData body_r4 = backarm4.addChild("body_r4", ModelPartBuilder.create().uv(52, 0).mirrored().cuboid(2.0F, -2.0F, 9.5F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(36, 0).cuboid(0.0F, -2.0F, -0.5F, 2.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 1.0F, 0.5F, 0.6109F, 0.6109F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(100, 0).mirrored().cuboid(0.0F, -12.0F, -5.0F, 8.0F, 12.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 36).mirrored().cuboid(0.0F, -12.0F, -5.0F, 8.0F, 19.0F, 6.0F, new Dilation(0.5F)).mirrored(false), ModelTransform.pivot(-4.0F, 12.0F, 2.0F));

        ModelPartData leg0 = modelPartData.addChild("leg0", ModelPartBuilder.create().uv(0, 81).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.0F, 12.0F, 0.0F));

        ModelPartData leg1 = modelPartData.addChild("leg1", ModelPartBuilder.create().uv(0, 81).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));

        ModelPartData arms = modelPartData.addChild("arms", ModelPartBuilder.create(), ModelTransform.of(0.0F, 2.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        ModelPartData arm1 = arms.addChild("arm1", ModelPartBuilder.create().uv(30, 61).mirrored().cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(6.0F, 0.1434F, 0.2048F));

        ModelPartData arm2 = arms.addChild("arm2", ModelPartBuilder.create().uv(30, 61).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.0F, 0.1434F, 0.2048F));

		return TexturedModelData.of(modelData, 128, 128);
	}
}
