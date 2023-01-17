package earth.terrarium.ad_astra.common.system;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.ad_astra.common.data.PlanetData;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GravitySystem {
    private static final Map<ResourceKey<Level>, Pair<Float, Set<BlockPos>>> GRAVITY_CACHE = new HashMap<>();
    public static final Set<BlockPos> GRAVITY_NORMALIZER_BLOCKS = new HashSet<>();
    public static final float DEFAULT_GRAVITY = 9.806f;

    public static float getLevelGravity(Level level) {
        return getLevelGravity(level.dimension());
    }

    public static float getLevelGravity(ResourceKey<Level> level) {
        return PlanetData.getPlanetGravityValues().get(level);
    }

    public static float getEntityGravity(Entity entity) {
        return getPosGravity(entity.level, entity.blockPosition().above());
    }

    public static float getPosGravity(Level level, BlockPos pos) {
        return (GRAVITY_CACHE.containsKey(level.dimension()) && GRAVITY_CACHE.get(level.dimension()).getSecond().contains(pos) ? GRAVITY_CACHE.get(level.dimension()).getFirst() : getLevelGravity(level)) / DEFAULT_GRAVITY;
    }

    public static void addGravitySource(Level level, Set<BlockPos> positions, float gravity) {
        positions.addAll(GRAVITY_CACHE.getOrDefault(level.dimension(), Pair.of(DEFAULT_GRAVITY, new HashSet<>())).getSecond());
        GRAVITY_CACHE.put(level.dimension(), Pair.of(gravity, positions));
    }

    public static void removeGravitySource(Level level, Set<BlockPos> positions) {
        GRAVITY_CACHE.getOrDefault(level.dimension(), Pair.of(DEFAULT_GRAVITY, new HashSet<>())).getSecond().removeAll(positions);
    }
}
