package com.github.alexnijjar.ad_astra.blocks.pipes;

import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class FluidPipeBlockEntity extends BlockEntity implements InteractablePipe<Storage<FluidVariant>> {
    private List<Node<Storage<FluidVariant>>> consumers = new ArrayList<>();
    private Node<Storage<FluidVariant>> source;

    public FluidPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLUID_PIPE, pos, state);
    }

    @Override
    public boolean supportsAutoExtract() {
        return true;
    }

    @Override
    public boolean canTakeFrom(Storage<FluidVariant> source) {
        return source.supportsExtraction();
    }

    @Override
    public boolean canInsertInto(Storage<FluidVariant> consumer) {
        return consumer.supportsInsertion();
    }

    @Override
    public boolean canConnectTo(BlockEntity next) {
        return true;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void insertInto(Storage<FluidVariant> consumer, Direction direction, BlockPos pos) {

        BlockState state = this.getCachedState();
        BlockState state2 = world.getBlockState(pos);

        if (state.isAir() || state2.isAir()) {
            return;
        }

        if (!(state.getBlock() instanceof FluidPipeBlock && state2.getBlock() instanceof FluidPipeBlock)) {
            return;
        }

        PipeState pipeState = state.get(FluidPipeBlock.DIRECTIONS.get(source.direction()));
        PipeState pipeState2 = state2.get(FluidPipeBlock.DIRECTIONS.get(direction));

        Storage<FluidVariant> input;
        Storage<FluidVariant> output;

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
            StorageUtil.move(input, output, f -> true, Math.max(0, this.getTransferAmount() / getConsumers().size()), null);
        }
    }

    @Override
    public Storage<FluidVariant> getInteraction(World world, BlockPos pos, Direction direction) {
        return FluidStorage.SIDED.find(world, pos, direction);
    }

    @Override
    public Node<Storage<FluidVariant>> getSource() {
        return source;
    }

    @Override
    public void setSource(Node<Storage<FluidVariant>> source) {
        this.source = source;
    }

    @Override
    public void clearSource() {
        this.source = null;
    }

    @Override
    public List<Node<Storage<FluidVariant>>> getConsumers() {
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