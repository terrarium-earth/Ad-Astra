package net.mrscauthd.boss_tools.gauge;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.INBTSerializable;

public interface IGaugeValue extends INBTSerializable<CompoundNBT> {

	ITextComponent getDisplayName();

	String getUnit();

	int getAmount();

	int getCapacity();
	
	public default int getColor()
	{
		return 0x00000000;
	}

	boolean isReverse();

	public default double getDisplayRatio() {
		int capacity = this.getCapacity();

		if (capacity == 0) {
			return 0.0D;
		}

		int amount = this.getAmount();
		return (this.isReverse() ? (capacity - amount) : amount) / (double) capacity;
	}

}
