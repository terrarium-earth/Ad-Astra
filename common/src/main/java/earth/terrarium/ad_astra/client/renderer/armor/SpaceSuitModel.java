package earth.terrarium.ad_astra.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.item.armor.ModArmourItem;
import earth.terrarium.ad_astra.common.item.armor.SpaceSuit;
import earth.terrarium.ad_astra.common.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SpaceSuitModel extends HumanoidModel<LivingEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "space_suit"), "main");
    public final ModelPart visor;
    public final ModelPart belt;
    public final ModelPart rightBoot;
    public final ModelPart leftBoot;
    private final HumanoidModel<LivingEntity> contextModel;
    private final ResourceLocation texture;
    private final EquipmentSlot slot;
    private final ItemStack stack;

    public SpaceSuitModel(ModelPart root, HumanoidModel<LivingEntity> contextModel, LivingEntity entity, EquipmentSlot slot, ItemStack stack) {
        super(root);

        this.contextModel = contextModel;
        if (stack.getItem() instanceof ModArmourItem item) {
            String armourTexture = item.getArmorTexture(stack, entity, slot, null);
            this.texture = new ResourceLocation(Objects.requireNonNullElse(armourTexture, ""));
        } else {
            this.texture = new ResourceLocation("");
        }
        this.slot = slot;
        this.stack = stack;

        this.visor = root.getChild("visor");
        this.belt = root.getChild("belt");
        this.rightBoot = root.getChild("left_boot");
        this.leftBoot = root.getChild("right_boot");
        this.setVisible();

    }

    public static VertexConsumer getVertex(RenderType renderLayer, boolean hasEnchantments) {
        MultiBufferSource vertexConsumerProvider = Minecraft.getInstance().renderBuffers().bufferSource();
        return hasEnchantments ? VertexMultiConsumer.create(vertexConsumerProvider.getBuffer(RenderType.entityGlint()), vertexConsumerProvider.getBuffer(renderLayer)) : vertexConsumerProvider.getBuffer(renderLayer);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition visor = modelPartData.addOrReplaceChild("visor", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(1.0f)), PartPose.offset(0.0f, 0.0f, 0.0f));

        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.6f)), PartPose.offset(0.0f, 0.0f, 0.0f));
        PartDefinition hat = modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.6f)), PartPose.offset(0.0f, 0.5f, 0.0f));
        PartDefinition belt = modelPartData.addOrReplaceChild("belt", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.5f)), PartPose.offset(0.0f, -0.5f, 0.0f));

        PartDefinition backpack = body.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(32, 44).addBox(-6.0f, -8.0f, -2.0f, 12.0f, 16.0f, 4.0f, new CubeDeformation(0.6f)), PartPose.offset(0.0f, 5.0f, 5.0f));

        PartDefinition right_arm = modelPartData.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0f, 2.0f, 0.0f));

        PartDefinition cube_r1 = right_arm.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 32).addBox(-2.0f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.6f)), PartPose.offsetAndRotation(-1.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        PartDefinition left_arm = modelPartData.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0f, 2.0f, 0.0f));

        PartDefinition cube_r2 = left_arm.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 32).mirror().addBox(-2.0f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.6f)), PartPose.offsetAndRotation(1.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        PartDefinition right_leg = modelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.7f)), PartPose.offset(-1.9f, 12.0f, 0.0f));

        PartDefinition left_leg = modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.7f)), PartPose.offset(1.9f, 12.0f, 0.0f));

        PartDefinition right_boot = modelPartData.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.6f))
                .texOffs(16, 48).addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f)), PartPose.offset(-1.9f, 12.0f, 0.0f));

        PartDefinition left_boot = modelPartData.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.6f))
                .texOffs(16, 48).addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f)), PartPose.offset(1.9f, 12.0f, 0.0f));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, @NotNull VertexConsumer vertices, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.attackTime = this.contextModel.attackTime;
        this.riding = this.contextModel.riding;
        this.young = this.contextModel.young;
        this.leftArmPose = this.contextModel.leftArmPose;
        this.rightArmPose = this.contextModel.rightArmPose;
        this.crouching = this.contextModel.crouching;

        this.head.copyFrom(this.contextModel.head);
        this.visor.copyFrom(this.contextModel.head);
        this.body.copyFrom(this.contextModel.body);
        this.belt.copyFrom(this.contextModel.body);
        this.rightArm.copyFrom(this.contextModel.rightArm);
        this.leftArm.copyFrom(this.contextModel.leftArm);
        this.leftLeg.copyFrom(this.contextModel.leftLeg);
        this.rightLeg.copyFrom(this.contextModel.rightLeg);
        this.rightBoot.copyFrom(this.contextModel.rightLeg);
        this.leftBoot.copyFrom(this.contextModel.leftLeg);

        poseStack.pushPose();
        if (this.young) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
            poseStack.translate(0, 1.5f, 0);
        }

        int decimal = ((SpaceSuit) this.stack.getItem()).getColor(stack);
        float r = (float) (decimal >> 16 & 0xFF) / 255.0f;
        float g = (float) (decimal >> 8 & 0xFF) / 255.0f;
        float b = (float) (decimal & 0xFF) / 255.0f;

        Minecraft minecraft = Minecraft.getInstance();
        MultiBufferSource provider = minecraft.renderBuffers().bufferSource();
        if (!this.stack.is(ModItems.SPACE_HELMET.get())) {
            this.head.render(poseStack, vertices, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
            this.visor.render(poseStack, provider.getBuffer(RenderType.entityTranslucent(this.texture)), packedLight, packedOverlay, r, g, b, 1.0f);
        } else {
            this.head.render(poseStack, vertices, packedLight, packedOverlay, r, g, b, 1.0f);
            this.visor.render(poseStack, provider.getBuffer(RenderType.entityTranslucent(this.texture)), packedLight, packedOverlay, r, g, b, 1.0f);
        }

        this.body.render(poseStack, vertices, packedLight, packedOverlay, r, g, b, 1.0f);
        this.rightArm.render(poseStack, vertices, packedLight, packedOverlay, r, g, b, 1.0f);
        this.leftArm.render(poseStack, vertices, packedLight, packedOverlay, r, g, b, 1.0f);

        this.belt.render(poseStack, vertices, packedLight, packedOverlay, r, g, b, 1.0f);
        this.rightLeg.render(poseStack, vertices, packedLight, packedOverlay, r, g, b, 1.0f);
        this.leftLeg.render(poseStack, vertices, packedLight, packedOverlay, r, g, b, 1.0f);

        this.rightBoot.render(poseStack, vertices, packedLight, packedOverlay, r, g, b, 1.0f);
        this.leftBoot.render(poseStack, vertices, packedLight, packedOverlay, r, g, b, 1.0f);

        poseStack.popPose();
    }

    private void setVisible() {
        this.setAllVisible(false);
        switch (this.slot) {
            case HEAD -> {
                this.head.visible = true;
                this.visor.visible = true;
            }
            case CHEST -> {
                this.body.visible = true;
                this.rightArm.visible = true;
                this.leftArm.visible = true;
            }
            case LEGS -> {
                this.belt.visible = true;
                this.rightLeg.visible = true;
                this.leftLeg.visible = true;
            }
            case FEET -> {
                this.rightBoot.visible = true;
                this.leftBoot.visible = true;
            }
            default -> {
            }
        }
    }

    @Override
    public void setAllVisible(boolean visible) {
        super.setAllVisible(visible);
        this.visor.visible = visible;
        this.belt.visible = visible;
        this.rightBoot.visible = visible;
        this.leftBoot.visible = visible;
    }

    public ResourceLocation getTextureLocation() {
        return this.texture;
    }
}