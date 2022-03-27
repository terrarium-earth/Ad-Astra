package net.mrscauthd.beyond_earth.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.mrscauthd.beyond_earth.blocks.machines.entity.NasaWorkbenchBlockEntity;

public class NasaWorkbenchBlock extends AbstractMachineBlock {


    public NasaWorkbenchBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean useFacing() {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d offset = state.getModelOffset(world, pos);
        return VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 19.2, 16)).offset(offset.x, offset.y, offset.z);
    }

    @Override
    public NasaWorkbenchBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NasaWorkbenchBlockEntity(pos, state);
    }
}