package net.mrscauthd.beyond_earth.machines.tile;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;
import net.mrscauthd.beyond_earth.gauge.IGaugeValue;

public abstract class PowerSystemFuelBurnTime extends PowerSystemFuel {

	public static final int FUEL_PER_TICK = 1;

	public PowerSystemFuelBurnTime(AbstractMachineBlockEntity blockEntity, int slot) {
		super(blockEntity, slot);
	}

	@Override
	public List<IGaugeValue> getGaugeValues() {
		List<IGaugeValue> list = super.getGaugeValues();
		list.add(GaugeValueHelper.getBurnTime(this));
		return list;
	}

	@Override
	public int getBasePowerPerTick() {
		return FUEL_PER_TICK;
	}

	@Override
	public int getBasePowerForOperation() {
		return 0;
	}

	@Override
	public ResourceLocation getName() {
		ResourceLocation name = super.getName();
		return new ResourceLocation(name.getNamespace(), name.getPath() + "/burntime");
	}
}
