package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.EnergyContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.blocks.machines.EnergizerBlock;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.registry.ModDataManagers;
import earth.terrarium.adastra.common.utils.ModUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext;
import earth.terrarium.common_storage_lib.energy.EnergyApi;
import earth.terrarium.common_storage_lib.energy.impl.SimpleValueStorage;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import earth.terrarium.common_storage_lib.storage.util.TransferUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class EnergizerBlockEntity extends EnergyContainerMachineBlockEntity {

    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.PUSH_PULL, ConstantComponents.SIDE_CONFIG_ENERGY)
    );
    private final SimpleValueStorage energy = new SimpleValueStorage(this, ModDataManagers.VALUE_CONTENT, MachineConfig.energizerEnergyCapacity) {
        @Override
        public void set(long l) {
            super.set(l);
            if (level().getGameTime() % 10 != 0) return;
            onEnergyChange();
        }
    };

    public EnergizerBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return null;
    }

    @Override
    public ValueStorage getEnergy(Direction direction) {
        return energy;
    }

    @Override
    public long maxInsertExtract() {
        return MachineConfig.OSTRUM.maxEnergyInOut;
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (!canFunction()) return;
        distributeToChargeSlot(level, pos);
        if (time % 10 == 0) setLit(!getItem(0).isEmpty());
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig) {
        TransferUtils.pushEnergyNearby(this, pos, maxInsertExtract(), sideConfig.get(0), filter);
        TransferUtils.pullEnergyNearby(this, pos, maxInsertExtract(), sideConfig.get(0), filter);
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return SIDE_CONFIG;
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{0};
    }

    @Override
    public ChargeSlotType getChargeSlotType() {
        return ChargeSlotType.NONE;
    }

    public void onEnergyChange() {
        int charge = Math.round(getEnergyStorage().getStoredAmount() / (float) getEnergyStorage().getCapacity() * 5);
        level().setBlock(getBlockPos(), getBlockState().setValue(EnergizerBlock.POWER, charge), Block.UPDATE_CLIENTS);
    }

    public void distributeToChargeSlot(ServerLevel level, BlockPos pos) {
        var stack = getItem(0);
        if (stack.isEmpty()) return;
        ModifyOnlyContext itemContext = new ModifyOnlyContext(stack);
        if (!itemContext.isPresent(EnergyApi.ITEM)) return;
        var container = itemContext.find(EnergyApi.ITEM);
        if (container.getStoredAmount() >= container.getCapacity()) return;
        if (TransferUtil.moveValue(getEnergyStorage(), container, maxInsertExtract(), false) == 0) return;
        setItem(0, itemContext.stack());
        ModUtils.sendParticles(level,
            ParticleTypes.ELECTRIC_SPARK,
            pos.getX() + 0.5,
            pos.getY() + 1.8,
            pos.getZ() + 0.5,
            2,
            0.1, 0.1, 0.1,
            0.1);
    }
}