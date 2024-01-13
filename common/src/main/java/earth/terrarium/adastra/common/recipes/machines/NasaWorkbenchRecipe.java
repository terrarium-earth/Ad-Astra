package earth.terrarium.adastra.common.recipes.machines;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record NasaWorkbenchRecipe(
    List<Ingredient> ingredients,
    ItemStack result
) implements CodecRecipe<Container> {

    public static final Codec<NasaWorkbenchRecipe> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(NasaWorkbenchRecipe::ingredients),
            ItemStackCodec.CODEC.fieldOf("result").forGetter(NasaWorkbenchRecipe::result)
        ).apply(instance, NasaWorkbenchRecipe::new));

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        if (container.getContainerSize() < 14) return false;
        for (int i = 0; i < 14; i++) {
            if (!ingredients.get(i).test(container.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public <T extends CodecRecipe<Container>> CodecRecipeSerializer<T> serializer() {
        return ModRecipeSerializers.NASA_WORKBENCH_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.NASA_WORKBENCH.get();
    }
}