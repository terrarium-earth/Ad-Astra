package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.blocks.machines.BatteryBlock;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.BatteryMenu;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class BatteryBlockEntity extends ContainerMachineBlockEntity {

    public BatteryBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 5);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new BatteryMenu(id, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage() {
        if (energyContainer != null) return energyContainer;
        return energyContainer = new WrappedBlockEnergyContainer(
            this,
            new SimpleEnergyContainer(2_000_000) {
                @Override
                public long maxInsert() {
                    return 5_000;
                }

                @Override
                public long maxExtract() {
                    return 5_000;
                }

                @Override
                public void setEnergy(long energy) {
                    super.setEnergy(energy);
                    onEnergyChange();
                }
            });
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (!canFunction()) return;
        tickSideInteractions(pos, Predicate.not(Direction.UP::equals));
        distributeToChargeSlots();
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter) {
        TransferUtils.pushItemsNearby(this, pos, new int[]{1, 2, 3, 4}, getSideConfig().get(0), filter);
        TransferUtils.pullItemsNearby(this, pos, new int[]{1, 2, 3, 4}, getSideConfig().get(0), filter);
        TransferUtils.pushEnergyNearby(this, pos, getEnergyStorage().maxExtract(), getSideConfig().get(1), filter);
        TransferUtils.pullEnergyNearby(this, pos, getEnergyStorage().maxInsert(), getSideConfig().get(1), filter);
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return List.of(
            new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_SLOTS),
            new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.PUSH_PULL, ConstantComponents.SIDE_CONFIG_ENERGY)
        );
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{1, 2, 3, 4};
    }

    public void onEnergyChange() {
        if (level().getGameTime() % 10 != 0) return;
        int charge = Math.round(getEnergyStorage().getStoredEnergy() / (float) getEnergyStorage().getMaxCapacity() * 4);
        level().setBlock(getBlockPos(), getBlockState().setValue(BatteryBlock.CHARGE, charge), Block.UPDATE_CLIENTS);
    }

    public void distributeToChargeSlots() {
        int filledSlots = 0;
        for (int i = 0; i < 4; i++) {
            if (EnergyApi.isEnergyItem(getItem(i + 1))) {
                filledSlots++;
            }
        }
        if (filledSlots == 0) return;

        for (int i = 0; i < 4; i++) {
            ItemStack stack = getItem(i + 1);
            if (stack.isEmpty()) continue;
            if (!EnergyApi.isEnergyItem(stack)) continue;
            ItemStackHolder holder = new ItemStackHolder(stack);
            EnergyApi.moveEnergy(this, null, holder, getEnergyStorage().maxExtract() / filledSlots, false);
            if (holder.isDirty()) {
                setItem(i + 1, holder.getStack());
            }
        }
    }
}
