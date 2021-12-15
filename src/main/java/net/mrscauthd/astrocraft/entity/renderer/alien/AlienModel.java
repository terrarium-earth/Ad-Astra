package net.mrscauthd.astrocraft.entity.renderer.alien;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.mrscauthd.astrocraft.entity.alien.AlienEntity;

public class AlienModel<T extends AlienEntity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "alien"), "main");

	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart leg0;
	private final ModelPart leg1;
	private final ModelPart arms;
	private final ModelPart head2;
	public AlienModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.leg0 = root.getChild("leg0");
		this.leg1 = root.getChild("leg1");
		this.arms = root.getChild("arms");
		this.head2 = root.getChild("head2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(32, 0).mirror().addBox(-4.5F, -19.0F, -4.5F, 9.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(16, 64).mirror().addBox(-8.0F, -15.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(80, 2).mirror().addBox(-6.0F, -16.0F, -6.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 0).mirror().addBox(-1.0F, -3.0F, -6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).mirror().addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 38).mirror().addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leg0 = partdefinition.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition arms = partdefinition.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(40, 38).mirror().addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(44, 22).mirror().addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition head2 = partdefinition.addOrReplaceChild("head2", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T e, float f, float f1, float f2, float f3, float f4) {
		this.head.yRot = f3 / (180F / (float) Math.PI);
		this.head.xRot = f4 / (180F / (float) Math.PI);
		this.leg0.xRot = Mth.cos(f * 1.0F) * -1.0F * f1;
		this.leg1.xRot = Mth.cos(f * 1.0F) * 1.0F * f1;
	}

	@Override
	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (young) {
			matrixStack.scale(0.5f, 0.5f, 0.5f);
			matrixStack.translate(0, 1.5f, 0);
		}
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		leg0.render(matrixStack, buffer, packedLight, packedOverlay);
		leg1.render(matrixStack, buffer, packedLight, packedOverlay);
		arms.render(matrixStack, buffer, packedLight, packedOverlay);
		head2.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}
