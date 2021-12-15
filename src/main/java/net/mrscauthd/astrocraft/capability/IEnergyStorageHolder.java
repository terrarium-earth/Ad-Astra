package net.mrscauthd.astrocraft.capability;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyStorageHolder {

	void onEnergyChanged(IEnergyStorage energyStorage, int energyDelta);

}
