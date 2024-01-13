package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.EnergyContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.blocks.machines.EnergizerBlock;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.utils.ModUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class EnergizerBlockEntity extends EnergyContainerMachineBlockEntity {
    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.PUSH_PULL, ConstantComponents.SIDE_CONFIG_ENERGY)
    );

    public EnergizerBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return null;
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity entity, @Nullable Direction direction) {
        if (energyContainer != null) return energyContainer;
        return energyContainer = new WrappedBlockEnergyContainer(
            this,
            new SimpleEnergyContainer(MachineConfig.energizerEnergyCapacity, MachineConfig.ostrumTierMaxEnergyInOut, MachineConfig.ostrumTierMaxEnergyInOut) {
                @Override
                public void setEnergy(long energy) {
                    super.setEnergy(energy);
                    if (level().getGameTime() % 10 != 0) return;
                    onEnergyChange();
                }
            });
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (!canFunction()) return;
        distributeToChargeSlot(level, pos);
        if (time % 10 == 0) setLit(!getItem(0).isEmpty());
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig) {
        TransferUtils.pushEnergyNearby(this, pos, getEnergyStorage().maxExtract(), sideConfig.get(0), filter);
        TransferUtils.pullEnergyNearby(this, pos, getEnergyStorage().maxInsert(), sideConfig.get(0), filter);
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
        int charge = Math.round(getEnergyStorage().getStoredEnergy() / (float) getEnergyStorage().getMaxCapacity() * 5);
        level().setBlock(getBlockPos(), getBlockState().setValue(EnergizerBlock.POWER, charge), Block.UPDATE_CLIENTS);
    }

    public void distributeToChargeSlot(ServerLevel level, BlockPos pos) {
        var stack = getItem(0);
        if (stack.isEmpty()) return;
        if (!EnergyContainer.holdsEnergy(stack)) return;
        ItemStackHolder holder = new ItemStackHolder(stack);
        if (EnergyApi.moveEnergy(this, null, holder, getEnergyStorage().maxExtract(), false) == 0) return;
        setItem(0, holder.getStack());
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