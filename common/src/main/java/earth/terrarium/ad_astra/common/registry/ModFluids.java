package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.botarium.api.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.api.registry.fluid.BotariumSourceFluid;
import net.minecraft.core.Registry;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public class ModFluids {
    public static final ResourcefulRegistry<Fluid> FLUIDS = ResourcefulRegistries.create(Registry.FLUID, AdAstra.MOD_ID);

    public static final RegistryEntry<FlowingFluid> OIL = FLUIDS.register("oil", () -> new BotariumSourceFluid(ModFluidProperties.OIL_FLUID));
    public static final RegistryEntry<FlowingFluid> FLOWING_OIL = FLUIDS.register("flowing_oil", () -> new BotariumFlowingFluid(ModFluidProperties.OIL_FLUID));

    public static final RegistryEntry<FlowingFluid> FUEL = FLUIDS.register("fuel", () -> new BotariumSourceFluid(ModFluidProperties.FUEL_FLUID));
    public static final RegistryEntry<FlowingFluid> FLOWING_FUEL = FLUIDS.register("flowing_fuel", () -> new BotariumFlowingFluid(ModFluidProperties.FUEL_FLUID));

    public static final RegistryEntry<FlowingFluid> CRYO_FUEL = FLUIDS.register("cryo_fuel", () -> new BotariumSourceFluid(ModFluidProperties.CRYO_FUEL_FLUID));
    public static final RegistryEntry<FlowingFluid> FLOWING_CRYO_FUEL = FLUIDS.register("flowing_cryo_fuel", () -> new BotariumFlowingFluid(ModFluidProperties.CRYO_FUEL_FLUID));

    public static final RegistryEntry<FlowingFluid> OXYGEN = FLUIDS.register("oxygen", () -> new BotariumSourceFluid(ModFluidProperties.OXYGEN_FLUID));
    public static final RegistryEntry<FlowingFluid> FLOWING_OXYGEN = FLUIDS.register("flowing_oxygen", () -> new BotariumFlowingFluid(ModFluidProperties.OXYGEN_FLUID));
}