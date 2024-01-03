package earth.terrarium.adastra.common.menus.slots;

import earth.terrarium.adastra.common.container.SingleSlotContainer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class PredicateSlot extends Slot {

    private final Predicate<ItemStack> predicate;

    public PredicateSlot(Container container, int slot, int x, int y, Predicate<ItemStack> predicate) {
        super(container, slot, x, y);
        this.predicate = predicate;
    }

    public static <T extends Recipe<Container>> PredicateSlot ofRecipeInput(Container container, int slot, int x, int y, Level level, RecipeType<T> type) {
        final RecipeManager recipeManager = level.getRecipeManager();
        final SingleSlotContainer inventory = new SingleSlotContainer(slot);
        return new PredicateSlot(container, slot, x, y, item -> {
            inventory.setItem(item);
            return recipeManager.getRecipeFor(type, inventory, level).isPresent();
        });
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return predicate.test(stack);
    }
}
