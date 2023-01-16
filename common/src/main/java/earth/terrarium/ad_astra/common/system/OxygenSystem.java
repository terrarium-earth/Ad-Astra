package earth.terrarium.ad_astra.common.system;

import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.registry.ModDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OxygenSystem {
    private static final Map<ResourceKey<Level>, Set<BlockPos>> OXYGEN_CACHE = new HashMap<>();

    public static boolean levelHasOxygen(Level level) {
        return PlanetData.getPlanetsWithOxygen().contains(level.dimension());
    }

    public static boolean entityHasOxygen(Entity entity) {
        return posHasOxygen(entity.level, entity.blockPosition());
    }

    public static boolean posHasOxygen(Level level, BlockPos pos) {
        return levelHasOxygen(level) || OXYGEN_CACHE.containsKey(level.dimension()) && OXYGEN_CACHE.get(level.dimension()).contains(pos);
    }

    public static void addOxygenSource(Level level, Set<BlockPos> positions) {
        positions.addAll(OXYGEN_CACHE.getOrDefault(level.dimension(), new HashSet<>()));
        OXYGEN_CACHE.put(level.dimension(), positions);
    }

    public static void livingEntityTick(LivingEntity entity, ServerLevel level) {
        if (level.getGameTime() % 20 != 0) return;
        if (entityHasOxygen(entity)) return;
        entity.hurt(ModDamageSource.OXYGEN, 2);
        entity.setAirSupply(-80);
    }
}
