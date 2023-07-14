package earth.terrarium.ad_astra.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;

public class SpaceStationRecipe extends ModRecipe {

    public SpaceStationRecipe(ResourceLocation id, List<IngredientHolder> input) {
        super(id, input);
    }

    public static Codec<SpaceStationRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            IngredientHolder.CODEC.listOf().fieldOf("ingredients").forGetter(SpaceStationRecipe::getHolders)
        ).apply(instance, SpaceStationRecipe::new));
    }

    public static Codec<SpaceStationRecipe> networkCodec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            IngredientHolder.NETWORK_CODEC.listOf().fieldOf("ingredients").forGetter(SpaceStationRecipe::getHolders)
        ).apply(instance, SpaceStationRecipe::new));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SPACE_STATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.SPACE_STATION_RECIPE.get();
    }

    public static SpaceStationRecipe findFirst(Level level, Predicate<SpaceStationRecipe> filter) {
        return getRecipes(level).stream().filter(filter).findFirst().orElse(null);
    }

    public static List<SpaceStationRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.SPACE_STATION_RECIPE.get());
    }
}