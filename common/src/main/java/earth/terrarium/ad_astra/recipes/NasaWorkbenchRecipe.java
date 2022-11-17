package earth.terrarium.ad_astra.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import earth.terrarium.ad_astra.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

public class NasaWorkbenchRecipe extends CookingRecipe {

    public NasaWorkbenchRecipe(ResourceLocation id, List<IngredientHolder> input, ItemStack output) {
        super(id, input, output);
    }

    public static Codec<NasaWorkbenchRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                IngredientHolder.CODEC.listOf().fieldOf("ingredients").forGetter(NasaWorkbenchRecipe::getHolders),
                ItemStackCodec.CODEC.fieldOf("output").forGetter(NasaWorkbenchRecipe::getResultItem)
        ).apply(instance, NasaWorkbenchRecipe::new));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.NASA_WORKBENCH_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.NASA_WORKBENCH_RECIPE.get();
    }
}