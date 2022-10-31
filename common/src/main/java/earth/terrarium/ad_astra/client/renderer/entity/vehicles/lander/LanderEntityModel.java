package earth.terrarium.ad_astra.client.renderer.entity.vehicles.lander;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.entities.vehicles.LanderEntity;
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

@Environment(EnvType.CLIENT)
public class LanderEntityModel extends EntityModel<LanderEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ModResourceLocation("lander"), "main");
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;
    private final ModelPart bb_main;

    public LanderEntityModel(ModelPart root) {
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
        this.leg4 = root.getChild("leg4");
        this.bb_main = root.getChild("bb_main");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition leg1 = modelPartData.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0f, 3.0f, 11.0f, 10.0f, 1.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, 20.0f, 0.0f));

        PartDefinition cube_r1 = leg1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-21.0f, 0.99f, -2.0f, 4.0f, 2.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 0.7854f, 0.0f));

        PartDefinition cube_r2 = leg1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 31).addBox(-2.0f, -2.0f, 18.0f, 4.0f, 4.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(16, 11).addBox(-3.0f, -3.0f, 14.0f, 6.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.6109f, -0.7854f, 0.0f));

        PartDefinition cube_r3 = leg1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(48, 46).addBox(-1.5f, -2.0f, 15.0f, 3.0f, 12.0f, 3.0f, new CubeDeformation(-0.5f)).texOffs(0, 17).addBox(-1.5f, -2.0f, 15.0f, 3.0f, 11.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.3927f, -0.7854f, 0.0f));

        PartDefinition leg2 = modelPartData.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(0.0f, 20.0f, 0.0f));

        PartDefinition cube_r4 = leg2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(16, 11).addBox(-3.0f, -3.0f, 14.0f, 6.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)).texOffs(0, 31).addBox(-2.0f, -2.0f, 18.0f, 4.0f, 4.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.6109f, -2.3562f, 0.0f));

        PartDefinition cube_r5 = leg2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(48, 46).addBox(-1.5f, -2.0f, 15.0f, 3.0f, 12.0f, 3.0f, new CubeDeformation(-0.5f)).texOffs(0, 17).addBox(-1.5f, -2.0f, 15.0f, 3.0f, 11.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.3927f, -2.3562f, 0.0f));

        PartDefinition cube_r6 = leg2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0f, 3.0f, 11.0f, 10.0f, 1.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, -1.5708f, 0.0f));

        PartDefinition cube_r7 = leg2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 11).addBox(-21.0f, 0.99f, -2.0f, 4.0f, 2.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, -0.7854f, 0.0f));

        PartDefinition leg3 = modelPartData.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offset(0.0f, 20.0f, 0.0f));

        PartDefinition cube_r8 = leg3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 31).addBox(-2.0f, -2.0f, 18.0f, 4.0f, 4.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(16, 11).addBox(-3.0f, -3.0f, 14.0f, 6.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.6109f, 2.3562f, 0.0f));

        PartDefinition cube_r9 = leg3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(48, 46).addBox(-1.5f, -2.0f, 15.0f, 3.0f, 12.0f, 3.0f, new CubeDeformation(-0.5f)).texOffs(0, 17).addBox(-1.5f, -2.0f, 15.0f, 3.0f, 11.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.3927f, 2.3562f, 0.0f));

        PartDefinition cube_r10 = leg3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0f, 3.0f, 11.0f, 10.0f, 1.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 3.1416f, 0.0f));

        PartDefinition cube_r11 = leg3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 11).addBox(-21.0f, 0.99f, -2.0f, 4.0f, 2.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, -2.3562f, 0.0f));

        PartDefinition leg4 = modelPartData.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offset(0.0f, 20.0f, 0.0f));

        PartDefinition cube_r12 = leg4.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 31).addBox(-2.0f, -2.0f, 18.0f, 4.0f, 4.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(16, 11).addBox(-3.0f, -3.0f, 14.0f, 6.0f, 6.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.6109f, 0.7854f, 0.0f));

        PartDefinition cube_r13 = leg4.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(48, 46).addBox(-1.5f, -2.0f, 15.0f, 3.0f, 12.0f, 3.0f, new CubeDeformation(-0.5f)).texOffs(0, 17).addBox(-1.5f, -2.0f, 15.0f, 3.0f, 11.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.3927f, 0.7854f, 0.0f));

        PartDefinition cube_r14 = leg4.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 0).addBox(-21.0f, 3.0f, 11.0f, 10.0f, 1.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 1.5708f, 0.0f));

        PartDefinition cube_r15 = leg4.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 11).addBox(-21.0f, 0.99f, -2.0f, 4.0f, 2.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.0f, 2.3562f, 0.0f));

        PartDefinition bb_main = modelPartData.addOrReplaceChild("bb_main",
                CubeListBuilder.create().texOffs(88, 0).addBox(-5.0f, -19.0f, -8.0f, 10.0f, 3.0f, 10.0f, new CubeDeformation(0.0f)).texOffs(0, 44).addBox(-8.0f, -35.0f, -13.0f, 16.0f, 16.0f, 16.0f, new CubeDeformation(0.0f)).texOffs(78, 46).addBox(8.0f, -30.5f, -8.5f, 2.0f, 10.0f, 10.0f, new CubeDeformation(0.0f)).texOffs(0, 44).addBox(1.0f, -33.0f, 14.0f, 3.0f, 9.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(0, 44).addBox(-4.0f, -33.0f, 14.0f, 3.0f, 9.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(88, 18).addBox(-6.0f, -23.0f, -14.0f, 12.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(88, 18).addBox(-6.0f, -31.0f, -16.0f, 12.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(64, 56).addBox(10.0f, -28.5f, -6.5f, 1.0f, 6.0f, 6.0f, new CubeDeformation(0.0f)).texOffs(42, 26).addBox(-5.0f, -13.9f, -5.0f, 10.0f, 3.0f, 10.0f, new CubeDeformation(0.0f)).texOffs(0, 26).addBox(-7.0f, -10.9f, -7.0f, 14.0f, 4.0f, 14.0f, new CubeDeformation(0.0f)).texOffs(16, 0).addBox(-12.0f, -16.0f, -12.0f, 24.0f, 2.0f, 24.0f, new CubeDeformation(0.0f)).texOffs(82, 26).addBox(-8.995f, -36.0f, -12.0f, 18.0f, 18.0f, 2.0f, new CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 24.0f, 0.0f));

        PartDefinition cube_r16 = bb_main.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(82, 26).addBox(-9.0f, -9.0f, -1.0f, 18.0f, 18.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -33.0f, -5.0f, 1.5708f, 0.0f, -3.1416f));

        PartDefinition cube_r17 = bb_main.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(64, 56).addBox(9.5f, -10.5f, 0.5f, 1.0f, 6.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, -18.0f, 0.0f, 0.0f, 3.1416f, 0.0f));

        PartDefinition cube_r18 = bb_main.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(64, 68).addBox(-6.0f, -38.0f, 5.0f, 12.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)).texOffs(64, 68).addBox(-6.0f, -38.0f, 0.0f, 12.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.2182f, 0.0f, 0.0f));

        PartDefinition cube_r19 = bb_main.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(42, 76).mirror().addBox(-12.5f, -25.5f, -7.5f, 4.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(54, 80).mirror().addBox(-12.5f, -10.5f, -7.5f, 4.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false).texOffs(54, 80).addBox(9.5f, -10.5f, -7.5f, 4.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(42, 76).addBox(9.5f, -25.5f, -7.5f, 4.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)).texOffs(0, 96).addBox(-11.5f, -24.5f, -6.5f, 24.0f, 17.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, -15.5f, 19.5f, 0.2182f, 0.0f, 0.0f));

        PartDefinition cube_r20 = bb_main.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(56, 74).addBox(-4.5f, -15.5f, -12.5f, 10.0f, 10.0f, 14.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, -17.5f, 15.5f, 0.2182f, 0.0f, 0.0f));

        PartDefinition cube_r21 = bb_main.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(52, 98).addBox(-9.5f, -21.5f, -6.5f, 20.0f, 13.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, -15.5f, 23.5f, 0.2182f, 0.0f, 0.0f));

        PartDefinition cube_r22 = bb_main.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(102, 46).addBox(-4.5f, -13.0f, 0.0f, 9.0f, 13.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(0.0f, -31.0f, -15.0f, 0.3054f, 0.0f, 0.0f));

        PartDefinition cube_r23 = bb_main.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(48, 44).addBox(-5.0f, -16.5f, -6.5f, 11.0f, 9.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, -15.5f, -3.5f, 0.3927f, 0.0f, 0.0f));

        PartDefinition cube_r24 = bb_main.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 76).addBox(-6.5f, -19.5f, -8.5f, 14.0f, 3.0f, 14.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-0.5f, -17.5f, 0.5f, 0.2182f, 0.0f, 0.0f));

        PartDefinition cube_r25 = bb_main.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(78, 46).addBox(-1.5f, -8.5f, -5.5f, 2.0f, 10.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.offsetAndRotation(-9.5f, -22.0f, -4.0f, 0.0f, 3.1416f, 0.0f));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
   public void setupAnim(LanderEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        leg1.render(matrices, vertices, light, overlay);
        leg2.render(matrices, vertices, light, overlay);
        leg3.render(matrices, vertices, light, overlay);
        leg4.render(matrices, vertices, light, overlay);
        bb_main.render(matrices, vertices, light, overlay);
    }
}
