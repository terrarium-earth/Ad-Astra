package net.mrscauthd.boss_tools.entity.renderer.spacesuit;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SpaceSuitModel {
    public static class SPACE_SUIT_P1 extends EntityModel<Entity> {
        public final ModelRenderer Head;
        public final ModelRenderer Body;
        public final ModelRenderer ArmRight;
        public final ModelRenderer ArmLeft;
        public final ModelRenderer FootRight;
        public final ModelRenderer FootLeft;

        public SPACE_SUIT_P1() {
            textureWidth = 64;
            textureHeight = 64;
            Head = new ModelRenderer(this);
            Head.setRotationPoint(0.0F, 0.0F, 0.0F);
            setRotationAngle(Head, -0.0175F, 0.0873F, 0.0F);
            Head.setTextureOffset(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);
            Head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.75F, false);
            Body = new ModelRenderer(this);
            Body.setRotationPoint(0.0F, 0.0F, 0.0F);
            Body.setTextureOffset(0, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
            Body.setTextureOffset(28, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);
            Body.setTextureOffset(50, 29).addBox(-3.0F, 5.0F, -2.5F, 6.0F, 4.0F, 1.0F, 0.25F, false);
            Body.setTextureOffset(0, 55).addBox(-2.5F, 1.0F, 2.75F, 5.0F, 8.0F, 1.0F, 0.75F, false);
            ArmRight = new ModelRenderer(this);
            ArmRight.setRotationPoint(-5.0F, 2.0F, 0.0F);
            ArmRight.setTextureOffset(20, 44).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.26F, false);
            ArmLeft = new ModelRenderer(this);
            ArmLeft.setRotationPoint(5.0F, 2.0F, 0.0F);
            ArmLeft.setTextureOffset(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.26F, false);
            FootLeft = new ModelRenderer(this);
            FootLeft.setRotationPoint(2.0F, 12.0F, 0.0F);
            FootLeft.setTextureOffset(48, 44).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.4F, false);
            FootLeft.setTextureOffset(48, 54).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.26F, false);
            FootRight = new ModelRenderer(this);
            FootRight.setRotationPoint(-2.0F, 12.0F, 0.0F);
            FootRight.setTextureOffset(48, 44).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.4F, false);
            FootRight.setTextureOffset(48, 54).addBox(-2.0F, 5.7F, -2.0F, 4.0F, 6.0F, 4.0F, 0.26F, false);
        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }

        @Override
        public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            Head.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
            Body.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
            ArmRight.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
            ArmLeft.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
            FootLeft.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
            FootRight.render(matrixStack, buffer, packedLight, packedOverlay, red,green,blue,alpha);
        }

        @Override
        public void setRotationAngles(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

    }

    public static class SPACE_SUIT_P2 extends EntityModel<Entity> {
        public final ModelRenderer LegRight;
        public final ModelRenderer LegLeft;

        public SPACE_SUIT_P2() {
            textureWidth = 64;
            textureHeight = 32;
            LegRight = new ModelRenderer(this);
            LegRight.setRotationPoint(-1.9F, 12.0F, 0.0F);
            LegRight.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.6F, false);
            LegLeft = new ModelRenderer(this);
            LegLeft.setRotationPoint(1.9F, 12.0F, 0.0F);
            LegLeft.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.6F, true);
        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }

        @Override
        public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            LegRight.render(matrixStack, buffer, packedLight, packedOverlay);
            LegLeft.render(matrixStack, buffer, packedLight, packedOverlay);
        }

        public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {

        }

    }
}
