package net.mrscauthd.beyond_earth.blocks.machines.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;

public class OxygenBubbleDistributorBlockEntity extends AbstractMachineBlockEntity {
    public OxygenBubbleDistributorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_BUBBLE_DISTRIBUTOR_ENTITY, blockPos, blockState);
    }
}
