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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record SpaceStationRecipe(List<IngredientHolder> ingredients,
                                 ResourceKey<Level> dimension,
                                 ResourceLocation structure) implements CodecRecipe<Container> {

    public static final Codec<SpaceStationRecipe> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            IngredientHolder.CODEC.listOf().fieldOf("ingredients").forGetter(SpaceStationRecipe::ingredients),
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(SpaceStationRecipe::dimension),
            ResourceLocation.CODEC.fieldOf("structure").forGetter(SpaceStationRecipe::structure)
        ).apply(instance, SpaceStationRecipe::new));

    public static final ByteCodec<SpaceStationRecipe> NETWORK_CODEC = ObjectByteCodec.create(
        IngredientHolder.NETWORK_CODEC.listOf().fieldOf(SpaceStationRecipe::ingredients),
        ExtraByteCodecs.resourceKey(Registries.DIMENSION).fieldOf(SpaceStationRecipe::dimension),
        ExtraByteCodecs.RESOURCE_LOCATION.fieldOf(SpaceStationRecipe::structure),
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

    public static Optional<RecipeHolder<SpaceStationRecipe>> getSpaceStation(Level level, ResourceKey<Level> dimension) {
        return level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.SPACE_STATION_RECIPE.get()).stream()
            .filter(r -> dimension.equals(r.value().dimension())).findFirst();
    }

    public static boolean hasIngredients(Player player, Level level, SpaceStationRecipe recipe) {
        if (player.isCreative() || player.isSpectator()) return true;
        return recipe.matches(player.getInventory(), level);
    }

    public static void consumeIngredients(Player player, Level level) {
        if (player.isCreative() || player.isSpectator()) return;

        var recipe = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.SPACE_STATION_RECIPE.get()).stream()
            .filter(r -> level.dimension().equals(r.value().dimension())).findFirst().orElse(null);
        if (recipe == null) return;
        if (!recipe.value().matches(player.getInventory(), level)) return;

        var inventory = player.getInventory();
        for (IngredientHolder holder : recipe.value().ingredients()) {
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                if (holder.ingredient().test(inventory.getItem(i))) {
                    inventory.removeItem(i, holder.count());
                    break;
                }
            }
        }
    }
}
