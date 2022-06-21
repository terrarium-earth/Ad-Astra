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

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData frame = modelPartData.addChild("Frame", ModelPartBuilder.create().uv(0, 0).cuboid(-17.5f, -10.5f, -52.5f, 35.0f, 3.0f, 73.0f, new Dilation(0.0f)).uv(0, 31).cuboid(12.5f, -15.0f, -37.5f, 0.0f, 5.0f, 25.0f, new Dilation(0.0f)).uv(0, 26)
                .cuboid(-12.5f, -15.0f, -37.5f, 0.0f, 5.0f, 25.0f, new Dilation(0.0f)).uv(56, 108).cuboid(-17.5f, -8.0f, 12.5f, 35.0f, 3.0f, 3.0f, new Dilation(0.0f)).uv(56, 102).cuboid(-17.5f, -8.0f, -47.5f, 35.0f, 3.0f, 3.0f, new Dilation(0.0f))
                .uv(0, 130).cuboid(-12.5f, -13.0f, -55.0f, 10.0f, 3.0f, 13.0f, new Dilation(0.0f)).uv(32, 64).cuboid(-7.5f, -13.0f, -57.5f, 15.0f, 3.0f, 3.0f, new Dilation(0.0f)).uv(0, 13).cuboid(-3.0f, -13.0f, -55.0f, 8.0f, 3.0f, 3.0f, new Dilation(0.0f))
                .uv(12, 66).cuboid(-10.5f, -15.5f, -52.5f, 3.0f, 3.0f, 3.0f, new Dilation(0.0f)).uv(0, 66).cuboid(-10.5f, -23.0f, -52.5f, 3.0f, 3.0f, 3.0f, new Dilation(0.0f)).uv(46, 137).cuboid(7.0f, -50.5f, -55.0f, 3.0f, 43.0f, 3.0f, new Dilation(0.0f))
                .uv(138, 127).cuboid(-13.0f, -20.0f, -55.0f, 8.0f, 5.0f, 8.0f, new Dilation(0.0f)).uv(136, 76).cuboid(-13.0f, -27.5f, -55.0f, 8.0f, 5.0f, 8.0f, new Dilation(0.0f)).uv(0, 102)
                .cuboid(-12.5f, -35.0f, 17.5f, 25.0f, 25.0f, 3.0f, new Dilation(0.0f)).uv(0, 76).cuboid(-15.0f, -13.0f, -12.5f, 30.0f, 3.0f, 23.0f, new Dilation(0.0f)).uv(83, 76).cuboid(-10.0f, -20.0f, -42.5f, 20.0f, 10.0f, 13.0f, new Dilation(0.0f))
                .uv(0, 76).cuboid(17.5f, -15.625f, -49.875f, 5.0f, 0.0f, 13.0f, new Dilation(0.0f)).uv(0, 0).cuboid(-22.5f, -15.625f, -49.775f, 5.0f, 0.0f, 13.0f, new Dilation(0.0f)).uv(47, 51)
                .cuboid(17.5f, -15.625f, 10.125f, 5.0f, 0.0f, 13.0f, new Dilation(0.0f)).uv(37, 51).cuboid(-22.5f, -15.625f, 10.625f, 5.0f, 0.0f, 13.0f, new Dilation(0.0f)).uv(56, 114)
                .cuboid(17.5f, -15.625f, -36.875f, 5.0f, 13.0f, 0.0f, new Dilation(0.0f)).uv(0, 0).cuboid(-22.5f, -15.625f, -36.875f, 5.0f, 13.0f, 0.0f, new Dilation(0.0f)).uv(83, 76).cuboid(17.5f, -15.625f, 23.125f, 5.0f, 13.0f, 0.0f, new Dilation(0.0f))
                .uv(0, 76).cuboid(-22.5f, -15.625f, 23.125f, 5.0f, 13.0f, 0.0f, new Dilation(0.0f)), ModelTransform.pivot(0.0f, 24.0f, 0.0f));

        // cube_r1.
        frame.addChild("cube_r1", ModelPartBuilder.create().uv(0, 23).cuboid(-15.0f, -22.5f, -2.5f, 30.0f, 25.0f, 3.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -12.1737f, 12.4786f, -0.1309f, 0.0f, 0.0f));

        // cube_r2.
        frame.addChild("cube_r2", ModelPartBuilder.create().uv(73, 140).cuboid(-2.5f, -15.0f, 0.05f, 15.0f, 15.0f, 0.0f, new Dilation(0.0f)).uv(58, 140).cuboid(-2.5f, -15.0f, 0.0f, 15.0f, 15.0f, 0.0f, new Dilation(0.0f)),
                ModelTransform.of(0.0f, -10.0f, -52.5f, -0.2182f, 0.0f, 0.0f));

        // cube_r3.
        frame.addChild("cube_r3", ModelPartBuilder.create().uv(0, 61).cuboid(-7.5f, 0.0f, -5.0f, 15.0f, 0.0f, 5.0f, new Dilation(0.0f)), ModelTransform.of(0.0f, -12.4979f, -57.4987f, 0.5672f, 0.0f, 0.0f));

        // Wheel1.
        frame.addChild("Wheel1", ModelPartBuilder.create().uv(115, 127).cuboid(-2.5f, -6.5f, -6.5f, 5.0f, 13.0f, 13.0f, new Dilation(0.0f)), ModelTransform.pivot(20.0f, -6.5f, -46.0f));

        // Wheel2.
        frame.addChild("Wheel2", ModelPartBuilder.create().uv(119, 101).cuboid(-2.5f, -6.5f, -6.5f, 5.0f, 13.0f, 13.0f, new Dilation(0.0f)), ModelTransform.pivot(20.0f, -6.5f, 14.0f));

        // Wheel3
        frame.addChild("Wheel3", ModelPartBuilder.create().uv(56, 114).cuboid(-2.5f, -6.5f, -6.5f, 5.0f, 13.0f, 13.0f, new Dilation(0.0f)), ModelTransform.pivot(-20.0f, -6.5f, -46.0f));

        // Wheel4.
        frame.addChild("Wheel4", ModelPartBuilder.create().uv(92, 114).cuboid(-2.5f, -6.5f, -6.5f, 5.0f, 13.0f, 13.0f, new Dilation(0.0f)), ModelTransform.pivot(-20.0f, -6.5f, 14.0f));

        ModelPartData sat = frame.addChild("sat", ModelPartBuilder.create(), ModelTransform.pivot(8.645f, -49.3111f, -53.75f));

        // cube_r4.
        sat.addChild("cube_r4", ModelPartBuilder.create().uv(0, 0).cuboid(-11.75f, 0.375f, -11.25f, 23.0f, 0.0f, 23.0f, new Dilation(0.0f)), ModelTransform.of(0.105f, -0.6889f, 0.0f, 0.2618f, 0.0f, 0.0f));

        // cube_r5.
        sat.addChild("cube_r5", ModelPartBuilder.create().uv(79, 114).cuboid(-1.5399f, -3.8778f, -1.3274f, 3.0f, 8.0f, 3.0f, new Dilation(0.0f)), ModelTransform.of(-0.105f, -4.3111f, -0.9476f, 0.2618f, 0.0f, 0.0f));

        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(RoverEntity entity, float tickDelta, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float newYaw = MathHelper.lerp(tickDelta, entity.prevRoverYaw, entity.getYaw());
        this.frame.yaw = (float) Math.toRadians(newYaw);
        this.frame.roll = 0.0f;

        float wheelPitch = (float) MathHelper.lerp(tickDelta, entity.prevWheelPitch / 3.0, entity.wheelPitch / 3.0);
        float antennaDishYaw = (float) MathHelper.lerp(tickDelta, entity.antennaDishYaw - 0.1, entity.antennaDishYaw);

        frame.getChild("Wheel1").pitch = wheelPitch;
        frame.getChild("Wheel2").pitch = wheelPitch;
        frame.getChild("Wheel3").pitch = wheelPitch;
        frame.getChild("Wheel4").pitch = wheelPitch;

        frame.getChild("Wheel1").yaw = entity.getTurnSpeed() > 0 ? 5 : -5;
        frame.getChild("Wheel3").yaw = entity.getTurnSpeed() > 0 ? 5 : -5;

        float turnSpeed = entity.getTurnSpeed();
        float turnYaw = (float) Math.toRadians(turnSpeed * 5.0f);
        frame.getChild("Wheel1").yaw = turnYaw;
        frame.getChild("Wheel3").yaw = turnYaw;

        this.frame.getChild("sat").yaw = antennaDishYaw;
    }
}
