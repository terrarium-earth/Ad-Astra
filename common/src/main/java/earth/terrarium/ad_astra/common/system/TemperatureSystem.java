package earth.terrarium.ad_astra.common.system;

import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.util.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TemperatureSystem {
    private static final Map<ResourceKey<Level>, Set<BlockPos>> TEMPERATURE_CACHE = new HashMap<>();
    public static final Set<BlockPos> TEMPERATURE_REGULATOR_BLOCKS = new HashSet<>();
    public static final InclusiveRange<Integer> SAFE_TEMPERATURE = new InclusiveRange<>(-50, 50);
    public static final int TARGET_TEMPERATURE = 20;

    public static boolean isSafeTemperature(int temperature) {
        return SAFE_TEMPERATURE.isValueInRange(temperature);
    }

    public static int getLevelTemperature(Level level) {
        return getLevelTemperature(level.dimension());
    }

    public static int getLevelTemperature(ResourceKey<Level> level) {
        return PlanetData.getPlanetTemperatures().get(level);
    }

    public static boolean entitySafeTemperature(Entity entity) {
        return posSafeTemperature(entity.level, entity.blockPosition().above());
    }

    public static boolean posSafeTemperature(Level level, BlockPos pos) {
        return isSafeTemperature(getLevelTemperature(level)) || TEMPERATURE_CACHE.containsKey(level.dimension()) && TEMPERATURE_CACHE.get(level.dimension()).contains(pos);
    }

    public static void addTemperatureSource(Level level, Set<BlockPos> positions) {
        positions.addAll(TEMPERATURE_CACHE.getOrDefault(level.dimension(), new HashSet<>()));
        TEMPERATURE_CACHE.put(level.dimension(), positions);
    }

    public static void removeTemperatureSource(Level level, Set<BlockPos> positions) {
        TEMPERATURE_CACHE.getOrDefault(level.dimension(), new HashSet<>()).removeAll(positions);
    }

    public static void livingEntityTick(LivingEntity entity, ServerLevel level) {
        if (level.getGameTime() % 20 != 0) return;
        if (entitySafeTemperature(entity)) return;
        int temperature = getLevelTemperature(level);
        if (temperature > SAFE_TEMPERATURE.maxInclusive()) {
            entity.hurt(DamageSource.ON_FIRE, 6);
            entity.setSecondsOnFire(10);
        } else if (temperature < SAFE_TEMPERATURE.minInclusive()) {
            entity.hurt(DamageSource.FREEZE, 3);
            entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze() + TARGET_TEMPERATURE, entity.getTicksFrozen() + 5 * 10));
            RandomSource randomSource = entity.level.getRandom();
            ModUtils.spawnServerParticles((level), ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY() + 1, entity.getZ(), 1, Mth.randomBetween(randomSource, -1.0f, 1.0f) * 0.083333336f, 0.05, (double) Mth.randomBetween(randomSource, -1.0f, 1.0f) * 0.083333336, 0);
        }
    }
}
