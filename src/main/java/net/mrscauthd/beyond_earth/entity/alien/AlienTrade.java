package net.mrscauthd.beyond_earth.entity.alien;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.commons.lang3.tuple.Triple;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.beyond_earth.crafting.AlienTradingRecipe;
import net.mrscauthd.beyond_earth.crafting.AlienTradingRecipeType;

public class AlienTrade implements ItemListing {
	public static int MAX_USES = 9999;
	public static final Map<VillagerProfession, Int2ObjectMap<ItemListing[]>> TRADES = new HashMap<>();

	public static void onAddReloadListener(AddReloadListenerEvent event) {
		AlienTrade.addReloadListener(event);
	}

	public static void registerTrades(RecipeManager recipeManager) {
		Map<VillagerProfession, Int2ObjectMap<List<ItemListing>>> jobMap = new HashMap<>();
		VillagerProfession[] jobs = ForgeRegistries.PROFESSIONS.getValues().toArray(new VillagerProfession[0]);

		for (VillagerProfession job : jobs) {
			jobMap.put(job, new Int2ObjectOpenHashMap<>());
		}

		for (AlienTradingRecipeType<? extends AlienTradingRecipe> type : AlienTradingRecipeType.getTypes()) {
			List<? extends AlienTradingRecipe> recipes = recipeManager.getAllRecipesFor(type);

			for (AlienTradingRecipe recipe : recipes) {
				Int2ObjectMap<List<ItemListing>> listMap = jobMap.get(recipe.getJob());
				listMap.computeIfAbsent(recipe.getLevel(), l -> new ArrayList<>()).add(new AlienTrade(recipe));
			}

		}

		Map<VillagerProfession, Int2ObjectMap<ItemListing[]>> map = AlienTrade.TRADES;
		map.clear();

		for (Entry<VillagerProfession, Int2ObjectMap<List<ItemListing>>> entry : jobMap.entrySet()) {
			Int2ObjectMap<ItemListing[]> listMap = map.computeIfAbsent(entry.getKey(), l -> new Int2ObjectOpenHashMap<>());
			Int2ObjectMap<List<ItemListing>> values = entry.getValue();

			for (int level : values.keySet()) {
				List<ItemListing> trades = values.get(level);
				listMap.put(level, trades.toArray(new ItemListing[0]));
			}

		}

	}

	public static void addReloadListener(AddReloadListenerEvent event) {
		if (event.getServerResources() != null) {
			RecipeManager recipeManager = event.getServerResources().getRecipeManager();

			event.addListener(new ResourceManagerReloadListener() {
				@Override
				public void onResourceManagerReload(ResourceManager resourceManager) {
					AlienTrade.registerTrades(recipeManager);
				}

			});
		}
	}

	private AlienTradingRecipe recipe;

	private AlienTrade(AlienTradingRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public MerchantOffer getOffer(Entity entity, Random random) {
		AlienTradingRecipe recipe = this.getRecipe();
		Triple<ItemStack, ItemStack, ItemStack> trade = recipe.getTrade(entity, random);
		return new MerchantOffer(trade.getLeft(), trade.getMiddle(), trade.getRight(), 0, recipe.getMaxUses(), recipe.getXP(), recipe.getPriceMultiplier());
	}

	public AlienTradingRecipe getRecipe() {
		return this.recipe;
	}
}