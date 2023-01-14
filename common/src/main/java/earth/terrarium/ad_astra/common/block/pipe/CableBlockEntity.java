package earth.terrarium.ad_astra.common.block.pipe;

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

        BlockState state = this.getBlockState();
        BlockState state2 = level.getBlockState(pos);
        PipeState pipeState = state.getValue(PipeBlock.DIRECTIONS.get(this.getSource().direction()));
        PipeState pipeState2 = state2.getValue(PipeBlock.DIRECTIONS.get(direction));

        if (!(state.getBlock() instanceof PipeBlock) || !(state2.getBlock() instanceof PipeBlock)) {
            return;
        }


        if ((pipeState == PipeState.NORMAL && pipeState2 == PipeState.NORMAL) || (pipeState == PipeState.NONE && pipeState2 == PipeState.NONE)) {
            if (state.getValue(PipeBlock.DIRECTIONS.get(this.getSource().direction())) == PipeState.NONE) {
                return;
            }

            if (state2.getValue(PipeBlock.DIRECTIONS.get(direction)) == PipeState.NONE) {
                return;
            }

            if (getSource().storage() != null && getConsumers().size() > 0) {
                EnergyHooks.moveEnergy(getSource().storage(), consumer, Math.max(0, this.getTransferAmount() / getConsumers().size()));
            }
        } else {
            PlatformEnergyManager input;
            PlatformEnergyManager output;

            if (pipeState == PipeState.INSERT && pipeState2 == PipeState.INSERT) {
                return;
            } else if (pipeState == PipeState.EXTRACT && pipeState2 == PipeState.EXTRACT) {
                return;
            } else if (pipeState == PipeState.NONE || pipeState2 == PipeState.NONE) {
                return;
            } else if (pipeState2 == PipeState.INSERT || pipeState == PipeState.EXTRACT) {
                input = source.storage();
                output = consumer;
            } else if (pipeState2 == PipeState.EXTRACT || pipeState == PipeState.INSERT) {
                input = consumer;
                output = source.storage();
            } else {
                return;
            }

            if (getSource() != null && getConsumers().size() > 0) {
                EnergyHooks.moveEnergy(input, output, Math.max(0, this.getTransferAmount() / getConsumers().size()));
            }
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
        this.source = null;
    }

    @Override
    public List<Node<PlatformEnergyManager>> getConsumers() {
        return this.consumers;
    }

    @Override
    public int getWorkTime() {
        return 5;
    }

    @Override
    public Level getPipelevel() {
        return this.level;
    }

    @Override
    public long getTransferAmount() {
        if (this.getBlockState().getBlock() instanceof PipeBlock cableBlock) {
            return cableBlock.getTransferRate();
        }
        return 0;
    }

    @Override
    public BlockPos getPipePos() {
        return this.getBlockPos();
    }
}