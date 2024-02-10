package earth.terrarium.adastra.common.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import earth.terrarium.adastra.common.recipes.base.IngredientHolder;
import earth.terrarium.adastra.common.registry.ModRecipeSerializers;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record SpaceStationRecipe(ResourceLocation id, List<IngredientHolder> ingredients,
                                 ResourceKey<Level> dimension,
                                 ResourceLocation structure) implements CodecRecipe<Container> {

    public static Codec<SpaceStationRecipe> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            IngredientHolder.CODEC.listOf().fieldOf("ingredients").forGetter(SpaceStationRecipe::ingredients),
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(SpaceStationRecipe::dimension),
            ResourceLocation.CODEC.fieldOf("structure").forGetter(SpaceStationRecipe::structure)
        ).apply(instance, SpaceStationRecipe::new));
    }

    public static Codec<SpaceStationRecipe> netCodec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
            RecordCodecBuilder.point(id),
            IngredientHolder.NETWORK_CODEC.listOf().fieldOf("ingredients").forGetter(SpaceStationRecipe::ingredients),
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(SpaceStationRecipe::dimension),
            ResourceLocation.CODEC.fieldOf("structure").forGetter(SpaceStationRecipe::structure)
        ).apply(instance, SpaceStationRecipe::new));
    }

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
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SPACE_STATION_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.SPACE_STATION_RECIPE.get();
    }

    public static Optional<SpaceStationRecipe> getSpaceStation(Level level, ResourceKey<Level> dimension) {
        return level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.SPACE_STATION_RECIPE.get()).stream()
            .filter(r -> dimension.equals(r.dimension())).findFirst();
    }

    public static boolean hasIngredients(Player player, Level level, SpaceStationRecipe recipe) {
        if (player.isCreative() || player.isSpectator()) return true;
        return recipe.matches(player.getInventory(), level);
    }

    public static void consumeIngredients(Player player, Level level) {
        if (player.isCreative() || player.isSpectator()) return;

        var recipe = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.SPACE_STATION_RECIPE.get()).stream()
            .filter(r -> level.dimension().equals(r.dimension())).findFirst().orElse(null);
        if (recipe == null) return;
        if (!recipe.matches(player.getInventory(), level)) return;

        var inventory = player.getInventory();
        for (IngredientHolder holder : recipe.ingredients()) {
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                if (holder.ingredient().test(inventory.getItem(i))) {
                    inventory.removeItem(i, holder.count());
                    break;
                }
            }
        }
    }
}
