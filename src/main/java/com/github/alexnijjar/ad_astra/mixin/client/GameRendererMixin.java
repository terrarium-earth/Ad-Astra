package com.github.alexnijjar.beyond_earth.mixin.client;

import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    // Shake the player camera when inside of a vehicle.
    @Inject(at = @At(value = "HEAD"), method = "bobView", cancellable = true)
    public void bobView(MatrixStack matrices, float tickDelta, CallbackInfo ci) {

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.getCameraEntity() instanceof ClientPlayerEntity player) {
            if (player.getVehicle() instanceof RocketEntity entity) {
                if (entity.getPhase() != 3) {
                    if (entity.isFlying()) {
                        ci.cancel();

                        float f = player.horizontalSpeed - player.prevHorizontalSpeed;
                        float g = -(player.horizontalSpeed + f * tickDelta);
                        float h = MathHelper.lerp(tickDelta, 0.075f, -0.075f);
                        matrices.translate(MathHelper.sin(g * (float) Math.PI) * h * 0.5f, -Math.abs(MathHelper.cos(g * (float) Math.PI) * h), 0.0);
                        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.sin(g * (float) Math.PI) * h * 3.0f));
                        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(Math.abs(MathHelper.cos(g * (float) Math.PI - 0.2f) * h) * 5.0f));
                    }
                }
            }
        }
    }
}