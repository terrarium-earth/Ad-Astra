package earth.terrarium.ad_astra.client.renderer.entity.mobs.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import earth.terrarium.ad_astra.entities.mobs.GlacianRam;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class GlacianRamEntityModel<T extends GlacianRam> extends QuadrupedModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ModResourceLocation("glacian_ram"), "main");
    private float headPitchModifier;

    public GlacianRamEntityModel(ModelPart modelPart) {
        super(modelPart, false, 0.0f, 4.0f, 2.0f, 2.0f, 24);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(3, 19).addBox(-3.5f, -4.8f, -2.9f, 7.0f, 6.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(50, 53).addBox(1.5f, -6.8f, -0.9f, 4.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(32, 52).addBox(3.5f, -6.8f, 1.1f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(51, 56).addBox(-5.5f, -6.8f, 1.1f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).texOffs(52, 18).addBox(-5.5f, -6.8f, -0.9f, 4.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 13.8f, -1.9f, 0.0f, 3.1416f, 0.0f));

        PartDefinition ear_left = head.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(21, 38).addBox(3.8985f, -0.5f, -1.5f, 4.0f, 1.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-7.3249f, 2.0408f, 1.35f, 0.0f, 0.0f, -0.9599f));

        PartDefinition ear_right = head.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(21, 38).mirror().addBox(-7.8985f, -0.5f, -1.5f, 4.0f, 1.0f, 3.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(7.3249f, 2.0408f, 1.35f, 0.0f, 0.0f, 0.9599f));

        PartDefinition left_front_leg = modelPartData.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 54).addBox(-1.5f, -1.5f, -0.5f, 3.0f, 7.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offset(2.5f, 19.5f, -0.5f));

        PartDefinition right_front_leg = modelPartData.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(12, 30).addBox(-1.5f, -1.5f, -1.5f, 3.0f, 7.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offset(-2.5f, 19.5f, 0.5f));

        PartDefinition left_hind_leg = modelPartData.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 54).addBox(0.5f, -1.5f, -0.5f, 3.0f, 7.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offset(1.0f, 19.5f, 8.5f));

        PartDefinition right_hind_leg = modelPartData.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(12, 30).addBox(-2.0f, -1.5f, -1.5f, 3.0f, 7.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offset(-2.5f, 19.5f, 9.5f));

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 40).addBox(-1.5f, -2.4f, -7.6f, 3.0f, 2.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(0, 40).addBox(5.5f, 1.6f, -0.6f, 0.0f, 2.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(0, 0).addBox(-5.5f, -6.4f, -0.6f, 11.0f, 8.0f, 10.0f, new CubeDeformation(0.0f)).texOffs(27, 27).addBox(-5.5f, -4.4f, -5.6f, 11.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)).texOffs(0, 40).addBox(-5.5f, 1.6f, -0.6f, 0.0f, 2.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 18.4f, 6.6f, 0.0f, 3.1416f, 0.0f));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 40).addBox(13.0f, -1.0f, 3.0f, 0.0f, 2.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(4.5f, 2.6f, -3.6f, 0.0f, -1.5708f, 0.0f));
        return LayerDefinition.create(modelData, 64, 64);
    }

    public void animateModel(T entity, float f, float g, float h) {
        super.prepareMobModel(entity, f, g, h);
        this.head.y = 6.0f + entity.getNeckAngle(h) * 9.0f;
        this.headPitchModifier = entity.getHeadAngle(h);
    }

   public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        super.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        this.head.xRot = this.headPitchModifier;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertices, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (this.young) {
            poseStack.pushPose();
            float scale = 1.25f / 2.0f;
            poseStack.scale(scale, scale, scale);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
            poseStack.translate(0, 1.1, 0.25);
            this.headParts().forEach(headPart -> headPart.render(poseStack, vertices, packedLight, packedOverlay, red, green, blue, alpha));
            poseStack.popPose();

            poseStack.pushPose();
            float scale1 = 1.15f / 2.0f;
            poseStack.scale(scale1, scale1, scale1);
            poseStack.translate(0, 0.7, 0);
            this.bodyParts().forEach(bodyPart -> bodyPart.render(poseStack, vertices, packedLight, packedOverlay, red, green, blue, alpha));
            poseStack.popPose();
        } else {
            poseStack.pushPose();
            float scale = 1.25f;
            poseStack.scale(scale, scale, scale);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
            poseStack.translate(0, 0.1, 0.25);
            this.headParts().forEach(headPart -> headPart.render(poseStack, vertices, packedLight, packedOverlay, red, green, blue, alpha));
            poseStack.popPose();

            poseStack.pushPose();
            float scale1 = 1.15f;
            poseStack.scale(scale1, scale1, scale1);
            poseStack.translate(0, -0.3, 0);
            this.bodyParts().forEach(bodyPart -> bodyPart.render(poseStack, vertices, packedLight, packedOverlay, red, green, blue, alpha));
            poseStack.popPose();
        }
    }
}
