package net.mrscauthd.boss_tools.entity.renderer.mogler;

import com.google.common.collect.ImmutableList;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;


@OnlyIn(Dist.CLIENT)
public class MoglerModel/*<T extends MobEntity & IFlinging> extends AgeableModel<T>*/  {
    /*
    private final ModelRenderer body;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer head;
    private final ModelRenderer cube_r5;
    private final ModelRenderer cube_r6;
    private final ModelRenderer cube_r7;
    private final ModelRenderer jaw2;
    private final ModelRenderer cube_r8;
    private final ModelRenderer jaw1;
    private final ModelRenderer cube_r9;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;
    public MoglerModel() {
        textureWidth = 256;
        textureHeight = 256;
        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);
        body.setTextureOffset(0, 0).addBox(-8.0F, -15.0F, -13.0F, 16.0F, 9.0F, 32.0F, 0.0F, false);
        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.2182F, 0.0F, 0.0F);
        cube_r1.setTextureOffset(85, 30).addBox(-9.0F, -20.0F, 12.0F, 18.0F, 9.0F, 11.0F, 0.0F, false);
        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.1309F, 0.0F, 0.0F);
        cube_r2.setTextureOffset(0, 41).addBox(-10.0F, -18.0F, 5.0F, 20.0F, 9.0F, 16.0F, 0.0F, false);
        cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.3927F, 0.0F, 0.0F);
        cube_r3.setTextureOffset(58, 52).addBox(-11.0F, -18.0F, 2.0F, 22.0F, 9.0F, 14.0F, 0.0F, false);
        cube_r4 = new ModelRenderer(this);
        cube_r4.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.5236F, 0.0F, 0.0F);
        cube_r4.setTextureOffset(66, 2).addBox(-12.0F, -22.0F, -4.0F, 24.0F, 11.0F, 11.0F, 0.0F, false);
        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 9.0F, -8.0F);
        cube_r5 = new ModelRenderer(this);
        cube_r5.setRotationPoint(0.0F, 13.0F, 8.0F);
        head.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.829F, 0.0F, 0.0F);
        cube_r5.setTextureOffset(0, 66).addBox(-10.0F, -26.0F, -3.0F, 20.0F, 11.0F, 11.0F, 0.0F, false);
        cube_r6 = new ModelRenderer(this);
        cube_r6.setRotationPoint(0.0F, 1.5F, -12.0F);
        head.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, 0.0F, 0.0F);
        cube_r6.setTextureOffset(0, 22).addBox(-7.5F, -8.5F, -1.75F, 15.0F, 8.0F, 0.0F, 0.0F, false);
        cube_r7 = new ModelRenderer(this);
        cube_r7.setRotationPoint(0.0F, 1.5F, -12.0F);
        head.addChild(cube_r7);
        setRotationAngle(cube_r7, -0.6545F, 0.0F, 0.0F);
        cube_r7.setTextureOffset(62, 75).addBox(-9.0F, -8.5F, -1.75F, 18.0F, 13.0F, 11.0F, 0.0F, false);
        jaw2 = new ModelRenderer(this);
        jaw2.setRotationPoint(0.0F, 1.5F, -12.0F);
        head.addChild(jaw2);
        setRotationAngle(jaw2, -0.6545F, 0.0F, 0.0F);
        cube_r8 = new ModelRenderer(this);
        cube_r8.setRotationPoint(0.0F, 0.0F, 0.0F);
        jaw2.addChild(cube_r8);
        setRotationAngle(cube_r8, 0.0F, 0.0F, 0.5672F);
        cube_r8.setTextureOffset(96, 99).addBox(5.25F, -1.5F, -0.75F, 5.0F, 8.0F, 9.0F, 0.0F, false);
        jaw1 = new ModelRenderer(this);
        jaw1.setRotationPoint(0.0F, 1.5F, -12.0F);
        head.addChild(jaw1);
        setRotationAngle(jaw1, -0.6545F, 0.0F, 0.0F);
        cube_r9 = new ModelRenderer(this);
        cube_r9.setRotationPoint(0.0F, 0.0F, 0.0F);
        jaw1.addChild(cube_r9);
        setRotationAngle(cube_r9, 0.0F, 0.0F, -0.5672F);
        cube_r9.setTextureOffset(0, 110).addBox(-10.25F, -1.5F, -0.75F, 5.0F, 8.0F, 9.0F, 0.0F, false);
        leg1 = new ModelRenderer(this);
        leg1.setRotationPoint(-9.5F, 11.5F, -6.5F);
        leg1.setTextureOffset(64, 99).addBox(-3.5F, -0.5F, -4.5F, 7.0F, 13.0F, 9.0F, 0.0F, false);
        leg2 = new ModelRenderer(this);
        leg2.setRotationPoint(9.5F, 11.5F, -6.5F);
        leg2.setTextureOffset(32, 90).addBox(-3.5F, -0.5F, -4.5F, 7.0F, 13.0F, 9.0F, 0.0F, false);
        leg3 = new ModelRenderer(this);
        leg3.setRotationPoint(9.5F, 11.5F, 12.5F);
        leg3.setTextureOffset(0, 88).addBox(-3.5F, -0.5F, -4.5F, 7.0F, 13.0F, 9.0F, 0.0F, false);
        leg4 = new ModelRenderer(this);
        leg4.setRotationPoint(-9.5F, 11.5F, 12.5F);
        leg4.setTextureOffset(0, 0).addBox(-3.5F, -0.5F, -4.5F, 7.0F, 13.0F, 9.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,float alpha) {
        if (isChild) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
            matrixStack.translate(0, 1.5f, 0);
        }
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        leg1.render(matrixStack, buffer, packedLight, packedOverlay);
        leg2.render(matrixStack, buffer, packedLight, packedOverlay);
        leg3.render(matrixStack, buffer, packedLight, packedOverlay);
        leg4.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.body, this.leg1, this.leg2, this.leg3, this.leg4);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //     private final ModelRenderer field_239106_a_; head 52.5
        //     private final ModelRenderer field_239107_b_; right ear
        //     private final ModelRenderer field_239108_f_; left ear
        //     private final ModelRenderer field_239109_g_; body
        //     private final ModelRenderer field_239110_h_; frontRightLeg
        //     private final ModelRenderer field_239111_i_; frontLeftLeg
        //     private final ModelRenderer field_239112_j_; backRightLeg
        //     private final ModelRenderer field_239113_k_; backLeftLeg
        //     private final ModelRenderer field_239114_l_; mane



        //this.field_239107_b_.rotateAngleZ = -0.6981317F - limbSwingAmount * MathHelper.sin(limbSwing); //ear animation
        //this.field_239108_f_.rotateAngleZ = 0.6981317F + limbSwingAmount * MathHelper.sin(limbSwing); //ear animation
        //this.head.rotateAngleX = 52;
        this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        int i = entityIn.func_230290_eL_();
        float f = 1.0F - (float)MathHelper.abs(10 - 2 * i) / 10.0F;
        this.head.rotateAngleX = MathHelper.lerp(f, 0.0F, -1.14906584F);

        this.leg1.rotateAngleX = MathHelper.cos(limbSwing) * 1.2F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing + (float)Math.PI) * 1.2F * limbSwingAmount;
        this.leg3.rotateAngleX = this.leg1.rotateAngleX;
        this.leg4.rotateAngleX = this.leg2.rotateAngleX;
    }

     */
}