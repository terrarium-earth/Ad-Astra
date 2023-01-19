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
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RecyclerBlockEntity extends CookingMachineBlockEntity implements EnergyAttachment.Block {
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
        if (level == null) return;

        for (CraftingRecipe recipe : level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING)) {
            if (ItemStack.isSame(recipe.getResultItem(), getItem(0))) {
                NonNullList<Ingredient> ingredients = recipe.getIngredients();
                for (Ingredient value : ingredients) {
                    if (level.random.nextInt(5) == 0) {
                        if (value.getItems().length > 0) {
                            ItemUtils.addItem(this, value.getItems()[0].copy(), slot -> slot != 0);
                        }
                    }
                }
                getItem(0).shrink(1);
                return;
            }
        }

        update();
    }

    private boolean canCraft() {
        return getItem(0).is(ModTags.Items.RECYCLABLE) && getItems().stream().anyMatch(ItemStack::isEmpty);
    }
}
