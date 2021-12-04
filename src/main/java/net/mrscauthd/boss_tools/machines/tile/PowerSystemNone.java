package net.mrscauthd.boss_tools.machines.tile;

import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.BossToolsMod;

import ResourceLocation;

public class PowerSystemNone extends PowerSystem {
	public PowerSystemNone(AbstractMachineTileEntity tileEntity) {
		super(tileEntity);
	}

	@Override
	public int getBasePowerPerTick() {
		return 0;
	}

	@Override
	public int getBasePowerForOperation() {
		return 0;
	}

	@Override
	public int getStored() {
		return 0;
	}

	@Override
	public int getCapacity() {
		return 0;
	}

	@Override
	public int receive(int amount, boolean simulate) {
		return 0;
	}

	@Override
	public int extract(int amount, boolean simulate) {
		return 0;
	}

	@Override
	public ResourceLocation getName() {
		return new ResourceLocation(BossToolsMod.ModId, "none");
	}
}
