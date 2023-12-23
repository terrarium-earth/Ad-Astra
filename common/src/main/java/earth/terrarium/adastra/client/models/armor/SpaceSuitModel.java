package earth.terrarium.adastra.client.models.armor;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.ClientPlatformUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class SpaceSuitModel extends HumanoidModel<LivingEntity> {
    public static final ModelLayerLocation SPACE_SUIT_LAYER = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "space_suit"), "main");
    public static final ModelLayerLocation NETHERITE_SPACE_SUIT_LAYER = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "netherite_space_suit"), "main");
    public static final ModelLayerLocation JET_SUIT_LAYER = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "jet_suit"), "main");

    public static final ResourceLocation SPACE_SUIT_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/armor/space_suit.png");
    public static final ResourceLocation NETHERITE_SPACE_SUIT_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/armor/netherite_space_suit.png");
    public static final ResourceLocation JET_SUIT_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/entity/armor/jet_suit.png");

    private final ModelPart visor;
    private final ModelPart belt;
    private final ModelPart rightBoot;
    private final ModelPart leftBoot;

    private final EquipmentSlot slot;
    private final HumanoidModel<LivingEntity> parentModel;

    public SpaceSuitModel(ModelPart root, EquipmentSlot slot, HumanoidModel<LivingEntity> parentModel) {
        super(root, RenderType::entityTranslucent);

        this.visor = root.getChild("visor");
        this.belt = root.getChild("belt");
        this.rightBoot = root.getChild("left_boot");
        this.leftBoot = root.getChild("right_boot");
        this.slot = slot;
        this.parentModel = parentModel;
        this.setVisible();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.visor.copyFrom(parentModel.head);
        this.belt.copyFrom(parentModel.body);
        this.rightBoot.copyFrom(parentModel.rightLeg);
        this.leftBoot.copyFrom(parentModel.leftLeg);
        parentModel.copyPropertiesTo(this);

        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setAllVisible(boolean visible) {
        super.setAllVisible(visible);
        this.visor.visible = visible;
        this.belt.visible = visible;
        this.rightBoot.visible = visible;
        this.leftBoot.visible = visible;
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
        }
    }

    @Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(head, visor);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(body, rightArm, leftArm, rightLeg, leftLeg, hat, belt, rightBoot, leftBoot);
	}

    @SuppressWarnings("unused")
    public static LayerDefinition createSpaceSuitLayer() {
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

    @SuppressWarnings("unused")
    public static LayerDefinition createNetheriteSpaceSuitLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition visor = modelPartData.addOrReplaceChild("visor", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(1.0f)), PartPose.offset(0.0f, -1.0f, 0.0f));

        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.6f)), PartPose.offset(0.0f, -1.0f, 0.0f));
        PartDefinition hat = modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.6f)), PartPose.offset(0.0f, -0.5f, 0.0f));
        PartDefinition belt = modelPartData.addOrReplaceChild("belt", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.5f)), PartPose.offset(0.0f, -0.5f, 0.0f));

        PartDefinition backpack = body.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(32, 46).addBox(-5.0f, -7.0f, -2.0f, 10.0f, 14.0f, 4.0f, new CubeDeformation(0.5f)), PartPose.offset(0.0f, 5.0f, 5.0f));

        PartDefinition right_arm = modelPartData.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0f, 1.0f, 0.0f));

        PartDefinition cube_r1 = right_arm.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 32).addBox(-2.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f))
                .texOffs(16, 48).addBox(-2.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.6f)), PartPose.offsetAndRotation(-1.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        PartDefinition left_arm = modelPartData.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0f, 1.0f, 0.0f));

        PartDefinition cube_r2 = left_arm.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 32).mirror().addBox(-1.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f))
                .texOffs(16, 48).addBox(-1.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.6f)), PartPose.offsetAndRotation(1.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        PartDefinition right_leg = modelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.7f)), PartPose.offset(-1.9f, 12.0f, 0.0f));

        PartDefinition left_leg = modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.7f)), PartPose.offset(1.9f, 12.0f, 0.0f));

        PartDefinition right_boot = modelPartData.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f)), PartPose.offset(-1.9f, 12.0f, 0.0f));

        PartDefinition left_boot = modelPartData.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f)), PartPose.offset(1.9f, 12.0f, 0.0f));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createJetSuitLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(0.6f)), PartPose.offset(0.0f, 0.0f, 0.0f));
        PartDefinition hat = modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create().texOffs(0, 0), PartPose.ZERO);

        PartDefinition visor = modelPartData.addOrReplaceChild("visor", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new CubeDeformation(1.0f)), PartPose.offset(0.0f, 0.0f, 0.0f));

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.6f))
                .texOffs(32, 48).addBox(-4.0f, -1.0f, 3.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.5f))
                .texOffs(32, 32).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.9f)), PartPose.offset(1.0f, 0.5f, 0.0f));
        PartDefinition belt = modelPartData.addOrReplaceChild("belt", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.5f)), PartPose.offset(0.0f, -0.5f, 0.0f));

        PartDefinition backpack = body.addOrReplaceChild("backpack", CubeListBuilder.create(), PartPose.offset(0.0f, 5.0f, 5.0f));

        PartDefinition right_arm = modelPartData.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-4.5f, 2.0f, 0.0f));

        PartDefinition cube_r1 = right_arm.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 32).addBox(-2.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f))
                .texOffs(16, 48).addBox(-2.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.6f)), PartPose.offsetAndRotation(-1.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        PartDefinition left_arm = modelPartData.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(4.5f, 2.0f, 0.0f));

        PartDefinition cube_r2 = left_arm.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 32).mirror().addBox(-1.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f))
                .texOffs(16, 48).addBox(-1.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.6f)), PartPose.offsetAndRotation(1.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        PartDefinition right_leg = modelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.7f)), PartPose.offset(-1.9f, 12.0f, 0.0f));

        PartDefinition left_leg = modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.7f)), PartPose.offset(1.9f, 12.0f, 0.0f));

        PartDefinition right_boot = modelPartData.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f)), PartPose.offset(-1.9f, 12.0f, 0.0f));

        PartDefinition left_boot = modelPartData.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(1.0f)), PartPose.offset(1.9f, 12.0f, 0.0f));

        return LayerDefinition.create(modelData, 64, 64);
    }

    public static void register(ClientPlatformUtils.LayerDefinitionRegistry registry) {
        registry.register(SPACE_SUIT_LAYER, SpaceSuitModel::createSpaceSuitLayer);
        registry.register(NETHERITE_SPACE_SUIT_LAYER, SpaceSuitModel::createNetheriteSpaceSuitLayer);
        registry.register(JET_SUIT_LAYER, SpaceSuitModel::createJetSuitLayer);
    }
}
