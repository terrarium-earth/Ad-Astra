package com.github.alexnijjar.beyond_earth.blocks.machines;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.FuelRefineryBlockEntity;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FuelRefineryBlock extends AbstractMachineBlock {

    public FuelRefineryBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean useFacing() {
        return true;
    }

    @Override
    protected boolean useLit() {
        return true;
    }

    @Override
    public FuelRefineryBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FuelRefineryBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.FUEL_REFINERY_ENTITY, FuelRefineryBlockEntity::tick);
    }
}