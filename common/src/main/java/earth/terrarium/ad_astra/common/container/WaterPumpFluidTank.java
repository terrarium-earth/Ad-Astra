package earth.terrarium.ad_astra.common.container;

import earth.terrarium.ad_astra.common.config.WaterPumpConfig;
import earth.terrarium.botarium.api.Updatable;
import earth.terrarium.botarium.api.fluid.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.Objects;

public class WaterPumpFluidTank implements UpdatingFluidContainer<BlockEntity> {
    private FluidHolder tank;
    private final Updatable updatable;

    public WaterPumpFluidTank(FluidHolder tank, Updatable updatable) {
        this.tank = tank;
        this.updatable = updatable;
    }

    public WaterPumpFluidTank(Updatable updatable) {
        this(FluidHooks.emptyFluid(), updatable);
    }

    @Override
    public long insertFluid(FluidHolder fluid, boolean simulate) {
        return 0;
    }

    public long insertInternal(FluidHolder fluid, boolean simulate) {
        if (fluid.isEmpty()) return 0;
        if (tank.isEmpty()) {
            CompoundTag compound = fluid.getCompound() != null ? fluid.getCompound().copy() : null;
            FluidHolder fluidHolder = FluidHooks.newFluidHolder(fluid.getFluid(), Math.min(this.getTankCapacity(0), fluid.getFluidAmount()), compound);
            if (!simulate) tank = fluidHolder;
            return fluidHolder.getFluidAmount();
        }
        if (!tank.getFluid().equals(fluid.getFluid())) return 0;
        long amount = Math.min(this.getTankCapacity(0) - tank.getFluidAmount(), fluid.getFluidAmount());
        if (!simulate) tank.setAmount(tank.getFluidAmount() + amount);
        return amount;
    }


    @Override
    public FluidHolder extractFluid(FluidHolder fluid, boolean simulate) {
        if (tank.isEmpty()) return FluidHooks.emptyFluid();
        if (fluid.isEmpty()) return FluidHooks.emptyFluid();
        if (!fluid.getFluid().equals(Fluids.WATER)) return FluidHooks.emptyFluid();
        if (!Objects.equals(fluid.getCompound(), tank.getCompound())) return FluidHooks.emptyFluid();
        long amount = Math.min(tank.getFluidAmount(), fluid.getFluidAmount());
        if (!simulate) tank.setAmount(tank.getFluidAmount() - amount);
        CompoundTag compound = fluid.getCompound() != null ? fluid.getCompound().copy() : null;
        return FluidHooks.newFluidHolder(fluid.getFluid(), amount, compound);
    }

    @Override
    public void setFluid(int slot, FluidHolder fluid) {
        if (slot == 0 && fluid.getFluid().equals(Fluids.WATER)) {
            tank = fluid.copyHolder();
        }
    }

    @Override
    public List<FluidHolder> getFluids() {
        return List.of(tank);
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.tank.isEmpty();
    }

    @Override
    public FluidContainer copy() {
        return new WaterPumpFluidTank(tank.copyHolder(), updatable);
    }

    @Override
    public long getTankCapacity(int tankSlot) {
        return WaterPumpConfig.tankSize;
    }

    @Override
    public void fromContainer(FluidContainer container) {
        this.tank = container.getFluids().get(0).copyHolder();
    }

    @Override
    public long extractFromSlot(FluidHolder fluidHolder, FluidHolder toInsert, Runnable snapshot) {
        if (Objects.equals(fluidHolder.getCompound(), toInsert.getCompound()) && fluidHolder.getFluid().isSame(toInsert.getFluid())) {
            long amount = Mth.clamp(toInsert.getFluidAmount(), 0, fluidHolder.getFluidAmount());
            snapshot.run();
            fluidHolder.setAmount(fluidHolder.getFluidAmount() - amount);
            if (fluidHolder.getFluidAmount() == 0) {
                fluidHolder.setFluid(Fluids.EMPTY);
            }
            return amount;
        }
        return 0;
    }

    @Override
    public boolean allowsInsertion() {
        return false;
    }

    @Override
    public boolean allowsExtraction() {
        return true;
    }

    @Override
    public FluidSnapshot createSnapshot() {
        return new WaterPumpSnapshot(this.tank);
    }

    @Override
    public void deserialize(CompoundTag nbtCompound) {
        this.tank = FluidHooks.fluidFromCompound(nbtCompound.getCompound("Tank"));
    }

    @Override
    public CompoundTag serialize(CompoundTag nbtCompound) {
        nbtCompound.put("Tank", this.tank.serialize());
        return nbtCompound;
    }

    @Override
    public void update(BlockEntity block) {
        updatable.update();
    }

    private static class WaterPumpSnapshot implements FluidSnapshot {
        private final FluidHolder tank;

        public WaterPumpSnapshot(FluidHolder tank) {
            this.tank = tank.copyHolder();
        }

        @Override
        public void loadSnapshot(FluidContainer container) {
            container.setFluid(0, tank);
        }
    }
}
