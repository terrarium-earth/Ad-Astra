package net.mrscauthd.boss_tools.gauge;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

public class GaugeValueFluidStack implements IGaugeValue {

	private FluidStack stack;
	private int capacity;

	public GaugeValueFluidStack() {

	}

	public GaugeValueFluidStack(FluidStack stack, int capacity) {
		this.setStack(stack);
		this.setCapacity(capacity);
	}

	@Override
	public void deserializeNBT(CompoundTag compound) {
		this.setStack(FluidStack.loadFluidStackFromNBT(compound.getCompound("stack")));
		this.setCapacity(compound.getInt("capacity"));
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag compound = new CompoundTag();
		CompoundTag stackCompound = new CompoundTag();
		this.getStack().writeToNBT(stackCompound);
		compound.put("stack", stackCompound);
		compound.putInt("capacity", this.getCapacity());

		return compound;
	}

	@Override
	public int getColor() {
		FluidStack fluidStack = this.getStack();
		FluidAttributes attributes = fluidStack.getFluid().getAttributes();
		return attributes.getColor(fluidStack);
	}

	public FluidStack getStack() {
		return this.stack;
	}

	public void setStack(FluidStack stack) {
		this.stack = stack;
	}

	@Override
	public Component getDisplayName() {
		return this.getStack().getDisplayName();
	}

	@Override
	public String getUnit() {
		return GaugeValueHelper.FLUID_UNIT;
	}

	@Override
	public int getAmount() {
		return this.getStack().getAmount();
	}

	@Override
	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public boolean isReverse() {
		return false;
	}

}
