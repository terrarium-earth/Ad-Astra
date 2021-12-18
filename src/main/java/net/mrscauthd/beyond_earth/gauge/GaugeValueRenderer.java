package net.mrscauthd.beyond_earth.gauge;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;

public class GaugeValueRenderer extends AbstractGaugeDataRenderer {

	private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(BeyondEarthMod.MODID, "textures/simplegaugevalue.png");

	public GaugeValueRenderer(IGaugeValue value) {
		super(value);
	}

	public GaugeValueRenderer(FriendlyByteBuf buffer) {
		super(GaugeValueSerializer.Serializer.read(buffer));
	}

	@Override
	public TextureAtlasSprite getBackgroundTileTexture() {
		IGaugeValue value = this.getValue();

		if (value instanceof GaugeValueFluidStack) {
			return GuiHelper.getStillFluidSprite(((GaugeValueFluidStack) value).getStack());
		} else {
			return null;
		}
	}

	@Override
	public ResourceLocation getBackgroundTexture() {
		IGaugeValue value = this.getValue();
		return value instanceof GaugeValueSimple ? DEFAULT_TEXTURE : null;
	}

}
