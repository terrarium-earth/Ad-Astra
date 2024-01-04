package earth.terrarium.adastra.common.compat.cadmus;

import com.mojang.datafixers.util.Pair;
import com.teamresourceful.resourcefullib.common.utils.modinfo.ModInfoUtils;
import earth.terrarium.cadmus.common.claims.ClaimHandler;
import earth.terrarium.cadmus.common.claims.ClaimType;
import earth.terrarium.cadmus.common.util.ModUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;

import java.util.Map;

public class CadmusIntegration {

    public static boolean cadmusLoaded() {
        return ModInfoUtils.isModLoaded("cadmus");
    }

    public static void claim(ServerPlayer player, ChunkPos pos) {
        Pair<String, ClaimType> claimData = ClaimHandler.getClaim(player.serverLevel(), pos);
        if (claimData != null) return;
        var claim = Map.of(pos, ClaimType.CLAIMED);
        ModUtils.tryClaim(player.serverLevel(), player, claim, Map.of());
    }
}
