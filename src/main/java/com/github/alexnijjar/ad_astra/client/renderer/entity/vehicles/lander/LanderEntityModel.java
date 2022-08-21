package com.github.alexnijjar.beyond_earth.client.renderer.entity.vehicles.lander;

import com.github.alexnijjar.beyond_earth.entities.vehicles.LanderEntity;
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
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class LanderEntityModel extends EntityModel<LanderEntity> {

        public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("lander"), "main");
        private final ModelPart leg1;
        private final ModelPart leg2;
        private final ModelPart leg3;
        private final ModelPart leg4;
        private final ModelPart bb_main;

        public LanderEntityModel(ModelPart root) {
                this.leg1 = root.getChild("leg1");
                this.leg2 = root.getChild("leg2");
                this.leg3 = root.getChild("leg3");
                this.leg4 = root.getChild("leg4");
                this.bb_main = root.getChild("bb_main");
        }

        public static TexturedModelData getTexturedModelData() {
                ModelData modelData = new ModelData();
                ModelPartData modelPartData = modelData.getRoot();

                ModelPartData leg1 = modelPartData.addChild("leg1", ModelPartBuilder.create().uv(0, 144).cuboid(-21.0f, 3.0f, 11.0f, 10.0f, 1.0f, 10.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 20.0f, 0.0f));

                // cube_r1.
                leg1.addChild("cube_r1", ModelPartBuilder.create().uv(116, 44).cuboid(-1.0f, -12.5f, 16.5f, 2.0f, 1.0f, 3.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, -0.6981f, -0.7854f, 0.0f));

                // cube_r2.
                leg1.addChild("cube_r2", ModelPartBuilder.create().uv(116, 37).cuboid(-21.0f, 0.99f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 0.7854f, 0.0f));

                // cube_r3.
                leg1.addChild("cube_r3", ModelPartBuilder.create().uv(64, 26).cuboid(-2.0f, -2.0f, 18.0f, 4.0f, 4.0f, 3.0f, new Dilation(0.0f)).uv(42, 26).cuboid(-3.0f, -3.0f, 14.0f, 6.0f, 6.0f, 5.0f, new Dilation(0.0f)),
                                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.6109f, -0.7854f, 0.0f));

                // cube_r4.
                leg1.addChild("cube_r4", ModelPartBuilder.create().uv(48, 46).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 12.0f, 3.0f, new Dilation(-0.5f)).uv(104, 37).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 11.0f, 3.0f, new Dilation(0.0f)),
                                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.3927f, -0.7854f, 0.0f));

                ModelPartData leg2 = modelPartData.addChild("leg2", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 20.0f, 0.0f));

                // cube_r5.
                leg2.addChild("cube_r5", ModelPartBuilder.create().uv(42, 26).cuboid(-3.0f, -3.0f, 14.0f, 6.0f, 6.0f, 5.0f, new Dilation(0.0f)).uv(64, 26).cuboid(-2.0f, -2.0f, 18.0f, 4.0f, 4.0f, 3.0f, new Dilation(0.0f)),
                                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.6109f, -2.3562f, 0.0f));

                // cube_r6.
                leg2.addChild("cube_r6", ModelPartBuilder.create().uv(48, 46).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 12.0f, 3.0f, new Dilation(-0.5f)).uv(104, 37).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 11.0f, 3.0f, new Dilation(0.0f)),
                                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.3927f, -2.3562f, 0.0f));

                // cube_r7.
                leg2.addChild("cube_r7", ModelPartBuilder.create().uv(0, 144).cuboid(-21.0f, 3.0f, 11.0f, 10.0f, 1.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, -1.5708f, 0.0f));

                // cube_r8.
                leg2.addChild("cube_r8", ModelPartBuilder.create().uv(116, 37).cuboid(-21.0f, 0.99f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, -0.7854f, 0.0f));

                // cube_r9.
                leg2.addChild("cube_r9", ModelPartBuilder.create().uv(116, 44).cuboid(-1.0f, -12.5f, 16.5f, 2.0f, 1.0f, 3.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, -0.6981f, -2.3562f, 0.0f));

                ModelPartData leg3 = modelPartData.addChild("leg3", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 20.0f, 0.0f));

                // cube_r10.
                leg3.addChild("cube_r10", ModelPartBuilder.create().uv(64, 26).cuboid(-2.0f, -2.0f, 18.0f, 4.0f, 4.0f, 3.0f, new Dilation(0.0f)).uv(42, 26).cuboid(-3.0f, -3.0f, 14.0f, 6.0f, 6.0f, 5.0f, new Dilation(0.0f)),
                                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.6109f, 2.3562f, 0.0f));

                // cube_r11.
                leg3.addChild("cube_r11", ModelPartBuilder.create().uv(48, 46).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 12.0f, 3.0f, new Dilation(-0.5f)).uv(104, 37).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 11.0f, 3.0f, new Dilation(0.0f)),
                                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.3927f, 2.3562f, 0.0f));
                // cube_r12.
                leg3.addChild("cube_r12", ModelPartBuilder.create().uv(0, 144).cuboid(-21.0f, 3.0f, 11.0f, 10.0f, 1.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 3.1416f, 0.0f));

                // cube_r13.
                leg3.addChild("cube_r13", ModelPartBuilder.create().uv(116, 37).cuboid(-21.0f, 0.99f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, -2.3562f, 0.0f));

                // cube_r14.
                leg3.addChild("cube_r14", ModelPartBuilder.create().uv(116, 44).cuboid(-1.0f, -12.5f, 16.5f, 2.0f, 1.0f, 3.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, -0.6981f, 2.3562f, 0.0f));

                ModelPartData leg4 = modelPartData.addChild("leg4", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 20.0f, 0.0f));

                // cube_r15.
                leg4.addChild("cube_r15", ModelPartBuilder.create().uv(64, 26).cuboid(-2.0f, -2.0f, 18.0f, 4.0f, 4.0f, 3.0f, new Dilation(0.0f)).uv(42, 26).cuboid(-3.0f, -3.0f, 14.0f, 6.0f, 6.0f, 5.0f, new Dilation(0.0f)),
                                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.6109f, 0.7854f, 0.0f));

                // cube_r16.
                leg4.addChild("cube_r16", ModelPartBuilder.create().uv(48, 46).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 12.0f, 3.0f, new Dilation(-0.5f)).uv(104, 37).cuboid(-1.5f, -2.0f, 15.0f, 3.0f, 11.0f, 3.0f, new Dilation(0.0f)),
                                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.3927f, 0.7854f, 0.0f));

                // cube_r17.
                leg4.addChild("cube_r17", ModelPartBuilder.create().uv(0, 144).cuboid(-21.0f, 3.0f, 11.0f, 10.0f, 1.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 1.5708f, 0.0f));

                // cube_r18.
                leg4.addChild("cube_r18", ModelPartBuilder.create().uv(116, 37).cuboid(-21.0f, 0.99f, -2.0f, 4.0f, 2.0f, 4.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 2.3562f, 0.0f));

                // cube_r19.
                leg4.addChild("cube_r19", ModelPartBuilder.create().uv(116, 44).cuboid(-1.0f, -12.5f, 16.5f, 2.0f, 1.0f, 3.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, -0.6981f, 0.7854f, 0.0f));

                ModelPartData bb_main = modelPartData.addChild("bb_main",
                                ModelPartBuilder.create().uv(155, 59).cuboid(-6.0f, -11.9f, -6.0f, 12.0f, 1.0f, 12.0f, new Dilation(0.0f)).uv(0, 0).cuboid(-12.0f, -16.0f, -12.0f, 24.0f, 2.0f, 24.0f, new Dilation(0.0f)).uv(0, 111)
                                                .cuboid(-5.0f, -19.0f, -8.0f, 10.0f, 3.0f, 10.0f, new Dilation(0.0f)).uv(0, 46).cuboid(-8.0f, -35.0f, -13.0f, 16.0f, 16.0f, 16.0f, new Dilation(0.0f)).uv(0, 124)
                                                .cuboid(8.0f, -30.0f, -8.0f, 2.0f, 10.0f, 10.0f, new Dilation(0.0f)).uv(104, 12).cuboid(-8.995f, -36.0f, -12.0f, 18.0f, 18.0f, 2.0f, new Dilation(0.0f)).uv(155, 24)
                                                .cuboid(3.5f, -46.0f, -15.5f, 1.0f, 13.0f, 1.0f, new Dilation(0.0f)).uv(155, 24).cuboid(-4.5f, -46.0f, -15.5f, 1.0f, 13.0f, 1.0f, new Dilation(0.0f)).uv(159, 24)
                                                .cuboid(1.5f, -41.0f, -15.5f, 1.0f, 13.0f, 1.0f, new Dilation(0.0f)).uv(163, 24).cuboid(-2.5f, -43.0f, -15.5f, 1.0f, 13.0f, 1.0f, new Dilation(0.0f)).uv(167, 24)
                                                .cuboid(-0.5f, -44.0f, -15.5f, 1.0f, 13.0f, 1.0f, new Dilation(0.0f)).uv(104, 0).cuboid(1.0f, -33.0f, 14.0f, 3.0f, 9.0f, 3.0f, new Dilation(0.0f)).uv(104, 0)
                                                .cuboid(-4.0f, -33.0f, 14.0f, 3.0f, 9.0f, 3.0f, new Dilation(0.0f)).uv(155, 39).cuboid(-4.0f, -13.9f, -4.0f, 8.0f, 1.0f, 8.0f, new Dilation(0.0f)).uv(155, 48)
                                                .cuboid(-5.0f, -12.9f, -5.0f, 10.0f, 1.0f, 10.0f, new Dilation(0.0f)).uv(104, 51).cuboid(-6.0f, -25.0f, -14.0f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)).uv(104, 51)
                                                .cuboid(-6.0f, -33.0f, -16.0f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)).uv(104, 51).cuboid(-6.0f, -16.505f, 9.005f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)).uv(24, 134)
                                                .cuboid(10.0f, -28.0f, -5.0f, 1.0f, 6.0f, 4.0f, new Dilation(0.0f)).uv(34, 134).cuboid(10.0f, -27.0f, -6.0f, 1.0f, 4.0f, 6.0f, new Dilation(0.0f)),
                                ModelTransform.pivot(0.0f, 24.0f, 0.0f));

                // cube_r20.
                bb_main.addChild("cube_r20", ModelPartBuilder.create().uv(24, 134).cuboid(9.5f, -10.5f, 1.5f, 1.0f, 6.0f, 4.0f, new Dilation(0.0f)).uv(34, 134).cuboid(9.5f, -9.5f, 0.5f, 1.0f, 4.0f, 6.0f, new Dilation(0.0f)),
                                ModelTransform.of(-0.5f, -17.5f, 0.5f, 0.0f, 3.1416f, 0.0f));

                // cube_r21.
                bb_main.addChild("cube_r21", ModelPartBuilder.create().uv(104, 51).cuboid(-6.0f, -16.505f, 9.005f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 3.1416f, 0.0f));

                // cube_r22.
                bb_main.addChild("cube_r22", ModelPartBuilder.create().uv(104, 51).cuboid(-6.0f, -16.505f, 9.005f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 1.5708f, 0.0f));

                // cube_r23.
                bb_main.addChild("cube_r23", ModelPartBuilder.create().uv(104, 51).cuboid(-6.0f, -16.505f, 9.005f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, -1.5708f, 0.0f));

                // cube_r24.
                bb_main.addChild("cube_r24", ModelPartBuilder.create().uv(104, 51).cuboid(-6.0f, -38.0f, 5.0f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)).uv(104, 51).cuboid(-6.0f, -38.0f, 0.0f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)),
                                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.2182f, 0.0f, 0.0f));

                // cube_r25.
                bb_main.addChild("cube_r25", ModelPartBuilder.create().uv(104, 51).cuboid(-6.0f, -8.0f, 15.0f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)).uv(104, 51).cuboid(-6.0f, -11.0f, 12.0f, 12.0f, 3.0f, 3.0f, new Dilation(0.0f)),
                                ModelTransform.of(0.0f, 0.0f, 0.0f, 0.7854f, 0.0f, 0.0f));

                // cube_r26.
                bb_main.addChild("cube_r26",
                                ModelPartBuilder.create().uv(14, 126).cuboid(-12.5f, -25.5f, -7.5f, 4.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(14, 126).cuboid(-12.5f, -10.5f, -7.5f, 4.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(14, 126)
                                                .cuboid(9.5f, -10.5f, -7.5f, 4.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(14, 126).cuboid(9.5f, -25.5f, -7.5f, 4.0f, 4.0f, 4.0f, new Dilation(0.0f)).uv(0, 92)
                                                .cuboid(-11.5f, -24.5f, -6.5f, 24.0f, 17.0f, 2.0f, new Dilation(0.0f)),
                                ModelTransform.of(-0.5f, -15.5f, 19.5f, 0.2182f, 0.0f, 0.0f));

                // cube_r27.
                bb_main.addChild("cube_r27", ModelPartBuilder.create().uv(104, 32).cuboid(-6.0f, -16.5f, 11.0f, 12.0f, 3.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 2.3562f, 0.0f));

                // cube_r28.
                bb_main.addChild("cube_r28", ModelPartBuilder.create().uv(104, 32).cuboid(-6.0f, -16.5f, 11.0f, 12.0f, 3.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, -2.3562f, 0.0f));

                // cube_r29.
                bb_main.addChild("cube_r29", ModelPartBuilder.create().uv(104, 32).cuboid(-6.0f, -16.5f, 11.0f, 12.0f, 3.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, -0.7854f, 0.0f));

                // cube_r30.
                bb_main.addChild("cube_r30", ModelPartBuilder.create().uv(104, 32).cuboid(-6.0f, -16.5f, 11.0f, 12.0f, 3.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, 0.0f, 0.0f, 0.0f, 0.7854f, 0.0f));

                // cube_r31.
                bb_main.addChild("cube_r31", ModelPartBuilder.create().uv(155, 0).cuboid(-4.5f, -15.5f, -12.5f, 10.0f, 10.0f, 14.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, -17.5f, 15.5f, 0.2182f, 0.0f, 0.0f));

                // cube_r32.
                bb_main.addChild("cube_r32", ModelPartBuilder.create().uv(0, 78).cuboid(-9.5f, -21.5f, -6.5f, 20.0f, 13.0f, 1.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, -15.5f, 23.5f, 0.2182f, 0.0f, 0.0f));

                // cube_r33.
                bb_main.addChild("cube_r33", ModelPartBuilder.create().uv(116, 0).cuboid(-5.0f, -16.5f, -6.5f, 11.0f, 9.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, -17.5f, -3.5f, 0.3927f, 0.0f, 0.0f));

                // cube_r34.
                bb_main.addChild("cube_r34", ModelPartBuilder.create().uv(104, 12).cuboid(-8.5f, -15.5f, 14.5f, 18.0f, 18.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, -17.5f, 1.5f, 1.5708f, 0.0f, 0.0f));

                // cube_r35.
                bb_main.addChild("cube_r35", ModelPartBuilder.create().uv(0, 26).cuboid(-6.5f, -19.5f, -8.5f, 14.0f, 6.0f, 14.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, -17.5f, 0.5f, 0.2182f, 0.0f, 0.0f));

                // cube_r36.
                bb_main.addChild("cube_r36", ModelPartBuilder.create().uv(0, 124).cuboid(-1.5f, -8.5f, -5.5f, 2.0f, 10.0f, 10.0f, new Dilation(0.0f)), ModelTransform.of(-9.5f, -21.5f, -3.5f, 0.0f, 3.1416f, 0.0f));

                return TexturedModelData.of(modelData, 256, 256);
        }

        @Override
        public void setAngles(LanderEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        }

        @Override
        public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
                leg1.render(matrices, vertices, light, overlay);
                leg2.render(matrices, vertices, light, overlay);
                leg3.render(matrices, vertices, light, overlay);
                leg4.render(matrices, vertices, light, overlay);
                bb_main.render(matrices, vertices, light, overlay);
        }
}
