package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.ContainerMachineBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.screen.machine.GravityNormalizerMenu;
import earth.terrarium.ad_astra.common.system.GravitySystem;
import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.ad_astra.common.util.algorithm.FloodFiller3D;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Set;

public class GravityNormalizerBlockEntity extends ContainerMachineBlockEntity implements EnergyAttachment.Block {
    private WrappedBlockEnergyContainer energyContainer;
    private boolean showGravity;
    private float gravityTarget;
    private float currentGravity;
    private final Set<BlockPos> sources = new HashSet<>();

    public GravityNormalizerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.GRAVITY_NORMALIZER.get(), blockPos, blockState, 0);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        showGravity = tag.getBoolean("ShowGravity");
        gravityTarget = tag.getFloat("GravityTarget");
        currentGravity = tag.getFloat("CurrentGravity");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("ShowGravity", showGravity);
        tag.putFloat("GravityTarget", gravityTarget);
        tag.putFloat("CurrentGravity", currentGravity);
    }

    @Override
    public void serverTick() {
        if (level == null) return;
        if (gravityTarget == 0) {
            gravityTarget = GravitySystem.DEFAULT_GRAVITY;
            currentGravity = GravitySystem.getLevelGravity(level);
        }
        if (level.getGameTime() % 60 == 0) {
            int energyCost = 100; // TODO: Calculate energy costs

            if (getEnergyStorage().internalExtract(energyCost, true) >= energyCost) {
                getEnergyStorage().internalExtract(energyCost, false);
                craft();
            } else {
                clearSources();
                currentGravity = GravitySystem.getLevelGravity(level);
            }
        }
    }

    public float getCurrentGravity() {
        return currentGravity;
    }

    public float getGravityTarget() {
        return gravityTarget;
    }

    public Set<BlockPos> getSources() {
        return sources;
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new GravityNormalizerMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(200000)) : energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public void update() {
        updateFluidSlots();
    }

    @Override
    public void onDestroy() {
        clearSources();
    }

    public void clearSources() {
        if (level == null) return;
        if (level.isClientSide) return;
        GravitySystem.removeGravitySource(level, sources);
        GravitySystem.GRAVITY_NORMALIZER_BLOCKS.remove(getBlockPos());
        sources.clear();
    }

    private void craft() {
        if (level == null) return;
        update();

        if (currentGravity > gravityTarget) {
            currentGravity = Math.max(currentGravity - 0.5f, gravityTarget);
        } else if (currentGravity < gravityTarget) {
            currentGravity = Math.min(currentGravity + 0.5f, gravityTarget);
        }
        clearSources();
        GravitySystem.GRAVITY_NORMALIZER_BLOCKS.add(getBlockPos());
        Set<BlockPos> positions = FloodFiller3D.run(level, getBlockPos());
        GravitySystem.addGravitySource(level, positions, currentGravity);
        sources.addAll(positions);
    }
}
