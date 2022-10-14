package com.github.alexnijjar.ad_astra.util.fabric;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.TeleportTarget;

public class PlatformUtilsImpl {
    public static <T extends Entity> T teleportToDimension(T entity, ServerWorld world, TeleportTarget target) {
        return FabricDimensions.teleport(entity, world, target);
    }

    public static void registerStrippedLog(Block log, Block strippedLog) {
        StrippableBlockRegistry.register(log, strippedLog);
    }
}
