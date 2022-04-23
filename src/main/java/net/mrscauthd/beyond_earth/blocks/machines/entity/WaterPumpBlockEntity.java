package net.mrscauthd.beyond_earth.blocks.machines.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;

public class WaterPumpBlockEntity extends AbstractMachineBlockEntity {

    public static final int TRANSFER_PER_TICK = 10;

    public WaterPumpBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.WATER_PUMP_ENTITY, blockPos, blockState);
    }
}