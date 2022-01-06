package net.mrscauthd.beyond_earth.capability.air;

import net.minecraft.nbt.CompoundTag;

import java.util.Optional;

public class AirStorage implements IAirStorage {
	private IAirStorageHolder holder;
	protected int air;
	protected int capacity;

	public AirStorage(IAirStorageHolder holder, int capacity) {
		this(holder, capacity, 0);
	}

	public AirStorage(IAirStorageHolder holder, int capacity, int air) {
		this.holder = holder;
		this.capacity = capacity;
		this.air = Math.max(0, Math.min(capacity, air));
	}

	public int receiveAir(int maxReceive, boolean simulate) {
		int air = this.getAirStored();
		int airReceived = Math.min(this.getMaxAirStored() - air, Math.max(0, maxReceive));

		if (!simulate) {
			this.setAirStored(air + airReceived);
		}

		return airReceived;
	}

	public int extractAir(int maxExtract, boolean simulate) {
		int air = this.getAirStored();
		int airExtracted = Math.min(air, Math.max(0, maxExtract));

		if (!simulate) {
			this.setAirStored(air - airExtracted);
		}

		return airExtracted;
	}

	public IAirStorageHolder getHolder() {
		return this.holder;
	}

	public int getAirStored() {
		return this.air;
	}

	@Override
	public void setAirStored(int air) {
		air = Math.max(Math.min(air, this.getMaxAirStored()), 0);
		int airPrev = this.getAirStored();

		if (airPrev != air) {
			this.air = air;

			int delta = air - airPrev;
			Optional.ofNullable(this.getHolder()).ifPresent(h -> h.onAirChanged(this, delta));
		}
	}

	public int getMaxAirStored() {
		return this.capacity;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag compound = new CompoundTag();
		compound.putInt("air", this.getAirStored());
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundTag compound) {
		this.setAirStored(compound.getInt("air"));
	}

}
