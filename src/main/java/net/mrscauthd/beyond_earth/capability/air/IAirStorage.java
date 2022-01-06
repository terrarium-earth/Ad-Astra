package net.mrscauthd.beyond_earth.capability.air;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IAirStorage extends INBTSerializable<CompoundTag> {

	int receiveAir(int maxReceive, boolean simulate);

	int extractAir(int maxExtract, boolean simulate);

	int getAirStored();

	void setAirStored(int air);

	int getMaxAirStored();
}
