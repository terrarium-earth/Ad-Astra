package earth.terrarium.ad_astra.common.block.pipe;

import earth.terrarium.ad_astra.common.registry.ModSoundEvents;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluidPipeBlock extends AbstractPipeBlock {

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

    public FluidPipeBlock(long transferRate, int decay, double size, Properties properties) {
        super(transferRate, decay, size, properties);
        this.registerDefaultState(defaultBlockState().setValue(UP, PipeState.NONE).setValue(DOWN, PipeState.NONE).setValue(NORTH, PipeState.NONE).setValue(EAST, PipeState.NONE).setValue(SOUTH, PipeState.NONE).setValue(WEST, PipeState.NONE).setValue(WATERLOGGED, false));
        this.stateDefinition.getPossibleStates().forEach(state -> shapes.put(state, this.createShape(state)));
    }

    @Override
    public void appendHoverText(ItemStack stack, BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add((Component.translatable("item.ad_astra.fluid_transfer_rate.tooltip", FluidHooks.toMillibuckets(this.getTransferRate())).setStyle(Style.EMPTY.withColor(ChatFormatting.BLUE))));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FluidPipeBlockEntity(pos, state);
    }

    @Override
    public void updateShape(Level level, BlockPos pos, BlockState state, Direction direction) {
        if (!level.isClientSide) {
            BlockPos offset = pos.relative(direction);
            BlockEntity entity = level.getBlockEntity(offset);
            if (entity != null && (level.getBlockState(offset).getBlock() instanceof FluidPipeBlock || FluidHooks.safeGetBlockFluidManager(entity, direction).orElse(null) != null)) {
                if (level.getBlockState(pos).getValue(DIRECTIONS.get(direction)).equals(PipeState.NONE)) {
                    level.setBlock(pos, level.getBlockState(pos).setValue(DIRECTIONS.get(direction), PipeState.NORMAL), Block.UPDATE_ALL);
                }
                if (level.getBlockState(offset).getBlock().equals(this)) {
                    level.setBlock(offset, level.getBlockState(offset).setValue(DIRECTIONS.get(direction.getOpposite()), PipeState.NORMAL), Block.UPDATE_ALL);
                }
            } else {
                if (level.getBlockState(pos).getValue(DIRECTIONS.get(direction)).equals(PipeState.NORMAL)) {
                    level.setBlock(pos, level.getBlockState(pos).setValue(DIRECTIONS.get(direction), PipeState.NONE), Block.UPDATE_ALL);
                }
            }
        }
    }

    @Override
    public @NotNull VoxelShape updateOutlineShape(BlockState state) {
        return shapes.getOrDefault(state, Shapes.block());
    }

    public VoxelShape createShape(BlockState state) {
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

    @Override
    public void handleWrench(Level level, BlockPos pos, BlockState state, Direction side, Player user, Vec3 hitPos) {
        if (!level.isClientSide) {
            EnumProperty<PipeState> property = DIRECTIONS.get(getDirectionByVec(hitPos, pos).orElse(user.isShiftKeyDown() ? side.getOpposite() : side));
            level.setBlock(pos, togglePipeState(state, property, user), Block.UPDATE_ALL, 0);
            level.playSound(null, pos, ModSoundEvents.WRENCH.get(), SoundSource.BLOCKS, 1, 1);
        }
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
}