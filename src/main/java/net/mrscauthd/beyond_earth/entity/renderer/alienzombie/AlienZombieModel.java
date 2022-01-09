package net.mrscauthd.beyond_earth.entity.renderer.alienzombie;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.AlienZombieEntity;

@OnlyIn(Dist.CLIENT)
public class AlienZombieModel<T extends AlienZombieEntity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(BeyondEarthMod.MODID, "alien_zombie"), "main");

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

    public AlienZombieModel(ModelPart root) {
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

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.0F, -6.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(88, 59).mirror().addBox(-0.5095F, -2.211F, -0.6496F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.25F, 4.1027F, -5.534F, 1.0036F, 0.48F, -0.8727F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(88, 59).addBox(-1.7975F, -1.8508F, -0.9483F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 4.1027F, -5.534F, 1.0036F, -0.48F, 0.8727F));

        PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(88, 54).addBox(-1.5F, -1.75F, -0.75F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.8527F, -7.434F, -0.3927F, 0.0F, 0.0F));

        PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(33, 0).mirror().addBox(-4.5F, -3.9804F, -4.3483F, 9.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -14.0F, 1.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition head_r2 = head.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(40, 53).mirror().addBox(-3.0F, 2.8551F, 0.3492F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -5.0F, 0.0F, -1.0036F, 0.0F, 0.0F));

        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.offset(0.0F, -3.1927F, 0.7599F));

        PartDefinition nose_r1 = nose.addOrReplaceChild("nose_r1", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-1.0F, -0.5896F, 1.4131F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, -6.0F, -1.0036F, 0.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).mirror().addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 38).mirror().addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition leg0 = partdefinition.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition arm1 = partdefinition.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(-8.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.0F, -1.5F, 0.0436F, 0.0F, 0.0F));

        PartDefinition arm2 = partdefinition.addOrReplaceChild("arm2", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -5.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 7.0F, -1.5F));

        PartDefinition monsterarm1 = partdefinition.addOrReplaceChild("monsterarm1", CubeListBuilder.create().texOffs(30, 46).addBox(-17.0F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.9599F));

        PartDefinition cube_r4 = monsterarm1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(34, 46).addBox(-15.0F, -1.0F, -1.0F, 15.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-16.25F, 0.0F, 0.75F, 0.0F, -1.1345F, 0.0F));

        PartDefinition monsterarm2 = partdefinition.addOrReplaceChild("monsterarm2", CubeListBuilder.create().texOffs(30, 46).addBox(-17.0F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -0.25F, -3.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r5 = monsterarm2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(34, 46).addBox(-15.0F, -1.0F, -1.0F, 15.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-16.25F, 0.0F, 0.75F, 0.0F, -1.1345F, 0.0F));

        PartDefinition monsterarm3 = partdefinition.addOrReplaceChild("monsterarm3", CubeListBuilder.create().texOffs(30, 46).addBox(-17.0F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, -2.0F, 0.0F, 0.0F, 2.1817F));

        PartDefinition cube_r6 = monsterarm3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(34, 46).addBox(-15.0F, -1.0F, -1.0F, 15.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-16.25F, 0.0F, 0.75F, 0.0F, -1.1345F, 0.0F));

        PartDefinition monsterarm4 = partdefinition.addOrReplaceChild("monsterarm4", CubeListBuilder.create().texOffs(30, 46).addBox(-17.0F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -0.25F, -3.0F, 0.0F, 0.0F, -2.7489F));

        PartDefinition cube_r7 = monsterarm4.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(30, 46).addBox(-15.0F, -1.0F, -1.0F, 15.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-16.25F, 0.0F, 0.75F, 0.0F, -1.1345F, 0.0F));

        return LayerDefinition.create(meshdefinition, 96, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        leg0.render(poseStack, buffer, packedLight, packedOverlay);
        leg1.render(poseStack, buffer, packedLight, packedOverlay);
        arm1.render(poseStack, buffer, packedLight, packedOverlay);
        arm2.render(poseStack, buffer, packedLight, packedOverlay);
        monsterarm1.render(poseStack, buffer, packedLight, packedOverlay);
        monsterarm2.render(poseStack, buffer, packedLight, packedOverlay);
        monsterarm3.render(poseStack, buffer, packedLight, packedOverlay);
        monsterarm4.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void setupAnim(T entity, float f, float f1, float f2, float f3, float f4) {
        //base
        this.arm2.yRot = 0.0F;
        this.arm1.yRot = 0.0F;
        this.arm2.zRot = 0.0F;
        this.arm1.zRot = 0.0F;
        this.arm2.xRot = 0.0F;
        this.arm1.xRot = 0.0F;

        this.arm2.zRot -= Mth.cos(f2 * 0.04F) * 0.04F + 0.04F;
        this.arm1.zRot += Mth.cos(f2 * 0.04F) * 0.04F + 0.04F;

        //base end

        this.head.yRot = f3 / (180F / (float) Math.PI);
        this.head.xRot = f4 / (180F / (float) Math.PI);
        this.leg0.xRot = Mth.cos(f * 1.0F) * -1.0F * f1;
        this.leg1.xRot = Mth.cos(f * 1.0F) * 1.0F * f1;
        this.monsterarm1.yRot = Mth.cos(f * 0.3662F + (float) Math.PI) * f1 / 2;
        this.monsterarm4.yRot = Mth.cos(f * 0.3662F + (float) Math.PI) * f1 / 2;
        this.monsterarm3.yRot = Mth.cos(f * 0.3662F + (float) Math.PI) * f1 / 2;
        this.monsterarm2.yRot = Mth.cos(f * 0.3662F + (float) Math.PI) * f1 / 2;
        this.arm1.xRot = 30f;
        this.arm2.xRot = 30f;

        this.arm2.xRot -= Mth.cos(f2 * 0.04F) * 0.04F + 0.04F;
        this.arm1.xRot += Mth.cos(f2 * 0.04F) * 0.04F + 0.04F;
    }
}
