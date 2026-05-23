package earth.terrarium.adastra.common.blockentities.machines;

import earth.terrarium.adastra.common.blockentities.base.EnergyContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.config.MachineConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.menus.machines.CoalGeneratorMenu;
import earth.terrarium.adastra.common.registry.ModDataManagers;
import earth.terrarium.adastra.common.utils.PlatformUtils;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.common_storage_lib.energy.impl.SimpleValueStorage;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class CoalGeneratorBlockEntity extends EnergyContainerMachineBlockEntity {

    public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(
        new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS),
        new ConfigurationEntry(ConfigurationType.ENERGY, Configuration.PUSH, ConstantComponents.SIDE_CONFIG_ENERGY)
    );

    protected int cookTime;
    protected int cookTimeTotal;

    public CoalGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 2);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new CoalGeneratorMenu(id, inventory, this);
    }

    @Override
    public ValueStorage getEnergy(Direction direction) {
        return new SimpleValueStorage(this, ModDataManagers.VALUE_CONTENT, MachineConfig.IRON.energyCapacity);
    }

    @Override
    public long maxInsertExtract() {
        return MachineConfig.IRON.maxEnergyInOut;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        cookTime = tag.getInt("CookTime");
        cookTimeTotal = tag.getInt("CookTimeTotal");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("CookTime", cookTime);
        tag.putInt("CookTimeTotal", cookTimeTotal);
    }

    @Override
    public ChargeSlotType getChargeSlotType() {
        return ChargeSlotType.POWER_ITEM;
    }

    @Override
    public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        if (!canFunction()) {
            if (time % 10 == 0) setLit(false);
            return;
        }
        var input = getItem(1);
        if (getEnergyStorage().insert(MachineConfig.coalGeneratorEnergyGenerationPerTick, true) == 0) {
            if (time % 10 == 0) setLit(false);
            return;
        }

        if (cookTime > 0) {
            cookTime--;
            getEnergyStorage().insert(MachineConfig.coalGeneratorEnergyGenerationPerTick, false);
            if (time % 10 == 0) setLit(true);
        } else if (!input.isEmpty()
            && !(input.getItem() instanceof BucketItem)) {
            int burnTime = Math.min(20_000, PlatformUtils.getBurnTime(input));
            if (burnTime > 0) {
                input.shrink(1);
                cookTimeTotal = burnTime;
                cookTime = burnTime;
            }
        } else {
            if (time % 10 == 0) setLit(false);
        }
    }

    @Override
    public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig) {
        TransferUtils.pushItemsNearby(this, pos, new int[]{1}, sideConfig.get(0), filter);
        TransferUtils.pullItemsNearby(this, pos, new int[]{1}, sideConfig.get(0), filter);
        TransferUtils.pushEnergyNearby(this, pos, maxInsertExtract(), sideConfig.get(1), filter);
    }

    @Override
    public List<ConfigurationEntry> getDefaultConfig() {
        return SIDE_CONFIG;
    }

    public int cookTime() {
        return cookTime;
    }

    public int cookTimeTotal() {
        return cookTimeTotal;
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[]{1};
    }
}