package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models;

import com.github.alexnijjar.ad_astra.entities.mobs.PygroBruteEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

@Environment(EnvType.CLIENT)
public class PygroBruteEntityModel extends BipedEntityModel<PygroBruteEntity> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("pygro_brute"), "main");

    public PygroBruteEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    @SuppressWarnings("unused")
    public static TexturedModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData hat = modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(0, 0), ModelTransform.NONE);
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(32, 44).cuboid(-3.5F, -4.0F, -6.0F, 6.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, 0.0F, -1.0F));

        ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-6.4448F, -2.1679F, 0.0F, 0.0F, 0.0F, 0.4363F));

        ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(6.2052F, -2.5179F, 2.0F, 0.2182F, 0.0F, -0.4363F));

        ModelPartData cube_r3 = head.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(5.9787F, -2.4122F, -1.0F, -0.1745F, 0.0F, -0.4363F));

        ModelPartData cube_r4 = head.addChild("cube_r4", ModelPartBuilder.create().uv(32, 32).cuboid(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-5.2424F, -4.361F, 0.0F, 0.0F, 0.0F, 0.4363F));

        ModelPartData cube_r5 = head.addChild("cube_r5", ModelPartBuilder.create().uv(18, 17).cuboid(-2.0F, 0.0F, -3.5F, 4.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -0.7679F, -4.999F, 1.5708F, 0.0F, 0.0F));

        ModelPartData cube_r6 = head.addChild("cube_r6", ModelPartBuilder.create().uv(22, 0).cuboid(-2.0F, 0.0F, -0.5F, 4.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 0.2995F, -4.7491F, 1.0472F, 0.0F, 0.0F));

        ModelPartData cube_r7 = head.addChild("cube_r7", ModelPartBuilder.create().uv(42, 35).cuboid(-3.0F, -0.5F, -1.0F, 6.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 0.5F, -4.0F, 1.0472F, 0.0F, 0.0F));

        ModelPartData cube_r8 = head.addChild("cube_r8", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, 0.25F, -0.5F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -9.25F, -1.5F, 2.4435F, -0.7854F, -3.1416F));

        ModelPartData cube_r9 = head.addChild("cube_r9", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5F, 0.25F, -0.5F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -9.25F, 0.5F, -0.6981F, -1.0036F, 0.0F));

        ModelPartData cube_r10 = head.addChild("cube_r10", ModelPartBuilder.create().uv(24, 2).cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.25F, 1.5F, -0.6981F, 0.0F, 0.0F));

        ModelPartData cube_r11 = head.addChild("cube_r11", ModelPartBuilder.create().uv(24, 2).cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.25F, -9.25F, -1.5F, 2.4435F, 1.0036F, 3.1416F));

        ModelPartData cube_r12 = head.addChild("cube_r12", ModelPartBuilder.create().uv(40, 16).cuboid(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(4.2424F, -4.361F, 0.0F, 0.0F, 0.0F, -0.4363F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(42, 28).cuboid(-2.5F, 4.0F, -3.5F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F))
                .uv(16, 32).cuboid(-2.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 12.0F, 0.0F));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(32, 0).cuboid(-2.0F, 0.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(42, 28).cuboid(-2.5F, 4.0F, -3.5F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 12.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(24, 16).cuboid(-4.0F, -2.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(0, 32).cuboid(-2.0F, -2.0F, -3.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-4.5F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 48).cuboid(-2.5F, 7.0F, -3.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, 0.0F, -1.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}