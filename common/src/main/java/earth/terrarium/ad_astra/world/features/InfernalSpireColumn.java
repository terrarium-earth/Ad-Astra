package earth.terrarium.ad_astra.world.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import earth.terrarium.ad_astra.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
import org.jetbrains.annotations.Nullable;

public class InfernalSpireColumn extends Feature<ColumnFeatureConfiguration> {

    private static final ImmutableList<Block> CANNOT_PLACE_ON = ImmutableList.of(Blocks.LAVA, Blocks.BEDROCK, Blocks.MAGMA_BLOCK, Blocks.SOUL_SAND, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER);

    public InfernalSpireColumn(Codec<ColumnFeatureConfiguration> configCodec) {
        super(configCodec);
    }

    @Nullable
    private static BlockPos findSurface(WorldGenLevel level, int seaLevel, BlockPos.MutableBlockPos pos, int calculatedDistance) {
        while (pos.getY() > level.getMinBuildHeight() + 1 && calculatedDistance > 0) {
            --calculatedDistance;
            if (canPlaceAt(level, seaLevel, pos)) {
                return pos;
            }

            pos.move(Direction.DOWN);
        }

        return null;
    }

    private static boolean canPlaceAt(WorldGenLevel level, int seaLevel, BlockPos.MutableBlockPos pos) {
        if (!isAirOrLavaOcean(level, seaLevel, pos)) {
            return false;
        } else {
            BlockState blockstate = level.getBlockState(pos.move(Direction.DOWN));
            pos.move(Direction.UP);
            return !blockstate.isAir() && !CANNOT_PLACE_ON.contains(blockstate.getBlock());
        }
    }

    @Nullable
    private static BlockPos findAir(WorldGenLevel level, BlockPos.MutableBlockPos pos, int i) {
        while (pos.getY() < level.getMaxBuildHeight() && i > 0) {
            --i;
            BlockState blockstate = level.getBlockState(pos);
            if (CANNOT_PLACE_ON.contains(blockstate.getBlock())) {
                return null;
            }

            if (blockstate.isAir()) {
                return pos;
            }

            pos.move(Direction.UP);
        }

        return null;
    }

    private static boolean isAirOrLavaOcean(WorldGenLevel level, int seaLevel, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        return blockstate.isAir() || blockstate.is(Blocks.LAVA) && pos.getY() <= seaLevel;
    }

    @Override
    public boolean place(FeaturePlaceContext<ColumnFeatureConfiguration> context) {

        int seaLevel = context.chunkGenerator().getSeaLevel();
        BlockPos blockpos = context.origin();
        RandomSource random = context.random();
        ColumnFeatureConfiguration config = context.config();
        if (!canPlaceAt(context.level(), seaLevel, blockpos.mutable())) {
            return false;
        } else {
            int height = config.height().sample(random);
            boolean flag = random.nextFloat() < 0.9f;
            int k = Math.min(height, flag ? 5 : 8);
            int l = flag ? 50 : 15;
            boolean flag1 = false;

            for (BlockPos randomPos : BlockPos.randomBetweenClosed(random, l, blockpos.getX() - k, blockpos.getY(), blockpos.getZ() - k, blockpos.getX() + k, blockpos.getY(), blockpos.getZ() + k)) {
                int calculatedDistance = height - randomPos.distManhattan(blockpos);
                if (calculatedDistance >= 0) {
                    flag1 |= this.placeColumn(context.level(), seaLevel, randomPos, calculatedDistance, config.reach().sample(random));
                }
            }

            return flag1;
        }
    }

    private boolean placeColumn(WorldGenLevel level, int seaLevel, BlockPos randomPos, int calculatedDistance, int reach) {
        boolean flag = false;

        for (BlockPos blockpos : BlockPos.betweenClosed(randomPos.getX() - reach, randomPos.getY(), randomPos.getZ() - reach, randomPos.getX() + reach, randomPos.getY(), randomPos.getZ() + reach)) {
            int calculatedDistance2 = blockpos.distManhattan(randomPos);
            BlockPos blockpos1 = isAirOrLavaOcean(level, seaLevel, blockpos) ? findSurface(level, seaLevel, blockpos.mutable(), calculatedDistance2) : findAir(level, blockpos.mutable(), calculatedDistance2);
            if (blockpos1 != null) {
                int j = calculatedDistance - calculatedDistance2 / 2;

                for (BlockPos.MutableBlockPos pos = blockpos1.mutable(); j >= 0; --j) {
                    if (isAirOrLavaOcean(level, seaLevel, pos)) {
                        this.setBlock(level, pos, ModBlocks.INFERNAL_SPIRE_BLOCK.get().defaultBlockState());
                        pos.move(Direction.UP);
                        flag = true;
                    } else {
                        if (!level.getBlockState(pos).is(ModBlocks.INFERNAL_SPIRE_BLOCK.get())) {
                            break;
                        }

                        pos.above();
                    }
                }
            }
        }

        return flag;
    }
}