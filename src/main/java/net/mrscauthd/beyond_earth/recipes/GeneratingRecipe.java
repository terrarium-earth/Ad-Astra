package net.mrscauthd.beyond_earth.recipes;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.registry.ModRecipes;

public class GeneratingRecipe extends ModRecipe {

    public GeneratingRecipe(Identifier id, Ingredient input, ItemStack output, short cookTime) {
        super(id, input, output, cookTime);
    }

    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.GENERATING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.GENERATING_RECIPE;
    }

    public static class Serializer implements RecipeSerializer<GeneratingRecipe> {

        @Override
        public GeneratingRecipe read(Identifier id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(json.get("input"));
            short cookTime = json.get("burnTime").getAsShort();
            return new GeneratingRecipe(id, input, ItemStack.EMPTY, cookTime);
        }

        @Override
        public GeneratingRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient input = Ingredient.fromPacket(buf);
            short cookTime = buf.readShort();
            return new GeneratingRecipe(id, input, ItemStack.EMPTY, cookTime);
        }

        @Override
        public void write(PacketByteBuf buf, GeneratingRecipe recipe) {
            recipe.getInputIngredient().write(buf);
            buf.writeShort(recipe.cookTime);
        }
    }
}