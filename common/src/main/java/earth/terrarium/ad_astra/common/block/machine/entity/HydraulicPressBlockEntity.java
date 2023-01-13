package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.CookingMachineBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import earth.terrarium.ad_astra.common.screen.machine.HydraulicPressMenu;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@MethodsReturnNonnullByDefault
public class HydraulicPressBlockEntity extends CookingMachineBlockEntity implements EnergyAttachment.Block {
    private WrappedBlockEnergyContainer energyContainer;
    private AbstractCookingRecipe recipe;

    public HydraulicPressBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.HYDRAULIC_PRESS.get(), blockPos, blockState, 2);
    }

    @Override
    public void serverTick() {
        if (this.recipe != null && this.canCraft()) {
            if (this.getEnergyStorage().internalExtract(10, true) >= 10) {
                this.getEnergyStorage().internalExtract(10, false);
                this.cookTime++;
                if (this.cookTime >= cookTimeTotal) {
                    this.cookTime = 0;
                    this.craft();
                }
            } else {
                this.cookTime = 0;
            }
        } else {
            this.cookTime = 0;
        }
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new HydraulicPressMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(20000)) : this.energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public void update() {
        if (level == null) return;
        this.recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.HYDRAULIC_PRESSING.get(), this, level).orElse(null);
        if (this.recipe == null) {
            this.cookTime = 0;
        } else if (!canCraft()) {
            this.recipe = null;
        } else {
            this.cookTimeTotal = Math.max(20, this.recipe.getCookingTime() - 40);
        }
    }

    private void craft() {
        if (recipe == null) return;
        getItem(0).shrink(1);
        ItemStack item = getItem(1);
        if (item.isEmpty()) {
            setItem(1, this.recipe.getResultItem().copy());
        } else {
            item.grow(this.recipe.getResultItem().getCount());
        }
        recipe = null;
        update();
    }

    private boolean canCraft() {
        if (recipe != null) {
            ItemStack output = getItem(1);
            return output.isEmpty() || (ItemStack.isSameItemSameTags(output, recipe.getResultItem()) && recipe.getResultItem().getCount() + output.getCount() <= output.getMaxStackSize());
        }
        return false;
    }
}
