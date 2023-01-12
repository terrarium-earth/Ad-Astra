package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public class ModFluids {
    public static final ResourcefulRegistry<Fluid> FLUIDS = ResourcefulRegistries.create(BuiltInRegistries.FLUID, AdAstra.MOD_ID);

    public static final RegistryEntry<FlowingFluid> OXYGEN = FLUIDS.register("oxygen", () -> new BotariumSourceFluid(ModFluidProperties.OXYGEN));
    public static final RegistryEntry<FlowingFluid> FLOWING_OXYGEN = FLUIDS.register("flowing_oxygen", () -> new BotariumFlowingFluid(ModFluidProperties.OXYGEN));

    public static final RegistryEntry<FlowingFluid> HYDROGEN = FLUIDS.register("hydrogen", () -> new BotariumSourceFluid(ModFluidProperties.HYDROGEN));
    public static final RegistryEntry<FlowingFluid> FLOWING_HYDROGEN = FLUIDS.register("flowing_hydrogen", () -> new BotariumFlowingFluid(ModFluidProperties.HYDROGEN));

    public static final RegistryEntry<FlowingFluid> CRUDE_OIL = FLUIDS.register("crude_oil", () -> new BotariumSourceFluid(ModFluidProperties.CRUDE_OIL));
    public static final RegistryEntry<FlowingFluid> FLOWING_CRUDE_OIL = FLUIDS.register("flowing_crude_oil", () -> new BotariumFlowingFluid(ModFluidProperties.CRUDE_OIL));

    public static final RegistryEntry<FlowingFluid> KEROSENE = FLUIDS.register("kerosene", () -> new BotariumSourceFluid(ModFluidProperties.KEROSENE));
    public static final RegistryEntry<FlowingFluid> FLOWING_KEROSENE = FLUIDS.register("flowing_kerosene", () -> new BotariumFlowingFluid(ModFluidProperties.KEROSENE));

    public static final RegistryEntry<FlowingFluid> ETRIONIC_FUEL = FLUIDS.register("etrionic_fuel", () -> new BotariumSourceFluid(ModFluidProperties.ETRIONIC_FUEL));
    public static final RegistryEntry<FlowingFluid> FLOWING_ETRIONIC_FUEL = FLUIDS.register("flowing_etrionic_fuel", () -> new BotariumFlowingFluid(ModFluidProperties.ETRIONIC_FUEL));

    public static final RegistryEntry<FlowingFluid> CRYOGENIC_FUEL = FLUIDS.register("cryogenic_fuel", () -> new BotariumSourceFluid(ModFluidProperties.CRYOGENIC_FUEL));
    public static final RegistryEntry<FlowingFluid> FLOWING_CRYOGENIC_FUEL = FLUIDS.register("flowing_cryogenic_fuel", () -> new BotariumFlowingFluid(ModFluidProperties.CRYOGENIC_FUEL));
}
