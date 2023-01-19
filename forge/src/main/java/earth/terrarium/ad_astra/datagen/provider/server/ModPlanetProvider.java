package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.common.data.Planet;
import earth.terrarium.ad_astra.common.system.GravitySystem;
import earth.terrarium.ad_astra.datagen.provider.base.PlanetProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;


public class ModPlanetProvider extends PlanetProvider {
    public ModPlanetProvider(PackOutput output) {
        super(output);
    }

    protected void buildPlanets(Consumer<Planet> consumer) {
        consumer.accept(new Planet(Level.OVERWORLD, true, GravitySystem.DEFAULT_GRAVITY, 15));
        consumer.accept(new Planet(Planet.MOON, false, 1.625f, -160));
        consumer.accept(new Planet(Planet.MARS, false, 3.72076f, -65));
        consumer.accept(new Planet(Planet.VENUS, false, 8.87f, 464));
        consumer.accept(new Planet(Planet.MERCURY, false, 3.7f, 167));
        consumer.accept(new Planet(Planet.GLACIO, true, 3.721f, -46));
    }
}
