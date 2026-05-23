package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.adastra.common.blockentities.machines.EtrionicBlastFurnaceBlockEntity;
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

import java.util.List;

public record AlloyingRecipe(
    int cookingTime, int energy,
    List<Ingredient> ingredients, ItemStack result
) implements CodecRecipe<RecipeInput> {

    public static final MapCodec<AlloyingRecipe> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            Codec.INT.fieldOf("cookingtime").forGetter(AlloyingRecipe::cookingTime),
            Codec.INT.fieldOf("energy").forGetter(AlloyingRecipe::energy),
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(AlloyingRecipe::ingredients),
            ItemStackCodec.CODEC.fieldOf("result").forGetter(AlloyingRecipe::result)
        ).apply(instance, AlloyingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AlloyingRecipe> NETWORK_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        AlloyingRecipe::cookingTime,
        ByteBufCodecs.INT,
        AlloyingRecipe::energy,
        Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
        AlloyingRecipe::ingredients,
        ItemStack.STREAM_CODEC,
        AlloyingRecipe::result,
        AlloyingRecipe::new
    );

    @Override
    public boolean matches(@NotNull RecipeInput container, @NotNull Level level) {
        if (container.size() < ingredients.size()) return false;
        for (int i = 0; i < Math.min(4, ingredients.size()); i++) {
            boolean found = false;
            for (int j = 0; j < 4; j++) {
                if (ingredients.get(i).test(container.getItem(j + 1))) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }

        if (!(container instanceof EtrionicBlastFurnaceBlockEntity entity)) return true;
        if (entity.getEnergyStorage().extract(energy, true) < energy) return false;
        return ItemUtils.canAddItem(container, result, 5, 6, 7, 8);
    }

    @Override
    public CodecRecipeSerializer<? extends CodecRecipe<RecipeInput>> serializer() {
        return ModRecipeSerializers.ALLOYING.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.ALLOYING.get();
    }
}
