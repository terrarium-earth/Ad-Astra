package net.mrscauthd.beyond_earth.blocks.machines.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;

public class OxygenLoaderBlockEntity extends AbstractMachineBlockEntity {
    public OxygenLoaderBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_LOADER_ENTITY, blockPos, blockState);
    }
}
