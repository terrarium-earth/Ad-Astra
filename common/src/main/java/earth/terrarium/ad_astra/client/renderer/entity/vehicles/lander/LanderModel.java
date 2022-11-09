package earth.terrarium.ad_astra.client.renderer.entity.vehicles.lander;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.entities.vehicles.Lander;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class LanderModel extends EntityModel<Lander> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ModResourceLocation("lander"), "main");
    private final ModelPart body;

    public LanderModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(49, 0).addBox(-3.0F, -118.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 58).addBox(-10.0F, -73.0F, -10.0F, 20.0F, 4.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(74, 9).addBox(-9.0F, -73.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(75, -10).addBox(-6.0F, -94.5F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(75, -10).addBox(6.0F, -94.5F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(88, 11).addBox(10.0F, -75.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(88, 11).addBox(-10.0F, -75.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(88, 31).addBox(-10.0F, -75.0F, 10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(88, 31).addBox(-10.0F, -75.0F, -10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(-4.0F, -110.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 93.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -110.0F, 0.0F, -0.3491F, 0.7854F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 12).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -83.0F, 0.0F, -2.8798F, -1.5708F, 3.1416F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -110.0F, 0.0F, -0.3491F, 2.3562F, 0.0F));

        PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 12).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -83.0F, 0.0F, -2.8798F, 3.1416F, 3.1416F));

        PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -110.0F, 0.0F, -0.3491F, -2.3562F, 0.0F));

        PartDefinition cube_r6 = body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 12).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -83.0F, 0.0F, -2.8798F, 1.5708F, 3.1416F));

        PartDefinition cube_r7 = body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.3647F, -94.5491F, 4.9353F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r8 = body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.3647F, -94.5491F, -6.3647F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r9 = body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(39, 0).addBox(-1.02F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.9353F, -94.5491F, -6.3647F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r10 = body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(75, -10).addBox(-6.0F, -4.5F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(75, -10).addBox(6.0F, -4.5F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -90.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r11 = body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(39, 0).addBox(-1.02F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.9353F, -94.5491F, 4.9353F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r12 = body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -110.0F, 0.0F, -0.3491F, -0.7854F, 0.0F));

        PartDefinition cube_r13 = body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 12).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -83.0F, 0.0F, -2.8798F, 0.0F, 3.1416F));

        PartDefinition cube_r14 = body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -101.0F, 0.0F, -0.3491F, -2.3562F, 0.0F));

        PartDefinition cube_r15 = body.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -101.0F, 0.0F, -0.3491F, 2.3562F, 0.0F));

        PartDefinition cube_r16 = body.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -101.0F, 0.0F, -0.3491F, -0.7854F, 0.0F));

        PartDefinition cube_r17 = body.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -101.0F, 0.0F, -0.3491F, 0.7854F, 0.0F));

        PartDefinition cube_r18 = body.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -74.0F, 0.0F, -2.8798F, -1.5708F, 3.1416F));

        PartDefinition cube_r19 = body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -74.0F, 0.0F, -2.8798F, 1.5708F, 3.1416F));

        PartDefinition cube_r20 = body.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -74.0F, 0.0F, -2.8798F, 3.1416F, 3.1416F));

        PartDefinition cube_r21 = body.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -74.0F, 0.0F, -2.8798F, 0.0F, 3.1416F));

        PartDefinition cube_r22 = body.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 42).addBox(-10.0F, -74.0F, 10.0F, 25.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition cube_r23 = body.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 42).addBox(-17.5F, -2.5F, 12.5F, 25.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -71.5F, -7.5F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r24 = body.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 42).addBox(-2.5F, -2.5F, 7.5F, 25.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -71.5F, -7.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r25 = body.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 42).addBox(-10.0F, -74.0F, 10.0F, 25.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition fins = body.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(-2.0F, -8.0F, -2.0F));

        PartDefinition pyramid = body.addOrReplaceChild("pyramid", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition booster = body.addOrReplaceChild("booster", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tip = body.addOrReplaceChild("tip", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
   public void setupAnim(Lander entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertices, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertices, packedLight, packedOverlay);
    }
}
