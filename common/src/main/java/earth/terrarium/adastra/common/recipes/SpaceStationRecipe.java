package earth.terrarium.adastra.common.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.adastra.common.recipes.base.IngredientHolder;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record SpaceStationRecipe(List<IngredientHolder> ingredients,
                                 ResourceKey<Level> dimension) implements CodecRecipe<Container> {

    public static final Codec<SpaceStationRecipe> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            IngredientHolder.CODEC.listOf().fieldOf("ingredients").forGetter(SpaceStationRecipe::ingredients),
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(SpaceStationRecipe::dimension)
        ).apply(instance, SpaceStationRecipe::new));

    public static final ByteCodec<SpaceStationRecipe> NETWORK_CODEC = ObjectByteCodec.create(
        IngredientHolder.NETWORK_CODEC.listOf().fieldOf(SpaceStationRecipe::ingredients),
        ExtraByteCodecs.resourceKey(Registries.DIMENSION).fieldOf(SpaceStationRecipe::dimension),
        SpaceStationRecipe::new
    );

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        for (IngredientHolder holder : ingredients) {
            int count = 0;
            for (int i = 0; i < container.getContainerSize(); i++) {
                var stack = container.getItem(i);
                if (holder.ingredient().test(stack)) {
                    count += stack.getCount();
                }
            }
            if (count < holder.count()) return false;
        }
        return true;
    }

    @Override
    public CodecRecipeSerializer<? extends CodecRecipe<Container>> serializer() {
        return ModRecipeSerializers.SPACE_STATION_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.SPACE_STATION_RECIPE.get();
    }
}
