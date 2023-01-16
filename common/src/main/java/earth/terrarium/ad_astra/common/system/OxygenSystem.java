package earth.terrarium.ad_astra.common.system;

import earth.terrarium.ad_astra.common.data.PlanetData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class OxygenSystem {
    private static final Map<ResourceKey<Level>, Set<BlockPos>> OXYGEN_CACHE = new HashMap<>();

    public static Map<ResourceKey<Level>, Set<BlockPos>> getOxygenCache() {
        return OXYGEN_CACHE;
    }

    public static boolean levelHasOxygen(Level level) {
        return PlanetData.getPlanetsWithOxygen().contains(level.dimension());
    }
}
