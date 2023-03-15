package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.config.CompressorConfig;
import earth.terrarium.ad_astra.common.recipe.CompressingRecipe;
import earth.terrarium.ad_astra.common.recipe.CookingRecipe;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.screen.menu.CompressorMenu;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CompressorBlockEntity extends ProcessingMachineBlockEntity implements EnergyAttachment.Block {
    private WrappedBlockEnergyContainer energyContainer;

    public CompressorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.COMPRESSOR.get(), blockPos, blockState);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inv, @NotNull Player player) {
        return new CompressorMenu(syncId, inv, this);
    }

    // Input and output.
    @Override
    public int getInventorySize() {
        return 2;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == 1;
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide()) {
            if (this.getEnergyStorage(this).internalExtract(this.getEnergyPerTick(), true) > 0) {
                ItemStack input = this.getItem(0);
                if (!input.isEmpty() && (input.is(this.inputStack.getItem()) || this.inputStack.isEmpty())) {
                    this.setActive(true);
                    if (this.cookTime < this.cookTimeTotal) {
                        this.cookTime++;
                        this.getEnergyStorage(this).internalExtract(this.getEnergyPerTick(), false);

                    } else if (!this.outputStack.isEmpty()) {
                        input.shrink(1);
                        this.finishCooking();

                    } else {
                        CookingRecipe recipe = this.createRecipe(input, true);
                        if (recipe != null) {
                            this.cookTimeTotal = recipe.getCookTime();
                            this.cookTime = 0;
                        }
                    }
                } else if (!this.outputStack.isEmpty()) {
                    this.stopCooking();
                } else {
                    this.setActive(false);
                }
            }
        }
    }

    public CompressingRecipe createRecipe(ItemStack testStack, boolean checkOutput) {
        stopCooking();

        CompressingRecipe recipe = CompressingRecipe.findFirst(this.level, f -> f.test(testStack));

        if (recipe != null) {

            // Stop if something is already in the output.
            if (checkOutput) {
                ItemStack outputSlot = this.getItem(1);
                ItemStack output = recipe.getResultItem();
                if (!outputSlot.isEmpty() && !outputSlot.getItem().equals(recipe.getResultItem().getItem()) || outputSlot.getCount() + output.getCount() > outputSlot.getMaxStackSize()) {
                    return null;
                }
            }

            this.outputStack = recipe.getResultItem();
            this.inputStack = testStack;
        }

        return recipe;
    }

    public long getEnergyPerTick() {
        return CompressorConfig.energyPerTick;
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage(this).getMaxCapacity();
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(CompressorConfig.maxEnergy)) : this.energyContainer;
    }
}