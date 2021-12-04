package net.mrscauthd.boss_tools.capability;

import net.minecraftforge.energy.IEnergyStorage;

public class EnergyStorageExtractaOnly implements IEnergyStorage {

	private IEnergyStorage parent;
	private int maxExtract;

	public EnergyStorageExtractaOnly(IEnergyStorage parent, int maxExtract) {
		this.parent = parent;
		this.maxExtract = maxExtract;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return this.getParent().extractEnergy(maxExtract, simulate);
	}

	public IEnergyStorage getParent() {
		return this.parent;
	}

	@Override
	public int getEnergyStored() {
		return this.getParent().getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored() {
		return this.getParent().getMaxEnergyStored();
	}

	@Override
	public boolean canExtract() {
		return this.getMaxExtract() > 0;
	}

	public int getMaxExtract() {
		return this.maxExtract;
	}

	@Override
	public boolean canReceive() {
		return false;
	}

}
