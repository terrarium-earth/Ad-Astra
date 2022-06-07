package com.github.alexnijjar.beyond_earth.client.renderer.spacesuit;

import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class JetSuitModel extends SpaceSuitModel {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("spacesuit"), "main");

	public <T extends LivingEntity> JetSuitModel(ModelPart root, BipedEntityModel<T> contextModel, Identifier headTexture) {
		super(root, contextModel, headTexture);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(0.5f)).uv(0, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(0.75f)).uv(14, 59).cuboid(3.0f, -13.0f, 1.0f,
				1.0f, 5.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, -0.0175f, 0.0873f, 0.0f));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 32).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new Dilation(0.25f)).uv(28, 28).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new Dilation(0.25f)).uv(50, 29)
				.cuboid(-3.0f, 5.0f, -2.5f, 6.0f, 4.0f, 1.0f, new Dilation(0.25f)).uv(0, 55).cuboid(-2.5f, 1.0f, 2.75f, 5.0f, 8.0f, 1.0f, new Dilation(0.75f)), ModelTransform.pivot(0.0f, 0.0f, 0.0f));

		body.addChild("body_r1", ModelPartBuilder.create().uv(32, 31).cuboid(-2.0f, -5.0f, 0.75f, 0.0f, 11.0f, 6.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 6.0f, 2.0f, 0.0f, -0.3491f, 0.0f));

		body.addChild("body_r2", ModelPartBuilder.create().uv(32, 31).cuboid(2.0f, -5.0f, 0.75f, 0.0f, 11.0f, 6.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 6.0f, 2.0f, 0.0f, 0.3491f, 0.0f));

		modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(20, 44).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.26f)).uv(48, 8).cuboid(-3.0f, 6.0f, -2.0f, 4.0f, 4.0f, 4.0f, new Dilation(0.5f)),
				ModelTransform.pivot(-5.0f, 2.0f, 0.0f));

		modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(32, 0).cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.26f)).uv(48, 0).cuboid(-1.0f, 6.0f, -2.0f, 4.0f, 4.0f, 4.0f, new Dilation(0.5f)),
				ModelTransform.pivot(5.0f, 2.0f, 0.0f));

		modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(48, 44).cuboid(-2.0f, 5.7f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.5f)).uv(48, 54).cuboid(-2.0f, 5.7f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.27f)),
				ModelTransform.pivot(-2.0f, 12.0f, 0.0f));

		modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(48, 44).cuboid(-2.0f, 5.7f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.5f)).uv(48, 54).cuboid(-2.0f, 5.7f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.27f)),
				ModelTransform.pivot(2.0f, 12.0f, 0.0f));

		return TexturedModelData.of(modelData, 64, 64);
	}
}