package net.mrscauthd.beyond_earth.entity.renderer.lander;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.LanderEntity;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

@OnlyIn(Dist.CLIENT)
public class LanderModel<T extends LanderEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondEarthMod.MODID, "lander"), "main");
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;
    private final ModelPart bb_main;

    public LanderModel(ModelPart root) {
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
        this.leg4 = root.getChild("leg4");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 144).addBox(-21.0F, 3.0F, 11.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition cube_r1 = leg1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(116, 44).addBox(-1.0F, -12.5F, 16.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6981F, -0.7854F, 0.0F));

        PartDefinition cube_r2 = leg1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(116, 37).addBox(-21.0F, 0.99F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r3 = leg1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 26).addBox(-2.0F, -2.0F, 18.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(42, 26).addBox(-3.0F, -3.0F, 14.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, -0.7854F, 0.0F));

        PartDefinition cube_r4 = leg1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(48, 46).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.5F))
                .texOffs(104, 37).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, -0.7854F, 0.0F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition cube_r5 = leg2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(42, 26).addBox(-3.0F, -3.0F, 14.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(64, 26).addBox(-2.0F, -2.0F, 18.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, -2.3562F, 0.0F));

        PartDefinition cube_r6 = leg2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(48, 46).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.5F))
                .texOffs(104, 37).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, -2.3562F, 0.0F));

        PartDefinition cube_r7 = leg2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 144).addBox(-21.0F, 3.0F, 11.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r8 = leg2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(116, 37).addBox(-21.0F, 0.99F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r9 = leg2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(116, 44).addBox(-1.0F, -12.5F, 16.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6981F, -2.3562F, 0.0F));

        PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition cube_r10 = leg3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(64, 26).addBox(-2.0F, -2.0F, 18.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(42, 26).addBox(-3.0F, -3.0F, 14.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 2.3562F, 0.0F));

        PartDefinition cube_r11 = leg3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(48, 46).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.5F))
                .texOffs(104, 37).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 2.3562F, 0.0F));

        PartDefinition cube_r12 = leg3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 144).addBox(-21.0F, 3.0F, 11.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r13 = leg3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(116, 37).addBox(-21.0F, 0.99F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r14 = leg3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(116, 44).addBox(-1.0F, -12.5F, 16.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6981F, 2.3562F, 0.0F));

        PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition cube_r15 = leg4.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(64, 26).addBox(-2.0F, -2.0F, 18.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(42, 26).addBox(-3.0F, -3.0F, 14.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.7854F, 0.0F));

        PartDefinition cube_r16 = leg4.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(48, 46).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(-0.5F))
                .texOffs(104, 37).addBox(-1.5F, -2.0F, 15.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.7854F, 0.0F));

        PartDefinition cube_r17 = leg4.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 144).addBox(-21.0F, 3.0F, 11.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r18 = leg4.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(116, 37).addBox(-21.0F, 0.99F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r19 = leg4.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(116, 44).addBox(-1.0F, -12.5F, 16.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6981F, 0.7854F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(155, 59).addBox(-6.0F, -11.9F, -6.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-12.0F, -16.0F, -12.0F, 24.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(0, 111).addBox(-5.0F, -19.0F, -8.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 46).addBox(-8.0F, -35.0F, -13.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 124).addBox(8.0F, -30.0F, -8.0F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(104, 12).addBox(-8.995F, -36.0F, -12.0F, 18.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(155, 24).addBox(3.5F, -46.0F, -15.5F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(155, 24).addBox(-4.5F, -46.0F, -15.5F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(159, 24).addBox(1.5F, -41.0F, -15.5F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(163, 24).addBox(-2.5F, -43.0F, -15.5F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(167, 24).addBox(-0.5F, -44.0F, -15.5F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 0).addBox(1.0F, -33.0F, 14.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(104, 0).addBox(-4.0F, -33.0F, 14.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(155, 39).addBox(-4.0F, -13.9F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(155, 48).addBox(-5.0F, -12.9F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(104, 51).addBox(-6.0F, -25.0F, -14.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(104, 51).addBox(-6.0F, -33.0F, -16.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(104, 51).addBox(-6.0F, -16.505F, 9.005F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 134).addBox(10.0F, -28.0F, -5.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(34, 134).addBox(10.0F, -27.0F, -6.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r20 = bb_main.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(24, 134).addBox(9.5F, -10.5F, 1.5F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(34, 134).addBox(9.5F, -9.5F, 0.5F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -17.5F, 0.5F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r21 = bb_main.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(104, 51).addBox(-6.0F, -16.505F, 9.005F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r22 = bb_main.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(104, 51).addBox(-6.0F, -16.505F, 9.005F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r23 = bb_main.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(104, 51).addBox(-6.0F, -16.505F, 9.005F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r24 = bb_main.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(104, 51).addBox(-6.0F, -38.0F, 5.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(104, 51).addBox(-6.0F, -38.0F, 0.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r25 = bb_main.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(104, 51).addBox(-6.0F, -8.0F, 15.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(104, 51).addBox(-6.0F, -11.0F, 12.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r26 = bb_main.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(14, 126).addBox(-12.5F, -25.5F, -7.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(14, 126).addBox(-12.5F, -10.5F, -7.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(14, 126).addBox(9.5F, -10.5F, -7.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(14, 126).addBox(9.5F, -25.5F, -7.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 92).addBox(-11.5F, -24.5F, -6.5F, 24.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -15.5F, 19.5F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r27 = bb_main.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(104, 32).addBox(-6.0F, -16.5F, 11.0F, 12.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r28 = bb_main.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(104, 32).addBox(-6.0F, -16.5F, 11.0F, 12.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r29 = bb_main.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(104, 32).addBox(-6.0F, -16.5F, 11.0F, 12.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r30 = bb_main.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(104, 32).addBox(-6.0F, -16.5F, 11.0F, 12.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r31 = bb_main.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(155, 0).addBox(-4.5F, -15.5F, -12.5F, 10.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -17.5F, 15.5F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r32 = bb_main.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(0, 78).addBox(-9.5F, -21.5F, -6.5F, 20.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -15.5F, 23.5F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r33 = bb_main.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(116, 0).addBox(-5.0F, -16.5F, -6.5F, 11.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -17.5F, -3.5F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r34 = bb_main.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(104, 12).addBox(-8.5F, -15.5F, 14.5F, 18.0F, 18.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -17.5F, 1.5F, 1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r35 = bb_main.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(0, 26).addBox(-6.5F, -19.5F, -8.5F, 14.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -17.5F, 0.5F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r36 = bb_main.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(0, 124).addBox(-1.5F, -8.5F, -5.5F, 2.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.5F, -21.5F, -3.5F, 0.0F, 3.1416F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        leg1.render(poseStack, buffer, packedLight, packedOverlay);
        leg2.render(poseStack, buffer, packedLight, packedOverlay);
        leg3.render(poseStack, buffer, packedLight, packedOverlay);
        leg4.render(poseStack, buffer, packedLight, packedOverlay);
        bb_main.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
