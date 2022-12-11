package earth.terrarium.ad_astra.client.renderer.entity.mob.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.entity.mob.StarCrawler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class StarCrawlerModel extends EntityModel<StarCrawler> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "star_crawler"), "main");

    private final ModelPart body;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;

    public StarCrawlerModel(ModelPart root) {
        this.body = root.getChild("body");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
        this.leg4 = root.getChild("leg4");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 27).addBox(-16.0F, -9.0F, -6.0F, 8.0F, 9.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 51).addBox(-24.0F, -7.0F, -5.0F, 8.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(28, 61).addBox(-29.0F, -5.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = leg1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(51, 44).addBox(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, 0.804F, -5.8016F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r2 = leg1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.5F, -3.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.0F, 0.804F, -4.8016F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r3 = leg1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(23, 32).addBox(-4.0F, -2.5F, -3.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.0F, 1.3216F, 6.7334F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r4 = leg1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(65, 44).addBox(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, 1.3216F, 7.7334F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r5 = leg1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(21, 48).addBox(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 1.3216F, 8.7334F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r6 = leg1.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(23, 27).addBox(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 0.804F, -6.8016F, 0.2618F, 0.0F, 0.0F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(40, 27).addBox(-6.0F, -9.0F, 8.0F, 12.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(58, 53).addBox(-5.0F, -7.0F, 16.0F, 10.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(54, 68).addBox(-4.0F, -5.0F, 24.0F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r7 = leg2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 23).addBox(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4146F, -1.352F, 24.5F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r8 = leg2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.4827F, -1.8696F, 24.5F, 0.0F, 0.0F, 1.8326F));

        PartDefinition cube_r9 = leg2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(56, 42).addBox(0.0F, -2.5F, -3.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4486F, -1.6108F, 19.5F, 0.0F, 0.0F, 1.8326F));

        PartDefinition cube_r10 = leg2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 5).addBox(0.0F, -2.5F, -3.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.4146F, -1.352F, 19.5F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r11 = leg2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.5F, -3.5F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4146F, -1.352F, 12.5F, 0.0F, 0.0F, 1.8326F));

        PartDefinition cube_r12 = leg2.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(26, 67).addBox(0.0F, -2.5F, -3.5F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4146F, -1.352F, 12.5F, 0.0F, 0.0F, 1.309F));

        PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(48, 0).addBox(-5.0F, -7.0F, -24.0F, 10.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(64, 15).addBox(-4.0F, -5.0F, -29.0F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(40, 27).addBox(-6.0F, -9.0F, -16.0F, 12.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r13 = leg3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.4827F, -1.8696F, -27.5F, 0.0F, 0.0F, 1.8326F));

        PartDefinition cube_r14 = leg3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(56, 42).addBox(0.0F, -2.5F, -3.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.4486F, -1.6108F, -19.5F, 0.0F, 0.0F, 1.8326F));

        PartDefinition cube_r15 = leg3.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(26, 67).addBox(0.0F, -2.5F, -3.5F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.4146F, -1.352F, -12.5F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r16 = leg3.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 23).addBox(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4146F, -1.352F, -27.5F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r17 = leg3.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 5).addBox(0.0F, -2.5F, -3.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.4146F, -1.352F, -19.5F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r18 = leg3.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.5F, -3.5F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.4146F, -1.352F, -11.5F, 0.0F, 0.0F, 1.8326F));

        PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(30, 44).addBox(16.0F, -7.0F, -5.0F, 8.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 68).addBox(24.0F, -5.0F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 27).addBox(8.0F, -9.0F, -6.0F, 8.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r19 = leg4.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(23, 32).addBox(-4.0F, -2.5F, -3.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, 1.3216F, 6.7334F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r20 = leg4.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.5F, -3.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, 0.804F, -4.8016F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r21 = leg4.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(65, 44).addBox(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.0F, 1.3216F, 7.7334F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r22 = leg4.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(51, 44).addBox(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(23.0F, 0.804F, -5.8016F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r23 = leg4.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(21, 48).addBox(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 1.3216F, 8.7334F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r24 = leg4.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(23, 27).addBox(-7.0F, -2.5F, -3.5F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 0.804F, -6.8016F, 0.2618F, 0.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -11.0F, -8.0F, 16.0F, 11.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
   public void setupAnim(StarCrawler entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.leg1.yRot = Mth.cos(limbAngle * 0.6662f) * limbDistance;
        this.leg2.yRot = Mth.cos(limbAngle * 0.6662f) * limbDistance;
        this.leg3.yRot = Mth.cos(limbAngle * 0.6662f) * limbDistance;
        this.leg4.yRot = Mth.cos(limbAngle * 0.6662f) * limbDistance;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertices, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertices, packedLight, packedOverlay);
        leg1.render(poseStack, vertices, packedLight, packedOverlay);
        leg2.render(poseStack, vertices, packedLight, packedOverlay);
        leg3.render(poseStack, vertices, packedLight, packedOverlay);
        leg4.render(poseStack, vertices, packedLight, packedOverlay);
    }
}
