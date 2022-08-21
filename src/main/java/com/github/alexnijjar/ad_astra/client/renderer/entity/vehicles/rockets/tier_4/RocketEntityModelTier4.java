package com.github.alexnijjar.beyond_earth.client.renderer.entity.vehicles.rockets.tier_4;

import com.github.alexnijjar.beyond_earth.client.renderer.entity.vehicles.VehicleEntityModel;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier4;
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

@Environment(EnvType.CLIENT)
public class RocketEntityModelTier4 extends VehicleEntityModel<RocketEntityTier4> {

        public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("tier_4_rocket"), "main");

        public RocketEntityModelTier4(ModelPart root) {
                super(root, "rocket");
        }

        public static TexturedModelData getTexturedModelData() {
                ModelData modelData = new ModelData();
                ModelPartData modelPartData = modelData.getRoot();

                ModelPartData rocket = modelPartData.addChild("rocket", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 24.0f, 0.0f));

                // side_booster_right.
                rocket.addChild("side_booster_right",
                                ModelPartBuilder.create().uv(0, 147).cuboid(-18.0f, -56.0f, -3.0f, 6.0f, 6.0f, 6.0f, new Dilation(0.0f)).uv(0, 159).cuboid(-19.0f, -50.0f, -4.0f, 8.0f, 35.0f, 8.0f, new Dilation(0.0f)).uv(0, 243)
                                                .cuboid(-19.0f, -13.0f, -4.0f, 8.0f, 5.0f, 8.0f, new Dilation(0.0f)).uv(32, 248).cuboid(-18.0f, -15.0f, -3.0f, 6.0f, 2.0f, 6.0f, new Dilation(0.0f)).uv(0, 139)
                                                .cuboid(-11.0f, -25.0f, -2.0f, 2.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(0, 139).cuboid(-11.0f, -42.0f, -2.0f, 2.0f, 4.0f, 4.0f, new Dilation(0.0f)),
                                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

                // side_booster_left.
                rocket.addChild("side_booster_left",
                                ModelPartBuilder.create().uv(0, 147).cuboid(11.0f, -56.0f, -3.0f, 6.0f, 6.0f, 6.0f, new Dilation(0.0f)).uv(32, 159).cuboid(10.0f, -50.0f, -4.0f, 8.0f, 35.0f, 8.0f, new Dilation(0.0f)).uv(32, 248)
                                                .cuboid(11.0f, -15.0f, -3.0f, 6.0f, 2.0f, 6.0f, new Dilation(0.0f)).uv(0, 243).cuboid(10.0f, -13.0f, -4.0f, 8.0f, 5.0f, 8.0f, new Dilation(0.0f)).uv(0, 139)
                                                .cuboid(8.0f, -42.0f, -2.0f, 2.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(0, 139).cuboid(8.0f, -25.0f, -2.0f, 2.0f, 4.0f, 4.0f, new Dilation(0.0f)),
                                ModelTransform.pivot(1.0f, 0.0f, 0.0f));

                ModelPartData main = rocket.addChild("main", ModelPartBuilder.create().uv(0, 28).cuboid(-9.0f, -74.0f, -9.0f, 18.0f, 59.0f, 18.0f, new Dilation(0.0f)).uv(54, 39).cuboid(-6.0f, -26.0f, -10.0f, 12.0f, 6.0f, 1.0f, new Dilation(0.0f))
                                .uv(0, 105).cuboid(-9.0f, -16.0f, -9.0f, 18.0f, 0.0f, 18.0f, new Dilation(0.0f)).uv(31, 0).cuboid(-4.0f, -54.0f, -9.5f, 8.0f, 2.0f, 1.0f, new Dilation(0.0f)).uv(31, 3)
                                .cuboid(-4.0f, -44.0f, -9.5f, 8.0f, 2.0f, 1.0f, new Dilation(0.0f)).uv(32, 6).cuboid(-6.0f, -54.0f, -9.5f, 2.0f, 12.0f, 1.0f, new Dilation(0.0f)).uv(38, 6).cuboid(4.0f, -54.0f, -9.5f, 2.0f, 12.0f, 1.0f, new Dilation(0.0f)),
                                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

                // cube_r1.
                main.addChild("cube_r1", ModelPartBuilder.create().uv(54, 39).cuboid(-6.0f, -26.0f, -10.0f, 12.0f, 6.0f, 1.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, -3.1416f, 0.0f, 3.1416f));

                // fins.
                main.addChild("fins", ModelPartBuilder.create(), ModelTransform.pivot(-2.0f, -8.0f, -2.0f));

                ModelPartData pyramid = main.addChild("pyramid", ModelPartBuilder.create().uv(88, 0).cuboid(-10.0f, -58.0f, -10.0f, 20.0f, 2.0f, 20.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, -17.0f, 0.0f));

                // . cube_r2
                pyramid.addChild("cube_r2", ModelPartBuilder.create().uv(80, 0).cuboid(-1.0f, 2.6076f, -3.171f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -84.0f, 0.0f, -0.3491f, -2.3562f, 0.0f));

                // cube_r3.
                pyramid.addChild("cube_r3", ModelPartBuilder.create().uv(80, 0).cuboid(-1.0f, 2.6076f, -3.171f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -84.0f, 0.0f, -0.3491f, 2.3562f, 0.0f));

                // cube_r4
                pyramid.addChild("cube_r4", ModelPartBuilder.create().uv(80, 0).cuboid(-1.0f, 2.6076f, -3.171f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -84.0f, 0.0f, -0.3491f, -0.7854f, 0.0f));

                // cube_r5.
                pyramid.addChild("cube_r5", ModelPartBuilder.create().uv(80, 0).cuboid(-1.0f, 2.6076f, -3.171f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -84.0f, 0.0f, -0.3491f, 0.7854f, 0.0f));

                // cube_r6.
                pyramid.addChild("cube_r6", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -21.5488f, 8.7536f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -57.0f, 0.0f, -2.8798f, -1.5708f, 3.1416f));

                // cube_r7.
                pyramid.addChild("cube_r7", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -21.5488f, 8.7536f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -57.0f, 0.0f, -2.8798f, 1.5708f, 3.1416f));

                // cube_r8.
                pyramid.addChild("cube_r8", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -21.5488f, 8.7536f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -57.0f, 0.0f, -2.8798f, 3.1416f, 3.1416f));

                // cube_r9.
                pyramid.addChild("cube_r9", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -21.5488f, 8.7536f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -57.0f, 0.0f, -2.8798f, 0.0f, 3.1416f));

                // booster.
                main.addChild("booster", ModelPartBuilder.create().uv(0, 225).cuboid(-6.0f, -15.0f, -6.0f, 12.0f, 6.0f, 12.0f, new Dilation(0.0f)).uv(0, 202).cuboid(-7.0f, -9.0f, -7.0f, 14.0f, 9.0f, 14.0f, new Dilation(0.0f)),
                                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

                // tip.
                main.addChild("tip",
                                ModelPartBuilder.create().uv(224, 32).cuboid(-4.0f, -101.0f, -4.0f, 8.0f, 8.0f, 8.0f, new Dilation(0.0f)).uv(232, 0).cuboid(-3.0f, -109.0f, -3.0f, 6.0f, 8.0f, 6.0f, new Dilation(0.0f)).uv(240, 14)
                                                .cuboid(-2.0f, -111.0f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)).uv(240, 14).cuboid(-2.0f, -123.0f, -2.0f, 4.0f, 4.0f, 4.0f, new Dilation(-0.375f)).uv(240, 14)
                                                .cuboid(-2.0f, -114.0f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)).uv(80, 0).cuboid(-1.0f, -121.0f, -1.0f, 2.0f, 11.0f, 2.0f, new Dilation(0.0f)),
                                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

                return TexturedModelData.of(modelData, 256, 256);
        }
}
