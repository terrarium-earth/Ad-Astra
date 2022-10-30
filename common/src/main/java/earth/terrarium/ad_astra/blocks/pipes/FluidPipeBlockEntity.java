package earth.terrarium.ad_astra.blocks.pipes;

import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

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
    public boolean canTakeFrom(PlatformFluidHandler source) {
        if (source.getFluidInTank(0).isEmpty()) {
            return false;
        }
        return source.supportsExtraction();
    }

    @Override
    public boolean canInsertInto(PlatformFluidHandler consumer) {
        if (consumer.getFluidInTank(0).isEmpty()) {
            return false;
        }
        return consumer.supportsInsertion();
    }

    @Override
    public boolean canConnectTo(BlockEntity next, Direction direction, BlockPos pos) {
        return true;
    }

    @Override
    public void insertInto(PlatformFluidHandler consumer, Direction direction, BlockPos pos) {

        BlockState state = this.getCachedState();
        BlockState state2 = world.getBlockState(pos);

        if (!(state.getBlock() instanceof FluidPipeBlock) || !(state2.getBlock() instanceof FluidPipeBlock)) {
            return;
        }

        PipeState pipeState = state.get(FluidPipeBlock.DIRECTIONS.get(this.getSource().direction()));
        PipeState pipeState2 = state2.get(FluidPipeBlock.DIRECTIONS.get(direction));

        PlatformFluidHandler input;
        PlatformFluidHandler output;

        if (pipeState == PipeState.INSERT && pipeState2 == PipeState.INSERT) {
            return;
        } else if (pipeState == PipeState.EXTRACT && pipeState2 == PipeState.EXTRACT) {
            return;
        } else if (pipeState == PipeState.NONE || pipeState2 == PipeState.NONE) {
            return;
        } else if (pipeState2 == PipeState.INSERT) {
            input = source.storage();
            output = consumer;
        } else if (pipeState2 == PipeState.EXTRACT) {
            input = consumer;
            output = source.storage();
        } else if (pipeState == PipeState.INSERT) {
            input = consumer;
            output = source.storage();
        } else if (pipeState == PipeState.EXTRACT) {
            input = source.storage();
            output = consumer;
        } else {
            return;
        }

        if (getSource() != null && getConsumers().size() > 0) {
            if (!input.getFluidInTank(0).getFluid().equals(Fluids.EMPTY)) {
                FluidHooks.moveFluid(input, output, input.getFluidInTank(0));
            }
        }
    }

    @Override
    public PlatformFluidHandler getInteraction(World world, BlockPos pos, Direction direction) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
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
    public World getPipeWorld() {
        return this.world;
    }

    @Override
    public long getTransferAmount() {
        if (this.getCachedState().getBlock() instanceof FluidPipeBlock fluidPipe) {
            return fluidPipe.getTransferRate();
        }
        return 0;
    }

    @Override
    public BlockPos getPipePos() {
        return this.getPos();
    }
}