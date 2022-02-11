package net.mrscauthd.beyond_earth.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RocketLaunchPad extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty STAGE = BlockStateProperties.LIT;

    /** 3x3 */
    private static final List<Pair<Integer, Integer>> x = new ArrayList<>(List.of(
            Pair.of(0, 1),
            Pair.of(1, 1),
            Pair.of(1, 0),
            Pair.of(-1, 0),
            Pair.of(-1, -1),
            Pair.of(0, -1),
            Pair.of(1, -1),
            Pair.of(-1, 1)
    ));

    /** 4x4 */
    private static final List<Pair<Integer, Integer>> z = new ArrayList<>(List.of(
            Pair.of(0,2),
            Pair.of(0,-2),
            Pair.of(2,0),
            Pair.of(-2,0),
            Pair.of(-2,1),
            Pair.of(-2,-1),
            Pair.of(2,1),
            Pair.of(1,-2),
            Pair.of(2,-2),
            Pair.of(-2,-2),
            Pair.of(-1,-1),
            Pair.of(3,1),
            Pair.of(1,2),
            Pair.of(2,2)
    ));

    public RocketLaunchPad(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(STAGE, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER);
        return this.defaultBlockState().setValue(WATERLOGGED, flag);
    }

    @Override
    public BlockState updateShape(BlockState p_56285_, Direction p_56286_, BlockState p_56287_, LevelAccessor p_56288_, BlockPos p_56289_, BlockPos p_56290_) {
        if (p_56285_.getValue(WATERLOGGED)) {
            p_56288_.scheduleTick(p_56289_, Fluids.WATER, Fluids.WATER.getTickDelay(p_56288_));
        }

        return super.updateShape(p_56285_, p_56286_, p_56287_, p_56288_, p_56289_, p_56290_);
    }

    @Override
    public FluidState getFluidState(BlockState p_56299_) {
        return p_56299_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_56299_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, STAGE);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return (worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() != this.defaultBlockState().getBlock());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(world, pos);
        if (state.getValue(STAGE)) {
            return Shapes.or(box(0, 0, 0, 16, 4, 16)).move(offset.x, offset.y, offset.z);
        } else {
            return Shapes.or(box(0, 0, 0, 16, 3, 16)).move(offset.x, offset.y, offset.z);
        }
    }

    @Override
    public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, p_60567_, p_60568_, p_60569_, p_60570_);
        p_60567_.scheduleTick(p_60568_, this, 1);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        boolean canEdit = true;

        for (Pair<Integer, Integer> value : x) {
            BlockPos bp = new BlockPos(pos.getX() + value.getFirst(), pos.getY(), pos.getZ() + value.getSecond());

            if (!(world.getBlockState(bp).getBlock() instanceof RocketLaunchPad && !world.getBlockState(bp).getValue(STAGE))) {
                canEdit = false;
            }
        }

        for (Pair<Integer, Integer> val : z) {
            BlockPos bp = new BlockPos(pos.getX() + val.getFirst(), pos.getY(), pos.getZ() + val.getSecond());

            if (!(!(world.getBlockState(bp).getBlock() instanceof RocketLaunchPad) || world.getBlockState(bp).getBlock() instanceof RocketLaunchPad && !world.getBlockState(bp).getValue(STAGE))) {
                canEdit = false;
            }
        }

        if (canEdit) {
            if (!state.getValue(STAGE)) {
                world.setBlock(pos, state.setValue(STAGE, true), 2);
            }
        } else if (state.getValue(STAGE)) {
            if (state.getValue(STAGE)) {
                world.setBlock(pos, state.setValue(STAGE, false), 2);
            }
        }

        world.scheduleTick(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), this, 1);
    }

    @Override
    public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
        return false;
    }
}
