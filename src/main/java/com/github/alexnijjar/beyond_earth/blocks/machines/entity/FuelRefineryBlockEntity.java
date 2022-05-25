package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class FuelRefineryBlockEntity extends AbstractMachineBlockEntity {
    public FuelRefineryBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FUEL_REFINERY_ENTITY, blockPos, blockState);
    }
}