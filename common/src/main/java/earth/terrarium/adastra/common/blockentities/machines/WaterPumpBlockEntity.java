package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.EnergyContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.WaterPumpMenu;
import earth.terrarium.adastra.common.registry.ModParticleTypes;
import earth.terrarium.adastra.common.utils.EnergyUtils;
import earth.terrarium.adastra.common.utils.ModUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.ExtractOnlyFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class WaterPumpBlockEntity extends EnergyContainerMachineBlockEntity implements BotariumFluidBlock<WrappedBlockFluidContainer> {
    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY),
        new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID)
    );

    private WrappedBlockFluidContainer fluidContainer;

    public WaterPumpBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new WaterPumpMenu(id, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage() {
        if (energyContainer != null) return energyContainer;
        return energyContainer = new WrappedBlockEnergyContainer(
            this,
            EnergyUtils.machineInsertOnlyEnergy(MachineConfig.DESH)
        );
    }

    @Override
    public WrappedBlockFluidContainer getFluidContainer() {
        if (fluidContainer != null) return fluidContainer;
        return fluidContainer = new WrappedBlockFluidContainer(
            this,
            new ExtractOnlyFluidContainer(
                i -> FluidConstants.fromMillibuckets(MachineConfig.DESH.fluidCapacity),
                1,
                (tank, holder) -> holder.is(FluidTags.WATER)));
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (!canFunction()) return;
        if (canPump(pos, energyContainer)) pump(level, energyContainer);
    }

    private boolean canPump(BlockPos pos, WrappedBlockEnergyContainer energyStorage) {
        if (!level().getFluidState(pos.below()).is(Fluids.WATER)) return false;
        if (energyStorage.internalExtract(MachineConfig.waterPumpEnergyPerTick, true) < MachineConfig.waterPumpEnergyPerTick)
            return false;
        return fluidContainer.getFirstFluid().getFluidAmount() < fluidContainer.getTankCapacity(0);
    }

    private void pump(ServerLevel level, WrappedBlockEnergyContainer energyStorage) {
        energyStorage.internalExtract(MachineConfig.waterPumpEnergyPerTick, false);
        fluidContainer.internalInsert(FluidHolder.ofMillibuckets(Fluids.WATER, FluidConstants.fromMillibuckets(MachineConfig.waterPumpFluidGenerationPerTick)), false);
        ModUtils.sendParticles(level,
            ModParticleTypes.OXYGEN_BUBBLE.get(),
            getBlockPos().getX() + 0.5,
            getBlockPos().getY() - 0.5,
            getBlockPos().getZ() + 0.5,
            1,
            0.0, 0.0, 0.0,
            0.01);
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig) {
        TransferUtils.pullEnergyNearby(this, pos, getEnergyStorage().maxInsert(), sideConfig.get(0), filter);
        TransferUtils.pushFluidNearby(this, pos, getFluidContainer(), FluidConstants.fromMillibuckets(MachineConfig.waterPumpFluidGenerationPerTick), 0, sideConfig.get(1), filter);
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
