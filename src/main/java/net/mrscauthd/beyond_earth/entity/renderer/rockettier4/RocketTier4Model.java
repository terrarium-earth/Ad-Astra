package net.mrscauthd.beyond_earth.entity.renderer.rockettier4;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.RocketTier4Entity;

@OnlyIn(Dist.CLIENT)
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

        PartDefinition side_booster_right = rocket.addOrReplaceChild("side_booster_right", CubeListBuilder.create().texOffs(0, 147).addBox(-18.0F, -56.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 159).addBox(-19.0F, -50.0F, -4.0F, 8.0F, 35.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 243).addBox(-19.0F, -13.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 248).addBox(-18.0F, -15.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 139).addBox(-11.0F, -25.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 139).addBox(-11.0F, -42.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition side_booster_left = rocket.addOrReplaceChild("side_booster_left", CubeListBuilder.create().texOffs(0, 147).addBox(11.0F, -56.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(32, 159).addBox(10.0F, -50.0F, -4.0F, 8.0F, 35.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 248).addBox(11.0F, -15.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 243).addBox(10.0F, -13.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 139).addBox(8.0F, -42.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 139).addBox(8.0F, -25.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, 0.0F));

        PartDefinition main = rocket.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 28).addBox(-9.0F, -74.0F, -9.0F, 18.0F, 59.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(54, 39).addBox(-6.0F, -26.0F, -10.0F, 12.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 105).addBox(-9.0F, -16.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(31, 0).addBox(-4.0F, -54.0F, -9.5F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(31, 3).addBox(-4.0F, -44.0F, -9.5F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 6).addBox(-6.0F, -54.0F, -9.5F, 2.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(38, 6).addBox(4.0F, -54.0F, -9.5F, 2.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(54, 39).addBox(-6.0F, -26.0F, -10.0F, 12.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition fins = main.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(-2.0F, -8.0F, -2.0F));

        PartDefinition pyramid = main.addOrReplaceChild("pyramid", CubeListBuilder.create().texOffs(88, 0).addBox(-10.0F, -58.0F, -10.0F, 20.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition cube_r2 = pyramid.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(80, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 0.0F, -0.3491F, -2.3562F, 0.0F));

        PartDefinition cube_r3 = pyramid.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(80, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 0.0F, -0.3491F, 2.3562F, 0.0F));

        PartDefinition cube_r4 = pyramid.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(80, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 0.0F, -0.3491F, -0.7854F, 0.0F));

        PartDefinition cube_r5 = pyramid.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(80, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 0.0F, -0.3491F, 0.7854F, 0.0F));

        PartDefinition cube_r6 = pyramid.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -57.0F, 0.0F, -2.8798F, -1.5708F, 3.1416F));

        PartDefinition cube_r7 = pyramid.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -57.0F, 0.0F, -2.8798F, 1.5708F, 3.1416F));

        PartDefinition cube_r8 = pyramid.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -57.0F, 0.0F, -2.8798F, 3.1416F, 3.1416F));

        PartDefinition cube_r9 = pyramid.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -57.0F, 0.0F, -2.8798F, 0.0F, 3.1416F));

        PartDefinition booster = main.addOrReplaceChild("booster", CubeListBuilder.create().texOffs(0, 225).addBox(-6.0F, -15.0F, -6.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 202).addBox(-7.0F, -9.0F, -7.0F, 14.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tip = main.addOrReplaceChild("tip", CubeListBuilder.create().texOffs(224, 32).addBox(-4.0F, -101.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(232, 0).addBox(-3.0F, -109.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(240, 14).addBox(-2.0F, -111.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(240, 14).addBox(-2.0F, -123.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.375F))
                .texOffs(240, 14).addBox(-2.0F, -114.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(80, 0).addBox(-1.0F, -121.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
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
