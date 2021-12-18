package net.mrscauthd.beyond_earth.entity.renderer.mogler;

import com.google.common.collect.ImmutableList;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.mrscauthd.beyond_earth.BeyondEarthMod;

@OnlyIn(Dist.CLIENT)
public class MoglerModel<T extends Mob & HoglinBase> extends AgeableListModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondEarthMod.MODID, "mogler"), "main");
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
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -15.0F, -13.0F, 16.0F, 9.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(85, 30).addBox(-9.0F, -20.0F, 12.0F, 18.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 41).addBox(-10.0F, -18.0F, 5.0F, 20.0F, 9.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(58, 52).addBox(-11.0F, -18.0F, 2.0F, 22.0F, 9.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(64, 0).addBox(-12.0F, -22.0F, -4.0F, 24.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r5 = head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 66).addBox(-10.0F, -26.0F, -3.0F, 20.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.829F, 0.0F, 0.0F));

        PartDefinition cube_r6 = head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 22).addBox(-7.5F, -8.5F, -1.75F, 15.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.5F, -20.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition cube_r7 = head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(62, 75).addBox(-9.0F, -8.5F, -1.75F, 18.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.5F, -20.0F, -0.6545F, 0.0F, 0.0F));

        PartDefinition jaw2 = head.addOrReplaceChild("jaw2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -11.5F, -20.0F, -0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r8 = jaw2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(96, 99).addBox(5.25F, -1.5F, -0.75F, 5.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition jaw1 = head.addOrReplaceChild("jaw1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -11.5F, -20.0F, -0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r9 = jaw1.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 110).addBox(-10.25F, -1.5F, -0.75F, 5.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(64, 99).addBox(-3.5F, -3.5F, -4.5F, 7.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.5F, 14.5F, -6.5F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(32, 90).addBox(-3.5F, -3.5F, -4.5F, 7.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 14.5F, -6.5F));

        PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 88).addBox(-3.5F, -3.5F, -4.5F, 7.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 14.5F, 12.5F));

        PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -4.5F, 7.0F, 13.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.5F, 14.5F, 12.5F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        int i = entity.getAttackAnimationRemainingTicks();
        float f = 1.0F - (float) Mth.abs(10 - 2 * i) / 10.0F;
        this.head.xRot = Mth.lerp(f, 0.0F, -1.14906584F);

        this.leg1.xRot = Mth.cos(limbSwing) * 1.2F * limbSwingAmount;
        this.leg2.xRot = Mth.cos(limbSwing + (float)Math.PI) * 1.2F * limbSwingAmount;
        this.leg3.xRot = this.leg1.xRot;
        this.leg4.xRot = this.leg2.xRot;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (young) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
            poseStack.translate(0, 1.5f, 0);
        }
        body.render(poseStack, buffer, packedLight, packedOverlay);
        head.render(poseStack, buffer, packedLight, packedOverlay);
        leg1.render(poseStack, buffer, packedLight, packedOverlay);
        leg2.render(poseStack, buffer, packedLight, packedOverlay);
        leg3.render(poseStack, buffer, packedLight, packedOverlay);
        leg4.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.leg1, this.leg2, this.leg3, this.leg4);
    }
}