package earth.terrarium.ad_astra.common.block.machine;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

public interface BasicContainer extends Container {

    NonNullList<ItemStack> getItems();

    void update();

    @Override
    default boolean isEmpty() {
        return getItems().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    default ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(getItems(), slot, amount);
        update();
        return stack;
    }

    @Override
    default void setItem(int slot, ItemStack stack) {
        getItems().set(slot, stack);
        update();
    }

    @Override
    default ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(getItems(), slot);
    }

    @Override
    default ItemStack getItem(int slot) {
        return getItems().get(slot);
    }

    @Override
    default int getContainerSize() {
        return getItems().size();
    }

    @Override
    default void clearContent() {
        getItems().clear();
        update();
    }
}
