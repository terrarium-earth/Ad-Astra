package earth.terrarium.adastra.common.blockentities.base;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface BasicContainer extends Container {

    NonNullList<ItemStack> items();

    void update();

    @Override
    default boolean isEmpty() {
        return items().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    default @NotNull ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(items(), slot, amount);
        update();
        return stack;
    }

    @Override
    default void setItem(int slot, @NotNull ItemStack stack) {
        items().set(slot, stack);
        update();
    }

    @Override
    default @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items(), slot);
    }

    @Override
    default @NotNull ItemStack getItem(int slot) {
        return items().get(slot);
    }

    @Override
    default int getContainerSize() {
        return items().size();
    }

    @Override
    default void clearContent() {
        items().clear();
        update();
    }
}
