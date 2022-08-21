package com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs.models;

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
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.PiglinEntityModel;
import net.minecraft.entity.mob.AbstractPiglinEntity;

@Environment(EnvType.CLIENT)
public class PygroEntityModel<T extends AbstractPiglinEntity> extends PiglinEntityModel<T> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("pygro"), "main");

    public PygroEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static TexturedModelData getModelData() {
        ModelData modelData = PygroEntityModel.getModelData(Dilation.NONE);
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData modelPartData1 = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0f, -8.0f, -4.0f, 10.0f, 8.0f, 8.0f, Dilation.NONE).uv(31, 1).cuboid(-2.0f, -4.0f, -5.0f, 4.0f, 4.0f, 1.0f, Dilation.NONE).uv(2, 4)
                .cuboid(2.0f, -2.0f, -5.0f, 1.0f, 2.0f, 1.0f, Dilation.NONE).uv(2, 0).cuboid(-3.0f, -2.0f, -5.0f, 1.0f, 2.0f, 1.0f, Dilation.NONE), ModelTransform.NONE);

        // Left Ear.
        modelPartData1.addChild("left_ear", ModelPartBuilder.create().uv(39, 6).cuboid(0.0f, 0.0f, -2.0f, 1.0f, 5.0f, 4.0f, Dilation.NONE), ModelTransform.of(4.5f, -6.0f, 0.0f, 0.0f, 0.0f, (-(float) Math.PI / 6f)));

        // eyes.
        ModelPartData eyes = modelPartData1.addChild("eyesg", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 0.0f, 0.0f));
        eyes.addChild("eyes", ModelPartBuilder.create().uv(46, 0).cuboid(-4.5f, -4.5f, -0.75f, 9.0f, 7.0f, 0.0f, Dilation.NONE), ModelTransform.of(0.0f, -7.5f, -4.0f, 0.3054f, 0.0f, 0.0f));

        // nose 1.
        ModelPartData fang = modelPartData1.addChild("noseg1", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 24.0f, 0.0f));
        fang.addChild("nose1", ModelPartBuilder.create().uv(33, 2).cuboid(-1.25f, -1.35f, -0.5f, 3.0f, 3.0f, 1.0f, Dilation.NONE), ModelTransform.of(-2.0f, -25.0f, -4.5f, 0.0631f, 0.3435f, 0.1855f));

        // nose 2.
        ModelPartData fang2 = modelPartData1.addChild("noseg2", ModelPartBuilder.create(), ModelTransform.pivot(4.5f, 24.0f, 0.0f));
        fang2.addChild("nose2", ModelPartBuilder.create().uv(33, 2).cuboid(-2.15f, -1.45f, -0.35f, 3.0f, 3.0f, 1.0f, Dilation.NONE), ModelTransform.of(-2.0f, -25.0f, -4.5f, 0.0631f, -0.3435f, -0.1855f));

        return TexturedModelData.of(modelData, 64, 64);
    }
}