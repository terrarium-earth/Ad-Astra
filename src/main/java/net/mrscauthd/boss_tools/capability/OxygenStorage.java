package net.mrscauthd.boss_tools.capability;

import java.util.Optional;

import net.minecraft.nbt.CompoundTag;

public class OxygenStorage implements IOxygenStorage {
	private IOxygenStorageHolder holder;
	protected int oxygen;
	protected int capacity;

	public OxygenStorage(IOxygenStorageHolder holder, int capacity) {
		this(holder, capacity, 0);
	}

	public OxygenStorage(IOxygenStorageHolder holder, int capacity, int oxygen) {
		this.holder = holder;
		this.capacity = capacity;
		this.oxygen = Math.max(0, Math.min(capacity, oxygen));
	}

	public int receiveOxygen(int maxReceive, boolean simulate) {
		int oxygen = this.getOxygenStored();
		int oxygenReceived = Math.min(this.getMaxOxygenStored() - oxygen, Math.max(0, maxReceive));

		if (!simulate) {
			this.setOxygenStored(oxygen + oxygenReceived);
		}

		return oxygenReceived;
	}

	public int extractOxygen(int maxExtract, boolean simulate) {
		int oxygen = this.getOxygenStored();
		int oxygenExtracted = Math.min(oxygen, Math.max(0, maxExtract));

		if (!simulate) {
			this.setOxygenStored(oxygen - oxygenExtracted);
		}

		return oxygenExtracted;
	}

	public IOxygenStorageHolder getHolder() {
		return this.holder;
	}

	public int getOxygenStored() {
		return this.oxygen;
	}

	@Override
	public void setOxygenStored(int oxygen) {
		oxygen = Math.max(Math.min(oxygen, this.getMaxOxygenStored()), 0);
		int oxygenPrev = this.getOxygenStored();

		if (oxygenPrev != oxygen) {
			this.oxygen = oxygen;

			int delta = oxygen - oxygenPrev;
			Optional.ofNullable(this.getHolder()).ifPresent(h -> h.onOxygenChanged(this, delta));
		}
	}

	public int getMaxOxygenStored() {
		return this.capacity;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag compound = new CompoundTag();
		compound.putInt("oxygen", this.getOxygenStored());
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundTag compound) {
		this.setOxygenStored(compound.getInt("oxygen"));
	}

}
