package com.github.alexnijjar.beyond_earth.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.beyond_earth.world.AlienWanderingTraderManager;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.spawner.Spawner;

// Adds alien wandering trader spawning
@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(at = @At(value = "TAIL"), method = "<init>")
    public void ServerWorld(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> worldKey, RegistryEntry<DimensionType> registryEntry, WorldGenerationProgressListener worldGenerationProgressListener, ChunkGenerator chunkGenerator, boolean debugWorld, long seed, List<Spawner> spawners, boolean shouldTickTime, CallbackInfo ci) {

        List<Spawner> serverSpawners = new ArrayList<>(((ServerWorld) (Object) this).spawners);
        if (!shouldTickTime) {
            serverSpawners.add(new AlienWanderingTraderManager(properties));
            ((ServerWorld) (Object) this).spawners = serverSpawners;
        }
    }
}
