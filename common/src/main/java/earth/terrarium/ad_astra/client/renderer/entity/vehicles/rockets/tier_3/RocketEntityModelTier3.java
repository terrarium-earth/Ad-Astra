package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_3;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityModel;
import earth.terrarium.ad_astra.entities.vehicles.RocketTier3;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class RocketEntityModelTier3 extends VehicleEntityModel<RocketTier3> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ModResourceLocation("tier_3_rocket"), "main");

    public RocketEntityModelTier3(ModelPart root) {
        super(root, "rocket");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition rocket3 = modelPartData.addOrReplaceChild("rocket", CubeListBuilder.create(), PartPose.offset(0.0f, 25.0f, 0.0f));

        PartDefinition body = rocket3.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(36, -18).addBox(9.0f, -51.0f, -9.0f, 0.0f, 52.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-9.0f, -51.0f, -9.0f, 18.0f, 52.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(80, 74).addBox(-9.0f, -51.0f, -9.0f, 2.0f, 52.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(36, 0).addBox(-9.0f, -51.0f, 9.0f, 18.0f, 52.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(36, -18).addBox(-9.0f, -51.0f, -9.0f, 0.0f, 52.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(-18, 99).addBox(-9.0f, 1.0f, -9.0f, 18.0f, 0.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(-18, 99).addBox(-9.0f, -51.0f, -9.0f, 18.0f, 0.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(0, 52).addBox(-10.0f, -11.0f, -10.0f, 20.0f, 11.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 32).addBox(10.0f, -11.0f, -10.0f, 0.0f, 11.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 52).addBox(-10.0f, -11.0f, 10.0f, 20.0f, 11.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 32).addBox(-10.0f, -11.0f, -10.0f, 0.0f, 11.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 65).addBox(-10.0f, -26.0f, -10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 65)
                        .addBox(-10.0f, -32.0f, -10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 43).addBox(-10.0f, -26.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 43).addBox(-10.0f, -32.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(88, 83).addBox(-10.0f, -44.0f, -10.0f, 20.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(88, 63).addBox(-10.0f, -44.0f, -10.0f, 0.0f, 7.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(40, 52).addBox(-6.0f, -34.0f, -10.0f, 12.0f, 12.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 43).addBox(10.0f, -32.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 43).addBox(10.0f, -26.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(88, 63).addBox(10.0f, -44.0f, -10.0f, 0.0f, 7.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(88, 83).addBox(-10.0f, -44.0f, 10.0f, 20.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 63).addBox(-10.0f, -32.0f, 10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 63).addBox(-10.0f, -26.0f, 10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(39, 65)
                        .addBox(-4.0f, -24.0f, -10.0f, 8.0f, 0.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(66, 51).addBox(-4.0f, -32.0f, -10.0f, 0.0f, 8.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(39, 65).addBox(-4.0f, -32.0f, -10.0f, 8.0f, 0.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(66, 51).addBox(4.0f, -32.0f, -10.0f, 0.0f, 8.0f, 1.0f, new CubeDeformation(0.0f)),
                PartPose.offset(0.0f, -9.0f, 0.0f));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(96, 9).addBox(-15.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -3.0f, 0.0f, 0.0f, 1.5708f, 0.0f));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(96, 9).addBox(-15.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -3.0f, 0.0f, 0.0f, -1.5708f, 0.0f));

        PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(80, 74).addBox(-1.0f, -26.0f, -1.0f, 2.0f, 52.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-8.0f, -25.0f, 8.0f, 0.0f, 1.5708f, 0.0f));

        PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(80, 74).addBox(-1.0f, -26.0f, -1.0f, 2.0f, 52.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(8.0f, -25.0f, 8.0f, 0.0f, 3.1416f, 0.0f));

        PartDefinition body_r3 = body.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(80, 74).addBox(-1.0f, -26.0f, -1.0f, 2.0f, 52.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(8.0f, -25.0f, -8.0f, 0.0f, -1.5708f, 0.0f));

        PartDefinition bottom = rocket3.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 117).addBox(-7.0f, -12.0f, -7.0f, 14.0f, 3.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 103).addBox(-7.0f, -12.0f, -7.0f, 0.0f, 3.0f, 14.0f, new CubeDeformation(0.0f)).texOffs(0, 117).addBox(-7.0f, -12.0f, 7.0f, 14.0f, 3.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 103).addBox(7.0f, -12.0f, -7.0f, 0.0f, 3.0f, 14.0f, new CubeDeformation(0.0f)).texOffs(18, 92).addBox(-9.0f, -9.0f, -9.0f, 18.0f, 0.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(0, 120).addBox(-9.0f, -9.0f, -9.0f, 18.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 102).addBox(9.0f, -9.0f, -9.0f, 0.0f, 4.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(0, 102).addBox(-9.0f, -9.0f, -9.0f, 0.0f, 4.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(0, 120).addBox(-9.0f, -9.0f, 9.0f, 18.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(18, 110).addBox(-9.0f, -5.0f, -9.0f, 18.0f, 0.0f, 18.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 4.0f, 0.0f));

        PartDefinition top = rocket3.addOrReplaceChild("top", CubeListBuilder.create().texOffs(72, 18).addBox(-1.0f, -1.5f, -1.0f, 2.0f, 14.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(16, 87).addBox(-2.0f, -5.5f, -2.0f, 4.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(0, 43).addBox(10.0f, 41.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 63).addBox(-10.0f, 41.0f, -10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 63).addBox(-10.0f, 41.0f, 10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 43).addBox(-10.0f, 41.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(52, 67).addBox(-5.0f, 26.0f, 6.0f, 10.0f, 9.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(52, 57).addBox(-6.0f, 26.0f, -5.0f, 0.0f, 9.0f, 10.0f, new CubeDeformation(0.0f)).texOffs(52, 67).addBox(-5.0f, 26.0f, -6.0f, 10.0f, 9.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(52, 57).addBox(6.0f, 26.0f, -5.0f, 0.0f, 9.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -102.5f, 0.0f));

        PartDefinition cube_r3 = top.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 67).addBox(0.0f, -10.0f, -5.0f, 0.0f, 10.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-6.0f, 26.0f, 0.0f, 0.0f, 0.0f, 0.3491f));

        PartDefinition cube_r4 = top.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(16, 67).addBox(0.0f, -10.0f, -5.0f, 0.0f, 10.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(6.0f, 26.0f, 0.0f, 0.0f, 0.0f, -0.3491f));

        PartDefinition cube_r5 = top.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(16, 77).addBox(-5.0f, -10.0f, 0.0f, 10.0f, 10.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 26.0f, -6.0f, -0.3491f, 0.0f, 0.0f));

        PartDefinition cube_r6 = top.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(16, 77).addBox(-5.0f, -10.0f, 0.0f, 10.0f, 10.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 26.0f, 6.0f, 0.3491f, 0.0f, 0.0f));

        PartDefinition body_r4 = top.addOrReplaceChild("body_r4", CubeListBuilder.create().texOffs(16, 67).addBox(-9.0f, -10.0f, 0.25f, 18.0f, 10.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 42.5f, -9.3f, -0.3491f, 0.0f, 0.0f));

        PartDefinition body_r5 = top.addOrReplaceChild("body_r5", CubeListBuilder.create().texOffs(16, 49).addBox(0.0f, -10.0f, -9.0f, 0.0f, 10.0f, 18.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-9.0f, 42.5f, 0.0f, 0.0f, 0.0f, 0.3491f));

        PartDefinition body_r6 = top.addOrReplaceChild("body_r6", CubeListBuilder.create().texOffs(16, 67).addBox(-9.0f, -10.0f, 0.0f, 18.0f, 10.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 42.5f, 9.0f, 0.3491f, 0.0f, 0.0f));

        PartDefinition body_r7 = top.addOrReplaceChild("body_r7", CubeListBuilder.create().texOffs(16, 49).addBox(0.0f, -10.0f, -9.0f, 0.0f, 10.0f, 18.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(9.0f, 42.5f, -0.3f, 0.0f, 0.0f, -0.3491f));

        PartDefinition body_r8 = top.addOrReplaceChild("body_r8", CubeListBuilder.create().texOffs(98, 31).addBox(-1.0f, -16.0f, -2.0607f, 2.0f, 16.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-6.4571f, 26.5f, 6.4571f, 0.4363f, -0.7854f, 0.0f));

        PartDefinition body_r9 = top.addOrReplaceChild("body_r9", CubeListBuilder.create().texOffs(76, 54).addBox(-1.0f, -0.5f, 0.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-5.0f, 27.0f, 5.0f, 0.0f, -0.7854f, 0.0f));

        PartDefinition body_r10 = top.addOrReplaceChild("body_r10", CubeListBuilder.create().texOffs(98, 31).addBox(-1.0f, -16.0f, 0.0607f, 2.0f, 16.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-6.4571f, 26.5f, -6.4571f, -0.4363f, 0.7854f, 0.0f));

        PartDefinition body_r11 = top.addOrReplaceChild("body_r11", CubeListBuilder.create().texOffs(76, 54).addBox(-1.0f, -0.5f, -2.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-5.0f, 27.0f, -5.0f, 0.0f, 0.7854f, 0.0f));

        PartDefinition body_r12 = top.addOrReplaceChild("body_r12", CubeListBuilder.create().texOffs(98, 31).addBox(-2.0607f, -16.0f, -1.0f, 2.0f, 16.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(6.4571f, 26.5f, -6.4571f, -0.3999f, 0.6956f, -0.583f));

        PartDefinition body_r13 = top.addOrReplaceChild("body_r13", CubeListBuilder.create().texOffs(76, 54).addBox(0.0f, -0.5f, -1.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(5.0f, 27.0f, -5.0f, 0.0f, 0.7854f, 0.0f));

        PartDefinition body_r14 = top.addOrReplaceChild("body_r14", CubeListBuilder.create().texOffs(98, 31).addBox(-2.0607f, -16.0f, -1.0f, 2.0f, 16.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(6.4571f, 26.5f, 6.4571f, 0.3999f, -0.6956f, -0.583f));

        PartDefinition body_r15 = top.addOrReplaceChild("body_r15", CubeListBuilder.create().texOffs(76, 54).addBox(0.0f, -0.5f, -1.0f, 2.0f, 8.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(5.0f, 27.0f, 5.0f, 0.0f, -0.7854f, 0.0f));

        PartDefinition body_r16 = top.addOrReplaceChild("body_r16", CubeListBuilder.create().texOffs(68, 54).addBox(-1.0f, -10.0f, -1.0f, 2.0f, 9.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(9.0f, 42.5f, -9.0f, -0.4326f, 0.678f, -0.6346f));

        PartDefinition body_r17 = top.addOrReplaceChild("body_r17", CubeListBuilder.create().texOffs(68, 54).addBox(-1.0f, -10.0f, -1.0f, 2.0f, 9.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-9.0f, 42.5f, -9.0f, -0.48f, 0.7854f, 0.0f));

        PartDefinition body_r18 = top.addOrReplaceChild("body_r18", CubeListBuilder.create().texOffs(68, 54).addBox(-1.0f, -10.0f, -1.0f, 2.0f, 9.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-9.0f, 42.5f, 9.0f, 0.4326f, 0.678f, 0.6346f));

        PartDefinition cube_r7 = top.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(80, 17).addBox(-2.0f, -6.5f, -2.0f, 4.0f, 2.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(80, 0).addBox(-3.0f, -1.5f, -3.0f, 6.0f, 11.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(80, 17).addBox(-2.0f, -3.5f, -2.0f, 4.0f, 2.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 12.0f, 0.0f, 0.0f, -0.7854f, 0.0f));

        PartDefinition body_r19 = top.addOrReplaceChild("body_r19", CubeListBuilder.create().texOffs(68, 54).addBox(-1.0f, -10.0f, -1.0f, 2.0f, 9.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(9.0f, 42.5f, 9.0f, 0.48f, 0.7854f, 0.0f));

        PartDefinition fins = rocket3.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(0.0f, -10.0f, 0.0f));

        PartDefinition cube_r8 = fins.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 67).addBox(-2.0f, 5.0f, 23.8284f, 4.0f, 28.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, -24.0f, -1.0f, 0.0f, 0.7854f, 0.0f));

        PartDefinition cube_r9 = fins.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 67).addBox(-0.5858f, 5.0f, 22.4142f, 4.0f, 28.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, -24.0f, -1.0f, 0.0f, -0.7854f, 0.0f));

        PartDefinition cube_r10 = fins.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 67).addBox(-2.0f, 5.0f, 21.0f, 4.0f, 28.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, -24.0f, -1.0f, 0.0f, -2.3562f, 0.0f));

        PartDefinition cube_r11 = fins.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 67).addBox(-3.4142f, 5.0f, 22.4142f, 4.0f, 28.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, -24.0f, -1.0f, 0.0f, 2.3562f, 0.0f));

        PartDefinition cube_r12 = fins.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(72, 21).addBox(0.0f, 4.9167f, -0.6883f, 0.0f, 20.0f, 13.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -10.0f, 1.0f, 1.0472f, 0.7854f, 0.0f));

        PartDefinition cube_r13 = fins.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(72, 21).addBox(1.4142f, 6.1984f, -0.0906f, 0.0f, 20.0f, 13.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -10.0f, 1.0f, -2.0944f, 0.7854f, 3.1416f));

        PartDefinition cube_r14 = fins.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(72, 21).addBox(0.0f, 7.4801f, 0.507f, 0.0f, 20.0f, 13.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -10.0f, 1.0f, -2.0944f, -0.7854f, 3.1416f));

        PartDefinition cube_r15 = fins.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(72, 21).addBox(-1.4142f, 6.1984f, -0.0906f, 0.0f, 20.0f, 13.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -10.0f, 1.0f, 1.0472f, -0.7854f, 0.0f));

        PartDefinition cube_r16 = fins.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 12.9801f, 1.507f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -7.0f, 1.0f, -2.0944f, -0.7854f, 3.1416f));

        PartDefinition cube_r17 = fins.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(72, 0).addBox(-2.4142f, 11.6984f, 0.9094f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -7.0f, 1.0f, 1.0472f, -0.7854f, 0.0f));

        PartDefinition cube_r18 = fins.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 10.4167f, 0.3117f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -7.0f, 1.0f, 1.0472f, 0.7854f, 0.0f));

        PartDefinition cube_r19 = fins.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(72, 0).addBox(0.4142f, 11.6984f, 0.9094f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -7.0f, 1.0f, -2.0944f, 0.7854f, 3.1416f));

        PartDefinition cube_r20 = fins.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 10.4167f, 0.3117f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -15.0f, 1.0f, 1.0472f, 0.7854f, 0.0f));

        PartDefinition cube_r21 = fins.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(72, 0).addBox(-2.4142f, 11.6984f, 0.9094f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -15.0f, 1.0f, 1.0472f, -0.7854f, 0.0f));

        PartDefinition cube_r22 = fins.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 12.9801f, 1.507f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -15.0f, 1.0f, -2.0944f, -0.7854f, 3.1416f));

        PartDefinition cube_r23 = fins.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(72, 0).addBox(0.4142f, 11.6984f, 0.9094f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -15.0f, 1.0f, -2.0944f, 0.7854f, 3.1416f));

        PartDefinition cube_r24 = fins.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 12.9801f, 1.507f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -23.0f, 1.0f, -2.0944f, -0.7854f, 3.1416f));

        PartDefinition cube_r25 = fins.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(72, 0).addBox(0.4142f, 11.6984f, 0.9094f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -23.0f, 1.0f, -2.0944f, 0.7854f, 3.1416f));

        PartDefinition cube_r26 = fins.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(72, 0).addBox(-2.4142f, 11.6984f, 0.9094f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -23.0f, 1.0f, 1.0472f, -0.7854f, 0.0f));

        PartDefinition cube_r27 = fins.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(72, 0).addBox(-1.0f, 10.4167f, 0.3117f, 2.0f, 15.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -23.0f, 1.0f, 1.0472f, 0.7854f, 0.0f));

        PartDefinition boosters = rocket3.addOrReplaceChild("boosters", CubeListBuilder.create().texOffs(104, 90).addBox(-19.0f, -39.0f, -3.0f, 6.0f, 6.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(96, 49).mirror().addBox(-20.0f, -33.0f, -4.0f, 8.0f, 26.0f, 8.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(32, 79).mirror().addBox(-20.0f, -5.0f, -4.0f, 8.0f, 5.0f, 8.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(72, 64).mirror().addBox(-19.0f, -7.0f, -3.0f, 6.0f, 2.0f, 6.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(110, 32).addBox(-12.0f, -17.0f, -2.0f, 2.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(110, 32).addBox(-12.0f, -27.0f, -2.0f, 2.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(104, 90).addBox(11.0f, -39.0f, -3.0f, 6.0f, 6.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(96, 49).addBox(10.0f, -33.0f, -4.0f, 8.0f, 26.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(72, 64).addBox(11.0f, -7.0f, -3.0f, 6.0f, 2.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(32, 79).addBox(10.0f, -5.0f, -4.0f, 8.0f, 5.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(110, 32)
                .addBox(8.0f, -27.0f, -2.0f, 2.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(110, 32).addBox(8.0f, -17.0f, -2.0f, 2.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offset(1.0f, -3.0f, 0.0f));
        return LayerDefinition.create(modelData, 128, 128);
    }
}
