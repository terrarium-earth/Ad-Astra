package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.blocks.machines.AbstractMachineBlock;
import com.github.alexnijjar.beyond_earth.recipes.OxygenConversionRecipe;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;
import com.github.alexnijjar.beyond_earth.registry.ModParticleTypes;
import com.github.alexnijjar.beyond_earth.registry.ModRecipes;
import com.github.alexnijjar.beyond_earth.screen.handler.OxygenDistributorScreenHandler;
import com.github.alexnijjar.beyond_earth.util.FluidUtils;
import com.github.alexnijjar.beyond_earth.util.ModUtils;
import com.github.alexnijjar.beyond_earth.util.OxygenFillerAlgorithm;
import com.github.alexnijjar.beyond_earth.util.OxygenUtils;

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
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

    public int getMaxBlockChecks() {
        return BeyondEarth.CONFIG.oxygenDistributor.maxBlockChecks;
    }

    public long getFluidToExtract(long oxygenBlocks) {
        return (long) ((oxygenBlocks * BeyondEarth.CONFIG.oxygenDistributor.oxygenMultiplier) / 40);
    }

    public long getEnergyToConsume(long oxygenBlocks) {
        return (long) ((oxygenBlocks * BeyondEarth.CONFIG.oxygenDistributor.energyMultiplier) / 75);
    }

    public void extractResources() {
        long oxygenBlocks = OxygenUtils.getOxygenBlocksCount(this.world, this.getPos());
        long amountOfFluidToExtract = this.getFluidToExtract(oxygenBlocks);
        long amountOfEnergyToConsume = this.getEnergyToConsume(oxygenBlocks);

        try (Transaction transaction = Transaction.openOuter()) {
            if (!this.outputTank.variant.isBlank()) {
                if (this.outputTank.extract(this.outputTank.getResource(), amountOfFluidToExtract, transaction) != amountOfFluidToExtract) {
                    this.outputTank.amount = 0;
                }
                transaction.commit();
            }
        }

        if (this.drainEnergy(amountOfEnergyToConsume)) {
            ModUtils.spawnForcedParticles((ServerWorld) this.world, ModParticleTypes.OXYGEN_BUBBLE, this.getPos().getX() + 0.5, this.getPos().getY() + 0.5, this.getPos().getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.1);
        }
    }

    public boolean canDistribute(int oxygenBlocks) {
        long amountOfFluidToExtract = this.getFluidToExtract(oxygenBlocks);
        long amountOfEnergyToConsume = this.getEnergyToConsume(oxygenBlocks);
        if (this.getCachedState().get(AbstractMachineBlock.POWERED)) {
            return false;
        }
        else if (!this.canDrainEnergy(amountOfEnergyToConsume)) {
            return false;
        } else if (this.outputTank.variant.isBlank()) {
            return false;
        } else if (this.outputTank.simulateExtract(this.outputTank.getResource(), amountOfFluidToExtract, null) != amountOfFluidToExtract) {
            return false;
        }
        return true;
    }

    @Override
    public void tick() {

        BlockPos pos = this.getPos();

        ItemStack insertSlot = this.getItems().get(0);
        ItemStack extractSlot = this.getItems().get(1);

        // Convert the input fluid into oxygen
        if (!this.world.isClient) {
            if (!insertSlot.isEmpty() && extractSlot.getCount() < extractSlot.getMaxCount()) {
                ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world);
                FluidUtils.insertFluidIntoTank(this, this.inputTank, 0, 1, f -> ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world).stream().anyMatch(r -> r.getFluidInput().equals(f.getFluid())));
            }

            if (this.canDrainEnergy()) {
                List<OxygenConversionRecipe> recipes = ModRecipes.OXYGEN_CONVERSION_RECIPE.getRecipes(this.world);
                if (FluidUtils.convertFluid(this, recipes, 50)) {
                    this.drainEnergy();
                }
            }
        }

        // Distribute the oxygen every certain amount of ticks. The algorithm is then run to determine how much oxygen to distribute.
        if (oxygenFillCheckTicks >= BeyondEarth.CONFIG.oxygenDistributor.refreshTicks) {
            OxygenFillerAlgorithm floodFiller = new OxygenFillerAlgorithm(this.world, this.getMaxBlockChecks());
            Set<BlockPos> positions = floodFiller.runAlgorithm(pos.up());

            if (this.canDistribute(positions.size())) {
                OxygenUtils.setEntry(this.world, pos, positions);
            } else {
                OxygenUtils.removeEntry(this.world, this.getPos());
            }
            
            oxygenFillCheckTicks = 0;
        } else {
            oxygenFillCheckTicks++;
        }

        boolean active = OxygenUtils.getOxygenBlocksCount(this.world, this.getPos()) > 0;
        this.setActive(active);
        if (active && !this.world.isClient) {
            this.extractResources();
        }
    }
}