package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
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

public record NasaWorkbenchRecipe(
    List<Ingredient> ingredients,
    ItemStack result
) implements CodecRecipe<RecipeInput> {

    public static final MapCodec<NasaWorkbenchRecipe> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(NasaWorkbenchRecipe::ingredients),
            ItemStackCodec.CODEC.fieldOf("result").forGetter(NasaWorkbenchRecipe::result)
        ).apply(instance, NasaWorkbenchRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, NasaWorkbenchRecipe> NETWORK_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
        NasaWorkbenchRecipe::ingredients,
        ItemStack.STREAM_CODEC,
        NasaWorkbenchRecipe::result,
        NasaWorkbenchRecipe::new
    );

    @Override
    public boolean matches(@NotNull RecipeInput container, @NotNull Level level) {
        if (container.size() < ingredients.size()) return false;
        for (int i = 0; i < ingredients.size(); i++) {
            if (!ingredients.get(i).test(container.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public CodecRecipeSerializer<? extends CodecRecipe<RecipeInput>> serializer() {
        return ModRecipeSerializers.NASA_WORKBENCH_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.NASA_WORKBENCH.get();
    }
}