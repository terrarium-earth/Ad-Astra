package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.client.renderers.world.OxygenDistributorOverlayRenderer;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.container.BiFluidContainer;
import earth.terrarium.adastra.common.entities.AirVortex;
import earth.terrarium.adastra.common.menus.machines.OxygenDistributorMenu;
import earth.terrarium.adastra.common.registry.ModSoundEvents;
import earth.terrarium.adastra.common.utils.FluidUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.adastra.common.utils.floodfill.FloodFill3D;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class OxygenDistributorBlockEntity extends OxygenLoaderBlockEntity {
    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY),
        new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_FLUID),
        new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID)
    );

    private final Set<BlockPos> lastDistributedBlocks = new HashSet<>();
    private long energyPerTick;
    private float fluidPerTick;
    private int distributedBlocksCount;
    private double accumulatedFluid;
    private int shutDownTicks;
    private int limit;

    public OxygenDistributorBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3);
    }


    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        lastDistributedBlocks.clear();
        for (var pos : tag.getLongArray("LastDistributedBlocks")) {
            lastDistributedBlocks.add(BlockPos.of(pos));
        }
        energyPerTick = tag.getLong("EnergyPerTick");
        fluidPerTick = tag.getFloat("FluidPerTick");
        distributedBlocksCount = tag.getInt("DistributedBlocksCount");
        accumulatedFluid = tag.getDouble("AccumulatedFluid");
        limit = tag.getInt("Limit");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLongArray("LastDistributedBlocks", lastDistributedBlocks.stream().mapToLong(BlockPos::asLong).toArray());
        tag.putLong("EnergyPerTick", energyPerTick);
        tag.putFloat("FluidPerTick", fluidPerTick);
        tag.putInt("DistributedBlocksCount", distributedBlocksCount);
        tag.putDouble("AccumulatedFluid", accumulatedFluid);
        tag.putInt("Limit", limit);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new OxygenDistributorMenu(id, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage() {
        if (this.energyContainer != null) return this.energyContainer;
        return this.energyContainer = new WrappedBlockEnergyContainer(
            this,
            new InsertOnlyEnergyContainer(50_000) {
                @Override
                public long maxInsert() {
                    return 1_000;
                }
            });
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        super.serverTick(level, time, state, pos);
        if (shutDownTicks > 0) {
            shutDownTicks--;
            return;
        }

        float fluidPerTick = calculateFluidPerTick();
        boolean canDistribute = canCraftDistribution(FluidHooks.buckets(Math.max(0.001, fluidPerTick / 1000)));
        if (canFunction() && canDistribute) {
            getEnergyStorage().internalExtract(calculateEnergyPerTick(), false);

            accumulatedFluid += fluidPerTick;
            int wholeBuckets = (int) accumulatedFluid;
            if (wholeBuckets > 0) {
                consumeDistribution(FluidHooks.buckets(Math.max(0.001, wholeBuckets / 1000f)));
                accumulatedFluid -= wholeBuckets;
            }

            if (time % 100 == 0) tickOxygen(level, pos);

            if (time % 200 == 0) {
                level.playSound(null, pos, ModSoundEvents.OXYGEN_OUTTAKE.get(), SoundSource.BLOCKS, 0.2f, 1);
            } else if (time % 100 == 0) {
                level.playSound(null, pos, ModSoundEvents.OXYGEN_INTAKE.get(), SoundSource.BLOCKS, 0.2f, 1);
            }
        } else if (!lastDistributedBlocks.isEmpty()) {
            clearOxygenBlocks();
            shutDownTicks = 60;
        }

        energyPerTick = (recipe != null && canCraft(getEnergyStorage()) ? recipe.energy() : 0) + (canDistribute ? calculateEnergyPerTick() : 0);
        this.fluidPerTick = canDistribute ? fluidPerTick : 0;
        distributedBlocksCount = canDistribute ? lastDistributedBlocks.size() : 0;
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter) {
        TransferUtils.pullItemsNearby(this, pos, new int[]{1}, getSideConfig().get(0), filter);
        TransferUtils.pushItemsNearby(this, pos, new int[]{2}, getSideConfig().get(1), filter);
        TransferUtils.pullEnergyNearby(this, pos, getEnergyStorage().maxInsert(), getSideConfig().get(2), filter);
        TransferUtils.pullFluidNearby(this, pos, getFluidContainer(), FluidHooks.buckets(0.2f), 0, getSideConfig().get(3), filter);
        TransferUtils.pushFluidNearby(this, pos, getFluidContainer(), FluidHooks.buckets(0.2f), 1, getSideConfig().get(4), filter);
    }

    @Override
    public void onRemoved() {
        clearOxygenBlocks();
    }

    private boolean canCraftDistribution(long fluidAmount) {
        long energy = calculateEnergyPerTick();
        if (getEnergyStorage().internalExtract(energy, true) < energy) return false;
        FluidHolder toExtract = FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(1).getFluid(), fluidAmount, null);
        return ((BiFluidContainer) getFluidContainer().container()).output().internalExtract(toExtract, true).getFluidAmount() >= fluidAmount;
    }

    protected void consumeDistribution(long fluidAmount) {
        FluidHolder toExtract = FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(1).getFluid(), fluidAmount, null);
        ((BiFluidContainer) getFluidContainer().container()).output().internalExtract(toExtract, false);
    }

    protected void tickOxygen(ServerLevel level, BlockPos pos) {
        limit = AdAstraConfig.maxDistributionBlocks;
        Set<BlockPos> positions = FloodFill3D.run(level, pos.above(), limit, FloodFill3D.TEST_FULL_SEAL, true);

        OxygenApi.API.setOxygen(level, positions, true);
        TemperatureApi.API.setTemperature(level, positions, PlanetConstants.COMFY_EARTH_TEMPERATURE); // TODO: move to Temperature Regulator machine

        Set<BlockPos> lastPositionsCopy = new HashSet<>(lastDistributedBlocks);
        this.resetLastDistributedBlocks(positions);

        if (positions.size() < limit) return;
        if (lastPositionsCopy.size() >= limit) return;
        if (OxygenApi.API.hasOxygen(level)) return;

        positions.removeAll(lastPositionsCopy);
        BlockPos target = positions.stream()
            .skip(1)
            .findFirst()
            .orElse(positions.stream().findFirst().orElse(null));
        if (target == null) return;
        AirVortex vortex = new AirVortex(level, pos, lastPositionsCopy);
        vortex.setPos(Vec3.atCenterOf(target));
        level.addFreshEntity(vortex);
    }

    protected void resetLastDistributedBlocks(Set<BlockPos> positions) {
        lastDistributedBlocks.removeAll(positions);
        clearOxygenBlocks();
        lastDistributedBlocks.addAll(positions);
    }

    protected void clearOxygenBlocks() {
        OxygenApi.API.removeOxygen(level, lastDistributedBlocks);
        TemperatureApi.API.removeTemperature(level, lastDistributedBlocks); // TODO: move to Temperature Regulator machine
        lastDistributedBlocks.clear();
    }

    @Override
    public void updateSlots() {
        FluidUtils.moveItemToContainer(this, getFluidContainer(), 1, 2, 0);
        sync();
    }

    @Override
    public void clientTick(ClientLevel level, long time, BlockState state, BlockPos pos) {
        if (time % 40 == 0) {
            if (AdAstraConfigClient.showOxygenDistributorArea) {
                OxygenDistributorOverlayRenderer.removePositions(pos);
                if (OxygenDistributorOverlayRenderer.canAdd(pos)
                    && canFunction()
                    && canCraftDistribution(FluidHooks.buckets(Math.max(0.001, calculateFluidPerTick() / 1000)))) {
                    OxygenDistributorOverlayRenderer.addPositions(pos, lastDistributedBlocks);
                }
            } else OxygenDistributorOverlayRenderer.clearPositions();
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

    public float fluidPerTick() {
        return canFunction() ? fluidPerTick : 0;
    }

    private long calculateEnergyPerTick() {
        return Math.max(1, lastDistributedBlocks.size() / 50);
    }

    private float calculateFluidPerTick() {
        return Math.max(1, lastDistributedBlocks.size() / 1500f);
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return SIDE_CONFIG;
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{1, 2};
    }
}
