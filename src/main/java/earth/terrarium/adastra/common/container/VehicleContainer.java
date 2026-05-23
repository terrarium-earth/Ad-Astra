package earth.terrarium.adastra.common.container;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

/**
 * Simple container that retains the order stacks were inserted in.
 */
public class VehicleContainer extends SimpleContainer {

    public VehicleContainer(int size) {
        super(size);
    }

    @Override
    public void fromTag(ListTag containerNbt, HolderLookup.Provider provider) {
        for (int i = 0; i < containerNbt.size(); i++) {
            var stack = ItemStack.parseOptional(provider, containerNbt.getCompound(i));
            setItem(i, stack);
        }
    }

    @Override
    public ListTag createTag(HolderLookup.Provider provider) {
        ListTag containerNbt = new ListTag();
        for (int i = 0; i < getContainerSize(); i++) {
            var stack = getItem(i);
            containerNbt.add(stack.save(provider));
        }
        return containerNbt;
    }
}
