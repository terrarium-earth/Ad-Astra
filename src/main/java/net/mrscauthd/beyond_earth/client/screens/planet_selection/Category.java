package net.mrscauthd.beyond_earth.client.screens.planet_selection;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public record Category(Identifier id, Identifier solarSystemType, Category parent) {
        public static final Identifier SOLAR_SYSTEM_TYPE = new ModIdentifier("solar_system");
        public static final Identifier PROXIMA_CENTAURI_TYPE = new ModIdentifier("proxima_centauri");
        public static final Identifier MILKY_WAY_TYPE = new ModIdentifier("milky_way");
        public static final Category MILKY_WAY_CATEGORY = new Category(new ModIdentifier("milky_way"), MILKY_WAY_TYPE,
                        null);
        public static final Category SOLAR_SYSTEM_CATEGORY = new Category(new ModIdentifier("solar_system"),
                        SOLAR_SYSTEM_TYPE, MILKY_WAY_CATEGORY);
        public static final Category EARTH_CATEGORY = new Category(new ModIdentifier("earth_category"),
                        SOLAR_SYSTEM_TYPE,
                        SOLAR_SYSTEM_CATEGORY);
        public static final Category MARS_CATEGORY = new Category(new ModIdentifier("mars_category"), SOLAR_SYSTEM_TYPE,
                        SOLAR_SYSTEM_CATEGORY);
        public static final Category VENUS_CATEGORY = new Category(new ModIdentifier("venus_category"),
                        SOLAR_SYSTEM_TYPE,
                        SOLAR_SYSTEM_CATEGORY);
        public static final Category MERCURY_CATEGORY = new Category(new ModIdentifier("mercury_category"),
                        SOLAR_SYSTEM_TYPE, SOLAR_SYSTEM_CATEGORY);
        public static final Category PROXIMA_CENTAURI_CATEGORY = new Category(new ModIdentifier("proxima_centauri"),
                        PROXIMA_CENTAURI_TYPE, MILKY_WAY_CATEGORY);
        public static final Category GLACIO_CATEGORY = new Category(new ModIdentifier("glacio_category"),
                        PROXIMA_CENTAURI_TYPE, PROXIMA_CENTAURI_CATEGORY);
}