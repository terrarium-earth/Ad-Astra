package earth.terrarium.adastra.common.container;

import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.FluidSnapshot;
import earth.terrarium.botarium.common.fluid.impl.ExtractOnlyFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.InsertOnlyFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import net.minecraft.nbt.CompoundTag;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public record BiFluidContainer(SimpleFluidContainer input, SimpleFluidContainer output) implements FluidContainer {

    public BiFluidContainer(long capacity, int inputTanks, int outputTanks, BiPredicate<Integer, FluidHolder> inputFilter, BiPredicate<Integer, FluidHolder> outputFilter) {
        this(
            new InsertOnlyFluidContainer(
                i -> capacity,
                inputTanks,
                inputFilter),
            new ExtractOnlyFluidContainer(
                i -> capacity,
                outputTanks,
                outputFilter));
    }

    @Override
    public long insertFluid(FluidHolder fluid, boolean simulate) {
        if (fluid.isEmpty()) return 0;
        return input.insertFluid(fluid, simulate);
    }

    @Override
    public long internalInsert(FluidHolder fluids, boolean simulate) {
        if (fluids.isEmpty()) return 0;
        return output.internalInsert(fluids, simulate);
    }

    @Override
    public FluidHolder extractFluid(FluidHolder fluid, boolean simulate) {
        if (fluid.isEmpty()) return FluidHolder.empty();
        return output.extractFluid(fluid, simulate);
    }

    @Override
    public FluidHolder internalExtract(FluidHolder fluid, boolean simulate) {
        if (fluid.isEmpty()) return FluidHolder.empty();
        return input.internalExtract(fluid, simulate);
    }

    @Override
    public void setFluid(int slot, FluidHolder fluid) {
    }

    @Override
    public List<FluidHolder> getFluids() {
        return Stream.concat(input.getFluids().stream(), output.getFluids().stream()).toList();
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
        return new BiFluidContainer(input.copy(), output.copy());
    }

    @Override
    public long getTankCapacity(int tankSlot) {
        return tankSlot == 0 ? input.getTankCapacity(0) : output.getTankCapacity(0);
    }

    @Override
    public void fromContainer(FluidContainer container) {
        if (!(container instanceof BiFluidContainer tank)) return;
        input.fromContainer(tank.input);
        output.fromContainer(tank.output);
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
        return new BiFluidSnapshot(input.createSnapshot(), output.createSnapshot());
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        input.deserialize(nbt.getCompound("Input"));
        output.deserialize(nbt.getCompound("Output"));
    }

    @Override
    public CompoundTag serialize(CompoundTag nbt) {
        nbt.put("Input", input.serialize(new CompoundTag()));
        nbt.put("Output", output.serialize(new CompoundTag()));
        return nbt;
    }

    @Override
    public void clearContent() {
        input.clearContent();
        output.clearContent();
    }

    @Override
    public void readSnapshot(FluidSnapshot snapshot) {
        if (!(snapshot instanceof BiFluidSnapshot tank)) return;
        input.readSnapshot(tank.input);
        output.readSnapshot(tank.output);
    }

    private record BiFluidSnapshot(FluidSnapshot input, FluidSnapshot output) implements FluidSnapshot {

        @Override
        public void loadSnapshot(FluidContainer container) {
            if (!(container instanceof WrappedBlockFluidContainer wrapped)) return;
            if (!(wrapped.container() instanceof BiFluidContainer tank)) return;
            input.loadSnapshot(tank.input);
            output.loadSnapshot(tank.output);
        }
    }
}
