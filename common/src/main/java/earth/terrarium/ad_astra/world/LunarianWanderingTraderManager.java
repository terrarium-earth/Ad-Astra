package earth.terrarium.ad_astra.world;

import earth.terrarium.ad_astra.config.SpawnConfig;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import earth.terrarium.ad_astra.util.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.level.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.ServerLevelData;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class LunarianWanderingTraderManager implements CustomSpawner {

    public static final int DEFAULT_SPAWN_DELAY = 24000;
    private static final int DEFAULT_SPAWN_TIMER = 1200;
    private final Random random = new Random();
    private final ServerLevelData properties;
    private int spawnTimer;
    private int spawnDelay;
    private int spawnChance;

    public LunarianWanderingTraderManager(ServerLevelData properties) {
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
    public int tick(ServerLevel level, boolean spawnMonsters, boolean spawnAnimals) {
        if (!SpawnConfig.spawnLunarianWanderingTrader) {
            return 0;
        }
        if (!level.getGameRules().getBoolean(GameRules.RULE_DO_TRADER_SPAWNING)) {
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
        if (!level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
            return 0;
        }
        int i = this.spawnChance;
        this.spawnChance = Mth.clamp(this.spawnChance + 25, 25, 75);
        this.properties.setWanderingTraderSpawnChance(this.spawnChance);
        if (this.random.nextInt(100) > i) {
            return 0;
        }
        if (this.trySpawn(level)) {
            this.spawnChance = 25;
            return 1;
        }
        return 0;
    }

    private boolean trySpawn(ServerLevel level) {
        ServerPlayer playerEntity = level.getRandomPlayer();
        if (playerEntity == null) {
            return true;
        }

        if (this.random.nextInt(10) != 0) {
            return false;
        }

        if (!SpawnConfig.spawnCorruptedLunarians) {
            return false;
        }

        // Only spawn on planets
        if (!ModUtils.isPlanet(level)) {
            return false;
        }

        BlockPos blockPos = playerEntity.blockPosition();
        PoiManager pointOfInterestStorage = level.getPoiManager();
        Optional<BlockPos> optional = pointOfInterestStorage.find(holder -> holder.is(PoiTypes.MEETING), pos -> true, blockPos, 48, PoiManager.Occupancy.ANY);
        BlockPos blockPos2 = optional.orElse(blockPos);
        BlockPos blockPos3 = this.getNearbySpawnPos(level, blockPos2, 48);
        if (blockPos3 != null && this.doesNotSuffocateAt(level, blockPos3)) {
            if (level.getBiome(blockPos3).is((BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS))) {
                return false;
            }

            WanderingTrader wanderingTraderEntity = ModEntityTypes.LUNARIAN_WANDERING_TRADER.get().spawn(level, null, null, null, blockPos3, MobSpawnType.EVENT, false, false);
            if (wanderingTraderEntity != null) {

                this.properties.setWanderingTraderId(wanderingTraderEntity.getUUID());
                wanderingTraderEntity.setDespawnDelay(48000);
                wanderingTraderEntity.setWanderTarget(blockPos2);
                wanderingTraderEntity.restrictTo(blockPos2, 16);
                return true;
            }
        }
        return false;
    }

    @Nullable
    private BlockPos getNearbySpawnPos(LevelReader level, BlockPos pos, int range) {
        BlockPos blockPos = null;
        for (int i = 0; i < 10; ++i) {
            int k;
            int j = pos.getX() + this.random.nextInt(range * 2) - range;
            BlockPos blockPos2 = new BlockPos(j, level.getHeight(Heightmap.Types.WORLD_SURFACE, j, k = pos.getZ() + this.random.nextInt(range * 2) - range), k);
            if (!NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, level, blockPos2, ModEntityTypes.LUNARIAN_WANDERING_TRADER.get()))
                continue;
            blockPos = blockPos2;
            break;
        }
        return blockPos;
    }

    private boolean doesNotSuffocateAt(BlockGetter level, BlockPos pos) {
        for (BlockPos blockPos : BlockPos.betweenClosed(pos, pos.offset(1, 2, 1))) {
            if (level.getBlockState(blockPos).getCollisionShape(level, blockPos).isEmpty())
                continue;
            return false;
        }
        return true;
    }
}