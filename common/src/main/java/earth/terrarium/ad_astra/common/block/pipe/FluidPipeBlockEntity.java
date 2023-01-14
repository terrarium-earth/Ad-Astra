package earth.terrarium.ad_astra.common.block.pipe;

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
        BlockState state = this.getBlockState();
        BlockState state2 = level.getBlockState(pos);

        if (!(state.getBlock() instanceof PipeBlock) || !(state2.getBlock() instanceof PipeBlock)) {
            return;
        }

        PipeState pipeState = state.getValue(PipeBlock.DIRECTIONS.get(this.getSource().direction()));
        PipeState pipeState2 = state2.getValue(PipeBlock.DIRECTIONS.get(direction));

        PlatformFluidHandler input;
        PlatformFluidHandler output;

        if (pipeState == PipeState.INSERT && pipeState2 == PipeState.INSERT) {
            return;
        } else if (pipeState == PipeState.EXTRACT && pipeState2 == PipeState.EXTRACT) {
            return;
        } else if (pipeState == PipeState.NONE || pipeState2 == PipeState.NONE) {
            return;
        } else if (pipeState2 == PipeState.INSERT || pipeState == PipeState.EXTRACT) {
            input = source.storage();
            output = consumer;
        } else if (pipeState2 == PipeState.EXTRACT || pipeState == PipeState.INSERT) {
            input = consumer;
            output = source.storage();
        } else {
            return;
        }

        if (getSource() != null && getConsumers().size() > 0) {
            for (FluidHolder fluid : input.getFluidTanks()) {
                if (!fluid.isEmpty()) {
                    FluidHolder transfer = FluidHooks.newFluidHolder(fluid.getFluid(), ((PipeBlock) this.getBlockState().getBlock()).getTransferRate(), fluid.getCompound());
                    FluidHooks.moveFluid(input, output, transfer);
                }
            }
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
        this.source = null;
    }

    @Override
    public List<Node<PlatformFluidHandler>> getConsumers() {
        return this.consumers;
    }

    @Override
    public int getWorkTime() {
        return 5;
    }

    @Override
    public Level getPipelevel() {
        return this.level;
    }

    @Override
    public long getTransferAmount() {
        if (this.getBlockState().getBlock() instanceof PipeBlock fluidPipe) {
            return fluidPipe.getTransferRate();
        }
        return 0;
    }

    @Override
    public BlockPos getPipePos() {
        return this.getBlockPos();
    }
}