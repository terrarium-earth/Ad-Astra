package net.mrscauthd.beyond_earth.compat.theoneprobe;

import mcjty.theoneprobe.api.IElement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.gauge.GaugeValueRenderer;
import net.mrscauthd.beyond_earth.gauge.IGaugeValue;

public class GaugeValueElement extends GaugeValueRenderer implements IElement {

	public GaugeValueElement(IGaugeValue value) {
		super(value);
	}

	public GaugeValueElement(FriendlyByteBuf buffer) {
		super(buffer);
	}

	@Override
	public ResourceLocation getID() {
		return GaugeValueElementFactory.ELEMENT_ID;
	}

}
