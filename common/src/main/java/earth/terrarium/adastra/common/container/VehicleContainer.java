package earth.terrarium.adastra.common.container;

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
        for (int i = 0; i < containerNbt.size(); i++) {
            var stack = ItemStack.of(containerNbt.getCompound(i));
            setItem(i, stack);
        }
    }

    @Override
    public ListTag createTag() {
        ListTag containerNbt = new ListTag();
        for (int i = 0; i < getContainerSize(); i++) {
            var stack = getItem(i);
            containerNbt.add(stack.save(new CompoundTag()));
        }
        return containerNbt;
    }
}
