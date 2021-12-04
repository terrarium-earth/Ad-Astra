package net.mrscauthd.boss_tools.entity.renderer.rover;

import net.mrscauthd.boss_tools.entity.RoverEntity;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class RoverModel<T extends RoverEntity> extends EntityModel<T> {
	private final ModelRenderer Frame;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer Wheel1;
	private final ModelRenderer Wheel2;
	private final ModelRenderer Wheel3;
	private final ModelRenderer Wheel4;
	private final ModelRenderer sat;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	public RoverModel() {
		textureWidth = 256;
		textureHeight = 256;

		Frame = new ModelRenderer(this);
		Frame.setRotationPoint(0.0F, 24.0F, 0.0F);
		Frame.setTextureOffset(0, 0).addBox(-17.5F, -10.5F, -52.5F, 35.0F, 3.0F, 73.0F, 0.0F, false);
		Frame.setTextureOffset(0, 31).addBox(12.5F, -15.0F, -37.5F, 0.0F, 5.0F, 25.0F, 0.0F, false);
		Frame.setTextureOffset(0, 26).addBox(-12.5F, -15.0F, -37.5F, 0.0F, 5.0F, 25.0F, 0.0F, false);
		Frame.setTextureOffset(56, 108).addBox(-17.5F, -8.0F, 12.5F, 35.0F, 3.0F, 3.0F, 0.0F, false);
		Frame.setTextureOffset(56, 102).addBox(-17.5F, -8.0F, -47.5F, 35.0F, 3.0F, 3.0F, 0.0F, false);
		Frame.setTextureOffset(0, 130).addBox(-12.5F, -13.0F, -55.0F, 10.0F, 3.0F, 13.0F, 0.0F, false);
		Frame.setTextureOffset(32, 64).addBox(-7.5F, -13.0F, -57.5F, 15.0F, 3.0F, 3.0F, 0.0F, false);
		Frame.setTextureOffset(0, 13).addBox(-3.0F, -13.0F, -55.0F, 8.0F, 3.0F, 3.0F, 0.0F, false);
		Frame.setTextureOffset(12, 66).addBox(-10.5F, -15.5F, -52.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		Frame.setTextureOffset(0, 66).addBox(-10.5F, -23.0F, -52.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		Frame.setTextureOffset(46, 137).addBox(7.0F, -50.5F, -55.0F, 3.0F, 43.0F, 3.0F, 0.0F, false);
		Frame.setTextureOffset(138, 127).addBox(-13.0F, -20.0F, -55.0F, 8.0F, 5.0F, 8.0F, 0.0F, false);
		Frame.setTextureOffset(136, 76).addBox(-13.0F, -27.5F, -55.0F, 8.0F, 5.0F, 8.0F, 0.0F, false);
		Frame.setTextureOffset(0, 102).addBox(-12.5F, -35.0F, 17.5F, 25.0F, 25.0F, 3.0F, 0.0F, false);
		Frame.setTextureOffset(0, 76).addBox(-15.0F, -13.0F, -12.5F, 30.0F, 3.0F, 23.0F, 0.0F, false);
		Frame.setTextureOffset(83, 76).addBox(-10.0F, -20.0F, -42.5F, 20.0F, 10.0F, 13.0F, 0.0F, false);
		Frame.setTextureOffset(0, 76).addBox(17.5F, -15.625F, -49.875F, 5.0F, 0.0F, 13.0F, 0.0F, false);
		Frame.setTextureOffset(0, 0).addBox(-22.5F, -15.625F, -49.775F, 5.0F, 0.0F, 13.0F, 0.0F, false);
		Frame.setTextureOffset(47, 51).addBox(17.5F, -15.625F, 10.125F, 5.0F, 0.0F, 13.0F, 0.0F, false);
		Frame.setTextureOffset(37, 51).addBox(-22.5F, -15.625F, 10.625F, 5.0F, 0.0F, 13.0F, 0.0F, false);
		Frame.setTextureOffset(56, 114).addBox(17.5F, -15.625F, -36.875F, 5.0F, 13.0F, 0.0F, 0.0F, false);
		Frame.setTextureOffset(0, 0).addBox(-22.5F, -15.625F, -36.875F, 5.0F, 13.0F, 0.0F, 0.0F, false);
		Frame.setTextureOffset(83, 76).addBox(17.5F, -15.625F, 23.125F, 5.0F, 13.0F, 0.0F, 0.0F, false);
		Frame.setTextureOffset(0, 76).addBox(-22.5F, -15.625F, 23.125F, 5.0F, 13.0F, 0.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, -12.1737F, 12.4786F);
		Frame.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.1309F, 0.0F, 0.0F);
		cube_r1.setTextureOffset(0, 23).addBox(-15.0F, -22.5F, -2.5F, 30.0F, 25.0F, 3.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, -10.0F, -52.5F);
		Frame.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.2182F, 0.0F, 0.0F);
		cube_r2.setTextureOffset(73, 140).addBox(-2.5F, -15.0F, 0.05F, 15.0F, 15.0F, 0.0F, 0.0F, false);
		cube_r2.setTextureOffset(58, 140).addBox(-2.5F, -15.0F, 0.0F, 15.0F, 15.0F, 0.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.0F, -12.4979F, -57.4987F);
		Frame.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.5672F, 0.0F, 0.0F);
		cube_r3.setTextureOffset(0, 61).addBox(-7.5F, 0.0F, -5.0F, 15.0F, 0.0F, 5.0F, 0.0F, false);

		Wheel1 = new ModelRenderer(this);
		Wheel1.setRotationPoint(17.5F, -6.25F, -46.25F);
		Frame.addChild(Wheel1);
		Wheel1.setTextureOffset(115, 127).addBox(0.0F, -6.75F, -6.25F, 5.0F, 13.0F, 13.0F, 0.0F, false);

		Wheel2 = new ModelRenderer(this);
		Wheel2.setRotationPoint(20.0F, -6.25F, 13.75F);
		Frame.addChild(Wheel2);
		Wheel2.setTextureOffset(119, 101).addBox(-2.5F, -6.75F, -6.25F, 5.0F, 13.0F, 13.0F, 0.0F, false);

		Wheel3 = new ModelRenderer(this);
		Wheel3.setRotationPoint(-20.0F, -6.25F, -46.25F);
		Frame.addChild(Wheel3);
		Wheel3.setTextureOffset(56, 114).addBox(-2.5F, -6.75F, -6.25F, 5.0F, 13.0F, 13.0F, 0.0F, false);

		Wheel4 = new ModelRenderer(this);
		Wheel4.setRotationPoint(-20.0F, -6.25F, 13.75F);
		Frame.addChild(Wheel4);
		Wheel4.setTextureOffset(92, 114).addBox(-2.5F, -6.75F, -6.25F, 5.0F, 13.0F, 13.0F, 0.0F, false);

		sat = new ModelRenderer(this);
		sat.setRotationPoint(8.645F, -49.3111F, -53.75F);
		Frame.addChild(sat);


		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.105F, -0.6889F, 0.0F);
		sat.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.2618F, 0.0F, 0.0F);
		cube_r4.setTextureOffset(0, 0).addBox(-11.75F, 0.375F, -11.25F, 23.0F, 0.0F, 23.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(-0.105F, -4.3111F, -0.9476F);
		sat.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.2618F, 0.0F, 0.0F);
		cube_r5.setTextureOffset(79, 114).addBox(-1.5399F, -3.8778F, -1.3274F, 3.0F, 8.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Frame.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(RoverEntity e, float f, float f1, float f2, float f3, float f4) {
		this.Frame.rotateAngleY = f3 / (180F / (float) Math.PI);

		this.Wheel1.rotateAngleX = f2 / (180F / (float) Math.PI);
		this.Wheel2.rotateAngleX = f2 / (180F / (float) Math.PI);
		this.Wheel3.rotateAngleX = f2 / (180F / (float) Math.PI);
		this.Wheel4.rotateAngleX = f2 / (180F / (float) Math.PI);
		if (e.getforward()) {
			this.Wheel1.rotateAngleX = (float) (f / 4);
			this.Wheel2.rotateAngleX = (float) (f / 4);
			this.Wheel3.rotateAngleX = (float) (f / 4);
			this.Wheel4.rotateAngleX = (float) (f / 4);
		}
		if (!e.getforward()) {
			this.Wheel1.rotateAngleX = (float) (f / 4);
			this.Wheel2.rotateAngleX = (float) (f / 4);
			this.Wheel3.rotateAngleX = (float) (f / 4);
			this.Wheel4.rotateAngleX = (float) (f / 4);
		}

		this.sat.rotateAngleY = f2 / 20f;
	}
}