package net.mrscauthd.boss_tools.entity.renderer.rockettier1;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

@OnlyIn(Dist.CLIENT)
public class RocketTier1Model<T extends RocketTier1Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BossToolsMod.ModId, "rocket_t1"), "main");
    private final ModelPart rocket;

    public RocketTier1Model(ModelPart root) {
        this.rocket = root.getChild("rocket");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rocket = partdefinition.addOrReplaceChild("rocket", CubeListBuilder.create().texOffs(0, 167).addBox(-12.0F, -21.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(184, 0).addBox(-9.0F, -52.0F, -9.0F, 18.0F, 44.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(184, 62).addBox(-9.0F, -10.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(36, 23).addBox(-2.0F, -78.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-2.0F, -91.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.375F))
                .texOffs(36, 8).addBox(-1.0F, -90.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 29).addBox(-3.0F, -76.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(22, 128).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 85).addBox(-10.0F, -13.0F, -10.0F, 20.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 85).addBox(-10.0F, -16.0F, -10.0F, 20.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(22, 116).addBox(-5.0F, -7.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(22, 107).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(230, 88).addBox(-6.0F, -43.0F, -9.5F, 12.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(238, 101).addBox(-4.0F, -41.0F, -9.5F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 152).addBox(-13.0F, -18.0F, -3.0F, 4.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition cube_r1 = rocket.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 167).addBox(-12.0F, -21.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 152).addBox(-13.0F, -18.0F, -3.0F, 4.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r2 = rocket.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -72.0F, 0.0F, -0.48F, 0.7854F, 0.0F));

        PartDefinition cube_r3 = rocket.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -72.0F, 0.0F, -0.48F, -2.3562F, 0.0F));

        PartDefinition cube_r4 = rocket.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -72.0F, 0.0F, -0.48F, 2.3562F, 0.0F));

        PartDefinition cube_r5 = rocket.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -72.0F, 0.0F, -0.48F, -0.7854F, 0.0F));

        PartDefinition cube_r6 = rocket.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -52.0F, 0.0F, 0.3491F, 1.5708F, 0.0F));

        PartDefinition cube_r7 = rocket.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -52.0F, 0.0F, 0.3491F, 3.1416F, 0.0F));

        PartDefinition cube_r8 = rocket.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -52.0F, 0.0F, 0.3491F, -1.5708F, 0.0F));

        PartDefinition cube_r9 = rocket.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -52.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r10 = rocket.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 131).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 1.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r11 = rocket.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 107).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 1.0F, 1.1345F, -0.7854F, 0.0F));

        PartDefinition cube_r12 = rocket.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 131).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -1.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r13 = rocket.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 107).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -1.0F, 1.1345F, -2.3562F, 0.0F));

        PartDefinition cube_r14 = rocket.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 131).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 1.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r15 = rocket.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 107).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 1.0F, 1.1345F, 0.7854F, 0.0F));

        PartDefinition cube_r16 = rocket.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(20, 142).addBox(-3.0F, -18.0F, 20.4142F, 6.0F, 5.0F, 6.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r17 = rocket.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(20, 142).addBox(-3.0F, -18.0F, 20.4142F, 6.0F, 5.0F, 6.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r18 = rocket.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(20, 142).addBox(-3.0F, -18.0F, 20.4142F, 6.0F, 5.0F, 6.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r19 = rocket.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(20, 142).addBox(-3.0F, -18.0F, 20.4142F, 6.0F, 5.0F, 6.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r20 = rocket.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 131).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -1.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r21 = rocket.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 107).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -1.0F, 1.1345F, 2.3562F, 0.0F));

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
