package earth.terrarium.ad_astra.blocks.pipes;

import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FluidPipeBlockEntity extends BlockEntity implements InteractablePipe<PlatformFluidHandler> {
    private final List<Node<PlatformFluidHandler>> consumers = new ArrayList<>();
    private Node<PlatformFluidHandler> source;

    public FluidPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLUID_PIPE.get(), pos, state);
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

        if (!(state.getBlock() instanceof FluidPipeBlock) || !(state2.getBlock() instanceof FluidPipeBlock)) {
            return;
        }

        PipeState pipeState = state.getValue(FluidPipeBlock.DIRECTIONS.get(this.getSource().direction()));
        PipeState pipeState2 = state2.getValue(FluidPipeBlock.DIRECTIONS.get(direction));

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
                    FluidHolder transfer = FluidHooks.newFluidHolder(fluid.getFluid(), ((FluidPipeBlock) this.getBlockState().getBlock()).getTransferRate(), fluid.getCompound());
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
        if (this.getBlockState().getBlock() instanceof FluidPipeBlock fluidPipe) {
            return fluidPipe.getTransferRate();
        }
        return 0;
    }

    @Override
    public BlockPos getPipePos() {
        return this.getBlockPos();
    }
}