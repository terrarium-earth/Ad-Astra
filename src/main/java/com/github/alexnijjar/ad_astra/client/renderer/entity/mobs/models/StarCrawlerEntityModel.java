package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models;

import com.github.alexnijjar.ad_astra.entities.mobs.StarCrawlerEntity;
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
public class StarCrawlerEntityModel extends EntityModel<StarCrawlerEntity> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("star_crawler"), "main");

	private final ModelPart body;
	private final ModelPart leg1;
	private final ModelPart leg2;
	private final ModelPart leg3;
	private final ModelPart leg4;

	public StarCrawlerEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
        this.leg4 = root.getChild("leg4");
	}

	@Override
	public void setAngles(StarCrawlerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.leg1.yaw = MathHelper.cos(limbAngle * 0.6662f) * limbDistance;
		this.leg2.yaw = MathHelper.cos(limbAngle * 0.6662f) * limbDistance;
		this.leg3.yaw = MathHelper.cos(limbAngle * 0.6662f) * limbDistance;
		this.leg4.yaw = MathHelper.cos(limbAngle * 0.6662f) * limbDistance;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertices, light, overlay);
		leg1.render(matrices, vertices, light, overlay);
		leg2.render(matrices, vertices, light, overlay);
		leg3.render(matrices, vertices, light, overlay);
		leg4.render(matrices, vertices, light, overlay);
	}

	@SuppressWarnings("unused")
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData leg1 = modelPartData.addChild("leg1", ModelPartBuilder.create().uv(0, 27).cuboid(-16.0F, -9.0F, -6.0F, 8.0F, 9.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 51).cuboid(-24.0F, -7.0F, -5.0F, 8.0F, 7.0F, 10.0F, new Dilation(0.0F))
                .uv(28, 61).cuboid(-29.0F, -5.0F, -4.0F, 5.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r1 = leg1.addChild("cube_r1", ModelPartBuilder.create().uv(51, 44).cuboid(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-16.0F, 0.804F, -5.8016F, 0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r2 = leg1.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -2.5F, -3.5F, 4.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-24.0F, 0.804F, -4.8016F, 0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r3 = leg1.addChild("cube_r3", ModelPartBuilder.create().uv(23, 32).cuboid(-4.0F, -2.5F, -3.5F, 4.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-24.0F, 1.3216F, 6.7334F, -0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r4 = leg1.addChild("cube_r4", ModelPartBuilder.create().uv(65, 44).cuboid(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-16.0F, 1.3216F, 7.7334F, -0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r5 = leg1.addChild("cube_r5", ModelPartBuilder.create().uv(21, 48).cuboid(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-9.0F, 1.3216F, 8.7334F, -0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r6 = leg1.addChild("cube_r6", ModelPartBuilder.create().uv(23, 27).cuboid(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-9.0F, 0.804F, -6.8016F, 0.2618F, 0.0F, 0.0F));

        ModelPartData leg2 = modelPartData.addChild("leg2", ModelPartBuilder.create().uv(40, 27).cuboid(-6.0F, -9.0F, 8.0F, 12.0F, 9.0F, 8.0F, new Dilation(0.0F))
                .uv(58, 53).cuboid(-5.0F, -7.0F, 16.0F, 10.0F, 7.0F, 8.0F, new Dilation(0.0F))
                .uv(54, 68).cuboid(-4.0F, -5.0F, 24.0F, 8.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r7 = leg2.addChild("cube_r7", ModelPartBuilder.create().uv(0, 23).cuboid(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-6.4146F, -1.352F, 24.5F, 0.0F, 0.0F, 1.309F));

        ModelPartData cube_r8 = leg2.addChild("cube_r8", ModelPartBuilder.create().uv(0, 26).cuboid(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(4.4827F, -1.8696F, 24.5F, 0.0F, 0.0F, 1.8326F));

        ModelPartData cube_r9 = leg2.addChild("cube_r9", ModelPartBuilder.create().uv(56, 42).cuboid(0.0F, -2.5F, -3.5F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(6.4486F, -1.6108F, 19.5F, 0.0F, 0.0F, 1.8326F));

        ModelPartData cube_r10 = leg2.addChild("cube_r10", ModelPartBuilder.create().uv(0, 5).cuboid(0.0F, -2.5F, -3.5F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-7.4146F, -1.352F, 19.5F, 0.0F, 0.0F, 1.309F));

        ModelPartData cube_r11 = leg2.addChild("cube_r11", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -2.5F, -3.5F, 0.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(8.4146F, -1.352F, 12.5F, 0.0F, 0.0F, 1.8326F));

        ModelPartData cube_r12 = leg2.addChild("cube_r12", ModelPartBuilder.create().uv(26, 67).cuboid(0.0F, -2.5F, -3.5F, 0.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-8.4146F, -1.352F, 12.5F, 0.0F, 0.0F, 1.309F));

        ModelPartData leg3 = modelPartData.addChild("leg3", ModelPartBuilder.create().uv(48, 0).cuboid(-5.0F, -7.0F, -24.0F, 10.0F, 7.0F, 8.0F, new Dilation(0.0F))
                .uv(64, 15).cuboid(-4.0F, -5.0F, -29.0F, 8.0F, 5.0F, 5.0F, new Dilation(0.0F))
                .uv(40, 27).cuboid(-6.0F, -9.0F, -16.0F, 12.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r13 = leg3.addChild("cube_r13", ModelPartBuilder.create().uv(0, 26).cuboid(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(4.4827F, -1.8696F, -27.5F, 0.0F, 0.0F, 1.8326F));

        ModelPartData cube_r14 = leg3.addChild("cube_r14", ModelPartBuilder.create().uv(56, 42).cuboid(0.0F, -2.5F, -3.5F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(6.4486F, -1.6108F, -19.5F, 0.0F, 0.0F, 1.8326F));

        ModelPartData cube_r15 = leg3.addChild("cube_r15", ModelPartBuilder.create().uv(26, 67).cuboid(0.0F, -2.5F, -3.5F, 0.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-8.4146F, -1.352F, -12.5F, 0.0F, 0.0F, 1.309F));

        ModelPartData cube_r16 = leg3.addChild("cube_r16", ModelPartBuilder.create().uv(0, 23).cuboid(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-6.4146F, -1.352F, -27.5F, 0.0F, 0.0F, 1.309F));

        ModelPartData cube_r17 = leg3.addChild("cube_r17", ModelPartBuilder.create().uv(0, 5).cuboid(0.0F, -2.5F, -3.5F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-7.4146F, -1.352F, -19.5F, 0.0F, 0.0F, 1.309F));

        ModelPartData cube_r18 = leg3.addChild("cube_r18", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -2.5F, -3.5F, 0.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(8.4146F, -1.352F, -11.5F, 0.0F, 0.0F, 1.8326F));

        ModelPartData leg4 = modelPartData.addChild("leg4", ModelPartBuilder.create().uv(30, 44).cuboid(16.0F, -7.0F, -5.0F, 8.0F, 7.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 68).cuboid(24.0F, -5.0F, -4.0F, 5.0F, 5.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 27).cuboid(8.0F, -9.0F, -6.0F, 8.0F, 9.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData cube_r19 = leg4.addChild("cube_r19", ModelPartBuilder.create().uv(23, 32).cuboid(-4.0F, -2.5F, -3.5F, 4.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(28.0F, 1.3216F, 6.7334F, -0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r20 = leg4.addChild("cube_r20", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -2.5F, -3.5F, 4.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(28.0F, 0.804F, -4.8016F, 0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r21 = leg4.addChild("cube_r21", ModelPartBuilder.create().uv(65, 44).cuboid(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(23.0F, 1.3216F, 7.7334F, -0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r22 = leg4.addChild("cube_r22", ModelPartBuilder.create().uv(51, 44).cuboid(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(23.0F, 0.804F, -5.8016F, 0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r23 = leg4.addChild("cube_r23", ModelPartBuilder.create().uv(21, 48).cuboid(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(16.0F, 1.3216F, 8.7334F, -0.2618F, 0.0F, 0.0F));

        ModelPartData cube_r24 = leg4.addChild("cube_r24", ModelPartBuilder.create().uv(23, 27).cuboid(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(16.0F, 0.804F, -6.8016F, 0.2618F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -11.0F, -8.0F, 16.0F, 11.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		return TexturedModelData.of(modelData, 128, 128);
	}
}
