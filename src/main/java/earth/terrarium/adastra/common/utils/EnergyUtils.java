package earth.terrarium.adastra.common.utils;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext;
import earth.terrarium.common_storage_lib.energy.EnergyApi;
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
        return itemContext.stack();
    }
}
