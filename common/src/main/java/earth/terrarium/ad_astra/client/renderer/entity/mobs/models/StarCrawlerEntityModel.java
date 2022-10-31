package earth.terrarium.ad_astra.client.renderer.entity.mobs.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.entities.mobs.StarCrawlerEntity;
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
public class StarCrawlerEntityModel extends EntityModel<StarCrawlerEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ModResourceLocation("star_crawler"), "main");

    private final ModelPart body;
    private final ModelPart arm1g;
    private final ModelPart arm2g;
    private final ModelPart arm3g;
    private final ModelPart arm4g;

    public StarCrawlerEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.arm1g = root.getChild("arm1g");
        this.arm2g = root.getChild("arm2g");
        this.arm3g = root.getChild("arm3g");
        this.arm4g = root.getChild("arm4g");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        // Body.
        modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0f, -13.0f, -8.0f, 16.0f, 10.0f, 16.0f, new CubeDeformation(0.0f)).texOffs(0, 26).addBox(-7.0f, -9.0f, -7.0f, 14.0f, 9.0f, 14.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 24.0f, 0.0f));

        PartDefinition arm1g = modelPartData.addOrReplaceChild("arm1g", CubeListBuilder.create(), PartPose.offset(0.0f, 18.3f, 6.75f));

        PartDefinition Arm1 = arm1g.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(48, 48).addBox(-6.0f, -5.2f, 0.25f, 12.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(42, 26).addBox(-5.0f, 2.8f, -1.75f, 10.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 0.0f, 0.0f));

        // cube_r1.
        Arm1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(48, 64).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-5.0f, 3.0f, 4.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r2.
        Arm1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(58, 64).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(5.0f, 3.0f, 3.25f, 0.0f, 0.0f, 0.3491f));

        PartDefinition Limb1 = arm1g.addOrReplaceChild("Limb1", CubeListBuilder.create().texOffs(48, 0).addBox(-5.0f, -4.3f, 0.25f, 10.0f, 7.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(0, 64).addBox(-4.0f, 2.7f, -0.75f, 8.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 0.0f, 7.0f));

        // cube_r3.
        Limb1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(59, 38).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-4.0f, 3.0f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r4.
        Limb1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(57, 16).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(4.0f, 3.0f, 4.25f, 0.0f, 0.0f, 0.3491f));

        PartDefinition Hand1 = arm1g.addOrReplaceChild("Hand1", CubeListBuilder.create().texOffs(0, 49).addBox(-4.0f, -3.3f, 0.25f, 8.0f, 6.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(25, 55).addBox(-3.0f, 2.7f, -0.75f, 6.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 0.1f, 15.0f));

        // cube_r5.
        Hand1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(39, 49).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-3.0f, 2.9f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r6.
        Hand1.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(49, 38).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(3.0f, 2.9f, 4.25f, 0.0f, 0.0f, 0.3491f));

        PartDefinition arm2g = modelPartData.addOrReplaceChild("arm2g", CubeListBuilder.create(), PartPose.offset(0.0f, 18.4f, -7.75f));

        PartDefinition Arm2 = arm2g.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(48, 48).addBox(-6.0f, -5.2f, 0.25f, 12.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(42, 26).addBox(-5.0f, 2.8f, -1.75f, 10.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -0.1f, 1.0f, 0.0f, 3.1416f, 0.0f));

        // cube_r7.
        Arm2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(48, 64).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-5.0f, 3.0f, 4.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r8.
        Arm2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(58, 64).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(5.0f, 3.0f, 3.25f, 0.0f, 0.0f, 0.3491f));

        PartDefinition Limb2 = arm2g.addOrReplaceChild("Limb2", CubeListBuilder.create().texOffs(48, 0).addBox(-5.0f, -4.3f, 0.25f, 10.0f, 7.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(0, 64).addBox(-4.0f, 2.7f, -0.75f, 8.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -0.1f, -6.0f, 0.0f, 3.1416f, 0.0f));

        // cube_r9.
        Limb2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(59, 38).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-4.0f, 3.0f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r10.
        Limb2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(57, 16).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(4.0f, 3.0f, 4.25f, 0.0f, 0.0f, 0.3491f));

        PartDefinition Hand2 = arm2g.addOrReplaceChild("Hand2", CubeListBuilder.create().texOffs(0, 49).addBox(-4.0f, -3.3f, -4.75f, 8.0f, 6.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(25, 55).addBox(-3.0f, 2.7f, -3.75f, 6.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 0.0f, -19.0f));

        // cube_r11.
        Hand2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(39, 49).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-3.0f, 2.9f, 0.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r12.
        Hand2.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(49, 38).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(3.0f, 2.9f, -0.75f, 0.0f, 0.0f, 0.3491f));

        PartDefinition arm3g = modelPartData.addOrReplaceChild("arm3g", CubeListBuilder.create(), PartPose.offset(-7.0f, 24.0f, 0.0f));

        PartDefinition Arm3 = arm3g.addOrReplaceChild("Arm3", CubeListBuilder.create().texOffs(48, 48).addBox(-6.0f, -5.2f, 0.25f, 12.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(42, 26).addBox(-5.0f, 2.8f, -1.75f, 10.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.25f, -5.7f, 0.0f, 0.0f, -1.5708f, 0.0f));

        // cube_r13.
        Arm3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(48, 64).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-5.0f, 3.0f, 4.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r14.
        Arm3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(58, 64).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(5.0f, 3.0f, 3.25f, 0.0f, 0.0f, 0.3491f));

        PartDefinition Limb3 = arm3g.addOrReplaceChild("Limb3", CubeListBuilder.create().texOffs(48, 0).addBox(-5.0f, -4.3f, 0.25f, 10.0f, 7.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(0, 64).addBox(-4.0f, 2.7f, -0.75f, 8.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-6.75f, -5.7f, 0.0f, 0.0f, -1.5708f, 0.0f));

        // cube_r15.
        Limb3.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(59, 38).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-4.0f, 3.0f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r16.
        Limb3.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(57, 16).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(4.0f, 3.0f, 4.25f, 0.0f, 0.0f, 0.3491f));

        PartDefinition Hand3 = arm3g.addOrReplaceChild("Hand3", CubeListBuilder.create().texOffs(0, 49).addBox(-4.0f, -3.3f, 0.25f, 8.0f, 6.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(25, 55).addBox(-3.0f, 2.7f, -0.75f, 6.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-14.75f, -5.6f, 0.0f, 0.0f, -1.5708f, 0.0f));

        // cube_r17.
        Hand3.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(39, 49).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-3.0f, 2.9f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r18.
        Hand3.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(49, 38).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(3.0f, 2.9f, 4.25f, 0.0f, 0.0f, 0.3491f));

        PartDefinition arm4g = modelPartData.addOrReplaceChild("arm4g", CubeListBuilder.create(), PartPose.offset(8.0f, 24.0f, 0.0f));

        PartDefinition Arm4 = arm4g.addOrReplaceChild("Arm4", CubeListBuilder.create().texOffs(48, 48).addBox(-6.0f, -5.2f, 0.25f, 12.0f, 8.0f, 8.0f, new CubeDeformation(0.0f)).texOffs(42, 26).addBox(-5.0f, 2.8f, -1.75f, 10.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.25f, -5.7f, 0.0f, 0.0f, 1.5708f, 0.0f));

        // cube_r19.
        Arm4.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(48, 64).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-5.0f, 3.0f, 4.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r20.
        Arm4.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(58, 64).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(5.0f, 3.0f, 3.25f, 0.0f, 0.0f, 0.3491f));

        PartDefinition Limb4 = arm4g.addOrReplaceChild("Limb4", CubeListBuilder.create().texOffs(48, 0).addBox(-5.0f, -4.3f, 0.25f, 10.0f, 7.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(0, 64).addBox(-4.0f, 2.7f, -0.75f, 8.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(5.75f, -5.7f, 0.0f, 0.0f, 1.5708f, 0.0f));

        // cube_r21.
        Limb4.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(59, 38).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-4.0f, 3.0f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r22.
        Limb4.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(57, 16).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(4.0f, 3.0f, 4.25f, 0.0f, 0.0f, 0.3491f));

        PartDefinition Hand4 = arm4g.addOrReplaceChild("Hand4", CubeListBuilder.create().texOffs(0, 49).addBox(-4.0f, -3.3f, 0.25f, 8.0f, 6.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(25, 55).addBox(-3.0f, 2.7f, -0.75f, 6.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(13.75f, -5.6f, 0.0f, 0.0f, 1.5708f, 0.0f));

        // cube_r23.
        Hand4.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(39, 49).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-3.0f, 2.9f, 5.25f, 0.0f, 3.1416f, -0.3491f));

        // cube_r24.
        Hand4.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(49, 38).addBox(0.0f, 0.0f, -3.0f, 5.0f, 0.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(3.0f, 2.9f, 4.25f, 0.0f, 0.0f, 0.3491f));

        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
   public void setupAnim(StarCrawlerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // arm1.
        this.arm1g.getChild("Hand1").yRot = Mth.cos(limbAngle * 0.6662f) * limbDistance;
        this.arm2g.getChild("Hand2").yRot = Mth.cos(limbAngle * 0.6662f) * limbDistance;
        this.arm3g.getChild("Hand3").yRot = 80.115f + Mth.cos(limbAngle * 0.6662f) * limbDistance;
        this.arm4g.getChild("Hand4").yRot = -80.115f + Mth.cos(limbAngle * 0.6662f) * limbDistance;
        // arm.
        this.arm1g.yRot = Mth.cos(limbAngle * 0.6662f) * limbDistance;
        this.arm2g.yRot = Mth.cos(limbAngle * 0.6662f) * limbDistance;
        this.arm3g.yRot = Mth.cos(limbAngle * 0.6662f) * limbDistance;
        this.arm4g.yRot = Mth.cos(limbAngle * 0.6662f) * limbDistance;
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertices, light, overlay);
        arm1g.render(matrices, vertices, light, overlay);
        arm2g.render(matrices, vertices, light, overlay);
        arm3g.render(matrices, vertices, light, overlay);
        arm4g.render(matrices, vertices, light, overlay);
    }
}
