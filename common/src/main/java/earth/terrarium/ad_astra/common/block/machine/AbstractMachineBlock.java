package earth.terrarium.ad_astra.common.block.machine;

import earth.terrarium.ad_astra.common.block.machine.entity.AbstractMachineBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.botarium.api.menu.MenuHooks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.ToIntFunction;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
public abstract class AbstractMachineBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public AbstractMachineBlock(Properties properties) {
        super(properties.lightLevel(getLuminance()));
        this.registerDefaultState(this.buildDefaultState());
    }

    private static ToIntFunction<BlockState> getLuminance() {
        return (blockState) -> blockState.hasProperty(LIT) ? (blockState.getValue(LIT) ? ((AbstractMachineBlock) blockState.getBlock()).getBrightness() : 0) : 0;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (entityWorld, pos, entityState, blockEntity) -> {
            if (blockEntity instanceof AbstractMachineBlockEntity machine) {
                machine.tick();
            }
        };
    }

    protected BlockState buildDefaultState() {
        BlockState state = this.stateDefinition.any();

        state = state.setValue(POWERED, false);
        if (this.useFacing()) {
            state = state.setValue(FACING, Direction.NORTH);
        }
        if (this.useLit()) {
            state = state.setValue(LIT, false);
        }

        return state;
    }

    protected boolean useFacing() {
        return false;
    }

    protected boolean useLit() {
        return false;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack item = player.getItemInHand(hand);

        if (item.is(ModTags.WRENCHES) && this.useFacing()) {
            level.setBlock(pos, this.rotate(state, Rotation.CLOCKWISE_90), Block.UPDATE_ALL);
            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof AbstractMachineBlockEntity machineBlock) {
                MenuHooks.openMenu((ServerPlayer) player, machineBlock);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        if (this.useFacing()) {
            return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
        } else {
            return state;
        }
    }

    public int getBrightness() {
        return 12;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AbstractMachineBlockEntity machineBlock) {
                if (machineBlock.getInventorySize() > 0) {
                    if (this.removeOutput()) {
                        machineBlock.removeItemNoUpdate(machineBlock.getInventorySize() - 1);
                    }
                    Containers.dropContents(level, pos, machineBlock);
                    level.updateNeighbourForOutputSignal(pos, this);
                }
            }
            super.onRemove(state, level, pos, newState, moved);
        }
    }

    public boolean removeOutput() {
        return false;
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        if (this.useFacing()) {
            builder.add(FACING);
        }

        builder.add(POWERED);

        if (this.useLit()) {
            builder.add(LIT);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState state = this.defaultBlockState().setValue(POWERED, false);
        return this.useFacing() ? state.setValue(FACING, ctx.getHorizontalDirection().getOpposite()) : state;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public boolean doRedstoneCheck() {
        return true;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborChanged(state, level, pos, block, fromPos, notify);

        if (this.doRedstoneCheck()) {
            if (!level.isClientSide) {
                level.setBlockAndUpdate(pos, state.setValue(POWERED, level.hasNeighborSignal(pos)));
            }
        }
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);

        return blockEntity instanceof AbstractMachineBlockEntity ? AbstractContainerMenu.getRedstoneSignalFromBlockEntity(blockEntity) : 0;
    }
}