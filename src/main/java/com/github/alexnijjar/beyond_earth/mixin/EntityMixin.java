package com.github.alexnijjar.beyond_earth.mixin;

import com.github.alexnijjar.beyond_earth.util.ModUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "baseTick", at = @At("TAIL"))
    private void baseTick(CallbackInfo info) {
        Entity entity = ((Entity) (Object) this);
        // Teleport the entity to the planet when they fall in the void while in an
        // orbit dimension.
        RegistryKey<World> key = entity.world.getRegistryKey();

        if (entity.getY() < entity.world.getBottomY() && ModUtils.isOrbitDimension(false, key)) {
            ModUtils.teleportToWorld(ModUtils.getPlanetOrbit(false, key), entity, true);
        }
    }
}