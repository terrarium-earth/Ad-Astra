package com.github.alexnijjar.ad_astra.recipes;

import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.google.gson.JsonObject;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CryoFuelConversionRecipe extends ConversionRecipe {

    public CryoFuelConversionRecipe(Identifier id, ItemStack input, Fluid output, double conversionRatio) {
        super(id, input, output, conversionRatio);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CRYO_FUEL_CONVERSION_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CRYO_FUEL_CONVERSION_RECIPE;
    }

    public static class Serializer implements RecipeSerializer<CryoFuelConversionRecipe> {

        @Override
        public CryoFuelConversionRecipe read(Identifier id, JsonObject json) {
            ItemStack input = Registry.ITEM.get(new Identifier(json.get("input").getAsJsonObject().get("item").getAsString())).getDefaultStack();
            Fluid output = Registry.FLUID.get(new Identifier(json.get("output").getAsJsonObject().get("name").getAsString()));
            double conversionRatio = json.get("conversion_ratio").getAsDouble();
            return new CryoFuelConversionRecipe(id, input, output, conversionRatio);
        }

        @Override
        public CryoFuelConversionRecipe read(Identifier id, PacketByteBuf buf) {
            ItemStack input = Registry.ITEM.get(buf.readIdentifier()).getDefaultStack();
            Fluid output = Registry.FLUID.get(buf.readIdentifier());
            double conversionRatio = buf.readDouble();
            return new CryoFuelConversionRecipe(id, input, output, conversionRatio);
        }

        @Override
        public void write(PacketByteBuf buf, CryoFuelConversionRecipe recipe) {
            buf.writeIdentifier(Registry.ITEM.getId(recipe.getItemInput().getItem()));
            buf.writeIdentifier(Registry.FLUID.getId(recipe.getFluidOutput()));
            buf.writeDouble(recipe.getConversionRatio());
        }
    }
}