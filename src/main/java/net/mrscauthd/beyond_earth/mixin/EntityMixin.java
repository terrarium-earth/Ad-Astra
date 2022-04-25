package net.mrscauthd.beyond_earth.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "baseTick", at = @At("TAIL"))
    private void baseTick(CallbackInfo info) {
        Entity entity = ((Entity) (Object) this);
        // Teleport the entity to the planet when they fall in the void while in an
        // orbit dimension.
        RegistryKey<World> key = entity.world.getRegistryKey();

        if (entity.getY() < entity.world.getBottomY() && ModUtils.isOrbitDimension(false, key)) {
            ModUtils.teleportToWorld(ModUtils.getPlanetOrbit(false, key), entity);
        }
    }
}
// TODO: check if player is not in a lander entity.
// if (this.getY() < 0 && !(this.getVehicle() instanceof LanderEntity)) {