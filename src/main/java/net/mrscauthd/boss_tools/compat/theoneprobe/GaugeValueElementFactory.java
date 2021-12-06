package net.mrscauthd.boss_tools.compat.theoneprobe;

import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IElementFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.boss_tools.BossToolsMod;

public class GaugeValueElementFactory implements IElementFactory {

	public static final ResourceLocation ELEMENT_ID = new ResourceLocation(BossToolsMod.ModId, "top_element");
	public static final GaugeValueElementFactory INSTANCE = new GaugeValueElementFactory();

	@Override
	public ResourceLocation getId() {
		return ELEMENT_ID;
	}

	@Override
	public IElement createElement(FriendlyByteBuf buffer) {
		return new GaugeValueElement(buffer);
	}

}
