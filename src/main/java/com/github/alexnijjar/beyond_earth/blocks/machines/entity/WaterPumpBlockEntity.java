package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class WaterPumpBlockEntity extends AbstractMachineBlockEntity {

    public static final int TRANSFER_PER_TICK = 10;

    public WaterPumpBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.WATER_PUMP_ENTITY, blockPos, blockState);
    }
}