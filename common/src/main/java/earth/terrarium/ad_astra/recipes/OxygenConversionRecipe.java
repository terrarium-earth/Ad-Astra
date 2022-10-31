package earth.terrarium.ad_astra.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.tags.HolderSetCodec;
import earth.terrarium.ad_astra.registry.ModRecipes;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluid;

public class OxygenConversionRecipe extends FluidConversionRecipe {

    public OxygenConversionRecipe(ResourceLocation id, HolderSet<Fluid> input, Fluid output, double conversionRatio) {
        super(id, input, output, conversionRatio);
    }

    public static Codec<OxygenConversionRecipe> oxygenCodec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                HolderSetCodec.of(Registry.FLUID).fieldOf("input").forGetter(ConversionRecipe::getFluidInput),
                Registry.FLUID.byNameCodec().fieldOf("output").forGetter(ConversionRecipe::getFluidOutput),
                Codec.DOUBLE.fieldOf("conversion_ratio").orElse(1.0).forGetter(ConversionRecipe::getConversionRatio)
        ).apply(instance, OxygenConversionRecipe::new));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.OXYGEN_CONVERSION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.OXYGEN_CONVERSION_RECIPE.get();
    }
}