package earth.terrarium.ad_astra.block.pipe;

import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.PlatformEnergyManager;
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
        super(ModBlockEntities.CABLE.get(), pos, state);
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

        if (!(state.getBlock() instanceof CableBlock) || !(state2.getBlock() instanceof CableBlock)) {
            return;
        }

        if (!state.getValue(CableBlock.DIRECTIONS.get(this.getSource().direction()))) {
            return;
        }

        if (!state2.getValue(CableBlock.DIRECTIONS.get(direction))) {
            return;
        }

        if (getSource().storage() != null && getConsumers().size() > 0) {
            EnergyHooks.moveEnergy(getSource().storage(), consumer, Math.max(0, this.getTransferAmount() / getConsumers().size()));
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
        if (this.getBlockState().getBlock() instanceof CableBlock cableBlock) {
            return cableBlock.getTransferRate();
        }
        return 0;
    }

    @Override
    public BlockPos getPipePos() {
        return this.getBlockPos();
    }
}