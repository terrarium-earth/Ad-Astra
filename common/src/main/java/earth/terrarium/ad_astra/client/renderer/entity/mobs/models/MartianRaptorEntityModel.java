package earth.terrarium.ad_astra.client.renderer.entity.mobs.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.entities.mobs.MartianRaptor;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class MartianRaptorEntityModel extends EntityModel<MartianRaptor> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ModResourceLocation("martian_raptor"), "main");

    private final ModelPart body;
    private final ModelPart leg1;
    private final ModelPart leg2;

    public MartianRaptorEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition leg1 = modelPartData.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(14, 69).addBox(-2.0f, 11.7065f, -5.0813f, 5.0f, 3.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.offset(6.0f, 9.0f, 4.0f));

        // cube_r1.
        leg1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(36, 64).addBox(-2.5f, -7.0f, -2.5f, 4.0f, 14.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(1.0f, 7.7065f, 2.4187f, -0.6545f, 0.0f, 0.0f));

        // cube_r2.
        leg1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 58).addBox(-2.5f, -6.0f, -2.5f, 5.0f, 12.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.5f, -0.2935f, 2.4187f, 0.6545f, 0.0f, 0.0f));

        PartDefinition leg2 = modelPartData.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(14, 69).mirror().addBox(-3.0f, 11.7065f, -5.0813f, 5.0f, 3.0f, 6.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offset(-6.0f, 9.0f, 4.0f));

        // cube_r3.
        leg2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(36, 64).mirror().addBox(-2.5f, -7.0f, -2.5f, 4.0f, 14.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(0.0f, 7.7065f, 2.4187f, -0.6545f, 0.0f, 0.0f));

        // cube_r4.
        leg2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 58).mirror().addBox(-2.5f, -6.0f, -2.5f, 5.0f, 12.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(-0.5f, -0.2935f, 2.4187f, 0.6545f, 0.0f, 0.0f));

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -24.0f, -6.0f, 8.0f, 10.0f, 15.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 24.0f, 0.0f));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0f, -19.0f, -3.0f));

        PartDefinition bone2 = head.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(34, 34).addBox(-5.0f, -4.0f, 0.0f, 10.0f, 9.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -1.0f, -8.0f, -0.4363f, 0.0f, 0.0f));

        PartDefinition bone = bone2.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.25f, -6.75f, 6.0f, -0.5236f, 0.0f, 0.0f));

        // cube_r5.
        bone.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 42).addBox(-6.0f, -5.5f, -4.5f, 10.0f, 11.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-2.2012f, 0.0f, -0.1198f, -0.0436f, 0.6981f, 0.0f));

        // cube_r6.
        bone.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-4.0f, -5.5f, -4.5f, 10.0f, 11.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(1.7988f, 0.0f, -0.1198f, -0.0436f, -0.6981f, 0.0f));

        PartDefinition mouth1 = bone2.addOrReplaceChild("mouth1", CubeListBuilder.create(), PartPose.offset(-2.1711f, 4.4542f, 3.0357f));

        // cube_r7.
        mouth1.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(52, 71).addBox(-1.5789f, -1.5855f, -2.25f, 3.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3491f));

        PartDefinition mouth2 = bone2.addOrReplaceChild("mouth2", CubeListBuilder.create(), PartPose.offset(1.8289f, 4.4542f, 3.0357f));

        // cube_r8.
        mouth2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(52, 71).mirror().addBox(-1.0789f, -1.4145f, -2.25f, 3.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.3491f));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0f, -19.0f, 9.0f));

        // cube_r9.
        tail.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(20, 50).addBox(-2.0f, -2.0f, -5.0f, 4.0f, 4.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 5.1366f, 17.1501f, 0.1309f, 0.0f, 0.0f));

        // cube_r10.
        tail.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(31, 0).addBox(-3.0f, -2.5f, -5.0f, 6.0f, 5.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 4.8909f, 9.0849f, -0.1309f, 0.0f, 0.0f));

        // cube_r11.
        tail.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(63, 7).addBox(-2.5f, -1.0f, -1.5f, 5.0f, 2.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 6.4973f, 16.5505f, 1.7017f, 0.0f, 0.0f));

        // cube_r12.
        tail.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(61, 31).addBox(-3.5f, -1.0f, -2.5f, 7.0f, 2.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 6.7473f, 12.5505f, 1.6144f, 0.0f, 0.0f));

        // cube_r13.
        tail.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(24, 26).addBox(-10.5f, -2.0f, -5.5f, 8.0f, 2.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(6.5f, 2.7065f, 5.4187f, 1.1345f, 0.0f, 0.0f));

        // cube_r14.
        tail.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(53, 0).addBox(-10.5f, -2.0f, -5.5f, 8.0f, 2.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(6.5f, 3.7065f, 9.4187f, 1.3526f, 0.0f, 0.0f));

        // cube_r15.
        tail.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 25).addBox(-3.5f, -3.5f, -5.0f, 7.0f, 7.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 1.4079f, 1.7286f, -0.6545f, 0.0f, 0.0f));

        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
   public void setupAnim(MartianRaptor entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float cooldown = entity.getMovementCooldownTicks();
        float f = 1.0f - Mth.abs(10 - 2 * cooldown) / 10.0f;

        this.body.getChild("head").getChild("bone2").getChild("mouth1").zRot = Mth.lerp(f, 0.0f, -1.14906584f);
        this.body.getChild("head").getChild("bone2").getChild("mouth2").zRot = -Mth.lerp(f, 0.0f, -1.14906584f);

        this.leg1.xRot = Mth.cos(limbAngle) * -1.0f * limbDistance;
        this.leg2.xRot = Mth.cos(limbAngle) * 1.0f * limbDistance;

        this.body.getChild("head").yRot = headYaw / (180f / (float) Math.PI);
        this.body.getChild("head").xRot = headPitch / (180f / (float) Math.PI);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertices, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertices, packedLight, packedOverlay);
        leg1.render(poseStack, vertices, packedLight, packedOverlay);
        leg2.render(poseStack, vertices, packedLight, packedOverlay);
    }
}
