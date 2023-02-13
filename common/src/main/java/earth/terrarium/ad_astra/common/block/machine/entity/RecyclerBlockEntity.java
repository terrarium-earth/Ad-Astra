package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.CookingMachineBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.ad_astra.common.screen.machine.RecyclerMenu;
import earth.terrarium.ad_astra.common.util.ItemUtils;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class RecyclerBlockEntity extends CookingMachineBlockEntity implements EnergyAttachment.Block {

    private static final Map<net.minecraft.world.item.Item, List<Recipe<?>>> RECIPE_CACHE = new IdentityHashMap<>();
    private WrappedBlockEnergyContainer energyContainer;

    public RecyclerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.RECYCLER.get(), blockPos, blockState, 9);
    }

    @Override
    public void serverTick() {
        if (canCraft()) {
            if (getEnergyStorage().internalExtract(50, true) >= 50) {
                getEnergyStorage().internalExtract(50, false);
                cookTime++;
                if (cookTime >= cookTimeTotal) {
                    cookTime = 0;
                    craft();
                }
            } else {
                cookTime = 0;
            }
        } else {
            cookTime = 0;
        }
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new RecyclerMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(20000)) : energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public void update() {
        if (canCraft()) {
            cookTimeTotal = 40;
        }
    }

    private void craft() {
        Recipe<?> recipe = getRecipeFromInput(getItem(0));
        if (recipe == null) return;
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient value : ingredients) {
            if (level.random.nextInt(5) == 0) {
                if (value.getItems().length > 0) {
                    ItemUtils.addItem(this, value.getItems()[0].copy(), slot -> slot != 0);
                }
            }
        }
        getItem(0).shrink(1);
        update();
    }

    @Nullable
    private Recipe<?> getRecipeFromInput(ItemStack stack) {
        if (!RECIPE_CACHE.containsKey(stack.getItem())) {
            if (level == null) return null;
            HolderSet<RecipeType<?>> recyclableRecipes = level.registryAccess().registry(Registries.RECIPE_TYPE).map(r -> r.getOrCreateTag(ModTags.Recipes.RECYCLABLES)).orElse(null);
            if (recyclableRecipes == null) return null;
            List<Recipe<?>> recipes = new ArrayList<>();
            for (Holder<RecipeType<?>> recyclableRecipe : recyclableRecipes) {
                if (!recyclableRecipe.isBound()) continue;
                addRecipes(cast(recyclableRecipe.value()), stack, recipes);
            }
            RECIPE_CACHE.put(stack.getItem(), recipes);
        }
        for (Recipe<?> recipe : RECIPE_CACHE.getOrDefault(stack.getItem(), List.of())) {
            if (!ItemStack.isSame(recipe.getResultItem(), stack) || recipe.getResultItem().getCount() > stack.getCount())
                continue;
            return recipe;
        }
        return null;
    }

    private <C extends Container, T extends Recipe<C>> void addRecipes(RecipeType<T> recyclableRecipe, ItemStack stack, List<Recipe<?>> recipes) {
        for (var recipe : level.getRecipeManager().getAllRecipesFor(recyclableRecipe)) {
            if (!recipe.getResultItem().is(stack.getItem())) {
                continue;
            }
            recipes.add(recipe);
        }
    }

    @SuppressWarnings("unchecked")
    private static <F, T> T cast(F f) {
        return (T) f;
    }

    // TODO: run in reload event
    public static void onReload() {
        RECIPE_CACHE.clear();
    }

    private boolean canCraft() {
        return getItem(0).is(ModTags.Items.RECYCLABLE) && getItems().stream().anyMatch(ItemStack::isEmpty);
    }
}
