package com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_2;

import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.VehicleEntityModel;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier2;
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
public class RocketEntityModelTier2 extends VehicleEntityModel<RocketEntityTier2> {

        public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("tier_2_rocket"), "main");

        public RocketEntityModelTier2(ModelPart root) {
                super(root, "rocket");
        }

        @SuppressWarnings("unused")
        public static TexturedModelData getTexturedModelData() {
                ModelData modelData = new ModelData();
                ModelPartData modelPartData = modelData.getRoot();
                ModelPartData rocket2 = modelPartData.addChild("rocket", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 24.0f, 0.0f));

                ModelPartData top = rocket2.addChild("top",
                                ModelPartBuilder.create().uv(0, 98).cuboid(-10.0f, -47.0f, 10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(0, 78).cuboid(-10.0f, -47.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(0, 98)
                                                .cuboid(-10.0f, -47.0f, -10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(0, 78).cuboid(10.0f, -47.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(120, 39)
                                                .cuboid(-1.0f, -85.0f, -1.0f, 2.0f, 14.0f, 2.0f, new Dilation(0.0f)).uv(112, 18).cuboid(-2.0f, -89.0f, -2.0f, 4.0f, 4.0f, 4.0f, new Dilation(0.0f)),
                                ModelTransform.pivot(0.0f, -8.0f, 0.0f));

                ModelPartData cube_r1 = top.addChild("cube_r1",
                                ModelPartBuilder.create().uv(90, 22).cuboid(-2.0f, -6.5f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)).uv(90, 22).cuboid(-2.0f, -3.5f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)).uv(72, 26)
                                                .cuboid(-3.0f, -1.5f, -3.0f, 6.0f, 11.0f, 6.0f, new Dilation(0.0f)).uv(32, 57).cuboid(0.0f, 4.5f, -12.0f, 0.0f, 17.0f, 8.0f, new Dilation(0.0f)).uv(32, 65).mirrored()
                                                .cuboid(-12.0f, 4.5f, 0.0f, 8.0f, 17.0f, 0.0f, new Dilation(0.0f)).mirrored(false).uv(32, 65).cuboid(4.0f, 4.5f, 0.0f, 8.0f, 17.0f, 0.0f, new Dilation(0.0f)),
                                ModelTransform.of(0.0f, -68.5f, 0.0f, 0.0f, -0.7854f, 0.0f));

                ModelPartData cube_r2 = top.addChild("cube_r2", ModelPartBuilder.create().uv(104, 26).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -66.0f, 0.0f, -0.48f, -0.7854f, 0.0f));

                ModelPartData cube_r3 = top.addChild("cube_r3", ModelPartBuilder.create().uv(112, 26).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 19.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -66.0f, 0.0f, -0.829f, 0.7854f, 0.0f));

                ModelPartData cube_r4 = top.addChild("cube_r4", ModelPartBuilder.create().uv(120, 26).cuboid(-1.0f, -65.4f, -13.0779f, 2.0f, 11.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 8.0f, 0.0f, 0.0f, 0.7854f, 0.0f));

                ModelPartData cube_r5 = top.addChild("cube_r5", ModelPartBuilder.create().uv(104, 26).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -66.0f, 0.0f, -0.48f, 0.7854f, 0.0f));

                ModelPartData cube_r6 = top.addChild("cube_r6", ModelPartBuilder.create().uv(112, 26).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 19.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -66.0f, 0.0f, 2.3126f, 0.7854f, 3.1416f));

                ModelPartData cube_r7 = top.addChild("cube_r7", ModelPartBuilder.create().uv(32, 57).cuboid(0.0f, 24.0f, -12.0f, 0.0f, 17.0f, 8.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -88.0f, 0.0f, 0.0f, 2.3562f, 0.0f));

                ModelPartData cube_r8 = top.addChild("cube_r8", ModelPartBuilder.create().uv(104, 26).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -66.0f, 0.0f, -0.48f, 2.3562f, 0.0f));

                ModelPartData cube_r9 = top.addChild("cube_r9", ModelPartBuilder.create().uv(120, 26).cuboid(-1.0f, -65.4f, -13.0779f, 2.0f, 11.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 8.0f, 0.0f, 0.0f, 2.3562f, 0.0f));

                ModelPartData cube_r10 = top.addChild("cube_r10", ModelPartBuilder.create().uv(112, 26).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 19.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -66.0f, 0.0f, 2.3126f, -0.7854f, 3.1416f));

                ModelPartData cube_r11 = top.addChild("cube_r11", ModelPartBuilder.create().uv(120, 26).cuboid(-1.0f, -65.4f, -13.0779f, 2.0f, 11.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 8.0f, 0.0f, 0.0f, -2.3562f, 0.0f));

                ModelPartData cube_r12 = top.addChild("cube_r12", ModelPartBuilder.create().uv(104, 26).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 27.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -66.0f, 0.0f, -0.48f, -2.3562f, 0.0f));

                ModelPartData cube_r13 = top.addChild("cube_r13", ModelPartBuilder.create().uv(112, 26).cuboid(-1.0f, -3.5f, -2.5f, 2.0f, 19.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -66.0f, 0.0f, -0.829f, -0.7854f, 0.0f));

                ModelPartData cube_r14 = top.addChild("cube_r14", ModelPartBuilder.create().uv(120, 26).cuboid(-1.0f, -65.4f, -13.0779f, 2.0f, 11.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 8.0f, 0.0f, 0.0f, -0.7854f, 0.0f));

                ModelPartData cube_r15 = top.addChild("cube_r15", ModelPartBuilder.create().uv(72, 44).cuboid(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -46.0f, 0.0f, 0.3491f, 1.5708f, 0.0f));

                ModelPartData cube_r16 = top.addChild("cube_r16", ModelPartBuilder.create().uv(72, 44).cuboid(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -46.0f, 0.0f, 0.3491f, 3.1416f, 0.0f));

                ModelPartData cube_r17 = top.addChild("cube_r17", ModelPartBuilder.create().uv(72, 44).cuboid(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -46.0f, 0.0f, 0.3491f, -1.5708f, 0.0f));

                ModelPartData cube_r18 = top.addChild("cube_r18", ModelPartBuilder.create().uv(72, 44).cuboid(-8.0f, -20.8f, 8.5175f, 16.0f, 24.0f, 0.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -46.0f, 0.0f, 0.3491f, 0.0f, 0.0f));

                ModelPartData body = rocket2.addChild("body", ModelPartBuilder.create().uv(62, 46).cuboid(4.0f, -32.0f, -10.0f, 0.0f, 8.0f, 1.0f, new Dilation(0.0f)).uv(0, 89).cuboid(-10.0f, -9.0f, -10.0f, 20.0f, 8.0f, 0.0f, new Dilation(0.0f)).uv(0, 69)
                                .cuboid(-10.0f, -9.0f, -10.0f, 0.0f, 8.0f, 20.0f, new Dilation(0.0f)).uv(0, 89).cuboid(-10.0f, -9.0f, 10.0f, 20.0f, 8.0f, 0.0f, new Dilation(0.0f)).uv(88, 0).cuboid(9.0f, -10.0f, -4.0f, 6.0f, 14.0f, 8.0f, new Dilation(0.0f))
                                .uv(0, 69).cuboid(10.0f, -9.0f, -10.0f, 0.0f, 8.0f, 20.0f, new Dilation(0.0f)).uv(0, 78).cuboid(10.0f, -32.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(0, 78)
                                .cuboid(10.0f, -26.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(0, 98).cuboid(-10.0f, -32.0f, 10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(0, 98)
                                .cuboid(-10.0f, -26.0f, 10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(36, -18).cuboid(9.0f, -46.0f, -9.0f, 0.0f, 47.0f, 18.0f, new Dilation(0.0f)).uv(36, 0)
                                .cuboid(-9.0f, -46.0f, 9.0f, 18.0f, 47.0f, 0.0f, new Dilation(0.0f)).uv(0, 0).cuboid(-9.0f, -46.0f, -9.0f, 18.0f, 47.0f, 0.0f, new Dilation(0.0f)).uv(62, 53)
                                .cuboid(-9.0f, -46.0f, -9.0f, 2.0f, 47.0f, 2.0f, new Dilation(0.0f)).uv(0, 101).cuboid(-10.0f, -26.0f, -10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(0, 101)
                                .cuboid(-10.0f, -32.0f, -10.0f, 20.0f, 2.0f, 0.0f, new Dilation(0.0f)).uv(36, -18).mirrored().cuboid(-9.0f, -46.0f, -9.0f, 0.0f, 47.0f, 18.0f, new Dilation(0.0f)).mirrored(false).uv(0, 78)
                                .cuboid(-10.0f, -26.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(0, 78).cuboid(-10.0f, -32.0f, -10.0f, 0.0f, 2.0f, 20.0f, new Dilation(0.0f)).uv(36, 47)
                                .cuboid(-6.0f, -34.0f, -10.0f, 12.0f, 12.0f, 1.0f, new Dilation(0.0f)).uv(35, 60).cuboid(-4.0f, -24.0f, -10.0f, 8.0f, 0.0f, 1.0f, new Dilation(0.0f)).uv(62, 46)
                                .cuboid(-4.0f, -32.0f, -10.0f, 0.0f, 8.0f, 1.0f, new Dilation(0.0f)).uv(35, 60).cuboid(-4.0f, -32.0f, -10.0f, 8.0f, 0.0f, 1.0f, new Dilation(0.0f)).uv(-18, 47)
                                .cuboid(-9.0f, 1.0f, -9.0f, 18.0f, 0.0f, 18.0f, new Dilation(0.0f)).uv(-18, 47).cuboid(-9.0f, -46.0f, -9.0f, 18.0f, 0.0f, 18.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, -8.0f, 0.0f));

                ModelPartData body_r1 = body.addChild("body_r1", ModelPartBuilder.create().uv(62, 53).cuboid(-1.0f, -23.5f, -1.0f, 2.0f, 47.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-8.0f, -22.5f, 8.0f, 0.0f, 1.5708f, 0.0f));

                ModelPartData body_r2 = body.addChild("body_r2", ModelPartBuilder.create().uv(62, 53).cuboid(-1.0f, -23.5f, -1.0f, 2.0f, 47.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(8.0f, -22.5f, 8.0f, 0.0f, 3.1416f, 0.0f));

                ModelPartData body_r3 = body.addChild("body_r3", ModelPartBuilder.create().uv(62, 53).cuboid(-1.0f, -23.5f, -1.0f, 2.0f, 47.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(8.0f, -22.5f, -8.0f, 0.0f, -1.5708f, 0.0f));

                ModelPartData cube_r19 = body.addChild("cube_r19", ModelPartBuilder.create().uv(88, 0).cuboid(-3.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -3.0f, 12.0f, 0.0f, -1.5708f, 0.0f));

                ModelPartData cube_r20 = body.addChild("cube_r20", ModelPartBuilder.create().uv(88, 0).cuboid(-3.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new Dilation(0.0f)), ModelTransform.of(-12.0f, -3.0f, 0.0f, 0.0f, 3.1416f, 0.0f));

                ModelPartData cube_r21 = body.addChild("cube_r21", ModelPartBuilder.create().uv(88, 0).cuboid(-3.0f, -7.0f, -4.0f, 6.0f, 14.0f, 8.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -3.0f, -12.0f, 0.0f, 1.5708f, 0.0f));

                ModelPartData bottom = body.addChild("bottom", ModelPartBuilder.create().uv(0, 65).cuboid(-8.0f, 4.0f, 8.0f, 16.0f, 4.0f, 0.0f, new Dilation(0.0f)).uv(-16, 72).cuboid(-8.0f, 4.0f, -8.0f, 16.0f, 0.0f, 16.0f, new Dilation(0.0f)).uv(-16, 103)
                                .cuboid(-8.0f, 8.0f, -8.0f, 16.0f, 0.0f, 16.0f, new Dilation(0.0f)).uv(0, 49).cuboid(-8.0f, 4.0f, -8.0f, 0.0f, 4.0f, 16.0f, new Dilation(0.0f)).uv(0, 65).cuboid(-8.0f, 4.0f, -8.0f, 16.0f, 4.0f, 0.0f, new Dilation(0.0f))
                                .uv(0, 49).cuboid(8.0f, 4.0f, -8.0f, 0.0f, 4.0f, 16.0f, new Dilation(0.0f)).uv(0, 69).cuboid(-6.0f, 1.0f, -6.0f, 12.0f, 3.0f, 0.0f, new Dilation(0.0f)).uv(0, 57)
                                .cuboid(-6.0f, 1.0f, -6.0f, 0.0f, 3.0f, 12.0f, new Dilation(0.0f)).uv(0, 57).cuboid(6.0f, 1.0f, -6.0f, 0.0f, 3.0f, 12.0f, new Dilation(0.0f)).uv(0, 69).cuboid(-6.0f, 1.0f, 6.0f, 12.0f, 3.0f, 0.0f, new Dilation(0.0f)),
                                ModelTransform.pivot(0.0f, 0.0f, 0.0f));

                ModelPartData fins = rocket2.addChild("fins", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, -8.0f, 0.0f));

                ModelPartData cube_r22 = fins.addChild("cube_r22", ModelPartBuilder.create().uv(72, 0).cuboid(-2.0f, 11.0f, 22.8284f, 4.0f, 22.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, -24.0f, -1.0f, 0.0f, 0.7854f, 0.0f));

                ModelPartData cube_r23 = fins.addChild("cube_r23", ModelPartBuilder.create().uv(72, 0).cuboid(-0.5858f, 11.0f, 21.4142f, 4.0f, 22.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, -24.0f, -1.0f, 0.0f, -0.7854f, 0.0f));

                ModelPartData cube_r24 = fins.addChild("cube_r24", ModelPartBuilder.create().uv(72, 0).cuboid(-2.0f, 11.0f, 20.0f, 4.0f, 22.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, -24.0f, -1.0f, 0.0f, -2.3562f, 0.0f));

                ModelPartData cube_r25 = fins.addChild("cube_r25", ModelPartBuilder.create().uv(72, 0).cuboid(-3.4142f, 11.0f, 21.4142f, 4.0f, 22.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, -24.0f, -1.0f, 0.0f, 2.3562f, 0.0f));

                ModelPartData cube_r26 = fins.addChild("cube_r26", ModelPartBuilder.create().uv(116, 0).cuboid(-1.0f, 4.25f, 13.0f, 2.0f, 13.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, 9.0f, -1.0f, 1.1345f, -2.3562f, 0.0f));

                ModelPartData cube_r27 = fins.addChild("cube_r27", ModelPartBuilder.create().uv(116, 0).cuboid(-1.0f, 12.9801f, 1.507f, 2.0f, 13.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -18.0f, 1.0f, -2.0944f, -0.7854f, 3.1416f));

                ModelPartData cube_r28 = fins.addChild("cube_r28", ModelPartBuilder.create().uv(110, 46).cuboid(0.0f, 1.0f, 13.0f, 0.0f, 15.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, 7.0f, -1.0f, 1.1345f, -2.3562f, 0.0f));

                ModelPartData cube_r29 = fins.addChild("cube_r29", ModelPartBuilder.create().uv(116, 0).cuboid(0.4142f, 11.6984f, 0.9094f, 2.0f, 13.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -18.0f, 1.0f, -2.0944f, 0.7854f, 3.1416f));

                ModelPartData cube_r30 = fins.addChild("cube_r30", ModelPartBuilder.create().uv(116, 0).cuboid(-1.0f, 4.25f, 13.0f, 2.0f, 13.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 9.0f, -1.0f, 1.1345f, 2.3562f, 0.0f));

                ModelPartData cube_r31 = fins.addChild("cube_r31", ModelPartBuilder.create().uv(110, 46).cuboid(0.0f, 1.0f, 13.0f, 0.0f, 15.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 7.0f, -1.0f, 1.1345f, 2.3562f, 0.0f));

                ModelPartData cube_r32 = fins.addChild("cube_r32", ModelPartBuilder.create().uv(116, 0).cuboid(-2.4142f, 11.6984f, 0.9094f, 2.0f, 13.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -18.0f, 1.0f, 1.0472f, -0.7854f, 0.0f));

                ModelPartData cube_r33 = fins.addChild("cube_r33", ModelPartBuilder.create().uv(110, 46).cuboid(0.0f, 1.0f, 13.0f, 0.0f, 15.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, 7.0f, 1.0f, 1.1345f, -0.7854f, 0.0f));

                ModelPartData cube_r34 = fins.addChild("cube_r34", ModelPartBuilder.create().uv(116, 0).cuboid(-1.0f, 4.25f, 13.0f, 2.0f, 13.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-1.0f, 9.0f, 1.0f, 1.1345f, -0.7854f, 0.0f));

                ModelPartData cube_r35 = fins.addChild("cube_r35", ModelPartBuilder.create().uv(116, 0).cuboid(-1.0f, 10.4167f, 0.3117f, 2.0f, 13.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, -18.0f, 1.0f, 1.0472f, 0.7854f, 0.0f));

                ModelPartData cube_r36 = fins.addChild("cube_r36", ModelPartBuilder.create().uv(110, 46).cuboid(0.0f, 1.0f, 13.0f, 0.0f, 15.0f, 9.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 7.0f, 1.0f, 1.1345f, 0.7854f, 0.0f));

                ModelPartData cube_r37 = fins.addChild("cube_r37", ModelPartBuilder.create().uv(116, 0).cuboid(-1.0f, 4.25f, 13.0f, 2.0f, 13.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(1.0f, 9.0f, 1.0f, 1.1345f, 0.7854f, 0.0f));
                return TexturedModelData.of(modelData, 128, 128);
        }
}
