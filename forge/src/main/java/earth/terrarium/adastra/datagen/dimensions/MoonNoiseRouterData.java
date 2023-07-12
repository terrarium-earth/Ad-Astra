package earth.terrarium.adastra.datagen.dimensions;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

// just copy of nether for now until i actually figure out how the fuck im supposed to do this
public class MoonNoiseRouterData {
    private static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
    private static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
    private static final ResourceKey<DensityFunction> BASE_3D_NOISE_NETHER = createKey("nether/base_3d_noise");


    private static ResourceKey<DensityFunction> createKey(String location) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(location));
    }

    public static NoiseRouter moon(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters) {
        return noNewCaves(densityFunctions, noiseParameters, slideNetherLike(densityFunctions, 0, 128));
    }

    private static DensityFunction slideNetherLike(HolderGetter<DensityFunction> densityFunctions, int i, int j) {
        return slide(getFunction(densityFunctions, BASE_3D_NOISE_NETHER), i, j, 24, 0, 0.9375, -8, 24, 2.5);
    }

    private static DensityFunction slide(DensityFunction arg, int i, int j, int k, int l, double d, int m, int n, double e) {
        DensityFunction densityFunction2 = DensityFunctions.yClampedGradient(i + j - k, i + j - l, 1.0, 0.0);
        DensityFunction densityFunction = DensityFunctions.lerp(densityFunction2, d, arg);
        DensityFunction densityFunction3 = DensityFunctions.yClampedGradient(i + m, i + n, 0.0, 1.0);
        densityFunction = DensityFunctions.lerp(densityFunction3, e, densityFunction);
        return densityFunction;
    }

    private static NoiseRouter noNewCaves(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters, DensityFunction arg3) {
        DensityFunction densityFunction = getFunction(densityFunctions, SHIFT_X);
        DensityFunction densityFunction2 = getFunction(densityFunctions, SHIFT_Z);
        DensityFunction densityFunction3 = DensityFunctions.shiftedNoise2d(densityFunction, densityFunction2, 0.25, noiseParameters.getOrThrow(Noises.TEMPERATURE));
        DensityFunction densityFunction4 = DensityFunctions.shiftedNoise2d(densityFunction, densityFunction2, 0.25, noiseParameters.getOrThrow(Noises.VEGETATION));
        DensityFunction densityFunction5 = postProcess(arg3);
        return new NoiseRouter(DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), densityFunction3, densityFunction4, DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), densityFunction5, DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero());
    }

    private static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }

    private static DensityFunction postProcess(DensityFunction arg) {
        DensityFunction densityFunction = DensityFunctions.blendDensity(arg);
        return DensityFunctions.mul(DensityFunctions.interpolated(densityFunction), DensityFunctions.constant(0.64)).squeeze();
    }
}
