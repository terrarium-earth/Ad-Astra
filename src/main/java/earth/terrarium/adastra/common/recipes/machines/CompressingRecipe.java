package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.adastra.common.blockentities.machines.CompressorBlockEntity;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import earth.terrarium.adastra.common.utils.ItemUtils;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record CompressingRecipe(
    int cookingTime, int energy,
    Ingredient ingredient, ItemStack result
) implements CodecRecipe<Container> {

    public static final Codec<CompressingRecipe> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.INT.fieldOf("cookingtime").forGetter(CompressingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(CompressingRecipe::energy),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(CompressingRecipe::ingredient),
            ItemStackCodec.CODEC.fieldOf("result").forGetter(CompressingRecipe::result)
        ).apply(instance, CompressingRecipe::new));

    public static final ByteCodec<CompressingRecipe> NETWORK_CODEC = ObjectByteCodec.create(
        ByteCodec.INT.fieldOf(CompressingRecipe::cookingTime),
        ByteCodec.INT.fieldOf(CompressingRecipe::energy),
        ExtraByteCodecs.INGREDIENT.fieldOf(CompressingRecipe::ingredient),
        ExtraByteCodecs.ITEM_STACK.fieldOf(CompressingRecipe::result),
        CompressingRecipe::new
    );

    @Override
    public boolean matches(Container container, Level level) {
        if (!ingredient.test(container.getItem(1))) return false;
        if (!(container instanceof CompressorBlockEntity entity)) return true;
        if (entity.getEnergyStorage().internalExtract(energy, true) < energy) return false;
        return ItemUtils.canAddItem(container.getItem(2), result);
    }

    @Override
    public CodecRecipeSerializer<? extends CodecRecipe<Container>> serializer() {
        return ModRecipeSerializers.COMPRESSING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.COMPRESSING.get();
    }
}
