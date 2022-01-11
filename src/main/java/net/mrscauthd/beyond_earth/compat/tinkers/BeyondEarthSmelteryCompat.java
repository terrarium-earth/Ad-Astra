package net.mrscauthd.beyond_earth.compat.tinkers;

import java.util.Locale;

import net.minecraft.world.item.Item;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.smeltery.data.Byproduct;

public enum BeyondEarthSmelteryCompat
{
	DESH(TinkersBeyondEarthFluids.MOLTEN_DESH, true),
	OSTRUM(TinkersBeyondEarthFluids.MOLTEN_OSTRUM, true),
	// EOL
	;

	private final String name = this.name().toLowerCase(Locale.US);
	private final FluidObject<? extends ForgeFlowingFluid> fluid;
	private final boolean isOre;
	private final boolean hasDust;
	private final Byproduct[] byproducts;

	BeyondEarthSmelteryCompat(FluidObject<? extends ForgeFlowingFluid> fluid, boolean hasDust)
	{
		this.fluid = fluid;
		this.isOre = false;
		this.byproducts = new Byproduct[0];
		this.hasDust = hasDust;
	}

	/** Byproducts means its an ore, no byproucts are alloys */
	BeyondEarthSmelteryCompat(FluidObject<? extends ForgeFlowingFluid> fluid, Byproduct... byproducts)
	{
		this.fluid = fluid;
		this.isOre = byproducts.length > 0;
		this.byproducts = byproducts;
		this.hasDust = true;
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

	public boolean hasDust()
	{
		return this.hasDust;
	}

	public Byproduct[] getByproducts()
	{
		return this.byproducts;
	}

}
