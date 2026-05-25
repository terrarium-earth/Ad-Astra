package earth.terrarium.adastra.common.blockentities.pipes;

import earth.terrarium.adastra.common.blocks.properties.PipeProperty;
import earth.terrarium.common_storage_lib.energy.EnergyApi;
import earth.terrarium.common_storage_lib.storage.util.TransferUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CableBlockEntity extends PipeBlockEntity {

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public void addNode(@NotNull BlockEntity entity, PipeProperty pipeProperty, Direction direction, BlockPos pos) {
        if (pipeProperty.isNone()) return;

        var container = EnergyApi.BLOCK.find(entity, direction);
        if (container == null) return;

        if (!pipeProperty.isInsert() && (pipeProperty.isExtract() || container.extract(container.getStoredAmount(), true) > 0)) {
            sources.put(pos, direction);
        } else if (pipeProperty.isNormal() || pipeProperty.isInsert()) {
            consumers.put(pos, direction);
        }
    }

    @Override
    public void moveContents(long transferRate, @NotNull BlockEntity source, @NotNull BlockEntity consumer, Direction direction) {
        var sourceContainer = EnergyApi.BLOCK.find(source, direction);
        if (sourceContainer == null) return;
        var consumerContainer = EnergyApi.BLOCK.find(consumer, direction.getOpposite());
        if (consumerContainer == null) return;
        TransferUtil.moveValue(sourceContainer, consumerContainer, Math.min(transferRate, sourceContainer.getStoredAmount()), false);
    }

    @Override
    public boolean isValid(@NotNull BlockEntity entity, Direction direction) {
        return EnergyApi.BLOCK.isPresent(entity, direction.getOpposite());
    }
}
