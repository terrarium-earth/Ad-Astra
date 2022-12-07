package earth.terrarium.ad_astra.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import earth.terrarium.ad_astra.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.registry.ModRecipeTypes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.function.Predicate;

public class CryoFuelConversionRecipe extends ConversionRecipe {

    public CryoFuelConversionRecipe(ResourceLocation id, Ingredient input, Fluid output, double conversionRatio) {
        super(id, input, output, conversionRatio);
    }

    public static Codec<CryoFuelConversionRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                IngredientCodec.CODEC.fieldOf("input").forGetter(CryoFuelConversionRecipe::getInput),
                Registry.FLUID.byNameCodec().fieldOf("output").forGetter(ConversionRecipe::getFluidOutput),
                Codec.DOUBLE.fieldOf("conversion_ratio").orElse(1.0).forGetter(ConversionRecipe::getConversionRatio)
        ).apply(instance, CryoFuelConversionRecipe::new));
    }

    private Ingredient getInput() {
        return getIngredients().get(0);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CRYO_FUEL_CONVERSION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.CRYO_FUEL_CONVERSION_RECIPE.get();
    }

    public static CryoFuelConversionRecipe findFirst(Level level, Predicate<CryoFuelConversionRecipe> filter) {
        return getRecipes(level).stream().filter(filter).findFirst().orElse(null);
    }

    public static List<CryoFuelConversionRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CRYO_FUEL_CONVERSION_RECIPE.get());
    }
}