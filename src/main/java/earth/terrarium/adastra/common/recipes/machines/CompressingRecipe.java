package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.adastra.common.blockentities.machines.CompressorBlockEntity;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.adastra.common.utils.ItemUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record CompressingRecipe(
    int cookingTime, int energy,
    Ingredient ingredient, ItemStack result
) implements CodecRecipe<RecipeInput> {

    public static final MapCodec<CompressingRecipe> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            Codec.INT.fieldOf("cookingtime").forGetter(CompressingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(CompressingRecipe::energy),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(CompressingRecipe::ingredient),
            ItemStackCodec.CODEC.fieldOf("result").forGetter(CompressingRecipe::result)
        ).apply(instance, CompressingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CompressingRecipe> NETWORK_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        CompressingRecipe::cookingTime,
        ByteBufCodecs.INT,
        CompressingRecipe::energy,
        Ingredient.CONTENTS_STREAM_CODEC,
        CompressingRecipe::ingredient,
        ItemStack.STREAM_CODEC,
        CompressingRecipe::result,
        CompressingRecipe::new
    );

    @Override
    public boolean matches(RecipeInput container, Level level) {
        if (!ingredient.test(container.getItem(1))) return false;
        if (!(container instanceof CompressorBlockEntity entity)) return true;
        if (entity.getEnergyStorage().extract(energy, true) < energy) return false;
        return ItemUtils.canAddItem(container.getItem(2), result);
    }

    @Override
    public CodecRecipeSerializer<? extends CodecRecipe<RecipeInput>> serializer() {
        return ModRecipeSerializers.COMPRESSING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.COMPRESSING.get();
    }
}
