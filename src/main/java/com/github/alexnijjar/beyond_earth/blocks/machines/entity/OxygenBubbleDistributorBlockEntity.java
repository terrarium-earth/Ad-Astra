package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class OxygenBubbleDistributorBlockEntity extends FluidMachineBlockEntity {
    
    public static final int TANK_SIZE = 3;
    public OxygenBubbleDistributorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_BUBBLE_DISTRIBUTOR_ENTITY, blockPos, blockState);
    }

    @Override
    public int getBuckets() {
        return TANK_SIZE;
    }
}