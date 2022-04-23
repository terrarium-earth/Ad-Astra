package net.mrscauthd.beyond_earth.world.features;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.mrscauthd.beyond_earth.registry.ModBlocks;

public class MarsBlockBlobFeature extends Feature<SingleStateFeatureConfig> {

    public MarsBlockBlobFeature(Codec<SingleStateFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<SingleStateFeatureConfig> context) {
        BlockPos pos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();

        SingleStateFeatureConfig config;
        for (config = context.getConfig(); pos.getY() > world.getBottomY() + 3; pos = pos.down()) {
            if (!world.isAir(pos.down())) {
                BlockState state = world.getBlockState(pos.down());
                if (Feature.isSoil(state) || isStone(state) || state.isOf(ModBlocks.MARS_SAND)) {
                    break;
                }
            }
        }

        if (pos.getY() <= world.getBottomY() + 3) {
            return false;
        } else {
            for (int l = 0; l < 3; ++l) {
                int i = random.nextInt(2);
                int j = random.nextInt(2);
                int k = random.nextInt(2);
                float f = (float) (i + j + k) * 0.333F + 0.5F;

                for (BlockPos blockpos1 : BlockPos.iterate(pos.add(-i, -j, -k), pos.add(i, j, k))) {
                    if (blockpos1.getSquaredDistance(pos) <= (double) (f * f)) {
                        world.setBlockState(blockpos1, config.state, 4);
                    }
                }

                pos = pos.add(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
            }

            return true;
        }
    }
}