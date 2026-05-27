package earth.terrarium.adastra.common.blockentities.pipes;

import earth.terrarium.adastra.common.blocks.properties.PipeProperty;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.storage.util.TransferUtil;
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
        var container = FluidApi.BLOCK.find(entity, direction);
        if (container == null) return;
        var toTransfer = container.getContents(0);
        if (!pipeProperty.isInsert() && !toTransfer.isEmpty() && (pipeProperty.isExtract() ||
            container.extract(toTransfer.resource(), toTransfer.amount(), true) > 0)) {
            sources.put(pos, direction);
        } else if (pipeProperty.isNormal() || pipeProperty.isInsert()) {
            consumers.put(pos, direction);
        }
    }

    @Override
    public void moveContents(long transferRate, @NotNull BlockEntity source, @NotNull BlockEntity consumer, Direction direction) {
        if (!(FluidApi.BLOCK.isPresent(source, direction))) return;
        var sourceContainer = FluidApi.BLOCK.find(source, direction);
        if (sourceContainer == null) return;
        if (!(FluidApi.BLOCK.isPresent(consumer, direction))) return;
        var consumerContainer = FluidApi.BLOCK.find(consumer, direction.getOpposite());
        if (consumerContainer == null) return;
        for (int i = 0; i < sourceContainer.size(); i++) {
            var fluid = sourceContainer.getContents(i);
            if (fluid.isEmpty()) continue;
            var toTransfer = new ResourceStack<>(fluid.resource(), Math.min(transferRate, fluid.amount()));
            if (toTransfer.isEmpty()) continue;
            try {
                TransferUtil.move(sourceContainer, consumerContainer, toTransfer.resource(), toTransfer.amount(), true);
                TransferUtil.move(sourceContainer, consumerContainer, toTransfer.resource(), toTransfer.amount(), false);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    @Override
    public boolean isValid(@NotNull BlockEntity entity, Direction direction) {
        return FluidApi.BLOCK.isPresent(entity, direction.getOpposite());
    }
}
