package earth.terrarium.adastra.client.models.entities.vehicles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.ClientPlatformUtils;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class RocketModel<T extends Rocket> extends EntityModel<T> {
    public static final ModelLayerLocation TIER_1_LAYER = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "tier_1_rocket"), "main");
    public static final ModelLayerLocation TIER_2_LAYER = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "tier_2_rocket"), "main");
    public static final ModelLayerLocation TIER_3_LAYER = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "tier_3_rocket"), "main");
    public static final ModelLayerLocation TIER_4_LAYER = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "tier_4_rocket"), "main");

    private final ModelPart root;

    public RocketModel(ModelPart root) {
        this.root = root.getChild("main");
    }

    @SuppressWarnings({"unused", "DuplicatedCode"})
    public static LayerDefinition createTier1Layer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition rocket = modelPartData.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0f, 25.0f, 0.0f));

        PartDefinition top = rocket.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 48).addBox(10.0f, -52.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 68).addBox(-10.0f, -52.0f, -10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 48).addBox(-10.0f, -52.0f, -10.0f, 0.0f, 2.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 68).addBox(-10.0f, -52.0f, 10.0f, 20.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(104, 67).addBox(-3.0f, -75.0f, -3.0f, 6.0f, 8.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(88, 69).addBox(-2.0f, -77.0f, -2.0f, 4.0f, 2.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(80, 69).addBox(-1.0f, -89.0f, -1.0f, 2.0f, 13.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(64, 69).addBox(-2.0f, -90.0f, -2.0f, 4.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -1.0f, 0.0f));

        PartDefinition cube_r1 = top.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(120, 37).addBox(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -71.0f, 0.0f, -0.48f, -0.7854f, 0.0f));

        PartDefinition cube_r2 = top.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(120, 37).addBox(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -71.0f, 0.0f, -0.48f, -2.3562f, 0.0f));

        PartDefinition cube_r3 = top.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(120, 37).addBox(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -71.0f, 0.0f, -0.48f, 2.3562f, 0.0f));

        PartDefinition cube_r4 = top.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(120, 37).addBox(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -71.0f, 0.0f, -0.48f, 0.7854f, 0.0f));

        PartDefinition cube_r5 = top.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(65, 45).addBox(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -51.0f, 0.0f, 0.3491f, -1.5708f, 0.0f));

        PartDefinition cube_r6 = top.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(65, 45).addBox(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -51.0f, 0.0f, 0.3491f, 0.0f, 0.0f));

        PartDefinition cube_r7 = top.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(65, 45).addBox(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -51.0f, 0.0f, 0.3491f, 1.5708f, 0.0f));

        PartDefinition cube_r8 = top.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(65, 45).addBox(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -51.0f, 0.0f, 0.3491f, 3.1416f, 0.0f));

        PartDefinition body = rocket.addOrReplaceChild("body", CubeListBuilder.create().texOffs(36, 44).addBox(-6.0f, -42.0f, -10.0f, 12.0f, 12.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(35, 58).addBox(-4.0f, -32.0f, -10.0f, 8.0f, 0.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(63, 43).addBox(4.0f, -40.0f, -10.0f, 0.0f, 8.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(35, 58).addBox(-4.0f, -40.0f, -10.0f, 8.0f, 0.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(63, 43).addBox(-4.0f, -40.0f, -10.0f, 0.0f, 8.0f, 1.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-9.0f, -51.0f, -9.0f, 18.0f, 44.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 77).addBox(-9.0f, -51.0f, -9.0f, 2.0f, 44.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(36, -18).addBox(-9.0f, -51.0f, -9.0f, 0.0f, 44.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(36, 0).addBox(-9.0f, -51.0f, 9.0f, 18.0f, 44.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(36, -18).addBox(9.0f, -51.0f, -9.0f, 0.0f, 44.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(0, 62).addBox(-10.0f, -15.0f, -10.0f, 20.0f, 5.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 42)
            .addBox(10.0f, -15.0f, -10.0f, 0.0f, 5.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(0, 62).addBox(-10.0f, -15.0f, 10.0f, 20.0f, 5.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(0, 42).addBox(-10.0f, -15.0f, -10.0f, 0.0f, 5.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(-18, 44).addBox(-9.0f, -7.0f, -9.0f, 18.0f, 0.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(-18, 44).addBox(-9.0f, -50.0f, -9.0f, 18.0f, 0.0f, 18.0f, new CubeDeformation(0.0f)).texOffs(88, 0).addBox(-13.0f, -17.0f, -3.0f, 4.0f, 9.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(0, 71).addBox(-12.0f, -20.0f, -1.0f, 3.0f, 3.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -1.0f, 0.0f));

        PartDefinition cube_r9 = body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 71).addBox(-12.0f, -21.0f, -1.0f, 3.0f, 3.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(88, 0).addBox(-13.0f, -18.0f, -3.0f, 4.0f, 9.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 1.0f, 0.0f, 0.0f, 3.1416f, 0.0f));

        PartDefinition cube_r10 = body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 77).addBox(-1.0f, -22.0f, -1.0f, 2.0f, 44.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-8.0f, -29.0f, 8.0f, 0.0f, 1.5708f, 0.0f));

        PartDefinition cube_r11 = body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 77).addBox(-1.0f, -22.0f, -1.0f, 2.0f, 44.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(8.0f, -29.0f, 8.0f, 0.0f, 3.1416f, 0.0f));

        PartDefinition cube_r12 = body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 77).addBox(-1.0f, -22.0f, -1.0f, 2.0f, 44.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(8.0f, -29.0f, -8.0f, 0.0f, -1.5708f, 0.0f));

        PartDefinition bottom = body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(94, 15).addBox(-8.0f, -4.0f, -8.0f, 16.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(94, -1).addBox(-8.0f, -4.0f, -8.0f, 0.0f, 4.0f, 16.0f, new CubeDeformation(0.0f)).texOffs(94, 15).addBox(-8.0f, -4.0f, 8.0f, 16.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(94, -1).addBox(8.0f, -4.0f, -8.0f, 0.0f, 4.0f, 16.0f, new CubeDeformation(0.0f)).texOffs(78, 22).addBox(-8.0f, -4.0f, -8.0f, 16.0f, 0.0f, 16.0f, new CubeDeformation(0.0f)).texOffs(80, 81).addBox(-8.0f, 0.0f, -8.0f, 16.0f, 0.0f, 16.0f, new CubeDeformation(0.0f)).texOffs(94, 19).addBox(-6.0f, -7.0f, 6.0f, 12.0f, 3.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(94, 19).addBox(-6.0f, -7.0f, -6.0f, 12.0f, 3.0f, 0.0f, new CubeDeformation(0.0f)).texOffs(94, 7).addBox(6.0f, -7.0f, -6.0f, 0.0f, 3.0f, 12.0f, new CubeDeformation(0.0f)).texOffs(94, 7).addBox(-6.0f, -7.0f, -6.0f, 0.0f, 3.0f, 12.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 0.0f, 0.0f));

        PartDefinition fins = rocket.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(0.0f, -1.0f, 0.0f));

        PartDefinition cube_r13 = fins.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0f, 1.0f, 13.0f, 2.0f, 15.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, 2.0f, -1.0f, 1.1345f, 2.3562f, 0.0f));

        PartDefinition cube_r14 = fins.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0f, -17.0f, 20.0f, 4.0f, 17.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, 1.0f, -1.0f, 0.0f, 2.3562f, 0.0f));

        PartDefinition cube_r15 = fins.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0f, -17.0f, 20.0f, 4.0f, 17.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, 1.0f, -1.0f, 0.0f, -2.3562f, 0.0f));

        PartDefinition cube_r16 = fins.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0f, 1.0f, 13.0f, 2.0f, 15.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, 2.0f, -1.0f, 1.1345f, -2.3562f, 0.0f));

        PartDefinition cube_r17 = fins.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0f, -17.0f, 20.0f, 4.0f, 17.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, 1.0f, 1.0f, 0.0f, -0.7854f, 0.0f));

        PartDefinition cube_r18 = fins.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0f, 1.0f, 13.0f, 2.0f, 15.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.0f, 2.0f, 1.0f, 1.1345f, -0.7854f, 0.0f));

        PartDefinition cube_r19 = fins.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(72, 0).addBox(-2.0f, -17.0f, 20.0f, 4.0f, 17.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, 1.0f, 1.0f, 0.0f, 0.7854f, 0.0f));

        PartDefinition cube_r20 = fins.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(72, 21).addBox(-1.0f, 1.0f, 13.0f, 2.0f, 15.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, 2.0f, 1.0f, 1.1345f, 0.7854f, 0.0f));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @SuppressWarnings({"unused", "DuplicatedCode"})
    public static LayerDefinition createTier2Layer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition rocket2 = modelPartData.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, 0.0f));

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

    @SuppressWarnings({"unused", "DuplicatedCode"})
    public static LayerDefinition createTier3Layer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition rocket3 = modelPartData.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0f, 25.0f, 0.0f));

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

    @SuppressWarnings({"unused", "DuplicatedCode"})
    public static LayerDefinition createTier4Layer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition rocket = modelPartData.addOrReplaceChild("main",
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

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, buffer, packedLight, packedOverlay);
    }

    public static void register(ClientPlatformUtils.LayerDefinitionRegistry registry) {
        registry.register(TIER_1_LAYER, RocketModel::createTier1Layer);
        registry.register(TIER_2_LAYER, RocketModel::createTier2Layer);
        registry.register(TIER_3_LAYER, RocketModel::createTier3Layer);
        registry.register(TIER_4_LAYER, RocketModel::createTier4Layer);
    }
}
