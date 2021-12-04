package net.mrscauthd.boss_tools.compat.theoneprobe;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.compat.CompatibleManager;
import net.mrscauthd.boss_tools.compat.mekanism.MekanismHelper;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;

public class ProbeInfoProvider implements IProbeInfoProvider, Function<ITheOneProbe, Void> {

	public static int ELEMENT_ID;

	public ProbeInfoProvider() {

	}

	@Override
	public Void apply(ITheOneProbe top) {
		top.registerProvider(this);
		top.registerProbeConfigProvider(ProbeConfigProvider.INSTANCE);
		ELEMENT_ID = top.registerElementFactory(GaugeValueElement::new);

		return null;
	}

	@Override
	public void addProbeInfo(ProbeMode probeMod, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState blockState, IProbeHitData hitData) {
		TileEntity tileEntity = world.getTileEntity(hitData.getPos());

		if (tileEntity instanceof AbstractMachineTileEntity) {
			AbstractMachineTileEntity machineTileEntity = (AbstractMachineTileEntity) tileEntity;
			
			if (probeMod != ProbeMode.EXTENDED)
			{
				machineTileEntity.getFluidHandlers().values().stream().map(machineTileEntity::getFluidHandlerGaugeValues).flatMap(Collection::stream).forEach(g -> probeInfo.element(new GaugeValueElement(g)));
				
				if (CompatibleManager.MEKANISM.isLoaded()) {
					List<? extends IElement> elements = MekanismHelper.createGasGaugeDataElement(machineTileEntity.getCapability(MekanismHelper.getGasHandlerCapability()).orElse(null));
					elements.forEach(element -> probeInfo.element(element));
				}
				
			}
			
			machineTileEntity.getGaugeValues().forEach(g -> probeInfo.element(new GaugeValueElement(g)));
		}
	}

	@Override
	public String getID() {
		return new ResourceLocation("boss_tools", "top").toString();
	}
}
