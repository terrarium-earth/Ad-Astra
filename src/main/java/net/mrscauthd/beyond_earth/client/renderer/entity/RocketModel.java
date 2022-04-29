package net.mrscauthd.beyond_earth.client.renderer.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntity;

@Environment(EnvType.CLIENT)
public class RocketModel extends EntityModel<RocketEntity> {

    // private final ModelPart base;

    @Override
    public void setAngles(RocketEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        // TODO Auto-generated method stub
        // ImmutableList.of(this.base).forEach((modelRenderer) -> {
        //     modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        // });
    }
}
