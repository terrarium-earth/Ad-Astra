package net.mrscauthd.beyond_earth.blocks.flags;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class FlagBlock extends BlockWithEntity implements Waterloggable, BlockEntityProvider {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public FlagBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d offset = state.getModelOffset(world, pos);

        if (state.get(HALF).equals(DoubleBlockHalf.LOWER)) {
            return switch (state.get(FACING)) {
                case SOUTH -> Stream.of(
                        boxSimple(14.5, 0, 9, 12.5, 1, 7),
                        boxSimple(14, 1, 8.5, 13, 16, 7.5)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get().offset(offset.getX(), offset.getY(), offset.getZ());
                case NORTH -> Stream.of(
                        boxSimple(1.5, 0, 7, 3.5, 1, 9),
                        boxSimple(2, 1, 7.5, 3, 16, 8.5)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get().offset(offset.getX(), offset.getY(), offset.getZ());
                case EAST -> Stream.of(
                        boxSimple(9, 0, 1.5, 7, 1, 3.5),
                        boxSimple(8.5, 1, 2, 7.5, 16, 3)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get().offset(offset.getX(), offset.getY(), offset.getZ());
                case WEST -> Stream.of(
                        boxSimple(7, 0, 14.5, 9, 1, 12.5),
                        boxSimple(7.5, 1, 14, 8.5, 16, 13)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get().offset(offset.getX(), offset.getY(), offset.getZ());
                default -> throw new IllegalStateException("Unexpected value: " + state.get(FACING));
            };
        } else {
            return switch (state.get(FACING)) {
                case SOUTH -> Stream.of(
                        boxSimple(14, 0, 8.5, 13, 16, 7.5),
                        boxSimple(14, 7, 8.5, 1, 15, 7.5)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get().offset(offset.getX(), offset.getY(), offset.getZ());
                case NORTH -> Stream.of(
                        boxSimple(2, 0, 7.5, 3, 16, 8.5),
                        boxSimple(2, 7, 7.5, 15, 15, 8.5)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get().offset(offset.getX(), offset.getY(), offset.getZ());
                case EAST -> Stream.of(
                        boxSimple(8.5, 0, 2, 7.5, 16, 3),
                        boxSimple(8.5, 7, 2, 7.5, 15, 15)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get().offset(offset.getX(), offset.getY(), offset.getZ());
                case WEST -> Stream.of(
                        boxSimple(7.5, 0, 14, 8.5, 16, 13),
                        boxSimple(7.5, 7, 14, 8.5, 15, 1)
                ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get().offset(offset.getX(), offset.getY(), offset.getZ());
                default -> throw new IllegalStateException("Unexpected value: " + state.get(FACING));
            };
        }
    }

    public static VoxelShape boxSimple(double x1, double y1, double z1, double x2, double y2, double z2) {
        Box box = new Box(x1, y1, z1, x2, y2, z2);
        return VoxelShapes.cuboid(box.minX / 16.0d, box.minY / 16.0d, box.minZ / 16.0d, box.maxX / 16.0d, box.maxY / 16.0d, box.maxZ / 16.0d);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()) {
            destroyOtherSide(state.get(HALF), world, pos, state, player);
        }
        super.onBreak(world, pos, state, player);
    }

    private static void destroyOtherSide(DoubleBlockHalf doubleblockhalf, World world, BlockPos pos, BlockState state, PlayerEntity player) {
        boolean isUpper = doubleblockhalf.equals(DoubleBlockHalf.UPPER);
        BlockPos blockpos = isUpper ? pos.down() : pos.up();
        if (world.getBlockState(blockpos).contains(HALF)) {
            if (world.getBlockState(blockpos).get(HALF).equals(isUpper ? DoubleBlockHalf.LOWER : DoubleBlockHalf.UPPER)) {
                world.breakBlock(blockpos, false);
            }
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);

        BlockEntity blockEntity = world.getBlockEntity(pos.up());

        if (placer instanceof PlayerEntity player) {
            if (blockEntity instanceof FlagBlockEntity flagEntity) {
                GameProfile profile = player.getGameProfile();
                NbtCompound compound = new NbtCompound();
                flagEntity.setOwner(profile);
                flagEntity.writeNbt(compound);
            }
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        if (state.get(HALF).equals(DoubleBlockHalf.UPPER)) {
            return new FlagBlockEntity(pos, state);
        }
        return null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public long getRenderingSeed(BlockState state, BlockPos pos) {
        return MathHelper.hashCode(pos.getX(), pos.down(state.get(HALF).equals(DoubleBlockHalf.LOWER) ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, HALF);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(WATERLOGGED, fluidState.getFluid().equals(Fluids.WATER));
    }
}
