package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.EnergyContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.WaterPumpMenu;
import earth.terrarium.adastra.common.registry.ModDataManagers;
import earth.terrarium.adastra.common.registry.ModParticleTypes;
import earth.terrarium.adastra.common.utils.ModUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.common_storage_lib.energy.EnergyApi;
import earth.terrarium.common_storage_lib.energy.impl.SimpleValueStorage;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.fluid.impl.SimpleFluidStorage;
import earth.terrarium.common_storage_lib.fluid.util.FluidProvider;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class WaterPumpBlockEntity extends EnergyContainerMachineBlockEntity implements FluidProvider.Block {

    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.NONE, ConstantComponents.SIDE_CONFIG_ENERGY),
        new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID)
    );

    public WaterPumpBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new WaterPumpMenu(id, inventory, this);
    }

    @Override
    public ValueStorage getEnergy(Direction direction) {
        return new SimpleValueStorage(this, ModDataManagers.VALUE_CONTENT, MachineConfig.DESH.energyCapacity);
    }

    @Override
    public long maxInsertExtract() {
        return MachineConfig.DESH.maxEnergyInOut;
    }

    @Override
    public CommonStorage<FluidResource> getFluids(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, Direction direction) {
        return new SimpleFluidStorage(this, ModDataManagers.FLUID_CONTENTS, 1, FluidAmounts.toPlatformAmount(MachineConfig.DESH.fluidCapacity))
            .filter(0, f -> f.is(FluidTags.WATER));
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (!canFunction()) return;
        var energyContainer = EnergyApi.BLOCK.find(level, pos, null);
        if (canPump(pos, energyContainer)) pump(level, energyContainer);
    }

    private CommonStorage<FluidResource> getFluidContainer() {
        return FluidApi.BLOCK.find(this, null);
    }

    private boolean canPump(BlockPos pos, ValueStorage energyStorage) {
        if (!level().getFluidState(pos.below()).is(Fluids.WATER)) return false;
        if (energyStorage.extract(MachineConfig.waterPumpEnergyPerTick, true) < MachineConfig.waterPumpEnergyPerTick)
            return false;
        var fluidContainer = getFluidContainer();
        return fluidContainer != null && fluidContainer.getAmount(0) < fluidContainer.getLimit(0, FluidResource.BLANK);
    }

    private void pump(ServerLevel level, ValueStorage energyStorage) {
        energyStorage.extract(MachineConfig.waterPumpEnergyPerTick, false);
        var fluidContainer = getFluidContainer();
        fluidContainer.insert(FluidResource.of(Fluids.WATER), FluidAmounts.toPlatformAmount(MachineConfig.waterPumpFluidGenerationPerTick), false);
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
        TransferUtils.pullEnergyNearby(this, pos, maxInsertExtract(), sideConfig.get(0), filter);
        TransferUtils.pushFluidNearby(this, pos, getFluidContainer(), FluidAmounts.toPlatformAmount(MachineConfig.waterPumpFluidGenerationPerTick), 0, sideConfig.get(1), filter);
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
