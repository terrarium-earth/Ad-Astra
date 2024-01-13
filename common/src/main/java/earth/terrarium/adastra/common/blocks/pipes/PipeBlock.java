package earth.terrarium.adastra.common.blocks.pipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.adastra.common.blockentities.pipes.PipeBlockEntity;
import earth.terrarium.adastra.common.blocks.base.BasicEntityBlock;
import earth.terrarium.adastra.common.blocks.base.Wrenchable;
import earth.terrarium.adastra.common.blocks.properties.PipeProperty;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.registry.ModSoundEvents;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.*;

@SuppressWarnings("deprecation")
public class PipeBlock extends BasicEntityBlock implements SimpleWaterloggedBlock, Wrenchable, TransferablePipe {
    public static final MapCodec<PipeBlock> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
                Codec.LONG.fieldOf("transfer_rate").forGetter(PipeBlock::transferRate),
                Type.CODEC.fieldOf("type").forGetter(PipeBlock::type),
                Codec.DOUBLE.fieldOf("size").forGetter(PipeBlock::size),
                propertiesCodec()
            )
            .apply(instance, PipeBlock::new)
    );

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final EnumProperty<PipeProperty> CONNECTED_UP = EnumProperty.create("connected_up", PipeProperty.class);
    public static final EnumProperty<PipeProperty> CONNECTED_DOWN = EnumProperty.create("connected_down", PipeProperty.class);
    public static final EnumProperty<PipeProperty> CONNECTED_NORTH = EnumProperty.create("connected_north", PipeProperty.class);
    public static final EnumProperty<PipeProperty> CONNECTED_EAST = EnumProperty.create("connected_east", PipeProperty.class);
    public static final EnumProperty<PipeProperty> CONNECTED_SOUTH = EnumProperty.create("connected_south", PipeProperty.class);
    public static final EnumProperty<PipeProperty> CONNECTED_WEST = EnumProperty.create("connected_west", PipeProperty.class);

    public static final EnumMap<Direction, EnumProperty<PipeProperty>> DIRECTION_TO_CONNECTION = Util.make(new EnumMap<>(Direction.class), map -> {
        map.put(Direction.UP, CONNECTED_UP);
        map.put(Direction.DOWN, CONNECTED_DOWN);
        map.put(Direction.NORTH, CONNECTED_NORTH);
        map.put(Direction.EAST, CONNECTED_EAST);
        map.put(Direction.SOUTH, CONNECTED_SOUTH);
        map.put(Direction.WEST, CONNECTED_WEST);
    });

    private final Map<BlockState, VoxelShape> shapes = new HashMap<>();

    private final long transferRate;
    private final Type type;
    private final double size;

    public PipeBlock(long transferRate, Type type, double size, Properties properties) {
        super(properties, true);
        this.transferRate = transferRate;
        this.type = type;
        this.size = size;
        registerDefaultState(stateDefinition.any()
            .setValue(WATERLOGGED, false)
            .setValue(CONNECTED_UP, PipeProperty.NONE)
            .setValue(CONNECTED_DOWN, PipeProperty.NONE)
            .setValue(CONNECTED_NORTH, PipeProperty.NONE)
            .setValue(CONNECTED_EAST, PipeProperty.NONE)
            .setValue(CONNECTED_SOUTH, PipeProperty.NONE)
            .setValue(CONNECTED_WEST, PipeProperty.NONE));
        if (this.size > 0) {
            stateDefinition.getPossibleStates().forEach(state -> shapes.put(state, makeShape(state, this.size)));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED,
            CONNECTED_UP, CONNECTED_DOWN,
            CONNECTED_NORTH, CONNECTED_EAST,
            CONNECTED_SOUTH, CONNECTED_WEST);
    }

    @Override
    public void appendHoverText(ItemStack stack, BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        if (type == Type.ENERGY) {
            tooltip.add(Component.translatable("tooltip.ad_astra.energy_transfer_tick", transferRate).withStyle(ChatFormatting.GOLD));
            TooltipUtils.addDescriptionComponent(tooltip, ConstantComponents.CABLE_INFO);
        } else {
            tooltip.add(Component.translatable("tooltip.ad_astra.fluid_transfer_tick", FluidConstants.toMillibuckets(transferRate)).withStyle(ChatFormatting.GOLD));
            TooltipUtils.addDescriptionComponent(tooltip, ConstantComponents.FLUID_PIPE_INFO);
        }
    }

    @Override
    public long transferRate() {
        return transferRate;
    }

    @Override
    public Type type() {
        return type;
    }

    public double size() {
        return size;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapes.getOrDefault(state, Shapes.block());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ?
            Fluids.WATER.getSource(false) :
            super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState().setValue(WATERLOGGED, fluidState.getType().equals(Fluids.WATER));
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
    public void onWrench(Level level, BlockPos pos, BlockState state, Direction side, Player user, Vec3 hitPos) {
        if (!level.isClientSide()) {
            var property = DIRECTION_TO_CONNECTION.get(getDirectionByVec(hitPos, pos).orElse(user.isShiftKeyDown() ? side.getOpposite() : side));
            level.setBlockAndUpdate(pos, state.cycle(property));
            level.playSound(null, pos, ModSoundEvents.WRENCH.get(), SoundSource.BLOCKS, 1, level.random.nextFloat() * 0.2f + 0.9f);

            user.displayClientMessage(switch (level.getBlockState(pos).getValue(property)) {
                case NONE -> ConstantComponents.PIPE_NONE;
                case NORMAL -> ConstantComponents.PIPE_NORMAL;
                case INSERT -> ConstantComponents.PIPE_INSERT;
                case EXTRACT -> ConstantComponents.PIPE_EXTRACT;
            }, true);
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        update(level, pos);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        update((Level) level, pos, state, direction);
        if (level.getBlockEntity(pos) instanceof PipeBlockEntity pipe) {
            pipe.pipeChanged((Level) level, pos);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    private void update(Level level, BlockPos pos) {
        if (level.isClientSide()) return;
        for (var direction : Direction.values()) {
            update(level, pos, level.getBlockState(pos), direction);
        }

        if (level.getBlockEntity(pos) instanceof PipeBlockEntity pipe) {
            pipe.pipeChanged(level, pos);
        }
    }

    private void update(Level level, BlockPos pos, BlockState state, Direction direction) {
        if (level.isClientSide()) return;
        var directionProperty = DIRECTION_TO_CONNECTION.get(direction);

        if (canConnect(level, pos, direction)) {
            level.setBlockAndUpdate(pos, state.setValue(directionProperty, PipeProperty.NORMAL));
        } else {
            level.setBlockAndUpdate(pos, state.setValue(directionProperty, PipeProperty.NONE));
        }
    }

    private boolean canConnect(Level level, BlockPos pos, Direction direction) {
        var connectPos = pos.relative(direction);
        var connectState = level.getBlockState(connectPos);
        if (connectState.getBlock() instanceof TransferablePipe pipe) {
            return pipe.type() == type;
        }

        var entity = level.getBlockEntity(connectPos);
        if (entity == null) return false;

        if (type == Type.ENERGY) {
            return EnergyContainer.holdsEnergy(entity, direction.getOpposite());
        } else if (type == Type.FLUID) {
            return FluidContainer.holdsFluid(entity, direction.getOpposite());
        }
        return false;
    }

    public static Direction[] getConnectedDirections(BlockState state) {
        return DIRECTION_TO_CONNECTION.entrySet()
            .stream()
            .filter(entry -> !state.getValue(entry.getValue()).isNone())
            .map(Map.Entry::getKey)
            .toArray(Direction[]::new);
    }

    public static VoxelShape makeShape(BlockState state, double size) {
        VoxelShape shape = Shapes.box(size, size, size, 1 - size, 1 - size, 1 - size);

        if (!state.getValue(CONNECTED_UP).isNone()) {
            shape = Shapes.or(shape, Shapes.box(size, size, size, 1 - size, 1, 1 - size));
        }
        if (!state.getValue(CONNECTED_DOWN).isNone()) {
            shape = Shapes.or(shape, Shapes.box(size, 0, size, 1 - size, 1 - size, 1 - size));
        }
        if (!state.getValue(CONNECTED_NORTH).isNone()) {
            shape = Shapes.or(shape, Shapes.box(size, size, 0, 1 - size, 1 - size, 1 - size));
        }
        if (!state.getValue(CONNECTED_EAST).isNone()) {
            shape = Shapes.or(shape, Shapes.box(size, size, size, 1, 1 - size, 1 - size));
        }
        if (!state.getValue(CONNECTED_SOUTH).isNone()) {
            shape = Shapes.or(shape, Shapes.box(size, size, size, 1 - size, 1 - size, 1));
        }
        if (!state.getValue(CONNECTED_WEST).isNone()) {
            shape = Shapes.or(shape, Shapes.box(0, size, size, 1 - size, 1 - size, 1 - size));
        }

        return shape;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
