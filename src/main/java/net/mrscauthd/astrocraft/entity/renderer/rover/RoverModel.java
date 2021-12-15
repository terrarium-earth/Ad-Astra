package net.mrscauthd.astrocraft.entity.renderer.rover;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.astrocraft.AstroCraftMod;
import net.mrscauthd.astrocraft.entity.RoverEntity;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

@OnlyIn(Dist.CLIENT)
public class RoverModel<T extends RoverEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AstroCraftMod.MODID, "rover"), "main");

    private final ModelPart Frame;

    public RoverModel(ModelPart root) {
        this.Frame = root.getChild("Frame");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Frame = partdefinition.addOrReplaceChild("Frame", CubeListBuilder.create().texOffs(0, 0).addBox(-17.5F, -10.5F, -52.5F, 35.0F, 3.0F, 73.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(12.5F, -15.0F, -37.5F, 0.0F, 5.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-12.5F, -15.0F, -37.5F, 0.0F, 5.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(56, 108).addBox(-17.5F, -8.0F, 12.5F, 35.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(56, 102).addBox(-17.5F, -8.0F, -47.5F, 35.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 130).addBox(-12.5F, -13.0F, -55.0F, 10.0F, 3.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(32, 64).addBox(-7.5F, -13.0F, -57.5F, 15.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 13).addBox(-3.0F, -13.0F, -55.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(12, 66).addBox(-10.5F, -15.5F, -52.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 66).addBox(-10.5F, -23.0F, -52.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(46, 137).addBox(7.0F, -50.5F, -55.0F, 3.0F, 43.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(138, 127).addBox(-13.0F, -20.0F, -55.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(136, 76).addBox(-13.0F, -27.5F, -55.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 102).addBox(-12.5F, -35.0F, 17.5F, 25.0F, 25.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 76).addBox(-15.0F, -13.0F, -12.5F, 30.0F, 3.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(83, 76).addBox(-10.0F, -20.0F, -42.5F, 20.0F, 10.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(0, 76).addBox(17.5F, -15.625F, -49.875F, 5.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-22.5F, -15.625F, -49.775F, 5.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(47, 51).addBox(17.5F, -15.625F, 10.125F, 5.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(37, 51).addBox(-22.5F, -15.625F, 10.625F, 5.0F, 0.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(56, 114).addBox(17.5F, -15.625F, -36.875F, 5.0F, 13.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-22.5F, -15.625F, -36.875F, 5.0F, 13.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(83, 76).addBox(17.5F, -15.625F, 23.125F, 5.0F, 13.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 76).addBox(-22.5F, -15.625F, 23.125F, 5.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = Frame.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 23).addBox(-15.0F, -22.5F, -2.5F, 30.0F, 25.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.1737F, 12.4786F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r2 = Frame.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(73, 140).addBox(-2.5F, -15.0F, 0.05F, 15.0F, 15.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(58, 140).addBox(-2.5F, -15.0F, 0.0F, 15.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, -52.5F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r3 = Frame.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 61).addBox(-7.5F, 0.0F, -5.0F, 15.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.4979F, -57.4987F, 0.5672F, 0.0F, 0.0F));

        PartDefinition Wheel1 = Frame.addOrReplaceChild("Wheel1", CubeListBuilder.create().texOffs(115, 127).addBox(0.0F, -6.75F, -6.25F, 5.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(17.5F, -6.25F, -46.25F));

        PartDefinition Wheel2 = Frame.addOrReplaceChild("Wheel2", CubeListBuilder.create().texOffs(119, 101).addBox(-2.5F, -6.75F, -6.25F, 5.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(20.0F, -6.25F, 13.75F));

        PartDefinition Wheel3 = Frame.addOrReplaceChild("Wheel3", CubeListBuilder.create().texOffs(56, 114).addBox(-2.5F, -6.75F, -6.25F, 5.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-20.0F, -6.25F, -46.25F));

        PartDefinition Wheel4 = Frame.addOrReplaceChild("Wheel4", CubeListBuilder.create().texOffs(92, 114).addBox(-2.5F, -6.75F, -6.25F, 5.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-20.0F, -6.25F, 13.75F));

        PartDefinition sat = Frame.addOrReplaceChild("sat", CubeListBuilder.create(), PartPose.offset(8.645F, -49.3111F, -53.75F));

        PartDefinition cube_r4 = sat.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-11.75F, 0.375F, -11.25F, 23.0F, 0.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.105F, -0.6889F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r5 = sat.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(79, 114).addBox(-1.5399F, -3.8778F, -1.3274F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.105F, -4.3111F, -0.9476F, 0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float f, float f1, float f2, float f3, float f4) {
        this.Frame.yRot = f3 / (180F / (float) Math.PI);

        this.Frame.getChild("Wheel1").xRot = f2 / (180F / (float) Math.PI);
        this.Frame.getChild("Wheel2").xRot = f2 / (180F / (float) Math.PI);
        this.Frame.getChild("Wheel3").xRot = f2 / (180F / (float) Math.PI);
        this.Frame.getChild("Wheel4").xRot = f2 / (180F / (float) Math.PI);
        if (entity.getforward()) {
            this.Frame.getChild("Wheel1").xRot = (float) (f / 4);
            this.Frame.getChild("Wheel2").xRot = (float) (f / 4);
            this.Frame.getChild("Wheel3").xRot = (float) (f / 4);
            this.Frame.getChild("Wheel4").xRot = (float) (f / 4);
        }
        if (!entity.getforward()) {
            this.Frame.getChild("Wheel1").xRot = (float) (f / 4);
            this.Frame.getChild("Wheel2").xRot = (float) (f / 4);
            this.Frame.getChild("Wheel3").xRot = (float) (f / 4);
            this.Frame.getChild("Wheel4").xRot = (float) (f / 4);
        }

        this.Frame.getChild("sat").yRot = f2 / 20f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Frame.render(poseStack, buffer, packedLight, packedOverlay);
    }
}