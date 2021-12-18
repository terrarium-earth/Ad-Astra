package net.mrscauthd.beyond_earth.capability;

import javax.annotation.Nonnull;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidHandlerWrapper implements IFluidHandler {
	private final IFluidHandler parent;
	private final boolean canDrain;
	private final boolean canFill;

	public FluidHandlerWrapper(IFluidHandler parent) {
		this(parent, true, true);
	}

	public FluidHandlerWrapper(IFluidHandler parent, boolean canDrain, boolean canFill) {
		this.parent = parent;
		this.canDrain = canDrain;
		this.canFill = canFill;
	}

	public final IFluidHandler getParent() {
		return this.parent;
	}

	public final boolean isCanDrain() {
		return this.canDrain;
	}

	public final boolean isCanFill() {
		return this.canFill;
	}

	@Override
	public int getTanks() {
		return this.getParent().getTanks();
	}

	@Nonnull
	@Override
	public FluidStack getFluidInTank(int var1) {
		return this.getParent().getFluidInTank(var1);
	}

	@Override
	public int getTankCapacity(int var1) {
		return this.getParent().getTankCapacity(var1);
	}

	@Override
	public boolean isFluidValid(int var1, @Nonnull FluidStack var2) {
		return this.getParent().isFluidValid(var1, var2);
	}

	@Override
	public int fill(FluidStack var1, FluidAction var2) {
		if (this.isCanFill()) {
			return this.getParent().fill(var1, var2);
		} else {
			return 0;
		}
	}

	@Nonnull
	@Override
	public FluidStack drain(FluidStack var1, FluidAction var2) {
		if (this.isCanDrain()) {
			return this.getParent().drain(var1, var2);
		} else {
			return FluidStack.EMPTY;
		}
	}

	@Nonnull
	@Override
	public FluidStack drain(int var1, FluidAction var2) {
		if (this.isCanDrain()) {
			return this.getParent().drain(var1, var2);
		} else {
			return FluidStack.EMPTY;
		}
	}

}
