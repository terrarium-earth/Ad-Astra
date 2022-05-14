package net.mrscauthd.beyond_earth.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntity;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Inject(method = "update", at = @At("TAIL"), cancellable = true)
    public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        CameraInvoker camera = (CameraInvoker) ((Camera) (Object) this);
        if (thirdPerson && focusedEntity.getVehicle() instanceof RocketEntity) {
            camera.invokeMoveBy(-camera.invokeClipToSpace(12.0), 0.0, 0.0);
        }
    }
}