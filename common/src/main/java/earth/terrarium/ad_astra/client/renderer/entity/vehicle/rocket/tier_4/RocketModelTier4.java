package earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_4;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.VehicleModel;
import earth.terrarium.ad_astra.common.entity.vehicle.RocketTier4;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RocketModelTier4 extends VehicleModel<RocketTier4> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "tier_4_rocket"), "main");

    public RocketModelTier4(ModelPart root) {
        super(root, "rocket");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition rocket = modelPartData.addOrReplaceChild("rocket",
                CubeListBuilder.create().texOffs(88, 80).addBox(10.0f, -28.0f, -10.0f, 0.0f, 8.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(88, 100).addBox(-10.0f, -28.0f, 10.0f, 20.0f, 8.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(88, 80).addBox(-10.0f, -28.0f, -10.0f, 0.0f, 8.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(88, 100).addBox(-10.0f, -28.0f, -10.0f, 20.0f, 8.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(88, 115).addBox(-10.0f, -38.0f, -10.0f, 20.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(88, 95).addBox(10.0f, -38.0f, -10.0f, 0.0f, 7.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(88, 115).addBox(-10.0f, -38.0f, 10.0f, 20.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(88, 95).addBox(-10.0f, -38.0f, -10.0f, 0.0f, 7.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(88, 108).addBox(-10.0f, -63.0f, -10.0f, 20.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(88, 88).addBox(10.0f, -63.0f, -10.0f, 0.0f, 7.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(88, 108).addBox(-10.0f, -63.0f, 10.0f, 20.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(88, 88).addBox(-10.0f, -63.0f, -10.0f, 0.0f, 7.0f, 20.0f, new CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 24.0f, 0.0f));

        PartDefinition side_booster_right = rocket.addOrReplaceChild("side_booster_right", CubeListBuilder.create().texOffs(104, 16).addBox(-18.0f, -56.0f, -3.0f, 6.0f, 6.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(96, 28).mirror().addBox(-19.0f, -50.0f, -4.0f, 8.0f, 35.0f, 8.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(16, 115).mirror().addBox(-19.0f, -13.0f, -4.0f, 8.0f, 5.0f, 8.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(72, 30).mirror().addBox(-18.0f, -15.0f, -3.0f, 6.0f, 2.0f, 6.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offset(-4.0f, 4.0f, 0.0f));

        PartDefinition side_booster_left = rocket.addOrReplaceChild("side_booster_left", CubeListBuilder.create().texOffs(104, 16).addBox(11.0f, -56.0f, -3.0f, 6.0f, 6.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(96, 28).addBox(10.0f, -50.0f, -4.0f, 8.0f, 35.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(72, 30).addBox(11.0f, -15.0f, -3.0f, 6.0f, 2.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(16, 115).addBox(10.0f, -13.0f, -4.0f, 8.0f, 5.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offset(5.0f, 4.0f, 0.0f));

        PartDefinition cube_r1 = side_booster_left.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(102, 124).addBox(0.844f, -11.3692f, -1.0f, 11.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(102, 124).addBox(-7.6412f, -19.8544f, -1.0f, 11.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(82, 85).addBox(-5.3778f, -18.591f, 0.0f, 23.0f, 15.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(102, 124).addBox(8.6222f, -3.591f, -1.0f, 11.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-5.0f, -31.75f, 0.0f, 0.0f, 0.0f, 0.7854f));

        PartDefinition cube_r2 = side_booster_left.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(102, 124).addBox(-11.844f, -11.3692f, -1.0f, 11.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(102, 124).addBox(-3.3588f, -19.8544f, -1.0f, 11.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(82, 85).mirror().addBox(-17.6222f, -18.591f, 0.0f, 23.0f, 15.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(102, 124).addBox(-19.6222f, -3.591f, -1.0f, 11.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-5.0f, -31.75f, 0.0f, 0.0f, 0.0f, -0.7854f));

        PartDefinition main = rocket.addOrReplaceChild("main", CubeListBuilder.create().texOffs(36, -18).addBox(-9.0f, -74.0f, -9.0f, 0.0f, 59.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(36, -18).addBox(9.0f, -74.0f, -9.0f, 0.0f, 59.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(36, 0).addBox(-9.0f, -74.0f, 9.0f, 18.0f, 59.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-9.0f, -74.0f, -9.0f, 18.0f, 59.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(73, 67).addBox(-9.0f, -74.0f, -9.0f, 2.0f, 59.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(75, 69).addBox(-9.0f, -74.0f, -1.0f, 2.0f, 59.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(75, 69).addBox(7.0f, -74.0f, 1.0f, 2.0f, 59.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(75, 69).addBox(7.0f, -74.0f, -1.0f, 2.0f, 59.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(75, 69).addBox(-9.0f, -74.0f, 1.0f, 2.0f, 59.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(-18, 59).addBox(-9.0f, -15.0f, -9.0f, 18.0f, 0.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(-18, 59).addBox(-9.0f, -73.0f, -9.0f, 18.0f, 0.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(35, 72)
                .addBox(-4.0f, -52.0f, -10.0f, 8.0f, 0.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(35, 72).addBox(-4.0f, -44.0f, -10.0f, 8.0f, 0.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(62, 58).addBox(-4.0f, -52.0f, -10.0f, 0.0f, 8.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(36, 59).addBox(-6.0f, -54.0f, -10.0f, 12.0f, 12.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(62, 58).addBox(4.0f, -52.0f, -10.0f, 0.0f, 8.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 0.0f, 0.0f));

        PartDefinition main_r1 = main.addOrReplaceChild("main_r1", CubeListBuilder.create().texOffs(73, 67).addBox(-1.0f, -29.5f, -1.0f, 2.0f, 59.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-8.0f, -44.5f, 8.0f, 0.0f, 1.5708f, 0.0f));

        PartDefinition main_r2 = main.addOrReplaceChild("main_r2", CubeListBuilder.create().texOffs(73, 67).addBox(-1.0f, -29.5f, -1.0f, 2.0f, 59.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(8.0f, -44.5f, 8.0f, 0.0f, 3.1416f, 0.0f));

        PartDefinition main_r3 = main.addOrReplaceChild("main_r3", CubeListBuilder.create().texOffs(73, 67).addBox(-1.0f, -29.5f, -1.0f, 2.0f, 59.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(8.0f, -44.5f, -8.0f, 0.0f, -1.5708f, 0.0f));

        PartDefinition main_r4 = main.addOrReplaceChild("main_r4", CubeListBuilder.create().texOffs(75, 69).addBox(-1.0f, -29.5f, -14.0f, 2.0f, 59.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(75, 69).addBox(-1.0f, -29.5f, 0.0f, 2.0f, 59.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-7.0f, -44.5f, 0.0f, 0.0f, -1.5708f, 0.0f));

        PartDefinition fins = main.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(-2.0f, -8.0f, -2.0f));

        PartDefinition pyramid = main.addOrReplaceChild("pyramid", CubeListBuilder.create().texOffs(73, 29).addBox(-6.0f, -77.5f, -5.0f, 0.0f, 9.0f, 10.0f, new CubeDeformation(0.0f)).texOffs(73, 29).addBox(6.0f, -77.5f, -5.0f, 0.0f, 9.0f, 10.0f, new CubeDeformation(0.0f)).texOffs(88, 102).addBox(10.0f, -58.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(88, 102).addBox(-10.0f, -58.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(88, 122).addBox(-10.0f, -58.0f, 10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(88, 122).addBox(-10.0f, -58.0f, -10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -17.0f, 0.0f));

        PartDefinition cube_r3 = pyramid.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 2.6076f, -3.171f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -93.0f, 0.0f, -0.3491f, 0.7854f, 0.0f));

        PartDefinition cube_r4 = pyramid.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(27, 77).addBox(-8.0f, -21.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -66.0f, 0.0f, -2.8798f, -1.5708f, 3.1416f));

        PartDefinition cube_r5 = pyramid.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 2.6076f, -3.171f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -93.0f, 0.0f, -0.3491f, 2.3562f, 0.0f));

        PartDefinition cube_r6 = pyramid.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(27, 77).addBox(-8.0f, -21.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -66.0f, 0.0f, -2.8798f, 3.1416f, 3.1416f));

        PartDefinition cube_r7 = pyramid.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 2.6076f, -3.171f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -93.0f, 0.0f, -0.3491f, -2.3562f, 0.0f));

        PartDefinition cube_r8 = pyramid.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(27, 77).addBox(-8.0f, -21.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -66.0f, 0.0f, -2.8798f, 1.5708f, 3.1416f));

        PartDefinition cube_r9 = pyramid.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 0.01f, 0.0027f, 2.0f, 10.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(6.3647f, -77.5491f, 4.9353f, 0.0f, -0.7854f, 0.0f));

        PartDefinition cube_r10 = pyramid.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 0.01f, 0.0027f, 2.0f, 10.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(6.3647f, -77.5491f, -6.3647f, 0.0f, -0.7854f, 0.0f));

        PartDefinition cube_r11 = pyramid.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(72, 0).addBox(-1.02f, 0.01f, 0.0027f, 2.0f, 10.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-4.9353f, -77.5491f, -6.3647f, 0.0f, -0.7854f, 0.0f));

        PartDefinition cube_r12 = pyramid.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(73, 29).addBox(-6.0f, -4.5f, -5.0f, 0.0f, 9.0f, 10.0f, new CubeDeformation(0.0f)).texOffs(73, 29).addBox(6.0f, -4.5f, -5.0f, 0.0f, 9.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -73.0f, 0.0f, 0.0f, -1.5708f, 0.0f));

        PartDefinition cube_r13 = pyramid.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(72, 0).addBox(-1.02f, 0.01f, 0.0027f, 2.0f, 10.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-4.9353f, -77.5491f, 4.9353f, 0.0f, -0.7854f, 0.0f));

        PartDefinition cube_r14 = pyramid.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 2.6076f, -3.171f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -93.0f, 0.0f, -0.3491f, -0.7854f, 0.0f));

        PartDefinition cube_r15 = pyramid.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(27, 77).addBox(-8.0f, -21.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -66.0f, 0.0f, -2.8798f, 0.0f, 3.1416f));

        PartDefinition cube_r16 = pyramid.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 17.6076f, -3.171f, 2.0f, 12.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -84.0f, 0.0f, -0.3491f, -2.3562f, 0.0f));

        PartDefinition cube_r17 = pyramid.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 17.6076f, -3.171f, 2.0f, 12.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -84.0f, 0.0f, -0.3491f, 2.3562f, 0.0f));

        PartDefinition cube_r18 = pyramid.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 17.6076f, -3.171f, 2.0f, 12.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -84.0f, 0.0f, -0.3491f, -0.7854f, 0.0f));

        PartDefinition cube_r19 = pyramid.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 17.6076f, -3.171f, 2.0f, 12.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -84.0f, 0.0f, -0.3491f, 0.7854f, 0.0f));

        PartDefinition cube_r20 = pyramid.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(27, 89).addBox(-8.0f, -9.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -57.0f, 0.0f, -2.8798f, -1.5708f, 3.1416f));

        PartDefinition cube_r21 = pyramid.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(27, 89).addBox(-8.0f, -9.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -57.0f, 0.0f, -2.8798f, 1.5708f, 3.1416f));

        PartDefinition cube_r22 = pyramid.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(27, 89).addBox(-8.0f, -9.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -57.0f, 0.0f, -2.8798f, 3.1416f, 3.1416f));

        PartDefinition cube_r23 = pyramid.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(27, 89).addBox(-8.0f, -9.5488f, 8.7536f, 16.0f, 12.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -57.0f, 0.0f, -2.8798f, 0.0f, 3.1416f));

        PartDefinition booster = main.addOrReplaceChild("booster", CubeListBuilder.create().texOffs(0, 114).addBox(-6.0f, -15.0f, -6.0f, 12.0f, 6.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 102).addBox(6.0f, -15.0f, -6.0f, 0.0f, 6.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(0, 102).addBox(-6.0f, -15.0f, -6.0f, 0.0f, 6.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(0, 114).addBox(-6.0f, -15.0f, 6.0f, 12.0f, 6.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 91).addBox(7.0f, -9.0f, -7.0f, 0.0f, 9.0f, 14.0f, new CubeDeformation(0.0f)).texOffs(0, 105).addBox(-7.0f, -9.0f, 7.0f, 14.0f, 9.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 91).addBox(-7.0f, -9.0f, -7.0f, 0.0f, 9.0f, 14.0f, new CubeDeformation(0.0f)).texOffs(-14, 77).addBox(-7.0f, -9.0f, -7.0f, 14.0f, 0.0f, 14.0f, new CubeDeformation(0.0f)).texOffs(-14, 91).addBox(-7.0f, 0.0f, -7.0f, 14.0f, 0.0f, 14.0f, new CubeDeformation(0.0f)).texOffs(0, 105).addBox(-7.0f, -9.0f, -7.0f, 14.0f, 9.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 0.0f, 0.0f));

        PartDefinition tip = main.addOrReplaceChild("tip", CubeListBuilder.create().texOffs(80, 0).addBox(-4.0f, -110.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(80, 16).addBox(-3.0f, -118.0f, -3.0f, 6.0f, 8.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(104, 0).addBox(-2.0f, -120.0f, -2.0f, 4.0f, 2.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(0, 120).addBox(-2.0f, -135.0f, -2.0f, 4.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(104, 0).addBox(-2.0f, -123.0f, -2.0f, 4.0f, 2.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(120, 0).addBox(-1.0f, -131.0f, -1.0f, 2.0f, 11.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 0.0f, 0.0f));

        PartDefinition bb_main = modelPartData.addOrReplaceChild("bb_main", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, 0.0f));

        PartDefinition cube_r24 = bb_main.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(40, 101).addBox(-15.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -24.0f, 0.0f, 0.0f, 1.5708f, 0.0f));

        PartDefinition cube_r25 = bb_main.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(40, 101).addBox(-15.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -24.0f, 0.0f, 0.0f, -1.5708f, 0.0f));
        return LayerDefinition.create(modelData, 128, 128);
    }
}
