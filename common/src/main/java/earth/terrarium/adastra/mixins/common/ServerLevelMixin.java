package earth.terrarium.adastra.mixins.common;

import com.google.common.collect.ImmutableList;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.common.entities.mob.lunarians.LunarianWanderingTraderSpawner;
import earth.terrarium.adastra.common.systems.EnvironmentEffects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
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
import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @Shadow
    @Mutable
    @Final
    private List<CustomSpawner> customSpawners;

    @Inject(method = "<init>", at = @At("TAIL"))
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

    @Inject(method = "tickChunk", at = @At("TAIL"))
    public void tickChunk(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci) {
        if (!OxygenApi.API.hasOxygen(chunk.getLevel())) {
            var level = chunk.getLevel();
            level.getProfiler().popPush("adastra$spaceeffects");
            EnvironmentEffects.tickChunk((ServerLevel) level, chunk);
            level.getProfiler().pop();
        }
    }

    @Inject(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V",
            shift = At.Shift.AFTER
        )
    )
    public void adastra$tick(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        ServerLevel level = (ServerLevel) (Object) this;
        if (PlanetApi.API.isExtraterrestrial(level)) {
            // Fix night not advancing when sleeping in space
            long time = level.getLevelData().getDayTime() + 24000L;
            level.getServer().getAllLevels().forEach(l -> l.setDayTime(time - time % 24000L));
        }
    }
}
