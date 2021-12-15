package net.mrscauthd.astrocraft.compat.mekanism;


public class GasHandlerOxygenAdapter/* implements IOxygenStorage */{

	/*
	private IGasHandler gasHandler;
	private boolean canExtract;
	private boolean canReceive;

	public GasHandlerOxygenAdapter(IGasHandler gasHandler) {
		this(gasHandler, true, true);
	}

	public GasHandlerOxygenAdapter(IGasHandler gasHandler, boolean canExtract, boolean canReceive) {
		this.gasHandler = gasHandler;
		this.canExtract = canExtract;
		this.canReceive = canReceive;
	}

	public Gas getGas() {
		return MekanismGases.OXYGEN.get();
	}

	protected GasStack createGasStack(int amount) {
		return new GasStack(this.getGas(), amount);
	}

	@Override
	public int receiveOxygen(int maxReceive, boolean simulate) {
		if (!this.isCanReceive()) {
			return 0;
		}

		GasStack toInsert = this.createGasStack(maxReceive);
		GasStack remain = this.getGasHandler().insertChemical(toInsert, Action.get(!simulate));

		if (remain.isEmpty()) {
			return maxReceive;
		} else {
			return (int) (maxReceive - remain.getAmount());
		}

	}

	@Override
	public int extractOxygen(int maxExtract, boolean simulate) {
		if (!this.isCanExtract()) {
			return 0;
		}

		return (int) this.getGasHandler().extractChemical(maxExtract, Action.get(!simulate)).getAmount();
	}

	@Override
	public int getOxygenStored() {
		long stored = 0;
		IGasHandler gasHandler = this.getGasHandler();
		int tanks = gasHandler.getTanks();

		for (int i = 0; i < tanks; i++) {
			GasStack stack = gasHandler.getChemicalInTank(i);

			if (stack.getType() == this.getGas()) {
				stored = Math.max(stored + Math.max(stack.getAmount(), Integer.MAX_VALUE), Integer.MAX_VALUE);
			}
		}

		return (int) stored;
	}

	@Override
	public void setOxygenStored(int oxygen) {
		IGasHandler gasHandler = this.getGasHandler();
		int tanks = gasHandler.getTanks();

		for (int i = 0; i < tanks; i++) {
			GasStack stack = gasHandler.getChemicalInTank(i);

			if (stack.getType() == this.getGas()) {
				gasHandler.setChemicalInTank(i, GasStack.EMPTY);
			}
		}

		gasHandler.insertChemical(this.createGasStack(tanks), Action.EXECUTE);
	}

	@Override
	public int getMaxOxygenStored() {
		long capacity = 0;
		IGasHandler gasHandler = this.getGasHandler();
		int tanks = gasHandler.getTanks();

		for (int i = 0; i < tanks; i++) {
			if (gasHandler.isValid(i, this.createGasStack(1))) {
				capacity = Math.max(capacity + Math.max(gasHandler.getTankCapacity(i), Integer.MAX_VALUE), Integer.MAX_VALUE);
			}
		}

		return (int) capacity;
	}

	public IGasHandler getGasHandler() {
		return this.gasHandler;
	}

	public boolean isCanExtract() {
		return this.canExtract;
	}

	public boolean isCanReceive() {
		return this.canReceive;
	}
*/
}