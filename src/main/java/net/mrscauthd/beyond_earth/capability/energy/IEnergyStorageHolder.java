package net.mrscauthd.beyond_earth.capability.energy;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyStorageHolder {

	void onEnergyChanged(IEnergyStorage energyStorage, int energyDelta);

}
