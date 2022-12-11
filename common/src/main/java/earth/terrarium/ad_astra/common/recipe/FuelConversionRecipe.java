package earth.terrarium.ad_astra.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.tags.HolderSetCodec;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.function.Predicate;

public class FuelConversionRecipe extends ConversionRecipe {

    public FuelConversionRecipe(ResourceLocation id, HolderSet<Fluid> input, Fluid output, double conversionRatio) {
        super(id, input, output, conversionRatio);
    }

    public static Codec<FuelConversionRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                HolderSetCodec.of(Registry.FLUID).fieldOf("input").forGetter(ConversionRecipe::getFluidInput),
                Registry.FLUID.byNameCodec().fieldOf("output").forGetter(ConversionRecipe::getFluidOutput),
                Codec.DOUBLE.fieldOf("conversion_ratio").orElse(1.0).forGetter(ConversionRecipe::getConversionRatio)
        ).apply(instance, FuelConversionRecipe::new));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.FUEL_CONVERSION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.FUEL_CONVERSION_RECIPE.get();
    }

    public static FuelConversionRecipe findFirst(Level level, Predicate<FuelConversionRecipe> filter) {
        return getRecipes(level).stream().filter(filter).findFirst().orElse(null);
    }

    public static List<FuelConversionRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.FUEL_CONVERSION_RECIPE.get());
    }
}