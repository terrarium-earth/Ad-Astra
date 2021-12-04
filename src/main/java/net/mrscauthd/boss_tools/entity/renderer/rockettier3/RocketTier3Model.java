package net.mrscauthd.boss_tools.entity.renderer.rockettier3;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;

@OnlyIn(Dist.CLIENT)
public class RocketTier3Model<T extends RocketTier3Entity> extends EntityModel<T> {
	private final ModelRenderer rocket;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;
	private final ModelRenderer cube_r8;
	private final ModelRenderer cube_r9;
	private final ModelRenderer cube_r10;
	private final ModelRenderer cube_r11;
	private final ModelRenderer cube_r12;
	private final ModelRenderer cube_r13;
	private final ModelRenderer cube_r14;
	private final ModelRenderer cube_r15;
	private final ModelRenderer cube_r16;
	private final ModelRenderer cube_r17;
	private final ModelRenderer cube_r18;
	private final ModelRenderer cube_r19;
	private final ModelRenderer cube_r20;
	private final ModelRenderer cube_r21;
	private final ModelRenderer cube_r22;
	private final ModelRenderer cube_r23;

	public RocketTier3Model() {
		textureWidth = 256;
		textureHeight = 256;

		rocket = new ModelRenderer(this);
		rocket.setRotationPoint(0.0F, 25.0F, 0.0F);
		rocket.setTextureOffset(184, 0).addBox(-9.0F, -58.0F, -9.0F, 18.0F, 50.0F, 18.0F, 0.0F, false);
		rocket.setTextureOffset(184, 68).addBox(-9.0F, -10.0F, -9.0F, 18.0F, 0.0F, 18.0F, 0.0F, false);
		rocket.setTextureOffset(36, 25).addBox(-2.0F, -84.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
		rocket.setTextureOffset(36, 25).addBox(-2.0F, -90.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
		rocket.setTextureOffset(36, 25).addBox(-2.0F, -87.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
		rocket.setTextureOffset(36, 0).addBox(-2.0F, -101.0F, -2.0F, 4.0F, 4.0F, 4.0F, -0.375F, false);
		rocket.setTextureOffset(36, 8).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 15.0F, 2.0F, 0.0F, false);
		rocket.setTextureOffset(36, 31).addBox(-3.0F, -82.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);
		rocket.setTextureOffset(22, 128).addBox(-6.0F, -5.0F, -6.0F, 12.0F, 2.0F, 12.0F, 0.0F, false);
		rocket.setTextureOffset(0, 85).addBox(-10.0F, -13.0F, -10.0F, 20.0F, 2.0F, 20.0F, 0.0F, false);
		rocket.setTextureOffset(0, 85).addBox(-10.0F, -16.0F, -10.0F, 20.0F, 2.0F, 20.0F, 0.0F, false);
		rocket.setTextureOffset(0, 85).addBox(-10.0F, -59.0F, -10.0F, 20.0F, 2.0F, 20.0F, 0.0F, false);
		rocket.setTextureOffset(0, 85).addBox(-10.0F, -55.0F, -10.0F, 20.0F, 2.0F, 20.0F, 0.0F, false);
		rocket.setTextureOffset(0, 85).addBox(-10.0F, -44.0F, -10.0F, 20.0F, 2.0F, 20.0F, 0.0F, false);
		rocket.setTextureOffset(0, 85).addBox(-10.0F, -32.0F, -10.0F, 20.0F, 2.0F, 20.0F, 0.0F, false);
		rocket.setTextureOffset(22, 116).addBox(-5.0F, -7.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);
		rocket.setTextureOffset(22, 107).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
		rocket.setTextureOffset(230, 88).addBox(-6.0F, -43.0F, -9.5F, 12.0F, 12.0F, 1.0F, 0.0F, false);
		rocket.setTextureOffset(238, 101).addBox(-4.0F, -41.0F, -9.5F, 8.0F, 8.0F, 1.0F, 0.0F, false);
		rocket.setTextureOffset(0, 169).addBox(-12.0F, -21.0F, -1.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
		rocket.setTextureOffset(0, 152).addBox(-13.0F, -18.0F, -3.0F, 4.0F, 11.0F, 6.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		rocket.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 1.5708F, 0.0F);
		cube_r1.setTextureOffset(0, 169).addBox(-12.0F, -21.0F, -1.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
		cube_r1.setTextureOffset(0, 152).addBox(-13.0F, -18.0F, -3.0F, 4.0F, 11.0F, 6.0F, 0.0F, false);
		cube_r1.setTextureOffset(224, 88).addBox(4.0F, -43.0F, -9.5F, 2.0F, 12.0F, 1.0F, 0.0F, false);
		cube_r1.setTextureOffset(224, 88).addBox(-6.0F, -43.0F, -9.5F, 2.0F, 12.0F, 1.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, 0.0F, 0.0F);
		rocket.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 3.1416F, 0.0F);
		cube_r2.setTextureOffset(0, 152).addBox(-13.0F, -18.0F, -3.0F, 4.0F, 11.0F, 6.0F, 0.0F, false);
		cube_r2.setTextureOffset(0, 169).addBox(-12.0F, -21.0F, -1.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
		cube_r2.setTextureOffset(224, 88).addBox(4.0F, -43.0F, -9.5F, 2.0F, 12.0F, 1.0F, 0.0F, false);
		cube_r2.setTextureOffset(224, 88).addBox(-6.0F, -43.0F, -9.5F, 2.0F, 12.0F, 1.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
		rocket.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, -1.5708F, 0.0F);
		cube_r3.setTextureOffset(0, 169).addBox(-12.0F, -21.0F, -1.0F, 3.0F, 3.0F, 2.0F, 0.0F, false);
		cube_r3.setTextureOffset(0, 152).addBox(-13.0F, -18.0F, -3.0F, 4.0F, 11.0F, 6.0F, 0.0F, false);
		cube_r3.setTextureOffset(224, 88).addBox(-6.0F, -43.0F, -9.5F, 2.0F, 12.0F, 1.0F, 0.0F, false);
		cube_r3.setTextureOffset(224, 88).addBox(4.0F, -43.0F, -9.5F, 2.0F, 12.0F, 1.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, -78.0F, 0.0F);
		rocket.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.48F, 0.7854F, 0.0F);
		cube_r4.setTextureOffset(0, 24).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.0F, -78.0F, 0.0F);
		rocket.addChild(cube_r5);
		setRotationAngle(cube_r5, -0.48F, -2.3562F, 0.0F);
		cube_r5.setTextureOffset(0, 24).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.0F, -78.0F, 0.0F);
		rocket.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.48F, 2.3562F, 0.0F);
		cube_r6.setTextureOffset(0, 24).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, 0.0F, false);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(0.0F, -78.0F, 0.0F);
		rocket.addChild(cube_r7);
		setRotationAngle(cube_r7, -0.48F, -0.7854F, 0.0F);
		cube_r7.setTextureOffset(0, 24).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 27.0F, 2.0F, 0.0F, false);

		cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(0.0F, -58.0F, 0.0F);
		rocket.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.3491F, 1.5708F, 0.0F);
		cube_r8.setTextureOffset(0, 0).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, 0.0F, false);

		cube_r9 = new ModelRenderer(this);
		cube_r9.setRotationPoint(0.0F, -58.0F, 0.0F);
		rocket.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.3491F, 3.1416F, 0.0F);
		cube_r9.setTextureOffset(0, 0).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, 0.0F, false);

		cube_r10 = new ModelRenderer(this);
		cube_r10.setRotationPoint(0.0F, -58.0F, 0.0F);
		rocket.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.3491F, -1.5708F, 0.0F);
		cube_r10.setTextureOffset(0, 0).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, 0.0F, false);

		cube_r11 = new ModelRenderer(this);
		cube_r11.setRotationPoint(0.0F, -58.0F, 0.0F);
		rocket.addChild(cube_r11);
		setRotationAngle(cube_r11, 0.3491F, 0.0F, 0.0F);
		cube_r11.setTextureOffset(0, 0).addBox(-8.0F, -20.8F, 8.5175F, 16.0F, 24.0F, 0.0F, 0.0F, false);

		cube_r12 = new ModelRenderer(this);
		cube_r12.setRotationPoint(-1.0F, 0.0F, 1.0F);
		rocket.addChild(cube_r12);
		setRotationAngle(cube_r12, 0.0F, -0.7854F, 0.0F);
		cube_r12.setTextureOffset(0, 131).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, 0.0F, false);

		cube_r13 = new ModelRenderer(this);
		cube_r13.setRotationPoint(-1.0F, 0.0F, 1.0F);
		rocket.addChild(cube_r13);
		setRotationAngle(cube_r13, 1.1345F, -0.7854F, 0.0F);
		cube_r13.setTextureOffset(0, 107).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, 0.0F, false);

		cube_r14 = new ModelRenderer(this);
		cube_r14.setRotationPoint(-1.0F, 0.0F, -1.0F);
		rocket.addChild(cube_r14);
		setRotationAngle(cube_r14, 0.0F, -2.3562F, 0.0F);
		cube_r14.setTextureOffset(0, 131).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, 0.0F, false);

		cube_r15 = new ModelRenderer(this);
		cube_r15.setRotationPoint(-1.0F, 0.0F, -1.0F);
		rocket.addChild(cube_r15);
		setRotationAngle(cube_r15, 1.1345F, -2.3562F, 0.0F);
		cube_r15.setTextureOffset(0, 107).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, 0.0F, false);

		cube_r16 = new ModelRenderer(this);
		cube_r16.setRotationPoint(1.0F, 0.0F, 1.0F);
		rocket.addChild(cube_r16);
		setRotationAngle(cube_r16, 0.0F, 0.7854F, 0.0F);
		cube_r16.setTextureOffset(0, 131).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, 0.0F, false);

		cube_r17 = new ModelRenderer(this);
		cube_r17.setRotationPoint(1.0F, 0.0F, 1.0F);
		rocket.addChild(cube_r17);
		setRotationAngle(cube_r17, 1.1345F, 0.7854F, 0.0F);
		cube_r17.setTextureOffset(0, 107).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, 0.0F, false);

		cube_r18 = new ModelRenderer(this);
		cube_r18.setRotationPoint(0.0F, 0.0F, 0.0F);
		rocket.addChild(cube_r18);
		setRotationAngle(cube_r18, 0.0F, 2.3562F, 0.0F);
		cube_r18.setTextureOffset(20, 142).addBox(-3.0F, -18.0F, 20.4142F, 6.0F, 5.0F, 6.0F, -0.3F, false);
		cube_r18.setTextureOffset(36, 8).addBox(-1.0F, -13.0F, 18.4142F, 2.0F, 13.0F, 2.0F, -0.2F, false);
		cube_r18.setTextureOffset(36, 8).addBox(-1.0F, -13.0F, 15.4142F, 2.0F, 13.0F, 2.0F, -0.2F, false);

		cube_r19 = new ModelRenderer(this);
		cube_r19.setRotationPoint(0.0F, 0.0F, 0.0F);
		rocket.addChild(cube_r19);
		setRotationAngle(cube_r19, 0.0F, 0.7854F, 0.0F);
		cube_r19.setTextureOffset(20, 142).addBox(-3.0F, -18.0F, 20.4142F, 6.0F, 5.0F, 6.0F, -0.3F, false);
		cube_r19.setTextureOffset(36, 8).addBox(-1.0F, -13.0F, 15.4142F, 2.0F, 13.0F, 2.0F, -0.2F, false);
		cube_r19.setTextureOffset(36, 8).addBox(-1.0F, -13.0F, 18.4142F, 2.0F, 13.0F, 2.0F, -0.2F, false);

		cube_r20 = new ModelRenderer(this);
		cube_r20.setRotationPoint(0.0F, 0.0F, 0.0F);
		rocket.addChild(cube_r20);
		setRotationAngle(cube_r20, 0.0F, -2.3562F, 0.0F);
		cube_r20.setTextureOffset(20, 142).addBox(-3.0F, -18.0F, 20.4142F, 6.0F, 5.0F, 6.0F, -0.3F, false);
		cube_r20.setTextureOffset(36, 8).addBox(-1.0F, -13.0F, 15.4142F, 2.0F, 13.0F, 2.0F, -0.2F, false);
		cube_r20.setTextureOffset(36, 8).addBox(-1.0F, -13.0F, 18.4142F, 2.0F, 13.0F, 2.0F, -0.2F, false);

		cube_r21 = new ModelRenderer(this);
		cube_r21.setRotationPoint(0.0F, 0.0F, 0.0F);
		rocket.addChild(cube_r21);
		setRotationAngle(cube_r21, 0.0F, -0.7854F, 0.0F);
		cube_r21.setTextureOffset(20, 142).addBox(-3.0F, -18.0F, 20.4142F, 6.0F, 5.0F, 6.0F, -0.3F, false);
		cube_r21.setTextureOffset(36, 8).addBox(-1.0F, -13.0F, 18.4142F, 2.0F, 13.0F, 2.0F, -0.2F, false);
		cube_r21.setTextureOffset(36, 8).addBox(-1.0F, -13.0F, 15.4142F, 2.0F, 13.0F, 2.0F, -0.2F, false);

		cube_r22 = new ModelRenderer(this);
		cube_r22.setRotationPoint(1.0F, 0.0F, -1.0F);
		rocket.addChild(cube_r22);
		setRotationAngle(cube_r22, 0.0F, 2.3562F, 0.0F);
		cube_r22.setTextureOffset(0, 131).addBox(-2.0F, -17.0F, 20.0F, 4.0F, 17.0F, 4.0F, 0.0F, false);

		cube_r23 = new ModelRenderer(this);
		cube_r23.setRotationPoint(1.0F, 0.0F, -1.0F);
		rocket.addChild(cube_r23);
		setRotationAngle(cube_r23, 1.1345F, 2.3562F, 0.0F);
		cube_r23.setTextureOffset(0, 107).addBox(-1.0F, 1.0F, 13.0F, 2.0F, 15.0F, 9.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(T entityIn, float f, float f1, float f2, float f3, float f4) {
		this.rocket.rotateAngleY = f3 / (180F / (float) Math.PI);

		this.rocket.rotateAngleZ = (float) entityIn.ay;

		this.rocket.rotateAngleX = (float) entityIn.ap;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		rocket.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
