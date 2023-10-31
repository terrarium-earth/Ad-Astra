package earth.terrarium.ad_astra.client.renderer.armor;

import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class JetSuitModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "jet_suit"), "main");

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.6f)), PartPose.offset(0.0f, 0.0f, 0.0f));
        PartDefinition hat = modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);

        PartDefinition visor = modelPartData.addOrReplaceChild("visor", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(1.0f)), PartPose.offset(0.0f, 0.0f, 0.0f));

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.6f))
                .texOffs(32, 48).addBox(-4.0f, -1.0f, 3.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.5f))
                .texOffs(32, 32).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.9f)), PartPose.offset(1.0f, 0.5f, 0.0f));
        PartDefinition belt = modelPartData.addOrReplaceChild("belt", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.5f)), PartPose.offset(0.0f, -0.5f, 0.0f));

        PartDefinition backpack = body.addOrReplaceChild("backpack", CubeListBuilder.create(), PartPose.offset(0.0f, 5.0f, 5.0f));

        PartDefinition right_arm = modelPartData.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-4.5f, 2.0f, 0.0f));

        PartDefinition cube_r1 = right_arm.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 32).addBox(-2.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f))
                .texOffs(16, 48).addBox(-2.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.6f)), PartPose.offsetAndRotation(-1.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        PartDefinition left_arm = modelPartData.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(4.5f, 2.0f, 0.0f));

        PartDefinition cube_r2 = left_arm.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 32).mirror().addBox(-1.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f))
                .texOffs(16, 48).addBox(-1.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.6f)), PartPose.offsetAndRotation(1.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        PartDefinition right_leg = modelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.7f)), PartPose.offset(-1.9f, 12.0f, 0.0f));

        PartDefinition left_leg = modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.7f)), PartPose.offset(1.9f, 12.0f, 0.0f));

        PartDefinition right_boot = modelPartData.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f)), PartPose.offset(-1.9f, 12.0f, 0.0f));

        PartDefinition left_boot = modelPartData.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f)), PartPose.offset(1.9f, 12.0f, 0.0f));

        return LayerDefinition.create(modelData, 64, 64);
    }
}