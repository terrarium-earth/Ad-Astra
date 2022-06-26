package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import javax.annotation.Nullable;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.blocks.machines.AbstractMachineBlock;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.WaterPumpScreenHandler;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class WaterPumpBlockEntity extends FluidMachineBlockEntity {

    public static final long MAX_ENERGY = BeyondEarth.CONFIG.mainConfig.waterPumpGeneratorMaxEnergy;
    public static final long ENERGY_PER_TICK = BeyondEarth.CONFIG.mainConfig.waterPumpGeneratorEnergyPerTick;
    public static final int TANK_SIZE = BeyondEarth.CONFIG.mainConfig.waterPumpTankBuckets;
    public static final long TRANSFER_PER_TICK = BeyondEarth.CONFIG.mainConfig.waterPumpTransferPerTick;
    public static final Direction[] INSERT_DIRECTIONS = { Direction.UP, Direction.SOUTH };

    private long waterExtracted;

    public WaterPumpBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.WATER_PUMP_ENTITY, blockPos, blockState);
    }

    @Override
    public long getInputSize() {
        return TANK_SIZE;
    }

    @Override
    public long getOutputSize() {
        return 0;
    }

    @Override
    public boolean usesEnergy() {
        return true;
    }

    @Override
    public long getMaxGeneration() {
        return MAX_ENERGY;
    }

    @Override
    public long getEnergyPerTick() {
        return ENERGY_PER_TICK;
    }

    @Override
    public long getMaxEnergyInsert() {
        return ENERGY_PER_TICK * 32;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new WaterPumpScreenHandler(syncId, inv, this);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.waterExtracted = nbt.getLong("WaterExtracted");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putLong("WaterExtracted", this.waterExtracted);
    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            FluidVariant waterFluid = FluidVariant.of(Fluids.WATER);
            BlockState water = this.world.getBlockState(this.getPos().down());
            if (this.inputTank.getAmount() < this.inputTank.getCapacity()) {
                if (water.isOf(Blocks.WATER) && water.get(FluidBlock.LEVEL) == 0) {

                    // Drain the water block and add it to the tank.
                    if (this.hasEnergy()) {
                        this.setActive(true);
                        this.drainEnergy();
                        waterExtracted += TRANSFER_PER_TICK;
                        try (Transaction transaction = Transaction.openOuter()) {
                            this.inputTank.insert(waterFluid, TRANSFER_PER_TICK, transaction);
                            transaction.commit();
                        }
                    } else {
                        this.setActive(false);
                    }

                    if (BeyondEarth.CONFIG.mainConfig.deleteWaterBelowWaterPump) {
                        // Delete the water block after it has been fully extracted.
                        if (waterExtracted >= FluidConstants.BLOCK) {
                            waterExtracted = 0;
                            world.setBlockState(this.getPos().down(), Blocks.AIR.getDefaultState());
                        }
                    }
                }
            } else {
                this.setActive(false);
            }

            if (this.hasEnergy()) {
                this.drainEnergy();
                // Insert the fluid into nearby tanks.
                for (Direction direction : new Direction[] { Direction.UP, this.getCachedState().get(AbstractMachineBlock.FACING) }) {
                    Storage<FluidVariant> storage = FluidStorage.SIDED.find(this.world, this.getPos().offset(direction), direction);
                    if (storage != null) {
                        try (Transaction transaction = Transaction.openOuter()) {
                            if (this.inputTank.extract(waterFluid, TRANSFER_PER_TICK, transaction) == TRANSFER_PER_TICK && storage.insert(waterFluid, TRANSFER_PER_TICK, transaction) == TRANSFER_PER_TICK) {
                                transaction.commit();
                            }
                        }
                    }
                }
            }
        }
    }
}