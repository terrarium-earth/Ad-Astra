package net.mrscauthd.beyond_earth.util;

public class PlanetFacts {

    // Source: https://en.wikipedia.org/wiki/Standard_gravity
    public static final float EARTH_TRUE_GRAVITY = 9.806f;
    // Source: https://en.wikipedia.org/wiki/Gravitation_of_the_Moon
    public static final float MOON_TRUE_GRAVITY = 1.625f;
    // Source: https://en.wikipedia.org/wiki/Gravity_of_Mars
    public static final float MARS_TRUE_GRAVITY = 3.72076f;
    // Source: https://en.wikipedia.org/wiki/Venus
    public static final float VENUS_TRUE_GRAVITY = 8.87f;
    // Source: https://en.wikipedia.org/wiki/Mercury_(planet)
    public static final float MERCURY_TRUE_GRAVITY = 3.7f;
    // Fabricated.
    public static final float GLACIO_TRUE_GRAVITY = 9.806f;
    // Fabricated.
    public static final float ORBIT_TRUE_GRAVITY = 0.0f;

    // Source:
    // https://nssdc.gsfc.nasa.gov/planetary/factsheet/planet_table_ratio.html
    public static final float EARTH_GRAVITY = EARTH_TRUE_GRAVITY / EARTH_TRUE_GRAVITY; // 1.0
    public static final float MOON_GRAVITY = MOON_TRUE_GRAVITY / EARTH_TRUE_GRAVITY; // 0.166
    public static final float MARS_GRAVITY = MARS_TRUE_GRAVITY / EARTH_TRUE_GRAVITY; // 0.377
    public static final float VENUS_GRAVITY = VENUS_TRUE_GRAVITY / EARTH_TRUE_GRAVITY; // 0.907
    public static final float MERCURY_GRAVITY = MERCURY_TRUE_GRAVITY / EARTH_TRUE_GRAVITY; // 0.378

    // Fabricated.
    public static final float GLACIO_GRAVITY = 1.0f;

    // Source: https://spaceplace.nasa.gov/years-on-other-planets/en/
    public static final float EARTH_CYCLE = 365;
    public static final float MARS_CYCLE = 687;
    public static final float VENUS_CYCLE = 225;
    public static final float MERCURY_CYCLE = 88;

    // Source: https://solarsystem.nasa.gov/resources/681/solar-system-temperatures/
    public static final int EARTH_TEMPERATURE = 15;
    public static final int MOON_TEMPERATURE = -160;
    public static final int MARS_TEMPERATURE = -65;
    public static final int VENUS_TEMPERATURE = 464;
    public static final int MERCURY_TEMPERATURE = 167;
    public static final int GLACIO_TEMPERATURE = -20;
    // Source:
    // https://www.scienceabc.com/nature/universe/what-is-the-temperature-of-space.html
    public static final int ORBIT_TEMPERATURE = -270;

    // Fabricated.
    public static final float GLACIO_CYCLE = 88;

    // Fabricated. Actual gravity is 0. Orbit dimensions are given a 'fake' gravity
    // in order for the player to fall into orbit.
    public static final float ORBIT_GRAVITY = 0.5f;
}