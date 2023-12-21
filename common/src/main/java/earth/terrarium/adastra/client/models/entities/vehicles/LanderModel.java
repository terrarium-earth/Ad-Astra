package earth.terrarium.adastra.client.models.entities.vehicles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.entities.vehicles.Lander;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class LanderModel extends EntityModel<Lander> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(AdAstra.MOD_ID, "lander"), "main");

    private final ModelPart root;

    public LanderModel(ModelPart root) {
        this.root = root.getChild("main");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition raft = body.addOrReplaceChild("raft", CubeListBuilder.create(), PartPose.offset(-7.0F, 1.5F, -13.0F));

        PartDefinition cube_r1 = raft.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-6.0F, -3.5F, -5.0F, 12.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r2 = raft.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 42).addBox(-6.0F, -75.5F, 8.0F, 12.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 71.5F, 13.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition cube_r3 = raft.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 59).addBox(-9.0F, -3.0F, -5.0F, 18.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.8839F, -0.5F, 5.1265F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r4 = raft.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-6.0F, -4.0F, 8.0F, 12.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, 0.0F, 13.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r5 = raft.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 59).mirror().addBox(-9.0F, -3.0F, -5.0F, 18.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(14.8839F, -0.5F, 5.1265F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r6 = raft.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 59).mirror().addBox(-4.75F, -3.5F, -8.0F, 18.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(14.0F, 0.0F, 26.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r7 = raft.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 59).addBox(-13.25F, -3.5F, -8.0F, 18.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 26.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r8 = raft.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 42).addBox(-6.0F, -4.0F, 8.0F, 12.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 0.0F, 13.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition main = body.addOrReplaceChild("main", CubeListBuilder.create().texOffs(74, 9).addBox(-9.0F, -73.0F, -9.0F, 18.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 73.0F, 0.0F));

        PartDefinition fins = main.addOrReplaceChild("fins", CubeListBuilder.create(), PartPose.offset(-2.0F, -8.0F, -2.0F));

        PartDefinition pyramid = main.addOrReplaceChild("pyramid", CubeListBuilder.create().texOffs(75, -10).addBox(-6.0F, -77.5F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(75, -10).addBox(6.0F, -77.5F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(88, 11).addBox(10.0F, -58.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
            .texOffs(88, 11).addBox(-10.0F, -58.0F, -10.0F, 0.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
            .texOffs(88, 31).addBox(-10.0F, -58.0F, 10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(88, 31).addBox(-10.0F, -58.0F, -10.0F, 20.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition cube_r9 = pyramid.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -93.0F, 0.0F, -0.3491F, 0.7854F, 0.0F));

        PartDefinition cube_r10 = pyramid.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 12).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -2.8798F, -1.5708F, 3.1416F));

        PartDefinition cube_r11 = pyramid.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -93.0F, 0.0F, -0.3491F, 2.3562F, 0.0F));

        PartDefinition cube_r12 = pyramid.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 12).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -2.8798F, 3.1416F, 3.1416F));

        PartDefinition cube_r13 = pyramid.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -93.0F, 0.0F, -0.3491F, -2.3562F, 0.0F));

        PartDefinition cube_r14 = pyramid.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 12).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -2.8798F, 1.5708F, 3.1416F));

        PartDefinition cube_r15 = pyramid.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.3647F, -77.5491F, 4.9353F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r16 = pyramid.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.3647F, -77.5491F, -6.3647F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r17 = pyramid.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(39, 0).addBox(-1.02F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.9353F, -77.5491F, -6.3647F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r18 = pyramid.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(75, -10).addBox(-6.0F, -4.5F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(75, -10).addBox(6.0F, -4.5F, -5.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -73.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r19 = pyramid.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(39, 0).addBox(-1.02F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.9353F, -77.5491F, 4.9353F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r20 = pyramid.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -93.0F, 0.0F, -0.3491F, -0.7854F, 0.0F));

        PartDefinition cube_r21 = pyramid.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 12).addBox(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -2.8798F, 0.0F, 3.1416F));

        PartDefinition cube_r22 = pyramid.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 0.0F, -0.3491F, -2.3562F, 0.0F));

        PartDefinition cube_r23 = pyramid.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 0.0F, -0.3491F, 2.3562F, 0.0F));

        PartDefinition cube_r24 = pyramid.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 0.0F, -0.3491F, -0.7854F, 0.0F));

        PartDefinition cube_r25 = pyramid.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(39, 0).addBox(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -84.0F, 0.0F, -0.3491F, 0.7854F, 0.0F));

        PartDefinition cube_r26 = pyramid.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -57.0F, 0.0F, -2.8798F, -1.5708F, 3.1416F));

        PartDefinition cube_r27 = pyramid.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -57.0F, 0.0F, -2.8798F, 1.5708F, 3.1416F));

        PartDefinition cube_r28 = pyramid.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -57.0F, 0.0F, -2.8798F, 3.1416F, 3.1416F));

        PartDefinition cube_r29 = pyramid.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -57.0F, 0.0F, -2.8798F, 0.0F, 3.1416F));

        PartDefinition booster = main.addOrReplaceChild("booster", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tip = main.addOrReplaceChild("tip", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -110.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(49, 0).addBox(-3.0F, -118.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Lander entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
