package net.mrscauthd.beyond_earth.entity.renderer.spacesuit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.mrscauthd.beyond_earth.BeyondEarthMod;

public class SpaceSuitModel {
    public static class SPACE_SUIT_P1<T extends LivingEntity> extends EntityModel<T> {

        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondEarthMod.MODID, "space_suit_p1"), "main");

        public final ModelPart Head;
        public final ModelPart Body;
        public final ModelPart Right_Arm;
        public final ModelPart Left_Arm;
        public final ModelPart Left_Boots;
        public final ModelPart Right_Boots;

        public SPACE_SUIT_P1(ModelPart root) {
            this.Head = root.getChild("head");
            this.Body = root.getChild("body");
            this.Right_Arm = root.getChild("right_arm");
            this.Left_Arm = root.getChild("left_arm");
            this.Left_Boots = root.getChild("left_boots");
            this.Right_Boots = root.getChild("right_boots");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
            .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.75F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0175F, 0.0873F, 0.0F));

            PartDefinition Body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
            .texOffs(28, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
            .texOffs(50, 29).addBox(-3.0F, 5.0F, -2.5F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.25F))
            .texOffs(0, 55).addBox(-2.5F, 1.0F, 2.55F, 5.0F, 8.0F, 1.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition Right_Arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(20, 44).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

            PartDefinition Left_Arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)), PartPose.offset(5.0F, 2.0F, 0.0F));

            PartDefinition Left_Boots = partdefinition.addOrReplaceChild("left_boots", CubeListBuilder.create().texOffs(48, 44).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.4F))
            .texOffs(48, 54).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.27F)), PartPose.offset(2.0F, 12.0F, 0.0F));

            PartDefinition Right_Boots = partdefinition.addOrReplaceChild("right_boots", CubeListBuilder.create().texOffs(48, 44).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.4F))
            .texOffs(48, 54).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.27F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

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
            Left_Boots.render(poseStack, buffer, packedLight, packedOverlay);
            Right_Boots.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }

    public static class SPACE_SUIT_P2<T extends Entity> extends EntityModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondEarthMod.MODID, "space_suit_p2"), "main");

        public final ModelPart Right_Leg;
        public final ModelPart Left_Leg;

        public SPACE_SUIT_P2(ModelPart root) {
            this.Right_Leg = root.getChild("right_leg");
            this.Left_Leg = root.getChild("left_leg");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition Right_Leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

            PartDefinition Left_Leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

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
