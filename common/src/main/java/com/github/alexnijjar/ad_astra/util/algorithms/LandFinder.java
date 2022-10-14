package com.github.alexnijjar.ad_astra.util.algorithms;

import java.util.LinkedList;
import java.util.List;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Searches for land (aka. a chunk with blocks in in it).
 * 
 */

public class LandFinder {

    public static Vec3d findNearestLand(World world, Vec3d center, int size) {

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

        List<Vec3d> positions = new LinkedList<>();
        // Check in a square around the center for land
        for (int i = -size * 16 / 2; i < size * 16 / 2; i += 16) {
            for (int j = -size * 16 / 2; j < size * 16 / 2; j += 16) {
                positions.add(new Vec3d(center.getX() + i, center.getY(), center.getZ() + j));
            }
        }

        // sort by distance from center. That way, it will teleport the lander to the nearest land.
        positions.sort((a, b) -> (int) (a.distanceTo(center) - b.distanceTo(center)));

        for (Vec3d pos : positions) {
            for (int y = world.getBottomY(); y < world.getTopY(); y++) {
                if (!world.getBlockState(new BlockPos(pos.getX(), y, pos.getZ())).isAir()) {
                    if (!world.isClient) {
                        AdAstra.LOGGER.info("Moved the lander from: " + center + " To: " + pos);
                    }
                    // return the pos where the land was found
                    return pos;
                }
            }
        }

        // No land was found, return the center
        if (!world.isClient) {
            AdAstra.LOGGER.info("Could not find any land in the area. Spawning the lander in the original position: " + center);
        }
        return center;
    }
}
