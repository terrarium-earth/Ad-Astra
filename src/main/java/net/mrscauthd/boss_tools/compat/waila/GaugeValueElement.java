package net.mrscauthd.boss_tools.compat.waila;

import com.mojang.blaze3d.vertex.PoseStack;

import mcp.mobius.waila.api.ui.Element;
import net.minecraft.world.phys.Vec2;
import net.mrscauthd.boss_tools.gauge.GaugeValueRenderer;

public class GaugeValueElement extends Element {

	private final GaugeValueRenderer renderer;

	public GaugeValueElement(GaugeValueRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public Vec2 getSize() {
		return this.getRenderer().getSize();
	}

	@Override
	public void render(PoseStack matrixStack, float x, float y, float maxX, float maxY) {
		this.getRenderer().render(matrixStack, (int) x, (int) y, (int) (maxX - x - 2), (int) (maxY - y));
	}

	public GaugeValueRenderer getRenderer() {
		return this.renderer;
	}

}
