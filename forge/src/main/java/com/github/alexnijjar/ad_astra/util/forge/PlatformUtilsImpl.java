package com.github.alexnijjar.ad_astra.util.forge;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.TeleportTarget;

public class PlatformUtilsImpl {
    @SuppressWarnings("unchecked")
    public static <T extends Entity> T teleportToDimension(T entity, ServerWorld world, TeleportTarget target) {
        return (T)entity.changeDimension(world, new AdAstraTeleporter(target));
    }
}
