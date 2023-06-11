package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.common.world.LunarianWanderingTraderSpawner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

// Adds lunarian wandering trader spawning
@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @Shadow
    @Mutable
    @Final
    private List<CustomSpawner> customSpawners;

    @Inject(at = @At(value = "TAIL"), method = "<init>")
    public void ad_astra$Serverlevel(MinecraftServer minecraftServer, Executor executor, LevelStorageSource.LevelStorageAccess levelStorageAccess, ServerLevelData serverLevelData, ResourceKey resourceKey, LevelStem levelStem, ChunkProgressListener chunkProgressListener, boolean debugWorld, long seed, List spawners, boolean shouldTickTime, RandomSequences randomSequences, CallbackInfo ci) {

        List<CustomSpawner> serverSpawners = new ArrayList<>(this.customSpawners);
        if (!shouldTickTime) {
            serverSpawners.add(new LunarianWanderingTraderSpawner(serverLevelData));
            this.customSpawners = serverSpawners;
        }
    }
}
