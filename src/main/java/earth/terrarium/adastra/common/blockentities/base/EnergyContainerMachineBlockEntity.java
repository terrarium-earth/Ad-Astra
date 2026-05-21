package earth.terrarium.adastra.common.blockentities.base;

import earth.terrarium.adastra.common.utils.EnergyUtils;
import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext;
import earth.terrarium.common_storage_lib.energy.EnergyApi;
import earth.terrarium.common_storage_lib.energy.EnergyProvider;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class EnergyContainerMachineBlockEntity extends ContainerMachineBlockEntity implements EnergyProvider.BlockEntity {

    public EnergyContainerMachineBlockEntity(BlockPos pos, BlockState state, int containerSize) {
        super(pos, state, containerSize);
    }

    public ChargeSlotType getChargeSlotType() {
        return ChargeSlotType.POWER_MACHINE;
    }

    @Override
    public void internalServerTick(ServerLevel level, long time, BlockState state, BlockPos pos) {
        super.internalServerTick(level, time, state, pos);
        switch (getChargeSlotType()) {
            case POWER_ITEM -> insertBatterySlot();
            case POWER_MACHINE -> extractBatterySlot();
        }
    }

    public ValueStorage getEnergyStorage() {
        return getEnergy(null);
    }

    public long maxInsertExtract() {
        return 250;
    }

    public void extractBatterySlot() {
        ItemStack stack = this.getItem(0);
        if (stack.isEmpty()) return;
        var container = new ModifyOnlyContext(stack).find(EnergyApi.ITEM);
        if (container.getStoredAmount() <= 0) return;
        EnergyUtils.moveEnergy(getEnergyStorage(), getEnergyStorage(), maxInsertExtract(), false);
//        if (holder.isDirty()) {
//            this.setItem(0, holder.getStack());
//        }
    }

    public void insertBatterySlot() {
        ItemStack stack = this.getItem(0);
        if (stack.isEmpty()) return;
        var container = new ModifyOnlyContext(stack).find(EnergyApi.ITEM);
        if (container.getStoredAmount() <= 0) return;
        EnergyUtils.moveEnergy(getEnergyStorage(), container, maxInsertExtract(), false);
//        if (holder.isDirty()) {
//            this.setItem(0, holder.getStack());
//        }
    }

    public enum ChargeSlotType {
        NONE,
        POWER_MACHINE,
        POWER_ITEM
    }
}
