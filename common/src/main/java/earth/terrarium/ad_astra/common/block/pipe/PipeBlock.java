package earth.terrarium.ad_astra.common.block.pipe;

import earth.terrarium.ad_astra.common.block.BasicEntityBlock;
import earth.terrarium.ad_astra.common.block.Wrenchable;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.registry.ModSoundEvents;
import earth.terrarium.botarium.common.energy.util.EnergyHooks;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("deprecation")
public class PipeBlock extends BasicEntityBlock implements SimpleWaterloggedBlock, Pipe, Wrenchable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final EnumProperty<PipeState> UP = EnumProperty.create("up", PipeState.class);
    public static final EnumProperty<PipeState> DOWN = EnumProperty.create("down", PipeState.class);
    public static final EnumProperty<PipeState> NORTH = EnumProperty.create("north", PipeState.class);
    public static final EnumProperty<PipeState> EAST = EnumProperty.create("east", PipeState.class);
    public static final EnumProperty<PipeState> SOUTH = EnumProperty.create("south", PipeState.class);
    public static final EnumProperty<PipeState> WEST = EnumProperty.create("west", PipeState.class);

    public static final Map<Direction, EnumProperty<PipeState>> DIRECTIONS = Util.make(new HashMap<>(), map -> {
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
    });

    private final Map<BlockState, VoxelShape> shapes = new HashMap<>();
    private final long transferRate;
    private final PipeType type;

    public PipeBlock(long transferRate, double size, PipeType type, Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(UP, PipeState.NONE).setValue(DOWN, PipeState.NONE).setValue(NORTH, PipeState.NONE).setValue(EAST, PipeState.NONE).setValue(SOUTH, PipeState.NONE).setValue(WEST, PipeState.NONE).setValue(WATERLOGGED, false));
        stateDefinition.getPossibleStates().forEach(state -> shapes.put(state, createVoxelShape(state, size)));
        this.transferRate = transferRate;
        this.type = type;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack item = player.getItemInHand(hand);

        if (item.is(ModItems.WRENCH.get())) { // TODO: Use wrench tag
            handleWrench(level, pos, state, hit.getDirection(), player, hit.getLocation());
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    private boolean checkCompat(Level level, BlockPos offset) {
        if (level.getBlockState(offset).getBlock() instanceof PipeBlock pipe) {
            if (getType() == PipeType.FLUID_PIPE) {
                return pipe.getType() == PipeType.FLUID_PIPE;

            } else if (getType() == PipeType.CABLE) {
                return pipe.getType() == PipeType.CABLE;
            }
        } else if (level.getBlockState(offset).getBlock() instanceof PipeDuctBlock pipe) {
            if (getType() == PipeType.FLUID_PIPE) {
                return pipe.equals(ModBlocks.FLUID_PIPE_DUCT.get());

            } else if (getType() == PipeType.CABLE) {
                return pipe.equals(ModBlocks.CABLE_DUCT.get());
            }
        }
        return false;
    }

    protected void updatePipeState(Level level, BlockPos pos, Direction direction) {
        if (!level.isClientSide) {
            BlockPos offset = pos.relative(direction);
            BlockEntity entity = level.getBlockEntity(offset);
            if (entity != null && (checkCompat(level, offset) || (type == PipeType.FLUID_PIPE ? FluidHooks.safeGetBlockFluidManager(entity, direction).orElse(null) : EnergyHooks.safeGetBlockEnergyManager(entity, direction).orElse(null)) != null)) {
                if (level.getBlockState(pos).getValue(DIRECTIONS.get(direction)) == PipeState.NONE) {
                    level.setBlock(pos, level.getBlockState(pos).setValue(DIRECTIONS.get(direction), PipeState.NORMAL), Block.UPDATE_ALL);
                }
                if (level.getBlockState(offset).getBlock().equals(this)) {
                    level.setBlock(offset, level.getBlockState(offset).setValue(DIRECTIONS.get(direction.getOpposite()), PipeState.NORMAL), Block.UPDATE_ALL);
                }
            } else {
                if (level.getBlockState(pos).getValue(DIRECTIONS.get(direction)) == PipeState.NORMAL) {
                    level.setBlock(pos, level.getBlockState(pos).setValue(DIRECTIONS.get(direction), PipeState.NONE), Block.UPDATE_ALL);
                }
            }
        }
    }

    public void updatePipeState(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            for (Direction direction : Direction.values()) {
                updatePipeState(level, pos, direction);
            }
        }
    }

    @Override
    public long getTransferRate() {
        return transferRate;
    }

    public PipeType getType() {
        return type;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType().equals(Fluids.WATER));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    // Extend the pipe when something around it is updated.
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        updatePipeState((Level) level, pos, direction);

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (entityLevel, blockPos, blockState, blockEntity) -> {
            if (blockEntity instanceof InteractablePipe<?> pipe) {
                pipe.pipeTick();
            }
        };
    }

    // Extend the pipe when it is first placed.
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(level, pos, state, placer, itemStack);
        updatePipeState(level, pos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapes.getOrDefault(state, Shapes.block());
    }

    @Override
    public void appendHoverText(ItemStack stack, BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(type == PipeType.FLUID_PIPE ? Component.translatable("item.ad_astra.fluid_transfer_rate.tooltip", FluidHooks.toMillibuckets(getTransferRate())) : Component.translatable("item.ad_astra.energy_transfer_rate.tooltip", (getTransferRate())));
    }

    public BlockState togglePipeState(BlockState state, EnumProperty<PipeState> property, Player user) {
        state = state.cycle(property);
        switch (state.getValue(property)) {
            case NORMAL -> user.displayClientMessage(Component.translatable("info.ad_astra.normal"), true);
            case INSERT -> user.displayClientMessage(Component.translatable("info.ad_astra.insert"), true);
            case EXTRACT -> user.displayClientMessage(Component.translatable("info.ad_astra.extract"), true);
            case NONE -> user.displayClientMessage(Component.translatable("info.ad_astra.none"), true);
        }
        return state;
    }

    public static Optional<Direction> getDirectionByVec(Vec3 hit, BlockPos pos) {
        var relativePos = hit.add(-pos.getX(), -pos.getY(), -pos.getZ());
        if (relativePos.x < (2f / 16f)) return Optional.of(Direction.WEST);
        else if (relativePos.x > (14f / 16f)) return Optional.of(Direction.EAST);
        else if (relativePos.z < (2f / 16f)) return Optional.of(Direction.NORTH);
        else if (relativePos.z > (14f / 16f)) return Optional.of(Direction.SOUTH);
        else if (relativePos.y < (2f / 16f)) return Optional.of(Direction.DOWN);
        else if (relativePos.y > (14f / 16f)) return Optional.of(Direction.UP);
        return Optional.empty();
    }

    @Override
    public void handleWrench(Level level, BlockPos pos, BlockState state, Direction side, Player user, Vec3 hitPos) {
        if (!level.isClientSide) {
            EnumProperty<PipeState> property = DIRECTIONS.get(getDirectionByVec(hitPos, pos).orElse(user.isShiftKeyDown() ? side.getOpposite() : side));
            level.setBlock(pos, togglePipeState(state, property, user), Block.UPDATE_ALL, 0);
            level.playSound(null, pos, ModSoundEvents.WRENCH.get(), SoundSource.BLOCKS, 1, 1);
        }
    }

    public static VoxelShape createVoxelShape(BlockState state, double size) {
        VoxelShape shape = Shapes.box(size, size, size, 1 - size, 1 - size, 1 - size);

        if (state.getValue(UP) != PipeState.NONE) {
            shape = Shapes.or(shape, Shapes.box(size, size, size, 1 - size, 1, 1 - size));
        }
        if (state.getValue(DOWN) != PipeState.NONE) {
            shape = Shapes.or(shape, Shapes.box(size, 0, size, 1 - size, 1 - size, 1 - size));
        }
        if (state.getValue(NORTH) != PipeState.NONE) {
            shape = Shapes.or(shape, Shapes.box(size, size, 0, 1 - size, 1 - size, 1 - size));
        }
        if (state.getValue(EAST) != PipeState.NONE) {
            shape = Shapes.or(shape, Shapes.box(size, size, size, 1, 1 - size, 1 - size));
        }
        if (state.getValue(SOUTH) != PipeState.NONE) {
            shape = Shapes.or(shape, Shapes.box(size, size, size, 1 - size, 1 - size, 1));
        }
        if (state.getValue(WEST) != PipeState.NONE) {
            shape = Shapes.or(shape, Shapes.box(0, size, size, 1 - size, 1 - size, 1 - size));
        }

        return shape;
    }

    public enum PipeType {
        CABLE,
        FLUID_PIPE
    }
}
