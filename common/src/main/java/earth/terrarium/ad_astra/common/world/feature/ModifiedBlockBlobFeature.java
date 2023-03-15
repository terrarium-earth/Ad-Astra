package earth.terrarium.ad_astra.common.world.feature;

import com.mojang.serialization.Codec;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class ModifiedBlockBlobFeature extends Feature<BlockStateConfiguration> {

    public ModifiedBlockBlobFeature(Codec<BlockStateConfiguration> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> context) {
        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();

        BlockStateConfiguration config;
        for (config = context.config(); pos.getY() > level.getMinBuildHeight() + 3; pos = pos.below()) {
            if (!level.isEmptyBlock(pos.below())) {
                BlockState state = level.getBlockState(pos.below());
                if (Feature.isDirt(state) || isStone(state) || state.is(ModBlocks.MARS_SAND.get())) {
                    break;
                }
            }
        }

        if (pos.getY() <= level.getMinBuildHeight() + 3) {
            return false;
        } else {
            for (int l = 0; l < 3; ++l) {
                int i = random.nextInt(2);
                int j = random.nextInt(2);
                int k = random.nextInt(2);
                float f = (float) (i + j + k) * 0.333f + 0.5f;

                for (BlockPos blockpos1 : BlockPos.betweenClosed(pos.offset(-i, -j, -k), pos.offset(i, j, k))) {
                    if (blockpos1.distSqr(pos) <= (double) (f * f)) {
                        level.setBlock(blockpos1, config.state, 4);
                    }
                }

                pos = pos.offset(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
            }

            return true;
        }
    }
}