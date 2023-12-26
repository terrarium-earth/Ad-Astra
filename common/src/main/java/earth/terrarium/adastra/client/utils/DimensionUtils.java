package earth.terrarium.adastra.client.utils;

import earth.terrarium.adastra.mixins.client.LevelRendererAccessor;
import net.minecraft.client.Minecraft;

public class DimensionUtils {

    public static int getTicks() {
        return ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getTicks();
    }
}
