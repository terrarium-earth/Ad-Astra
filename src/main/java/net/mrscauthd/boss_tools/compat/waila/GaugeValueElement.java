package net.mrscauthd.boss_tools.compat.waila;

import com.mojang.blaze3d.vertex.PoseStack;

import mcp.mobius.waila.api.ui.Element;
import net.minecraft.world.phys.Vec2;
import net.mrscauthd.boss_tools.gauge.GaugeValueRenderer;

public class GaugeValueElement extends Element {

	public static final int RIGHT_PADDING = 2;
	public static final int TOP_OFFSET = 1;
	public static final int BOTTOM_PADDING = 1;

	private final GaugeValueRenderer renderer;

	public GaugeValueElement(GaugeValueRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public Vec2 getSize() {
		Vec2 size = this.getRenderer().getSize();
		return new Vec2(size.x, size.y + TOP_OFFSET);
	}

	@Override
	public void render(PoseStack matrixStack, float x, float y, float maxX, float maxY) {
		this.getRenderer().render(matrixStack, (int) x, (int) y, (int) (maxX - x - RIGHT_PADDING), (int) (maxY - y - TOP_OFFSET - BOTTOM_PADDING));
	}

	public GaugeValueRenderer getRenderer() {
		return this.renderer;
	}

}
