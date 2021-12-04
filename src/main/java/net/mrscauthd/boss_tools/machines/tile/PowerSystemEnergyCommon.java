package net.mrscauthd.boss_tools.machines.tile;

import net.minecraftforge.energy.IEnergyStorage;

public abstract class PowerSystemEnergyCommon extends PowerSystemEnergy {

	public PowerSystemEnergyCommon(AbstractMachineTileEntity tileEntity) {
		super(tileEntity);
	}

	public PowerSystemEnergyCommon(AbstractMachineTileEntity tileEntity, IEnergyStorage energyStorage) {
		super(tileEntity, energyStorage);
	}

	@Override
	public int getBasePowerPerTick() {
		return 0;
	}

}
