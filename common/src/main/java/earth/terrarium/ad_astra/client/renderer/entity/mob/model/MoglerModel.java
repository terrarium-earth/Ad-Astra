package earth.terrarium.ad_astra.client.renderer.entity.mob.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.AdAstra;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;

@Environment(EnvType.CLIENT)
public class MoglerModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "mogler"), "main");

    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart right_front_leg;
    private final ModelPart left_front_leg;
    private final ModelPart leg3;
    private final ModelPart left_back_leg;

    public MoglerModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.right_front_leg = root.getChild("right_front_leg");
        this.left_front_leg = root.getChild("left_front_leg");
        this.leg3 = root.getChild("right_back_leg");
        this.left_back_leg = root.getChild("left_back_leg");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(33, 127).addBox(-8.0F, 4.3819F, -6.3545F, 16.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(97, 68).addBox(-9.0F, -7.3501F, -12.2827F, 18.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.1899F, -17.7795F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(57, 46).addBox(-9.0F, -25.0F, -8.0F, 18.0F, 11.0F, 11.0F, new CubeDeformation(0.0F))
            .texOffs(59, 106).addBox(-8.0F, -14.0F, 2.0F, 16.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(56, 68).addBox(-8.0F, -14.0F, -7.0F, 16.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.8101F, -11.2205F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 102).mirror().addBox(-2.0F, -2.5F, -2.5F, 7.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.4912F, 4.0149F, -10.184F, -0.4164F, -0.3272F, -0.6284F));

        PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 102).addBox(-5.0F, -2.5F, -2.5F, 7.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.4912F, 4.0149F, -10.184F, -0.4164F, 0.3272F, 0.6284F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -0.4541F, -13.2739F, 10.0F, 6.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.4541F, -6.7261F));

        PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 34).addBox(-11.0F, 0.5F, -13.0F, 22.0F, 11.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1781F, 0.0F, 0.0F));

        PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(48, 0).addBox(-10.0F, -7.5F, -10.875F, 20.0F, 13.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.3963F, 0.0F, 0.0F));

        PartDefinition cube_r6 = body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 57).addBox(-9.0F, -15.5F, -8.0F, 18.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r7 = body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(66, 25).addBox(-8.0F, -20.5F, -8.0F, 16.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.8326F, 0.0F, 0.0F));

        PartDefinition right_back_leg = partdefinition.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(36, 83).addBox(-4.5F, -1.0F, -4.5F, 9.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5F, 13.0F, 2.5F));

        PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(99, 95).addBox(-4.5F, -1.0F, -4.5F, 9.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5F, 13.0F, -16.5F));

        PartDefinition left_back_leg = partdefinition.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(0, 81).addBox(-4.5F, -1.0F, -4.5F, 9.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 13.0F, 2.5F));

        PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(72, 83).addBox(-4.5F, -1.0F, -4.5F, 9.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 13.0F, -16.5F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        Hoglin hoglin = null;

        if (entity instanceof Hoglin hog) {
            hoglin = hog;
        }

        if (entity instanceof Hoglin || entity instanceof Zoglin) {
            this.head.yRot = headYaw * ((float) Math.PI / 180.0f);
            int i = 0;
            if (hoglin != null) {
                i = hoglin.getAttackAnimationRemainingTicks();
            }
            float f = 1.0f - (float) Mth.abs(10 - 2 * i) / 10.0f;
            this.head.xRot = Mth.lerp(f, 0.0f, -1.14906584f);

            this.right_front_leg.xRot = Mth.cos(limbAngle) * 1.2f * limbDistance;
            this.left_front_leg.xRot = Mth.cos(limbAngle + (float) Math.PI) * 1.2f * limbDistance;
            this.leg3.xRot = this.right_front_leg.xRot;
            this.left_back_leg.xRot = this.left_front_leg.xRot;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertices, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (this.young) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
            poseStack.translate(0, 1.5f, 0);
        }

        body.render(poseStack, vertices, packedLight, packedOverlay);
        head.render(poseStack, vertices, packedLight, packedOverlay);
        right_front_leg.render(poseStack, vertices, packedLight, packedOverlay);
        left_front_leg.render(poseStack, vertices, packedLight, packedOverlay);
        leg3.render(poseStack, vertices, packedLight, packedOverlay);
        left_back_leg.render(poseStack, vertices, packedLight, packedOverlay);
    }
}
