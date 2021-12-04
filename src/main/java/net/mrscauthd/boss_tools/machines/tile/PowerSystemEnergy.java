package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.BossToolsMod;

public abstract class PowerSystemEnergy extends PowerSystem {
	private final IEnergyStorage energyStorage;

	public PowerSystemEnergy(AbstractMachineTileEntity tileEntity) {
		this(tileEntity, tileEntity.getPrimaryEnergyStorage());
	}

	public PowerSystemEnergy(AbstractMachineTileEntity tileEntity, IEnergyStorage energyStorage) {
		super(tileEntity);
		this.energyStorage = energyStorage;
	}

	@Override
	public int getStored() {
		return this.getEnergyStorage().getEnergyStored();
	}

	@Override
	public int getCapacity() {
		return this.getEnergyStorage().getMaxEnergyStored();
	}

	@Override
	public int receive(int amount, boolean simulate) {
		return this.getEnergyStorage().receiveEnergy(amount, simulate);
	}

	@Override
	public int extract(int amount, boolean simulate) {
		return this.getEnergyStorage().extractEnergy(amount, simulate);
	}

	public final IEnergyStorage getEnergyStorage() {
		return this.energyStorage;
	}

	@Override
	public ResourceLocation getName() {
		return new ResourceLocation(BossToolsMod.ModId, "energy");
	}
}
