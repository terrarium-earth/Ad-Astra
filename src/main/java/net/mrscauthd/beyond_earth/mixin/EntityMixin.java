package net.mrscauthd.beyond_earth.mixin;


import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.util.ModUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "baseTick", at = @At("TAIL"))
    private void baseTick(CallbackInfo info) {
        Entity entity = ((Entity) (Object) this);
        // Teleport the entity to the planet when they fall in the void while in an orbit dimension.
        RegistryKey<World> key = entity.world.getRegistryKey();

        if (entity.getY() < entity.world.getBottomY() && ModUtils.isOrbitDimension(key)) {
            ModUtils.teleportToPlanet(key, entity);
        }
    }

    // Set a custom teleport target when falling into an orbit void.
    @Inject(method = "getTeleportTarget", at = @At("HEAD"), cancellable = true)
    private void getTeleportTarget(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> info) {
        if (ModUtils.isPlanet(destination.getRegistryKey())) {
            Entity entity = ((Entity) (Object) this);

            if (ModUtils.isOrbitDimension(entity.getWorld().getRegistryKey())) {
                Vec3d newPos = new Vec3d(entity.getX(), ModUtils.SPAWN_START, entity.getZ());
                info.setReturnValue(new TeleportTarget(newPos, entity.getVelocity(), entity.getYaw(), entity.getPitch()));
            }
        }
    }
}
// TODO: check if player is not in a lander entity.
//        if (this.getY() < 0 && !(this.getVehicle() instanceof LanderEntity)) {