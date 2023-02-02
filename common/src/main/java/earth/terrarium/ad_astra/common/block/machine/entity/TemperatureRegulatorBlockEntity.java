package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.ContainerMachineBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.ad_astra.common.screen.machine.TemperatureRegulatorMenu;
import earth.terrarium.ad_astra.common.system.TemperatureSystem;
import earth.terrarium.ad_astra.common.util.FluidUtils;
import earth.terrarium.ad_astra.common.util.algorithm.FloodFiller3D;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.FluidAttachment;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("deprecation")
public class TemperatureRegulatorBlockEntity extends ContainerMachineBlockEntity implements EnergyAttachment.Block, FluidAttachment.Block {
    private WrappedBlockEnergyContainer energyContainer;
    private WrappedBlockFluidContainer fluidContainer;
    private boolean showTemperature;
    private int temperatureTarget;
    private int currentTemperature;
    private final Set<BlockPos> sources = new HashSet<>();

    public TemperatureRegulatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.TEMPERATURE_REGULATOR.get(), blockPos, blockState, 2);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        showTemperature = tag.getBoolean("ShowTemperature");
        temperatureTarget = tag.getInt("TemperatureTarget");
        currentTemperature = tag.getInt("CurrentTemperature");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("ShowTemperature", showTemperature);
        tag.putInt("TemperatureTarget", temperatureTarget);
        tag.putInt("CurrentTemperature", currentTemperature);
    }

    @Override
    public void serverTick() {
        if (level == null) return;
        if (temperatureTarget == 0) {
            temperatureTarget = TemperatureSystem.TARGET_TEMPERATURE;
            currentTemperature = TemperatureSystem.getLevelTemperature(level, getBlockPos());
        }
        if (level.getGameTime() % 60 == 0) {
            int energyCost = 1000; // TODO: Calculate energy and fluid costs
            long hydrogenCost = FluidHooks.buckets(0.01f);

            if (getEnergyStorage().internalExtract(energyCost, true) >= energyCost) {
                if (getFluidContainer().extractFluid(FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(0).getFluid(), hydrogenCost, null), true).getFluidAmount() > 0) {
                    getEnergyStorage().internalExtract(energyCost, false);

                    getFluidContainer().extractFluid(FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(0).getFluid(), hydrogenCost, null), false);
                    craft();
                } else {
                    clearSources();
                    currentTemperature = TemperatureSystem.getLevelTemperature(level, getBlockPos());
                }
            } else {
                clearSources();
                currentTemperature = TemperatureSystem.getLevelTemperature(level, getBlockPos());
            }
        }
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public int getTemperatureTarget() {
        return temperatureTarget;
    }

    public Set<BlockPos> getSources() {
        return sources;
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new TemperatureRegulatorMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(200000)) : energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public WrappedBlockFluidContainer getFluidContainer(BlockEntity holder) {
        return fluidContainer == null ? fluidContainer = new WrappedBlockFluidContainer(this, new SimpleFluidContainer(i -> FluidHooks.buckets(10f), 1, (tank, fluid) -> fluid.getFluid().is(ModTags.Fluids.HYDROGEN))) : fluidContainer;
    }

    public WrappedBlockFluidContainer getFluidContainer() {
        return getFluidContainer(this);
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
        TemperatureSystem.removeTemperatureSource(level, sources);
        TemperatureSystem.TEMPERATURE_REGULATOR_BLOCKS.remove(getBlockPos());
        sources.clear();
    }

    private void craft() {
        if (level == null) return;
        update();

        if (currentTemperature > temperatureTarget) {
            currentTemperature = Math.max(currentTemperature - 10, temperatureTarget);
        } else if (currentTemperature < temperatureTarget) {
            currentTemperature = Math.min(currentTemperature + 10, temperatureTarget);
        }
        clearSources();
        TemperatureSystem.TEMPERATURE_REGULATOR_BLOCKS.add(getBlockPos());
        if (!TemperatureSystem.isSafeTemperature(currentTemperature)) return;

        Set<BlockPos> positions = FloodFiller3D.run(level, getBlockPos());
        TemperatureSystem.addTemperatureSource(level, positions);
        sources.addAll(positions);
    }

    @Override
    public void updateFluidSlots() {
        FluidUtils.insertItemFluidToTank(this, this, 0, 1, f -> f.is(ModTags.Fluids.HYDROGEN)); // TODO: Use common tag
        FluidUtils.extractTankFluidToItem(this, this, 0, 1, 0, f -> true);
    }
}
