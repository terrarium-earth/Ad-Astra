package earth.terrarium.adastra.common.blockentities.pipes;

import earth.terrarium.adastra.common.blockentities.base.TickableBlockEntity;
import earth.terrarium.adastra.common.blocks.base.BasicEntityBlock;
import earth.terrarium.adastra.common.blocks.pipes.PipeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.IdentityHashMap;
import java.util.Map;

public abstract class PipeBlockEntity extends BlockEntity implements TickableBlockEntity, Pipe {
    protected final Map<BlockPos, Direction> sources = new IdentityHashMap<>();
    protected final Map<BlockPos, Direction> consumers = new IdentityHashMap<>();
    private final long transferRate;

    private Direction[] connectedDirections;
    private boolean initialized;
    private boolean isController;

    public PipeBlockEntity(BlockPos pos, BlockState state) {
        super(((BasicEntityBlock) state.getBlock()).entity(state), pos, state);
        PipeBlock pipe = ((PipeBlock) state.getBlock());
        this.transferRate = pipe.transferRate();
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        // Only run if it's a controller i.e. at least one side is connected to something
        if (isController) {
            if (time % 30 == 0) {
                sources.clear();
                consumers.clear();
                findNodes(level, pos);
            }
            if (!consumers.isEmpty() && !sources.isEmpty()) {
                transfer(level, transferRate, sources, consumers);
            }
        }
    }

    public void pipeChanged(Level level, BlockPos pos) {
        Direction[] directions = PipeBlock.getConnectedDirections(level.getBlockState(pos));
        connectedDirections = directions;
        for (var direction : directions) {
            if (!(level.getBlockState(pos.relative(direction)).getBlock() instanceof PipeBlock)) {
                isController = true;
                return;
            }
        }
        isController = false;
    }

    @Override
    public void firstTick(Level level, BlockPos pos, BlockState state) {
        this.initialized = true;
        pipeChanged(level, pos);
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    public Direction[] connectedDirections() {
        return connectedDirections;
    }
}
