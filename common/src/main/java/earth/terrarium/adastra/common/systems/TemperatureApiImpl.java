package earth.terrarium.adastra.common.systems;

import earth.terrarium.adastra.api.events.AdAstraEvents;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.handlers.PlanetHandler;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import earth.terrarium.adastra.common.planets.Planet;
import earth.terrarium.adastra.common.tags.ModEntityTypeTags;
import earth.terrarium.adastra.common.tags.ModItemTags;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.Optionull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Collection;

public class TemperatureApiImpl implements TemperatureApi {
    @Override
    public short getTemperature(Level level) {
        return getTemperature(level.dimension());
    }

    @Override
    public short getTemperature(ResourceKey<Level> level) {
        return Optionull.mapOrDefault(PlanetApi.API.getPlanet(level), Planet::temperature, PlanetConstants.EARTH_TEMPERATURE);
    }

    @Override
    public short getTemperature(Level level, BlockPos pos) {
        if (level.isClientSide()) return getTemperature(level);
        return PlanetHandler.getTemperature((ServerLevel) level, pos);
    }

    @Override
    public short getTemperature(Entity entity) {
        return getTemperature(entity.level(), BlockPos.containing(entity.getX(), entity.getEyeY(), entity.getZ()));
    }

    @Override
    public void setTemperature(Level level, BlockPos pos, short temperature) {
        if (level.isClientSide()) return;
        PlanetHandler.setTemperature((ServerLevel) level, pos, temperature);
    }

    @Override
    public void setTemperature(Level level, Collection<BlockPos> positions, short temperature) {
        if (level.isClientSide()) return;
        PlanetHandler.setTemperature((ServerLevel) level, positions, temperature);
    }

    @Override
    public void removeTemperature(Level level, BlockPos pos) {
        setTemperature(level, pos, getTemperature(level));
    }

    @Override
    public void removeTemperature(Level level, Collection<BlockPos> positions) {
        setTemperature(level, positions, getTemperature(level));
    }

    @Override
    public boolean isLiveable(Level level, BlockPos pos) {
        short temperature = getTemperature(level, pos);
        return temperature >= PlanetConstants.MIN_LIVEABLE_TEMPERATURE && temperature <= PlanetConstants.MAX_LIVEABLE_TEMPERATURE;
    }

    @Override
    public boolean isHot(Level level, BlockPos pos) {
        return getTemperature(level, pos) > PlanetConstants.MAX_LIVEABLE_TEMPERATURE;
    }

    @Override
    public boolean isCold(Level level, BlockPos pos) {
        return getTemperature(level, pos) < PlanetConstants.MIN_LIVEABLE_TEMPERATURE;
    }

    @Override
    public void entityTick(ServerLevel level, LivingEntity entity) {
        if (AdAstraConfig.disableTemperature) return;
        if (entity.getType().is(ModEntityTypeTags.CAN_SURVIVE_IN_SPACE)) return;
        if (this.isHot(level, entity.blockPosition())) {
            if (entity.getType().is(ModEntityTypeTags.CAN_SURVIVE_EXTREME_HEAT)) return;
            if (SpaceSuitItem.hasFullSet(entity, ModItemTags.HEAT_RESISTANT_ARMOR)) return;
            if (entity.hasEffect(MobEffects.FIRE_RESISTANCE)) return;
            if (AdAstraEvents.HotTemperatureTickEvent.post(level, entity)) {
                burnEntity(entity);
            }
        } else if (this.isCold(level, entity.blockPosition())) {
            if (entity.getType().is(ModEntityTypeTags.CAN_SURVIVE_EXTREME_COLD)) return;
            if (SpaceSuitItem.hasFullSet(entity, ModItemTags.FREEZE_RESISTANT_ARMOR)) return;
            if (AdAstraEvents.ColdTemperatureTickEvent.post(level, entity)) {
                freezeEntity(entity, level);
            }
        }
    }

    private void burnEntity(LivingEntity entity) {
        entity.hurt(entity.damageSources().onFire(), 6);
        entity.setSecondsOnFire(10);
    }

    private void freezeEntity(LivingEntity entity, ServerLevel level) {
        if (entity.getType().is(ModEntityTypeTags.CAN_SURVIVE_EXTREME_COLD)) return;
        if (SpaceSuitItem.hasFullSet(entity, ModItemTags.FREEZE_RESISTANT_ARMOR)) return;
        if (AdAstraEvents.ColdTemperatureTickEvent.post(level, entity)) {
            entity.hurt(entity.damageSources().freeze(), 3);
            entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze() + 20, entity.getTicksFrozen() + 5 * 10));
            ModUtils.sendParticles(level,
                ParticleTypes.SNOWFLAKE,
                entity.getX(),
                entity.getY() + 1,
                entity.getZ(), 1,
                Mth.randomBetween(level.random, -1.0f, 1.0f) * 0.085f,
                0.05,
                Mth.randomBetween(level.random,
                    -1.0f,
                    1.0f) * 0.085,
                0);
        }
    }
}
