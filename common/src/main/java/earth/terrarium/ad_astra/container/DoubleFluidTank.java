package earth.terrarium.ad_astra.container;

import earth.terrarium.botarium.api.Updatable;
import earth.terrarium.botarium.api.fluid.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.function.Predicate;

public class DoubleFluidTank implements UpdatingFluidContainer<BlockEntity> {
    private final SimpleUpdatingFluidContainer input;
    private final SimpleUpdatingFluidContainer output;

    public DoubleFluidTank(Updatable updatable, long inputCapacity, long outputCapacity, Predicate<FluidHolder> inputFilter, Predicate<FluidHolder> outputFilter) {
        this.input = new SimpleUpdatingFluidContainer(updatable, i -> inputCapacity, 1, (i, f) -> inputFilter.test(f));
        this.output = new SimpleUpdatingFluidContainer(updatable, i -> outputCapacity, 1, (i, f) -> outputFilter.test(f));
    }

    protected DoubleFluidTank(SimpleUpdatingFluidContainer input, SimpleUpdatingFluidContainer output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public long insertFluid(FluidHolder fluid, boolean simulate) {
        if (fluid.isEmpty()) return 0;
        return input.insertFluid(fluid, simulate);
    }

    @Override
    public FluidHolder extractFluid(FluidHolder fluid, boolean simulate) {
        if (fluid.isEmpty()) return FluidHooks.emptyFluid();
        return output.extractFluid(fluid, simulate);
    }

    @Override
    public void setFluid(int slot, FluidHolder fluid) {
    }

    @Override
    public List<FluidHolder> getFluids() {
        return List.of(input.getFluids().get(0), output.getFluids().get(0));
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return input.isEmpty() && output.isEmpty();
    }

    @Override
    public FluidContainer copy() {
        return new DoubleFluidTank(input.copy(), output.copy());
    }

    @Override
    public long getTankCapacity(int tankSlot) {
        return tankSlot == 0 ? input.getTankCapacity(0) : output.getTankCapacity(0);
    }

    @Override
    public void fromContainer(FluidContainer container) {
        if (container instanceof DoubleFluidTank doubleFluidTank) {
            input.fromContainer(doubleFluidTank.input);
            output.fromContainer(doubleFluidTank.output);
        }
    }

    @Override
    public long extractFromSlot(FluidHolder fluidHolder, FluidHolder toInsert, Runnable snapshot) {
        return output.extractFromSlot(fluidHolder, toInsert, snapshot);
    }

    @Override
    public boolean allowsInsertion() {
        return true;
    }

    @Override
    public boolean allowsExtraction() {
        return true;
    }

    @Override
    public FluidSnapshot createSnapshot() {
        return new DoubleTankSnapshot(input.createSnapshot(), output.createSnapshot());
    }

    @Override
    public void deserialize(CompoundTag nbtCompound) {
        input.deserialize(nbtCompound.getCompound("Input"));
        output.deserialize(nbtCompound.getCompound("Output"));
    }

    @Override
    public CompoundTag serialize(CompoundTag nbtCompound) {
        nbtCompound.put("Input", input.serialize(new CompoundTag()));
        nbtCompound.put("Output", output.serialize(new CompoundTag()));
        return nbtCompound;
    }

    public FluidContainer getInput() {
        return input;
    }

    public FluidContainer getOutput() {
        return output;
    }

    @Override
    public void update(BlockEntity updatable) {
    }

    private record DoubleTankSnapshot(FluidSnapshot input, FluidSnapshot output) implements FluidSnapshot {

        @Override
        public void loadSnapshot(FluidContainer container) {
            if (container instanceof DoubleFluidTank doubleFluidTank) {
                input.loadSnapshot(doubleFluidTank.input);
                output.loadSnapshot(doubleFluidTank.output);
            }
        }
    }
}
