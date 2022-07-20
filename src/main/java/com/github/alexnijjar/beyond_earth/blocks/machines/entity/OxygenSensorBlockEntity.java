package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import com.github.alexnijjar.beyond_earth.blocks.machines.OxygenSensorBlock;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;
import com.github.alexnijjar.beyond_earth.util.OxygenUtils;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class OxygenSensorBlockEntity extends AbstractMachineBlockEntity {

    public OxygenSensorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_SENSOR, blockPos, blockState);
    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            this.world.setBlockState(this.getPos(), this.getCachedState().with(OxygenSensorBlock.POWERED, OxygenUtils.posHasOxygen(this.world, this.getPos().up())));
        }
    } 
}
