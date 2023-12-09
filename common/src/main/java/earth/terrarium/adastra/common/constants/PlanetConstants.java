package earth.terrarium.adastra.common.constants;

// Source: https://www.windows2universe.org/our_solar_system/planets_table.html
public class PlanetConstants {
    public static final float EARTH_GRAVITY = 9.807f;
    public static final short EARTH_TEMPERATURE = 15;
    public static final int EARTH_SOLAR_POWER = 32;

    public static final float SPACE_GRAVITY = 0.0f;
    public static final short SPACE_TEMPERATURE = -270;
    public static final int SPACE_SOLAR_POWER = 128;

    public static final float MOON_GRAVITY = 1.622f;
    public static final short MOON_TEMPERATURE = -173;
    public static final int MOON_SOLAR_POWER = 96;

    public static final int MIN_LIVEABLE_TEMPERATURE = -50;
    public static final int MAX_LIVEABLE_TEMPERATURE = 70;

    public static final float SPACE_FRICTION = 0.999f;
    public static final float ZERO_GRAVITY_THRESHOLD = 0.09f;
}
