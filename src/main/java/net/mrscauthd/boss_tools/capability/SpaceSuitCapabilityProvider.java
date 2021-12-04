package net.mrscauthd.boss_tools.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.mrscauthd.boss_tools.compat.CompatibleManager;
import net.mrscauthd.boss_tools.compat.mekanism.MekanismHelper;
import net.mrscauthd.boss_tools.compat.mekanism.OxygenStorageGasAdapter;

import ItemStack;

public class SpaceSuitCapabilityProvider implements ICapabilityProvider, IOxygenStorageHolder {

	public static final String KEY_OXYGEN = "Energy"; // for compatible other code

	private ItemStack itemStack;
	private IOxygenStorage oxygenStorage;

	public SpaceSuitCapabilityProvider(ItemStack itemStack, int capacity) {
		this.itemStack = itemStack;
		this.oxygenStorage = new OxygenStorage(this, capacity);

		this.readOxygen();
	}

	private void readOxygen() {
		CompoundNBT compound = this.getItemStack().getOrCreateTag();
		this.getOxygenStorage().setOxygenStored(compound.getInt(KEY_OXYGEN));
	}

	public void writeOxygen() {
		CompoundNBT compound = this.getItemStack().getOrCreateTag();
		compound.putInt(KEY_OXYGEN, this.getOxygenStorage().getOxygenStored());
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction direction) {
		if (capability == CapabilityOxygen.OXYGEN) {
			this.readOxygen();
			return LazyOptional.of(this::getOxygenStorage).cast();
		}

		if (CompatibleManager.MEKANISM.isLoaded()) {
			if (capability == MekanismHelper.getGasHandlerCapability()) {
				return LazyOptional.of(() -> new OxygenStorageGasAdapter(this.getCapability(CapabilityOxygen.OXYGEN, direction).orElse(null), true, true)).cast();
			}
		}

		return LazyOptional.empty();
	}

	@Override
	public void onOxygenChanged(IOxygenStorage oxygenStorage, int oxygenDelta) {
		this.writeOxygen();
	}

	public ItemStack getItemStack() {
		return this.itemStack;
	}

	public IOxygenStorage getOxygenStorage() {
		return this.oxygenStorage;
	}

}
