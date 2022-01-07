package net.mrscauthd.beyond_earth.entity.renderer.martianraptor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.MartianRaptor;

public class MartianRaptorModel<T extends MartianRaptor> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondEarthMod.MODID, "martian_raptor"), "main");

    private final ModelPart body;
    private final ModelPart leg1;
    private final ModelPart leg2;

    public MartianRaptorModel(ModelPart root) {
        this.body = root.getChild("body");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(14, 69).addBox(-2.0F, 11.7065F, -5.0813F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 9.0F, 4.0F));

        PartDefinition cube_r1 = leg1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(36, 64).addBox(-2.5F, -7.0F, -2.5F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 7.7065F, 2.4187F, -0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r2 = leg1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 58).addBox(-2.5F, -6.0F, -2.5F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.2935F, 2.4187F, 0.6545F, 0.0F, 0.0F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(14, 69).mirror().addBox(-3.0F, 11.7065F, -5.0813F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, 9.0F, 4.0F));

        PartDefinition cube_r3 = leg2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(36, 64).mirror().addBox(-2.5F, -7.0F, -2.5F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.7065F, 2.4187F, -0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r4 = leg2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 58).mirror().addBox(-2.5F, -6.0F, -2.5F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -0.2935F, 2.4187F, 0.6545F, 0.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -24.0F, -6.0F, 8.0F, 10.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -19.0F, -3.0F));

        PartDefinition bone2 = head.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(34, 34).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -8.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition bone = bone2.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.25F, -6.75F, 6.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r5 = bone.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 42).addBox(-6.0F, -5.5F, -4.5F, 10.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2012F, 0.0F, -0.1198F, -0.0436F, 0.6981F, 0.0F));

        PartDefinition cube_r6 = bone.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-4.0F, -5.5F, -4.5F, 10.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.7988F, 0.0F, -0.1198F, -0.0436F, -0.6981F, 0.0F));

        PartDefinition mouth1 = bone2.addOrReplaceChild("mouth1", CubeListBuilder.create(), PartPose.offset(-2.1711F, 4.4542F, 3.0357F));

        PartDefinition cube_r7 = mouth1.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(52, 71).addBox(-1.5789F, -1.5855F, -2.25F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition mouth2 = bone2.addOrReplaceChild("mouth2", CubeListBuilder.create(), PartPose.offset(1.8289F, 4.4542F, 3.0357F));

        PartDefinition cube_r8 = mouth2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(52, 71).mirror().addBox(-1.0789F, -1.4145F, -2.25F, 3.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -19.0F, 9.0F));

        PartDefinition cube_r9 = tail.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(20, 50).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.1366F, 17.1501F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r10 = tail.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(31, 0).addBox(-3.0F, -2.5F, -5.0F, 6.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.8909F, 9.0849F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r11 = tail.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(63, 7).addBox(-2.5F, -1.0F, -1.5F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.4973F, 16.5505F, 1.7017F, 0.0F, 0.0F));

        PartDefinition cube_r12 = tail.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(61, 31).addBox(-3.5F, -1.0F, -2.5F, 7.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.7473F, 12.5505F, 1.6144F, 0.0F, 0.0F));

        PartDefinition cube_r13 = tail.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(24, 26).addBox(-10.5F, -2.0F, -5.5F, 8.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, 2.7065F, 5.4187F, 1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r14 = tail.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(53, 0).addBox(-10.5F, -2.0F, -5.5F, 8.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, 3.7065F, 9.4187F, 1.3526F, 0.0F, 0.0F));

        PartDefinition cube_r15 = tail.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 25).addBox(-3.5F, -3.5F, -5.0F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.4079F, 1.7286F, -0.6545F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.leg1.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
        this.leg2.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;

        this.body.getChild("head").yRot = netHeadYaw / (180F / (float) Math.PI);
        this.body.getChild("head").xRot = headPitch / (180F / (float) Math.PI);

        int i = entity.getAttackAnimationTick();
        if (i > 0) {
            System.out.println(entity.getAttackAnimationTick());
            this.body.getChild("head").getChild("bone2").getChild("mouth1").xRot = -2.0F + 1.5F * Mth.triangleWave((float)i - limbSwing, 10.0F);
            this.body.getChild("head").getChild("bone2").getChild("mouth2").xRot = -2.0F + 1.5F * Mth.triangleWave((float)i - limbSwing, 10.0F);
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, buffer, packedLight, packedOverlay);
        leg1.render(poseStack, buffer, packedLight, packedOverlay);
        leg2.render(poseStack, buffer, packedLight, packedOverlay);
    }
}