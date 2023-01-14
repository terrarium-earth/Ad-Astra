package earth.terrarium.ad_astra.common.registry;


import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.botarium.common.registry.fluid.FluidData;
import earth.terrarium.botarium.common.registry.fluid.FluidProperties;
import earth.terrarium.botarium.common.registry.fluid.FluidRegistry;
import net.minecraft.resources.ResourceLocation;

public class ModFluidProperties {
    public static final FluidRegistry FLUID_PROPERTIES = new FluidRegistry(AdAstra.MOD_ID);

    public static final FluidData OXYGEN = FLUID_PROPERTIES.register("oxygen", FluidProperties.create()
            .still(new ResourceLocation("minecraft:block/water_still"))
            .flowing(new ResourceLocation("minecraft:block/water_flow"))
            .overlay(new ResourceLocation("minecraft:block/water_overlay"))
            .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
            .viscosity(0)
            .density(-1)
            .disablePlacing()
            .tintColor(0xdae6f0)
            .canConvertToSource(false));

    public static final FluidData HYDROGEN = FLUID_PROPERTIES.register("hydrogen", FluidProperties.create()
            .still(new ResourceLocation("minecraft:block/water_still"))
            .flowing(new ResourceLocation("minecraft:block/water_flow"))
            .overlay(new ResourceLocation("minecraft:block/water_overlay"))
            .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
            .viscosity(0)
            .density(-1)
            .disablePlacing()
            .tintColor(0x89CFF0)
            .canConvertToSource(false));

    public static final FluidData CRUDE_OIL = FLUID_PROPERTIES.register("crude_oil", FluidProperties.create()
            .still(new ResourceLocation("minecraft:block/water_still"))
            .flowing(new ResourceLocation("minecraft:block/water_flow"))
            .overlay(new ResourceLocation("minecraft:block/water_overlay"))
            .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
            .viscosity(2000)
            .density(2000)
            .tintColor(0x373A36)
            .canConvertToSource(false));

    public static final FluidData KEROSENE = FLUID_PROPERTIES.register("kerosene", FluidProperties.create()
            .still(new ResourceLocation("minecraft:block/water_still"))
            .flowing(new ResourceLocation("minecraft:block/water_flow"))
            .overlay(new ResourceLocation("minecraft:block/water_overlay"))
            .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
            .viscosity(1500)
            .density(1500)
            .tintColor(0xE5292B)
            .canConvertToSource(false));

    public static final FluidData ETRIONIC_FUEL = FLUID_PROPERTIES.register("etrionic_fuel", FluidProperties.create()
            .still(new ResourceLocation("minecraft:block/water_still"))
            .flowing(new ResourceLocation("minecraft:block/water_flow"))
            .overlay(new ResourceLocation("minecraft:block/water_overlay"))
            .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
            .viscosity(1500)
            .density(1500)
            .tintColor(0xa7d3b6)
            .canConvertToSource(false));


    public static final FluidData CRYOGENIC_FUEL = FLUID_PROPERTIES.register("cryogenic_fuel", FluidProperties.create()
            .still(new ResourceLocation("minecraft:block/water_still"))
            .flowing(new ResourceLocation("minecraft:block/water_flow"))
            .overlay(new ResourceLocation("minecraft:block/water_overlay"))
            .screenOverlay(new ResourceLocation("textures/misc/underwater.png"))
            .viscosity(71)
            .density(71)
            .temperature(-10000000)
            .tintColor(0x6cfffa)
            .canConvertToSource(false));
}
