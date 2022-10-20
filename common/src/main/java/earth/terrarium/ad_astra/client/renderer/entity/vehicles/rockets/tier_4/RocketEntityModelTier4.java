package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_4;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityModel;
import earth.terrarium.ad_astra.entities.vehicles.RocketEntityTier4;
import earth.terrarium.ad_astra.util.ModIdentifier;

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
public class RocketEntityModelTier4 extends VehicleEntityModel<RocketEntityTier4> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("tier_4_rocket"), "main");

	public RocketEntityModelTier4(ModelPart root) {
		super(root, "rocket");
	}

	@SuppressWarnings("unused")
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData rocket = modelPartData.addChild("rocket",
				ModelPartBuilder.create().uv(88, 80).cuboid(10.0f, -28.0f, -10.0f, 0.0f, 8.0f, 20.0f, new Dilation(0.0f)).uv(88, 100).cuboid(-10.0f, -28.0f, 10.0f, 20.0f, 8.0f, 0.0f, new Dilation(0.0f)).uv(88, 80).cuboid(-10.0f, -28.0f, -10.0f, 0.0f, 8.0f, 20.0f, new Dilation(0.0f)).uv(88, 100).cuboid(-10.0f, -28.0f, -10.0f, 20.0f, 8.0f, 0.0f, new Dilation(0.0f)).uv(88, 115).cuboid(-10.0f, -38.0f, -10.0f, 20.0f, 7.0f, 0.0f, new Dilation(0.0f)).uv(88, 95).cuboid(10.0f, -38.0f, -10.0f, 0.0f, 7.0f, 20.0f, new Dilation(0.0f)).uv(88, 115).cuboid(-10.0f, -38.0f, 10.0f, 20.0f, 7.0f, 0.0f, new Dilation(0.0f)).uv(88, 95).cuboid(-10.0f, -38.0f, -10.0f, 0.0f, 7.0f, 20.0f, new Dilation(0.0f)).uv(88, 108).cuboid(-10.0f, -63.0f, -10.0f, 20.0f, 7.0f, 0.0f, new Dilation(0.0f)).uv(88, 88).cuboid(10.0f, -63.0f, -10.0f, 0.0f, 7.0f, 20.0f, new Dilation(0.0f)).uv(88, 108).cuboid(-10.0f, -63.0f, 10.0f, 20.0f, 7.0f, 0.0f, new Dilation(0.0f)).uv(88, 88).cuboid(-10.0f, -63.0f, -10.0f, 0.0f, 7.0f, 20.0f, new Dilation(0.0f)),
				ModelTransform.pivot(0.0f, 24.0f, 0.0f));

		ModelPartData side_booster_right = rocket.addChild("side_booster_right", ModelPartBuilder.create().uv(104, 16).cuboid(-18.0f, -56.0f, -3.0f, 6.0f, 6.0f, 6.0f, new Dilation(0.0f)).uv(96, 28).mirrored().cuboid(-19.0f, -50.0f, -4.0f, 8.0f, 35.0f, 8.0f, new Dilation(0.0f)).mirrored(false).uv(16, 115).mirrored().cuboid(-19.0f, -13.0f, -4.0f, 8.0f, 5.0f, 8.0f, new Dilation(0.0f)).mirrored(false).uv(72, 30).mirrored().cuboid(-18.0f, -15.0f, -3.0f, 6.0f, 2.0f, 6.0f, new Dilation(0.0f)).mirrored(false), ModelTransform.pivot(-4.0f, 4.0f, 0.0f));

		ModelPartData side_booster_left = rocket.addChild("side_booster_left", ModelPartBuilder.create().uv(104, 16).cuboid(11.0f, -56.0f, -3.0f, 6.0f, 6.0f, 6.0f, new Dilation(0.0f)).uv(96, 28).cuboid(10.0f, -50.0f, -4.0f, 8.0f, 35.0f, 8.0f, new Dilation(0.0f)).uv(72, 30).cuboid(11.0f, -15.0f, -3.0f, 6.0f, 2.0f, 6.0f, new Dilation(0.0f)).uv(16, 115).cuboid(10.0f, -13.0f, -4.0f, 8.0f, 5.0f, 8.0f, new Dilation(0.0f)), ModelTransform.pivot(5.0f, 4.0f, 0.0f));

		ModelPartData cube_r1 = side_booster_left.addChild("cube_r1", ModelPartBuilder.create().uv(102, 124).cuboid(0.844f, -11.3692f, -1.0f, 11.0f, 2.0f, 2.0f, new Dilation(0.0f)).uv(102, 124).cuboid(-7.6412f, -19.8544f, -1.0f, 11.0f, 2.0f, 2.0f, new Dilation(0.0f)).uv(82, 85).cuboid(-5.3778f, -18.591f, 0.0f, 23.0f, 15.0f, 0.0f, new Dilation(0.0f)).uv(102, 124).cuboid(8.6222f, -3.591f, -1.0f, 11.0f, 2.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-5.0f, -31.75f, 0.0f, 0.0f, 0.0f, 0.7854f));

		ModelPartData cube_r2 = side_booster_left.addChild("cube_r2", ModelPartBuilder.create().uv(102, 124).cuboid(-11.844f, -11.3692f, -1.0f, 11.0f, 2.0f, 2.0f, new Dilation(0.0f)).uv(102, 124).cuboid(-3.3588f, -19.8544f, -1.0f, 11.0f, 2.0f, 2.0f, new Dilation(0.0f)).uv(82, 85).mirrored().cuboid(-17.6222f, -18.591f, 0.0f, 23.0f, 15.0f, 0.0f, new Dilation(0.0f)).mirrored(false).uv(102, 124).cuboid(-19.6222f, -3.591f, -1.0f, 11.0f, 2.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-5.0f, -31.75f, 0.0f, 0.0f, 0.0f, -0.7854f));

		ModelPartData main = rocket.addChild("main", ModelPartBuilder.create().uv(36, -18).cuboid(-9.0f, -74.0f, -9.0f, 0.0f, 59.0f, 18.0f, new Dilation(0.0f)).uv(36, -18).cuboid(9.0f, -74.0f, -9.0f, 0.0f, 59.0f, 18.0f, new Dilation(0.0f)).uv(36, 0).cuboid(-9.0f, -74.0f, 9.0f, 18.0f, 59.0f, 0.0f, new Dilation(0.0f)).uv(0, 0).cuboid(-9.0f, -74.0f, -9.0f, 18.0f, 59.0f, 0.0f, new Dilation(0.0f)).uv(73, 67).cuboid(-9.0f, -74.0f, -9.0f, 2.0f, 59.0f, 2.0f, new Dilation(0.0f)).uv(75, 69).cuboid(-9.0f, -74.0f, -1.0f, 2.0f, 59.0f, 0.0f, new Dilation(0.0f)).uv(75, 69).cuboid(7.0f, -74.0f, 1.0f, 2.0f, 59.0f, 0.0f, new Dilation(0.0f)).uv(75, 69).cuboid(7.0f, -74.0f, -1.0f, 2.0f, 59.0f, 0.0f, new Dilation(0.0f)).uv(75, 69).cuboid(-9.0f, -74.0f, 1.0f, 2.0f, 59.0f, 0.0f, new Dilation(0.0f)).uv(-18, 59).cuboid(-9.0f, -15.0f, -9.0f, 18.0f, 0.0f, 18.0f, new Dilation(0.0f)).uv(-18, 59).cuboid(-9.0f, -73.0f, -9.0f, 18.0f, 0.0f, 18.0f, new Dilation(0.0f)).uv(35, 72)
				.cuboid(-4.0f, -52.0f, -10.0f, 8.0f, 0.0f, 1.0f, new Dilation(0.0f)).uv(35, 72).cuboid(-4.0f, -44.0f, -10.0f, 8.0f, 0.0f, 1.0f, new Dilation(0.0f)).uv(62, 58).cuboid(-4.0f, -52.0f, -10.0f, 0.0f, 8.0f, 1.0f, new Dilation(0.0f)).uv(36, 59).cuboid(-6.0f, -54.0f, -10.0f, 12.0f, 12.0f, 1.0f, new Dilation(0.0f)).uv(62, 58).cuboid(4.0f, -52.0f, -10.0f, 0.0f, 8.0f, 1.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 0.0f, 0.0f));

		ModelPartData main_r1 = main.addChild("main_r1", ModelPartBuilder.create().uv(73, 67).cuboid(-1.0f, -29.5f, -1.0f, 2.0f, 59.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-8.0f, -44.5f, 8.0f, 0.0f, 1.5708f, 0.0f));

		ModelPartData main_r2 = main.addChild("main_r2", ModelPartBuilder.create().uv(73, 67).cuboid(-1.0f, -29.5f, -1.0f, 2.0f, 59.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(8.0f, -44.5f, 8.0f, 0.0f, 3.1416f, 0.0f));

		ModelPartData main_r3 = main.addChild("main_r3", ModelPartBuilder.create().uv(73, 67).cuboid(-1.0f, -29.5f, -1.0f, 2.0f, 59.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(8.0f, -44.5f, -8.0f, 0.0f, -1.5708f, 0.0f));

		ModelPartData main_r4 = main.addChild("main_r4", ModelPartBuilder.create().uv(75, 69).cuboid(-1.0f, -29.5f, -14.0f, 2.0f, 59.0f, 0.0f, new Dilation(0.0f)).uv(75, 69).cuboid(-1.0f, -29.5f, 0.0f, 2.0f, 59.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(-7.0f, -44.5f, 0.0f, 0.0f, -1.5708f, 0.0f));

		ModelPartData fins = main.addChild("fins", ModelPartBuilder.create(), ModelTransform.pivot(-2.0f, -8.0f, -2.0f));

		ModelPartData pyramid = main.addChild("pyramid", ModelPartBuilder.create().uv(73, 29).cuboid(-6.0f, -77.5f, -5.0f, 0.0f, 9.0f, 10.0f, new Dilation(0.0f)).uv(73, 29).cuboid(6.0f, -77.5f, -5.0f, 0.0f, 9.0f, 10.0f, new Dilation(0.0f)).uv(88, 102).cuboid(10.0f, -58.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(88, 102).cuboid(-10.0f, -58.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(88, 122).cuboid(-10.0f, -58.0f, 10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(88, 122).cuboid(-10.0f, -58.0f, -10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, -17.0f, 0.0f));

		ModelPartData cube_r3 = pyramid.addChild("cube_r3", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 2.6076f, -3.171f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -93.0f, 0.0f, -0.3491f, 0.7854f, 0.0f));

		ModelPartData cube_r4 = pyramid.addChild("cube_r4", ModelPartBuilder.create().uv(27, 77).cuboid(-8.0f, -21.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -66.0f, 0.0f, -2.8798f, -1.5708f, 3.1416f));

		ModelPartData cube_r5 = pyramid.addChild("cube_r5", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 2.6076f, -3.171f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -93.0f, 0.0f, -0.3491f, 2.3562f, 0.0f));

		ModelPartData cube_r6 = pyramid.addChild("cube_r6", ModelPartBuilder.create().uv(27, 77).cuboid(-8.0f, -21.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -66.0f, 0.0f, -2.8798f, 3.1416f, 3.1416f));

		ModelPartData cube_r7 = pyramid.addChild("cube_r7", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 2.6076f, -3.171f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -93.0f, 0.0f, -0.3491f, -2.3562f, 0.0f));

		ModelPartData cube_r8 = pyramid.addChild("cube_r8", ModelPartBuilder.create().uv(27, 77).cuboid(-8.0f, -21.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -66.0f, 0.0f, -2.8798f, 1.5708f, 3.1416f));

		ModelPartData cube_r9 = pyramid.addChild("cube_r9", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 0.01f, 0.0027f, 2.0f, 10.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(6.3647f, -77.5491f, 4.9353f, 0.0f, -0.7854f, 0.0f));

		ModelPartData cube_r10 = pyramid.addChild("cube_r10", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 0.01f, 0.0027f, 2.0f, 10.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(6.3647f, -77.5491f, -6.3647f, 0.0f, -0.7854f, 0.0f));

		ModelPartData cube_r11 = pyramid.addChild("cube_r11", ModelPartBuilder.create().uv(72, 0).cuboid(-1.02f, 0.01f, 0.0027f, 2.0f, 10.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-4.9353f, -77.5491f, -6.3647f, 0.0f, -0.7854f, 0.0f));

		ModelPartData cube_r12 = pyramid.addChild("cube_r12", ModelPartBuilder.create().uv(73, 29).cuboid(-6.0f, -4.5f, -5.0f, 0.0f, 9.0f, 10.0f, new Dilation(0.0f)).uv(73, 29).cuboid(6.0f, -4.5f, -5.0f, 0.0f, 9.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -73.0f, 0.0f, 0.0f, -1.5708f, 0.0f));

		ModelPartData cube_r13 = pyramid.addChild("cube_r13", ModelPartBuilder.create().uv(72, 0).cuboid(-1.02f, 0.01f, 0.0027f, 2.0f, 10.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-4.9353f, -77.5491f, 4.9353f, 0.0f, -0.7854f, 0.0f));

		ModelPartData cube_r14 = pyramid.addChild("cube_r14", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 2.6076f, -3.171f, 2.0f, 15.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -93.0f, 0.0f, -0.3491f, -0.7854f, 0.0f));

		ModelPartData cube_r15 = pyramid.addChild("cube_r15", ModelPartBuilder.create().uv(27, 77).cuboid(-8.0f, -21.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -66.0f, 0.0f, -2.8798f, 0.0f, 3.1416f));

		ModelPartData cube_r16 = pyramid.addChild("cube_r16", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 17.6076f, -3.171f, 2.0f, 12.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -84.0f, 0.0f, -0.3491f, -2.3562f, 0.0f));

		ModelPartData cube_r17 = pyramid.addChild("cube_r17", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 17.6076f, -3.171f, 2.0f, 12.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -84.0f, 0.0f, -0.3491f, 2.3562f, 0.0f));

		ModelPartData cube_r18 = pyramid.addChild("cube_r18", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 17.6076f, -3.171f, 2.0f, 12.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -84.0f, 0.0f, -0.3491f, -0.7854f, 0.0f));

		ModelPartData cube_r19 = pyramid.addChild("cube_r19", ModelPartBuilder.create().uv(72, 0).cuboid(-1.0f, 17.6076f, -3.171f, 2.0f, 12.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -84.0f, 0.0f, -0.3491f, 0.7854f, 0.0f));

		ModelPartData cube_r20 = pyramid.addChild("cube_r20", ModelPartBuilder.create().uv(27, 89).cuboid(-8.0f, -9.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -57.0f, 0.0f, -2.8798f, -1.5708f, 3.1416f));

		ModelPartData cube_r21 = pyramid.addChild("cube_r21", ModelPartBuilder.create().uv(27, 89).cuboid(-8.0f, -9.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -57.0f, 0.0f, -2.8798f, 1.5708f, 3.1416f));

		ModelPartData cube_r22 = pyramid.addChild("cube_r22", ModelPartBuilder.create().uv(27, 89).cuboid(-8.0f, -9.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -57.0f, 0.0f, -2.8798f, 3.1416f, 3.1416f));

		ModelPartData cube_r23 = pyramid.addChild("cube_r23", ModelPartBuilder.create().uv(27, 89).cuboid(-8.0f, -9.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -57.0f, 0.0f, -2.8798f, 0.0f, 3.1416f));

		ModelPartData booster = main.addChild("booster", ModelPartBuilder.create().uv(0, 114).cuboid(-6.0f, -15.0f, -6.0f, 12.0f, 6.0f, 0.0f, new Dilation(0.0f)).uv(0, 102).cuboid(6.0f, -15.0f, -6.0f, 0.0f, 6.0f, 12.0f, new Dilation(0.0f)).uv(0, 102).cuboid(-6.0f, -15.0f, -6.0f, 0.0f, 6.0f, 12.0f, new Dilation(0.0f)).uv(0, 114).cuboid(-6.0f, -15.0f, 6.0f, 12.0f, 6.0f, 0.0f, new Dilation(0.0f)).uv(0, 91).cuboid(7.0f, -9.0f, -7.0f, 0.0f, 9.0f, 14.0f, new Dilation(0.0f)).uv(0, 105).cuboid(-7.0f, -9.0f, 7.0f, 14.0f, 9.0f, 0.0f, new Dilation(0.0f)).uv(0, 91).cuboid(-7.0f, -9.0f, -7.0f, 0.0f, 9.0f, 14.0f, new Dilation(0.0f)).uv(-14, 77).cuboid(-7.0f, -9.0f, -7.0f, 14.0f, 0.0f, 14.0f, new Dilation(0.0f)).uv(-14, 91).cuboid(-7.0f, 0.0f, -7.0f, 14.0f, 0.0f, 14.0f, new Dilation(0.0f)).uv(0, 105).cuboid(-7.0f, -9.0f, -7.0f, 14.0f, 9.0f, 0.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 0.0f, 0.0f));

		ModelPartData tip = main.addChild("tip", ModelPartBuilder.create().uv(80, 0).cuboid(-4.0f, -110.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(0.0f)).uv(80, 16).cuboid(-3.0f, -118.0f, -3.0f, 6.0f, 8.0f, 6.0f, new Dilation(0.0f)).uv(104, 0).cuboid(-2.0f, -120.0f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)).uv(0, 120).cuboid(-2.0f, -135.0f, -2.0f, 4.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(104, 0).cuboid(-2.0f, -123.0f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)).uv(120, 0).cuboid(-1.0f, -131.0f, -1.0f, 2.0f, 11.0f, 2.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 0.0f, 0.0f));

		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 24.0f, 0.0f));

		ModelPartData cube_r24 = bb_main.addChild("cube_r24", ModelPartBuilder.create().uv(40, 101).cuboid(-15.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -24.0f, 0.0f, 0.0f, 1.5708f, 0.0f));

		ModelPartData cube_r25 = bb_main.addChild("cube_r25", ModelPartBuilder.create().uv(40, 101).cuboid(-15.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -24.0f, 0.0f, 0.0f, -1.5708f, 0.0f));
		return TexturedModelData.of(modelData, 128, 128);
	}
}
