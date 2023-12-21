package earth.terrarium.adastra.common.entities.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class VehicleContainer extends SimpleContainer {

    public VehicleContainer(int size) {
        super(size);
    }

    @Override
    public void fromTag(ListTag containerNbt) {
        for (int slot = 0; slot < containerNbt.size(); ++slot) {
            var stack = ItemStack.of(containerNbt.getCompound(slot));
            setItem(slot, stack);
        }
    }

    @Override
    public ListTag createTag() {
        ListTag containerNbt = new ListTag();
        for (int slot = 0; slot < getContainerSize(); ++slot) {
            var stack = getItem(slot);
            containerNbt.add(stack.save(new CompoundTag()));
        }
        return containerNbt;
    }
}
