package net.mrscauthd.boss_tools.compat.theoneprobe;

import mcjty.theoneprobe.api.IProbeHitEntityData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoEntityProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.mrscauthd.boss_tools.gauge.IGaugeValuesProvider;

public class ProbeInfoEntityProvider implements IProbeInfoEntityProvider {

	public static final ProbeInfoEntityProvider INSTANCE = new ProbeInfoEntityProvider();

	public ProbeInfoEntityProvider() {

	}

	@Override
	public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo probeInfo, Player player, Level level, Entity entity, IProbeHitEntityData hitData) {

		if (entity instanceof IGaugeValuesProvider) {
			((IGaugeValuesProvider) entity).getGaugeValues().forEach(g -> probeInfo.element(new GaugeValueElement(g)));
		}

	}

	@Override
	public String getID() {
		return new ResourceLocation("boss_tools", "top_entity").toString();
	}

}
