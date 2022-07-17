package com.github.alexnijjar.beyond_earth.world;

import java.util.Optional;
import java.util.Random;

import javax.annotation.Nullable;

import com.github.alexnijjar.beyond_earth.registry.ModEntityTypes;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.spawner.Spawner;

public class AlienWanderingTraderManager implements Spawner {

    private static final int DEFAULT_SPAWN_TIMER = 1200;
    public static final int DEFAULT_SPAWN_DELAY = 24000;
    private final Random random = new Random();
    private final ServerWorldProperties properties;
    private int spawnTimer;
    private int spawnDelay;
    private int spawnChance;

    public AlienWanderingTraderManager(ServerWorldProperties properties) {
        this.properties = properties;
        this.spawnTimer = DEFAULT_SPAWN_TIMER;
        this.spawnDelay = properties.getWanderingTraderSpawnDelay();
        this.spawnChance = properties.getWanderingTraderSpawnChance();
        if (this.spawnDelay == 0 && this.spawnChance == 0) {
            this.spawnDelay = DEFAULT_SPAWN_TIMER;
            properties.setWanderingTraderSpawnDelay(this.spawnDelay);
            this.spawnChance = 25;
            properties.setWanderingTraderSpawnChance(this.spawnChance);
        }
    }

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!world.getGameRules().getBoolean(GameRules.DO_TRADER_SPAWNING)) {
            return 0;
        }
        if (--this.spawnTimer > 0) {
            return 0;
        }
        this.spawnTimer = DEFAULT_SPAWN_TIMER;
        this.spawnDelay -= DEFAULT_SPAWN_TIMER;
        this.properties.setWanderingTraderSpawnDelay(this.spawnDelay);
        if (this.spawnDelay > 0) {
            return 0;
        }
        this.spawnDelay = DEFAULT_SPAWN_TIMER;
        if (!world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            return 0;
        }
        int i = this.spawnChance;
        this.spawnChance = MathHelper.clamp(this.spawnChance + 25, 25, 75);
        this.properties.setWanderingTraderSpawnChance(this.spawnChance);
        if (this.random.nextInt(100) > i) {
            return 0;
        }
        if (this.trySpawn(world)) {
            this.spawnChance = 25;
            return 1;
        }
        return 0;
    }

    private boolean trySpawn(ServerWorld world) {
        ServerPlayerEntity playerEntity = world.getRandomAlivePlayer();
        if (playerEntity == null) {
            return true;
        }
        
        // Only spawn on planets
        if (!ModUtils.isPlanet(world)) {
            return false;
        }
        if (this.random.nextInt(10) != 0) {
            return false;
        }
        BlockPos blockPos = playerEntity.getBlockPos();
        PointOfInterestStorage pointOfInterestStorage = world.getPointOfInterestStorage();
        Optional<BlockPos> optional = pointOfInterestStorage.getPosition(PointOfInterestType.MEETING.getCompletionCondition(), pos -> true, blockPos, 48, PointOfInterestStorage.OccupationStatus.ANY);
        BlockPos blockPos2 = optional.orElse(blockPos);
        BlockPos blockPos3 = this.getNearbySpawnPos(world, blockPos2, 48);
        if (blockPos3 != null && this.doesNotSuffocateAt(world, blockPos3)) {
            if (world.getBiome(blockPos3).matchesKey(BiomeKeys.THE_VOID)) {
                return false;
            }
            WanderingTraderEntity wanderingTraderEntity = ModEntityTypes.ALIEN_WANDERING_TRADER.spawn(world, null, null, null, blockPos3, SpawnReason.EVENT, false, false);
            if (wanderingTraderEntity != null) {
                this.properties.setWanderingTraderId(wanderingTraderEntity.getUuid());
                wanderingTraderEntity.setDespawnDelay(48000);
                wanderingTraderEntity.setWanderTarget(blockPos2);
                wanderingTraderEntity.setPositionTarget(blockPos2, 16);
                return true;
            }
        }
        return false;
    }

    @Nullable
    private BlockPos getNearbySpawnPos(WorldView world, BlockPos pos, int range) {
        BlockPos blockPos = null;
        for (int i = 0; i < 10; ++i) {
            int k;
            int j = pos.getX() + this.random.nextInt(range * 2) - range;
            BlockPos blockPos2 = new BlockPos(j, world.getTopY(Heightmap.Type.WORLD_SURFACE, j, k = pos.getZ() + this.random.nextInt(range * 2) - range), k);
            if (!SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, blockPos2, ModEntityTypes.ALIEN_WANDERING_TRADER)) continue;
            blockPos = blockPos2;
            break;
        }
        return blockPos;
    }

    private boolean doesNotSuffocateAt(BlockView world, BlockPos pos) {
        for (BlockPos blockPos : BlockPos.iterate(pos, pos.add(1, 2, 1))) {
            if (world.getBlockState(blockPos).getCollisionShape(world, blockPos).isEmpty()) continue;
            return false;
        }
        return true;
    }
}
