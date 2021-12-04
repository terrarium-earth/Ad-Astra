package net.mrscauthd.boss_tools.machines.tile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public abstract class PowerSystem implements INBTSerializable<CompoundNBT> {
	private final AbstractMachineTileEntity tileEntity;

	public PowerSystem(AbstractMachineTileEntity tileEntity) {
		this.tileEntity = tileEntity;
	}

	public List<IGaugeValue> getGaugeValues() {
		return new ArrayList<>();
	}

	public int getPowerPerTick() {
		int base = this.getBasePowerPerTick();
		return this.getTileEntity().getPowerPerTick(this, base);
	}

	public int getPowerForOperation() {
		int base = this.getBasePowerForOperation();
		return this.getTileEntity().getPowerForOperation(this, base);
	}

	public boolean isPowerEnoughForOperation() {
		int require = this.getPowerPerTick() + this.getPowerForOperation();
		return this.extract(require, true) >= require;
	}

	public int consumeForOperation() {
		if (this.isPowerEnoughForOperation()) {
			return this.consume(this.getPowerForOperation());
		} else {
			return 0;
		}
	}

	public int getUsingSlots() {
		return 0;
	}

	public abstract int getBasePowerPerTick();

	public abstract int getBasePowerForOperation();

	public abstract int getStored();

	public abstract int getCapacity();

	public double getStoredRatio() {
		int capacity = this.getCapacity();
		return capacity > 0 ? ((double) this.getStored() / (double) capacity) : 0;
	}

	public abstract int receive(int amount, boolean simulate);

	public abstract int extract(int amount, boolean simulate);

	@Override
	public void deserializeNBT(CompoundNBT compound) {

	}

	@Override
	public CompoundNBT serializeNBT() {
		return new CompoundNBT();
	}

	/**
	 * 
	 * @return complete extract energy for operation
	 */
	public int consume(int amount) {
		if (this.extract(amount, true) == amount) {
			return this.extract(amount, false);
		} else {
			return 0;
		}
	}

	public boolean feed(boolean spareForNextTick) {
		return false;
	}

	public void getSlotsForFace(Direction direction, List<Integer> slots) {

	}

	public boolean canInsertItem(@Nullable Direction direction, int index, ItemStack stack) {
		return false;
	}

	public boolean canExtractItem(@Nullable Direction direction, int index, ItemStack stack) {
		return false;
	}

	public final AbstractMachineTileEntity getTileEntity() {
		return this.tileEntity;
	}

	public void update() {
		int powerPerTick = this.getPowerPerTick();

		if (powerPerTick > 0) {
			this.consume(powerPerTick);
		}

		if (!this.isPowerEnoughForOperation()) {
			this.feed(true);
		}

	}

	public abstract ResourceLocation getName();

	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		return LazyOptional.empty();
	}
}
