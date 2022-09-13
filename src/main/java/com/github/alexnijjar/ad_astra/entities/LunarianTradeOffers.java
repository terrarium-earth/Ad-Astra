package com.github.alexnijjar.ad_astra.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.ad_astra.items.OxygenTankItem;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers.Factory;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;

public class LunarianTradeOffers {
	public static final Map<VillagerProfession, Int2ObjectMap<Factory[]>> PROFESSION_TO_LEVELED_TRADE = Util.make(Maps.newHashMap(), map -> {
		map.put(VillagerProfession.FARMER,
				copyToFastUtilMap(ImmutableMap.of(1, new Factory[] { new BuyForOneEmeraldFactory(ModItems.CHEESE, 20, 20, 5), new BuyForOneEmeraldFactory(Items.GLOW_BERRIES, 22, 16, 3), new SellItemFactory(Items.BREAD, 1, 6, 16, 1) }, 2,
						new Factory[] { new SellItemFactory(Items.PUMPKIN_PIE, 1, 4, 5), new SellItemFactory(Items.GOLDEN_APPLE, 8, 1, 8, 20) }, 3,
						new Factory[] { new SellItemFactory(Items.COOKIE, 3, 18, 10), new BuyForOneEmeraldFactory(Blocks.MELON, 4, 12, 20) }, 4,
						new Factory[] { new SellItemFactory(Blocks.CAKE, 1, 1, 12, 15), new BuyForOneEmeraldFactory(Items.DIRT, 63, 16, 2), new SellSuspiciousStewFactory(StatusEffects.NIGHT_VISION, 100, 15),
								new SellSuspiciousStewFactory(StatusEffects.JUMP_BOOST, 160, 15), new SellSuspiciousStewFactory(StatusEffects.WEAKNESS, 140, 15), new SellSuspiciousStewFactory(StatusEffects.BLINDNESS, 120, 15),
								new SellSuspiciousStewFactory(StatusEffects.POISON, 280, 15), new SellSuspiciousStewFactory(StatusEffects.SATURATION, 7, 15) },
						5, new Factory[] { new SellItemFactory(Items.GOLDEN_CARROT, 3, 3, 30), new SellItemFactory(Items.GLISTERING_MELON_SLICE, 4, 3, 30) })));
		map.put(VillagerProfession.FISHERMAN,
				copyToFastUtilMap(ImmutableMap.of(1,
						new Factory[] { new BuyForOneEmeraldFactory(ModItems.CHEESE, 20, 20, 5), new BuyForOneEmeraldFactory(Items.STRING, 20, 16, 2), new BuyForOneEmeraldFactory(Items.COAL, 10, 16, 2),
								new ProcessItemFactory(Items.COD, 6, Items.COOKED_COD, 6, 16, 1), },
						2, new Factory[] { new BuyForOneEmeraldFactory(Items.COD, 15, 16, 10), new ProcessItemFactory(Items.SALMON, 6, Items.COOKED_SALMON, 6, 16, 5), new SellItemFactory(Items.SOUL_CAMPFIRE, 2, 1, 5) }, 3,
						new Factory[] { new BuyForOneEmeraldFactory(Items.SALMON, 13, 16, 20), new SellEnchantedToolFactory(Items.FISHING_ROD, 3, 3, 10, 0.2f) }, 4, new Factory[] { new BuyForOneEmeraldFactory(Items.TROPICAL_FISH, 6, 12, 30) }, 5,
						new Factory[] { new BuyForOneEmeraldFactory(Items.PUFFERFISH, 4, 12, 30), new SellItemFactory(new ItemStack(Items.WATER_BUCKET), 8, 1, 2, 9, 0.2f), new SellItemFactory(new ItemStack(Items.ICE), 4, 1, 8, 9, 0.2f) })));
		map.put(VillagerProfession.SHEPHERD,
				copyToFastUtilMap(ImmutableMap.of(1,
						new Factory[] { new BuyForOneEmeraldFactory(Blocks.WHITE_WOOL, 18, 16, 2), new BuyForOneEmeraldFactory(Blocks.BROWN_WOOL, 18, 16, 2), new BuyForOneEmeraldFactory(Blocks.BLACK_WOOL, 18, 16, 2),
								new BuyForOneEmeraldFactory(Blocks.GRAY_WOOL, 18, 16, 2), new SellItemFactory(Items.SHEARS, 2, 1, 1) },
						2,
						new Factory[] { new BuyForOneEmeraldFactory(Items.WHITE_DYE, 12, 16, 10), new BuyForOneEmeraldFactory(Items.GRAY_DYE, 12, 16, 10), new BuyForOneEmeraldFactory(Items.BLACK_DYE, 12, 16, 10),
								new BuyForOneEmeraldFactory(Items.LIGHT_BLUE_DYE, 12, 16, 10), new BuyForOneEmeraldFactory(Items.LIME_DYE, 12, 16, 10), new SellItemFactory(Blocks.WHITE_WOOL, 1, 1, 16, 5),
								new SellItemFactory(Blocks.ORANGE_WOOL, 1, 1, 16, 5), new SellItemFactory(Blocks.MAGENTA_WOOL, 1, 1, 16, 5), new SellItemFactory(Blocks.LIGHT_BLUE_WOOL, 1, 1, 16, 5), new SellItemFactory(Blocks.YELLOW_WOOL, 1, 1, 16, 5),
								new SellItemFactory(Blocks.LIME_WOOL, 1, 1, 16, 5), new SellItemFactory(Blocks.PINK_WOOL, 1, 1, 16, 5), new SellItemFactory(Blocks.GRAY_WOOL, 1, 1, 16, 5), new SellItemFactory(Blocks.LIGHT_GRAY_WOOL, 1, 1, 16, 5),
								new SellItemFactory(Blocks.CYAN_WOOL, 1, 1, 16, 5), new SellItemFactory(Blocks.PURPLE_WOOL, 1, 1, 16, 5), new SellItemFactory(Blocks.BLUE_WOOL, 1, 1, 16, 5), new SellItemFactory(Blocks.BROWN_WOOL, 1, 1, 16, 5),
								new SellItemFactory(Blocks.GREEN_WOOL, 1, 1, 16, 5), new SellItemFactory(Blocks.RED_WOOL, 1, 1, 16, 5), new SellItemFactory(Blocks.BLACK_WOOL, 1, 1, 16, 5), new SellItemFactory(Blocks.WHITE_CARPET, 1, 4, 16, 5),
								new SellItemFactory(Blocks.ORANGE_CARPET, 1, 4, 16, 5), new SellItemFactory(Blocks.MAGENTA_CARPET, 1, 4, 16, 5), new SellItemFactory(Blocks.LIGHT_BLUE_CARPET, 1, 4, 16, 5),
								new SellItemFactory(Blocks.YELLOW_CARPET, 1, 4, 16, 5), new SellItemFactory(Blocks.LIME_CARPET, 1, 4, 16, 5), new SellItemFactory(Blocks.PINK_CARPET, 1, 4, 16, 5), new SellItemFactory(Blocks.GRAY_CARPET, 1, 4, 16, 5),
								new SellItemFactory(Blocks.LIGHT_GRAY_CARPET, 1, 4, 16, 5), new SellItemFactory(Blocks.CYAN_CARPET, 1, 4, 16, 5), new SellItemFactory(Blocks.PURPLE_CARPET, 1, 4, 16, 5), new SellItemFactory(Blocks.BLUE_CARPET, 1, 4, 16, 5),
								new SellItemFactory(Blocks.BROWN_CARPET, 1, 4, 16, 5), new SellItemFactory(Blocks.GREEN_CARPET, 1, 4, 16, 5), new SellItemFactory(Blocks.RED_CARPET, 1, 4, 16, 5), new SellItemFactory(Blocks.BLACK_CARPET, 1, 4, 16, 5) },
						3,
						new Factory[] { new BuyForOneEmeraldFactory(Items.YELLOW_DYE, 12, 16, 20), new BuyForOneEmeraldFactory(Items.LIGHT_GRAY_DYE, 12, 16, 20), new BuyForOneEmeraldFactory(Items.ORANGE_DYE, 12, 16, 20),
								new BuyForOneEmeraldFactory(Items.RED_DYE, 12, 16, 20), new BuyForOneEmeraldFactory(Items.PINK_DYE, 12, 16, 20), new SellItemFactory(Blocks.WHITE_BED, 3, 1, 12, 10), new SellItemFactory(Blocks.YELLOW_BED, 3, 1, 12, 10),
								new SellItemFactory(Blocks.RED_BED, 3, 1, 12, 10), new SellItemFactory(Blocks.BLACK_BED, 3, 1, 12, 10), new SellItemFactory(Blocks.BLUE_BED, 3, 1, 12, 10), new SellItemFactory(Blocks.BROWN_BED, 3, 1, 12, 10),
								new SellItemFactory(Blocks.CYAN_BED, 3, 1, 12, 10), new SellItemFactory(Blocks.GRAY_BED, 3, 1, 12, 10), new SellItemFactory(Blocks.GREEN_BED, 3, 1, 12, 10), new SellItemFactory(Blocks.LIGHT_BLUE_BED, 3, 1, 12, 10),
								new SellItemFactory(Blocks.LIGHT_GRAY_BED, 3, 1, 12, 10), new SellItemFactory(Blocks.LIME_BED, 3, 1, 12, 10), new SellItemFactory(Blocks.MAGENTA_BED, 3, 1, 12, 10), new SellItemFactory(Blocks.ORANGE_BED, 3, 1, 12, 10),
								new SellItemFactory(Blocks.PINK_BED, 3, 1, 12, 10), new SellItemFactory(Blocks.PURPLE_BED, 3, 1, 12, 10) },
						4,
						new Factory[] { new BuyForOneEmeraldFactory(Items.BROWN_DYE, 12, 16, 30), new BuyForOneEmeraldFactory(Items.PURPLE_DYE, 12, 16, 30), new BuyForOneEmeraldFactory(Items.BLUE_DYE, 12, 16, 30),
								new BuyForOneEmeraldFactory(Items.GREEN_DYE, 12, 16, 30), new BuyForOneEmeraldFactory(Items.MAGENTA_DYE, 12, 16, 30), new BuyForOneEmeraldFactory(Items.CYAN_DYE, 12, 16, 30),
								new SellItemFactory(ModItems.WHITE_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.BLUE_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.LIGHT_BLUE_FLAG, 3, 1, 12, 25),
								new SellItemFactory(ModItems.RED_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.PINK_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.GREEN_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.LIME_FLAG, 3, 1, 12, 25),
								new SellItemFactory(ModItems.GRAY_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.PURPLE_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.MAGENTA_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.CYAN_FLAG, 3, 1, 12, 25),
								new SellItemFactory(ModItems.BROWN_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.YELLOW_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.ORANGE_FLAG, 3, 1, 12, 25) },
						5, new Factory[] { new SellItemFactory(ModItems.SPACE_PAINTING, 64, 1, 2, 50) })));
		map.put(VillagerProfession.FLETCHER,
				copyToFastUtilMap(ImmutableMap.of(1, new Factory[] { new BuyForOneEmeraldFactory(ModItems.IRON_ROD, 8, 16, 6), new SellItemFactory(Items.ARROW, 1, 16, 1), new ProcessItemFactory(Blocks.GRAVEL, 10, Items.FLINT, 10, 12, 1) }, 2,
						new Factory[] { new BuyForOneEmeraldFactory(Items.FLINT, 26, 12, 10), new SellItemFactory(Items.BOW, 2, 1, 5) }, 3,
						new Factory[] { new BuyForOneEmeraldFactory(Items.STRING, 14, 16, 20), new SellItemFactory(Items.CROSSBOW, 3, 1, 10) }, 4,
						new Factory[] { new BuyForOneEmeraldFactory(Items.FEATHER, 24, 16, 30), new SellEnchantedToolFactory(Items.BOW, 2, 3, 15) }, 5,
						new Factory[] { new BuyForOneEmeraldFactory(Items.TRIPWIRE_HOOK, 8, 12, 30), new SellEnchantedToolFactory(Items.CROSSBOW, 3, 3, 15), new SellPotionHoldingItemFactory(Items.ARROW, 5, Items.TIPPED_ARROW, 5, 2, 12, 30) })));
		map.put(VillagerProfession.LIBRARIAN,
				copyToFastUtilMap(ImmutableMap.of(1, new Factory[] { new BuyForOneEmeraldFactory(Items.PAPER, 24, 16, 2), new EnchantBookFactory(1), new SellItemFactory(Blocks.BOOKSHELF, 9, 1, 12, 1) }, 2,
						new Factory[] { new BuyForOneEmeraldFactory(Items.BOOK, 4, 12, 10), new EnchantBookFactory(5), new SellItemFactory(Items.LANTERN, 1, 1, 5) }, 3,
						new Factory[] { new BuyForOneEmeraldFactory(Items.INK_SAC, 5, 12, 20), new EnchantBookFactory(10), new SellItemFactory(Items.LIGHT_BLUE_STAINED_GLASS, 1, 8, 10) }, 4,
						new Factory[] { new BuyForOneEmeraldFactory(Items.WRITABLE_BOOK, 2, 12, 30), new EnchantBookFactory(15), new SellItemFactory(Items.CLOCK, 5, 1, 15), new SellItemFactory(Items.COMPASS, 4, 1, 15) }, 5,
						new Factory[] { new SellItemFactory(Items.NAME_TAG, 20, 1, 30) })));
		map.put(VillagerProfession.CARTOGRAPHER, copyToFastUtilMap(ImmutableMap.of(1, new Factory[] { new BuyForOneEmeraldFactory(Items.PAPER, 24, 16, 2), new SellItemFactory(Items.MAP, 7, 1, 1) }, 2,
				new Factory[] { new BuyForOneEmeraldFactory(Items.GLASS_PANE, 11, 16, 10) }, 3, new Factory[] { new BuyForOneEmeraldFactory(Items.COMPASS, 1, 12, 20), }, 4,
				new Factory[] { new SellItemFactory(Items.ITEM_FRAME, 7, 1, 15), new SellItemFactory(ModItems.WHITE_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.BLUE_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.LIGHT_BLUE_FLAG, 3, 1, 12, 25),
						new SellItemFactory(ModItems.RED_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.PINK_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.GREEN_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.LIME_FLAG, 3, 1, 12, 25),
						new SellItemFactory(ModItems.GRAY_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.PURPLE_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.MAGENTA_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.CYAN_FLAG, 3, 1, 12, 25),
						new SellItemFactory(ModItems.BROWN_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.YELLOW_FLAG, 3, 1, 12, 25), new SellItemFactory(ModItems.ORANGE_FLAG, 3, 1, 12, 25) },
				5, new Factory[] { new SellItemFactory(Items.GLOBE_BANNER_PATTERN, 6, 1, 45) })));
		map.put(VillagerProfession.CLERIC,
				copyToFastUtilMap(ImmutableMap.of(1, new Factory[] { new BuyForOneEmeraldFactory(Items.ROTTEN_FLESH, 32, 16, 2), new SellItemFactory(Items.REDSTONE, 1, 2, 1) }, 2,
						new Factory[] { new BuyForOneEmeraldFactory(ModItems.DESH_INGOT, 3, 8, 10), new SellItemFactory(Items.LAPIS_LAZULI, 1, 1, 5) }, 3,
						new Factory[] { new BuyForOneEmeraldFactory(Items.RABBIT_FOOT, 2, 12, 20), new SellItemFactory(Blocks.GLOWSTONE, 4, 1, 12, 10) }, 4,
						new Factory[] { new BuyForOneEmeraldFactory(Items.SCUTE, 4, 12, 30), new BuyForOneEmeraldFactory(Items.GLASS_BOTTLE, 9, 12, 30), new SellItemFactory(Items.ENDER_PEARL, 5, 1, 15) }, 5,
						new Factory[] { new BuyForOneEmeraldFactory(Items.NETHER_WART, 22, 12, 30), new SellItemFactory(Items.EXPERIENCE_BOTTLE, 2, 1, 30), new SellItemFactory(ModItems.OXYGEN_BUCKET, 32, 1, 1, 60) })));
		map.put(VillagerProfession.ARMORER,
				copyToFastUtilMap(ImmutableMap.of(1,
						new Factory[] { new BuyForOneEmeraldFactory(ModItems.ICE_SHARD, 12, 16, 2), new SellItemFactory(new ItemStack(ModItems.SPACE_PANTS), 14, 1, 12, 4, 0.2f), new SellItemFactory(new ItemStack(ModItems.SPACE_BOOTS), 8, 1, 12, 4, 0.2f),
								new SellItemFactory(new ItemStack(ModItems.SPACE_HELMET), 10, 1, 12, 4, 0.2f), new SellItemFactory(new ItemStack(ModItems.SPACE_SUIT), 36, 1, 12, 8, 0.2f) },
						2,
						new Factory[] { new BuyForOneEmeraldFactory(ModItems.DESH_INGOT, 4, 8, 10), new SellItemFactory(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2f), new SellItemFactory(new ItemStack(ModItems.SPACE_BOOTS), 8, 1, 12, 4, 0.2f),
								new SellItemFactory(new ItemStack(ModItems.SPACE_PANTS), 14, 1, 12, 4, 0.2f) },
						3,
						new Factory[] { new BuyForOneEmeraldFactory(Items.LAVA_BUCKET, 1, 12, 20), new BuyForOneEmeraldFactory(Items.DIAMOND, 1, 12, 20), new SellItemFactory(new ItemStack(ModItems.SPACE_HELMET), 10, 1, 12, 4, 0.2f),
								new SellItemFactory(new ItemStack(ModItems.SPACE_SUIT), 36, 1, 12, 8, 0.2f), new SellItemFactory(new ItemStack(Items.SHIELD), 5, 1, 12, 10, 0.2f) },
						4, new Factory[] { new SellEnchantedToolFactory(ModItems.SPACE_PANTS, 28, 3, 15, 0.2f), new SellEnchantedToolFactory(ModItems.SPACE_BOOTS, 16, 3, 15, 0.2f) }, 5,
						new Factory[] { new SellEnchantedToolFactory(ModItems.SPACE_HELMET, 16, 3, 30, 0.2f), new SellEnchantedToolFactory(ModItems.SPACE_SUIT, 48, 3, 30, 0.2f), new SellItemFactory(OxygenTankItem.createOxygenatedTank(), 24, 1, 2, 40),
								new SellItemFactory(ModItems.OXYGEN_BUCKET, 32, 1, 1, 60), new SellItemFactory(ModItems.OXYGEN_LOADER, 48, 1, 1, 60), new SellItemFactory(ModItems.COAL_GENERATOR, 32, 1, 1, 60) })));
		map.put(VillagerProfession.WEAPONSMITH,
				copyToFastUtilMap(ImmutableMap.of(1,
						new Factory[] { new BuyForOneEmeraldFactory(ModItems.ICE_SHARD, 12, 16, 2), new SellItemFactory(new ItemStack(ModItems.HAMMER), 3, 1, 8, 2, 0.2f), new SellItemFactory(new ItemStack(Items.IRON_AXE), 3, 1, 12, 1, 0.2f),
								new SellEnchantedToolFactory(Items.IRON_SWORD, 2, 3, 1) },
						2, new Factory[] { new BuyForOneEmeraldFactory(ModItems.DESH_INGOT, 4, 12, 10), new SellItemFactory(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2f) }, 3, new Factory[] { new BuyForOneEmeraldFactory(Items.FLINT, 24, 12, 20) }, 4,
						new Factory[] { new BuyForOneEmeraldFactory(Items.DIAMOND, 1, 12, 30), new SellEnchantedToolFactory(Items.DIAMOND_AXE, 12, 3, 15, 0.2f) }, 5, new Factory[] { new SellEnchantedToolFactory(Items.DIAMOND_SWORD, 8, 3, 30, 0.2f),
								new SellItemFactory(OxygenTankItem.createOxygenatedTank(), 24, 1, 2, 40), new SellItemFactory(ModItems.OXYGEN_BUCKET, 32, 1, 1, 60), new SellItemFactory(ModItems.OXYGEN_LOADER, 48, 3, 1, 60) })));
		map.put(VillagerProfession.TOOLSMITH,
				copyToFastUtilMap(ImmutableMap.of(1,
						new Factory[] { new BuyForOneEmeraldFactory(ModItems.ICE_SHARD, 12, 16, 2), new SellItemFactory(new ItemStack(Items.SOUL_TORCH), 3, 16, 8, 1, 0.2f), new SellItemFactory(new ItemStack(ModItems.HAMMER), 3, 1, 8, 2, 0.2f),
								new SellItemFactory(ModItems.WRENCH, 14, 1, 2, 2), new SellItemFactory(new ItemStack(Items.STONE_AXE), 1, 1, 12, 1, 0.2f), new SellItemFactory(new ItemStack(Items.STONE_SHOVEL), 1, 1, 12, 1, 0.2f),
								new SellItemFactory(new ItemStack(Items.STONE_PICKAXE), 1, 1, 12, 1, 0.2f), new SellItemFactory(new ItemStack(Items.STONE_HOE), 1, 1, 12, 1, 0.2f) },
						2, new Factory[] { new BuyForOneEmeraldFactory(ModItems.DESH_INGOT, 4, 12, 10), new SellItemFactory(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2f) }, 3,
						new Factory[] { new BuyForOneEmeraldFactory(Items.FLINT, 30, 12, 20), new SellEnchantedToolFactory(Items.IRON_AXE, 1, 3, 10, 0.2f), new SellEnchantedToolFactory(Items.IRON_SHOVEL, 2, 3, 10, 0.2f),
								new SellEnchantedToolFactory(Items.IRON_PICKAXE, 3, 3, 10, 0.2f), new SellItemFactory(new ItemStack(Items.DIAMOND_HOE), 4, 1, 3, 10, 0.2f) },
						4, new Factory[] { new BuyForOneEmeraldFactory(Items.DIAMOND, 1, 12, 30), new SellEnchantedToolFactory(Items.DIAMOND_AXE, 12, 3, 15, 0.2f), new SellEnchantedToolFactory(Items.DIAMOND_SHOVEL, 5, 3, 15, 0.2f) }, 5,
						new Factory[] { new SellEnchantedToolFactory(Items.DIAMOND_PICKAXE, 13, 3, 30, 0.2f), new SellItemFactory(OxygenTankItem.createOxygenatedTank(), 24, 1, 2, 40), new SellItemFactory(ModItems.OXYGEN_BUCKET, 32, 1, 1, 60),
								new SellItemFactory(ModItems.OXYGEN_LOADER, 48, 1, 1, 60), new SellItemFactory(ModItems.COAL_GENERATOR, 32, 1, 1, 60) })));
		map.put(VillagerProfession.BUTCHER,
				copyToFastUtilMap(ImmutableMap.of(1,
						new Factory[] { new BuyForOneEmeraldFactory(ModItems.CHEESE, 20, 20, 5), new BuyForOneEmeraldFactory(Items.CHICKEN, 14, 16, 2), new BuyForOneEmeraldFactory(Items.PORKCHOP, 7, 16, 2),
								new BuyForOneEmeraldFactory(Items.RABBIT, 4, 16, 2), new SellItemFactory(Items.RABBIT_STEW, 1, 1, 1) },
						2, new Factory[] { new BuyForOneEmeraldFactory(Items.COAL, 15, 16, 2), new SellItemFactory(Items.COOKED_PORKCHOP, 1, 5, 16, 5), new SellItemFactory(Items.COOKED_CHICKEN, 1, 8, 16, 5) }, 3,
						new Factory[] { new BuyForOneEmeraldFactory(Items.MUTTON, 7, 16, 20), new BuyForOneEmeraldFactory(Items.BEEF, 10, 16, 20) }, 4, new Factory[] { new BuyForOneEmeraldFactory(Items.DRIED_KELP_BLOCK, 10, 12, 30) }, 5,
						new Factory[] { new BuyForOneEmeraldFactory(Items.SWEET_BERRIES, 10, 12, 30) })));
		map.put(VillagerProfession.LEATHERWORKER,
				copyToFastUtilMap(ImmutableMap.of(1, new Factory[] { new BuyForOneEmeraldFactory(Items.LEATHER, 6, 16, 2), new SellDyedArmorFactory(Items.LEATHER_LEGGINGS, 3), new SellDyedArmorFactory(Items.LEATHER_CHESTPLATE, 7) }, 2,
						new Factory[] { new BuyForOneEmeraldFactory(Items.FLINT, 26, 12, 10), new SellDyedArmorFactory(Items.LEATHER_HELMET, 5, 12, 5), new SellDyedArmorFactory(Items.LEATHER_BOOTS, 4, 12, 5) }, 3,
						new Factory[] { new BuyForOneEmeraldFactory(Items.RABBIT_HIDE, 9, 12, 20), new SellDyedArmorFactory(Items.LEATHER_CHESTPLATE, 7) }, 4,
						new Factory[] { new BuyForOneEmeraldFactory(Items.SCUTE, 4, 12, 30), new SellDyedArmorFactory(Items.LEATHER_HORSE_ARMOR, 6, 12, 15) }, 5,
						new Factory[] { new SellItemFactory(new ItemStack(Items.SADDLE), 6, 1, 12, 30, 0.2f), new SellDyedArmorFactory(Items.LEATHER_HELMET, 5, 12, 30) })));
		map.put(VillagerProfession.MASON,
				copyToFastUtilMap(ImmutableMap.of(1, new Factory[] { new BuyForOneEmeraldFactory(ModItems.CONGLOMERATE, 10, 16, 2), new BuyForOneEmeraldFactory(ModItems.MOON_SAND, 32, 32, 1), new SellItemFactory(ModItems.MOON_STONE_BRICKS, 1, 10, 16, 1) },
						2, new Factory[] { new BuyForOneEmeraldFactory(ModItems.MOON_STONE, 20, 16, 10), new SellItemFactory(ModItems.CHISELED_MOON_STONE_BRICKS, 1, 4, 16, 5) }, 3,
						new Factory[] { new BuyForOneEmeraldFactory(ModItems.MARS_STONE, 16, 16, 20), new BuyForOneEmeraldFactory(ModItems.VENUS_STONE, 16, 16, 20), new BuyForOneEmeraldFactory(ModItems.MERCURY_STONE, 16, 16, 20),
								new SellItemFactory(Blocks.DRIPSTONE_BLOCK, 1, 4, 16, 10), new SellItemFactory(ModItems.POLISHED_MARS_STONE, 1, 4, 16, 10), new SellItemFactory(ModItems.POLISHED_VENUS_STONE, 1, 4, 16, 10),
								new SellItemFactory(ModItems.POLISHED_MERCURY_STONE, 1, 4, 16, 10) },
						4,
						new Factory[] { new BuyForOneEmeraldFactory(Items.QUARTZ, 12, 12, 30), new SellItemFactory(Blocks.ORANGE_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.WHITE_TERRACOTTA, 1, 1, 12, 15),
								new SellItemFactory(Blocks.BLUE_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.LIGHT_BLUE_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.GRAY_TERRACOTTA, 1, 1, 12, 15),
								new SellItemFactory(Blocks.LIGHT_GRAY_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.BLACK_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.RED_TERRACOTTA, 1, 1, 12, 15),
								new SellItemFactory(Blocks.PINK_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.MAGENTA_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.LIME_TERRACOTTA, 1, 1, 12, 15),
								new SellItemFactory(Blocks.GREEN_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.CYAN_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.PURPLE_TERRACOTTA, 1, 1, 12, 15),
								new SellItemFactory(Blocks.YELLOW_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.BROWN_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.ORANGE_GLAZED_TERRACOTTA, 1, 1, 12, 15),
								new SellItemFactory(Blocks.WHITE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15),
								new SellItemFactory(Blocks.GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.BLACK_GLAZED_TERRACOTTA, 1, 1, 12, 15),
								new SellItemFactory(Blocks.RED_GLAZED_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.PINK_GLAZED_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.MAGENTA_GLAZED_TERRACOTTA, 1, 1, 12, 15),
								new SellItemFactory(Blocks.LIME_GLAZED_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.GREEN_GLAZED_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.CYAN_GLAZED_TERRACOTTA, 1, 1, 12, 15),
								new SellItemFactory(Blocks.PURPLE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.YELLOW_GLAZED_TERRACOTTA, 1, 1, 12, 15), new SellItemFactory(Blocks.BROWN_GLAZED_TERRACOTTA, 1, 1, 12, 15) },
						5, new Factory[] { new SellItemFactory(ModItems.STEEL_PLATING, 1, 8, 12, 15), new SellItemFactory(ModItems.DESH_PLATING, 1, 8, 12, 15), new SellItemFactory(ModItems.OSTRUM_PLATING, 1, 4, 12, 15),
								new SellItemFactory(ModItems.STEEL_DOOR, 1, 3, 12, 15), new SellItemFactory(Blocks.QUARTZ_PILLAR, 1, 1, 12, 30), new SellItemFactory(Blocks.QUARTZ_BLOCK, 1, 1, 12, 30) })));
	});

	public static final Int2ObjectMap<Factory[]> WANDERING_TRADER_TRADES = copyToFastUtilMap(ImmutableMap.of(1,
			new Factory[] { new SellItemFactory(ModItems.CHEESE, 1, 3, 6, 1), new SellItemFactory(ModItems.SPACE_PAINTING, 48, 1, 2, 2), new SellItemFactory(ModItems.OXYGEN_TANK, 10, 1, 3, 1), new SellItemFactory(Items.SOUL_TORCH, 1, 12, 12, 1),
					new SellItemFactory(Items.SOUL_CAMPFIRE, 3, 1, 4, 1), new SellItemFactory(ModItems.WRENCH, 12, 1, 2, 2), new SellItemFactory(ModItems.HAMMER, 3, 1, 4, 1), new SellItemFactory(ModItems.WHITE_FLAG, 10, 1, 3, 2),
					new SellItemFactory(ModItems.SPACE_HELMET, 10, 1, 2, 1), new SellItemFactory(ModItems.SPACE_SUIT, 16, 1, 2, 1), new SellItemFactory(ModItems.SPACE_PANTS, 14, 1, 2, 1), new SellItemFactory(ModItems.SPACE_BOOTS, 8, 1, 2, 1),
					new SellItemFactory(Items.COAL, 1, 5, 4, 1), new SellItemFactory(ModItems.OXYGEN_BUCKET, 10, 1, 2, 2), new SellItemFactory(Items.COPPER_INGOT, 1, 4, 12, 1), new SellItemFactory(Items.WATER_BUCKET, 5, 1, 4, 1),
					new SellItemFactory(Items.LAVA_BUCKET, 3, 1, 4, 1), new BuyForOneEmeraldFactory(ModItems.DESH_INGOT, 4, 20, 1), new BuyForOneEmeraldFactory(ModItems.OSTRUM_INGOT, 4, 20, 1),
					new BuyForOneEmeraldFactory(ModItems.CALORITE_INGOT, 4, 20, 1) },
			2, new Factory[] { new SellItemFactory(OxygenTankItem.createOxygenatedTank(), 16, 1, 3, 2), new SellItemFactory(ModItems.ROCKET_LAUNCH_PAD, 3, 9, 3, 1), new SellItemFactory(ModItems.ROCKET_LAUNCH_PAD, 3, 9, 3, 1),
					new SellItemFactory(ModItems.FUEL_BUCKET, 4, 1, 6, 1) }));

	private static Int2ObjectMap<Factory[]> copyToFastUtilMap(ImmutableMap<Integer, Factory[]> map) {
		return new Int2ObjectOpenHashMap<Factory[]>(map);
	}

	static class BuyForOneEmeraldFactory implements Factory {
		private final Item buy;
		private final int price;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public BuyForOneEmeraldFactory(ItemConvertible item, int price, int maxUses, int experience) {
			this.buy = item.asItem();
			this.price = price;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = 0.05f;
		}

		@Override
		public TradeOffer create(Entity entity, RandomGenerator random) {
			ItemStack itemStack = new ItemStack(this.buy, this.price);
			return new TradeOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
		}
	}

	static class SellItemFactory implements Factory {
		private final ItemStack sell;
		private final int price;
		private final int count;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public SellItemFactory(Block block, int price, int count, int maxUses, int experience) {
			this(new ItemStack(block), price, count, maxUses, experience);
		}

		public SellItemFactory(Item item, int price, int count, int experience) {
			this(new ItemStack(item), price, count, 12, experience);
		}

		public SellItemFactory(Item item, int price, int count, int maxUses, int experience) {
			this(new ItemStack(item), price, count, maxUses, experience);
		}

		public SellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience) {
			this(stack, price, count, maxUses, experience, 0.05f);
		}

		public SellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience, float multiplier) {
			this.sell = stack;
			this.price = price;
			this.count = count;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = multiplier;
		}

		@Override
		public TradeOffer create(Entity entity, RandomGenerator random) {
			ItemStack stack = new ItemStack(this.sell.getItem(), this.count);
			stack.setNbt((this.sell.getNbt()));
			return new TradeOffer(new ItemStack(Items.EMERALD, this.price), stack, this.maxUses, this.experience, this.multiplier);
		}
	}

	static class SellSuspiciousStewFactory implements Factory {
		final StatusEffect effect;
		final int duration;
		final int experience;
		private final float multiplier;

		public SellSuspiciousStewFactory(StatusEffect effect, int duration, int experience) {
			this.effect = effect;
			this.duration = duration;
			this.experience = experience;
			this.multiplier = 0.05f;
		}

		@Override
		@Nullable
		public TradeOffer create(Entity entity, RandomGenerator random) {
			ItemStack itemStack = new ItemStack(Items.SUSPICIOUS_STEW, 1);
			SuspiciousStewItem.addEffectToStew(itemStack, this.effect, this.duration);
			return new TradeOffer(new ItemStack(Items.EMERALD, 1), itemStack, 12, this.experience, this.multiplier);
		}
	}

	static class ProcessItemFactory implements Factory {
		private final ItemStack secondBuy;
		private final int secondCount;
		private final int price;
		private final ItemStack sell;
		private final int sellCount;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public ProcessItemFactory(ItemConvertible item, int secondCount, Item sellItem, int sellCount, int maxUses, int experience) {
			this(item, secondCount, 1, sellItem, sellCount, maxUses, experience);
		}

		public ProcessItemFactory(ItemConvertible item, int secondCount, int price, Item sellItem, int sellCount, int maxUses, int experience) {
			this.secondBuy = new ItemStack(item);
			this.secondCount = secondCount;
			this.price = price;
			this.sell = new ItemStack(sellItem);
			this.sellCount = sellCount;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = 0.05f;
		}

		@Override
		@Nullable
		public TradeOffer create(Entity entity, RandomGenerator random) {
			return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.secondBuy.getItem(), this.secondCount), new ItemStack(this.sell.getItem(), this.sellCount), this.maxUses, this.experience, this.multiplier);
		}
	}

	static class SellEnchantedToolFactory implements Factory {
		private final ItemStack tool;
		private final int basePrice;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public SellEnchantedToolFactory(Item item, int basePrice, int maxUses, int experience) {
			this(item, basePrice, maxUses, experience, 0.05f);
		}

		public SellEnchantedToolFactory(Item item, int basePrice, int maxUses, int experience, float multiplier) {
			this.tool = new ItemStack(item);
			this.basePrice = basePrice;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = multiplier;
		}

		@Override
		public TradeOffer create(Entity entity, RandomGenerator random) {
			int i = 5 + random.nextInt(15);
			ItemStack itemStack = EnchantmentHelper.enchant(random, new ItemStack(this.tool.getItem()), i, false);
			int j = Math.min(this.basePrice + i, 64);
			ItemStack itemStack2 = new ItemStack(Items.EMERALD, j);
			return new TradeOffer(itemStack2, itemStack, this.maxUses, this.experience, this.multiplier);
		}
	}

	static class TypeAwareBuyForOneEmeraldFactory implements Factory {
		private final Map<VillagerType, Item> map;
		private final int count;
		private final int maxUses;
		private final int experience;

		public TypeAwareBuyForOneEmeraldFactory(int count, int maxUses, int experience, Map<VillagerType, Item> map) {
			Registry.VILLAGER_TYPE.stream().filter(villagerType -> !map.containsKey(villagerType)).findAny().ifPresent(villagerType -> {
				throw new IllegalStateException("Missing trade for villager type: " + Registry.VILLAGER_TYPE.getId(villagerType));
			});
			this.map = map;
			this.count = count;
			this.maxUses = maxUses;
			this.experience = experience;
		}

		@Override
		@Nullable
		public TradeOffer create(Entity entity, RandomGenerator random) {
			if (entity instanceof VillagerDataContainer) {
				ItemStack itemStack = new ItemStack(this.map.get(((VillagerDataContainer) entity).getVillagerData().getType()), this.count);
				return new TradeOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.experience, 0.05f);
			}
			return null;
		}
	}

	static class SellPotionHoldingItemFactory implements Factory {
		private final ItemStack sell;
		private final int sellCount;
		private final int price;
		private final int maxUses;
		private final int experience;
		private final Item secondBuy;
		private final int secondCount;
		private final float priceMultiplier;

		public SellPotionHoldingItemFactory(Item arrow, int secondCount, Item tippedArrow, int sellCount, int price, int maxUses, int experience) {
			this.sell = new ItemStack(tippedArrow);
			this.price = price;
			this.maxUses = maxUses;
			this.experience = experience;
			this.secondBuy = arrow;
			this.secondCount = secondCount;
			this.sellCount = sellCount;
			this.priceMultiplier = 0.05f;
		}

		@Override
		public TradeOffer create(Entity entity, RandomGenerator random) {
			ItemStack itemStack = new ItemStack(Items.EMERALD, this.price);
			List<Potion> list = Registry.POTION.stream().filter(potion -> !potion.getEffects().isEmpty() && BrewingRecipeRegistry.isBrewable(potion)).collect(Collectors.toList());
			Potion potion2 = list.get(random.nextInt(list.size()));
			ItemStack itemStack2 = PotionUtil.setPotion(new ItemStack(this.sell.getItem(), this.sellCount), potion2);
			return new TradeOffer(itemStack, new ItemStack(this.secondBuy, this.secondCount), itemStack2, this.maxUses, this.experience, this.priceMultiplier);
		}
	}

	static class EnchantBookFactory implements Factory {
		private final int experience;

		public EnchantBookFactory(int experience) {
			this.experience = experience;
		}

		@Override
		public TradeOffer create(Entity entity, RandomGenerator random) {
			List<Enchantment> list = Registry.ENCHANTMENT.stream().filter(Enchantment::isAvailableForEnchantedBookOffer).collect(Collectors.toList());
			Enchantment enchantment = list.get(random.nextInt(list.size()));
			int i = MathHelper.nextInt(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
			ItemStack itemStack = EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(enchantment, i));
			int j = 2 + random.nextInt(5 + i * 10) + 3 * i;
			if (enchantment.isTreasure()) {
				j *= 2;
			}
			if (j > 64) {
				j = 64;
			}
			return new TradeOffer(new ItemStack(Items.EMERALD, j), new ItemStack(Items.BOOK), itemStack, 12, this.experience, 0.2f);
		}
	}

	static class SellDyedArmorFactory implements Factory {
		private final Item sell;
		private final int price;
		private final int maxUses;
		private final int experience;

		public SellDyedArmorFactory(Item item, int price) {
			this(item, price, 12, 1);
		}

		public SellDyedArmorFactory(Item item, int price, int maxUses, int experience) {
			this.sell = item;
			this.price = price;
			this.maxUses = maxUses;
			this.experience = experience;
		}

		@Override
		public TradeOffer create(Entity entity, RandomGenerator random) {
			ItemStack itemStack = new ItemStack(Items.EMERALD, this.price);
			ItemStack itemStack2 = new ItemStack(this.sell);
			if (this.sell instanceof DyeableArmorItem) {
				ArrayList<DyeItem> list = Lists.newArrayList();
				list.add(SellDyedArmorFactory.getDye(random));
				if (random.nextFloat() > 0.7f) {
					list.add(SellDyedArmorFactory.getDye(random));
				}
				if (random.nextFloat() > 0.8f) {
					list.add(SellDyedArmorFactory.getDye(random));
				}
				itemStack2 = DyeableItem.blendAndSetColor(itemStack2, list);
			}
			return new TradeOffer(itemStack, itemStack2, this.maxUses, this.experience, 0.2f);
		}

		private static DyeItem getDye(RandomGenerator random) {
			return DyeItem.byColor(DyeColor.byId(random.nextInt(16)));
		}
	}
}
