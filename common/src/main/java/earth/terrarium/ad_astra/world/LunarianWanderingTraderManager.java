package earth.terrarium.ad_astra.world;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import earth.terrarium.ad_astra.util.ModUtils;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.*;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class LunarianWanderingTraderManager implements Spawner {

    public static final int DEFAULT_SPAWN_DELAY = 24000;
    private static final int DEFAULT_SPAWN_TIMER = 1200;
    private final Random random = new Random();
    private final ServerWorldProperties properties;
    private int spawnTimer;
    private int spawnDelay;
    private int spawnChance;

    public LunarianWanderingTraderManager(ServerWorldProperties properties) {
        this.properties = properties;
        this.spawnTimer = DEFAULT_SPAWN_TIMER;
        this.spawnDelay = properties.getWanderingTraderSpawnDelay();
        this.spawnChance = properties.getWanderingTraderSpawnChance();
        if (this.spawnDelay == 0 && this.spawnChance == 0) {
            this.spawnDelay = DEFAULT_SPAWN_DELAY;
            properties.setWanderingTraderSpawnDelay(this.spawnDelay);
            this.spawnChance = 25;
            properties.setWanderingTraderSpawnChance(this.spawnChance);
        }
    }

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!AdAstra.CONFIG.general.spawnLunarianWanderingTrader) {
            return 0;
        }
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
        this.spawnDelay = DEFAULT_SPAWN_DELAY;
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

        if (this.random.nextInt(10) != 0) {
            return false;
        }

        if (!AdAstra.CONFIG.general.spawnCorruptedLunarians) {
            return false;
        }

        // Only spawn on planets
        if (!ModUtils.isPlanet(world)) {
            return false;
        }

        BlockPos blockPos = playerEntity.getBlockPos();
        PointOfInterestStorage pointOfInterestStorage = world.getPointOfInterestStorage();
        Optional<BlockPos> optional = pointOfInterestStorage.getPosition(holder -> holder.isRegistryKey(PointOfInterestTypes.MEETING), pos -> true, blockPos, 48, PointOfInterestStorage.OccupationStatus.ANY);
        BlockPos blockPos2 = optional.orElse(blockPos);
        BlockPos blockPos3 = this.getNearbySpawnPos(world, blockPos2, 48);
        if (blockPos3 != null && this.doesNotSuffocateAt(world, blockPos3)) {
            if (world.getBiome(blockPos3).isIn((BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS))) {
                return false;
            }

            WanderingTraderEntity wanderingTraderEntity = ModEntityTypes.LUNARIAN_WANDERING_TRADER.get().spawn(world, null, null, null, blockPos3, SpawnReason.EVENT, false, false);
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
            if (!SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, blockPos2, ModEntityTypes.LUNARIAN_WANDERING_TRADER.get()))
                continue;
            blockPos = blockPos2;
            break;
        }
        return blockPos;
    }

    private boolean doesNotSuffocateAt(BlockView world, BlockPos pos) {
        for (BlockPos blockPos : BlockPos.iterate(pos, pos.add(1, 2, 1))) {
            if (world.getBlockState(blockPos).getCollisionShape(world, blockPos).isEmpty())
                continue;
            return false;
        }
        return true;
    }
}