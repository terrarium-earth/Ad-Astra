package earth.terrarium.ad_astra.blocks.machines.entity;

import dev.architectury.registry.fuel.FuelRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.screen.handler.CoalGeneratorScreenHandler;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.ExtractOnlyEnergyContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class CoalGeneratorBlockEntity extends ProcessingMachineBlockEntity implements EnergyBlock {
    private ExtractOnlyEnergyContainer energyContainer;
    int ticksToTurnOff = 0;

    public CoalGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.COAL_GENERATOR.get(), blockPos, blockState);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CoalGeneratorScreenHandler(syncId, inv, this);
    }

    // Only input.
    @Override
    public int getInventorySize() {
        return 1;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Override
    public void tick() {
        if (!this.world.isClient()) {
            ItemStack input = this.getItems().get(0);
            // Consume the fuel
            if (this.cookTime > 0) {
                this.cookTime--;
                this.getEnergyStorage().internalInsert(this.getEnergyPerTick(), false);
                this.setActive(true);
                ticksToTurnOff = 7;
                // Check if the input is a valid fuel
            } else if (!input.isEmpty()) {
                short burnTime = (short) FuelRegistry.get(input);
                input.decrement(1);
                this.cookTimeTotal = burnTime;
                this.cookTime = burnTime;
            } else {
                this.setActive(false);
            }
        } else {
            ticksToTurnOff--;
            if (ticksToTurnOff <= 0) {
                this.setActive(false);
            }

            EnergyHooks.distributeEnergyNearby(this, this.getEnergyPerTick());
        }
    }

    public long getEnergyPerTick() {
        return AdAstra.CONFIG.coalGenerator.energyPerTick;
    }

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public ExtractOnlyEnergyContainer getEnergyStorage() {
        return energyContainer == null ? energyContainer = new ExtractOnlyEnergyContainer(this, (int) AdAstra.CONFIG.coalGenerator.maxEnergy) : this.energyContainer;
    }

    @Override
    public void update() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }
}