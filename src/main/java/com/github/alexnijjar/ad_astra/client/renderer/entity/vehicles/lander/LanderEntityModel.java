package com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.lander;

import com.github.alexnijjar.ad_astra.entities.vehicles.LanderEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class LanderEntityModel extends EntityModel<LanderEntity> {

	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("lander"), "main");
	private final ModelPart body;

	public LanderEntityModel(ModelPart root) {
		this.body = root.getChild("body");
	}

	@SuppressWarnings("unused")
    public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData body = partdefinition.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 20.0F, 0.0F));

        ModelPartData raft = body.addChild("raft", ModelPartBuilder.create(), ModelTransform.pivot(-7.0F, 1.5F, -13.0F));

        ModelPartData cube_r1 = raft.addChild("cube_r1", ModelPartBuilder.create().uv(0, 42).cuboid(-6.0F, -3.5F, -5.0F, 12.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, -0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r2 = raft.addChild("cube_r2", ModelPartBuilder.create().uv(0, 42).cuboid(-6.0F, -75.5F, 8.0F, 12.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 71.5F, 13.0F, 0.0F, 0.0F, 0.0F));

        ModelPartData cube_r3 = raft.addChild("cube_r3", ModelPartBuilder.create().uv(0, 59).cuboid(-9.0F, -3.0F, -5.0F, 18.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(-0.8839F, -0.5F, 5.1265F, 0.0F, -2.3562F, 0.0F));

        ModelPartData cube_r4 = raft.addChild("cube_r4", ModelPartBuilder.create().uv(0, 42).mirrored().cuboid(-6.0F, -4.0F, 8.0F, 12.0F, 7.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(7.0F, 0.0F, 13.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData cube_r5 = raft.addChild("cube_r5", ModelPartBuilder.create().uv(0, 59).mirrored().cuboid(-9.0F, -3.0F, -5.0F, 18.0F, 6.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(14.8839F, -0.5F, 5.1265F, 0.0F, 2.3562F, 0.0F));

        ModelPartData cube_r6 = raft.addChild("cube_r6", ModelPartBuilder.create().uv(0, 59).mirrored().cuboid(-4.75F, -3.5F, -8.0F, 18.0F, 6.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(14.0F, 0.0F, 26.0F, 0.0F, 0.7854F, 0.0F));

        ModelPartData cube_r7 = raft.addChild("cube_r7", ModelPartBuilder.create().uv(0, 59).cuboid(-13.25F, -3.5F, -8.0F, 18.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 26.0F, 0.0F, -0.7854F, 0.0F));

        ModelPartData cube_r8 = raft.addChild("cube_r8", ModelPartBuilder.create().uv(0, 42).cuboid(-6.0F, -4.0F, 8.0F, 12.0F, 7.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 0.0F, 13.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData main = body.addChild("main", ModelPartBuilder.create().uv(74, 9).cuboid(-9.0F, -73.0F, -9.0F, 18.0F, 0.0F, 18.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 73.0F, 0.0F));

        ModelPartData fins = main.addChild("fins", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, -8.0F, -2.0F));

        ModelPartData pyramid = main.addChild("pyramid", ModelPartBuilder.create().uv(75, -10).cuboid(-6.0F, -77.5F, -5.0F, 0.0F, 9.0F, 10.0F, new Dilation(0.0F))
                .uv(75, -10).cuboid(6.0F, -77.5F, -5.0F, 0.0F, 9.0F, 10.0F, new Dilation(0.0F))
                .uv(88, 11).cuboid(10.0F, -58.0F, -10.0F, 0.0F, 2.0F, 20.0F, new Dilation(0.0F))
                .uv(88, 11).cuboid(-10.0F, -58.0F, -10.0F, 0.0F, 2.0F, 20.0F, new Dilation(0.0F))
                .uv(88, 31).cuboid(-10.0F, -58.0F, 10.0F, 20.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(88, 31).cuboid(-10.0F, -58.0F, -10.0F, 20.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -17.0F, 0.0F));

        ModelPartData cube_r9 = pyramid.addChild("cube_r9", ModelPartBuilder.create().uv(39, 0).cuboid(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -93.0F, 0.0F, -0.3491F, 0.7854F, 0.0F));

        ModelPartData cube_r10 = pyramid.addChild("cube_r10", ModelPartBuilder.create().uv(0, 12).cuboid(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -66.0F, 0.0F, -2.8798F, -1.5708F, 3.1416F));

        ModelPartData cube_r11 = pyramid.addChild("cube_r11", ModelPartBuilder.create().uv(39, 0).cuboid(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -93.0F, 0.0F, -0.3491F, 2.3562F, 0.0F));

        ModelPartData cube_r12 = pyramid.addChild("cube_r12", ModelPartBuilder.create().uv(0, 12).cuboid(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -66.0F, 0.0F, -2.8798F, 3.1416F, 3.1416F));

        ModelPartData cube_r13 = pyramid.addChild("cube_r13", ModelPartBuilder.create().uv(39, 0).cuboid(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -93.0F, 0.0F, -0.3491F, -2.3562F, 0.0F));

        ModelPartData cube_r14 = pyramid.addChild("cube_r14", ModelPartBuilder.create().uv(0, 12).cuboid(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -66.0F, 0.0F, -2.8798F, 1.5708F, 3.1416F));

        ModelPartData cube_r15 = pyramid.addChild("cube_r15", ModelPartBuilder.create().uv(39, 0).cuboid(-1.0F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(6.3647F, -77.5491F, 4.9353F, 0.0F, -0.7854F, 0.0F));

        ModelPartData cube_r16 = pyramid.addChild("cube_r16", ModelPartBuilder.create().uv(39, 0).cuboid(-1.0F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(6.3647F, -77.5491F, -6.3647F, 0.0F, -0.7854F, 0.0F));

        ModelPartData cube_r17 = pyramid.addChild("cube_r17", ModelPartBuilder.create().uv(39, 0).cuboid(-1.02F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.9353F, -77.5491F, -6.3647F, 0.0F, -0.7854F, 0.0F));

        ModelPartData cube_r18 = pyramid.addChild("cube_r18", ModelPartBuilder.create().uv(75, -10).cuboid(-6.0F, -4.5F, -5.0F, 0.0F, 9.0F, 10.0F, new Dilation(0.0F))
                .uv(75, -10).cuboid(6.0F, -4.5F, -5.0F, 0.0F, 9.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -73.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData cube_r19 = pyramid.addChild("cube_r19", ModelPartBuilder.create().uv(39, 0).cuboid(-1.02F, 0.01F, 0.0027F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.9353F, -77.5491F, 4.9353F, 0.0F, -0.7854F, 0.0F));

        ModelPartData cube_r20 = pyramid.addChild("cube_r20", ModelPartBuilder.create().uv(39, 0).cuboid(-1.0F, 2.6076F, -3.171F, 2.0F, 15.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -93.0F, 0.0F, -0.3491F, -0.7854F, 0.0F));

        ModelPartData cube_r21 = pyramid.addChild("cube_r21", ModelPartBuilder.create().uv(0, 12).cuboid(-8.0F, -21.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -66.0F, 0.0F, -2.8798F, 0.0F, 3.1416F));

        ModelPartData cube_r22 = pyramid.addChild("cube_r22", ModelPartBuilder.create().uv(39, 0).cuboid(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -84.0F, 0.0F, -0.3491F, -2.3562F, 0.0F));

        ModelPartData cube_r23 = pyramid.addChild("cube_r23", ModelPartBuilder.create().uv(39, 0).cuboid(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -84.0F, 0.0F, -0.3491F, 2.3562F, 0.0F));

        ModelPartData cube_r24 = pyramid.addChild("cube_r24", ModelPartBuilder.create().uv(39, 0).cuboid(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -84.0F, 0.0F, -0.3491F, -0.7854F, 0.0F));

        ModelPartData cube_r25 = pyramid.addChild("cube_r25", ModelPartBuilder.create().uv(39, 0).cuboid(-1.0F, 17.6076F, -3.171F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -84.0F, 0.0F, -0.3491F, 0.7854F, 0.0F));

        ModelPartData cube_r26 = pyramid.addChild("cube_r26", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -57.0F, 0.0F, -2.8798F, -1.5708F, 3.1416F));

        ModelPartData cube_r27 = pyramid.addChild("cube_r27", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -57.0F, 0.0F, -2.8798F, 1.5708F, 3.1416F));

        ModelPartData cube_r28 = pyramid.addChild("cube_r28", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -57.0F, 0.0F, -2.8798F, 3.1416F, 3.1416F));

        ModelPartData cube_r29 = pyramid.addChild("cube_r29", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -9.5488F, 8.7536F, 16.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -57.0F, 0.0F, -2.8798F, 0.0F, 3.1416F));

        ModelPartData booster = main.addChild("booster", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData tip = main.addChild("tip", ModelPartBuilder.create().uv(0, 24).cuboid(-4.0F, -110.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(49, 0).cuboid(-3.0F, -118.0F, -3.0F, 6.0F, 8.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 128, 128);
    }

	@Override
	public void setAngles(LanderEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertices, light, overlay);
	}
}
