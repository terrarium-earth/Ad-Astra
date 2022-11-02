package earth.terrarium.ad_astra.registry;

import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.blocks.fluid.ModFluidAttributes;
import net.minecraft.core.Registry;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(AdAstra.MOD_ID, Registry.FLUID_REGISTRY);

    public static final RegistrySupplier<FlowingFluid> OIL_STILL = FLUIDS.register("oil", () -> new ArchitecturyFlowingFluid.Source(ModFluidAttributes.OIL_FLUID_ATTRIBUTES));
    public static final RegistrySupplier<FlowingFluid> FLOWING_OIL = FLUIDS.register("flowing_oil", () -> new ArchitecturyFlowingFluid.Flowing(ModFluidAttributes.OIL_FLUID_ATTRIBUTES));

    public static final RegistrySupplier<FlowingFluid> FUEL_STILL = FLUIDS.register("fuel", ( ) -> new ArchitecturyFlowingFluid.Source(ModFluidAttributes.FUEL_FLUID_ATTRIBUTES));
    public static final RegistrySupplier<FlowingFluid> FLOWING_FUEL = FLUIDS.register("flowing_fuel", () -> new ArchitecturyFlowingFluid.Flowing(ModFluidAttributes.FUEL_FLUID_ATTRIBUTES));

    public static final RegistrySupplier<FlowingFluid> CRYO_FUEL_STILL = FLUIDS.register("cryo_fuel", () -> new ArchitecturyFlowingFluid.Source(ModFluidAttributes.CRYO_FUEL_FLUID_ATTRIBUTES));
    public static final RegistrySupplier<FlowingFluid> FLOWING_CRYO_FUEL = FLUIDS.register("flowing_cryo_fuel", () -> new ArchitecturyFlowingFluid.Flowing(ModFluidAttributes.CRYO_FUEL_FLUID_ATTRIBUTES));

    public static final RegistrySupplier<FlowingFluid> OXYGEN_STILL = FLUIDS.register("oxygen", () -> new ArchitecturyFlowingFluid.Source(ModFluidAttributes.OXYGEN_FLUID_ATTRIBUTES));
    public static final RegistrySupplier<FlowingFluid> FLOWING_OXYGEN = FLUIDS.register("flowing_oxygen", () -> new ArchitecturyFlowingFluid.Flowing(ModFluidAttributes.OXYGEN_FLUID_ATTRIBUTES));

    public static void register() {
        FLUIDS.register();
    }
}