package com.github.alexnijjar.ad_astra.util.algorithms;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

/**
 * Searches for land (aka. a chunk with blocks in in it).
 * 
 */

public class LandFinder {

    public static BlockPos findNearestLand(ServerWorld world, BlockPos center, int size) {

        // Don't search for land in orbit
        if (ModUtils.isOrbitWorld(world)) {
            return center;
        }
        
        // Don't search for land if the center is already land
        for (int y = world.getBottomY(); y < world.getTopY(); y++) {
            if (!world.getBlockState(new BlockPos(center.getX(), y, center.getZ())).isAir()) {
                return center;
            }
        }

        // Check in a square around the center for land
        for (int i = -size * 16 / 2; i < size * 16 / 2; i += 16) {
            for (int j = -size * 16 / 2; j < size * 16 / 2; j += 16) {
                BlockPos chunkPos = new BlockPos(center.getX() + i, center.getY(), center.getZ() + j);

                for (int y = world.getBottomY(); y < world.getTopY(); y++) {
                    if (!world.getBlockState(new BlockPos(chunkPos.getX(), y, chunkPos.getZ())).isAir()) {
                        AdAstra.LOGGER.info("Moved the entity from: " + center + " To: " + chunkPos.toString());
                        // return the pos where the land was found
                        return new BlockPos(chunkPos.getX(), center.getY(), chunkPos.getZ());
                    }
                }
            }
        }

        // No land was found, return the center
        AdAstra.LOGGER.info("Could not find any land in the area. Using the original position: " + center);
        return center;
    }
}
