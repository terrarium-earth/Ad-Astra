package earth.terrarium.adastra.common.compat.cadmus;

import com.teamresourceful.resourcefullib.common.utils.modinfo.ModInfoUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

public class CadmusIntegration {

    public static boolean cadmusLoaded() {
        return ModInfoUtils.isModLoaded("cadmus");
    }

    public static void claim(ServerPlayer player, ChunkPos pos) {
//        ClaimApi.API.claim(player.serverLevel(), pos, false, player);
    }

    public static boolean isClaimed(ServerLevel level, ChunkPos pos) {
//        return ClaimApi.API.isClaimed(level, pos);
        return true;
    }

    public static void addClientListeners(ResourceKey<Level> dimension) {
//        ClientClaims.get(dimension).addListener(AdAstra.MOD_ID, claims -> {
//            if (Minecraft.getInstance().player.containerMenu instanceof PlanetsMenu menu) {
//                menu.clearClaimedChunks();
//                claims.forEach((pos, entry) -> {
//                    if (Minecraft.getInstance().player.chunkPosition().equals(pos)) {
//                        menu.setClaimedChunk(dimension, true);
//                    }
//                });
//            }
//        });
    }

    public static void removeClientListeners(ResourceKey<Level> dimension) {
//        ClientClaims.get(dimension).removeListener(AdAstra.MOD_ID);
    }
}
