package com.github.alexnijjar.beyond_earth.world.features;

import java.util.Random;

import javax.annotation.Nullable;

import com.github.alexnijjar.beyond_earth.registry.ModBlocks;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.BasaltColumnsFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class VenusDeltas extends Feature<BasaltColumnsFeatureConfig> {

    private static final ImmutableList<Block> CANNOT_PLACE_ON = ImmutableList.of(Blocks.LAVA, Blocks.BEDROCK, Blocks.MAGMA_BLOCK, Blocks.SOUL_SAND, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART,
            Blocks.CHEST, Blocks.SPAWNER);

    public VenusDeltas(Codec<BasaltColumnsFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Nullable
    private static BlockPos findSurface(StructureWorldAccess world, int seaLevel, BlockPos.Mutable pos, int calculatedDistance) {
        while (pos.getY() > world.getBottomY() + 1 && calculatedDistance > 0) {
            --calculatedDistance;
            if (canPlaceAt(world, seaLevel, pos)) {
                return pos;
            }

            pos.move(Direction.DOWN);
        }

        return null;
    }

    private static boolean canPlaceAt(StructureWorldAccess world, int seaLevel, BlockPos.Mutable pos) {
        if (!isAirOrLavaOcean(world, seaLevel, pos)) {
            return false;
        } else {
            BlockState blockstate = world.getBlockState(pos.move(Direction.DOWN));
            pos.move(Direction.UP);
            return !blockstate.isAir() && !CANNOT_PLACE_ON.contains(blockstate.getBlock());
        }
    }

    @Nullable
    private static BlockPos findAir(StructureWorldAccess world, BlockPos.Mutable pos, int i) {
        while (pos.getY() < world.getTopY() && i > 0) {
            --i;
            BlockState blockstate = world.getBlockState(pos);
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

    private static boolean isAirOrLavaOcean(StructureWorldAccess world, int seaLevel, BlockPos pos) {
        BlockState blockstate = world.getBlockState(pos);
        return blockstate.isAir() || blockstate.isOf(Blocks.LAVA) && pos.getY() <= seaLevel;
    }

    @Override
    public boolean generate(FeatureContext<BasaltColumnsFeatureConfig> context) {

        int seaLevel = context.getGenerator().getSeaLevel();
        BlockPos blockpos = context.getOrigin();
        Random random = context.getRandom();
        BasaltColumnsFeatureConfig config = context.getConfig();
        if (!canPlaceAt(context.getWorld(), seaLevel, blockpos.mutableCopy())) {
            return false;
        } else {
            int height = config.getHeight().get(random);
            boolean flag = random.nextFloat() < 0.9f;
            int k = Math.min(height, flag ? 5 : 8);
            int l = flag ? 50 : 15;
            boolean flag1 = false;

            for (BlockPos randomPos : BlockPos.iterateRandomly(random, l, blockpos.getX() - k, blockpos.getY(), blockpos.getZ() - k, blockpos.getX() + k, blockpos.getY(), blockpos.getZ() + k)) {
                int calculatedDistance = height - randomPos.getManhattanDistance(blockpos);
                if (calculatedDistance >= 0) {
                    flag1 |= this.placeColumn(context.getWorld(), seaLevel, randomPos, calculatedDistance, config.getReach().get(random));
                }
            }

            return flag1;
        }
    }

    private boolean placeColumn(StructureWorldAccess world, int seaLevel, BlockPos randomPos, int calculatedDistance, int reach) {
        boolean flag = false;

        for (BlockPos blockpos : BlockPos.iterate(randomPos.getX() - reach, randomPos.getY(), randomPos.getZ() - reach, randomPos.getX() + reach, randomPos.getY(), randomPos.getZ() + reach)) {
            int calculatedDistance2 = blockpos.getManhattanDistance(randomPos);
            BlockPos blockpos1 = isAirOrLavaOcean(world, seaLevel, blockpos) ? findSurface(world, seaLevel, blockpos.mutableCopy(), calculatedDistance2) : findAir(world, blockpos.mutableCopy(), calculatedDistance2);
            if (blockpos1 != null) {
                int j = calculatedDistance - calculatedDistance2 / 2;

                for (BlockPos.Mutable pos = blockpos1.mutableCopy(); j >= 0; --j) {
                    if (isAirOrLavaOcean(world, seaLevel, pos)) {
                        this.setBlockState(world, pos, ModBlocks.INFERNAL_SPIRE_BLOCK.getDefaultState());
                        pos.move(Direction.UP);
                        flag = true;
                    } else {
                        if (!world.getBlockState(pos).isOf(ModBlocks.INFERNAL_SPIRE_BLOCK)) {
                            break;
                        }

                        pos.up();
                    }
                }
            }
        }

        return flag;
    }
}