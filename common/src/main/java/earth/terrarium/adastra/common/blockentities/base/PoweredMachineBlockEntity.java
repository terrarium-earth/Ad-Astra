package earth.terrarium.adastra.common.blockentities.base;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.adastra.common.utils.EnergyUtils;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public abstract class PoweredMachineBlockEntity extends ContainerMachineBlockEntity implements BotariumEnergyBlock<WrappedBlockEnergyContainer> {

    protected WrappedBlockEnergyContainer energyContainer;
    private long lastEnergy;
    private long energyDifference;

    public PoweredMachineBlockEntity(BlockPos pos, BlockState state, int containerSize) {
        super(pos, state, containerSize);
    }

    public void extractBatterySlot() {
        ItemStack stack = this.getItem(0);
        if (stack.isEmpty()) return;
        if (!EnergyApi.isEnergyItem(stack)) return;
        ItemStackHolder holder = new ItemStackHolder(stack);
        EnergyApi.moveEnergy(holder, this, null, energyContainer.maxInsert(), false);
        if (holder.isDirty()) {
            this.setItem(0, holder.getStack());
        }
    }

    public void distributeNearby() {
        distributeNearby(pair -> true);
    }

    public void distributeNearby(Predicate<Pair<Direction, BlockEntity>> directionFilter) {
        EnergyUtils.distributeEnergyNearby(this, getEnergyStorage().maxExtract(), directionFilter);
    }

    @Override
    public void clientTick(ClientLevel level, long time, BlockState state, BlockPos pos) {
        var energy = this.getEnergyStorage();
        this.energyDifference = energy.getStoredEnergy() - this.lastEnergy;
        this.lastEnergy = energy.getStoredEnergy();
    }

    public long energyDifference() {
        return this.energyDifference;
    }
}
