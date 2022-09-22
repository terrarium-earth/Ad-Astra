package com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_3;

import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.VehicleEntityModel;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier3;
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
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public class RocketEntityModelTier3 extends VehicleEntityModel<RocketEntityTier3> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("tier_3_rocket"), "main");

	public RocketEntityModelTier3(ModelPart root) {
		super(root, "rocket");
	}

	@SuppressWarnings("unused")
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData rocket3 = modelPartData.addChild("rocket", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 25.0f, 0.0f));

		ModelPartData body = rocket3.addChild("body",
				ModelPartBuilder.create().uv(36, -18).cuboid(9.0f, -51.0f, -9.0f, 0.0f, 52.0f, 18.0f, new Dilation(0.0f)).uv(0, 0).cuboid(-9.0f, -51.0f, -9.0f, 18.0f, 52.0f, 0.0f, new Dilation(0.0f)).uv(80, 74).cuboid(-9.0f, -51.0f, -9.0f, 2.0f, 52.0f, 2.0f, new Dilation(0.0f)).uv(36, 0).cuboid(-9.0f, -51.0f, 9.0f, 18.0f, 52.0f, 0.0f, new Dilation(0.0f)).uv(36, -18).cuboid(-9.0f, -51.0f, -9.0f, 0.0f, 52.0f, 18.0f, new Dilation(0.0f)).uv(-18, 99).cuboid(-9.0f, 1.0f, -9.0f, 18.0f, 0.0f, 18.0f, new Dilation(0.0f)).uv(-18, 99).cuboid(-9.0f, -51.0f, -9.0f, 18.0f, 0.0f, 18.0f, new Dilation(0.0f)).uv(0, 52).cuboid(-10.0f, -11.0f, -10.0f, 20.0f, 11.0f, 0.0f, new Dilation(0.0f)).uv(0, 32).cuboid(10.0f, -11.0f, -10.0f, 0.0f, 11.0f, 20.0f, new Dilation(0.0f)).uv(0, 52).cuboid(-10.0f, -11.0f, 10.0f, 20.0f, 11.0f, 0.0f, new Dilation(0.0f)).uv(0, 32).cuboid(-10.0f, -11.0f, -10.0f, 0.0f, 11.0f, 20.0f, new Dilation(0.0f)).uv(0, 65).cuboid(-10.0f, -26.0f, -10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(0, 65)
						.cuboid(-10.0f, -32.0f, -10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(0, 43).cuboid(-10.0f, -26.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(0, 43).cuboid(-10.0f, -32.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(88, 83).cuboid(-10.0f, -44.0f, -10.0f, 20.0f, 7.0f, 0.0f, new Dilation(0.0f)).uv(88, 63).cuboid(-10.0f, -44.0f, -10.0f, 0.0f, 7.0f, 20.0f, new Dilation(0.0f)).uv(40, 52).cuboid(-6.0f, -34.0f, -10.0f, 12.0f, 12.0f, 1.0f, new Dilation(0.0f)).uv(0, 43).cuboid(10.0f, -32.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(0, 43).cuboid(10.0f, -26.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(88, 63).cuboid(10.0f, -44.0f, -10.0f, 0.0f, 7.0f, 20.0f, new Dilation(0.0f)).uv(88, 83).cuboid(-10.0f, -44.0f, 10.0f, 20.0f, 7.0f, 0.0f, new Dilation(0.0f)).uv(0, 63).cuboid(-10.0f, -32.0f, 10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(0, 63).cuboid(-10.0f, -26.0f, 10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(39, 65)
						.cuboid(-4.0f, -24.0f, -10.0f, 8.0f, 0.0f, 1.0f, new Dilation(0.0f)).uv(66, 51).cuboid(-4.0f, -32.0f, -10.0f, 0.0f, 8.0f, 1.0f, new Dilation(0.0f)).uv(39, 65).cuboid(-4.0f, -32.0f, -10.0f, 8.0f, 0.0f, 1.0f, new Dilation(0.0f)).uv(66, 51).cuboid(4.0f, -32.0f, -10.0f, 0.0f, 8.0f, 1.0f, new Dilation(0.0f)),
				ModelTransform.pivot(0.0f, -9.0f, 0.0f));

		ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(96, 9).cuboid(-15.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -3.0f, 0.0f, 0.0f, 1.5708f, 0.0f));

		ModelPartData cube_r2 = body.addChild("cube_r2", ModelPartBuilder.create().uv(96, 9).cuboid(-15.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -3.0f, 0.0f, 0.0f, -1.5708f, 0.0f));

		ModelPartData body_r1 = body.addChild("body_r1", ModelPartBuilder.create().uv(80, 74).cuboid(-1.0f, -26.0f, -1.0f, 2.0f, 52.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-8.0f, -25.0f, 8.0f, 0.0f, 1.5708f, 0.0f));

		ModelPartData body_r2 = body.addChild("body_r2", ModelPartBuilder.create().uv(80, 74).cuboid(-1.0f, -26.0f, -1.0f, 2.0f, 52.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(8.0f, -25.0f, 8.0f, 0.0f, 3.1416f, 0.0f));

		ModelPartData body_r3 = body.addChild("body_r3", ModelPartBuilder.create().uv(80, 74).cuboid(-1.0f, -26.0f, -1.0f, 2.0f, 52.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(8.0f, -25.0f, -8.0f, 0.0f, -1.5708f, 0.0f));

		ModelPartData bottom = rocket3.addChild("bottom", ModelPartBuilder.create().uv(0, 117).cuboid(-7.0f, -12.0f, -7.0f, 14.0f, 3.0f, 0.0f, new Dilation(0.0f)).uv(0, 103).cuboid(-7.0f, -12.0f, -7.0f, 0.0f, 3.0f, 14.0f, new Dilation(0.0f)).uv(0, 117).cuboid(-7.0f, -12.0f, 7.0f, 14.0f, 3.0f, 0.0f, new Dilation(0.0f)).uv(0, 103).cuboid(7.0f, -12.0f, -7.0f, 0.0f, 3.0f, 14.0f, new Dilation(0.0f)).uv(18, 92).cuboid(-9.0f, -9.0f, -9.0f, 18.0f, 0.0f, 18.0f, new Dilation(0.0f)).uv(0, 120).cuboid(-9.0f, -9.0f, -9.0f, 18.0f, 4.0f, 0.0f, new Dilation(0.0f)).uv(0, 102).cuboid(9.0f, -9.0f, -9.0f, 0.0f, 4.0f, 18.0f, new Dilation(0.0f)).uv(0, 102).cuboid(-9.0f, -9.0f, -9.0f, 0.0f, 4.0f, 18.0f, new Dilation(0.0f)).uv(0, 120).cuboid(-9.0f, -9.0f, 9.0f, 18.0f, 4.0f, 0.0f, new Dilation(0.0f)).uv(18, 110).cuboid(-9.0f, -5.0f, -9.0f, 18.0f, 0.0f, 18.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 4.0f, 0.0f));

		ModelPartData top = rocket3.addChild("top", ModelPartBuilder.create().uv(72, 18).cuboid(-1.0f, -1.5f, -1.0f, 2.0f, 14.0f, 2.0f, new Dilation(0.0f)).uv(16, 87).cuboid(-2.0f, -5.5f, -2.0f, 4.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(0, 43).cuboid(10.0f, 41.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(0, 63).cuboid(-10.0f, 41.0f, -10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(0, 63).cuboid(-10.0f, 41.0f, 10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(0, 43).cuboid(-10.0f, 41.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(52, 67).cuboid(-5.0f, 26.0f, 6.0f, 10.0f, 9.0f, 0.0f, new Dilation(0.0f)).uv(52, 57).cuboid(-6.0f, 26.0f, -5.0f, 0.0f, 9.0f, 10.0f, new Dilation(0.0f)).uv(52, 67).cuboid(-5.0f, 26.0f, -6.0f, 10.0f, 9.0f, 0.0f, new Dilation(0.0f)).uv(52, 57).cuboid(6.0f, 26.0f, -5.0f, 0.0f, 9.0f, 10.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, -102.5f, 0.0f));

		ModelPartData cube_r3 = top.addChild("cube_r3", ModelPartBuilder.create().uv(16, 67).cuboid(0.0f, -10.0f, -5.0f, 0.0f, 10.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(-6.0f, 26.0f, 0.0f, 0.0f, 0.0f, 0.3491f));

		ModelPartData cube_r4 = top.addChild("cube_r4", ModelPartBuilder.create().uv(16, 67).cuboid(0.0f, -10.0f, -5.0f, 0.0f, 10.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(6.0f, 26.0f, 0.0f, 0.0f, 0.0f, -0.3491f));

		ModelPartData cube_r5 = top.addChild("cube_r5", ModelPartBuilder.create().uv(16, 77).cuboid(-5.0f, -10.0f, 0.0f, 10.0f, 10.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 26.0f, -6.0f, -0.3491f, 0.0f, 0.0f));

		ModelPartData cube_r6 = top.addChild("cube_r6", ModelPartBuilder.create().uv(16, 77).cuboid(-5.0f, -10.0f, 0.0f, 10.0f, 10.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 26.0f, 6.0f, 0.3491f, 0.0f, 0.0f));

		ModelPartData body_r4 = top.addChild("body_r4", ModelPartBuilder.create().uv(16, 67).cuboid(-9.0f, -10.0f, 0.25f, 18.0f, 10.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 42.5f, -9.3f, -0.3491f, 0.0f, 0.0f));

		ModelPartData body_r5 = top.addChild("body_r5", ModelPartBuilder.create().uv(16, 49).cuboid(0.0f, -10.0f, -9.0f, 0.0f, 10.0f, 18.0f, new Dilation(0.0f)), ModelTransform.of(-9.0f, 42.5f, 0.0f, 0.0f, 0.0f, 0.3491f));

		ModelPartData body_r6 = top.addChild("body_r6", ModelPartBuilder.create().uv(16, 67).cuboid(-9.0f, -10.0f, 0.0f, 18.0f, 10.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 42.5f, 9.0f, 0.3491f, 0.0f, 0.0f));

		ModelPartData body_r7 = top.addChild("body_r7", ModelPartBuilder.create().uv(16, 49).cuboid(0.0f, -10.0f, -9.0f, 0.0f, 10.0f, 18.0f, new Dilation(0.0f)), ModelTransform.of(9.0f, 42.5f, -0.3f, 0.0f, 0.0f, -0.3491f));

		ModelPartData body_r8 = top.addChild("body_r8", ModelPartBuilder.create().uv(98, 31).cuboid(-1.0f, -16.0f, -2.0607f, 2.0f, 16.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-6.4571f, 26.5f, 6.4571f, 0.4363f, -0.7854f, 0.0f));

		ModelPartData body_r9 = top.addChild("body_r9", ModelPartBuilder.create().uv(76, 54).cuboid(-1.0f, -0.5f, 0.0f, 2.0f, 8.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-5.0f, 27.0f, 5.0f, 0.0f, -0.7854f, 0.0f));

		ModelPartData body_r10 = top.addChild("body_r10", ModelPartBuilder.create().uv(98, 31).cuboid(-1.0f, -16.0f, 0.0607f, 2.0f, 16.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-6.4571f, 26.5f, -6.4571f, -0.4363f, 0.7854f, 0.0f));

		ModelPartData body_r11 = top.addChild("body_r11", ModelPartBuilder.create().uv(76, 54).cuboid(-1.0f, -0.5f, -2.0f, 2.0f, 8.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-5.0f, 27.0f, -5.0f, 0.0f, 0.7854f, 0.0f));

		ModelPartData body_r12 = top.addChild("body_r12", ModelPartBuilder.create().uv(98, 31).cuboid(-2.0607f, -16.0f, -1.0f, 2.0f, 16.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(6.4571f, 26.5f, -6.4571f, -0.3999f, 0.6956f, -0.583f));

		ModelPartData body_r13 = top.addChild("body_r13", ModelPartBuilder.create().uv(76, 54).cuboid(0.0f, -0.5f, -1.0f, 2.0f, 8.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(5.0f, 27.0f, -5.0f, 0.0f, 0.7854f, 0.0f));

		ModelPartData body_r14 = top.addChild("body_r14", ModelPartBuilder.create().uv(98, 31).cuboid(-2.0607f, -16.0f, -1.0f, 2.0f, 16.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(6.4571f, 26.5f, 6.4571f, 0.3999f, -0.6956f, -0.583f));

		ModelPartData body_r15 = top.addChild("body_r15", ModelPartBuilder.create().uv(76, 54).cuboid(0.0f, -0.5f, -1.0f, 2.0f, 8.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(5.0f, 27.0f, 5.0f, 0.0f, -0.7854f, 0.0f));

		ModelPartData body_r16 = top.addChild("body_r16", ModelPartBuilder.create().uv(68, 54).cuboid(-1.0f, -10.0f, -1.0f, 2.0f, 9.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(9.0f, 42.5f, -9.0f, -0.4326f, 0.678f, -0.6346f));

		ModelPartData body_r17 = top.addChild("body_r17", ModelPartBuilder.create().uv(68, 54).cuboid(-1.0f, -10.0f, -1.0f, 2.0f, 9.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-9.0f, 42.5f, -9.0f, -0.48f, 0.7854f, 0.0f));

		ModelPartData body_r18 = top.addChild("body_r18", ModelPartBuilder.create().uv(68, 54).cuboid(-1.0f, -10.0f, -1.0f, 2.0f, 9.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-9.0f, 42.5f, 9.0f, 0.4326f, 0.678f, 0.6346f));

		ModelPartData cube_r7 = top.addChild("cube_r7", ModelPartBuilder.create().uv(80, 17).cuboid(-2.0f, -6.5f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)).uv(80, 0).cuboid(-3.0f, -1.5f, -3.0f, 6.0f, 11.0f, 6.0f, new Dilation(0.0f)).uv(80, 17).cuboid(-2.0f, -3.5f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 12.0f, 0.0f, 0.0f, -0.7854f, 0.0f));

		ModelPartData body_r19 = top.addChild("body_r19", ModelPartBuilder.create().uv(68, 54).cuboid(-1.0f, -10.0f, -1.0f, 2.0f, 9.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(9.0f, 42.5f, 9.0f, 0.48f, 0.7854f, 0.0f));

		ModelPartData fins = rocket3.addChild("fins", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, -10.0f, 0.0f));

		ModelPartData cube_r8 = fins.addChild("cube_r8", ModelPartBuilder.create().uv(0, 67).cuboid(-2.0f, 5.0f, 23.8284f, 4.0f, 28.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, -24.0f, -1.0f, 0.0f, 0.7854f, 0.0f));

		ModelPartData cube_r9 = fins.addChild("cube_r9", ModelPartBuilder.create().uv(0, 67).cuboid(-0.5858f, 5.0f, 22.4142f, 4.0f, 28.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, -24.0f, -1.0f, 0.0f, -0.7854f, 0.0f));

		ModelPartData cube_r10 = fins.addChild("cube_r10", ModelPartBuilder.create().uv(0, 67).cuboid(-2.0f, 5.0f, 21.0f, 4.0f, 28.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, -24.0f, -1.0f, 0.0f, -2.3562f, 0.0f));

		ModelPartData cube_r11 = fins.addChild("cube_r11", ModelPartBuilder.create().uv(0, 67).cuboid(-3.4142f, 5.0f, 22.4142f, 4.0f, 28.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, -24.0f, -1.0f, 0.0f, 2.3562f, 0.0f));

		ModelPartData cube_r12 = fins.addChild("cube_r12", ModelPartBuilder.create().uv(72, 21).cuboid(0.0f, 4.9167f, -0.6883f, 0.0f, 20.0f, 13.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -10.0f, 1.0f, 1.0472f, 0.7854f, 0.0f));

		ModelPartData cube_r13 = fins.addChild("cube_r13", ModelPartBuilder.create().uv(72, 21).cuboid(1.4142f, 6.1984f, -0.0906f, 0.0f, 20.0f, 13.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -10.0f, 1.0f, -2.0944f, 0.7854f, 3.1416f));

		ModelPartData cube_r14 = fins.addChild("cube_r14", ModelPartBuilder.create().uv(72, 21).cuboid(0.0f, 7.4801f, 0.507f, 0.0f, 20.0f, 13.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -10.0f, 1.0f, -2.0944f, -0.7854f, 3.1416f));

		ModelPartData cube_r15 = fins.addChild("cube_r15", ModelPartBuilder.create().uv(72, 21).cuboid(-1.4142f, 6.1984f, -0.0906f, 0.0f, 20.0f, 13.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -10.0f, 1.0f, 1.0472f, -0.7854f, 0.0f));

		ModelPartData cube_r16 = fins.addChild("cube_r16", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 12.9801f, 1.507f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -7.0f, 1.0f, -2.0944f, -0.7854f, 3.1416f));

		ModelPartData cube_r17 = fins.addChild("cube_r17", ModelPartBuilder.create().uv(72, 0).cuboid(-2.4142f, 11.6984f, 0.9094f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -7.0f, 1.0f, 1.0472f, -0.7854f, 0.0f));

		ModelPartData cube_r18 = fins.addChild("cube_r18", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 10.4167f, 0.3117f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -7.0f, 1.0f, 1.0472f, 0.7854f, 0.0f));

		ModelPartData cube_r19 = fins.addChild("cube_r19", ModelPartBuilder.create().uv(72, 0).cuboid(0.4142f, 11.6984f, 0.9094f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -7.0f, 1.0f, -2.0944f, 0.7854f, 3.1416f));

		ModelPartData cube_r20 = fins.addChild("cube_r20", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 10.4167f, 0.3117f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -15.0f, 1.0f, 1.0472f, 0.7854f, 0.0f));

		ModelPartData cube_r21 = fins.addChild("cube_r21", ModelPartBuilder.create().uv(72, 0).cuboid(-2.4142f, 11.6984f, 0.9094f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -15.0f, 1.0f, 1.0472f, -0.7854f, 0.0f));

		ModelPartData cube_r22 = fins.addChild("cube_r22", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 12.9801f, 1.507f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -15.0f, 1.0f, -2.0944f, -0.7854f, 3.1416f));

		ModelPartData cube_r23 = fins.addChild("cube_r23", ModelPartBuilder.create().uv(72, 0).cuboid(0.4142f, 11.6984f, 0.9094f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -15.0f, 1.0f, -2.0944f, 0.7854f, 3.1416f));

		ModelPartData cube_r24 = fins.addChild("cube_r24", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 12.9801f, 1.507f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -23.0f, 1.0f, -2.0944f, -0.7854f, 3.1416f));

		ModelPartData cube_r25 = fins.addChild("cube_r25", ModelPartBuilder.create().uv(72, 0).cuboid(0.4142f, 11.6984f, 0.9094f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -23.0f, 1.0f, -2.0944f, 0.7854f, 3.1416f));

		ModelPartData cube_r26 = fins.addChild("cube_r26", ModelPartBuilder.create().uv(72, 0).cuboid(-2.4142f, 11.6984f, 0.9094f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -23.0f, 1.0f, 1.0472f, -0.7854f, 0.0f));

		ModelPartData cube_r27 = fins.addChild("cube_r27", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 10.4167f, 0.3117f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -23.0f, 1.0f, 1.0472f, 0.7854f, 0.0f));

		ModelPartData boosters = rocket3.addChild("boosters", ModelPartBuilder.create().uv(104, 90).cuboid(-19.0f, -39.0f, -3.0f, 6.0f, 6.0f, 6.0f, new Dilation(0.0f)).uv(96, 49).mirrored().cuboid(-20.0f, -33.0f, -4.0f, 8.0f, 26.0f, 8.0f, new Dilation(0.0f)).mirrored(false).uv(32, 79).mirrored().cuboid(-20.0f, -5.0f, -4.0f, 8.0f, 5.0f, 8.0f, new Dilation(0.0f)).mirrored(false).uv(72, 64).mirrored().cuboid(-19.0f, -7.0f, -3.0f, 6.0f, 2.0f, 6.0f, new Dilation(0.0f)).mirrored(false).uv(110, 32).cuboid(-12.0f, -17.0f, -2.0f, 2.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(110, 32).cuboid(-12.0f, -27.0f, -2.0f, 2.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(104, 90).cuboid(11.0f, -39.0f, -3.0f, 6.0f, 6.0f, 6.0f, new Dilation(0.0f)).uv(96, 49).cuboid(10.0f, -33.0f, -4.0f, 8.0f, 26.0f, 8.0f, new Dilation(0.0f)).uv(72, 64).cuboid(11.0f, -7.0f, -3.0f, 6.0f, 2.0f, 6.0f, new Dilation(0.0f)).uv(32, 79).cuboid(10.0f, -5.0f, -4.0f, 8.0f, 5.0f, 8.0f, new Dilation(0.0f)).uv(110, 32)
				.cuboid(8.0f, -27.0f, -2.0f, 2.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(110, 32).cuboid(8.0f, -17.0f, -2.0f, 2.0f, 4.0f, 4.0f, new Dilation(0.0f)), ModelTransform.pivot(1.0f, -3.0f, 0.0f));
		return TexturedModelData.of(modelData, 128, 128);
	}
}
