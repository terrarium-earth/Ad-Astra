package earth.terrarium.adastra.common.registry;


import com.teamresourceful.resourcefullib.common.fluid.data.FluidData;
import com.teamresourceful.resourcefullib.common.fluid.data.FluidProperties;
import com.teamresourceful.resourcefullib.common.fluid.registry.ResourcefulFluidRegistry;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistryType;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("UnstableApiUsage")
public final class ModFluidProperties {

    public static final ResourcefulFluidRegistry FLUID_PROPERTIES = ResourcefulRegistries.create(ResourcefulRegistryType.FLUID, AdAstra.MOD_ID);

    public static final RegistryEntry<FluidData> OXYGEN = FLUID_PROPERTIES.register("oxygen", FluidProperties.builder()
        .still(ResourceLocation.withDefaultNamespace("block/water_still"))
        .flowing(ResourceLocation.withDefaultNamespace("block/water_flow"))
        .overlay(ResourceLocation.withDefaultNamespace("block/water_overlay"))
        .screenOverlay(ResourceLocation.withDefaultNamespace("textures/misc/underwater.png"))
        .viscosity(0)
        .density(-1)
        .disablePlacing()
        .tintColor(0xffdae6f0)
        .canConvertToSource(false));

    public static final RegistryEntry<FluidData> HYDROGEN = FLUID_PROPERTIES.register("hydrogen", FluidProperties.builder()
        .still(ResourceLocation.withDefaultNamespace("block/water_still"))
        .flowing(ResourceLocation.withDefaultNamespace("block/water_flow"))
        .overlay(ResourceLocation.withDefaultNamespace("block/water_overlay"))
        .screenOverlay(ResourceLocation.withDefaultNamespace("textures/misc/underwater.png"))
        .viscosity(0)
        .density(-1)
        .disablePlacing()
        .tintColor(0xff89CFF0)
        .canConvertToSource(false));

    public static final RegistryEntry<FluidData> OIL = FLUID_PROPERTIES.register("oil", FluidProperties.builder()
        .still(ResourceLocation.withDefaultNamespace("block/water_still"))
        .flowing(ResourceLocation.withDefaultNamespace("block/water_flow"))
        .overlay(ResourceLocation.withDefaultNamespace("block/water_overlay"))
        .screenOverlay(ResourceLocation.withDefaultNamespace("textures/misc/underwater.png"))
        .viscosity(2000)
        .density(2000)
        .tintColor(0xff373A36)
        .canConvertToSource(false));

    public static final RegistryEntry<FluidData> FUEL = FLUID_PROPERTIES.register("fuel", FluidProperties.builder()
        .still(ResourceLocation.withDefaultNamespace("block/water_still"))
        .flowing(ResourceLocation.withDefaultNamespace("block/water_flow"))
        .overlay(ResourceLocation.withDefaultNamespace("block/water_overlay"))
        .screenOverlay(ResourceLocation.withDefaultNamespace("textures/misc/underwater.png"))
        .viscosity(1500)
        .density(1500)
        .tintColor(0xffE5292B)
        .canConvertToSource(false));

    public static final RegistryEntry<FluidData> CRYO_FUEL = FLUID_PROPERTIES.register("cryo_fuel", FluidProperties.builder()
        .still(ResourceLocation.withDefaultNamespace("block/water_still"))
        .flowing(ResourceLocation.withDefaultNamespace("block/water_flow"))
        .overlay(ResourceLocation.withDefaultNamespace("block/water_overlay"))
        .screenOverlay(ResourceLocation.withDefaultNamespace("textures/misc/underwater.png"))
        .viscosity(71)
        .density(71)
        .temperature(-196)
        .tintColor(0xff6cfffa)
        .canConvertToSource(false));
}
