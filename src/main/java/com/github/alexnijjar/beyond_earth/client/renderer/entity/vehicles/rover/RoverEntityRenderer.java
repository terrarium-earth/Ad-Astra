package com.github.alexnijjar.beyond_earth.client.renderer.entity.vehicles.rover;

import com.github.alexnijjar.beyond_earth.client.renderer.entity.vehicles.VehicleEntityRenderer;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RoverEntity;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RoverEntityRenderer extends VehicleEntityRenderer<RoverEntity, RoverEntityModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/vehicles/rover.png");

    public RoverEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new RoverEntityModel(context.getPart(RoverEntityModel.LAYER_LOCATION)), 0.0f);
    }

    @Override
    public void render(RoverEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(RoverEntity entity) {
        return TEXTURE;
    }

    @Override
    public int getYawOffset() {
        return 180;
    }

    @Override
    public boolean shouldRender(RoverEntity entity, Frustum frustum, double x, double y, double z) {
        return frustum.isVisible(entity.getBoundingBox().expand(4));
    }
}
