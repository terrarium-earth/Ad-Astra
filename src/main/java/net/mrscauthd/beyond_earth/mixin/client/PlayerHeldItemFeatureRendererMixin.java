package net.mrscauthd.beyond_earth.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.mrscauthd.beyond_earth.entities.vehicles.LanderEntity;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntity;

@Mixin(PlayerHeldItemFeatureRenderer.class)
public class PlayerHeldItemFeatureRendererMixin {
    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    public void renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
        if (entity.getVehicle() instanceof RocketEntity || entity.getVehicle() instanceof LanderEntity) {
            info.cancel();
        }
    }
}
