package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record CryoFreezingRecipe(
    ResourceLocation id,
    int cookingTime, int energy,
    Ingredient ingredient,
    FluidHolder resultFluid
) implements CodecRecipe<Container> {

    public static Codec<CryoFreezingRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            Codec.INT.fieldOf("cookingtime").forGetter(CryoFreezingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(CryoFreezingRecipe::energy),
            IngredientCodec.CODEC.fieldOf("ingredient").forGetter(CryoFreezingRecipe::ingredient),
            FluidHolder.CODEC.fieldOf("result_fluid").forGetter(CryoFreezingRecipe::resultFluid)
        ).apply(instance, CryoFreezingRecipe::new));
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        return ingredient.test(container.getItem(1));
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CRYO_FREEZING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.CRYO_FREEZING.get();
    }
}
