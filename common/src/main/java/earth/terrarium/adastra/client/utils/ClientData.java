package earth.terrarium.adastra.client.utils;

import earth.terrarium.adastra.common.handlers.base.PlanetData;
import org.jetbrains.annotations.Nullable;

public class ClientData {
    public static boolean suitFlightEnabled = true;
    @Nullable
    public static PlanetData localData;

    public static int clearTime;
    public static int rainTime;
    public static int thunderTime;
    public static boolean raining;
    public static boolean thundering;
}
