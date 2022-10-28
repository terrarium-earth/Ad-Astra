package earth.terrarium.ad_astra.recipes;

import com.google.gson.JsonObject;
import earth.terrarium.ad_astra.items.HammerItem;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.registry.ModRecipes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class HammerShapelessRecipe extends ShapelessRecipe {

    public HammerShapelessRecipe(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input) {
        super(id, group, output, input);
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        return getOutput().copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.HAMMER_SERIALIZER.get();
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {

        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        // Damage the hammer in the recipe.
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            Item item = stack.getItem();
            if (item instanceof HammerItem) {
                ItemStack hammer = new ItemStack(ModItems.HAMMER.get(), 1);
                hammer.setDamage(stack.getDamage() + 1);
                if (hammer.getDamage() >= hammer.getMaxDamage())
                    continue;
                defaultedList.set(i, hammer);
            }
        }
        return defaultedList;
    }

    public static class Serializer extends ShapelessRecipe.Serializer {

        @Override
        public HammerShapelessRecipe read(Identifier id, JsonObject json) {
            ShapelessRecipe shapelessRecipe = super.read(id, json);
            String group = JsonHelper.getString(json, "group", "");
            return new HammerShapelessRecipe(shapelessRecipe.getId(), group, shapelessRecipe.getOutput(), shapelessRecipe.getIngredients());
        }

        @Override
        public HammerShapelessRecipe read(Identifier id, PacketByteBuf buf) {
            ShapelessRecipe shapelessRecipe = super.read(id, buf);
            return new HammerShapelessRecipe(shapelessRecipe.getId(), shapelessRecipe.getGroup(), shapelessRecipe.getOutput(), shapelessRecipe.getIngredients());
        }
    }
}