package earth.terrarium.adastra.common.world.biome;

import com.mojang.serialization.Codec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

// Uses blended noise as we need to use the vanilla seed, which only BlendedNoise and EndIslandDensityFunction are hardcoded to get
public class CratersDensityFunction extends BlendedNoise {
    private final long seed;
    private final double scale;

    public static final KeyDispatchDataCodec<CratersDensityFunction> CODEC = KeyDispatchDataCodec.of(Codec
            .doubleRange(Double.MIN_VALUE, 1)
            .fieldOf("scale")
            .xmap(CratersDensityFunction::new, function -> function.scale)
    );

    public CratersDensityFunction(double scale) {
        this(new XoroshiroRandomSource(0L), scale);
    }

    public CratersDensityFunction(RandomSource randomSource, double scale) {
        super(randomSource, 0, 0, 0, 0, 0);

        this.seed = randomSource.nextLong();
        this.scale = scale;
    }

    @Override
    public BlendedNoise withNewRandom(RandomSource random) {
        return new CratersDensityFunction(random, scale);
    }

    @Override
    public double compute(FunctionContext context) {
        // Max the noise function distance to 1, and invert it so the lower distances to the points are higher values
        double value = 1 - Math.min(CellularNoise.getValue(seed, context.blockX() / (scale * 100), context.blockZ() / (scale * 100)), 1);

        // value^16, causing a strong ease-in effect, removing any unwanted artifacts from the noise
        value *= value; // value^2
        value *= value; // value^4
        value *= value; // value^8
        value *= value; // value^16

        return value;
    }

    @Override
    public double minValue() {
        return 0;
    }

    @Override
    public double maxValue() {
        return 1;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}
