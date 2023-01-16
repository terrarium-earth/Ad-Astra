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
import java.util.Map;
import java.util.Set;

public class TemperatureSystem {
    private static final Map<ResourceKey<Level>, Map<BlockPos, Integer>> TEMPERATURE_CACHE = new HashMap<>();
    public static final InclusiveRange<Integer> SAFE_TEMPERATURE = new InclusiveRange<>(-50, 50);
    public static final int TARGET_TEMPERATURE = 20;

    public static boolean isSafeTemperature(int temperature) {
        return SAFE_TEMPERATURE.isValueInRange(temperature);
    }

    public static int getLevelTemperature(Level level) {
        return PlanetData.getPlanetTemperatures().get(level.dimension());
    }

    public static int getEntityTemperature(Entity entity) {
        return getPosTemperature(entity.level, entity.blockPosition());
    }

    public static int getPosTemperature(Level level, BlockPos pos) {
        return TEMPERATURE_CACHE.containsKey(level.dimension()) ? TEMPERATURE_CACHE.get(level.dimension()).getOrDefault(pos, getLevelTemperature(level)) : getLevelTemperature(level);
    }

    public static void addTemperatureSource(Level level, Map<BlockPos, Integer> positions) {
        positions.putAll(TEMPERATURE_CACHE.getOrDefault(level.dimension(), new HashMap<>()));
        TEMPERATURE_CACHE.put(level.dimension(), positions);
    }

    public static void modifyTemperatureSources(Level level, Set<BlockPos> positions, int modifier, int target) {
        Map<BlockPos, Integer> temperatureMap = TEMPERATURE_CACHE.getOrDefault(level.dimension(), new HashMap<>());
        int levelTemperature = getLevelTemperature(level);

        positions.forEach(pos -> {
            if (temperatureMap.containsKey(pos)) {
                if (temperatureMap.get(pos) > target) {
                    temperatureMap.put(pos, Math.max(temperatureMap.get(pos) - modifier, target));
                } else if (temperatureMap.get(pos) < target) {
                    temperatureMap.put(pos, Math.min(temperatureMap.get(pos) + modifier, target));
                }
            } else {
                temperatureMap.put(pos, levelTemperature);
            }
        });

        TEMPERATURE_CACHE.put(level.dimension(), temperatureMap);
    }

    public static void livingEntityTick(LivingEntity entity, ServerLevel level) {
        if (level.getGameTime() % 20 != 0) return;
        int temperature = getEntityTemperature(entity);
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
