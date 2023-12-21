package earth.terrarium.adastra.common.world.biome;

import com.google.common.collect.Streams;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class CratersBiomeSource extends BiomeSource {
    public static final Codec<CratersBiomeSource> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Biome.CODEC.fieldOf("default_biome").forGetter(source -> source.defaultBiome),
            ExtraCodecs.nonEmptyList(DepthBiome.CODEC.listOf()).listOf().fieldOf("biomes").forGetter(source -> source.allowedBiomes),
            Codec.doubleRange(Double.MIN_VALUE, 1).fieldOf("erosion_threshold").orElse(0.5).forGetter(source -> source.erosionThreshold)
        ).apply(instance, CratersBiomeSource::new)
    );

    private final Holder<Biome> defaultBiome;
    private final List<List<DepthBiome>> allowedBiomes;
    private final double erosionThreshold;

    public CratersBiomeSource(Holder<Biome> defaultBiome, List<List<DepthBiome>> biomeLists, double erosionThreshold) {
        this.defaultBiome = defaultBiome;
        this.allowedBiomes = biomeLists;
        this.erosionThreshold = erosionThreshold;
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return Streams.concat(Stream.of(defaultBiome), this.allowedBiomes.stream().flatMap(List::stream).map(DepthBiome::biome));
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler climateSampler) {
        if (allowedBiomes.isEmpty()) return defaultBiome;

        DensityFunction.SinglePointContext context = new DensityFunction.SinglePointContext(x, y, z);

        // Only include values deemed high enough by the config
        if (climateSampler.erosion().compute(context) <= erosionThreshold) return defaultBiome;

        // Calculate an evaluation cube that only includes one biome index
        int cubeX = x - 8 >> 6;
        int cubeZ = z - 8 >> 6;
        int biomeIndex = (int) ((cubeX * 31 ^ cubeZ + Climate.quantizeCoord((float) climateSampler.humidity().compute(context))) % allowedBiomes.size());

        List<DepthBiome> biomes = allowedBiomes.get(biomeIndex);

        // Compare depth of biomes and see which is closest to the current point
        long depth = Climate.quantizeCoord((float) climateSampler.depth().compute(context));

        return biomes.stream().min(Comparator.comparingDouble(depthBiome -> depthBiome.depth().distance(depth))).map(DepthBiome::biome).orElse(defaultBiome);
    }

    // Holder<Biome> with depth parameter
    public record DepthBiome(Holder<Biome> biome, Climate.Parameter depth) {
        public static final Codec<DepthBiome> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                Biome.CODEC.fieldOf("biome").forGetter(DepthBiome::biome),
                Climate.Parameter.CODEC.fieldOf("depth").forGetter(DepthBiome::depth)
            ).apply(instance, DepthBiome::new)
        );
    }
}
