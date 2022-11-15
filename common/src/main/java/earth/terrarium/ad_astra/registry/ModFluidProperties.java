package earth.terrarium.ad_astra.registry;


import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import earth.terrarium.botarium.api.registry.fluid.FluidData;
import earth.terrarium.botarium.api.registry.fluid.FluidProperties;
import earth.terrarium.botarium.api.registry.fluid.FluidRegistry;

public class ModFluidProperties {
    public static final FluidRegistry FLUID_TYPES = new FluidRegistry(AdAstra.MOD_ID);

    public static final FluidData OIL_FLUID = FLUID_TYPES.register("oil", FluidProperties.create()
            .still(new ModResourceLocation("block/fluid_oil_still"))
            .flowing(new ModResourceLocation("block/fluid_oil_flow"))
            .overlay(new ModResourceLocation("block/oil_overlay"))
            .viscosity(2000)
            .density(2000)
    );

    public static final FluidData FUEL_FLUID = FLUID_TYPES.register("fuel", FluidProperties.create()
            .still(new ModResourceLocation("block/fluid_fuel_still"))
            .flowing(new ModResourceLocation("block/fluid_fuel_flow"))
            .overlay(new ModResourceLocation("block/fuel_overlay"))
            .viscosity(1500)
            .density(1500)
    );

    public static final FluidData CRYO_FUEL_FLUID = FLUID_TYPES.register("cryo_fuel", FluidProperties.create()
            .still(new ModResourceLocation("block/fluid_cryo_fuel_still"))
            .flowing(new ModResourceLocation("block/fluid_cryo_fuel_flow"))
            .overlay(new ModResourceLocation("block/cryo_fuel_overlay"))
            .viscosity(71)
            .density(71)
            .temperature(-10000000)
    );

    public static final FluidData OXYGEN_FLUID = FLUID_TYPES.register("oxygen", FluidProperties.create()
            .still(new ModResourceLocation("block/fluid_oxygen_still"))
            .flowing(new ModResourceLocation("block/fluid_oxygen_still"))
            .overlay(new ModResourceLocation("block/fluid_oxygen_still"))
            .viscosity(0)
            .disablePlacing()
            .density(-1)
    );

    public static void init() {
        FLUID_TYPES.initialize();
    }
}
