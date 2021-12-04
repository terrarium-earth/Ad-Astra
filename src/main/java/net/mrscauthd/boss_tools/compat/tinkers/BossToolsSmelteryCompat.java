package net.mrscauthd.boss_tools.compat.tinkers;

import java.util.Locale;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.smeltery.data.Byproduct;

import Byproduct;
import FluidObject;

public enum BossToolsSmelteryCompat {
	DESH(TinkersBossToolsFluids.moltenDesh, true),
	SILICON(TinkersBossToolsFluids.moltenSilicon, true),
	// EOL
	;

	private final String name = this.name().toLowerCase(Locale.US);
	private final FluidObject<? extends ForgeFlowingFluid> fluid;
	private final boolean isOre;
	private final Byproduct[] byproducts;

	BossToolsSmelteryCompat(FluidObject<? extends ForgeFlowingFluid> fluid, boolean isOre, Byproduct... byproducts)
	{
		this.fluid = fluid;
		this.isOre = isOre;
		this.byproducts = byproducts;
	}

	public String getName()
	{
		return this.name;
	}

	public FluidObject<?> getFluid()
	{
		return this.fluid;
	}

	public Item getBucket()
	{
		return this.fluid.asItem();
	}

	public boolean isOre()
	{
		return this.isOre;
	}

	public Byproduct[] getByproducts()
	{
		return this.byproducts;
	}

}
