package earth.terrarium.ad_astra.common.util.algorithm;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.util.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.LinkedList;
import java.util.List;

/**
 * Searches for land (aka. a chunk with blocks in it).
 */

public class LandFinder {

    public static Vec3 findNearestLand(Level level, Vec3 center, int size) {

        // Don't search for land in orbit
        if (ModUtils.isOrbitlevel(level)) {
            return center;
        }

        // Don't search for land if the center is already land
        for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
            if (!level.getBlockState(new BlockPos(center.x(), y, center.z())).isAir()) {
                return center;
            }
        }

        List<Vec3> positions = new LinkedList<>();
        // Check in a square around the center for land
        for (int i = -size * 16 / 2; i < size * 16 / 2; i += 16) {
            for (int j = -size * 16 / 2; j < size * 16 / 2; j += 16) {
                positions.add(new Vec3(center.x() + i, center.y(), center.z() + j));
            }
        }

        // sort by distance from center. That way, it will teleport the lander to the nearest land.
        positions.sort((a, b) -> (int) (a.distanceTo(center) - b.distanceTo(center)));

        for (Vec3 pos : positions) {
            for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
                if (!level.getBlockState(new BlockPos(pos.x(), y, pos.z())).isAir()) {
                    if (!level.isClientSide) {
                        AdAstra.LOGGER.info("Moved the lander from: " + center + " To: " + pos);
                    }
                    // return the pos where the land was found
                    return pos;
                }
            }
        }

        // No land was found, return the center
        if (!level.isClientSide) {
            AdAstra.LOGGER.info("Could not find any land in the area. Spawning the lander in the original position: " + center);
        }
        return center;
    }
}
