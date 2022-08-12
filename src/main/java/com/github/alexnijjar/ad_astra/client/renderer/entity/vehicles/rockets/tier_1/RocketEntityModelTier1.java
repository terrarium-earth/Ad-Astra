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

        @SuppressWarnings("unused")
        public static TexturedModelData getTexturedModelData() {
                ModelData modelData = new ModelData();
                ModelPartData modelPartData = modelData.getRoot();
                ModelPartData rocket = modelPartData.addChild("rocket", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 25.0f, 0.0f));

                ModelPartData top = rocket.addChild("top",
                                ModelPartBuilder.create().uv(0, 48).cuboid(10.0f, -52.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(0, 68).cuboid(-10.0f, -52.0f, -10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(0, 48)
                                                .cuboid(-10.0f, -52.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(0, 68).cuboid(-10.0f, -52.0f, 10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(104, 67)
                                                .cuboid(-3.0f, -75.0f, -3.0f, 6.0f, 8.0f, 6.0f, new Dilation(0.0f)).uv(88, 69).cuboid(-2.0f, -77.0f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)).uv(80, 69)
                                                .cuboid(-1.0f, -89.0f, -1.0f, 2.0f, 13.0f, 2.0f, new Dilation(0.0f)).uv(64, 69).cuboid(-2.0f, -90.0f, -2.0f, 4.0f, 4.0f, 4.0f, new Dilation(0.0f)),
                                ModelTransform.pivot(0.0f, -1.0f, 0.0f));

                ModelPartData cube_r1 = top.addChild("cube_r1", ModelPartBuilder.create().uv(120, 37).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -71.0f, 0.0f, -0.48f, -0.7854f, 0.0f));

                ModelPartData cube_r2 = top.addChild("cube_r2", ModelPartBuilder.create().uv(120, 37).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -71.0f, 0.0f, -0.48f, -2.3562f, 0.0f));

                ModelPartData cube_r3 = top.addChild("cube_r3", ModelPartBuilder.create().uv(120, 37).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -71.0f, 0.0f, -0.48f, 2.3562f, 0.0f));

                ModelPartData cube_r4 = top.addChild("cube_r4", ModelPartBuilder.create().uv(120, 37).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -71.0f, 0.0f, -0.48f, 0.7854f, 0.0f));

                ModelPartData cube_r5 = top.addChild("cube_r5", ModelPartBuilder.create().uv(65, 45).cuboid(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -51.0f, 0.0f, 0.3491f, -1.5708f, 0.0f));

                ModelPartData cube_r6 = top.addChild("cube_r6", ModelPartBuilder.create().uv(65, 45).cuboid(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -51.0f, 0.0f, 0.3491f, 0.0f, 0.0f));

                ModelPartData cube_r7 = top.addChild("cube_r7", ModelPartBuilder.create().uv(65, 45).cuboid(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -51.0f, 0.0f, 0.3491f, 1.5708f, 0.0f));

                ModelPartData cube_r8 = top.addChild("cube_r8", ModelPartBuilder.create().uv(65, 45).cuboid(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -51.0f, 0.0f, 0.3491f, 3.1416f, 0.0f));

                ModelPartData body = rocket.addChild("body",
                                ModelPartBuilder.create().uv(36, 44).cuboid(-6.0f, -42.0f, -10.0f, 12.0f, 12.0f, 1.0f, new Dilation(0.0f)).uv(35, 58).cuboid(-4.0f, -32.0f, -10.0f, 8.0f, 0.0f, 1.0f, new Dilation(0.0f)).uv(63, 43)
                                                .cuboid(4.0f, -40.0f, -10.0f, 0.0f, 8.0f, 1.0f, new Dilation(0.0f)).uv(35, 58).cuboid(-4.0f, -40.0f, -10.0f, 8.0f, 0.0f, 1.0f, new Dilation(0.0f)).uv(63, 43)
                                                .cuboid(-4.0f, -40.0f, -10.0f, 0.0f, 8.0f, 1.0f, new Dilation(0.0f)).uv(0, 0).cuboid(-9.0f, -51.0f, -9.0f, 18.0f, 44.0f, 0.0f, new Dilation(0.0f)).uv(0, 77)
                                                .cuboid(-9.0f, -51.0f, -9.0f, 2.0f, 44.0f, 2.0f, new Dilation(0.0f)).uv(36, -18).cuboid(-9.0f, -51.0f, -9.0f, 0.0f, 44.0f, 18.0f, new Dilation(0.0f)).uv(36, 0)
                                                .cuboid(-9.0f, -51.0f, 9.0f, 18.0f, 44.0f, 0.0f, new Dilation(0.0f)).uv(36, -18).cuboid(9.0f, -51.0f, -9.0f, 0.0f, 44.0f, 18.0f, new Dilation(0.0f)).uv(0, 62)
                                                .cuboid(-10.0f, -15.0f, -10.0f, 20.0f, 5.0f, 0.0f, new Dilation(0.0f)).uv(0, 42).cuboid(10.0f, -15.0f, -10.0f, 0.0f, 5.0f, 20.0f, new Dilation(0.0f)).uv(0, 62)
                                                .cuboid(-10.0f, -15.0f, 10.0f, 20.0f, 5.0f, 0.0f, new Dilation(0.0f)).uv(0, 42).cuboid(-10.0f, -15.0f, -10.0f, 0.0f, 5.0f, 20.0f, new Dilation(0.0f)).uv(-18, 44)
                                                .cuboid(-9.0f, -7.0f, -9.0f, 18.0f, 0.0f, 18.0f, new Dilation(0.0f)).uv(-18, 44).cuboid(-9.0f, -50.0f, -9.0f, 18.0f, 0.0f, 18.0f, new Dilation(0.0f)).uv(88, 0)
                                                .cuboid(-13.0f, -17.0f, -3.0f, 4.0f, 9.0f, 6.0f, new Dilation(0.0f)).uv(0, 71).cuboid(-12.0f, -20.0f, -1.0f, 3.0f, 3.0f, 2.0f, new Dilation(0.0f)),
                                ModelTransform.pivot(0.0f, -1.0f, 0.0f));

                ModelPartData cube_r9 = body.addChild("cube_r9", ModelPartBuilder.create().uv(0, 71).cuboid(-12.0f, -21.0f, -1.0f, 3.0f, 3.0f, 2.0f, new Dilation(0.0f)).uv(88, 0).cuboid(-13.0f, -18.0f, -3.0f, 4.0f, 9.0f, 6.0f, new Dilation(0.0f)),
                                ModelTransform.of(0.0f, 1.0f, 0.0f, 0.0f, 3.1416f, 0.0f));

                ModelPartData cube_r10 = body.addChild("cube_r10", ModelPartBuilder.create().uv(0, 77).cuboid(-1.0f, -22.0f, -1.0f, 2.0f, 44.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-8.0f, -29.0f, 8.0f, 0.0f, 1.5708f, 0.0f));

                ModelPartData cube_r11 = body.addChild("cube_r11", ModelPartBuilder.create().uv(0, 77).cuboid(-1.0f, -22.0f, -1.0f, 2.0f, 44.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(8.0f, -29.0f, 8.0f, 0.0f, 3.1416f, 0.0f));

                ModelPartData cube_r12 = body.addChild("cube_r12", ModelPartBuilder.create().uv(0, 77).cuboid(-1.0f, -22.0f, -1.0f, 2.0f, 44.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(8.0f, -29.0f, -8.0f, 0.0f, -1.5708f, 0.0f));

                ModelPartData bottom = body.addChild("bottom", ModelPartBuilder.create().uv(94, 15).cuboid(-8.0f, -4.0f, -8.0f, 16.0f, 4.0f, 0.0f, new Dilation(0.0f)).uv(94, -1).cuboid(-8.0f, -4.0f, -8.0f, 0.0f, 4.0f, 16.0f, new Dilation(0.0f)).uv(94, 15)
                                .cuboid(-8.0f, -4.0f, 8.0f, 16.0f, 4.0f, 0.0f, new Dilation(0.0f)).uv(94, -1).cuboid(8.0f, -4.0f, -8.0f, 0.0f, 4.0f, 16.0f, new Dilation(0.0f)).uv(78, 22).cuboid(-8.0f, -4.0f, -8.0f, 16.0f, 0.0f, 16.0f, new Dilation(0.0f))
                                .uv(80, 81).cuboid(-8.0f, 0.0f, -8.0f, 16.0f, 0.0f, 16.0f, new Dilation(0.0f)).uv(94, 19).cuboid(-6.0f, -7.0f, 6.0f, 12.0f, 3.0f, 0.0f, new Dilation(0.0f)).uv(94, 19)
                                .cuboid(-6.0f, -7.0f, -6.0f, 12.0f, 3.0f, 0.0f, new Dilation(0.0f)).uv(94, 7).cuboid(6.0f, -7.0f, -6.0f, 0.0f, 3.0f, 12.0f, new Dilation(0.0f)).uv(94, 7).cuboid(-6.0f, -7.0f, -6.0f, 0.0f, 3.0f, 12.0f, new Dilation(0.0f)),
                                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

                ModelPartData fins = rocket.addChild("fins", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, -1.0f, 0.0f));

                ModelPartData cube_r13 = fins.addChild("cube_r13", ModelPartBuilder.create().uv(72, 21).cuboid(-1.0f, 1.0f, 13.0f, 2.0f, 15.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 2.0f, -1.0f, 1.1345f, 2.3562f, 0.0f));

                ModelPartData cube_r14 = fins.addChild("cube_r14", ModelPartBuilder.create().uv(72, 0).cuboid(-2.0f, -17.0f, 20.0f, 4.0f, 17.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 1.0f, -1.0f, 0.0f, 2.3562f, 0.0f));

                ModelPartData cube_r15 = fins.addChild("cube_r15", ModelPartBuilder.create().uv(72, 0).cuboid(-2.0f, -17.0f, 20.0f, 4.0f, 17.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, 1.0f, -1.0f, 0.0f, -2.3562f, 0.0f));

                ModelPartData cube_r16 = fins.addChild("cube_r16", ModelPartBuilder.create().uv(72, 21).cuboid(-1.0f, 1.0f, 13.0f, 2.0f, 15.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, 2.0f, -1.0f, 1.1345f, -2.3562f, 0.0f));

                ModelPartData cube_r17 = fins.addChild("cube_r17", ModelPartBuilder.create().uv(72, 0).cuboid(-2.0f, -17.0f, 20.0f, 4.0f, 17.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, 1.0f, 1.0f, 0.0f, -0.7854f, 0.0f));

                ModelPartData cube_r18 = fins.addChild("cube_r18", ModelPartBuilder.create().uv(72, 21).cuboid(-1.0f, 1.0f, 13.0f, 2.0f, 15.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, 2.0f, 1.0f, 1.1345f, -0.7854f, 0.0f));

                ModelPartData cube_r19 = fins.addChild("cube_r19", ModelPartBuilder.create().uv(72, 0).cuboid(-2.0f, -17.0f, 20.0f, 4.0f, 17.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 1.0f, 1.0f, 0.0f, 0.7854f, 0.0f));

                ModelPartData cube_r20 = fins.addChild("cube_r20", ModelPartBuilder.create().uv(72, 21).cuboid(-1.0f, 1.0f, 13.0f, 2.0f, 15.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 2.0f, 1.0f, 1.1345f, 0.7854f, 0.0f));
                return TexturedModelData.of(modelData, 128, 128);
        }
}
