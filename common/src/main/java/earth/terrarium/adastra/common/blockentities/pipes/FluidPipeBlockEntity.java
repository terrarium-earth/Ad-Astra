package earth.terrarium.adastra.common.blockentities.pipes;

import earth.terrarium.adastra.common.blocks.properties.PipeProperty;
import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
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
        if (pipeProperty.isNone()) return;
        var container = FluidContainer.of(entity, direction);
        if (container == null) return;
        var toTransfer = container.getFirstFluid();
        if (!pipeProperty.isInsert() && !toTransfer.isEmpty() && (pipeProperty.isExtract() || container.extractFluid(toTransfer, true).getFluidAmount() > 0)) {
            sources.put(pos, direction);
        } else if (pipeProperty.isNormal() || pipeProperty.isInsert()) {
            consumers.put(pos, direction);
        }
    }

    @Override
    public void moveContents(long transferRate, @NotNull BlockEntity source, @NotNull BlockEntity consumer, Direction direction) {
        if (!(FluidContainer.holdsFluid(source, direction))) return;
        var sourceContainer = FluidContainer.of(source, direction);
        if (sourceContainer == null) return;
        if (!(FluidContainer.holdsFluid(consumer, direction))) return;
        var consumerContainer = FluidContainer.of(consumer, direction);
        if (consumerContainer == null) return;
        for (var fluid : sourceContainer.getFluids()) {
            if (fluid.isEmpty()) continue;
            var toTransfer = FluidHolder.ofMillibuckets(fluid.getFluid(), Math.min(transferRate, fluid.getFluidAmount()));
            if (toTransfer.isEmpty()) continue;
            try {
                FluidApi.moveFluid(sourceContainer, consumerContainer, toTransfer, true);
                FluidApi.moveFluid(sourceContainer, consumerContainer, toTransfer, false);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    @Override
    public boolean isValid(@NotNull BlockEntity entity, Direction direction) {
        return FluidContainer.holdsFluid(entity, direction.getOpposite());
    }
}
