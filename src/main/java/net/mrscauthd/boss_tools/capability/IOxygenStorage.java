package net.mrscauthd.boss_tools.capability;

public interface IOxygenStorage {

	int receiveOxygen(int maxReceive, boolean simulate);

	int extractOxygen(int maxExtract, boolean simulate);

	int getOxygenStored();

	void setOxygenStored(int oxygen);

	int getMaxOxygenStored();

	public default double getOxygenStoredRatio() {
		return (double) this.getOxygenStored() / (double) this.getMaxOxygenStored();
	}
}
