package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.client.AdAstraClient;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.common.blockentities.base.EnergyContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.GravityNormalizerMenu;
import earth.terrarium.adastra.common.registry.ModSoundEvents;
import earth.terrarium.adastra.common.utils.EnergyUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.adastra.common.utils.floodfill.FloodFill3D;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class GravityNormalizerBlockEntity extends EnergyContainerMachineBlockEntity {
    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY)
    );

    private final Set<BlockPos> lastDistributedBlocks = new HashSet<>();
    private long energyPerTick;
    private int distributedBlocksCount;
    private int shutDownTicks;
    private int limit = MachineConfig.maxDistributionBlocks;
    private float targetGravity = 1;

    private float animation;
    private float lastAnimation;

    public GravityNormalizerBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
    }


    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        lastDistributedBlocks.clear();
        for (var pos : tag.getLongArray("LastDistributedBlocks")) {
            lastDistributedBlocks.add(BlockPos.of(pos));
        }
        energyPerTick = tag.getLong("EnergyPerTick");
        distributedBlocksCount = tag.getInt("DistributedBlocksCount");
        limit = tag.getInt("Limit");
        targetGravity = tag.getFloat("TargetGravity");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLongArray("LastDistributedBlocks", lastDistributedBlocks.stream().mapToLong(BlockPos::asLong).toArray());
        tag.putLong("EnergyPerTick", energyPerTick);
        tag.putInt("DistributedBlocksCount", distributedBlocksCount);
        tag.putInt("Limit", limit);
        tag.putFloat("TargetGravity", targetGravity);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new GravityNormalizerMenu(id, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity entity, @Nullable Direction direction) {
        if (this.energyContainer != null) return this.energyContainer;
        return this.energyContainer = new WrappedBlockEnergyContainer(
            this,
            EnergyUtils.machineInsertOnlyEnergy(MachineConfig.DESH)
        );
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        super.serverTick(level, time, state, pos);
        if (shutDownTicks > 0) {
            shutDownTicks--;
            return;
        }

        boolean canDistribute = canCraftDistribution();
        if (canFunction() && canDistribute) {
            getEnergyStorage().internalExtract(calculateEnergyPerTick(), false);
            setLit(true);

            if (time % MachineConfig.distributionRefreshRate == 0) tickGravity(level, pos);

            if (time % 200 == 0) {
                level.playSound(null, pos, ModSoundEvents.GRAVITY_NORMALIZER_IDLE.get(), SoundSource.BLOCKS, 0.3f, 1);
            }
        } else if (!lastDistributedBlocks.isEmpty()) {
            clearGravityBlocks();
            shutDownTicks = 60;
            setLit(false);
        } else if (time % 10 == 0) setLit(false);

        energyPerTick = canDistribute ? calculateEnergyPerTick() : 0;
        distributedBlocksCount = canDistribute ? lastDistributedBlocks.size() : 0;
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig) {
        TransferUtils.pullEnergyNearby(this, pos, getEnergyStorage().maxInsert(), sideConfig.get(0), filter);
    }

    @Override
    public void onRemoved() {
        clearGravityBlocks();
    }

    private boolean canCraftDistribution() {
        long energy = calculateEnergyPerTick();
        return getEnergyStorage().internalExtract(energy, true) >= energy;
    }

    protected void tickGravity(ServerLevel level, BlockPos pos) {
        limit = MachineConfig.maxDistributionBlocks;
        Set<BlockPos> positions = FloodFill3D.run(level, pos.above(), limit, FloodFill3D.TEST_FULL_SEAL, false);

        GravityApi.API.setGravity(level, positions, targetGravity);
        this.resetLastDistributedBlocks(positions);
    }

    protected void resetLastDistributedBlocks(Set<BlockPos> positions) {
        lastDistributedBlocks.removeAll(positions);
        clearGravityBlocks();
        lastDistributedBlocks.addAll(positions);
    }

    protected void clearGravityBlocks() {
        GravityApi.API.removeGravity(level, lastDistributedBlocks);
        lastDistributedBlocks.clear();
    }

    @Override
    public void clientTick(ClientLevel level, long time, BlockState state, BlockPos pos) {
        if (time % 40 == 0) {
            if (AdAstraConfigClient.showGravityNormalizerArea) {
                AdAstraClient.GRAVITY_OVERLAY_RENDERER.removePositions(pos);
                if (AdAstraClient.GRAVITY_OVERLAY_RENDERER.canAdd(pos)
                    && canFunction()
                    && canCraftDistribution()) {
                    AdAstraClient.GRAVITY_OVERLAY_RENDERER.addPositions(pos, lastDistributedBlocks);
                }
            } else AdAstraClient.GRAVITY_OVERLAY_RENDERER.clearPositions();
        }

        lastAnimation = animation;
        if (isLit()) {
            animation += 10;
        }
    }

    public int distributedBlocksCount() {
        return canFunction() ? distributedBlocksCount : 0;
    }

    public int distributedBlocksLimit() {
        return limit;
    }

    public long energyPerTick() {
        return canFunction() ? energyPerTick : 0;
    }

    public float targetGravity() {
        return targetGravity;
    }

    public void setTargetGravity(float targetGravity) {
        this.targetGravity = targetGravity;
    }

    public float animation() {
        return animation;
    }

    public float lastAnimation() {
        return lastAnimation;
    }

    private long calculateEnergyPerTick() {
        return Math.max(1, lastDistributedBlocks.size() / 24);
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return SIDE_CONFIG;
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{};
    }
}
