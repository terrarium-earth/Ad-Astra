package earth.terrarium.ad_astra.client.renderer.entity.mobs.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.hoglin.Hoglin;

@Environment(EnvType.CLIENT)
public class MoglerModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ModResourceLocation("mogler"), "main");

    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;

    public MoglerModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
        this.leg4 = root.getChild("leg4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0f, -15.0f, -13.0f, 16.0f, 9.0f, 32.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 24.0f, 0.0f));

        // cube_r1.
        body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(85, 30).addBox(-9.0f, -20.0f, 12.0f, 18.0f, 9.0f, 11.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, -0.2182f, 0.0f, 0.0f));

        // cube_r2.
        body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 41).addBox(-10.0f, -18.0f, 5.0f, 20.0f, 9.0f, 16.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.1309f, 0.0f, 0.0f));

        // cube_r3.
        body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(58, 52).addBox(-11.0f, -18.0f, 2.0f, 22.0f, 9.0f, 14.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.3927f, 0.0f, 0.0f));

        // cube_r4.
        body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(64, 0).addBox(-12.0f, -22.0f, -4.0f, 24.0f, 11.0f, 11.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.5236f, 0.0f, 0.0f));

        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, 0.0f));

        // cube_r5.
        head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 66).addBox(-10.0f, -26.0f, -3.0f, 20.0f, 11.0f, 11.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.829f, 0.0f, 0.0f));

        // cube_r6.
        head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 22).addBox(-7.5f, -8.5f, -1.75f, 15.0f, 8.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -11.5f, -20.0f, 0.0f, 0.0f, 0.0f));

        // cube_r7.
        head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(62, 75).addBox(-9.0f, -8.5f, -1.75f, 18.0f, 13.0f, 11.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -11.5f, -20.0f, -0.6545f, 0.0f, 0.0f));

        PartDefinition jaw2 = head.addOrReplaceChild("jaw2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0f, -11.5f, -20.0f, -0.6545f, 0.0f, 0.0f));

        // cube_r8.
        jaw2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(96, 99).addBox(5.25f, -1.5f, -0.75f, 5.0f, 8.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5672f));

        PartDefinition jaw1 = head.addOrReplaceChild("jaw1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0f, -11.5f, -20.0f, -0.6545f, 0.0f, 0.0f));

        // cube_r9.
        jaw1.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 110).addBox(-10.25f, -1.5f, -0.75f, 5.0f, 8.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.5672f));

        // leg1.
        modelPartData.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(64, 99).addBox(-3.5f, -3.5f, -4.5f, 7.0f, 13.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(-9.5f, 14.5f, -6.5f));

        // leg2.
        modelPartData.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(32, 90).addBox(-3.5f, -3.5f, -4.5f, 7.0f, 13.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(9.5f, 14.5f, -6.5f));

        // leg3.
        modelPartData.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 88).addBox(-3.5f, -3.5f, -4.5f, 7.0f, 13.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(9.5f, 14.5f, 12.5f));

        // leg4.
        modelPartData.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5f, -3.5f, -4.5f, 7.0f, 13.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.offset(-9.5f, 14.5f, 12.5f));

        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

        Hoglin hoglin = null;
        Zoglin zoglin = null;

        if (entity instanceof Hoglin hog) {
            hoglin = hog;
        }

        if (entity instanceof Zoglin zog) {
            zoglin = zog;
        }

        if (entity instanceof Hoglin || entity instanceof Zoglin) {
            this.head.yRot = headYaw * ((float) Math.PI / 180.0f);
            int i = 0;
            if (hoglin != null) {
                i = hoglin.getAttackAnimationRemainingTicks();
            } else if (zoglin != null) {
                i = zoglin.getAttackAnimationRemainingTicks();
            }
            float f = 1.0f - (float) Mth.abs(10 - 2 * i) / 10.0f;
            this.head.xRot = Mth.lerp(f, 0.0f, -1.14906584f);

            this.leg1.xRot = Mth.cos(limbAngle) * 1.2f * limbDistance;
            this.leg2.xRot = Mth.cos(limbAngle + (float) Math.PI) * 1.2f * limbDistance;
            this.leg3.xRot = this.leg1.xRot;
            this.leg4.xRot = this.leg2.xRot;
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
        leg1.render(poseStack, vertices, packedLight, packedOverlay);
        leg2.render(poseStack, vertices, packedLight, packedOverlay);
        leg3.render(poseStack, vertices, packedLight, packedOverlay);
        leg4.render(poseStack, vertices, packedLight, packedOverlay);
    }
}
