package earth.terrarium.ad_astra.common.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;

public class ModUtils {
    public static <T extends ParticleOptions> void spawnServerParticles(ServerLevel level, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        for (ServerPlayer player : level.players()) {
            level.sendParticles(player, particle, true, x, y, z, count, deltaX, deltaY, deltaZ, speed);
        }
    }

    public static float getStarBrightness(float timeOfDay) {
        float g = 1.0F - (Mth.cos(timeOfDay * 6.2831855F) * 2.0F + 0.25F);
        g = Mth.clamp(g, 0.0F, 1.0F);
        return g * g * 0.5F;
    }
}
