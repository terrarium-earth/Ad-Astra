package net.mrscauthd.beyond_earth.crafting;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.BeyondEarthMod;

public class BeyondEarthRecipeTypes {
	public static final ItemStackToItemStackRecipeType<CompressingRecipe> COMPRESSING = create(new ItemStackToItemStackRecipeType<>("compressing"));
	public static final BeyondEarthRecipeType<GeneratingRecipe> GENERATING = create(new BeyondEarthRecipeType<>("generating"));
	public static final BeyondEarthRecipeType<OxygenLoaderRecipe> OXYGENLOADER = create(new BeyondEarthRecipeType<>("oxygenloader"));
	public static final BeyondEarthRecipeType<OxygenBubbleDistributorRecipe> OXYGENBUBBLEDISTRIBUTOR = create(new BeyondEarthRecipeType<>("oxygenbubbledistributor"));
	public static final BeyondEarthRecipeType<WorkbenchingRecipe> WORKBENCHING = create(new BeyondEarthRecipeType<>("workbenching"));
	public static final BeyondEarthRecipeType<FuelRefiningRecipe> FUELREFINING = create(new BeyondEarthRecipeType<>("fuelrefining"));
	public static final AlienTradingRecipeType<AlienTradingRecipeItemStack> ALIEN_TRADING_ITEMSTACK = create(new AlienTradingRecipeType<>("alien_trading_itemstack"));
	public static final AlienTradingRecipeType<AlienTradingRecipeEnchantedBook> ALIEN_TRADING_ENCHANTEDBOOK = create(new AlienTradingRecipeType<>("alien_trading_enchantedbook"));
	public static final AlienTradingRecipeType<AlienTradingRecipeEnchantedItem> ALIEN_TRADING_ENCHANTEDITEM = create(new AlienTradingRecipeType<>("alien_trading_enchanteditem"));
	public static final AlienTradingRecipeType<AlienTradingRecipeMap> ALIEN_TRADING_MAP = create(new AlienTradingRecipeType<>("alien_trading_map"));
	public static final AlienTradingRecipeType<AlienTradingRecipePotionedItem> ALIEN_TRADING_POTIONEDITEM = create(new AlienTradingRecipeType<>("alien_trading_potioneditem"));
	public static final AlienTradingRecipeType<AlienTradingRecipeDyedItem> ALIEN_TRADING_DYEDITEM = create(new AlienTradingRecipeType<>("alien_trading_dyeditem"));

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
