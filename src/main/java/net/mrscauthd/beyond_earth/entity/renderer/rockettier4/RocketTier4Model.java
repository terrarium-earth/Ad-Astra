package net.mrscauthd.beyond_earth.entity.renderer.rockettier4;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.RocketTier4Entity;

public class RocketTier4Model<T extends RocketTier4Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondEarthMod.MODID, "rocket_t4"), "main");
    private final ModelPart rocket;

    public RocketTier4Model(ModelPart root) {
        this.rocket = root.getChild("rocket");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rocket = partdefinition.addOrReplaceChild("rocket", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition side_booster_right = rocket.addOrReplaceChild("side_booster_right", CubeListBuilder.create().texOffs(92, 53).addBox(-16.0F, -56.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(32, 82).addBox(-17.0F, -50.0F, -4.0F, 8.0F, 35.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(88, 90).addBox(-17.0F, -13.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(88, 82).addBox(-16.0F, -15.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-9.0F, -23.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(64, 28).addBox(-9.0F, -46.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition side_booster_left = rocket.addOrReplaceChild("side_booster_left", CubeListBuilder.create().texOffs(48, 0).addBox(10.0F, -56.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 73).addBox(9.0F, -50.0F, -4.0F, 8.0F, 35.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(24, 73).addBox(10.0F, -15.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(64, 46).addBox(9.0F, -13.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(6, 4).addBox(8.0F, -46.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(8.0F, -23.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition main = rocket.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -72.0F, -8.0F, 16.0F, 57.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(64, 0).addBox(-6.0F, -88.0F, -6.0F, 12.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Booster = main.addOrReplaceChild("booster", CubeListBuilder.create().texOffs(64, 28).addBox(-6.0F, -15.0F, -6.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(50, 59).addBox(-7.0F, -9.0F, -7.0F, 14.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Tip = main.addOrReplaceChild("tip", CubeListBuilder.create().texOffs(64, 98).addBox(-2.0F, -112.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(64, 82).addBox(-4.0F, -96.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.rocket.yRot = netHeadYaw / (180F / (float) Math.PI);

        this.rocket.zRot = (float) entity.ay;

        this.rocket.xRot = (float) entity.ap;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rocket.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
