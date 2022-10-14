package com.github.alexnijjar.ad_astra.blocks.fluid;

import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.registry.ModFluids;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;

public class ModFluidAttributes {
    public static final ArchitecturyFluidAttributes OIL_FLUID_ATTRIBUTES = SimpleArchitecturyFluidAttributes.of(ModFluids.FLOWING_OIL, ModFluids.OIL_STILL)
            .block(ModBlocks.OIL_BLOCK)
            .bucketItem(ModItems.OIL_BUCKET)
            .density(2000)
            .viscosity(2000)
            .sourceTexture(new ModIdentifier("block/fluid_oil_still"))
            .flowingTexture(new ModIdentifier("block/fluid_oil_flow"))
            .overlayTexture(new ModIdentifier("block/oil_overlay"));


    public static final ArchitecturyFluidAttributes FUEL_FLUID_ATTRIBUTES = SimpleArchitecturyFluidAttributes.of(ModFluids.FLOWING_FUEL, ModFluids.FUEL_STILL)
            .block(ModBlocks.FUEL_BLOCK)
            .bucketItem(ModItems.FUEL_BUCKET)
            .density(1500)
            .viscosity(1500)
            .sourceTexture(new ModIdentifier("block/fluid_fuel_still"))
            .flowingTexture(new ModIdentifier("block/fluid_fuel_flow"))
            .overlayTexture(new ModIdentifier("block/fuel_overlay"));

    public static final ArchitecturyFluidAttributes CRYO_FUEL_FLUID_ATTRIBUTES = SimpleArchitecturyFluidAttributes.of(ModFluids.FLOWING_CRYO_FUEL, ModFluids.CRYO_FUEL_STILL)
            .block(ModBlocks.FUEL_BLOCK)
            .bucketItem(ModItems.FUEL_BUCKET)
            .density(71)
            .viscosity(71)
            .sourceTexture(new ModIdentifier("block/fluid_cryo_fuel_still"))
            .flowingTexture(new ModIdentifier("block/fluid_cryo_fuel_flow"))
            .overlayTexture(new ModIdentifier("block/cryo_fuel_overlay"));

    public static final ArchitecturyFluidAttributes OXYGEN_FLUID_ATTRIBUTES = SimpleArchitecturyFluidAttributes.of(ModFluids.FLOWING_CRYO_FUEL, ModFluids.OXYGEN_STILL)
            .block(ModBlocks.OXYGEN_BLOCK)
            .bucketItem(ModItems.OXYGEN_BUCKET)
            .density(-1)
            .viscosity(0)
            .sourceTexture(new ModIdentifier("block/fluid_oxygen_still"))
            .flowingTexture(new ModIdentifier("block/fluid_oxygen_flow"))
            .overlayTexture(new ModIdentifier("block/oxygen_overlay"));
}
