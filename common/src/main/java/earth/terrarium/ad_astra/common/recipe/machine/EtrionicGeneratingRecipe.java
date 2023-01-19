package earth.terrarium.ad_astra.common.recipe.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record EtrionicGeneratingRecipe(ResourceLocation id, Ingredient ingredient, int cookingTime,
                                       int energy) implements CodecRecipe<Container> {

    public static Codec<EtrionicGeneratingRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                IngredientCodec.CODEC.fieldOf("ingredient").forGetter(EtrionicGeneratingRecipe::ingredient),
                Codec.INT.fieldOf("cookingtime").orElse(200).forGetter(EtrionicGeneratingRecipe::cookingTime),
                Codec.INT.fieldOf("energy").orElse(10).forGetter(EtrionicGeneratingRecipe::energy)
        ).apply(instance, EtrionicGeneratingRecipe::new));
    }

    public static Codec<EtrionicGeneratingRecipe> networkingCodec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                IngredientCodec.NETWORK_CODEC.fieldOf("ingredient").forGetter(EtrionicGeneratingRecipe::ingredient),
                Codec.INT.fieldOf("cookingtime").orElse(200).forGetter(EtrionicGeneratingRecipe::cookingTime),
                Codec.INT.fieldOf("energy").orElse(10).forGetter(EtrionicGeneratingRecipe::energy)
        ).apply(instance, EtrionicGeneratingRecipe::new));
    }

    @Override
    public boolean matches(Container container, Level level) {
        return ingredient.test(container.getItem(0));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.ETRIONIC_GENERATING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.ETRIONIC_GENERATING.get();
    }
}
