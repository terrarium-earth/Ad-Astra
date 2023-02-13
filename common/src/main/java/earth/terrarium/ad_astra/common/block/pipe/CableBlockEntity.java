package earth.terrarium.ad_astra.common.block.pipe;

import earth.terrarium.ad_astra.common.block.machine.BasicContainer;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.botarium.common.energy.base.PlatformEnergyManager;
import earth.terrarium.botarium.common.energy.util.EnergyHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CableBlockEntity extends BlockEntity implements InteractablePipe<PlatformEnergyManager> {
    private final List<Node<PlatformEnergyManager>> consumers = new ArrayList<>();
    private Node<PlatformEnergyManager> source;

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.CABLE.get(), pos, state);
    }

    @Override
    public boolean supportsAutoExtract() {
        return true;
    }

    @Override
    public boolean canConnectTo(BlockEntity next, Direction direction, BlockPos pos) {
        return true;
    }

    @Override
    public void insertInto(PlatformEnergyManager consumer, Direction direction, BlockPos pos) {
        if (level == null) return;
        BlockState state = getBlockState();
        BlockState state2 = level.getBlockState(pos);
        if (state.isAir() || state2.isAir()) return;


        PlatformEnergyManager input = getSource().storage();
        PlatformEnergyManager output = consumer;

        if (!(state.getBlock() instanceof PipeDuctBlock) && !(state2.getBlock() instanceof PipeDuctBlock)) {
            PipeState pipeState = state.getValue(PipeBlock.DIRECTIONS.get(getSource().direction()));
            PipeState pipeState2 = state2.getValue(PipeBlock.DIRECTIONS.get(direction));

            if (getSource().storage() == null || getConsumers().isEmpty()) return;
            if (pipeState == PipeState.INSERT && pipeState2 == PipeState.INSERT) return;
            if (pipeState == PipeState.EXTRACT && pipeState2 == PipeState.EXTRACT) return;
            if (pipeState == PipeState.NONE || pipeState2 == PipeState.NONE) return;

            if (pipeState2 == PipeState.EXTRACT || pipeState == PipeState.INSERT) {
                input = consumer;
                output = getSource().storage();
            }
        }

        EnergyHooks.moveEnergy(input, output, Math.max(0, getTransferAmount() / getConsumers().size()));
        if (level.getBlockEntity(pos.relative(direction)) instanceof BasicContainer container) {
            container.update();
        }
    }

    @Override
    public PlatformEnergyManager getInteraction(Level level, BlockPos pos, Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity != null) {
            return EnergyHooks.safeGetBlockEnergyManager(blockEntity, direction).orElse(null);
        }
        return null;
    }

    @Override
    public Node<PlatformEnergyManager> getSource() {
        return source;
    }

    @Override
    public void setSource(Node<PlatformEnergyManager> source) {
        this.source = source;
    }

    @Override
    public void clearSource() {
        source = null;
    }

    @Override
    public List<Node<PlatformEnergyManager>> getConsumers() {
        return consumers;
    }

    @Override
    public int getWorkTime() {
        return 5;
    }

    @Override
    public Level getPipelevel() {
        return level;
    }

    @Override
    public long getTransferAmount() {
        if (getBlockState().getBlock() instanceof Pipe pipe) {
            return pipe.getTransferRate();
        }
        return 0;
    }

    @Override
    public BlockPos getPipePos() {
        return getBlockPos();
    }
}
