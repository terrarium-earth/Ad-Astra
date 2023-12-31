package earth.terrarium.adastra.client.models.entities.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.entities.mob.CorruptedLunarian;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

// LEGACY ENTITY. WILL BE REPLACED IN THE FUTURE.
public class CorruptedLunarianModel extends EntityModel<CorruptedLunarian> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "corrupted_lunarian"), "main");

    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leg0;
    private final ModelPart leg1;
    private final ModelPart arm1;
    private final ModelPart arm2;
    private final ModelPart monsterarm1;
    private final ModelPart monsterarm2;
    private final ModelPart monsterarm3;
    private final ModelPart monsterarm4;

    public CorruptedLunarianModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leg0 = root.getChild("leg0");
        this.leg1 = root.getChild("leg1");
        this.arm1 = root.getChild("arms").getChild("arm1");
        this.arm2 = root.getChild("arms").getChild("arm2");
        this.monsterarm1 = root.getChild("extra_arms").getChild("backarm1");
        this.monsterarm2 = root.getChild("extra_arms").getChild("backarm2");
        this.monsterarm3 = root.getChild("extra_arms").getChild("backarm3");
        this.monsterarm4 = root.getChild("extra_arms").getChild("backarm4");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 19).mirror().addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(0, 0).mirror().addBox(-4.5F, -18.0F, -4.5F, 9.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(0, 20).mirror().addBox(-1.0F, -3.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition extra_arms = partdefinition.addOrReplaceChild("extra_arms", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition backarm1 = extra_arms.addOrReplaceChild("backarm1", CubeListBuilder.create(), PartPose.offset(-1.2968F, 6.4078F, 0.3907F));

        PartDefinition body_r1 = backarm1.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(52, 0).addBox(-11.5F, 0.0F, 9.5F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(36, 0).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2968F, -0.4078F, 0.1093F, -0.6109F, -0.6109F, 0.0F));

        PartDefinition backarm2 = extra_arms.addOrReplaceChild("backarm2", CubeListBuilder.create(), PartPose.offset(1.0F, 6.0F, 1.0F));

        PartDefinition body_r2 = backarm2.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(2.0F, 0.0F, 9.5F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(36, 0).addBox(0.0F, 0.0F, -0.5F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -0.5F, -0.6109F, 0.6981F, 0.0F));

        PartDefinition backarm3 = extra_arms.addOrReplaceChild("backarm3", CubeListBuilder.create(), PartPose.offset(-1.0F, 4.0F, 1.0F));

        PartDefinition body_r3 = backarm3.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(52, 0).addBox(-11.5F, -2.0F, 9.5F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(36, 0).addBox(-1.5F, -2.0F, -0.5F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -0.5F, 0.6109F, -0.6109F, 0.0F));

        PartDefinition backarm4 = extra_arms.addOrReplaceChild("backarm4", CubeListBuilder.create(), PartPose.offset(1.0F, 4.0F, 0.0F));

        PartDefinition body_r4 = backarm4.addOrReplaceChild("body_r4", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(2.0F, -2.0F, 9.5F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(36, 0).addBox(0.0F, -2.0F, -0.5F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.0F, 0.5F, 0.6109F, 0.6109F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(100, 0).mirror().addBox(0.0F, -12.0F, -5.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(0, 36).mirror().addBox(0.0F, -12.0F, -5.0F, 8.0F, 19.0F, 6.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(-4.0F, 12.0F, 2.0F));

        PartDefinition leg0 = partdefinition.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(0, 81).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 81).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition arm1 = arms.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(30, 61).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(6.0F, 0.1434F, 0.2048F));

        PartDefinition arm2 = arms.addOrReplaceChild("arm2", CubeListBuilder.create().texOffs(30, 61).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 0.1434F, 0.2048F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public void setupAnim(CorruptedLunarian entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.arm2.yRot = 0.0f;
        this.arm1.yRot = 0.0f;
        this.arm2.zRot = 0.0f;
        this.arm1.zRot = 0.0f;
        this.arm2.xRot = 0.0f;
        this.arm1.xRot = 0.0f;

        this.arm2.zRot -= Mth.cos(animationProgress * 0.04f) * 0.04f + 0.04f;
        this.arm1.zRot += Mth.cos(animationProgress * 0.04f) * 0.04f + 0.04f;

        // base end

        this.head.yRot = headYaw / (180f / (float) Math.PI);
        this.head.xRot = headPitch / (180f / (float) Math.PI);
        this.leg0.xRot = Mth.cos(limbAngle) * -1.0f * limbDistance;
        this.leg1.xRot = Mth.cos(limbAngle) * 1.0f * limbDistance;
        this.monsterarm1.yRot = Mth.cos(limbAngle * 0.3662f + (float) Math.PI) * limbDistance / 2;
        this.monsterarm4.yRot = Mth.cos(limbAngle * 0.3662f + (float) Math.PI) * limbDistance / 2;
        this.monsterarm3.yRot = Mth.cos(limbAngle * 0.3662f + (float) Math.PI) * limbDistance / 2;
        this.monsterarm2.yRot = Mth.cos(limbAngle * 0.3662f + (float) Math.PI) * limbDistance / 2;
        this.arm1.xRot = 30f;
        this.arm2.xRot = 30f;

        this.arm2.xRot -= Mth.cos(animationProgress * 0.04f) * 0.04f + 0.04f;
        this.arm1.xRot += Mth.cos(animationProgress * 0.04f) * 0.04f + 0.04f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertices, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertices, packedLight, packedOverlay);
        body.render(poseStack, vertices, packedLight, packedOverlay);
        leg0.render(poseStack, vertices, packedLight, packedOverlay);
        leg1.render(poseStack, vertices, packedLight, packedOverlay);
        arm1.render(poseStack, vertices, packedLight, packedOverlay);
        arm2.render(poseStack, vertices, packedLight, packedOverlay);
        monsterarm1.render(poseStack, vertices, packedLight, packedOverlay);
        monsterarm2.render(poseStack, vertices, packedLight, packedOverlay);
        monsterarm3.render(poseStack, vertices, packedLight, packedOverlay);
        monsterarm4.render(poseStack, vertices, packedLight, packedOverlay);
    }
}
