package earth.terrarium.ad_astra.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.ad_astra.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SpaceStationRecipe extends ModRecipe {

    public SpaceStationRecipe(ResourceLocation id, List<Ingredient> input, List<Integer> stackCounts) {
        super(id, input, stackCounts);
    }

    public static Codec<SpaceStationRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                IngredientHolder.CODEC.listOf().fieldOf("ingredients").forGetter(SpaceStationRecipe::getHolders)
        ).apply(instance, SpaceStationRecipe::of));
    }


    private static SpaceStationRecipe of(ResourceLocation id, List<IngredientHolder> ingredients) {
        List<Ingredient> input = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for (IngredientHolder ingredient : ingredients) {
            input.add(ingredient.ingredient());
            count.add(ingredient.count());
        }
        return new SpaceStationRecipe(id, input, count);
    }

    public List<IngredientHolder> getHolders() {
        List<IngredientHolder> holders = new LinkedList<>();
        for (int i = 0; i < getIngredients().size(); i++) {
            holders.add(new IngredientHolder(getIngredients().get(i), stackCounts.get(i)));
        }
        return holders;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SPACE_STATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.SPACE_STATION_RECIPE.get();
    }

}