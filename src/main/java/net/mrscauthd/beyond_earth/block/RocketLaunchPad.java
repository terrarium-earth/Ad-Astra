package net.mrscauthd.beyond_earth.block;

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
import net.mrscauthd.beyond_earth.ModInit;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RocketLaunchPad extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty STAGE = BlockStateProperties.LIT;

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
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return !world.getBlockState(pos.below()).is(state.getBlock());
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
        int y = pos.getY();

        /** Pos for 3x3 */
        int x = pos.getX() - 1;
        int z = pos.getZ() - 1;

        /** Pos for 4x4 */
        int x2 = pos.getX() - 2;
        int z2 = pos.getZ() - 2;

        /** Lists */
        List<Boolean> flag1 = new ArrayList<>();
        List<Boolean> flag2 = new ArrayList<>();

        /** Check if Launch Pad 3x3 */
        for (int f1 = x; f1 < x + 3; f1++) {
            for (int f2 = z; f2 < z + 3; f2++) {
                BlockPos pos2 = new BlockPos(f1, y, f2);

                flag1.add(world.getBlockState(pos2).is(ModInit.ROCKET_LAUNCH_PAD.get()));
            }
        }

        /** Check if Launch Pad 5x5 STAGE false */
        for (int f1 = x2; f1 < x2 + 5; f1++) {
            for (int f2 = z2; f2 < z2 + 5; f2++) {
                BlockPos pos2 = new BlockPos(f1, y, f2);

                if (world.getBlockState(pos2).is(ModInit.ROCKET_LAUNCH_PAD.get()) && !pos2.equals(pos)) {
                    flag2.add(world.getBlockState(pos2).getValue(STAGE));
                }
            }
        }

        /** Variable Setter */
        if (!flag1.contains(false)) {
            if (!state.getValue(STAGE) && !flag2.contains(true)) {
                world.setBlock(pos, state.setValue(STAGE, true), 2);
            }
        } else {
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
