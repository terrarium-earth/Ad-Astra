package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.fluid.ResourcefulFlowingFluid;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public final class ModFluids {

    public static final ResourcefulRegistry<Fluid> FLUIDS = ResourcefulRegistries.create(BuiltInRegistries.FLUID, AdAstra.MOD_ID);

    public static final RegistryEntry<FlowingFluid> OXYGEN = FLUIDS.register("oxygen", () -> new ResourcefulFlowingFluid.Still(ModFluidProperties.OXYGEN.get()));
    public static final RegistryEntry<FlowingFluid> FLOWING_OXYGEN = FLUIDS.register("flowing_oxygen", () -> new ResourcefulFlowingFluid.Flowing(ModFluidProperties.OXYGEN.get()));

    public static final RegistryEntry<FlowingFluid> HYDROGEN = FLUIDS.register("hydrogen", () -> new ResourcefulFlowingFluid.Still(ModFluidProperties.HYDROGEN.get()));
    public static final RegistryEntry<FlowingFluid> FLOWING_HYDROGEN = FLUIDS.register("flowing_hydrogen", () -> new ResourcefulFlowingFluid.Flowing(ModFluidProperties.HYDROGEN.get()));

    public static final RegistryEntry<FlowingFluid> OIL = FLUIDS.register("oil", () -> new ResourcefulFlowingFluid.Still(ModFluidProperties.OIL.get()));
    public static final RegistryEntry<FlowingFluid> FLOWING_OIL = FLUIDS.register("flowing_oil", () -> new ResourcefulFlowingFluid.Flowing(ModFluidProperties.OIL.get()));

    public static final RegistryEntry<FlowingFluid> FUEL = FLUIDS.register("fuel", () -> new ResourcefulFlowingFluid.Still(ModFluidProperties.FUEL.get()));
    public static final RegistryEntry<FlowingFluid> FLOWING_FUEL = FLUIDS.register("flowing_fuel", () -> new ResourcefulFlowingFluid.Flowing(ModFluidProperties.FUEL.get()));

    public static final RegistryEntry<FlowingFluid> CRYO_FUEL = FLUIDS.register("cryo_fuel", () -> new ResourcefulFlowingFluid.Still(ModFluidProperties.CRYO_FUEL.get()));
    public static final RegistryEntry<FlowingFluid> FLOWING_CRYO_FUEL = FLUIDS.register("flowing_cryo_fuel", () -> new ResourcefulFlowingFluid.Flowing(ModFluidProperties.CRYO_FUEL.get()));
}
