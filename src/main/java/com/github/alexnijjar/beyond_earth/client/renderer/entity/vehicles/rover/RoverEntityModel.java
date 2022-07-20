package com.github.alexnijjar.beyond_earth.client.renderer.entity.vehicles.rover;

import com.github.alexnijjar.beyond_earth.client.renderer.entity.vehicles.VehicleEntityModel;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RoverEntity;
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
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class RoverEntityModel extends VehicleEntityModel<RoverEntity> {

        public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(new ModIdentifier("tier_1_rover"), "main");

        public RoverEntityModel(ModelPart root) {
                super(root, "Frame");
        }

        @SuppressWarnings("unused")
        public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData frame = modelPartData.addChild("Frame", ModelPartBuilder.create().uv(0, 0).cuboid(-15.0f, -10.0f, -16.0f, 30.0f, 3.0f, 43.0f, new Dilation(0.0f))
		.uv(88, 64).cuboid(-18.0f, -9.6f, -17.0f, 36.0f, 3.0f, 3.0f, new Dilation(0.0f))
		.uv(88, 64).cuboid(-18.0f, -9.6f, 25.0f, 36.0f, 3.0f, 3.0f, new Dilation(0.0f))
		.uv(0, 46).cuboid(-11.0f, -13.0f, -29.0f, 22.0f, 3.0f, 22.0f, new Dilation(0.0f))
		.uv(66, 53).cuboid(6.0f, -24.0f, -3.0f, 4.0f, 3.0f, 4.0f, new Dilation(0.0f))
		.uv(0, 0).cuboid(7.0f, -22.0f, -17.0f, 8.0f, 12.0f, 12.0f, new Dilation(0.0f))
		.uv(139, 28).cuboid(6.0f, -20.0f, -10.0f, 10.0f, 3.0f, 3.0f, new Dilation(0.0f))
		.uv(139, 28).cuboid(-16.0f, -20.0f, -10.0f, 10.0f, 3.0f, 3.0f, new Dilation(0.0f))
		.uv(0, 0).cuboid(-15.0f, -22.0f, -17.0f, 8.0f, 12.0f, 12.0f, new Dilation(0.0f))
		.uv(103, 0).cuboid(-9.0f, -35.0f, -23.0f, 2.0f, 22.0f, 2.0f, new Dilation(0.0f))
		.uv(103, 24).cuboid(-14.0f, -14.0f, 4.0f, 12.0f, 2.0f, 12.0f, new Dilation(0.0f))
		.uv(32, 24).cuboid(-9.0f, -23.0f, 17.0f, 2.0f, 13.0f, 2.0f, new Dilation(0.0f))
		.uv(0, 71).cuboid(-15.0f, -33.0f, 21.0f, 30.0f, 23.0f, 2.0f, new Dilation(0.0f))
		.uv(66, 46).cuboid(-15.0f, -12.0f, 23.0f, 30.0f, 2.0f, 16.0f, new Dilation(0.0f))
		.uv(64, 71).cuboid(-9.0f, -22.0f, 24.0f, 18.0f, 10.0f, 14.0f, new Dilation(0.0f))
		.uv(0, 96).cuboid(-9.0f, -24.0f, 24.0f, 18.0f, 2.0f, 14.0f, new Dilation(0.0f))
		.uv(32, 24).cuboid(7.0f, -23.0f, 17.0f, 2.0f, 13.0f, 2.0f, new Dilation(0.0f))
		.uv(103, 24).cuboid(2.0f, -14.0f, 4.0f, 12.0f, 2.0f, 12.0f, new Dilation(0.0f)), ModelTransform.pivot(2.0f, 24.0f, -4.0f));

		ModelPartData cube_r1 = frame.addChild("cube_r1", ModelPartBuilder.create().uv(50, 101).cuboid(2.5f, -1.0f, -10.0f, 12.0f, 2.0f, 14.0f, new Dilation(0.0f))
		.uv(50, 101).cuboid(-13.5f, -1.0f, -10.0f, 12.0f, 2.0f, 14.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, -23.0f, 18.0f, 1.2217f, 0.0f, 0.0f));

		ModelPartData cube_r2 = frame.addChild("cube_r2", ModelPartBuilder.create().uv(0, 55).cuboid(-5.0f, -5.0f, -0.5f, 10.0f, 6.0f, 1.0f, new Dilation(0.0f)), ModelTransform.of(8.0f, -21.0f, 1.5f, 0.2182f, 0.0f, 0.0f));

		ModelPartData cube_r3 = frame.addChild("cube_r3", ModelPartBuilder.create().uv(64, 71).cuboid(-1.0f, -3.5f, -3.0f, 2.0f, 12.0f, 2.0f, new Dilation(0.0f)), ModelTransform.of(8.0f, -17.5f, 0.0f, -0.2618f, 0.0f, 0.0f));

		ModelPartData Wheels = frame.addChild("Wheels", ModelPartBuilder.create(), ModelTransform.pivot(-16.0f, 0.0f, 21.0f));

		ModelPartData Wheelframe = Wheels.addChild("Wheelframe", ModelPartBuilder.create(), ModelTransform.pivot(-2.0f, -8.0f, 5.5f));

		ModelPartData bone18 = Wheelframe.addChild("bone18", ModelPartBuilder.create().uv(126, 0).cuboid(-1.5f, -11.9355f, -1.3787f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f))
		.uv(126, 0).cuboid(-1.5f, 17.763f, -31.0772f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f))
		.uv(126, 0).cuboid(33.5f, 17.763f, -31.0772f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f))
		.uv(126, 0).cuboid(33.5f, -11.9355f, -1.3787f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, 0.0f, -4.0f, -0.7854f, 0.0f, 0.0f));

		ModelPartData bone19 = Wheelframe.addChild("bone19", ModelPartBuilder.create().uv(126, 0).cuboid(-1.5f, 6.2787f, -7.0355f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f))
		.uv(126, 0).cuboid(-1.5f, 35.9772f, 22.663f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f))
		.uv(126, 0).cuboid(33.5f, 35.9772f, 22.663f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f))
		.uv(126, 0).cuboid(33.5f, 6.2787f, -7.0355f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, 0.0f, -4.0f, -2.3562f, 0.0f, 0.0f));

		ModelPartData bone20 = Wheelframe.addChild("bone20", ModelPartBuilder.create(), ModelTransform.of(-0.5f, 0.0f, -4.0f, -1.5708f, 0.0f, 0.0f));

		ModelPartData bone21 = Wheelframe.addChild("bone21", ModelPartBuilder.create().uv(126, 0).cuboid(-1.5f, -9.9f, 0.5f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f))
		.uv(126, 0).cuboid(-1.5f, -9.9f, -41.5f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f))
		.uv(126, 0).cuboid(33.5f, -9.9f, -41.5f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f))
		.uv(126, 0).cuboid(33.5f, -9.9f, 0.5f, 5.0f, 0.0f, 7.0f, new Dilation(0.0f)), ModelTransform.pivot(-0.5f, 0.5f, -4.0f));

		ModelPartData Wheel1 = Wheels.addChild("Wheel1", ModelPartBuilder.create(), ModelTransform.pivot(34.0f, -8.0f, -36.5f));

		ModelPartData bone10 = Wheel1.addChild("bone10", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -8.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 4.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-2.5f, 0.0f, 0.0f, -0.7854f, 0.0f, 0.0f));

		ModelPartData bone9 = Wheel1.addChild("bone9", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -8.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 4.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-2.5f, 0.0f, 0.0f, -2.3562f, 0.0f, 0.0f));

		ModelPartData bone8 = Wheel1.addChild("bone8", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -8.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 4.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-2.5f, 0.0f, 0.0f, -1.5708f, 0.0f, 0.0f));

		ModelPartData bone7 = Wheel1.addChild("bone7", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -8.9f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 3.9f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(0, 37).cuboid(1.5f, -5.1f, -4.5f, 0.0f, 9.0f, 9.0f, new Dilation(0.0f)), ModelTransform.pivot(-2.5f, 0.5f, 0.0f));

		ModelPartData Wheel2 = Wheels.addChild("Wheel2", ModelPartBuilder.create(), ModelTransform.pivot(-2.0f, -8.0f, -36.5f));

		ModelPartData bone2 = Wheel2.addChild("bone2", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -8.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 4.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, 0.0f, 0.0f, -0.7854f, 0.0f, 0.0f));

		ModelPartData bone3 = Wheel2.addChild("bone3", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -8.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 4.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, 0.0f, 0.0f, -2.3562f, 0.0f, 0.0f));

		ModelPartData bone4 = Wheel2.addChild("bone4", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -8.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 4.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, 0.0f, 0.0f, -1.5708f, 0.0f, 0.0f));

		ModelPartData bone5 = Wheel2.addChild("bone5", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -8.9f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 3.9f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(0, 37).cuboid(1.5f, -5.1f, -4.5f, 0.0f, 9.0f, 9.0f, new Dilation(0.0f)), ModelTransform.pivot(-0.5f, 0.5f, 0.0f));

		ModelPartData Wheel3 = Wheels.addChild("Wheel3", ModelPartBuilder.create(), ModelTransform.pivot(34.0f, -8.0f, 5.5f));

		ModelPartData bone6 = Wheel3.addChild("bone6", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -11.2284f, -0.6716f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 1.5716f, -0.6716f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-2.5f, 0.0f, -4.0f, -0.7854f, 0.0f, 0.0f));

		ModelPartData bone11 = Wheel3.addChild("bone11", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -11.2284f, -6.3284f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 1.5716f, -6.3284f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-2.5f, 0.0f, -4.0f, -2.3562f, 0.0f, 0.0f));

		ModelPartData bone12 = Wheel3.addChild("bone12", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -12.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 0.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-2.5f, 0.0f, -4.0f, -1.5708f, 0.0f, 0.0f));

		ModelPartData bone13 = Wheel3.addChild("bone13", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -8.9f, 0.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 3.9f, 0.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(0, 37).cuboid(1.5f, -5.1f, -0.5f, 0.0f, 9.0f, 9.0f, new Dilation(0.0f)), ModelTransform.pivot(-2.5f, 0.5f, -4.0f));

		ModelPartData Wheel4 = Wheels.addChild("Wheel4", ModelPartBuilder.create(), ModelTransform.pivot(-2.0f, -8.0f, 5.5f));

		ModelPartData bone14 = Wheel4.addChild("bone14", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -11.2284f, -0.6716f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 1.5716f, -0.6716f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, 0.0f, -4.0f, -0.7854f, 0.0f, 0.0f));

		ModelPartData bone15 = Wheel4.addChild("bone15", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -11.2284f, -6.3284f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 1.5716f, -6.3284f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, 0.0f, -4.0f, -2.3562f, 0.0f, 0.0f));

		ModelPartData bone16 = Wheel4.addChild("bone16", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -12.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 0.4f, -3.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f)), ModelTransform.of(-0.5f, 0.0f, -4.0f, -1.5708f, 0.0f, 0.0f));

		ModelPartData bone17 = Wheel4.addChild("bone17", ModelPartBuilder.create().uv(111, 0).cuboid(-0.5f, -8.9f, 0.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(111, 0).cuboid(-0.5f, 3.9f, 0.5f, 4.0f, 4.0f, 7.0f, new Dilation(0.0f))
		.uv(0, 37).cuboid(1.5f, -5.1f, -0.5f, 0.0f, 9.0f, 9.0f, new Dilation(0.0f)), ModelTransform.pivot(-0.5f, 0.5f, -4.0f));

		ModelPartData Antenna = frame.addChild("Antenna", ModelPartBuilder.create().uv(28, 0).cuboid(10.9319f, -0.6943f, 0.7497f, 1.0f, 3.0f, 3.0f, new Dilation(0.0f))
		.uv(0, 0).cuboid(4.9319f, -1.1943f, 0.2497f, 1.0f, 4.0f, 4.0f, new Dilation(0.0f))
		.uv(0, 40).cuboid(-6.0681f, 0.8057f, 1.7497f, 17.0f, 0.0f, 1.0f, new Dilation(0.0f))
		.uv(66, 46).cuboid(0.9319f, -0.1943f, 0.2497f, 4.0f, 3.0f, 4.0f, new Dilation(0.0f))
		.uv(0, 8).cuboid(4.9319f, -7.1943f, -5.7503f, 0.0f, 16.0f, 16.0f, new Dilation(0.0f)), ModelTransform.of(-12.0f, -35.275f, -22.0f, 0.0f, 0.6545f, -0.3927f));

		ModelPartData cube_r4 = Antenna.addChild("cube_r4", ModelPartBuilder.create().uv(0, 40).cuboid(-8.5f, 0.0f, -0.5f, 17.0f, 0.0f, 1.0f, new Dilation(0.0f)), ModelTransform.of(2.4319f, 0.8057f, 2.2497f, -1.6144f, 0.0f, 0.0f));
		return TexturedModelData.of(modelData, 256, 256);
	}

        @Override
        public void setAngles(RoverEntity entity, float tickDelta, float limbDistance, float animationProgress, float headYaw, float headPitch) {
                super.setAngles(entity, tickDelta, limbDistance, animationProgress, headYaw, headPitch);
                this.frame.pivotX = 0.0f;
                this.frame.pivotY = 24.0f;
                this.frame.pivotZ = 0.0f;

                float wheelPitch = (float) MathHelper.lerp(tickDelta, entity.prevWheelPitch / 3.0, entity.wheelPitch / 3.0);

                ModelPart wheels = this.frame.getChild("Wheels");
                this.frame.getChild("Wheels").getChild("Wheel1").pitch = wheelPitch;
                wheels.getChild("Wheel2").pitch = wheelPitch;
                wheels.getChild("Wheel3").pitch = wheelPitch;
                wheels.getChild("Wheel4").pitch = wheelPitch;

                float speed = MathHelper.sign(entity.getSpeed());
                wheels.getChild("Wheel1").yaw = (float) Math.toRadians(speed * entity.getTurnSpeed() * 5);
                wheels.getChild("Wheel2").yaw = (float) Math.toRadians(speed * entity.getTurnSpeed() * 5);
        }
}
