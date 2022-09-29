package com.github.alexnijjar.ad_astra.client.renderer.armour;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

@Environment(EnvType.CLIENT)
public class NetheriteSpaceSuitModel {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("netherite_space_suit"), "main");

	@SuppressWarnings("unused")
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData visor = modelPartData.addChild("visor", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(1.0f)), ModelTransform.pivot(0.0f, -1.0f, 0.0f));

		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(0.6f)), ModelTransform.pivot(0.0f, -1.0f, 0.0f));
		ModelPartData hat = modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(0, 0), ModelTransform.NONE);

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(32, 16).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new Dilation(0.6f)), ModelTransform.pivot(0.0f, -0.5f, 0.0f));

		ModelPartData backpack = body.addChild("backpack", ModelPartBuilder.create().uv(0, 46).cuboid(-5.0f, -7.0f, -2.0f, 10.0f, 14.0f, 4.0f, new Dilation(0.5f)), ModelTransform.pivot(0.0f, 5.0f, 5.0f));

		ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.pivot(-5.0f, 1.0f, 0.0f));

		ModelPartData cube_r1 = right_arm.addChild("cube_r1", ModelPartBuilder.create().uv(28, 45).cuboid(-2.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.9f)).uv(0, 16).cuboid(-2.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.6f)), ModelTransform.of(-1.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f));

		ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.pivot(5.0f, 1.0f, 0.0f));

		ModelPartData cube_r2 = left_arm.addChild("cube_r2", ModelPartBuilder.create().uv(44, 45).cuboid(-1.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(1.0f)).uv(16, 16).cuboid(-1.5f, -6.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.6f)), ModelTransform.of(1.0f, 4.0f, 0.0f, 0.0f, 0.0f, 0.0f));

		ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 32).cuboid(-2.0f, 0.6667f, -2.0f, 4.0f, 9.0f, 4.0f, new Dilation(0.7f)), ModelTransform.pivot(-2.0f, 10.3333f, 0.0f));

		ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(16, 32).cuboid(-2.0f, 0.6667f, -2.0f, 4.0f, 9.0f, 4.0f, new Dilation(0.7f)), ModelTransform.pivot(2.0f, 10.3333f, 0.0f));

		ModelPartData right_boot = modelPartData.addChild("right_boot", ModelPartBuilder.create().uv(32, 32).cuboid(-2.3f, 3.6667f, -2.0f, 4.0f, 9.0f, 4.0f, new Dilation(1.0f)), ModelTransform.pivot(-2.0f, 10.3333f, 0.0f));

		ModelPartData left_boot = modelPartData.addChild("left_boot", ModelPartBuilder.create().uv(48, 32).cuboid(-1.7f, 3.6667f, -2.0f, 4.0f, 9.0f, 4.0f, new Dilation(1.0f)), ModelTransform.pivot(2.0f, 10.3333f, 0.0f));
		return TexturedModelData.of(modelData, 64, 64);
	}
}