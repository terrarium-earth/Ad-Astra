package earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_2;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.VehicleModel;
import earth.terrarium.ad_astra.common.entity.vehicle.RocketTier2;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RocketModelTier2 extends VehicleModel<RocketTier2> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "tier_2_rocket"), "main");

    public RocketModelTier2(ModelPart root) {
        super(root, "rocket");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition rocket2 = modelPartData.addOrReplaceChild("rocket", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, 0.0f));

        PartDefinition top = rocket2.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 98).addBox(-10.0f, -47.0f, 10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 78).addBox(-10.0f, -47.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 98).addBox(-10.0f, -47.0f, -10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 78).addBox(10.0f, -47.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(120, 39).addBox(-1.0f, -85.0f, -1.0f, 2.0f, 14.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(112, 18).addBox(-2.0f, -89.0f, -2.0f, 4.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -8.0f, 0.0f));

        PartDefinition cube_r1 = top.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(90, 22).addBox(-2.0f, -6.5f, -2.0f, 4.0f, 2.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(90, 22).addBox(-2.0f, -3.5f, -2.0f, 4.0f, 2.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(72, 26).addBox(-3.0f, -1.5f, -3.0f, 6.0f, 11.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(32, 57).addBox(0.0f, 4.5f, -12.0f, 0.0f, 17.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(32, 65).mirror().addBox(-12.0f, 4.5f, 0.0f, 8.0f, 17.0f, 0.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(32, 65).addBox(4.0f, 4.5f, 0.0f, 8.0f, 17.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -68.5f, 0.0f, 0.0f, -0.7854f, 0.0f));

        PartDefinition cube_r2 = top.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(104, 26).addBox(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -66.0f, 0.0f, -0.48f, -0.7854f, 0.0f));

        PartDefinition cube_r3 = top.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(112, 26).addBox(-1.0f, -3.5f, -2.5f, 2.0f, 19.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -66.0f, 0.0f, -0.829f, 0.7854f, 0.0f));

        PartDefinition cube_r4 = top.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(120, 26).addBox(-1.0f, -65.4f, -13.0779f, 2.0f, 11.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 8.0f, 0.0f, 0.0f, 0.7854f, 0.0f));

        PartDefinition cube_r5 = top.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(104, 26).addBox(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -66.0f, 0.0f, -0.48f, 0.7854f, 0.0f));

        PartDefinition cube_r6 = top.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(112, 26).addBox(-1.0f, -3.5f, -2.5f, 2.0f, 19.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -66.0f, 0.0f, 2.3126f, 0.7854f, 3.1416f));

        PartDefinition cube_r7 = top.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(32, 57).addBox(0.0f, 24.0f, -12.0f, 0.0f, 17.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -88.0f, 0.0f, 0.0f, 2.3562f, 0.0f));

        PartDefinition cube_r8 = top.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(104, 26).addBox(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -66.0f, 0.0f, -0.48f, 2.3562f, 0.0f));

        PartDefinition cube_r9 = top.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(120, 26).addBox(-1.0f, -65.4f, -13.0779f, 2.0f, 11.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 8.0f, 0.0f, 0.0f, 2.3562f, 0.0f));

        PartDefinition cube_r10 = top.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(112, 26).addBox(-1.0f, -3.5f, -2.5f, 2.0f, 19.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -66.0f, 0.0f, 2.3126f, -0.7854f, 3.1416f));

        PartDefinition cube_r11 = top.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(120, 26).addBox(-1.0f, -65.4f, -13.0779f, 2.0f, 11.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 8.0f, 0.0f, 0.0f, -2.3562f, 0.0f));

        PartDefinition cube_r12 = top.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(104, 26).addBox(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -66.0f, 0.0f, -0.48f, -2.3562f, 0.0f));

        PartDefinition cube_r13 = top.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(112, 26).addBox(-1.0f, -3.5f, -2.5f, 2.0f, 19.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -66.0f, 0.0f, -0.829f, -0.7854f, 0.0f));

        PartDefinition cube_r14 = top.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(120, 26).addBox(-1.0f, -65.4f, -13.0779f, 2.0f, 11.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 8.0f, 0.0f, 0.0f, -0.7854f, 0.0f));

        PartDefinition cube_r15 = top.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(72, 44).addBox(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -46.0f, 0.0f, 0.3491f, 1.5708f, 0.0f));

        PartDefinition cube_r16 = top.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(72, 44).addBox(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -46.0f, 0.0f, 0.3491f, 3.1416f, 0.0f));

        PartDefinition cube_r17 = top.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(72, 44).addBox(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -46.0f, 0.0f, 0.3491f, -1.5708f, 0.0f));

        PartDefinition cube_r18 = top.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(72, 44).addBox(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -46.0f, 0.0f, 0.3491f, 0.0f, 0.0f));

        PartDefinition body = rocket2.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(62, 46).addBox(4.0f, -32.0f, -10.0f, 0.0f, 8.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 89).addBox(-10.0f, -9.0f, -10.0f, 20.0f, 8.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 69).addBox(-10.0f, -9.0f, -10.0f, 0.0f, 8.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 89).addBox(-10.0f, -9.0f, 10.0f, 20.0f, 8.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(88, 0).addBox(9.0f, -10.0f, -4.0f, 6.0f, 14.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(0, 69).addBox(10.0f, -9.0f, -10.0f, 0.0f, 8.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 78).addBox(10.0f, -32.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 78).addBox(10.0f, -26.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 98).addBox(-10.0f, -32.0f, 10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 98).addBox(-10.0f, -26.0f, 10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(36, -18).addBox(9.0f, -46.0f, -9.0f, 0.0f, 47.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(36, 0).addBox(-9.0f, -46.0f, 9.0f, 18.0f, 47.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 0)
                .addBox(-9.0f, -46.0f, -9.0f, 18.0f, 47.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(62, 53).addBox(-9.0f, -46.0f, -9.0f, 2.0f, 47.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(0, 101).addBox(-10.0f, -26.0f, -10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 101).addBox(-10.0f, -32.0f, -10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(36, -18).mirror().addBox(-9.0f, -46.0f, -9.0f, 0.0f, 47.0f, 18.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(0, 78).addBox(-10.0f, -26.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 78).addBox(-10.0f, -32.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(36, 47).addBox(-6.0f, -34.0f, -10.0f, 12.0f, 12.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(35, 60).addBox(-4.0f, -24.0f, -10.0f, 8.0f, 0.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(62, 46).addBox(-4.0f, -32.0f, -10.0f, 0.0f, 8.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(35, 60).addBox(-4.0f, -32.0f, -10.0f, 8.0f, 0.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(-18, 47).addBox(-9.0f, 1.0f, -9.0f, 18.0f, 0.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(-18, 47)
                .addBox(-9.0f, -46.0f, -9.0f, 18.0f, 0.0f, 18.0f, new CubeDeformation(0.0f)),
            PartPose.offset(0.0f, -8.0f, 0.0f));

        PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(62, 53).addBox(-1.0f, -23.5f, -1.0f, 2.0f, 47.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-8.0f, -22.5f, 8.0f, 0.0f, 1.5708f, 0.0f));

        PartDefinition body_r2 = body.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(62, 53).addBox(-1.0f, -23.5f, -1.0f, 2.0f, 47.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(8.0f, -22.5f, 8.0f, 0.0f, 3.1416f, 0.0f));

        PartDefinition body_r3 = body.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(62, 53).addBox(-1.0f, -23.5f, -1.0f, 2.0f, 47.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(8.0f, -22.5f, -8.0f, 0.0f, -1.5708f, 0.0f));

        PartDefinition cube_r19 = body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(88, 0).addBox(-3.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -3.0f, 12.0f, 0.0f, -1.5708f, 0.0f));

        PartDefinition cube_r20 = body.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(88, 0).addBox(-3.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-12.0f, -3.0f, 0.0f, 0.0f, 3.1416f, 0.0f));

        PartDefinition cube_r21 = body.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(88, 0).addBox(-3.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -3.0f, -12.0f, 0.0f, 1.5708f, 0.0f));

        PartDefinition bottom = body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 65).addBox(-8.0f, 4.0f, 8.0f, 16.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(-16, 72).addBox(-8.0f, 4.0f, -8.0f, 16.0f, 0.0f, 16.0f, new CubeDeformation(0.0f)).texOffs(-16, 103).addBox(-8.0f, 8.0f, -8.0f, 16.0f, 0.0f, 16.0f, new CubeDeformation(0.0f)).texOffs(0, 49).addBox(-8.0f, 4.0f, -8.0f, 0.0f, 4.0f, 16.0f, new CubeDeformation(0.0f)).texOffs(0, 65).addBox(-8.0f, 4.0f, -8.0f, 16.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 49).addBox(8.0f, 4.0f, -8.0f, 0.0f, 4.0f, 16.0f, new CubeDeformation(0.0f)).texOffs(0, 69).addBox(-6.0f, 1.0f, -6.0f, 12.0f, 3.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 57).addBox(-6.0f, 1.0f, -6.0f, 0.0f, 3.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(0, 57).addBox(6.0f, 1.0f, -6.0f, 0.0f, 3.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(0, 69).addBox(-6.0f, 1.0f, 6.0f, 12.0f, 3.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 0.0f, 0.0f));

        PartDefinition fins = rocket2.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(0.0f, -8.0f, 0.0f));

        PartDefinition cube_r22 = fins.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0f, 11.0f, 22.8284f, 4.0f, 22.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, -24.0f, -1.0f, 0.0f, 0.7854f, 0.0f));

        PartDefinition cube_r23 = fins.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(72, 0).addBox(-0.5858f, 11.0f, 21.4142f, 4.0f, 22.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, -24.0f, -1.0f, 0.0f, -0.7854f, 0.0f));

        PartDefinition cube_r24 = fins.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0f, 11.0f, 20.0f, 4.0f, 22.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, -24.0f, -1.0f, 0.0f, -2.3562f, 0.0f));

        PartDefinition cube_r25 = fins.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(72, 0).addBox(-3.4142f, 11.0f, 21.4142f, 4.0f, 22.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, -24.0f, -1.0f, 0.0f, 2.3562f, 0.0f));

        PartDefinition cube_r26 = fins.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0f, 4.25f, 13.0f, 2.0f, 13.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, 9.0f, -1.0f, 1.1345f, -2.3562f, 0.0f));

        PartDefinition cube_r27 = fins.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0f, 12.9801f, 1.507f, 2.0f, 13.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -18.0f, 1.0f, -2.0944f, -0.7854f, 3.1416f));

        PartDefinition cube_r28 = fins.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(110, 46).addBox(0.0f, 1.0f, 13.0f, 0.0f, 15.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, 7.0f, -1.0f, 1.1345f, -2.3562f, 0.0f));

        PartDefinition cube_r29 = fins.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(116, 0).addBox(0.4142f, 11.6984f, 0.9094f, 2.0f, 13.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -18.0f, 1.0f, -2.0944f, 0.7854f, 3.1416f));

        PartDefinition cube_r30 = fins.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0f, 4.25f, 13.0f, 2.0f, 13.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, 9.0f, -1.0f, 1.1345f, 2.3562f, 0.0f));

        PartDefinition cube_r31 = fins.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(110, 46).addBox(0.0f, 1.0f, 13.0f, 0.0f, 15.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, 7.0f, -1.0f, 1.1345f, 2.3562f, 0.0f));

        PartDefinition cube_r32 = fins.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(116, 0).addBox(-2.4142f, 11.6984f, 0.9094f, 2.0f, 13.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -18.0f, 1.0f, 1.0472f, -0.7854f, 0.0f));

        PartDefinition cube_r33 = fins.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(110, 46).addBox(0.0f, 1.0f, 13.0f, 0.0f, 15.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, 7.0f, 1.0f, 1.1345f, -0.7854f, 0.0f));

        PartDefinition cube_r34 = fins.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0f, 4.25f, 13.0f, 2.0f, 13.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, 9.0f, 1.0f, 1.1345f, -0.7854f, 0.0f));

        PartDefinition cube_r35 = fins.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0f, 10.4167f, 0.3117f, 2.0f, 13.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, -18.0f, 1.0f, 1.0472f, 0.7854f, 0.0f));

        PartDefinition cube_r36 = fins.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(110, 46).addBox(0.0f, 1.0f, 13.0f, 0.0f, 15.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, 7.0f, 1.0f, 1.1345f, 0.7854f, 0.0f));

        PartDefinition cube_r37 = fins.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(116, 0).addBox(-1.0f, 4.25f, 13.0f, 2.0f, 13.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, 9.0f, 1.0f, 1.1345f, 0.7854f, 0.0f));
        return LayerDefinition.create(modelData, 128, 128);
    }
}
