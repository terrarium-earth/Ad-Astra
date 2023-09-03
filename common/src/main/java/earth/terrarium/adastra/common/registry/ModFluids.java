package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;

public final class ModFluids {
    public static final ResourcefulRegistry<Fluid> FLUIDS = ResourcefulRegistries.create(BuiltInRegistries.FLUID, AdAstra.MOD_ID);

    public static final RegistryEntry<Fluid> OXYGEN = FLUIDS.register("oxygen", () -> new BotariumSourceFluid(ModFluidProperties.OXYGEN));
    public static final RegistryEntry<Fluid> FLOWING_OXYGEN = FLUIDS.register("flowing_oxygen", () -> new BotariumFlowingFluid(ModFluidProperties.OXYGEN));

    public static final RegistryEntry<Fluid> HYDROGEN = FLUIDS.register("hydrogen", () -> new BotariumSourceFluid(ModFluidProperties.HYDROGEN));
    public static final RegistryEntry<Fluid> FLOWING_HYDROGEN = FLUIDS.register("flowing_hydrogen", () -> new BotariumFlowingFluid(ModFluidProperties.HYDROGEN));

    public static final RegistryEntry<Fluid> OIL = FLUIDS.register("oil", () -> new BotariumSourceFluid(ModFluidProperties.OIL));
    public static final RegistryEntry<Fluid> FLOWING_OIL = FLUIDS.register("flowing_oil", () -> new BotariumFlowingFluid(ModFluidProperties.OIL));
}
