package earth.terrarium.adastra.common.blockentities;

import earth.terrarium.adastra.common.blocks.pipes.Pipe;
import earth.terrarium.adastra.common.blocks.pipes.PipeBlock;
import earth.terrarium.adastra.common.blocks.properties.PipeProperty;
import earth.terrarium.adastra.common.registry.ModBlockEntityTypes;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class PipeBlockEntity extends BlockEntity {
    private final Map<BlockPos, Direction> sources = new HashMap<>();
    private final Map<BlockPos, Direction> consumers = new HashMap<>();

    private final long transferRate;
    private final Pipe.Type type;

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.PIPE.get(), pos, state);
        PipeBlock pipe = ((PipeBlock) state.getBlock());
        this.transferRate = pipe.transferRate();
        this.type = pipe.type();
    }

    public void serverTick(ServerLevel level, long time, BlockPos pos) {
        if (isController(level, pos)) {
            if (time % 20 == 0) {
                sources.clear();
                consumers.clear();
                findNodes(level, pos);
            }
            transfer(level);
        }
    }

    private boolean isController(ServerLevel level, BlockPos pos) {
        for (var direction : PipeBlock.getConnectedDirections(level.getBlockState(pos))) {
            if (!(level.getBlockState(pos.relative(direction)).getBlock() instanceof PipeBlock)) {
                return true;
            }
        }
        return false;
    }

    private void findNodes(ServerLevel level, BlockPos startPos) {
        Set<BlockPos> visitedNodes = new HashSet<>();
        Queue<BlockPos> queue = new ArrayDeque<>();
        queue.add(startPos);

        while (!queue.isEmpty()) {
            var pipePos = queue.poll();
            BlockState pipeState = level.getBlockState(pipePos);
            for (var direction : PipeBlock.getConnectedDirections(pipeState)) {
                var pos = pipePos.relative(direction);

                if (visitedNodes.contains(pos)) continue;
                visitedNodes.add(pos);

                var pipeProperty = pipeState.getValue(PipeBlock.DIRECTION_TO_CONNECTION.get(direction));
                if (level.getBlockState(pos).getBlock() instanceof Pipe) queue.add(pos);
                else addNode(level.getBlockEntity(pos), pipeProperty, direction, pos);
            }
        }
    }

    private void addNode(BlockEntity entity, PipeProperty pipeProperty, Direction direction, BlockPos pos) {
        if (entity == null) return;
        if (type == Pipe.Type.ENERGY && EnergyApi.isEnergyBlock(entity, direction.getOpposite())) {
            var container = EnergyApi.getBlockEnergyContainer(entity, direction);
            if (container == null) return;
            if (pipeProperty == PipeProperty.EXTRACT || container.extractEnergy(container.getStoredEnergy(), true) > 0) {
                sources.put(pos, direction);
            } else {
                consumers.put(pos, direction);
            }
        } else if (type == Pipe.Type.FLUID && FluidApi.isFluidContainingBlock(entity, direction.getOpposite())) {
            var container = FluidApi.getBlockFluidContainer(entity, direction);
            if (container == null || container.getFluids().isEmpty()) return;

            if (pipeProperty == PipeProperty.EXTRACT || container.extractFluid(container.getFluids().get(0), true).getFluidAmount() > 0) {
                sources.put(pos, direction);
            } else {
                consumers.put(pos, direction);
            }
        }
    }

    private boolean isValid(BlockEntity entity, Direction direction) {
        if (entity == null) return false;
        if (type == Pipe.Type.ENERGY && !EnergyApi.isEnergyBlock(entity, direction.getOpposite())) return false;
        return type != Pipe.Type.FLUID || FluidApi.isFluidContainingBlock(entity, direction.getOpposite());
    }

    private void transfer(ServerLevel level) {
        if (consumers.isEmpty() || sources.isEmpty()) return;

        Set<BlockEntity> sources = new HashSet<>();
        this.sources.forEach((pos, direction) -> {
            var entity = level.getBlockEntity(pos);
            if (isValid(entity, direction)) {
                sources.add(entity);
            }
        });

        sources.forEach(source -> consumers.forEach((pos, direction) -> {
            var entity = level.getBlockEntity(pos);
            if (isValid(entity, direction)) {
                long transferRate = this.transferRate / consumers.size();
                if (type == Pipe.Type.ENERGY) {
                    EnergyApi.moveEnergy(source, entity, transferRate, false);
                } else {
                    var container = FluidApi.getBlockFluidContainer(source, direction);
                    var consumerContainer = FluidApi.getBlockFluidContainer(entity, direction);
                    if (container != null) {
                        for (var fluid : container.getFluids()) {
                            var toTransfer = FluidHooks.newFluidHolder(fluid.getFluid(), transferRate, null);
                            FluidApi.moveFluid(container, consumerContainer, toTransfer, false);
                        }
                    }
                }
            }
        }));
    }
}
