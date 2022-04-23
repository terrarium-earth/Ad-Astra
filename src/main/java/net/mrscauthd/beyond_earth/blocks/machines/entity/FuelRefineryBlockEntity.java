package net.mrscauthd.beyond_earth.blocks.machines.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;

public class FuelRefineryBlockEntity extends AbstractMachineBlockEntity {
    public FuelRefineryBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FUEL_REFINERY_ENTITY, blockPos, blockState);
    }
}