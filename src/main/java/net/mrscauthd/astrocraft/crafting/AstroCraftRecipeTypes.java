package net.mrscauthd.astrocraft.crafting;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.astrocraft.AstroCraftMod;

public class AstroCraftRecipeTypes {
	public static final ItemStackToItemStackRecipeType<BlastingRecipe> BLASTING = create(new ItemStackToItemStackRecipeType<>("blasting"));
	public static final ItemStackToItemStackRecipeType<CompressingRecipe> COMPRESSING = create(new ItemStackToItemStackRecipeType<>("compressing"));
	public static final AstroCraftRecipeType<GeneratingRecipe> GENERATING = create(new AstroCraftRecipeType<>("generating"));
	public static final AstroCraftRecipeType<OxygenLoaderRecipe> OXYGENLOADER = create(new AstroCraftRecipeType<>("oxygenloader"));
	public static final AstroCraftRecipeType<OxygenBubbleDistributorRecipe> OXYGENBUBBLEDISTRIBUTOR = create(new AstroCraftRecipeType<>("oxygenbubbledistributor"));
	public static final AstroCraftRecipeType<WorkbenchingRecipe> WORKBENCHING = create(new AstroCraftRecipeType<>("workbenching"));
	public static final AstroCraftRecipeType<FuelRefiningRecipe> FUELREFINING = create(new AstroCraftRecipeType<>("fuelrefining"));

	/**
	 * for initialize static final fields
	 */
	public static void init() {

	}

	private static <T extends AstroCraftRecipeType<?>> T create(T value) {
		Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(AstroCraftMod.MODID, value.getName()), value);
		return value;
	}

}
