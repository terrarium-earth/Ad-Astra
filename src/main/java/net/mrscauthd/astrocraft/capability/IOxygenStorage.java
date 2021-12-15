package net.mrscauthd.astrocraft.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IOxygenStorage extends INBTSerializable<CompoundTag> {

	int receiveOxygen(int maxReceive, boolean simulate);

	int extractOxygen(int maxExtract, boolean simulate);

	int getOxygenStored();

	void setOxygenStored(int oxygen);

	int getMaxOxygenStored();

	public default double getOxygenStoredRatio() {
		return (double) this.getOxygenStored() / (double) this.getMaxOxygenStored();
	}
}
