package net.mrscauthd.beyond_earth.entity.renderer.spacesuit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.rendertype.TranslucentArmorRenderType;

public class SpaceSuitModel {
    @OnlyIn(Dist.CLIENT)
    public static class SPACE_SUIT_P1<T extends LivingEntity> extends HumanoidModel<T> {

        private static ResourceLocation spaceSuit = new ResourceLocation(BeyondEarthMod.MODID, "textures/models/armor/space_suit_head.png");
        private static ResourceLocation netheriteSpaceSuit = new ResourceLocation(BeyondEarthMod.MODID, "textures/models/armor/netherite_space_suit_head.png");

        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondEarthMod.MODID, "space_suit_p1"), "main");

        public LivingEntity entity;

        public final ModelPart head;
        public final ModelPart body;
        public final ModelPart rightArm;
        public final ModelPart leftArm;
        public final ModelPart rightLeg;
        public final ModelPart leftLeg;

        public SPACE_SUIT_P1(ModelPart root) {
            super(new EntityRendererProvider.Context(Minecraft.getInstance().getEntityRenderDispatcher(), Minecraft.getInstance().getItemRenderer(), Minecraft.getInstance().getResourceManager(), Minecraft.getInstance().getEntityModels(), Minecraft.getInstance().font).bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
            this.head = root.getChild("head");
            this.body = root.getChild("body");
            this.rightArm = root.getChild("right_arm");
            this.leftArm = root.getChild("left_arm");
            this.rightLeg = root.getChild("right_leg");
            this.leftLeg = root.getChild("left_leg");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
            .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.75F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0175F, 0.0873F, 0.0F));

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
            .texOffs(28, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
            .texOffs(50, 29).addBox(-3.0F, 5.0F, -2.5F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.25F))
            .texOffs(0, 55).addBox(-2.5F, 1.0F, 2.55F, 5.0F, 8.0F, 1.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(20, 44).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

            PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)), PartPose.offset(5.0F, 2.0F, 0.0F));

            PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(48, 44).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.4F))
            .texOffs(48, 54).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.27F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

            PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(48, 44).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.4F))
                    .texOffs(48, 54).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.27F)), PartPose.offset(2.0F, 12.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            EquipmentSlot slot = EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, 3);
            ItemStack stack = entity.getItemBySlot(slot);

            /**Setup Anim*/
            HumanoidModel livingModel = (HumanoidModel<LivingEntity>) ((LivingEntityRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity)).getModel();

            this.attackTime = livingModel.attackTime;
            this.riding = livingModel.riding;
            this.young = livingModel.young;
            this.leftArmPose = livingModel.leftArmPose;
            this.rightArmPose = livingModel.rightArmPose;
            this.crouching = livingModel.crouching;
            this.head.copyFrom(livingModel.head);
            this.body.copyFrom(livingModel.body);
            this.rightArm.copyFrom(livingModel.rightArm);
            this.leftArm.copyFrom(livingModel.leftArm);
            this.rightLeg.copyFrom(livingModel.rightLeg);
            this.leftLeg.copyFrom(livingModel.leftLeg);

            /**Not Translucent Armor Parts*/
            body.render(poseStack, buffer, packedLight, packedOverlay);
            rightArm.render(poseStack, buffer, packedLight, packedOverlay);
            leftArm.render(poseStack, buffer, packedLight, packedOverlay);
            rightLeg.render(poseStack, buffer, packedLight, packedOverlay);
            leftLeg.render(poseStack, buffer, packedLight, packedOverlay);

            /**Translucent Armor Parts*/
            if (stack.getItem() == ModInit.OXYGEN_MASK.get()) {
                head.render(poseStack, this.getVertex(TranslucentArmorRenderType.armorCutoutNoCull(spaceSuit), false, stack.isEnchanted()), packedLight, packedOverlay);
            } else if (stack.getItem() == ModInit.NETHERITE_OXYGEN_MASK.get()) {
                head.render(poseStack, this.getVertex(TranslucentArmorRenderType.armorCutoutNoCull(netheriteSpaceSuit), false, stack.isEnchanted()), packedLight, packedOverlay);
            } else {
                head.render(poseStack, buffer, packedLight, packedOverlay);
            }
        }

        public VertexConsumer getVertex(RenderType p_115186_, boolean p_115187_, boolean p_115188_) {
            MultiBufferSource p_115185_ = Minecraft.getInstance().renderBuffers().bufferSource();
            return p_115188_ ? VertexMultiConsumer.create(p_115185_.getBuffer(p_115187_ ? RenderType.armorGlint() : RenderType.armorEntityGlint()), p_115185_.getBuffer(p_115186_)) : p_115185_.getBuffer(p_115186_);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class SPACE_SUIT_P2<T extends LivingEntity> extends HumanoidModel<T> {

        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondEarthMod.MODID, "space_suit_p2"), "main");

        public LivingEntity entity;

        public final ModelPart rightLeg;
        public final ModelPart leftLeg;

        public SPACE_SUIT_P2(ModelPart root) {
            super(new EntityRendererProvider.Context(Minecraft.getInstance().getEntityRenderDispatcher(), Minecraft.getInstance().getItemRenderer(), Minecraft.getInstance().getResourceManager(), Minecraft.getInstance().getEntityModels(), Minecraft.getInstance().font).bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
            this.rightLeg = root.getChild("right_leg");
            this.leftLeg = root.getChild("left_leg");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

            PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 32);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

            /**Setup Anim*/
            HumanoidModel livingModel = (HumanoidModel<LivingEntity>) ((LivingEntityRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity)).getModel();

            this.attackTime = livingModel.attackTime;
            this.riding = livingModel.riding;
            this.young = livingModel.young;
            this.leftArmPose = livingModel.leftArmPose;
            this.rightArmPose = livingModel.rightArmPose;
            this.crouching = livingModel.crouching;
            this.head.copyFrom(livingModel.head);
            this.body.copyFrom(livingModel.body);
            this.rightArm.copyFrom(livingModel.rightArm);
            this.leftArm.copyFrom(livingModel.leftArm);
            this.rightLeg.copyFrom(livingModel.rightLeg);
            this.leftLeg.copyFrom(livingModel.leftLeg);

            rightLeg.render(poseStack, buffer, packedLight, packedOverlay);
            leftLeg.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }
}
