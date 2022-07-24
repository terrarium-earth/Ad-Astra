package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.OxygenDistributorScreenHandler;
import com.github.alexnijjar.beyond_earth.recipes.OxygenConversionRecipe;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;
import com.github.alexnijjar.beyond_earth.registry.ModRecipes;
import com.github.alexnijjar.beyond_earth.util.FluidUtils;
import com.github.alexnijjar.beyond_earth.util.OxygenFillerAlgorithm;
import com.github.alexnijjar.beyond_earth.util.OxygenUtils;

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class OxygenDistributorBlockEntity extends FluidMachineBlockEntity {

    private int oxygenFillCheckTicks = BeyondEarth.CONFIG.oxygenDistributor.refreshTicks;
    private boolean showOxygen = false;

    public OxygenDistributorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_DISTRIBUTOR, blockPos, blockState);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        showOxygen = nbt.getBoolean("showOxygen");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("showOxygen", showOxygen);
    }

    public boolean shouldShowOxygen() {
        return this.showOxygen;
    }

    public void setShowOxygen(boolean value) {
        this.showOxygen = value;
    }

    @Override
    public long getInputSize() {
        return BeyondEarth.CONFIG.oxygenDistributor.tankBuckets;
    }

    @Override
    public long getOutputSize() {
        return BeyondEarth.CONFIG.oxygenDistributor.tankBuckets * 2;
    }

    @Override
    public boolean usesEnergy() {
        return true;
    }

    @Override
    public long getMaxGeneration() {
        return BeyondEarth.CONFIG.oxygenDistributor.maxEnergy;
    }

    @Override
    public long getEnergyPerTick() {
        return BeyondEarth.CONFIG.oxygenDistributor.energyPerTick;
    }

    @Override
    public long getMaxEnergyInsert() {
        return BeyondEarth.CONFIG.oxygenDistributor.energyPerTick * 32;
    }

    @Override
    public int getInventorySize() {
        return 2;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new OxygenDistributorScreenHandler(syncId, inv, this);
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

        BlockPos pos = this.getPos();

        ItemStack insertSlot = this.getItems().get(0);
        ItemStack extractSlot = this.getItems().get(1);

        if (!insertSlot.isEmpty() && extractSlot.getCount() < extractSlot.getMaxCount()) {
            ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world);
            FluidUtils.insertFluidIntoTank(this, this.inputTank, 0, 1, f -> ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world).stream().anyMatch(r -> r.getFluidInput().equals(f.getFluid())));
        }

        if (this.hasEnergy()) {
            List<OxygenConversionRecipe> recipes = ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world);
            if (FluidUtils.convertFluid(this, recipes)) {
                this.drainEnergy();
            }

            if (oxygenFillCheckTicks >= BeyondEarth.CONFIG.oxygenDistributor.refreshTicks) {
                if (this.outputTank.amount > 0) {
                    OxygenFillerAlgorithm floodFiller = new OxygenFillerAlgorithm(this.world, BeyondEarth.CONFIG.oxygenDistributor.maxBlockChecks);
                    Set<BlockPos> positions = floodFiller.runAlgorithm(pos.up());

                    if (positions.size() > 0) {
                        long amountOfFluidToExtract = (long) (positions.size() * BeyondEarth.CONFIG.oxygenDistributor.oxygenMultiplier);

                        if (this.getEnergy() > ((long) (positions.size() * BeyondEarth.CONFIG.oxygenDistributor.energyMultiplier))) {
                            try (Transaction transaction = Transaction.openOuter()) {
                                if (!this.outputTank.isResourceBlank()) {
                                    if (this.outputTank.extract(this.outputTank.getResource(), amountOfFluidToExtract, transaction) == amountOfFluidToExtract) {
                                        if (this.drainEnergy((long) (positions.size() * BeyondEarth.CONFIG.oxygenDistributor.energyMultiplier))) {
                                            OxygenUtils.setEntry(world, pos, positions);
                                        } else {
                                            OxygenUtils.removeEntry(world, pos);
                                        }
                                        transaction.commit();
                                    } else {
                                        OxygenUtils.removeEntry(world, pos);
                                    }
                                }
                            }
                        } else {
                            OxygenUtils.removeEntry(world, pos);
                        }
                    } else {
                        OxygenUtils.removeEntry(world, pos);
                    }
                }
                oxygenFillCheckTicks = 0;
            } else {
                oxygenFillCheckTicks++;
            }
        }
        this.setActive(OxygenUtils.getOxygenBlocksCount(world, this.getPos()) > 0);
    }
}