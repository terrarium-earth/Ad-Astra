package net.mrscauthd.beyond_earth.entity.renderer.starcrawler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.mrscauthd.beyond_earth.entity.StarCrawlerEntity;

@OnlyIn(Dist.CLIENT)
public class StarCrawlerModel<T extends StarCrawlerEntity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "starcrawler"), "main");

    private final ModelPart Body;
    private final ModelPart arm1g;
    private final ModelPart arm2g;
    private final ModelPart arm3g;
    private final ModelPart arm4g;

    public StarCrawlerModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.arm1g = root.getChild("arm1g");
        this.arm2g = root.getChild("arm2g");
        this.arm3g = root.getChild("arm3g");
        this.arm4g = root.getChild("arm4g");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -13.0F, -8.0F, 16.0F, 10.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-7.0F, -9.0F, -7.0F, 14.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition arm1g = partdefinition.addOrReplaceChild("arm1g", CubeListBuilder.create(), PartPose.offset(0.0F, 18.3F, 6.75F));

        PartDefinition Arm1 = arm1g.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(48, 48).addBox(-6.0F, -5.2F, 0.25F, 12.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(42, 26).addBox(-5.0F, 2.8F, -1.75F, 10.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = Arm1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(48, 64).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 3.0F, 4.25F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition cube_r2 = Arm1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(58, 64).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 3.0F, 3.25F, 0.0F, 0.0F, 0.3491F));

        PartDefinition Limb1 = arm1g.addOrReplaceChild("Limb1", CubeListBuilder.create().texOffs(48, 0).addBox(-5.0F, -4.3F, 0.25F, 10.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 64).addBox(-4.0F, 2.7F, -0.75F, 8.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.0F));

        PartDefinition cube_r3 = Limb1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(59, 38).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 3.0F, 5.25F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition cube_r4 = Limb1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(57, 16).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 3.0F, 4.25F, 0.0F, 0.0F, 0.3491F));

        PartDefinition Hand1 = arm1g.addOrReplaceChild("Hand1", CubeListBuilder.create().texOffs(0, 49).addBox(-4.0F, -3.3F, 0.25F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(25, 55).addBox(-3.0F, 2.7F, -0.75F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.1F, 15.0F));

        PartDefinition cube_r5 = Hand1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(39, 49).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.9F, 5.25F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition cube_r6 = Hand1.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(49, 38).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.9F, 4.25F, 0.0F, 0.0F, 0.3491F));

        PartDefinition arm2g = partdefinition.addOrReplaceChild("arm2g", CubeListBuilder.create(), PartPose.offset(0.0F, 18.4F, -7.75F));

        PartDefinition Arm2 = arm2g.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(48, 48).addBox(-6.0F, -5.2F, 0.25F, 12.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(42, 26).addBox(-5.0F, 2.8F, -1.75F, 10.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1F, 1.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r7 = Arm2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(48, 64).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 3.0F, 4.25F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition cube_r8 = Arm2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(58, 64).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 3.0F, 3.25F, 0.0F, 0.0F, 0.3491F));

        PartDefinition Limb2 = arm2g.addOrReplaceChild("Limb2", CubeListBuilder.create().texOffs(48, 0).addBox(-5.0F, -4.3F, 0.25F, 10.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 64).addBox(-4.0F, 2.7F, -0.75F, 8.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1F, -6.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r9 = Limb2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(59, 38).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 3.0F, 5.25F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition cube_r10 = Limb2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(57, 16).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 3.0F, 4.25F, 0.0F, 0.0F, 0.3491F));

        PartDefinition Hand2 = arm2g.addOrReplaceChild("Hand2", CubeListBuilder.create().texOffs(0, 49).addBox(-4.0F, -3.3F, -4.75F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(25, 55).addBox(-3.0F, 2.7F, -3.75F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -19.0F));

        PartDefinition cube_r11 = Hand2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(39, 49).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.9F, 0.25F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition cube_r12 = Hand2.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(49, 38).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.9F, -0.75F, 0.0F, 0.0F, 0.3491F));

        PartDefinition arm3g = partdefinition.addOrReplaceChild("arm3g", CubeListBuilder.create(), PartPose.offset(-7.0F, 24.0F, 0.0F));

        PartDefinition Arm3 = arm3g.addOrReplaceChild("Arm3", CubeListBuilder.create().texOffs(48, 48).addBox(-6.0F, -5.2F, 0.25F, 12.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(42, 26).addBox(-5.0F, 2.8F, -1.75F, 10.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -5.7F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r13 = Arm3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(48, 64).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 3.0F, 4.25F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition cube_r14 = Arm3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(58, 64).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 3.0F, 3.25F, 0.0F, 0.0F, 0.3491F));

        PartDefinition Limb3 = arm3g.addOrReplaceChild("Limb3", CubeListBuilder.create().texOffs(48, 0).addBox(-5.0F, -4.3F, 0.25F, 10.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 64).addBox(-4.0F, 2.7F, -0.75F, 8.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.75F, -5.7F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r15 = Limb3.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(59, 38).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 3.0F, 5.25F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition cube_r16 = Limb3.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(57, 16).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 3.0F, 4.25F, 0.0F, 0.0F, 0.3491F));

        PartDefinition Hand3 = arm3g.addOrReplaceChild("Hand3", CubeListBuilder.create().texOffs(0, 49).addBox(-4.0F, -3.3F, 0.25F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(25, 55).addBox(-3.0F, 2.7F, -0.75F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.75F, -5.6F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r17 = Hand3.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(39, 49).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.9F, 5.25F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition cube_r18 = Hand3.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(49, 38).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.9F, 4.25F, 0.0F, 0.0F, 0.3491F));

        PartDefinition arm4g = partdefinition.addOrReplaceChild("arm4g", CubeListBuilder.create(), PartPose.offset(8.0F, 24.0F, 0.0F));

        PartDefinition Arm4 = arm4g.addOrReplaceChild("Arm4", CubeListBuilder.create().texOffs(48, 48).addBox(-6.0F, -5.2F, 0.25F, 12.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(42, 26).addBox(-5.0F, 2.8F, -1.75F, 10.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, -5.7F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r19 = Arm4.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(48, 64).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 3.0F, 4.25F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition cube_r20 = Arm4.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(58, 64).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 3.0F, 3.25F, 0.0F, 0.0F, 0.3491F));

        PartDefinition Limb4 = arm4g.addOrReplaceChild("Limb4", CubeListBuilder.create().texOffs(48, 0).addBox(-5.0F, -4.3F, 0.25F, 10.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 64).addBox(-4.0F, 2.7F, -0.75F, 8.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.75F, -5.7F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r21 = Limb4.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(59, 38).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 3.0F, 5.25F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition cube_r22 = Limb4.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(57, 16).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 3.0F, 4.25F, 0.0F, 0.0F, 0.3491F));

        PartDefinition Hand4 = arm4g.addOrReplaceChild("Hand4", CubeListBuilder.create().texOffs(0, 49).addBox(-4.0F, -3.3F, 0.25F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(25, 55).addBox(-3.0F, 2.7F, -0.75F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.75F, -5.6F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r23 = Hand4.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(39, 49).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.9F, 5.25F, 0.0F, 3.1416F, -0.3491F));

        PartDefinition cube_r24 = Hand4.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(49, 38).addBox(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.9F, 4.25F, 0.0F, 0.0F, 0.3491F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float f, float f1, float f2, float f3, float f4) {
        // arm1
        this.arm1g.getChild("Hand1").yRot = Mth.cos(f * 0.6662F) * f1;
        this.arm2g.getChild("Hand2").yRot = Mth.cos(f * 0.6662F) * f1;
        this.arm3g.getChild("Hand3").yRot = 80.115f + Mth.cos(f * 0.6662F) * f1;
        this.arm4g.getChild("Hand4").yRot = -80.115f + Mth.cos(f * 0.6662F) * f1;
        // arm
        this.arm1g.yRot = Mth.cos(f * 0.6662F) * f1;
        this.arm2g.yRot = Mth.cos(f * 0.6662F) * f1;
        this.arm3g.yRot = Mth.cos(f * 0.6662F) * f1;
        this.arm4g.yRot = Mth.cos(f * 0.6662F) * f1;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(poseStack, buffer, packedLight, packedOverlay);
        arm1g.render(poseStack, buffer, packedLight, packedOverlay);
        arm2g.render(poseStack, buffer, packedLight, packedOverlay);
        arm3g.render(poseStack, buffer, packedLight, packedOverlay);
        arm4g.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
