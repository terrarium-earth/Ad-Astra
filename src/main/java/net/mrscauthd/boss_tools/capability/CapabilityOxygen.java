package net.mrscauthd.boss_tools.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityOxygen {
	@CapabilityInject(IOxygenStorage.class)
	public static Capability<IOxygenStorage> OXYGEN = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(IOxygenStorage.class, new IStorage<IOxygenStorage>() {
			@Override
			public void readNBT(Capability<IOxygenStorage> capability, IOxygenStorage instance, Direction direction, INBT nbt) {
				if (nbt instanceof CompoundNBT) {
					CompoundNBT compound = (CompoundNBT) nbt;
					instance.setOxygenStored(compound.getInt("oxygen"));
				}
			}

			@Override
			public INBT writeNBT(Capability<IOxygenStorage> capability, IOxygenStorage instance, Direction direction) {
				CompoundNBT compound = new CompoundNBT();
				compound.putInt("oxygen", instance.getOxygenStored());
				return compound;
			}
		}, () -> {
			return new OxygenStorage(null, 1000);
		});
	}
}
