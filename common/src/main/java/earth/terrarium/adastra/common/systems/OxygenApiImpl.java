package earth.terrarium.adastra.common.systems;

import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.common.handlers.PlanetHandler;
import earth.terrarium.adastra.common.planets.Planet;
import earth.terrarium.adastra.common.planets.PlanetData;
import earth.terrarium.adastra.common.registry.ModDamageSources;
import net.minecraft.Optionull;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class OxygenApiImpl implements OxygenApi {
    @Override
    public boolean hasOxygen(Level level) {
        return hasOxygen(level.dimension());
    }

    @Override
    public boolean hasOxygen(ResourceKey<Level> level) {
        return Optionull.mapOrDefault(PlanetData.getPlanet(level), Planet::oxygen, true);
    }

    @Override
    public boolean hasOxygen(Level level, BlockPos pos) {
        if (level.isClientSide()) return hasOxygen(level);
        return PlanetHandler.hasOxygen((ServerLevel) level, pos);
    }

    @Override
    public boolean hasOxygen(Entity entity) {
        return hasOxygen(entity.level(), entity.blockPosition());
    }

    @Override
    public void addOxygen(Level level, BlockPos pos) {
        if (level.isClientSide()) return;
        PlanetHandler.setOxygen((ServerLevel) level, pos, true);
    }

    @Override
    public void removeOxygen(Level level, BlockPos pos) {
        if (level.isClientSide()) return;
        PlanetHandler.setOxygen((ServerLevel) level, pos, false);
    }

    @Override
    public void entityTick(ServerLevel level, LivingEntity entity) {
        if (level.getGameTime() % 20 != 0) return;
        if (this.hasOxygen(entity)) return;
        entity.hurt(ModDamageSources.getOrCreate(level, ModDamageSources.OXYGEN), 2);
        entity.setAirSupply(-80);
    }
}

