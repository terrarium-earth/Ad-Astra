package earth.terrarium.adastra.datagen.dimensions;

import earth.terrarium.adastra.datagen.provider.server.registry.ModDensityFunctionProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class ModNoiseRouterData extends NoiseRouterData {

    public static NoiseRouter moon(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters) {
        // Aquifers
        DensityFunction barrierNoise = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 0.5);
        DensityFunction fluidLevelFloodednessNoise = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67);
        DensityFunction fluidLevelSpreadNoise = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143);
        DensityFunction lavaNoise = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_LAVA));

        // Biome climate parameters
        DensityFunction shiftXFunction = NoiseRouterHelper.getFunction(densityFunctions, NoiseRouterHelper.SHIFT_X);
        DensityFunction shiftZFunction = NoiseRouterHelper.getFunction(densityFunctions, NoiseRouterHelper.SHIFT_Z);

        DensityFunction temperature = DensityFunctions.shiftedNoise2d(shiftXFunction, shiftZFunction, 0.25, noiseParameters.getOrThrow(Noises.TEMPERATURE));
        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftXFunction, shiftZFunction, 0.25, noiseParameters.getOrThrow(Noises.VEGETATION));
        DensityFunction continents = NoiseRouterHelper.getFunction(densityFunctions, NoiseRouterHelper.CONTINENTS);
        DensityFunction erosion = NoiseRouterHelper.getFunction(densityFunctions, NoiseRouterHelper.EROSION);
        DensityFunction depth = NoiseRouterHelper.getFunction(densityFunctions, NoiseRouterHelper.DEPTH);
        DensityFunction ridges = NoiseRouterHelper.getFunction(densityFunctions, NoiseRouterHelper.RIDGES);

        // Approximate surface height
        DensityFunction noodle = NoiseRouterHelper.getFunction(densityFunctions, NoiseRouterHelper.NOODLE);
        DensityFunction factor = NoiseRouterHelper.getFunction(densityFunctions, NoiseRouterHelper.FACTOR);
        DensityFunction noiseGradientDensity = NoiseRouterHelper.noiseGradientDensity(DensityFunctions.cache2d(factor), depth);
        DensityFunction slideOverworldResult = NoiseRouterHelper.slideOverworld(false, DensityFunctions.add(noiseGradientDensity, DensityFunctions.constant(-0.703125)).clamp(-64.0, 64.0));
        DensityFunction postProcessedSlideOverworld = NoiseRouterHelper.postProcess(slideOverworldResult);
        DensityFunction minSlideOverworldNoodle = DensityFunctions.min(postProcessedSlideOverworld, noodle);
        DensityFunction initialDensityWithoutJaggedness = NoiseRouterHelper.slideOverworld(false, minSlideOverworldNoodle);

        // Ore veins
        DensityFunction veinGap = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_GAP));

        // Terrain
        DensityFunction finalDensity = moonTerrain(densityFunctions, noiseParameters);

        return create(
            barrierNoise,
            fluidLevelFloodednessNoise,
            fluidLevelSpreadNoise,
            lavaNoise,
            temperature,
            vegetation,
            continents,
            erosion,
            depth,
            ridges,
            initialDensityWithoutJaggedness,
            finalDensity,
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            veinGap
        );
    }

    public static DensityFunction moonTerrain(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters) {
        // TODO
        DensityFunction cratersFunction = NoiseRouterHelper.getFunction(
            densityFunctions,
            ModDensityFunctionProvider.CRATERS);

        DensityFunction slopedCheeseFunction = NoiseRouterHelper.getFunction(
            densityFunctions,
            NoiseRouterHelper.SLOPED_CHEESE);

        DensityFunction minSlopedCheeseEntrances = DensityFunctions.min(
            slopedCheeseFunction,
            DensityFunctions.mul(
                DensityFunctions.constant(5.0),
                NoiseRouterHelper.getFunction(
                    densityFunctions,
                    NoiseRouterHelper.ENTRANCES)));

        DensityFunction rangeChoiceSlopedCheese = DensityFunctions.rangeChoice(
            slopedCheeseFunction,
            -1000000.0,
            1.5625,
            minSlopedCheeseEntrances,
            NoiseRouterHelper.underground(densityFunctions, noiseParameters, slopedCheeseFunction));

        return DensityFunctions.min(
            NoiseRouterHelper.postProcess(
                NoiseRouterHelper.slideOverworld(
                    false,
                    rangeChoiceSlopedCheese)),
            NoiseRouterHelper.getFunction(
                densityFunctions,
                NoiseRouterHelper.NOODLE));
    }

    public static NoiseRouter none() {
        return create(
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero()
        );
    }

    public static NoiseRouter create(
        DensityFunction barrierNoise,
        DensityFunction fluidLevelFloodednessNoise,
        DensityFunction fluidLevelSpreadNoise,
        DensityFunction lavaNoise,
        DensityFunction temperature,
        DensityFunction vegetation,
        DensityFunction continents,
        DensityFunction erosion,
        DensityFunction depth,
        DensityFunction ridges,
        DensityFunction initialDensityWithoutJaggedness,
        DensityFunction finalDensity,
        DensityFunction veinToggle,
        DensityFunction veinRidged,
        DensityFunction veinGap) {
        return new NoiseRouter(barrierNoise, fluidLevelFloodednessNoise, fluidLevelSpreadNoise, lavaNoise, temperature, vegetation, continents, erosion, depth, ridges, initialDensityWithoutJaggedness, finalDensity, veinToggle, veinRidged, veinGap);
    }
}
