package com.github.alexnijjar.beyond_earth.client.renderer.entity.vehicles;

import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class VehicleEntityModel<T extends VehicleEntity> extends EntityModel<T> {

    protected final ModelPart frame;

    public VehicleEntityModel(ModelPart root, String child) {
        this.frame = root.getChild(child);
    }

    @Override
    public void setAngles(T entity, float tickDelta, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float newYaw = MathHelper.lerp(tickDelta, entity.previousYaw, entity.getYaw());
        this.frame.yaw = (float) Math.toRadians(newYaw);
        this.frame.roll = 0.0f;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        frame.render(matrices, vertices, light, overlay);
    }
}
