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

@Environment(EnvType.CLIENT)
public class SulfurCreeperModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ModResourceLocation("sulfur_creeper"), "main");

    private final ModelPart body;

    public SulfurCreeperModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(50, 9).addBox(0.0F, -17.0F, 0.0F, 0.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(16, 16).addBox(-4.0F, -18.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition sulfurcrystalbodyR_r1 = body.addOrReplaceChild("sulfurcrystalbodyR_r1", CubeListBuilder.create().texOffs(50, 0).addBox(3.4317F, -7.4874F, -1.9048F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(50, 0).addBox(14.4317F, -8.4874F, 0.0952F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -17.0F, 2.0F, 0.0983F, 0.0831F, 2.9853F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));

        PartDefinition sulfurcrystalhead_r1 = head.addOrReplaceChild("sulfurcrystalhead_r1", CubeListBuilder.create().texOffs(50, 0).addBox(-5.5F, -4.0F, -4.5F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -7.0F, 1.0F, 0.1259F, -0.3419F, 0.4257F));

        PartDefinition BackLeftLeg = body.addOrReplaceChild("BackLeftLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -6.0F, 4.0F));

        PartDefinition BackRightLeg = body.addOrReplaceChild("BackRightLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -6.0F, 4.0F));

        PartDefinition FrontLeftLeg = body.addOrReplaceChild("FrontLeftLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -6.0F, -4.0F));

        PartDefinition FrontRightLeg = body.addOrReplaceChild("FrontRightLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -6.0F, -4.0F));
        return LayerDefinition.create(modelData, 64, 32);
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.body.getChild("head").yRot = (float) (headYaw * Math.PI / 180.0);
        this.body.getChild("head").xRot = (float) (headPitch * Math.PI / 180.0);
        this.body.getChild("BackLeftLeg").xRot = Mth.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
        this.body.getChild("BackRightLeg").xRot = Mth.cos(limbAngle * 0.6662f + (float) Math.PI) * 1.4f * limbDistance;
        this.body.getChild("FrontLeftLeg").xRot = Mth.cos(limbAngle * 0.6662f + (float) Math.PI) * 1.4f * limbDistance;
        this.body.getChild("FrontRightLeg").xRot = Mth.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}