package net.mrscauthd.beyond_earth.crafting;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.BeyondEarthMod;

public class BeyondEarthRecipeTypes {
	public static final ItemStackToItemStackRecipeType<BlastingRecipe> BLASTING = create(new ItemStackToItemStackRecipeType<>("blasting"));
	public static final ItemStackToItemStackRecipeType<CompressingRecipe> COMPRESSING = create(new ItemStackToItemStackRecipeType<>("compressing"));
	public static final BeyondEarthRecipeType<GeneratingRecipe> GENERATING = create(new BeyondEarthRecipeType<>("generating"));
	public static final BeyondEarthRecipeType<OxygenLoaderRecipe> OXYGENLOADER = create(new BeyondEarthRecipeType<>("oxygenloader"));
	public static final BeyondEarthRecipeType<OxygenBubbleDistributorRecipe> OXYGENBUBBLEDISTRIBUTOR = create(new BeyondEarthRecipeType<>("oxygenbubbledistributor"));
	public static final BeyondEarthRecipeType<WorkbenchingRecipe> WORKBENCHING = create(new BeyondEarthRecipeType<>("workbenching"));
	public static final BeyondEarthRecipeType<FuelRefiningRecipe> FUELREFINING = create(new BeyondEarthRecipeType<>("fuelrefining"));

	/**
	 * for initialize static final fields
	 */
	public static void init() {

	}

	private static <T extends BeyondEarthRecipeType<?>> T create(T value) {
		Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(BeyondEarthMod.MODID, value.getName()), value);
		return value;
	}

}
