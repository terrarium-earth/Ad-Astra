package earth.terrarium.ad_astra.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;

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

    public static Codec<NasaWorkbenchRecipe> networkCodec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            IngredientHolder.NETWORK_CODEC.listOf().fieldOf("ingredients").forGetter(NasaWorkbenchRecipe::getHolders),
            ItemStackCodec.NETWORK_CODEC.fieldOf("output").forGetter(NasaWorkbenchRecipe::getResultItem)
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

    public static NasaWorkbenchRecipe findFirst(Level level, Predicate<NasaWorkbenchRecipe> filter) {
        return getRecipes(level).stream().filter(filter).findFirst().orElse(null);
    }

    public static List<NasaWorkbenchRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.NASA_WORKBENCH_RECIPE.get());
    }
}