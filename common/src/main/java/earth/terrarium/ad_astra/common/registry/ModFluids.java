package earth.terrarium.ad_astra.common.registry;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.botarium.api.registry.RegistryHolder;
import earth.terrarium.botarium.api.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.api.registry.fluid.BotariumSourceFluid;
import net.minecraft.core.Registry;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class ModFluids {
    public static final RegistryHolder<Fluid> FLUIDS = new RegistryHolder<>(Registry.FLUID, AdAstra.MOD_ID);

    public static final Supplier<FlowingFluid> OIL = FLUIDS.register("oil", () -> new BotariumSourceFluid(ModFluidProperties.OIL_FLUID));
    public static final Supplier<FlowingFluid> FLOWING_OIL = FLUIDS.register("flowing_oil", () -> new BotariumFlowingFluid(ModFluidProperties.OIL_FLUID));

    public static final Supplier<FlowingFluid> FUEL = FLUIDS.register("fuel", () -> new BotariumSourceFluid(ModFluidProperties.FUEL_FLUID));
    public static final Supplier<FlowingFluid> FLOWING_FUEL = FLUIDS.register("flowing_fuel", () -> new BotariumFlowingFluid(ModFluidProperties.FUEL_FLUID));

    public static final Supplier<FlowingFluid> CRYO_FUEL = FLUIDS.register("cryo_fuel", () -> new BotariumSourceFluid(ModFluidProperties.CRYO_FUEL_FLUID));
    public static final Supplier<FlowingFluid> FLOWING_CRYO_FUEL = FLUIDS.register("flowing_cryo_fuel", () -> new BotariumFlowingFluid(ModFluidProperties.CRYO_FUEL_FLUID));

    public static final Supplier<FlowingFluid> OXYGEN = FLUIDS.register("oxygen", () -> new BotariumSourceFluid(ModFluidProperties.OXYGEN_FLUID));
    public static final Supplier<FlowingFluid> FLOWING_OXYGEN = FLUIDS.register("flowing_oxygen", () -> new BotariumFlowingFluid(ModFluidProperties.OXYGEN_FLUID));
}