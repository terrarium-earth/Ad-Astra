package earth.terrarium.ad_astra.client.renderer.entity.mob.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.entity.mob.MartianRaptor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


public class MartianRaptorModel extends EntityModel<MartianRaptor> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "martian_raptor"), "main");

    private final ModelPart body;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public MartianRaptorModel(ModelPart root) {
        this.body = root.getChild("body");
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 65).addBox(-2.5F, 7.733F, -5.1341F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 13.267F, 3.3841F));

        PartDefinition cube_r1 = left_leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(5.75F, 2.0F, -2.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.75F, -0.3977F, 4.635F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r2 = left_leg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(68, 35).addBox(5.75F, 7.5F, 0.9F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(50, 39).addBox(5.25F, 2.5F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.75F, -1.7831F, 3.2889F, -0.7418F, 0.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 65).addBox(-2.5F, 7.733F, -5.1341F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.5F, 13.267F, 3.3841F));

        PartDefinition cube_r3 = right_leg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(5.75F, 2.0F, -2.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.75F, -0.3977F, 4.635F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r4 = right_leg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(68, 35).addBox(5.75F, 7.5F, 0.9F, 4.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(50, 39).addBox(5.25F, 2.5F, -4.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.75F, -1.7831F, 3.2889F, -0.7418F, 0.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -10.0F, 8.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(28, 29).addBox(-9.5F, 1.5F, -7.0F, 5.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
            .texOffs(1, 24).addBox(-3.5F, -4.5F, -7.0F, 9.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -4.5F, 0.0F, 0.2519F, -0.2443F, 0.7543F));

        PartDefinition Tail = body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(42, 52).addBox(-1.25F, 3.3318F, 6.4959F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(0, 25).addBox(0.75F, -0.6682F, 9.4959F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.75F, -0.3318F, 6.5041F));

        PartDefinition cube_r6 = Tail.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 18).addBox(0.75F, -7.5F, -4.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
            .texOffs(0, 39).addBox(-2.75F, -1.5F, -5.0F, 7.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7418F, 0.0F, 0.0F));

        PartDefinition cube_r7 = Tail.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(28, 19).addBox(0.75F, -5.0F, 4.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(22, 47).addBox(-2.25F, 0.0F, 1.0F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r8 = Tail.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, -5.2F, -3.25F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(36, 14).addBox(-4.5F, -0.2F, 1.75F, 9.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(0, 53).addBox(-1.5F, -2.2F, -4.25F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, 5.3318F, 18.4959F, 0.1745F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, -4.0F));

        PartDefinition cube_r9 = head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(16, 60).addBox(1.5F, -14.0F, -2.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(60, 58).addBox(-5.5F, -14.0F, -2.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(30, 0).addBox(-4.5F, -13.0F, -3.0F, 9.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r10 = head.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(36, 64).addBox(-3.5F, -13.0F, -7.75F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, 0.25F, 0.7974F, 0.1536F, 0.1555F));

        PartDefinition cube_r11 = head.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(62, 9).addBox(-0.5F, -13.0F, -7.75F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.25F, 0.25F, 0.7974F, -0.1536F, -0.1555F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(MartianRaptor entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        this.left_leg.xRot = Mth.cos(limbAngle) * -1.0f * limbDistance;
        this.right_leg.xRot = Mth.cos(limbAngle) * 1.0f * limbDistance;

        this.body.getChild("head").yRot = headYaw / (180f / (float) Math.PI);
        this.body.getChild("head").xRot = headPitch / (180f / (float) Math.PI);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertices, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertices, packedLight, packedOverlay);
        left_leg.render(poseStack, vertices, packedLight, packedOverlay);
        right_leg.render(poseStack, vertices, packedLight, packedOverlay);
    }
}
