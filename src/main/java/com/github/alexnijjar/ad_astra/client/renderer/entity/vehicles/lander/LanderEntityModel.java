package com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.lander;

import com.github.alexnijjar.ad_astra.entities.vehicles.LanderEntity;
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

@Environment(EnvType.CLIENT)
public class LanderEntityModel extends EntityModel<LanderEntity> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("lander"), "main");
	private final ModelPart leg1;
	private final ModelPart leg2;
	private final ModelPart leg3;
	private final ModelPart leg4;
	private final ModelPart bb_main;

	public LanderEntityModel(ModelPart root) {
		this.leg1 = root.getChild("leg1");
		this.leg2 = root.getChild("leg2");
		this.leg3 = root.getChild("leg3");
		this.leg4 = root.getChild("leg4");
		this.bb_main = root.getChild("bb_main");
	}

	@SuppressWarnings("unused")
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData leg1 = modelPartData.addChild("leg1", ModelPartBuilder.create().uv(0, 0).cuboid(-21.0f, 3.0f, 11.0f, 10.0f, 1.0f, 10.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 20.0f, 0.0f));

		ModelPartData cube_r1 = leg1.addChild("cube_r1", ModelPartBuilder.create().uv(0, 11).cuboid(-21.0f, 0.99f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 0.7854f, 0.0f));

		ModelPartData cube_r2 = leg1.addChild("cube_r2", ModelPartBuilder.create().uv(0, 31).cuboid(-2.0f, -2.0f, 18.0f, 4.0f, 4.0f, 3.0f, new Dilation(0.0f)).uv(16, 11).cuboid(-3.0f, -3.0f, 14.0f, 6.0f, 6.0f, 5.0f, new Dilation(0.0f)),
				ModelTransform.of(0.0f, 0.0f, 0.0f, 0.6109f, -0.7854f, 0.0f));

		ModelPartData cube_r3 = leg1.addChild("cube_r3", ModelPartBuilder.create().uv(48, 46).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 12.0f, 3.0f, new Dilation(-0.5f)).uv(0, 17).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 11.0f, 3.0f, new Dilation(0.0f)),
				ModelTransform.of(0.0f, 0.0f, 0.0f, 0.3927f, -0.7854f, 0.0f));

		ModelPartData leg2 = modelPartData.addChild("leg2", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 20.0f, 0.0f));

		ModelPartData cube_r4 = leg2.addChild("cube_r4", ModelPartBuilder.create().uv(16, 11).cuboid(-3.0f, -3.0f, 14.0f, 6.0f, 6.0f, 5.0f, new Dilation(0.0f)).uv(0, 31).cuboid(-2.0f, -2.0f, 18.0f, 4.0f, 4.0f, 3.0f, new Dilation(0.0f)),
				ModelTransform.of(0.0f, 0.0f, 0.0f, 0.6109f, -2.3562f, 0.0f));

		ModelPartData cube_r5 = leg2.addChild("cube_r5", ModelPartBuilder.create().uv(48, 46).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 12.0f, 3.0f, new Dilation(-0.5f)).uv(0, 17).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 11.0f, 3.0f, new Dilation(0.0f)),
				ModelTransform.of(0.0f, 0.0f, 0.0f, 0.3927f, -2.3562f, 0.0f));

		ModelPartData cube_r6 = leg2.addChild("cube_r6", ModelPartBuilder.create().uv(0, 0).cuboid(-21.0f, 3.0f, 11.0f, 10.0f, 1.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, -1.5708f, 0.0f));

		ModelPartData cube_r7 = leg2.addChild("cube_r7", ModelPartBuilder.create().uv(0, 11).cuboid(-21.0f, 0.99f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, -0.7854f, 0.0f));

		ModelPartData leg3 = modelPartData.addChild("leg3", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 20.0f, 0.0f));

		ModelPartData cube_r8 = leg3.addChild("cube_r8", ModelPartBuilder.create().uv(0, 31).cuboid(-2.0f, -2.0f, 18.0f, 4.0f, 4.0f, 3.0f, new Dilation(0.0f)).uv(16, 11).cuboid(-3.0f, -3.0f, 14.0f, 6.0f, 6.0f, 5.0f, new Dilation(0.0f)),
				ModelTransform.of(0.0f, 0.0f, 0.0f, 0.6109f, 2.3562f, 0.0f));

		ModelPartData cube_r9 = leg3.addChild("cube_r9", ModelPartBuilder.create().uv(48, 46).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 12.0f, 3.0f, new Dilation(-0.5f)).uv(0, 17).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 11.0f, 3.0f, new Dilation(0.0f)),
				ModelTransform.of(0.0f, 0.0f, 0.0f, 0.3927f, 2.3562f, 0.0f));

		ModelPartData cube_r10 = leg3.addChild("cube_r10", ModelPartBuilder.create().uv(0, 0).cuboid(-21.0f, 3.0f, 11.0f, 10.0f, 1.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 3.1416f, 0.0f));

		ModelPartData cube_r11 = leg3.addChild("cube_r11", ModelPartBuilder.create().uv(0, 11).cuboid(-21.0f, 0.99f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, -2.3562f, 0.0f));

		ModelPartData leg4 = modelPartData.addChild("leg4", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 20.0f, 0.0f));

		ModelPartData cube_r12 = leg4.addChild("cube_r12", ModelPartBuilder.create().uv(0, 31).cuboid(-2.0f, -2.0f, 18.0f, 4.0f, 4.0f, 3.0f, new Dilation(0.0f)).uv(16, 11).cuboid(-3.0f, -3.0f, 14.0f, 6.0f, 6.0f, 5.0f, new Dilation(0.0f)),
				ModelTransform.of(0.0f, 0.0f, 0.0f, 0.6109f, 0.7854f, 0.0f));

		ModelPartData cube_r13 = leg4.addChild("cube_r13", ModelPartBuilder.create().uv(48, 46).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 12.0f, 3.0f, new Dilation(-0.5f)).uv(0, 17).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 11.0f, 3.0f, new Dilation(0.0f)),
				ModelTransform.of(0.0f, 0.0f, 0.0f, 0.3927f, 0.7854f, 0.0f));

		ModelPartData cube_r14 = leg4.addChild("cube_r14", ModelPartBuilder.create().uv(0, 0).cuboid(-21.0f, 3.0f, 11.0f, 10.0f, 1.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 1.5708f, 0.0f));

		ModelPartData cube_r15 = leg4.addChild("cube_r15", ModelPartBuilder.create().uv(0, 11).cuboid(-21.0f, 0.99f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 2.3562f, 0.0f));

		ModelPartData bb_main = modelPartData.addChild("bb_main",
				ModelPartBuilder.create().uv(88, 0).cuboid(-5.0f, -19.0f, -8.0f, 10.0f, 3.0f, 10.0f, new Dilation(0.0f)).uv(0, 44).cuboid(-8.0f, -35.0f, -13.0f, 16.0f, 16.0f, 16.0f, new Dilation(0.0f)).uv(78, 46)
						.cuboid(8.0f, -30.5f, -8.5f, 2.0f, 10.0f, 10.0f, new Dilation(0.0f)).uv(0, 44).cuboid(1.0f, -33.0f, 14.0f, 3.0f, 9.0f, 3.0f, new Dilation(0.0f)).uv(0, 44).cuboid(-4.0f, -33.0f, 14.0f, 3.0f, 9.0f, 3.0f, new Dilation(0.0f)).uv(88, 18)
						.cuboid(-6.0f, -23.0f, -14.0f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)).uv(88, 18).cuboid(-6.0f, -31.0f, -16.0f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)).uv(64, 56).cuboid(10.0f, -28.5f, -6.5f, 1.0f, 6.0f, 6.0f, new Dilation(0.0f))
						.uv(42, 26).cuboid(-5.0f, -13.9f, -5.0f, 10.0f, 3.0f, 10.0f, new Dilation(0.0f)).uv(0, 26).cuboid(-7.0f, -10.9f, -7.0f, 14.0f, 4.0f, 14.0f, new Dilation(0.0f)).uv(16, 0)
						.cuboid(-12.0f, -16.0f, -12.0f, 24.0f, 2.0f, 24.0f, new Dilation(0.0f)).uv(82, 26).cuboid(-8.995f, -36.0f, -12.0f, 18.0f, 18.0f, 2.0f, new Dilation(0.0f)),
				ModelTransform.pivot(0.0f, 24.0f, 0.0f));

		ModelPartData cube_r16 = bb_main.addChild("cube_r16", ModelPartBuilder.create().uv(82, 26).cuboid(-9.0f, -9.0f, -1.0f, 18.0f, 18.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -33.0f, -5.0f, 1.5708f, 0.0f, -3.1416f));

		ModelPartData cube_r17 = bb_main.addChild("cube_r17", ModelPartBuilder.create().uv(64, 56).cuboid(9.5f, -10.5f, 0.5f, 1.0f, 6.0f, 6.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, -18.0f, 0.0f, 0.0f, 3.1416f, 0.0f));

		ModelPartData cube_r18 = bb_main.addChild("cube_r18", ModelPartBuilder.create().uv(64, 68).cuboid(-6.0f, -38.0f, 5.0f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)).uv(64, 68).cuboid(-6.0f, -38.0f, 0.0f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)),
				ModelTransform.of(0.0f, 0.0f, 0.0f, 0.2182f, 0.0f, 0.0f));

		ModelPartData cube_r19 = bb_main.addChild("cube_r19",
				ModelPartBuilder.create().uv(42, 76).mirrored().cuboid(-12.5f, -25.5f, -7.5f, 4.0f, 4.0f, 4.0f, new Dilation(0.0f)).mirrored(false).uv(54, 80).mirrored().cuboid(-12.5f, -10.5f, -7.5f, 4.0f, 4.0f, 4.0f, new Dilation(0.0f)).mirrored(false)
						.uv(54, 80).cuboid(9.5f, -10.5f, -7.5f, 4.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(42, 76).cuboid(9.5f, -25.5f, -7.5f, 4.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(0, 96)
						.cuboid(-11.5f, -24.5f, -6.5f, 24.0f, 17.0f, 2.0f, new Dilation(0.0f)),
				ModelTransform.of(-0.5f, -15.5f, 19.5f, 0.2182f, 0.0f, 0.0f));

		ModelPartData cube_r20 = bb_main.addChild("cube_r20", ModelPartBuilder.create().uv(56, 74).cuboid(-4.5f, -15.5f, -12.5f, 10.0f, 10.0f, 14.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, -17.5f, 15.5f, 0.2182f, 0.0f, 0.0f));

		ModelPartData cube_r21 = bb_main.addChild("cube_r21", ModelPartBuilder.create().uv(52, 98).cuboid(-9.5f, -21.5f, -6.5f, 20.0f, 13.0f, 1.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, -15.5f, 23.5f, 0.2182f, 0.0f, 0.0f));

		ModelPartData cube_r22 = bb_main.addChild("cube_r22", ModelPartBuilder.create().uv(102, 46).cuboid(-4.5f, -13.0f, 0.0f, 9.0f, 13.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -31.0f, -15.0f, 0.3054f, 0.0f, 0.0f));

		ModelPartData cube_r23 = bb_main.addChild("cube_r23", ModelPartBuilder.create().uv(48, 44).cuboid(-5.0f, -16.5f, -6.5f, 11.0f, 9.0f, 3.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, -15.5f, -3.5f, 0.3927f, 0.0f, 0.0f));

		ModelPartData cube_r24 = bb_main.addChild("cube_r24", ModelPartBuilder.create().uv(0, 76).cuboid(-6.5f, -19.5f, -8.5f, 14.0f, 3.0f, 14.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, -17.5f, 0.5f, 0.2182f, 0.0f, 0.0f));

		ModelPartData cube_r25 = bb_main.addChild("cube_r25", ModelPartBuilder.create().uv(78, 46).cuboid(-1.5f, -8.5f, -5.5f, 2.0f, 10.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(-9.5f, -22.0f, -4.0f, 0.0f, 3.1416f, 0.0f));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(LanderEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		leg1.render(matrices, vertices, light, overlay);
		leg2.render(matrices, vertices, light, overlay);
		leg3.render(matrices, vertices, light, overlay);
		leg4.render(matrices, vertices, light, overlay);
		bb_main.render(matrices, vertices, light, overlay);
	}
}
