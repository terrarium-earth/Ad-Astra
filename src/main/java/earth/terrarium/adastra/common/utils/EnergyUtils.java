package earth.terrarium.adastra.common.utils;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext;
import earth.terrarium.common_storage_lib.energy.EnergyApi;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EnergyUtils {

    public static ItemStack energyFilledItem(RegistryEntry<Item> item) {
        return energyFilledItem(item.get().getDefaultInstance());
    }

    public static ItemStack energyFilledItem(ItemStack stack) {
        ModifyOnlyContext itemContext = new ModifyOnlyContext(stack);
        var container = itemContext.find(EnergyApi.ITEM);
        if (container != null) {
            container.insert(container.getCapacity(), false);
//            stack.getOrCreateTagElement(Botarium.BOTARIUM_DATA)
//                .putLong("Energy", container.getMaxCapacity());
        }
        return stack;
    }

    public static long moveEnergy(ValueStorage from, ValueStorage to, long amount, boolean simulate) {
        long extracted = from.extract(amount, true);
        long inserted = to.insert(extracted, true);
        long simulatedExtraction = from.extract(inserted, true);
        if (!simulate && inserted > 0 && simulatedExtraction == inserted) {
            from.extract(inserted, false);
            to.insert(inserted, false);
        }
        return Math.max(0, inserted);
    }
}
