package earth.terrarium.adastra.common.utils;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.botarium.Botarium;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EnergyUtils {
    public static ItemStack energyFilledItem(RegistryEntry<Item> item) {
        return energyFilledItem(item.get().getDefaultInstance());
    }

    public static ItemStack energyFilledItem(ItemStack stack) {
        var holder = new ItemStackHolder(stack);
        var container = EnergyApi.getItemEnergyContainer(holder);
        if (container != null) {
            container.setEnergy(container.getMaxCapacity());
            stack.getOrCreateTag()
                .getCompound(Botarium.BOTARIUM_DATA)
                .putLong("Energy", container.getMaxCapacity());
        }
        return holder.getStack();
    }
}
