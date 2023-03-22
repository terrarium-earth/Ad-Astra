package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models;

import com.github.alexnijjar.ad_astra.entities.mobs.PygroEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

@Environment(EnvType.CLIENT)
public class PygroEntityModel extends BipedEntityModel<PygroEntity> {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("pygro"), "main");

	public PygroEntityModel(ModelPart modelPart) {
		super(modelPart);
	}

	@SuppressWarnings("unused")
	public static TexturedModelData getModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData hat = modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(0, 0), ModelTransform.NONE);
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(20, 51).cuboid(-3.5F, -4.0F, -6.0F, 6.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, 0.0F, -1.0F));

        ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(32, 32).cuboid(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-5.2424F, -4.361F, 0.0F, 0.0F, 0.0F, 0.4363F));

        ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(33, 46).cuboid(-2.0F, 0.0F, -1.5F, 4.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 0.2995F, -4.7491F, 1.0472F, 0.0F, 0.0F));

        ModelPartData cube_r3 = head.addChild("cube_r3", ModelPartBuilder.create().uv(21, 57).cuboid(-3.0F, -0.5F, -1.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 0.5F, -4.0F, 1.0472F, 0.0F, 0.0F));

        ModelPartData cube_r4 = head.addChild("cube_r4", ModelPartBuilder.create().uv(40, 16).cuboid(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(4.2424F, -4.361F, 0.0F, 0.0F, 0.0F, -0.4363F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 48).cuboid(-2.5F, 2.0F, -3.5F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F))
                .uv(16, 32).cuboid(-2.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 12.0F, 0.0F));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(32, 0).cuboid(-2.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 48).cuboid(-2.5F, 2.0F, -3.5F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 12.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(24, 16).cuboid(-4.0F, -2.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(0, 32).cuboid(-2.0F, -2.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 0.0F, -1.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}
}