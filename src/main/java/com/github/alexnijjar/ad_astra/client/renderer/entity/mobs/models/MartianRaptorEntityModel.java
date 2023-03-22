package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models;

import com.github.alexnijjar.ad_astra.entities.mobs.MartianRaptorEntity;
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
public class MartianRaptorEntityModel extends EntityModel<MartianRaptorEntity> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("martian_raptor"), "main");

	private final ModelPart body;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public MartianRaptorEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
	}

	@Override
	public void setAngles(MartianRaptorEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.left_leg.pitch = MathHelper.cos(limbAngle) * -1.0f * limbDistance;
        this.right_leg.pitch = MathHelper.cos(limbAngle) * 1.0f * limbDistance;

        this.body.getChild("head").yaw = headYaw / (180f / (float) Math.PI);
        this.body.getChild("head").pitch = headPitch / (180f / (float) Math.PI);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertices, light, overlay);
		left_leg.render(matrices, vertices, light, overlay);
		right_leg.render(matrices, vertices, light, overlay);
	}

	@SuppressWarnings("unused")
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 65).cuboid(-2.5F, 7.733F, -5.1341F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(6.5F, 13.267F, 3.3841F));

        ModelPartData cube_r1 = left_leg.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(5.75F, 2.0F, -2.0F, 4.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-7.75F, -0.3977F, 4.635F, -0.3491F, 0.0F, 0.0F));

        ModelPartData cube_r2 = left_leg.addChild("cube_r2", ModelPartBuilder.create().uv(68, 35).cuboid(5.75F, 7.5F, 0.9F, 4.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(50, 39).cuboid(5.25F, 2.5F, -4.0F, 5.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-7.75F, -1.7831F, 3.2889F, -0.7418F, 0.0F, 0.0F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 65).cuboid(-2.5F, 7.733F, -5.1341F, 5.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.5F, 13.267F, 3.3841F));

        ModelPartData cube_r3 = right_leg.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).cuboid(5.75F, 2.0F, -2.0F, 4.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-7.75F, -0.3977F, 4.635F, -0.3491F, 0.0F, 0.0F));

        ModelPartData cube_r4 = right_leg.addChild("cube_r4", ModelPartBuilder.create().uv(68, 35).cuboid(5.75F, 7.5F, 0.9F, 4.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(50, 39).cuboid(5.25F, 2.5F, -4.0F, 5.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-7.75F, -1.7831F, 3.2889F, -0.7418F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -5.0F, -10.0F, 8.0F, 9.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

        ModelPartData cube_r5 = body.addChild("cube_r5", ModelPartBuilder.create().uv(28, 29).cuboid(-9.5F, 1.5F, -7.0F, 5.0F, 9.0F, 9.0F, new Dilation(0.0F))
                .uv(1, 24).cuboid(-3.5F, -4.5F, -7.0F, 9.0F, 5.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(3.5F, -4.5F, 0.0F, 0.2519F, -0.2443F, 0.7543F));

        ModelPartData Tail = body.addChild("Tail", ModelPartBuilder.create().uv(42, 52).cuboid(-1.25F, 3.3318F, 6.4959F, 4.0F, 4.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 25).cuboid(0.75F, -0.6682F, 9.4959F, 0.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.75F, -0.3318F, 6.5041F));

        ModelPartData cube_r6 = Tail.addChild("cube_r6", ModelPartBuilder.create().uv(0, 18).cuboid(0.75F, -7.5F, -4.0F, 0.0F, 6.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 39).cuboid(-2.75F, -1.5F, -5.0F, 7.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7418F, 0.0F, 0.0F));

        ModelPartData cube_r7 = Tail.addChild("cube_r7", ModelPartBuilder.create().uv(28, 19).cuboid(0.75F, -5.0F, 4.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.0F))
                .uv(22, 47).cuboid(-2.25F, 0.0F, 1.0F, 6.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        ModelPartData cube_r8 = Tail.addChild("cube_r8", ModelPartBuilder.create().uv(0, 4).cuboid(0.0F, -5.2F, -3.25F, 0.0F, 3.0F, 6.0F, new Dilation(0.0F))
                .uv(36, 14).cuboid(-4.5F, -0.2F, 1.75F, 9.0F, 0.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 53).cuboid(-1.5F, -2.2F, -4.25F, 3.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.75F, 5.3318F, 18.4959F, 0.1745F, 0.0F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 7.0F, -4.0F));

        ModelPartData cube_r9 = head.addChild("cube_r9", ModelPartBuilder.create().uv(16, 60).cuboid(1.5F, -14.0F, -2.0F, 4.0F, 4.0F, 6.0F, new Dilation(0.0F))
                .uv(60, 58).cuboid(-5.5F, -14.0F, -2.0F, 4.0F, 4.0F, 6.0F, new Dilation(0.0F))
                .uv(30, 0).cuboid(-4.5F, -13.0F, -3.0F, 9.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData cube_r10 = head.addChild("cube_r10", ModelPartBuilder.create().uv(36, 64).cuboid(-3.5F, -13.0F, -7.75F, 4.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.25F, 0.25F, 0.7974F, 0.1536F, 0.1555F));

        ModelPartData cube_r11 = head.addChild("cube_r11", ModelPartBuilder.create().uv(62, 9).cuboid(-0.5F, -13.0F, -7.75F, 4.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.25F, 0.25F, 0.7974F, -0.1536F, -0.1555F));

		return TexturedModelData.of(modelData, 128, 128);
	}
}
