package earth.terrarium.adastra.common.blockentities.pipes;

import earth.terrarium.adastra.common.blocks.properties.PipeProperty;
import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class FluidPipeBlockEntity extends PipeBlockEntity {

    public FluidPipeBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public void addNode(@NotNull BlockEntity entity, PipeProperty pipeProperty, Direction direction, BlockPos pos) {
        var container = FluidApi.getBlockFluidContainer(entity, direction);
        if (container == null) return;

        if (pipeProperty.isNone()) return;
        if (!pipeProperty.isInsert() && (pipeProperty.isExtract() || container.extractFluid(container.getFluids().get(0), true).getFluidAmount() > 0)) {
            sources.put(pos, direction);
        } else if (pipeProperty.isNormal() || pipeProperty.isInsert()) {
            consumers.put(pos, direction);
        }
    }

    @Override
    public void moveContents(long transferRate, @NotNull BlockEntity source, @NotNull BlockEntity consumer, Direction direction) {
        if (!(FluidApi.isFluidContainingBlock(source, direction))) return;
        var sourceContainer = FluidApi.getBlockFluidContainer(source, direction);
        if (!(FluidApi.isFluidContainingBlock(consumer, direction))) return;
        var consumerContainer = FluidApi.getBlockFluidContainer(consumer, direction);
        for (var fluid : sourceContainer.getFluids()) {
            if (fluid.isEmpty()) continue;
            var toTransfer = FluidHooks.newFluidHolder(fluid.getFluid(), Math.min(transferRate, fluid.getFluidAmount()), null);
            FluidApi.moveFluid(sourceContainer, consumerContainer, toTransfer, false);
        }
    }

    @Override
    public boolean isValid(@NotNull BlockEntity entity, Direction direction) {
        return FluidApi.isFluidContainingBlock(entity, direction.getOpposite());
    }
}
