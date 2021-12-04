package net.mrscauthd.boss_tools.compat.hwyla;

import java.awt.Dimension;

import net.mrscauthd.boss_tools.gauge.GaugeValueRenderer;
import net.mrscauthd.boss_tools.gauge.GaugeValueSerializer;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class TooltipRenderer/* implements IComponentProvider, ITooltipRenderer*/ {

	/*
	public static final TooltipRenderer INSTANCE = new TooltipRenderer();

	@Override
	public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
		IComponentProvider.super.appendBody(tooltip, accessor, config);

		CompoundNBT serverData = accessor.getServerData();
		ListNBT list = ServerDataProvider.get(serverData);

		if (list.size() > 0) {
			CompoundNBT compound = new CompoundNBT();
			ServerDataProvider.put(compound, list);
			tooltip.add(new RenderableTextComponent(HwylaPlugin.TOOLTIP, compound));
		}

	}

	@Override
	public Dimension getSize(CompoundNBT compound, ICommonAccessor accessor) {
		return new Dimension(102, 15 * ServerDataProvider.get(compound).size());
	}

	@Override
	public void draw(CompoundNBT compound, ICommonAccessor accessor, int x, int y) {
		ListNBT list = ServerDataProvider.get(compound);
		MatrixStack matrix = new MatrixStack();

		for (int i = 0; i < list.size(); i++) {
			IGaugeValue value = GaugeValueSerializer.Serializer.deserialize(list.getCompound(i));
			GaugeValueRenderer renderer = new GaugeValueRenderer(value);
			renderer.render(matrix, x + 1, y);
			y += renderer.getHeight() + 1;
		}
	}
	 */
}
