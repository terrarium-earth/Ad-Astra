package earth.terrarium.adastra.datagen.dimensions;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

// literally just a bunch of stuff from NoiseRouterData that's been made public
public class NoiseRouterHelper extends NoiseRouterData {
    public static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
    public static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
    public static final ResourceKey<DensityFunction> CONTINENTS = createKey("overworld/continents");
    public static final ResourceKey<DensityFunction> EROSION = createKey("overworld/erosion");
    public static final ResourceKey<DensityFunction> RIDGES = createKey("overworld/ridges");
    public static final ResourceKey<DensityFunction> FACTOR = createKey("overworld/factor");
    public static final ResourceKey<DensityFunction> DEPTH = createKey("overworld/depth");
    public static final ResourceKey<DensityFunction> SLOPED_CHEESE = createKey("overworld/sloped_cheese");
    public static final ResourceKey<DensityFunction> SPAGHETTI_ROUGHNESS_FUNCTION = createKey("overworld/caves/spaghetti_roughness_function");
    public static final ResourceKey<DensityFunction> ENTRANCES = createKey("overworld/caves/entrances");
    public static final ResourceKey<DensityFunction> NOODLE = createKey("overworld/caves/noodle");
    public static final ResourceKey<DensityFunction> PILLARS = createKey("overworld/caves/pillars");
    public static final ResourceKey<DensityFunction> SPAGHETTI_2D = createKey("overworld/caves/spaghetti_2d");

    public static ResourceKey<DensityFunction> createKey(String location) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(location));
    }

    public static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }

    public static DensityFunction underground(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters, DensityFunction arg3) {
        DensityFunction densityFunction = getFunction(densityFunctions, SPAGHETTI_2D);
        DensityFunction densityFunction2 = getFunction(densityFunctions, SPAGHETTI_ROUGHNESS_FUNCTION);
        DensityFunction densityFunction3 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.CAVE_LAYER), 8.0);
        DensityFunction densityFunction4 = DensityFunctions.mul(DensityFunctions.constant(4.0), densityFunction3.square());
        DensityFunction densityFunction5 = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.CAVE_CHEESE), 0.6666666666666666);
        DensityFunction densityFunction6 = DensityFunctions.add(DensityFunctions.add(DensityFunctions.constant(0.27), densityFunction5).clamp(-1.0, 1.0), DensityFunctions.add(DensityFunctions.constant(1.5), DensityFunctions.mul(DensityFunctions.constant(-0.64), arg3)).clamp(0.0, 0.5));
        DensityFunction densityFunction7 = DensityFunctions.add(densityFunction4, densityFunction6);
        DensityFunction densityFunction8 = DensityFunctions.min(DensityFunctions.min(densityFunction7, getFunction(densityFunctions, ENTRANCES)), DensityFunctions.add(densityFunction, densityFunction2));
        DensityFunction densityFunction9 = getFunction(densityFunctions, PILLARS);
        DensityFunction densityFunction10 = DensityFunctions.rangeChoice(densityFunction9, -1000000.0, 0.03, DensityFunctions.constant(-1000000.0), densityFunction9);
        return DensityFunctions.max(densityFunction8, densityFunction10);
    }

    public static DensityFunction postProcess(DensityFunction arg) {
        DensityFunction densityFunction = DensityFunctions.blendDensity(arg);
        return DensityFunctions.mul(DensityFunctions.interpolated(densityFunction), DensityFunctions.constant(0.64)).squeeze();
    }

    public static DensityFunction slideOverworld(boolean bl, DensityFunction arg) {
        return slide(arg, -64, 384, bl ? 16 : 80, bl ? 0 : 64, -0.078125, 0, 24, bl ? 0.4 : 0.1171875);
    }

    public static DensityFunction noiseGradientDensity(DensityFunction arg, DensityFunction arg2) {
        DensityFunction densityFunction = DensityFunctions.mul(arg2, arg);
        return DensityFunctions.mul(DensityFunctions.constant(4.0), densityFunction.quarterNegative());
    }

    public static DensityFunction slide(DensityFunction arg, int i, int j, int k, int l, double d, int m, int n, double e) {
        DensityFunction densityFunction2 = DensityFunctions.yClampedGradient(i + j - k, i + j - l, 1.0, 0.0);
        DensityFunction densityFunction = DensityFunctions.lerp(densityFunction2, d, arg);
        DensityFunction densityFunction3 = DensityFunctions.yClampedGradient(i + m, i + n, 0.0, 1.0);
        densityFunction = DensityFunctions.lerp(densityFunction3, e, densityFunction);
        return densityFunction;
    }
}
