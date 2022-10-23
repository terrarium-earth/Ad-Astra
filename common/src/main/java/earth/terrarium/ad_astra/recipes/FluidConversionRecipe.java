package earth.terrarium.ad_astra.recipes;

import com.teamresourceful.resourcefullib.common.codecs.tags.HolderSetCodec;
import earth.terrarium.ad_astra.registry.ModRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.fluid.Fluid;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.HolderSet;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FluidConversionRecipe extends ConversionRecipe {

	public FluidConversionRecipe(Identifier id, HolderSet<Fluid> input, Fluid output, double conversionRatio) {
		super(id, input, output, conversionRatio);
	}

	public static Codec<FluidConversionRecipe> codec(Identifier id) {
		return RecordCodecBuilder.create(instance -> instance.group(
				RecordCodecBuilder.point(id),
				HolderSetCodec.of(Registry.FLUID).fieldOf("input").forGetter(ConversionRecipe::getFluidInput),
				Registry.FLUID.getCodec().fieldOf("output").forGetter(ConversionRecipe::getFluidOutput),
				Codec.DOUBLE.fieldOf("conversion_ratio").orElse(1.0).forGetter(ConversionRecipe::getConversionRatio)
		).apply(instance, FluidConversionRecipe::new));
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipes.FUEL_CONVERSION_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipes.FUEL_CONVERSION_RECIPE.get();
	}
}