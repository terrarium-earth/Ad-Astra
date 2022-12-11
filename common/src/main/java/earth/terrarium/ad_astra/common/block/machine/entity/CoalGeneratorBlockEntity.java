package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.registry.ModBlockEntities;
import earth.terrarium.ad_astra.common.config.CoalGeneratorConfig;
import earth.terrarium.ad_astra.common.screen.menu.CoalGeneratorMenu;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.ExtractOnlyEnergyContainer;
import earth.terrarium.botarium.util.CommonHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoalGeneratorBlockEntity extends ProcessingMachineBlockEntity implements EnergyBlock {
    private ExtractOnlyEnergyContainer energyContainer;

    public CoalGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.COAL_GENERATOR.get(), blockPos, blockState);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inv, @NotNull Player player) {
        return new CoalGeneratorMenu(syncId, inv, this);
    }

    // Only input.
    @Override
    public int getInventorySize() {
        return 1;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide()) {
            ItemStack input = this.getItems().get(0);
            // Consume the fuel
            if (this.cookTime > 0) {
                this.cookTime--;
                this.getEnergyStorage().internalInsert(this.getEnergyPerTick(), false);
                this.setActive(true);
                // Check if the input is a valid fuel
            } else if (!input.isEmpty() && !(input.getItem() instanceof BucketItem)) {
                int burnTime = Math.min(20000, CommonHooks.getBurnTime(input));
                if (burnTime > 0) {
                    input.shrink(1);
                    this.cookTimeTotal = burnTime;
                    this.cookTime = burnTime;
                }
            } else {
                this.setActive(false);
            }
            EnergyHooks.distributeEnergyNearby(this, this.getEnergyPerTick());
        }
    }

    public long getEnergyPerTick() {
        return CoalGeneratorConfig.energyPerTick;
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public ExtractOnlyEnergyContainer getEnergyStorage() {
        return energyContainer == null ? energyContainer = new ExtractOnlyEnergyContainer(this, (int) CoalGeneratorConfig.maxEnergy) : this.energyContainer;
    }

    @Override
    public void update() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }
}