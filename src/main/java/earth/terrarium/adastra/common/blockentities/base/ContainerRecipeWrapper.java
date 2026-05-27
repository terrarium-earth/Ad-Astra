package earth.terrarium.adastra.common.blockentities.base;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ContainerRecipeWrapper(Container container) implements RecipeInput {

    @Override
    public ItemStack getItem(int i) {
        return container().getItem(i);
    }

    @Override
    public int size() {
        return container().getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
