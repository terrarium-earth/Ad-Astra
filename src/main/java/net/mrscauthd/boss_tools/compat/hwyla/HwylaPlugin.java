package net.mrscauthd.boss_tools.compat.hwyla;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.WailaPlugin;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.BossToolsMod;

@WailaPlugin
public class HwylaPlugin implements IWailaPlugin {

	public static final ResourceLocation TOOLTIP = new ResourceLocation(BossToolsMod.ModId, "hwlya_tooltip");

	@Override
	public void register(IRegistrar registrar) {
		registrar.registerBlockDataProvider(ServerDataProvider.INSTANCE, TileEntity.class);
		registrar.registerComponentProvider(TooltipRenderer.INSTANCE, TooltipPosition.BODY, TileEntity.class);
		registrar.registerTooltipRenderer(TOOLTIP, TooltipRenderer.INSTANCE);
	}
}
