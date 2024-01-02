package earth.terrarium.adastra.common.blockentities.pipes;

import earth.terrarium.adastra.common.blocks.properties.PipeProperty;
import earth.terrarium.botarium.common.energy.EnergyApi;
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
        var container = EnergyApi.getBlockEnergyContainer(entity, direction);
        if (container == null) return;

        if (!pipeProperty.isInsert() && (pipeProperty.isExtract() || container.extractEnergy(container.getStoredEnergy(), true) > 0)) {
            sources.put(pos, direction);
        } else if (pipeProperty.isNormal() || pipeProperty.isInsert()) {
            consumers.put(pos, direction);
        }
    }

    @Override
    public void moveContents(long transferRate, @NotNull BlockEntity source, @NotNull BlockEntity consumer, Direction direction) {
        var sourceContainer = EnergyApi.getBlockEnergyContainer(source, direction);
        if (sourceContainer == null) return;
        var consumerContainer = EnergyApi.getBlockEnergyContainer(consumer, direction);
        if (consumerContainer == null) return;
        EnergyApi.moveEnergy(sourceContainer, consumerContainer, Math.min(transferRate, sourceContainer.getStoredEnergy()), false);
    }

    @Override
    public boolean isValid(@NotNull BlockEntity entity, Direction direction) {
        return EnergyApi.isEnergyBlock(entity, direction.getOpposite());
    }
}
