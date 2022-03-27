package net.mrscauthd.beyond_earth.blocks.machines.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.mrscauthd.beyond_earth.BeyondEarth;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;

public class NasaWorkbenchBlockEntity extends AbstractMachineBlockEntity {
    public NasaWorkbenchBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.NASA_WORKBENCH_ENTITY, blockPos, blockState);
    }
}
