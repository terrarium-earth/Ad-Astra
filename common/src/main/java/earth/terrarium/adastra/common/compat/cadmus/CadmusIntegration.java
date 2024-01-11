package earth.terrarium.adastra.common.compat.cadmus;

import com.teamresourceful.resourcefullib.common.utils.modinfo.ModInfoUtils;
import earth.terrarium.cadmus.api.claims.ClaimApi;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;

public class CadmusIntegration {

    public static boolean cadmusLoaded() {
        return ModInfoUtils.isModLoaded("cadmus");
    }

    public static void claim(ServerPlayer player, ChunkPos pos) {
        ClaimApi.API.claim(player.serverLevel(), pos, false, player);
    }

    public static boolean isClaimed(ServerLevel level, ChunkPos pos) {
        return ClaimApi.API.isClaimed(level, pos);
    }
}
