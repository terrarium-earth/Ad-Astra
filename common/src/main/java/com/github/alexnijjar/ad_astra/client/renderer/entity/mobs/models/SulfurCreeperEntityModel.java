package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SulfurCreeperEntityModel<T extends Entity> extends EntityModel<T> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("sulfur_creeper"), "main");

	private final ModelPart body;

	public SulfurCreeperEntityModel(ModelPart root) {
		this.body = root.getChild("body");
	}

	@SuppressWarnings("unused")
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(50, 9).cuboid(0.0F, -17.0F, 0.0F, 0.0F, 9.0F, 7.0F, new Dilation(0.0F)).uv(16, 16).cuboid(-4.0F, -18.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)),
				ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData sulfurcrystalbodyR_r1 = body.addChild("sulfurcrystalbodyR_r1",
				ModelPartBuilder.create().uv(50, 0).cuboid(3.4317F, -7.4874F, -1.9048F, 10.0F, 7.0F, 0.0F, new Dilation(0.0F)).uv(50, 0).cuboid(14.4317F, -8.4874F, 0.0952F, 6.0F, 2.0F, 0.0F, new Dilation(0.0F)),
				ModelTransform.of(11.0F, -17.0F, 2.0F, 0.0983F, 0.0831F, 2.9853F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -18.0F, 0.0F));

		ModelPartData sulfurcrystalhead_r1 = head.addChild("sulfurcrystalhead_r1", ModelPartBuilder.create().uv(50, 0).cuboid(-5.5F, -4.0F, -4.5F, 10.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -7.0F, 1.0F, 0.1259F, -0.3419F, 0.4257F));

		ModelPartData BackLeftLeg = body.addChild("BackLeftLeg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -6.0F, 4.0F));

		ModelPartData BackRightLeg = body.addChild("BackRightLeg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -6.0F, 4.0F));

		ModelPartData FrontLeftLeg = body.addChild("FrontLeftLeg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -6.0F, -4.0F));

		ModelPartData FrontRightLeg = body.addChild("FrontRightLeg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -6.0F, -4.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.body.getChild("head").yaw = (float)(headYaw * Math.PI / 180.0);
		this.body.getChild("head").pitch = (float)(headPitch * Math.PI / 180.0);
		this.body.getChild("BackLeftLeg").pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
		this.body.getChild("BackRightLeg").pitch = MathHelper.cos(limbAngle * 0.6662f + (float) Math.PI) * 1.4f * limbDistance;
		this.body.getChild("FrontLeftLeg").pitch = MathHelper.cos(limbAngle * 0.6662f + (float) Math.PI) * 1.4f * limbDistance;
		this.body.getChild("FrontRightLeg").pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}