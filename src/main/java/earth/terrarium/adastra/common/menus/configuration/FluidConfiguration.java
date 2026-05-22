package earth.terrarium.adastra.common.menus.configuration;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;

public record FluidConfiguration(
    int index,
    int x,
    int y,
    CommonStorage<FluidResource> container,
    int tank
) implements MenuConfiguration {

    @Override
    public ConfigurationType type() {
        return ConfigurationType.FLUID;
    }
}
