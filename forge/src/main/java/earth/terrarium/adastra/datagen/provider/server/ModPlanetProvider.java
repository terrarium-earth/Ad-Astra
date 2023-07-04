package earth.terrarium.adastra.datagen.provider.server;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.planets.Planet;
import earth.terrarium.adastra.datagen.provider.base.ModCodecProvider;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.function.BiConsumer;


public class ModPlanetProvider extends ModCodecProvider<Planet> {
    public static final ResourceKey<Registry<Planet>> PLANET_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(AdAstra.MOD_ID, "planets"));

    public ModPlanetProvider(PackOutput packOutput) {
        super(packOutput, Planet.CODEC, PLANET_REGISTRY);
    }

    @Override
    protected void build(BiConsumer<ResourceLocation, Planet> consumer) {
        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "overworld"),
            new Planet(
                Level.OVERWORLD,
                true,
                PlanetConstants.EARTH_TEMPERATURE,
                PlanetConstants.EARTH_GRAVITY,
                PlanetConstants.EARTH_SOLAR_POWER));
        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "space"),
            new Planet(Planet.SPACE,
                false,
                PlanetConstants.SPACE_TEMPERATURE,
                PlanetConstants.SPACE_GRAVITY,
                PlanetConstants.SPACE_SOLAR_POWER));
    }

    @Override
    public String getName() {
        return "Planets";
    }
}
