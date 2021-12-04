package net.mrscauthd.boss_tools.compat.theoneprobe;

import mcjty.theoneprobe.api.IProbeConfig;
import mcjty.theoneprobe.api.IProbeConfigProvider;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeHitEntityData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ProbeConfigProvider implements IProbeConfigProvider {

	public static final ProbeConfigProvider INSTANCE = new ProbeConfigProvider();

	@Override
	public void getProbeConfig(IProbeConfig var1, PlayerEntity var2, World var3, Entity var4, IProbeHitEntityData var5) {

	}

	@Override
	public void getProbeConfig(IProbeConfig var1, PlayerEntity var2, World var3, BlockState var4, IProbeHitData var5) {

	}

}
