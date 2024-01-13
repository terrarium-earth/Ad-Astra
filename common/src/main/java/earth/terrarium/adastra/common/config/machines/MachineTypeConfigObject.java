package earth.terrarium.adastra.common.config.machines;

import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigObject;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;

@ConfigObject
public class MachineTypeConfigObject {

    @ConfigEntry(
        id = "maxEnergyInOut",
        type = EntryType.LONG,
        translation = "config.ad_astra.machine.maxEnergyInOut"
    )
    public long maxEnergyInOut;

    @ConfigEntry(
        id = "energyCapacity",
        type = EntryType.LONG,
        translation = "config.ad_astra.machine.energyCapacity"
    )
    public long energyCapacity;

    @ConfigEntry(
        id = "fluidCapacity",
        type = EntryType.LONG,
        translation = "config.ad_astra.machine.fluidCapacity"
    )
    public long fluidCapacity;

    public MachineTypeConfigObject(long maxEnergyInOut, long energyCapacity, long fluidCapacity) {
        this.maxEnergyInOut = maxEnergyInOut;
        this.energyCapacity = energyCapacity;
        this.fluidCapacity = fluidCapacity;
    }
}
