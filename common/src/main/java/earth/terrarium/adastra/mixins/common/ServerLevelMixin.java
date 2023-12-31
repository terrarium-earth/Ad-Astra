package earth.terrarium.adastra.mixins.common;

import com.google.common.collect.ImmutableList;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.common.entities.mob.lunarians.LunarianWanderingTraderSpawner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @Shadow
    @Mutable
    @Final
    private List<CustomSpawner> customSpawners;

    @Inject(at = @At(value = "TAIL"), method = "<init>")
    public void adastra$init(
        MinecraftServer server,
        Executor dispatcher,
        LevelStorageSource.LevelStorageAccess levelStorageAccess,
        ServerLevelData serverLevelData,
        ResourceKey<Level> dimension,
        LevelStem levelStem,
        ChunkProgressListener progressListener,
        boolean isDebug, long biomeZoomSeed,
        List<CustomSpawner> customSpawners,
        boolean tickTime,
        @Nullable RandomSequences randomSequences,
        CallbackInfo ci
    ) {
        if (!PlanetApi.API.isPlanet(dimension)) return;
        this.customSpawners = ImmutableList.<CustomSpawner>builder()
            .addAll(customSpawners)
            .add(new LunarianWanderingTraderSpawner(serverLevelData))
            .build();
    }
}
