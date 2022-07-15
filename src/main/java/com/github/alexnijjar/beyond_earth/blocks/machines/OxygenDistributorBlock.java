package com.github.alexnijjar.beyond_earth.blocks.machines;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.OxygenDistributorBlockEntity;
import com.github.alexnijjar.beyond_earth.client.utils.ClientOxygenUtils;
import com.github.alexnijjar.beyond_earth.util.OxygenUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

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
        removeOxygen(world, pos);
        super.onBreak(world, pos, state, player);
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        removeOxygen(world, pos);
        super.onDestroyedByExplosion(world, pos, explosion);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    public static void removeOxygen(World world, BlockPos pos) {
        if (!world.isClient) {
            OxygenUtils.removeEntry(world, pos);
        } else {
            ClientOxygenUtils.removeEntry(world, pos);
        }
    }
}