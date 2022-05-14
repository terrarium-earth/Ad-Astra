package net.mrscauthd.beyond_earth.client.renderer.entity.vehicles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.mrscauthd.beyond_earth.entities.vehicles.VehicleEntity;

@Environment(EnvType.CLIENT)
public class RocketEntityModel<T extends VehicleEntity> extends EntityModel<T> {

    private final ModelPart rocket;

    public RocketEntityModel(ModelPart root) {
        this.rocket = root.getChild("rocket");
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.rocket.yaw = headYaw / (180f / (float) Math.PI);
        this.rocket.roll = (float) entity.getClientYaw();
        this.rocket.pitch = (float) entity.getClientPitch();
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        rocket.render(matrices, vertices, light, overlay);
    }
}
