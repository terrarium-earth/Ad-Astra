package earth.terrarium.ad_astra.blocks.pipes;

import java.util.ArrayList;
import java.util.List;

import earth.terrarium.ad_astra.registry.ModBlockEntities;

import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.PlatformEnergyManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

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
    public boolean canTakeFrom(PlatformEnergyManager source) {
        return source.extract(1, true) == 1;
    }

    @Override
    public boolean canInsertInto(PlatformEnergyManager consumer) {
        return consumer.insert(1, true) == 1;
    }

    @Override
    public boolean canConnectTo(BlockEntity next, Direction direction, BlockPos pos) {
        return true;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void insertInto(PlatformEnergyManager consumer, Direction direction, BlockPos pos) {
        BlockState state = this.getCachedState();
        BlockState state2 = world.getBlockState(pos);

        if (!(state.getBlock() instanceof CableBlock) || !(state2.getBlock() instanceof CableBlock)) {
            return;
        }

        if (!state.get(CableBlock.DIRECTIONS.get(this.getSource().direction()))) {
            return;
        }

        if (!state2.get(CableBlock.DIRECTIONS.get(direction))) {
            return;
        }

        if (getSource().storage() != null && getConsumers().size() > 0) {
            EnergyHooks.moveEnergy(getSource().storage(), consumer, Math.max(0, this.getTransferAmount() / getConsumers().size()));
        }

    }

    @Override
    public PlatformEnergyManager getInteraction(World world, BlockPos pos, Direction direction) {
        BlockEntity blockEntity = world.getBlockEntity(pos.offset(direction));
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
    public World getPipeWorld() {
        return this.world;
    }

    @Override
    public long getTransferAmount() {
        if (this.getCachedState().getBlock() instanceof CableBlock cableBlock) {
            return cableBlock.getTransferRate();
        }
        return 0;
    }

    @Override
    public BlockPos getPipePos() {
        return this.getPos();
    }
}