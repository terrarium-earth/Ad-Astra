package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.beyond_earth.gui.screen_handlers.FuelRefineryScreenHandler;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FuelRefineryBlockEntity extends FluidMachineBlockEntity {

    public static final long MAX_ENERGY = 9000L;
    public static final long ENERGY_PER_TICK = 1L;
    public static final int TANK_SIZE = 3;

    public FuelRefineryBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FUEL_REFINERY_ENTITY, blockPos, blockState);
    }

    @Override
    public int getBuckets() {
        return TANK_SIZE;
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

    @Override
    public int getInventorySize() {
        return 4;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new FuelRefineryScreenHandler(syncId, inv, this);
    }

    public static void tick(World world, BlockPos pos, BlockState state, AbstractMachineBlockEntity blockEntity) {
        if (blockEntity.usesEnergy()) {
            FuelRefineryBlockEntity entity = (FuelRefineryBlockEntity) blockEntity;

            FluidVariant water = FluidVariant.of(Fluids.WATER);
            
            try (Transaction transaction = Transaction.openOuter()) {
                // Try to insert, will return how much was actually inserted.
                long amountInserted = entity.fluidStorage.insert(water, FluidConstants.BUCKET / 10, transaction);
                if (amountInserted == FluidConstants.BUCKET / 10) {
                    transaction.commit();
                }
            }
        }
    }
}