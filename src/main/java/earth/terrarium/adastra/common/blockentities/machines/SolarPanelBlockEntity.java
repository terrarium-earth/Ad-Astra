package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.common.blockentities.base.EnergyContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.SolarPanelMenu;
import earth.terrarium.adastra.common.registry.ModDataManagers;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext;
import earth.terrarium.common_storage_lib.energy.EnergyApi;
import earth.terrarium.common_storage_lib.energy.impl.SimpleValueStorage;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import earth.terrarium.common_storage_lib.storage.util.TransferUtil;
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

    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.PUSH, ConstantComponents.SIDE_CONFIG_ENERGY)
    );
    private final SimpleValueStorage energy = new SimpleValueStorage(this, ModDataManagers.VALUE_CONTENT, MachineConfig.DESH.energyCapacity);

    public SolarPanelBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 1);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SolarPanelMenu(id, inventory, this);
    }

    @Override
    public ValueStorage getEnergy(Direction direction) {
        return energy;
    }

    @Override
    public long maxInsertExtract() {
        return MachineConfig.DESH.maxEnergyInOut;
    }

    @Override
    public ChargeSlotType getChargeSlotType() {
        return ChargeSlotType.POWER_ITEM;
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (canFunction()) {
            distributeToChargeSlots();
            if (isDay()) generateEnergy(PlanetApi.API.getSolarPower(level));
        }
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig) {
        TransferUtils.pushEnergyNearby(this, pos, maxInsertExtract(), sideConfig.get(0), filter);
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return SIDE_CONFIG;
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
        this.getEnergyStorage().insert(generationRate, false);
    }

    public void distributeToChargeSlots() {
        ItemStack stack = getItem(0);
        if (stack.isEmpty()) return;
        ModifyOnlyContext itemContext = new ModifyOnlyContext(stack);
        if (!itemContext.isPresent(EnergyApi.ITEM)) return;
        var container = itemContext.find(EnergyApi.ITEM);
        if (container.getStoredAmount() <= 0) return;
        TransferUtil.moveValue(getEnergyStorage(), container, maxInsertExtract(), false);
//        if (holder.isDirty()) {
//            setItem(0, holder.getStack());
//        }
    }
}
