package net.mrscauthd.beyond_earth.client.renderer.entity.vehicles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntity;

@Environment(EnvType.CLIENT)
public class RocketEntityRenderer extends EntityRenderer<RocketEntity> {

    public RocketEntityRenderer(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Identifier getTexture(RocketEntity entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void render(RocketEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

    }
}
