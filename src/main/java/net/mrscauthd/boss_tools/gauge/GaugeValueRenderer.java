package net.mrscauthd.boss_tools.gauge;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;

public class GaugeValueRenderer extends AbstractGaugeDataRenderer {

	private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(BossToolsMod.ModId, "textures/simplegaugevalue.png");

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
