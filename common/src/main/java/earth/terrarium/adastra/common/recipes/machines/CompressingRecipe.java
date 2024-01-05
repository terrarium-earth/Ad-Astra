package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.adastra.common.utils.ItemUtils;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record CompressingRecipe(
    ResourceLocation id,
    int cookingTime, int energy,
    Ingredient ingredient, ItemStack result
) implements CodecRecipe<Container> {

    public static Codec<CompressingRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            Codec.INT.fieldOf("cookingtime").forGetter(CompressingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(CompressingRecipe::energy),
            IngredientCodec.CODEC.fieldOf("ingredient").forGetter(CompressingRecipe::ingredient),
            ItemStackCodec.CODEC.fieldOf("result").forGetter(CompressingRecipe::result)
        ).apply(instance, CompressingRecipe::new));
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        if (!ingredient.test(container.getItem(1))) return false;
        if (!(container instanceof BotariumEnergyBlock<?> entity)) return true;
        if (entity.getEnergyStorage().internalExtract(energy, true) < energy) return false;
        return ItemUtils.canAddItem(container.getItem(2), result);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.COMPRESSING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.COMPRESSING.get();
    }
}
