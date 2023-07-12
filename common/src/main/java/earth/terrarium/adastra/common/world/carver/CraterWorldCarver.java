package earth.terrarium.adastra.common.world.carver;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

import java.util.function.Function;

public class CraterWorldCarver extends WorldCarver<CarverConfiguration> {
    public CraterWorldCarver(Codec<CarverConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean carve(CarvingContext context, CarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeAccessor, RandomSource random, Aquifer aquifer, ChunkPos chunkPos, CarvingMask carvingMask) {
        return carveEllipsoid(
                context,
                config,
                chunk,
                biomeAccessor,
                aquifer,
                chunkPos.getMiddleBlockX() + 0.5,
                chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, chunkPos.getMiddleBlockX(), chunkPos.getMiddleBlockZ()),
                chunkPos.getMiddleBlockZ() + 0.5,
                64,
                32,
                carvingMask,
                (carvingContext, d, e, f, i) -> false
        );
    }

    @Override
    public boolean isStartChunk(CarverConfiguration config, RandomSource random) {
        return random.nextFloat() <= config.probability;
    }
}
