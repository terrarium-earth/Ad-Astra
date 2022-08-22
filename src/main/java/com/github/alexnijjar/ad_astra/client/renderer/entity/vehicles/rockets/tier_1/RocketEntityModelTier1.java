package com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_1;

import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.VehicleEntityModel;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier1;
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
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public class RocketEntityModelTier1 extends VehicleEntityModel<RocketEntityTier1> {

    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("tier_1_rocket"), "main");

    public RocketEntityModelTier1(ModelPart root) {
        super(root, "rocket");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData rocket = modelPartData.addChild("rocket",
                ModelPartBuilder.create().uv(0, 167).cuboid(-12.0f, -21.0f, -1.0f, 3.0f, 3.0f, 2.0f, new Dilation(0.0f)).uv(184, 0).cuboid(-9.0f, -52.0f, -9.0f, 18.0f, 44.0f, 18.0f, new Dilation(0.0f)).uv(184, 62)
                        .cuboid(-9.0f, -10.0f, -9.0f, 18.0f, 0.0f, 18.0f, new Dilation(0.0f)).uv(36, 23).cuboid(-2.0f, -78.0f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)).uv(36, 0).cuboid(-2.0f, -91.0f, -2.0f, 4.0f, 4.0f, 4.0f, new Dilation(-0.375f))
                        .uv(36, 8).cuboid(-1.0f, -90.0f, -1.0f, 2.0f, 13.0f, 2.0f, new Dilation(0.0f)).uv(36, 29).cuboid(-3.0f, -76.0f, -3.0f, 6.0f, 8.0f, 6.0f, new Dilation(0.0f)).uv(22, 128)
                        .cuboid(-6.0f, -5.0f, -6.0f, 12.0f, 2.0f, 12.0f, new Dilation(0.0f)).uv(0, 85).cuboid(-10.0f, -13.0f, -10.0f, 20.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(0, 85).cuboid(-10.0f, -16.0f, -10.0f, 20.0f, 2.0f, 20.0f, new Dilation(0.0f))
                        .uv(22, 116).cuboid(-5.0f, -7.0f, -5.0f, 10.0f, 2.0f, 10.0f, new Dilation(0.0f)).uv(22, 107).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 1.0f, 8.0f, new Dilation(0.0f)).uv(230, 88)
                        .cuboid(-6.0f, -43.0f, -9.5f, 12.0f, 12.0f, 1.0f, new Dilation(0.0f)).uv(238, 101).cuboid(-4.0f, -41.0f, -9.5f, 8.0f, 8.0f, 1.0f, new Dilation(0.0f)).uv(0, 152).cuboid(-13.0f, -18.0f, -3.0f, 4.0f, 9.0f, 6.0f, new Dilation(0.0f)),
                ModelTransform.pivot(0.0f, 25.0f, 0.0f));

        // cube_r1.
        rocket.addChild("cube_r1", ModelPartBuilder.create().uv(0, 167).cuboid(-12.0f, -21.0f, -1.0f, 3.0f, 3.0f, 2.0f, new Dilation(0.0f)).uv(0, 152).cuboid(-13.0f, -18.0f, -3.0f, 4.0f, 9.0f, 6.0f, new Dilation(0.0f)),
                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 3.1416f, 0.0f));

        // cube_r2.
        rocket.addChild("cube_r2", ModelPartBuilder.create().uv(0, 24).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -72.0f, 0.0f, -0.48f, 0.7854f, 0.0f));

        // cube_r3.
        rocket.addChild("cube_r3", ModelPartBuilder.create().uv(0, 24).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -72.0f, 0.0f, -0.48f, -2.3562f, 0.0f));

        // cube_r4.
        rocket.addChild("cube_r4", ModelPartBuilder.create().uv(0, 24).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -72.0f, 0.0f, -0.48f, 2.3562f, 0.0f));

        // cube_r5.
        rocket.addChild("cube_r5", ModelPartBuilder.create().uv(0, 24).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -72.0f, 0.0f, -0.48f, -0.7854f, 0.0f));

        // cube_r6.
        rocket.addChild("cube_r6", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -52.0f, 0.0f, 0.3491f, 1.5708f, 0.0f));

        // cube_r7.
        rocket.addChild("cube_r7", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -52.0f, 0.0f, 0.3491f, 3.1416f, 0.0f));

        // cube_r8.
        rocket.addChild("cube_r8", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -52.0f, 0.0f, 0.3491f, -1.5708f, 0.0f));

        // cube_r9.
        rocket.addChild("cube_r9", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -52.0f, 0.0f, 0.3491f, 0.0f, 0.0f));

        // cube_r10.
        rocket.addChild("cube_r10", ModelPartBuilder.create().uv(0, 131).cuboid(-2.0f, -17.0f, 20.0f, 4.0f, 17.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, 0.0f, 1.0f, 0.0f, -0.7854f, 0.0f));

        // cube_r11.
        rocket.addChild("cube_r11", ModelPartBuilder.create().uv(0, 107).cuboid(-1.0f, 1.0f, 13.0f, 2.0f, 15.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, 0.0f, 1.0f, 1.1345f, -0.7854f, 0.0f));

        // cube_r12.
        rocket.addChild("cube_r12", ModelPartBuilder.create().uv(0, 131).cuboid(-2.0f, -17.0f, 20.0f, 4.0f, 17.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, 0.0f, -1.0f, 0.0f, -2.3562f, 0.0f));

        // cube_r13.
        rocket.addChild("cube_r13", ModelPartBuilder.create().uv(0, 107).cuboid(-1.0f, 1.0f, 13.0f, 2.0f, 15.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, 0.0f, -1.0f, 1.1345f, -2.3562f, 0.0f));

        // cube_r14.
        rocket.addChild("cube_r14", ModelPartBuilder.create().uv(0, 131).cuboid(-2.0f, -17.0f, 20.0f, 4.0f, 17.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 0.0f, 1.0f, 0.0f, 0.7854f, 0.0f));

        // cube_r15.
        rocket.addChild("cube_r15", ModelPartBuilder.create().uv(0, 107).cuboid(-1.0f, 1.0f, 13.0f, 2.0f, 15.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 0.0f, 1.0f, 1.1345f, 0.7854f, 0.0f));

        // cube_r16.
        rocket.addChild("cube_r16", ModelPartBuilder.create().uv(20, 142).cuboid(-3.0f, -18.0f, 20.4142f, 6.0f, 5.0f, 6.0f, new Dilation(-0.3f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 2.3562f, 0.0f));

        // cube_r17.
        rocket.addChild("cube_r17", ModelPartBuilder.create().uv(20, 142).cuboid(-3.0f, -18.0f, 20.4142f, 6.0f, 5.0f, 6.0f, new Dilation(-0.3f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 0.7854f, 0.0f));

        // cube_r18.
        rocket.addChild("cube_r18", ModelPartBuilder.create().uv(20, 142).cuboid(-3.0f, -18.0f, 20.4142f, 6.0f, 5.0f, 6.0f, new Dilation(-0.3f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, -2.3562f, 0.0f));

        // cube_r19.
        rocket.addChild("cube_r19", ModelPartBuilder.create().uv(20, 142).cuboid(-3.0f, -18.0f, 20.4142f, 6.0f, 5.0f, 6.0f, new Dilation(-0.3f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, -0.7854f, 0.0f));

        // cube_r20.
        rocket.addChild("cube_r20", ModelPartBuilder.create().uv(0, 131).cuboid(-2.0f, -17.0f, 20.0f, 4.0f, 17.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 0.0f, -1.0f, 0.0f, 2.3562f, 0.0f));

        // cube_r21.
        rocket.addChild("cube_r21", ModelPartBuilder.create().uv(0, 107).cuboid(-1.0f, 1.0f, 13.0f, 2.0f, 15.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 0.0f, -1.0f, 1.1345f, 2.3562f, 0.0f));

        return TexturedModelData.of(modelData, 256, 256);
    }
}
