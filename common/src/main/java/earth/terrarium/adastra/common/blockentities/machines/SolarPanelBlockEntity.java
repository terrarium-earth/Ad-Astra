package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.common.blockentities.base.EnergyContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.SolarPanelMenu;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.impl.ExtractOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class SolarPanelBlockEntity extends EnergyContainerMachineBlockEntity {
    public SolarPanelBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SolarPanelMenu(id, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage() {
        if (energyContainer != null) return energyContainer;
        return energyContainer = new WrappedBlockEnergyContainer(
            this,
            new ExtractOnlyEnergyContainer(20_000) {
                @Override
                public long maxExtract() {
                    return 2_000;
                }
            });
    }

    @Override
    public ChargeSlotType getChargeSlotType() {
        return ChargeSlotType.POWER_ITEM;
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (canFunction()) {
            tickSideInteractions(pos, d -> true);
            distributeToChargeSlots();
            if (isDay()) generateEnergy(PlanetApi.API.getSolarPower(level));
        }
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter) {
        TransferUtils.pushEnergyNearby(this, pos, getEnergyStorage().maxExtract(), getSideConfig().get(0), filter);
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return List.of(
            new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.PUSH, ConstantComponents.SIDE_CONFIG_ENERGY)
        );
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{};
    }

    public boolean isDay() {
        if (this.level().getDayTime() % 24000 > 12000) return false;
        return this.level().canSeeSky(getBlockPos().above());
    }

    public void generateEnergy(long generationRate) {
        this.energyContainer.internalInsert(generationRate, false);
    }

    public void distributeToChargeSlots() {
        ItemStack stack = getItem(0);
        if (stack.isEmpty()) return;
        if (!EnergyApi.isEnergyItem(stack)) return;
        ItemStackHolder holder = new ItemStackHolder(stack);
        EnergyApi.moveEnergy(this, null, holder, getEnergyStorage().maxExtract(), false);
        if (holder.isDirty()) {
            setItem(0, holder.getStack());
        }
    }
}
