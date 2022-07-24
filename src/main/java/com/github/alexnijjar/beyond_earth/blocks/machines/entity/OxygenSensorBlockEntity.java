package com.github.alexnijjar.beyond_earth.blocks.machines.entity;

import com.github.alexnijjar.beyond_earth.blocks.machines.OxygenSensorBlock;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;
import com.github.alexnijjar.beyond_earth.util.OxygenUtils;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class OxygenSensorBlockEntity extends AbstractMachineBlockEntity {

    public static final Direction[] CHECK_DIRECTIONS = new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP };

    public OxygenSensorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_SENSOR, blockPos, blockState);
    }

    @Override
    public void tick() {
        if (this.world instanceof ServerWorld serverWorld) {
            boolean hasOxygen = false;
            for (Direction dir : CHECK_DIRECTIONS) {
                if (OxygenUtils.posHasOxygen(serverWorld, this.pos.offset(dir))) {
                    hasOxygen = true;
                    break;
                }
            }
            serverWorld.setBlockState(this.getPos(), this.getCachedState().with(OxygenSensorBlock.POWERED, hasOxygen));
        }
    }
}
