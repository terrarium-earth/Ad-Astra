package earth.terrarium.ad_astra.blocks.fluid;


import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import earth.terrarium.ad_astra.registry.ModFluids;
import earth.terrarium.ad_astra.util.ModResourceLocation;

public class ModFluidAttributes {
    public static final ArchitecturyFluidAttributes OIL_FLUID_ATTRIBUTES = SimpleArchitecturyFluidAttributes.of(ModFluids.FLOWING_OIL, ModFluids.OIL)
//            .block(ModBlocks.OIL_BLOCK)
//            .bucketItem(ModItems.OIL_BUCKET)
            .density(2000)
            .viscosity(2000)
            .sourceTexture(new ModResourceLocation("block/fluid_oil_still"))
            .flowingTexture(new ModResourceLocation("block/fluid_oil_flow"))
            .overlayTexture(new ModResourceLocation("block/oil_overlay"));


    public static final ArchitecturyFluidAttributes FUEL_FLUID_ATTRIBUTES = SimpleArchitecturyFluidAttributes.of(ModFluids.FLOWING_FUEL, ModFluids.FUEL)
//            .block(ModBlocks.FUEL_BLOCK)
//            .bucketItem(ModItems.FUEL_BUCKET)
            .density(1500)
            .viscosity(1500)
            .sourceTexture(new ModResourceLocation("block/fluid_fuel_still"))
            .flowingTexture(new ModResourceLocation("block/fluid_fuel_flow"))
            .overlayTexture(new ModResourceLocation("block/fuel_overlay"));

    public static final ArchitecturyFluidAttributes CRYO_FUEL_FLUID_ATTRIBUTES = SimpleArchitecturyFluidAttributes.of(ModFluids.FLOWING_CRYO_FUEL, ModFluids.CRYO_FUEL)
//            .block(ModBlocks.CRYO_FUEL_BLOCK)
//            .bucketItem(ModItems.CRYO_FUEL_BUCKET)
            .density(71)
            .viscosity(71)
            .temperature(-10000000)
            .sourceTexture(new ModResourceLocation("block/fluid_cryo_fuel_still"))
            .flowingTexture(new ModResourceLocation("block/fluid_cryo_fuel_flow"))
            .overlayTexture(new ModResourceLocation("block/cryo_fuel_overlay"));

    public static final ArchitecturyFluidAttributes OXYGEN_FLUID_ATTRIBUTES = SimpleArchitecturyFluidAttributes.of(ModFluids.FLOWING_OXYGEN, ModFluids.OXYGEN)
//            .block(ModBlocks.OXYGEN_BLOCK)
//            .bucketItem(ModItems.OXYGEN_BUCKET)
            .density(-1)
            .viscosity(0)
            .sourceTexture(new ModResourceLocation("block/fluid_oxygen_still"))
            .flowingTexture(new ModResourceLocation("block/fluid_oxygen_still"))
            .overlayTexture(new ModResourceLocation("block/fluid_oxygen_still"));
}
