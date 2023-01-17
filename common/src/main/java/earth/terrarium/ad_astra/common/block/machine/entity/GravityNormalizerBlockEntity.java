package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.ContainerMachineBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.screen.machine.GravityNormalizerMenu;
import earth.terrarium.ad_astra.common.system.GravitySystem;
import earth.terrarium.ad_astra.common.util.algorithm.FloodFiller3D;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Set;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GravityNormalizerBlockEntity extends ContainerMachineBlockEntity implements EnergyAttachment.Block {
    private WrappedBlockEnergyContainer energyContainer;
    private boolean showGravity = false;
    private float gravityTarget;
    private float currentGravity;
    private final Set<BlockPos> sources = new HashSet<>();

    public GravityNormalizerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.GRAVITY_NORMALIZER.get(), blockPos, blockState, 0);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.showGravity = tag.getBoolean("ShowGravity");
        this.gravityTarget = tag.getFloat("GravityTarget");
        this.currentGravity = tag.getFloat("CurrentGravity");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("ShowGravity", this.showGravity);
        tag.putFloat("GravityTarget", this.gravityTarget);
        tag.putFloat("CurrentGravity", this.currentGravity);
    }

    @Override
    public void serverTick() {
        if (level == null) return;
        if (gravityTarget == 0) {
            gravityTarget = GravitySystem.DEFAULT_GRAVITY;
            currentGravity = GravitySystem.getLevelGravity(this.level);
        }
        if (level.getGameTime() % 40 == 0) {
            int energyCost = 100; // TODO: Calculate energy costs

            if (this.getEnergyStorage().internalExtract(energyCost, true) >= energyCost) {
                this.getEnergyStorage().internalExtract(energyCost, false);
                this.craft();
            } else {
                this.clearSources();
                this.currentGravity = GravitySystem.getLevelGravity(level);
            }
        }
    }

    public float getCurrentGravity() {
        return this.currentGravity;
    }

    public float getGravityTarget() {
        return this.gravityTarget;
    }

    public Set<BlockPos> getSources() {
        return this.sources;
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new GravityNormalizerMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(200000)) : this.energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public void update() {
        this.updateFluidSlots();
    }

    @Override
    public void onDestroy() {
        this.clearSources();
    }

    public void clearSources() {
        if (level == null) return;
        if (level.isClientSide) return;
        GravitySystem.removeGravitySource(level, sources);
        GravitySystem.GRAVITY_NORMALIZER_BLOCKS.remove(this.getBlockPos());
        sources.clear();
    }

    private void craft() {
        if (level == null) return;
        update();

        if (this.currentGravity > this.gravityTarget) {
            this.currentGravity = Math.max(this.currentGravity - 0.5f, this.gravityTarget);
        } else if (this.currentGravity < this.gravityTarget) {
            this.currentGravity = Math.min(this.currentGravity + 0.5f, this.gravityTarget);
        }
        clearSources();
        GravitySystem.GRAVITY_NORMALIZER_BLOCKS.add(this.getBlockPos());
        Set<BlockPos> positions = FloodFiller3D.run(level, getBlockPos().above());
        GravitySystem.addGravitySource(level, positions, this.currentGravity);
        sources.addAll(positions);
    }
}
