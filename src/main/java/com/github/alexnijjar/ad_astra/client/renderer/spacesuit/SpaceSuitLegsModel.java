package com.github.alexnijjar.beyond_earth.client.renderer.spacesuit;

import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class SpaceSuitLegsModel extends AbstractSpaceSuitModel {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("space_suit_p2"), "main");

	public SpaceSuitLegsModel(ModelPart root, BipedEntityModel<LivingEntity> contextModel, Identifier headTexture) {
		super(root, contextModel, headTexture);
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.6f)), ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
		modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.6f)).mirrored(false), ModelTransform.pivot(1.9f, 12.0f, 0.0f));

		return TexturedModelData.of(modelData, 64, 32);
	}
}
