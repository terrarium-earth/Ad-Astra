package earth.terrarium.ad_astra.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import earth.terrarium.ad_astra.registry.ModRecipes;
import net.minecraft.fluid.Fluid;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CryoFuelConversionRecipe extends ConversionRecipe {

    public CryoFuelConversionRecipe(Identifier id, Ingredient input, Fluid output, double conversionRatio) {
        super(id, input, output, conversionRatio);
    }

    public static Codec<CryoFuelConversionRecipe> codec(Identifier id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                IngredientCodec.CODEC.fieldOf("input").forGetter(CryoFuelConversionRecipe::getInput),
                Registry.FLUID.getCodec().fieldOf("output").forGetter(ConversionRecipe::getFluidOutput),
                Codec.DOUBLE.fieldOf("conversion_ratio").orElse(1.0).forGetter(ConversionRecipe::getConversionRatio)
        ).apply(instance, CryoFuelConversionRecipe::new));
    }

    private Ingredient getInput() {
        return getIngredients().get(0);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CRYO_FUEL_CONVERSION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CRYO_FUEL_CONVERSION_RECIPE.get();
    }
}