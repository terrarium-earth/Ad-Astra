package net.mrscauthd.beyond_earth.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.registry.ModRecipeTypes;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public record GeneratingRecipe(Item input, short burnTime) implements Recipe<Inventory> {

    public static final Identifier GENERATING_ID = new ModIdentifier("generating");

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.input.equals(inventory.getStack(0).getItem());
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public Identifier getId() {
        return new ModIdentifier("generating_" + input);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return new Serializer();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.GENERATING_RECIPE;
    }

    public Item getInput() {
        return input;
    }

    public short getBurnTime() {
        return burnTime;
    }


    public static class Serializer implements RecipeSerializer<GeneratingRecipe> {

        @Override
        public GeneratingRecipe read(Identifier id, JsonObject json) {
            Identifier inputId = new Identifier(json.get("input").getAsString());
            Item input = Registry.ITEM.get(inputId);
            short burnTime = json.get("burnTime").getAsShort();
            return new GeneratingRecipe(input, burnTime);
        }

        @Override
        public GeneratingRecipe read(Identifier id, PacketByteBuf buf) {
            Item input = Registry.ITEM.get(buf.readIdentifier());
            short burnTime = buf.readShort();
            return new GeneratingRecipe(input, burnTime);
        }

        @Override
        public void write(PacketByteBuf buf, GeneratingRecipe recipe) {
            buf.writeInt(recipe.burnTime);
            buf.writeItemStack(recipe.getOutput());
        }
    }
}
