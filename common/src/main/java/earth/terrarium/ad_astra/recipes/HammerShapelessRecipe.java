package earth.terrarium.ad_astra.recipes;

import com.google.gson.JsonObject;
import earth.terrarium.ad_astra.items.HammerItem;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.registry.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class HammerShapelessRecipe extends ShapelessRecipe {

    public HammerShapelessRecipe(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> input) {
        super(id, group, output, input);
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingInventory) {
        return getResultItem().copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.HAMMER_SERIALIZER.get();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inventory) {

        NonNullList<ItemStack> defaultedList = NonNullList.withSize(inventory.getContainerSize(), ItemStack.EMPTY);

        // Damage the hammer in the recipe.
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            Item item = stack.getItem();
            if (item instanceof HammerItem) {
                ItemStack hammer = new ItemStack(ModItems.HAMMER.get(), 1);
                hammer.setDamageValue(stack.getDamageValue() + 1);
                if (hammer.getDamageValue() >= hammer.getMaxDamage())
                    continue;
                defaultedList.set(i, hammer);
            }
        }
        return defaultedList;
    }

    public static class Serializer extends ShapelessRecipe.Serializer {

        @Override
        public HammerShapelessRecipe fromJson(ResourceLocation id, JsonObject json) {
            ShapelessRecipe shapelessRecipe = super.fromJson(id, json);
            String group = GsonHelper.getAsString(json, "group", "");
            return new HammerShapelessRecipe(shapelessRecipe.getId(), group, shapelessRecipe.getResultItem(), shapelessRecipe.getIngredients());
        }

        @Override
        public HammerShapelessRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            ShapelessRecipe shapelessRecipe = super.fromNetwork(id, buf);
            return new HammerShapelessRecipe(shapelessRecipe.getId(), shapelessRecipe.getGroup(), shapelessRecipe.getResultItem(), shapelessRecipe.getIngredients());
        }
    }
}