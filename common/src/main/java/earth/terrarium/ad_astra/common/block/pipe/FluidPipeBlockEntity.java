package earth.terrarium.ad_astra.common.block.pipe;

import earth.terrarium.ad_astra.common.block.machine.BasicContainer;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.PlatformFluidHandler;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class FluidPipeBlockEntity extends BlockEntity implements InteractablePipe<PlatformFluidHandler> {
    private final List<Node<PlatformFluidHandler>> consumers = new ArrayList<>();
    private Node<PlatformFluidHandler> source;

    public FluidPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.FLUID_PIPE.get(), pos, state);
    }

    @Override
    public boolean supportsAutoExtract() {
        return true;
    }

    @Override
    public boolean canConnectTo(BlockEntity next, Direction direction, BlockPos pos) {
        return true;
    }

    @Override
    public void insertInto(PlatformFluidHandler consumer, Direction direction, BlockPos pos) {
        if (level == null) return;
        BlockState state = getBlockState();
        BlockState state2 = level.getBlockState(pos);
        if (state.isAir() || state2.isAir()) return;
        PipeState pipeState = state.getValue(PipeBlock.DIRECTIONS.get(getSource().direction()));
        PipeState pipeState2 = state2.getValue(PipeBlock.DIRECTIONS.get(direction));

        if (getSource().storage() == null || getConsumers().isEmpty()) return;
        if (pipeState == PipeState.INSERT && pipeState2 == PipeState.INSERT) return;
        if (pipeState == PipeState.EXTRACT && pipeState2 == PipeState.EXTRACT) return;
        if (pipeState == PipeState.NONE || pipeState2 == PipeState.NONE) return;

        PlatformFluidHandler input = getSource().storage();
        PlatformFluidHandler output = consumer;

        if (pipeState2 == PipeState.EXTRACT || pipeState == PipeState.INSERT) {
            input = consumer;
            output = getSource().storage();
        }

        for (FluidHolder fluid : input.getFluidTanks()) {
            if (!fluid.isEmpty()) {
                FluidHolder transfer = FluidHooks.newFluidHolder(fluid.getFluid(), ((PipeBlock) getBlockState().getBlock()).getTransferRate(), fluid.getCompound());
                FluidHooks.moveFluid(input, output, transfer);
            }
        }
        if (level.getBlockEntity(pos.relative(direction)) instanceof BasicContainer container) {
            container.update();
        }
    }

    @Override
    public PlatformFluidHandler getInteraction(Level level, BlockPos pos, Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity != null) {
            return FluidHooks.safeGetBlockFluidManager(blockEntity, direction).orElse(null);
        }
        return null;
    }

    @Override
    public Node<PlatformFluidHandler> getSource() {
        return source;
    }

    @Override
    public void setSource(Node<PlatformFluidHandler> source) {
        this.source = source;
    }

    @Override
    public void clearSource() {
        source = null;
    }

    @Override
    public List<Node<PlatformFluidHandler>> getConsumers() {
        return consumers;
    }

    @Override
    public int getWorkTime() {
        return 5;
    }

    @Override
    public Level getPipelevel() {
        return level;
    }

    @Override
    public long getTransferAmount() {
        if (getBlockState().getBlock() instanceof PipeBlock fluidPipe) {
            return fluidPipe.getTransferRate();
        }
        return 0;
    }

    @Override
    public BlockPos getPipePos() {
        return getBlockPos();
    }
}
