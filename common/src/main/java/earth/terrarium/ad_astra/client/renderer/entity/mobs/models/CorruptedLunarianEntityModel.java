package earth.terrarium.ad_astra.client.renderer.entity.mobs.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.entities.mobs.CorruptedLunarianEntity;
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
public class CorruptedLunarianEntityModel extends EntityModel<CorruptedLunarianEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ModResourceLocation("corrupted_lunarian"), "main");

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

    public CorruptedLunarianEntityModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leg0 = root.getChild("leg0");
        this.leg1 = root.getChild("leg1");
        this.arm1 = root.getChild("arm1");
        this.arm2 = root.getChild("arm2");
        this.monsterarm1 = root.getChild("monsterarm1");
        this.monsterarm2 = root.getChild("monsterarm2");
        this.monsterarm3 = root.getChild("monsterarm3");
        this.monsterarm4 = root.getChild("monsterarm4");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0f, -9.0f, -4.0f, 8.0f, 9.0f, 8.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(0.0f, 2.0f, -6.0f, -0.2182f, 0.0f, 0.0f));

        // cube_r1.
        head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(88, 59).mirror().addBox(-0.5095f, -2.211f, -0.6496f, 2.0f, 3.0f, 2.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(2.25f, 4.1027f, -5.534f, 1.0036f, 0.48f, -0.8727f));

        // cube_r2.
        head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(88, 59).addBox(-1.7975f, -1.8508f, -0.9483f, 2.0f, 3.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-1.75f, 4.1027f, -5.534f, 1.0036f, -0.48f, 0.8727f));

        // cube_r3.
        head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(88, 54).addBox(-1.5f, -1.75f, -0.75f, 3.0f, 3.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 1.8527f, -7.434f, -0.3927f, 0.0f, 0.0f));

        // head_r1.
        head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(33, 0).mirror().addBox(-4.5f, -3.9804f, -4.3483f, 9.0f, 10.0f, 9.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(0.0f, -14.0f, 1.0f, -0.1745f, 0.0f, 0.0f));

        // head_r2.
        head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(40, 53).mirror().addBox(-3.0f, 2.8551f, 0.3492f, 7.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(-0.5f, -5.0f, 0.0f, -1.0036f, 0.0f, 0.0f));

        // nose.
        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.offset(0.0f, -3.1927f, 0.7599f));

        // nose_r1.
        nose.addOrReplaceChild("nose_r1", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-1.0f, -0.5896f, 1.4131f, 2.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(0.0f, -2.0f, -6.0f, -1.0036f, 0.0f, 0.0f));

        // body.
        modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).mirror().addBox(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(0, 38).mirror().addBox(-4.0f, 0.0f, -3.0f, 8.0f, 20.0f, 6.0f, new CubeDeformation(0.5f)).mirror(false), PartPose.offsetAndRotation(0.0f, 0.0f, -3.0f, 0.2182f, 0.0f, 0.0f));

        // leg0.
        modelPartData.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offset(2.0f, 12.0f, 0.0f));

        // leg1.
        modelPartData.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offset(-2.0f, 12.0f, 0.0f));

        // arm1.
        modelPartData.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(-8.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offsetAndRotation(0.0f, 4.0f, -1.5f, 0.0436f, 0.0f, 0.0f));

        // arm2.
        modelPartData.addOrReplaceChild("arm2", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0f, -2.0f, -5.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false), PartPose.offset(0.0f, 7.0f, -1.5f));

        // monsterarm1.
        PartDefinition monsterarm1 = modelPartData.addOrReplaceChild("monsterarm1", CubeListBuilder.create().texOffs(30, 46).addBox(-17.0f, -1.0f, -1.0f, 17.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-4.0f, 0.0f, -2.0f, 0.0f, 0.0f, 0.9599f));

        // cube_r4.
        monsterarm1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(34, 46).addBox(-15.0f, -1.0f, -1.0f, 15.0f, 2.0f, 2.0f, new CubeDeformation(-0.1f)), PartPose.offsetAndRotation(-16.25f, 0.0f, 0.75f, 0.0f, -1.1345f, 0.0f));

        // monsterarm2
        PartDefinition monsterarm2 = modelPartData.addOrReplaceChild("monsterarm2", CubeListBuilder.create().texOffs(30, 46).addBox(-17.0f, -1.0f, -1.0f, 17.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-3.0f, -0.25f, -3.0f, 0.0f, 0.0f, -0.3927f));

        // cube_r5.
        monsterarm2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(34, 46).addBox(-15.0f, -1.0f, -1.0f, 15.0f, 2.0f, 2.0f, new CubeDeformation(-0.1f)), PartPose.offsetAndRotation(-16.25f, 0.0f, 0.75f, 0.0f, -1.1345f, 0.0f));

        // monsterarm3.
        PartDefinition monsterarm3 = modelPartData.addOrReplaceChild("monsterarm3", CubeListBuilder.create().texOffs(30, 46).addBox(-17.0f, -1.0f, -1.0f, 17.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(4.0f, 0.0f, -2.0f, 0.0f, 0.0f, 2.1817f));

        // cube_r6.
        monsterarm3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(34, 46).addBox(-15.0f, -1.0f, -1.0f, 15.0f, 2.0f, 2.0f, new CubeDeformation(-0.1f)), PartPose.offsetAndRotation(-16.25f, 0.0f, 0.75f, 0.0f, -1.1345f, 0.0f));

        // monsterarm4.
        PartDefinition monsterarm4 = modelPartData.addOrReplaceChild("monsterarm4", CubeListBuilder.create().texOffs(30, 46).addBox(-17.0f, -1.0f, -1.0f, 17.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(3.0f, -0.25f, -3.0f, 0.0f, 0.0f, -2.7489f));

        // cube_r7.
        monsterarm4.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(30, 46).addBox(-15.0f, -1.0f, -1.0f, 15.0f, 2.0f, 2.0f, new CubeDeformation(-0.1f)), PartPose.offsetAndRotation(-16.25f, 0.0f, 0.75f, 0.0f, -1.1345f, 0.0f));

        return LayerDefinition.create(modelData, 96, 64);
    }

    @Override
   public void setupAnim(CorruptedLunarianEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
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
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertices, light, overlay);
        body.render(matrices, vertices, light, overlay);
        leg0.render(matrices, vertices, light, overlay);
        leg1.render(matrices, vertices, light, overlay);
        arm1.render(matrices, vertices, light, overlay);
        arm2.render(matrices, vertices, light, overlay);
        monsterarm1.render(matrices, vertices, light, overlay);
        monsterarm2.render(matrices, vertices, light, overlay);
        monsterarm3.render(matrices, vertices, light, overlay);
        monsterarm4.render(matrices, vertices, light, overlay);
    }
}
