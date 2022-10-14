package com.github.alexnijjar.ad_astra.blocks.pipes;

import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;

import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

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

    // TODO: Adrian needs to add insertion and extraction booleans on handlers
    @Override
    public boolean canTakeFrom(PlatformFluidHandler source) {
        return source != null;
    }

    @Override
    public boolean canInsertInto(PlatformFluidHandler consumer) {
        return consumer != null;
    }

    @Override
    public boolean canConnectTo(BlockEntity next, Direction direction, BlockPos pos) {
        return true;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
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

        if (pipeState.equals(PipeState.INSERT) && pipeState2.equals(PipeState.INSERT)) {
            return;
        } else if (pipeState.equals(PipeState.EXTRACT) && pipeState2.equals(PipeState.EXTRACT)) {
            return;
        } else if (pipeState.equals(PipeState.NONE) || pipeState2.equals(PipeState.NONE)) {
            return;
        } else if (pipeState2.equals(PipeState.INSERT) && !pipeState2.equals(PipeState.NONE)) {
            input = source.storage();
            output = consumer;
        } else if (pipeState2.equals(PipeState.EXTRACT) && !pipeState2.equals(PipeState.NONE)) {
            input = consumer;
            output = source.storage();
        } else if (pipeState.equals(PipeState.INSERT) && !pipeState.equals(PipeState.NONE)) {
            input = consumer;
            output = source.storage();
        } else if (pipeState.equals(PipeState.EXTRACT) && !pipeState.equals(PipeState.NONE)) {
            input = source.storage();
            output = consumer;
        } else {
            return;
        }

        if (getSource() != null && getConsumers().size() > 0) {
            FluidHooks.moveFluid(input, output, input.getFluidInTank(0));
        }
    }

    @Override
    public PlatformFluidHandler getInteraction(World world, BlockPos pos, Direction direction) {
        return FluidHooks.safeGetBlockFluidManager(world.getBlockEntity(pos.offset(direction)), direction).orElse(null);
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