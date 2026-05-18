package earth.terrarium.adastra.common.utils;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.common.config.machines.MachineTypeConfigObject;
import earth.terrarium.botarium.Botarium;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.energy.impl.ExtractOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EnergyUtils {

    public static InsertOnlyEnergyContainer machineInsertOnlyEnergy(MachineTypeConfigObject config) {
        return new InsertOnlyEnergyContainer(config.energyCapacity, config.maxEnergyInOut);
    }

    public static ExtractOnlyEnergyContainer machineExtractOnlyEnergy(MachineTypeConfigObject config) {
        return new ExtractOnlyEnergyContainer(config.energyCapacity, config.maxEnergyInOut);
    }

    public static ItemStack energyFilledItem(RegistryEntry<Item> item) {
        return energyFilledItem(item.get().getDefaultInstance());
    }

    public static ItemStack energyFilledItem(ItemStack stack) {
        var holder = new ItemStackHolder(stack);
        var container = EnergyContainer.of(holder);
        if (container != null) {
            container.setEnergy(container.getMaxCapacity());
            stack.getOrCreateTagElement(Botarium.BOTARIUM_DATA)
                .putLong("Energy", container.getMaxCapacity());
        }
        return stack;
    }

    public static long moveEnergy(ValueStorage from, ValueStorage to, long amount, boolean simulate) {
        long extracted = from.extract(amount, true);
        long inserted = to.extract(extracted, true);
        long simulatedExtraction = from.extract(inserted, true);
        if (!simulate && inserted > 0 && simulatedExtraction == inserted) {
            from.extract(inserted, false);
            to.insert(inserted, false);
        }
        return Math.max(0, inserted);
    }
}
