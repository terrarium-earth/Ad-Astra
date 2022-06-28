package com.github.alexnijjar.beyond_earth.mixin;

import com.github.alexnijjar.beyond_earth.util.ModUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "baseTick", at = @At("TAIL"))
    private void baseTick(CallbackInfo ci) {
        Entity entity = ((Entity) (Object) this);

        // Teleport the entity to the planet when they fall in the void while in an orbit dimension.
        if (entity.getY() < entity.world.getBottomY() && ModUtils.isOrbitWorld(entity.world)) {
            ModUtils.teleportToWorld(ModUtils.getPlanetOrbit(entity.world), entity);
        }
    }
}