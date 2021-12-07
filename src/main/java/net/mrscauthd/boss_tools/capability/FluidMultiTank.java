package net.mrscauthd.boss_tools.capability;

import java.util.Collections;
import java.util.List;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FluidMultiTank implements IFluidHandler {

	private final List<FluidTank> tanks;

	public FluidMultiTank(List<FluidTank> tanks) {
		this.tanks = Collections.unmodifiableList(tanks);
	}

	public FluidTank getTank(int tank) {
		return this.getTankList().get(tank);
	}

	public List<FluidTank> getTankList() {
		return this.tanks;
	}

	@Override
	public int getTanks() {
		return this.getTankList().size();
	}

	@Override
	public FluidStack getFluidInTank(int tank) {
		return this.getTank(tank).getFluid();
	}

	@Override
	public int getTankCapacity(int tank) {
		return this.getTank(tank).getCapacity();
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		return this.getTank(tank).isFluidValid(stack);
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		resource = resource.copy();
		FluidStack filled = resource.copy();
		filled.setAmount(0);

		for (FluidTank tank : this.getTankList()) {
			int amount = tank.fill(resource, action);
			filled.grow(amount);
			resource.shrink(amount);
		}

		return filled.getAmount();
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		resource = resource.copy();
		FluidStack drained = resource.copy();
		drained.setAmount(0);

		for (FluidTank tank : this.getTankList()) {
			int amount = tank.drain(resource, action).getAmount();
			drained.grow(amount);
			resource.shrink(amount);
		}

		return drained;
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		FluidStack drained = null;

		for (FluidTank tank : this.getTankList()) {
			if (drained == null) {
				FluidStack amount = tank.drain(maxDrain, action);

				if (!amount.isEmpty()) {
					drained = amount;
					maxDrain -= amount.getAmount();
				}
			} else {
				FluidStack test = drained.copy();
				test.setAmount(maxDrain);

				int amount = tank.drain(test, action).getAmount();
				drained.grow(amount);
				maxDrain -= amount;
			}

		}

		return drained;
	}

}
