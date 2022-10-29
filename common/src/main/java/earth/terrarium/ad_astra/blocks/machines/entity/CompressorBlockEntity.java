package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.recipes.CookingRecipe;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.registry.ModRecipes;
import earth.terrarium.ad_astra.screen.handler.CompressorScreenHandler;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.InsertOnlyEnergyContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class CompressorBlockEntity extends ProcessingMachineBlockEntity implements EnergyBlock {
    private InsertOnlyEnergyContainer energyContainer;

    public CompressorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.COMPRESSOR.get(), blockPos, blockState);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CompressorScreenHandler(syncId, inv, this);
    }

    // Input and output.
    @Override
    public int getInventorySize() {
        return 2;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 1;
    }

    @Override
    public void tick() {
        if (!this.world.isClient()) {
            if (this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), true) > 0) {
                ItemStack input = this.getStack(0);
                if (!input.isEmpty() && (input.getItem().equals(this.inputItem) || this.inputItem == null)) {
                    this.setActive(true);
                    if (this.cookTime < this.cookTimeTotal) {
                        this.cookTime++;
                        this.getEnergyStorage().internalExtract(this.getEnergyPerTick(), false);

                    } else if (this.outputStack != null) {
                        input.decrement(1);
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
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }
}