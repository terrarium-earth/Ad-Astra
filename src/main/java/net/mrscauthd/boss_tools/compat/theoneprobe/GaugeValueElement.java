package net.mrscauthd.boss_tools.compat.theoneprobe;

import mcjty.theoneprobe.api.IElement;
import net.minecraft.network.PacketBuffer;
import net.mrscauthd.boss_tools.gauge.GaugeValueRenderer;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class GaugeValueElement extends GaugeValueRenderer implements IElement {

	public GaugeValueElement(IGaugeValue value) {
		super(value);
	}

	public GaugeValueElement(PacketBuffer buffer) {
		super(buffer);
	}

	@Override
	public int getID() {
		return ProbeInfoProvider.ELEMENT_ID;
	}

}
