package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.recipes.CookingRecipe;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.registry.ModRecipes;
import earth.terrarium.ad_astra.screen.handler.CompressorScreenHandler;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.InsertOnlyEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CompressorBlockEntity extends ProcessingMachineBlockEntity implements EnergyBlock {
    private InsertOnlyEnergyContainer energyContainer;

    public CompressorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.COMPRESSOR.get(), blockPos, blockState);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new CompressorScreenHandler(syncId, inv, this);
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
            if (this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), true) > 0) {
                ItemStack input = this.getItem(0);
                if (!input.isEmpty() && (input.getItem().equals(this.inputItem) || this.inputItem == null)) {
                    this.setActive(true);
                    if (this.cookTime < this.cookTimeTotal) {
                        this.cookTime++;
                        this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), false);

                    } else if (this.outputStack != null) {
                        input.shrink(1);
                        this.finishCooking();

                    } else {
                        CookingRecipe recipe = this.createRecipe(ModRecipes.COMPRESSING_RECIPE.get(), input, true);
                        if (recipe != null) {
                            this.cookTimeTotal = recipe.getCookTime();
                            this.cookTime = 0;
                        }
                    }
                } else if (this.outputStack != null) {
                    this.stopCooking();
                } else {
                    this.setActive(false);
                }
            }
        }
    }

    public long getEnergyPerTick() {
        return AdAstra.CONFIG.compressor.energyPerTick;
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public InsertOnlyEnergyContainer getEnergyStorage() {
        return energyContainer == null ? energyContainer = new InsertOnlyEnergyContainer(this, (int) AdAstra.CONFIG.compressor.maxEnergy) : this.energyContainer;
    }

    @Override
    public void update() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }
}