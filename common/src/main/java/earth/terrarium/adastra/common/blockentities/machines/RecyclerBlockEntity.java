package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.RecipeMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.RecyclerMenu;
import earth.terrarium.adastra.common.tags.ModItemTags;
import earth.terrarium.adastra.common.tags.ModRecipeTypeTags;
import earth.terrarium.adastra.common.utils.ItemUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;
import java.util.function.Predicate;

public class RecyclerBlockEntity extends RecipeMachineBlockEntity<Recipe<?>> implements GeoBlockEntity {
    public static final RawAnimation IDLE_ON = RawAnimation.begin().thenLoop("animation.model.idle.on");
    public static final RawAnimation IDLE_OFF = RawAnimation.begin().thenLoop("animation.model.idle.off");
    private static final Map<Item, Set<Recipe<?>>> RECIPE_CACHE = new IdentityHashMap<>();

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public RecyclerBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 11);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state ->
            cookTimeTotal == 0 || !canFunction() ? state.setAndContinue(IDLE_OFF) : state.setAndContinue(IDLE_ON)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new RecyclerMenu(id, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage() {
        if (energyContainer != null) return energyContainer;
        return energyContainer = new WrappedBlockEnergyContainer(
            this,
            new InsertOnlyEnergyContainer(50_000) {
                @Override
                public long maxInsert() {
                    return 2_000;
                }
            });
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (canFunction()) tickSideInteractions(pos, Predicate.not(Direction.UP::equals));
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter) {
        TransferUtils.pullItemsNearby(this, pos, new int[]{1}, getSideConfig().get(0), filter);
        TransferUtils.pushItemsNearby(this, pos, new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10}, getSideConfig().get(1), filter);
        TransferUtils.pullEnergyNearby(this, pos, getEnergyStorage().maxInsert(), getSideConfig().get(2), filter);
    }

    @Override
    public void recipeTick(ServerLevel level, WrappedBlockEnergyContainer energyStorage) {
        if (recipe == null) return;
        if (!canCraft(energyStorage)) {
            clearRecipe();
            return;
        }

        energyStorage.internalExtract(30, false);

        cookTime++;
        if (cookTime < cookTimeTotal) return;
        craft();
    }

    @Override
    public boolean canCraft(WrappedBlockEnergyContainer energyStorage) {
        if (recipe == null) return false;
        if (!getItem(1).is(ModItemTags.RECYCLABLE)) return false;
        for (int i = 2; i < 11; i++) {
            if (getItem(i).isEmpty()) return true;
        }
        return false;
    }

    @Override
    public void craft() {
        if (recipe == null) return;

        getItem(1).shrink(1);
        for (var ingredient : recipe.getIngredients()) {
            if (ingredient.getItems().length > 0 && level().random.nextInt(2) == 0) {
                ItemUtils.addItem(this, ingredient.getItems()[0].copy(), 2, 3, 4, 5, 6, 7, 8, 9, 10);
            }
        }

        cookTime = 0;
        if (getItem(1).isEmpty()) clearRecipe();
    }

    @Override
    public void update() {
        if (level().isClientSide()) return;

        Set<Recipe<?>> recipeCache = RECIPE_CACHE.computeIfAbsent(getItem(1).getItem(), item -> {
            var recyclerRecipeTypes = level().registryAccess()
                .registry(Registries.RECIPE_TYPE).map(r -> r.getOrCreateTag(ModRecipeTypeTags.RECYCLABLES))
                .orElse(null);
            if (recyclerRecipeTypes == null) return null;

            Set<Recipe<?>> recipes = new HashSet<>();
            for (Holder<RecipeType<?>> type : recyclerRecipeTypes) {
                if (!type.isBound()) continue;
                addRecipes(cast(type.value()), getItem(1), recipes);
            }
            return recipes;
        });
        if (recipeCache == null) return;
        recipe = recipeCache.stream()
            .filter(r -> ItemStack.isSameItem(r.getResultItem(level().registryAccess()), getItem(1)))
            .findFirst()
            .orElse(null);
        if (recipe == null) return;
        cookTimeTotal = 100;
    }

    private <C extends Container, T extends Recipe<C>> void addRecipes(RecipeType<T> recyclableRecipe, ItemStack stack, Set<Recipe<?>> recipes) {
        for (var recipe : level().getRecipeManager().getAllRecipesFor(recyclableRecipe)) {
            if (!recipe.getResultItem(level().registryAccess()).is(stack.getItem())) continue;
            recipes.add(recipe);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T, U> T cast(U u) {
        return (T) u;
    }

    public static void clearCache() {
        RECIPE_CACHE.clear();
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return List.of(
            new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS),
            new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS),
            new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY)
        );
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    }
}
