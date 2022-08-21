package com.github.alexnijjar.ad_astra.blocks.machines;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;
import com.github.alexnijjar.ad_astra.client.utils.ClientOxygenUtils;
import com.github.alexnijjar.ad_astra.util.OxygenUtils;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OxygenDistributorBlock extends AbstractMachineBlock {

    public OxygenDistributorBlock(Settings settings) {
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
    public OxygenDistributorBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new OxygenDistributorBlockEntity(pos, state);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            OxygenUtils.removeEntry(world, pos);
        } else {
            ClientOxygenUtils.removeEntry(world, pos);
        }
        super.onBreak(world, pos, state, player);
    }
}