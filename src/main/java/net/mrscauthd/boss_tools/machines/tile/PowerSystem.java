package net.mrscauthd.boss_tools.machines.tile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public abstract class PowerSystem implements INBTSerializable<CompoundTag> {
	private final AbstractMachineBlockEntity blockEntity;

	public PowerSystem(AbstractMachineBlockEntity blockEntity) {
		this.blockEntity = blockEntity;
	}

	public List<IGaugeValue> getGaugeValues() {
		return new ArrayList<>();
	}

	public int getPowerPerTick() {
		int base = this.getBasePowerPerTick();
		return this.getBlockEntity().getPowerPerTick(this, base);
	}

	public int getPowerForOperation() {
		int base = this.getBasePowerForOperation();
		return this.getBlockEntity().getPowerForOperation(this, base);
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
	public void deserializeNBT(CompoundTag compound) {

	}

	@Override
	public CompoundTag serializeNBT() {
		return new CompoundTag();
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

	public final AbstractMachineBlockEntity getBlockEntity() {
		return this.blockEntity;
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
