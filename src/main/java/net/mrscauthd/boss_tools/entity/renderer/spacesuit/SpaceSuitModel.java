package net.mrscauthd.boss_tools.entity.renderer.spacesuit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.mrscauthd.boss_tools.BossToolsMod;

public class SpaceSuitModel {
    public static class SPACE_SUIT_P1<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidModel<T> {

        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BossToolsMod.ModId, "space_suit_p1"), "main");

        public static ModelPart Head;
        public static ModelPart Body;
        public static ModelPart Right_Arm;
        public static ModelPart Left_Arm;
        public static ModelPart Left_Foot;
        public static ModelPart Right_Foot;

        public SPACE_SUIT_P1(ModelPart root) {
            super(root);
            this.Head = root.getChild("Head");
            this.Body = root.getChild("Body");
            this.Right_Arm = root.getChild("Right_Arm");
            this.Left_Arm = root.getChild("Left_Arm");
            this.Left_Foot = root.getChild("Left_Foot");
            this.Right_Foot = root.getChild("Right_Foot");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0175F, 0.0873F, 0.0F));

            PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(28, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
            .texOffs(50, 29).addBox(-3.0F, 5.0F, -2.5F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.25F))
            .texOffs(0, 55).addBox(-2.5F, 1.0F, 2.55F, 5.0F, 8.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition Right_Arm = partdefinition.addOrReplaceChild("Right_Arm", CubeListBuilder.create().texOffs(20, 44).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

            PartDefinition Left_Arm = partdefinition.addOrReplaceChild("Left_Arm", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

            PartDefinition Left_Foot = partdefinition.addOrReplaceChild("Left_Foot", CubeListBuilder.create().texOffs(48, 44).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F))
            .texOffs(48, 54).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

            PartDefinition Right_Foot = partdefinition.addOrReplaceChild("Right_Foot", CubeListBuilder.create().texOffs(48, 44).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F))
            .texOffs(48, 54).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            Head.render(poseStack, buffer, packedLight, packedOverlay);
            Body.render(poseStack, buffer, packedLight, packedOverlay);
            Right_Arm.render(poseStack, buffer, packedLight, packedOverlay);
            Left_Arm.render(poseStack, buffer, packedLight, packedOverlay);
            Left_Foot.render(poseStack, buffer, packedLight, packedOverlay);
            Right_Foot.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }

    public static class SPACE_SUIT_P2<T extends Entity> extends EntityModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BossToolsMod.ModId, "space_suit_p2"), "main");

        public final ModelPart Right_Leg;
        public final ModelPart Left_Leg;

        public SPACE_SUIT_P2(ModelPart root) {
            this.Right_Leg = root.getChild("Right_Leg");
            this.Left_Leg = root.getChild("Left_Leg");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition RightLeg = partdefinition.addOrReplaceChild("Right_Leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

            PartDefinition LeftLeg = partdefinition.addOrReplaceChild("Left_Leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 32);
        }

        @Override
        public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            Right_Leg.render(poseStack, buffer, packedLight, packedOverlay);
            Left_Leg.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }
}
