package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.fluid.ResourcefulFlowingFluid;
import com.teamresourceful.resourcefullib.common.fluid.ResourcefulLiquidBlock;
import com.teamresourceful.resourcefullib.common.fluid.data.FluidData;
import com.teamresourceful.resourcefullib.common.fluid.data.FluidProperties;
import com.teamresourceful.resourcefullib.common.fluid.registry.ResourcefulFluidRegistry;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistryType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blocks.fluids.CryoFuelLiquidBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;

@SuppressWarnings("UnstableApiUsage")
public final class ModFluids {

    public static final ResourcefulRegistry<Fluid> FLUIDS = ResourcefulRegistries.create(BuiltInRegistries.FLUID, AdAstra.MOD_ID);
    public static final ResourcefulFluidRegistry FLUID_TYPES = ResourcefulRegistries.create(ResourcefulRegistryType.FLUID, AdAstra.MOD_ID);

    public static final ResourceLocation STILL = ResourceLocation.withDefaultNamespace("block/water_still");
    public static final ResourceLocation FLOWING = ResourceLocation.withDefaultNamespace("block/water_flow");
    public static final ResourceLocation OVERLAY = ResourceLocation.withDefaultNamespace("block/water_overlay");
    public static final ResourceLocation SCREEN_OVERLAY = ResourceLocation.withDefaultNamespace("textures/misc/underwater.png");

    public static final RegistryEntry<FluidData> OXYGEN_FLUID_TYPE = FLUID_TYPES.register("oxygen", FluidProperties.builder()
        .still(STILL)
        .flowing(FLOWING)
        .overlay(OVERLAY)
        .screenOverlay(SCREEN_OVERLAY)
        .viscosity(0)
        .density(-1)
        .disablePlacing()
        .tintColor(0xffdae6f0)
        .canConvertToSource(false));

    public static final RegistryEntry<FluidData> HYDROGEN_FLUID_TYPE = FLUID_TYPES.register("hydrogen", FluidProperties.builder()
        .still(STILL)
        .flowing(FLOWING)
        .overlay(OVERLAY)
        .screenOverlay(SCREEN_OVERLAY)
        .viscosity(0)
        .density(-1)
        .disablePlacing()
        .tintColor(0xff89CFF0)
        .canConvertToSource(false));

    public static final RegistryEntry<FluidData> OIL_FLUID_TYPE = FLUID_TYPES.register("oil", FluidProperties.builder()
        .still(STILL)
        .flowing(FLOWING)
        .overlay(OVERLAY)
        .screenOverlay(SCREEN_OVERLAY)
        .viscosity(2000)
        .density(2000)
        .tintColor(0xff373A36)
        .canConvertToSource(false));

    public static final RegistryEntry<FluidData> FUEL_FLUID_TYPE = FLUID_TYPES.register("fuel", FluidProperties.builder()
        .still(STILL)
        .flowing(FLOWING)
        .overlay(OVERLAY)
        .screenOverlay(SCREEN_OVERLAY)
        .viscosity(1500)
        .density(1500)
        .tintColor(0xffE5292B)
        .canConvertToSource(false));

    public static final RegistryEntry<FluidData> CRYO_FUEL_FLUID_TYPE = FLUID_TYPES.register("cryo_fuel", FluidProperties.builder()
        .still(STILL)
        .flowing(FLOWING)
        .overlay(OVERLAY)
        .screenOverlay(SCREEN_OVERLAY)
        .viscosity(71)
        .density(71)
        .temperature(-196)
        .tintColor(0xff6cfffa)
        .canConvertToSource(false));

    public static final RegistryEntry<FlowingFluid> OXYGEN = FLUIDS.register("oxygen", () -> new ResourcefulFlowingFluid.Still(ModFluids.OXYGEN_FLUID_TYPE.get()));
    public static final RegistryEntry<FlowingFluid> FLOWING_OXYGEN = FLUIDS.register("flowing_oxygen", () -> new ResourcefulFlowingFluid.Flowing(ModFluids.OXYGEN_FLUID_TYPE.get()));

    public static final RegistryEntry<FlowingFluid> HYDROGEN = FLUIDS.register("hydrogen", () -> new ResourcefulFlowingFluid.Still(ModFluids.HYDROGEN_FLUID_TYPE.get()));
    public static final RegistryEntry<FlowingFluid> FLOWING_HYDROGEN = FLUIDS.register("flowing_hydrogen", () -> new ResourcefulFlowingFluid.Flowing(ModFluids.HYDROGEN_FLUID_TYPE.get()));

    public static final RegistryEntry<FlowingFluid> OIL = FLUIDS.register("oil", () -> new ResourcefulFlowingFluid.Still(ModFluids.OIL_FLUID_TYPE.get()));
    public static final RegistryEntry<FlowingFluid> FLOWING_OIL = FLUIDS.register("flowing_oil", () -> new ResourcefulFlowingFluid.Flowing(ModFluids.OIL_FLUID_TYPE.get()));

    public static final RegistryEntry<FlowingFluid> FUEL = FLUIDS.register("fuel", () -> new ResourcefulFlowingFluid.Still(ModFluids.FUEL_FLUID_TYPE.get()));
    public static final RegistryEntry<FlowingFluid> FLOWING_FUEL = FLUIDS.register("flowing_fuel", () -> new ResourcefulFlowingFluid.Flowing(ModFluids.FUEL_FLUID_TYPE.get()));

    public static final RegistryEntry<FlowingFluid> CRYO_FUEL = FLUIDS.register("cryo_fuel", () -> new ResourcefulFlowingFluid.Still(ModFluids.CRYO_FUEL_FLUID_TYPE.get()));
    public static final RegistryEntry<FlowingFluid> FLOWING_CRYO_FUEL = FLUIDS.register("flowing_cryo_fuel", () -> new ResourcefulFlowingFluid.Flowing(ModFluids.CRYO_FUEL_FLUID_TYPE.get()));

    public static final RegistryEntry<LiquidBlock> OXYGEN_BLOCK = ModBlocks.FLUIDS.register("oxygen", () -> new ResourcefulLiquidBlock(ModFluids.OXYGEN_FLUID_TYPE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final RegistryEntry<LiquidBlock> HYDROGEN_BLOCK = ModBlocks.FLUIDS.register("hydrogen", () -> new ResourcefulLiquidBlock(ModFluids.HYDROGEN_FLUID_TYPE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
    public static final RegistryEntry<LiquidBlock> OIL_BLOCK = ModBlocks.FLUIDS.register("oil", () -> new ResourcefulLiquidBlock(ModFluids.OIL_FLUID_TYPE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryEntry<LiquidBlock> FUEL_BLOCK = ModBlocks.FLUIDS.register("fuel", () -> new ResourcefulLiquidBlock(ModFluids.FUEL_FLUID_TYPE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).mapColor(MapColor.COLOR_RED)));
    public static final RegistryEntry<LiquidBlock> CRYO_FUEL_BLOCK = ModBlocks.FLUIDS.register("cryo_fuel", () -> new CryoFuelLiquidBlock(ModFluids.CRYO_FUEL_FLUID_TYPE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).mapColor(MapColor.COLOR_CYAN)));


}
