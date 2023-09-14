package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record HydraulicPressingRecipe(
    ResourceLocation id,
    int cookingTime, int energy,
    Ingredient ingredient, ItemStack result
) implements CodecRecipe<Container> {

    public static Codec<HydraulicPressingRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            Codec.INT.fieldOf("cookingtime").forGetter(HydraulicPressingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(HydraulicPressingRecipe::energy),
            IngredientCodec.CODEC.fieldOf("ingredient").forGetter(HydraulicPressingRecipe::ingredient),
            ItemStackCodec.CODEC.fieldOf("result").forGetter(HydraulicPressingRecipe::result)
        ).apply(instance, HydraulicPressingRecipe::new));
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        return false;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.HYDRAULIC_PRESSING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.HYDRAULIC_PRESSING.get();
    }
}
