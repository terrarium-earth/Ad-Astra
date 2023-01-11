package earth.terrarium.ad_astra.common.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import earth.terrarium.ad_astra.common.item.OxygenTankItem;
import earth.terrarium.ad_astra.common.registry.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class LunarianMerchantOffer {
    public static final Map<VillagerProfession, Int2ObjectMap<ItemListing[]>> PROFESSION_TO_LEVELED_TRADE = Util.make(Maps.newHashMap(), map -> {
        map.put(VillagerProfession.FARMER, copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.CHEESE.get(), 20, 20, 5), new BuyForOneEmeraldFactory(Items.GLOW_BERRIES, 22, 16, 3), new ItemsForEmeralds(Items.BREAD, 1, 6, 16, 1)}, 2, new ItemListing[]{new ItemsForEmeralds(Items.PUMPKIN_PIE, 1, 4, 5), new ItemsForEmeralds(Items.GOLDEN_APPLE, 8, 1, 8, 20)}, 3, new ItemListing[]{new ItemsForEmeralds(Items.COOKIE, 3, 18, 10), new BuyForOneEmeraldFactory(Blocks.MELON, 4, 12, 20)}, 4, new ItemListing[]{new ItemsForEmeralds(Blocks.CAKE, 1, 1, 12, 15), new BuyForOneEmeraldFactory(Items.DIRT, 63, 16, 2), new SuspiciousStewForEmerald(MobEffects.NIGHT_VISION, 100, 15), new SuspiciousStewForEmerald(MobEffects.JUMP, 160, 15), new SuspiciousStewForEmerald(MobEffects.WEAKNESS, 140, 15), new SuspiciousStewForEmerald(MobEffects.BLINDNESS, 120, 15), new SuspiciousStewForEmerald(MobEffects.POISON, 280, 15), new SuspiciousStewForEmerald(MobEffects.SATURATION, 7, 15)},
                5, new ItemListing[]{new ItemsForEmeralds(Items.GOLDEN_CARROT, 3, 3, 30), new ItemsForEmeralds(Items.GLISTERING_MELON_SLICE, 4, 3, 30)})));
        map.put(VillagerProfession.FISHERMAN, copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.CHEESE.get(), 20, 20, 5), new BuyForOneEmeraldFactory(Items.STRING, 20, 16, 2), new BuyForOneEmeraldFactory(Items.COAL, 10, 16, 2), new ItemsAndEmeraldsToItems(Items.COD, 6, Items.COOKED_COD, 6, 16, 1),}, 2, new ItemListing[]{new BuyForOneEmeraldFactory(Items.COD, 15, 16, 10), new ItemsAndEmeraldsToItems(Items.SALMON, 6, Items.COOKED_SALMON, 6, 16, 5), new ItemsForEmeralds(Items.SOUL_CAMPFIRE, 2, 1, 5)}, 3, new ItemListing[]{new BuyForOneEmeraldFactory(Items.SALMON, 13, 16, 20), new EnchantedItemForEmeralds(Items.FISHING_ROD, 3, 3, 10, 0.2f)}, 4, new ItemListing[]{new BuyForOneEmeraldFactory(Items.TROPICAL_FISH, 6, 12, 30)}, 5, new ItemListing[]{new BuyForOneEmeraldFactory(Items.PUFFERFISH, 4, 12, 30), new ItemsForEmeralds(new ItemStack(Items.WATER_BUCKET), 8, 1, 2, 9, 0.2f), new ItemsForEmeralds(new ItemStack(Items.ICE), 4, 1, 8, 9, 0.2f)})));
        map.put(VillagerProfession.SHEPHERD,
                copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(Blocks.WHITE_WOOL, 18, 16, 2), new BuyForOneEmeraldFactory(Blocks.BROWN_WOOL, 18, 16, 2), new BuyForOneEmeraldFactory(Blocks.BLACK_WOOL, 18, 16, 2), new BuyForOneEmeraldFactory(Blocks.GRAY_WOOL, 18, 16, 2), new ItemsForEmeralds(Items.SHEARS, 2, 1, 1)}, 2,
                        new ItemListing[]{new BuyForOneEmeraldFactory(Items.WHITE_DYE, 12, 16, 10), new BuyForOneEmeraldFactory(Items.GRAY_DYE, 12, 16, 10), new BuyForOneEmeraldFactory(Items.BLACK_DYE, 12, 16, 10), new BuyForOneEmeraldFactory(Items.LIGHT_BLUE_DYE, 12, 16, 10), new BuyForOneEmeraldFactory(Items.LIME_DYE, 12, 16, 10), new ItemsForEmeralds(Blocks.WHITE_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.ORANGE_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.MAGENTA_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.LIGHT_BLUE_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.YELLOW_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.LIME_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.PINK_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.GRAY_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.LIGHT_GRAY_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.CYAN_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.PURPLE_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.BLUE_WOOL, 1, 1, 16, 5),
                                new ItemsForEmeralds(Blocks.BROWN_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.GREEN_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.RED_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.BLACK_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.WHITE_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.ORANGE_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.MAGENTA_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.LIGHT_BLUE_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.YELLOW_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.LIME_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.PINK_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.GRAY_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.LIGHT_GRAY_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.CYAN_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.PURPLE_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.BLUE_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.BROWN_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.GREEN_CARPET, 1, 4, 16, 5),
                                new ItemsForEmeralds(Blocks.RED_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.BLACK_CARPET, 1, 4, 16, 5)},
                        3,
                        new ItemListing[]{new BuyForOneEmeraldFactory(Items.YELLOW_DYE, 12, 16, 20), new BuyForOneEmeraldFactory(Items.LIGHT_GRAY_DYE, 12, 16, 20), new BuyForOneEmeraldFactory(Items.ORANGE_DYE, 12, 16, 20), new BuyForOneEmeraldFactory(Items.RED_DYE, 12, 16, 20), new BuyForOneEmeraldFactory(Items.PINK_DYE, 12, 16, 20), new ItemsForEmeralds(Blocks.WHITE_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.YELLOW_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.RED_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.BLACK_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.BLUE_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.BROWN_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.CYAN_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.GRAY_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.GREEN_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.LIGHT_BLUE_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.LIGHT_GRAY_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.LIME_BED, 3, 1, 12, 10),
                                new ItemsForEmeralds(Blocks.MAGENTA_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.ORANGE_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.PINK_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.PURPLE_BED, 3, 1, 12, 10)},
                        4, new ItemListing[]{new BuyForOneEmeraldFactory(Items.BROWN_DYE, 12, 16, 30), new BuyForOneEmeraldFactory(Items.PURPLE_DYE, 12, 16, 30), new BuyForOneEmeraldFactory(Items.BLUE_DYE, 12, 16, 30), new BuyForOneEmeraldFactory(Items.GREEN_DYE, 12, 16, 30), new BuyForOneEmeraldFactory(Items.MAGENTA_DYE, 12, 16, 30), new BuyForOneEmeraldFactory(Items.CYAN_DYE, 12, 16, 30), new ItemsForEmeralds(ModItems.WHITE_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.BLUE_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.LIGHT_BLUE_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.RED_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.PINK_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.GREEN_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.LIME_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.GRAY_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.PURPLE_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.MAGENTA_FLAG.get(), 3, 1, 12, 25),
                                new ItemsForEmeralds(ModItems.CYAN_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.BROWN_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.YELLOW_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.ORANGE_FLAG.get(), 3, 1, 12, 25)},
                        5, new ItemListing[]{new ItemsForEmeralds(ModItems.SPACE_PAINTING.get(), 64, 1, 2, 50)})));
        map.put(VillagerProfession.FLETCHER, copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.IRON_ROD.get(), 8, 16, 6), new ItemsForEmeralds(Items.ARROW, 1, 16, 1), new ItemsAndEmeraldsToItems(Blocks.GRAVEL, 10, Items.FLINT, 10, 12, 1)}, 2, new ItemListing[]{new BuyForOneEmeraldFactory(Items.FLINT, 26, 12, 10), new ItemsForEmeralds(Items.BOW, 2, 1, 5)}, 3, new ItemListing[]{new BuyForOneEmeraldFactory(Items.STRING, 14, 16, 20), new ItemsForEmeralds(Items.CROSSBOW, 3, 1, 10)}, 4, new ItemListing[]{new BuyForOneEmeraldFactory(Items.FEATHER, 24, 16, 30), new EnchantedItemForEmeralds(Items.BOW, 2, 3, 15)}, 5, new ItemListing[]{new BuyForOneEmeraldFactory(Items.TRIPWIRE_HOOK, 8, 12, 30), new EnchantedItemForEmeralds(Items.CROSSBOW, 3, 3, 15), new TippedArrowForItemsAndEmeralds(Items.ARROW, 5, Items.TIPPED_ARROW, 5, 2, 12, 30)})));
        map.put(VillagerProfession.LIBRARIAN, copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(Items.PAPER, 24, 16, 2), new EnchantBookForEmeralds(1), new ItemsForEmeralds(Blocks.BOOKSHELF, 9, 1, 12, 1)}, 2, new ItemListing[]{new BuyForOneEmeraldFactory(Items.BOOK, 4, 12, 10), new EnchantBookForEmeralds(5), new ItemsForEmeralds(Items.LANTERN, 1, 1, 5)}, 3, new ItemListing[]{new BuyForOneEmeraldFactory(Items.INK_SAC, 5, 12, 20), new EnchantBookForEmeralds(10), new ItemsForEmeralds(Items.LIGHT_BLUE_STAINED_GLASS, 1, 8, 10)}, 4, new ItemListing[]{new BuyForOneEmeraldFactory(Items.WRITABLE_BOOK, 2, 12, 30), new EnchantBookForEmeralds(15), new ItemsForEmeralds(Items.CLOCK, 5, 1, 15), new ItemsForEmeralds(Items.COMPASS, 4, 1, 15)}, 5, new ItemListing[]{new ItemsForEmeralds(Items.NAME_TAG, 20, 1, 30)})));
        map.put(VillagerProfession.CARTOGRAPHER,
                copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(Items.PAPER, 24, 16, 2), new ItemsForEmeralds(Items.MAP, 7, 1, 1)}, 2, new ItemListing[]{new BuyForOneEmeraldFactory(Items.GLASS_PANE, 11, 16, 10)}, 3, new ItemListing[]{new BuyForOneEmeraldFactory(Items.COMPASS, 1, 12, 20),}, 4,
                        new ItemListing[]{new ItemsForEmeralds(Items.ITEM_FRAME, 7, 1, 15), new ItemsForEmeralds(ModItems.WHITE_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.BLUE_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.LIGHT_BLUE_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.RED_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.PINK_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.GREEN_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.LIME_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.GRAY_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.PURPLE_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.MAGENTA_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.CYAN_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.BROWN_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.YELLOW_FLAG.get(), 3, 1, 12, 25), new ItemsForEmeralds(ModItems.ORANGE_FLAG.get(), 3, 1, 12, 25)}, 5,
                        new ItemListing[]{new ItemsForEmeralds(Items.GLOBE_BANNER_PATTERN, 6, 1, 45)})));
        map.put(VillagerProfession.CLERIC, copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(Items.ROTTEN_FLESH, 32, 16, 2), new ItemsForEmeralds(Items.REDSTONE, 1, 2, 1)}, 2, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.DESH_INGOT.get(), 3, 8, 10), new ItemsForEmeralds(Items.LAPIS_LAZULI, 1, 1, 5)}, 3, new ItemListing[]{new BuyForOneEmeraldFactory(Items.RABBIT_FOOT, 2, 12, 20), new ItemsForEmeralds(Blocks.GLOWSTONE, 4, 1, 12, 10)}, 4, new ItemListing[]{new BuyForOneEmeraldFactory(Items.SCUTE, 4, 12, 30), new BuyForOneEmeraldFactory(Items.GLASS_BOTTLE, 9, 12, 30), new ItemsForEmeralds(Items.ENDER_PEARL, 5, 1, 15)}, 5, new ItemListing[]{new BuyForOneEmeraldFactory(Items.NETHER_WART, 22, 12, 30), new ItemsForEmeralds(Items.EXPERIENCE_BOTTLE, 2, 1, 30), new ItemsForEmeralds(ModItems.OXYGEN_BUCKET.get(), 32, 1, 1, 60)})));
        map.put(VillagerProfession.ARMORER, copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.ICE_SHARD.get(), 12, 16, 2), new ItemsForEmeralds(new ItemStack(ModItems.SPACE_PANTS.get()), 14, 1, 12, 4, 0.2f), new ItemsForEmeralds(new ItemStack(ModItems.SPACE_BOOTS.get()), 8, 1, 12, 4, 0.2f), new ItemsForEmeralds(new ItemStack(ModItems.SPACE_HELMET.get()), 10, 1, 12, 4, 0.2f), new ItemsForEmeralds(new ItemStack(ModItems.SPACE_SUIT.get()), 36, 1, 12, 8, 0.2f)}, 2, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.DESH_INGOT.get(), 4, 8, 10), new ItemsForEmeralds(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2f), new ItemsForEmeralds(new ItemStack(ModItems.SPACE_BOOTS.get()), 8, 1, 12, 4, 0.2f), new ItemsForEmeralds(new ItemStack(ModItems.SPACE_PANTS.get()), 14, 1, 12, 4, 0.2f)}, 3,
                new ItemListing[]{new BuyForOneEmeraldFactory(Items.LAVA_BUCKET, 1, 12, 20), new BuyForOneEmeraldFactory(Items.DIAMOND, 1, 12, 20), new ItemsForEmeralds(new ItemStack(ModItems.SPACE_HELMET.get()), 10, 1, 12, 4, 0.2f), new ItemsForEmeralds(new ItemStack(ModItems.SPACE_SUIT.get()), 36, 1, 12, 8, 0.2f), new ItemsForEmeralds(new ItemStack(Items.SHIELD), 5, 1, 12, 10, 0.2f)}, 4, new ItemListing[]{new EnchantedItemForEmeralds(ModItems.SPACE_PANTS.get(), 28, 3, 15, 0.2f), new EnchantedItemForEmeralds(ModItems.SPACE_BOOTS.get(), 16, 3, 15, 0.2f)}, 5, new ItemListing[]{new EnchantedItemForEmeralds(ModItems.SPACE_HELMET.get(), 16, 3, 30, 0.2f), new EnchantedItemForEmeralds(ModItems.SPACE_SUIT.get(), 48, 3, 30, 0.2f), new ItemsForEmeralds(OxygenTankItem.createOxygenatedTank(), 24, 1, 2, 40), new ItemsForEmeralds(ModItems.OXYGEN_BUCKET.get(), 32, 1, 1, 60), new ItemsForEmeralds(ModItems.OXYGEN_LOADER.get(), 48, 1, 1, 60), new ItemsForEmeralds(ModItems.COAL_GENERATOR.get(), 32, 1, 1, 60)})));
        map.put(VillagerProfession.WEAPONSMITH,
                copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.ICE_SHARD.get(), 12, 16, 2), new ItemsForEmeralds(new ItemStack(ModItems.HAMMER.get()), 3, 1, 8, 2, 0.2f), new ItemsForEmeralds(new ItemStack(Items.IRON_AXE), 3, 1, 12, 1, 0.2f), new EnchantedItemForEmeralds(Items.IRON_SWORD, 2, 3, 1)}, 2, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.DESH_INGOT.get(), 4, 12, 10), new ItemsForEmeralds(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2f)}, 3, new ItemListing[]{new BuyForOneEmeraldFactory(Items.FLINT, 24, 12, 20)}, 4, new ItemListing[]{new BuyForOneEmeraldFactory(Items.DIAMOND, 1, 12, 30), new EnchantedItemForEmeralds(Items.DIAMOND_AXE, 12, 3, 15, 0.2f)}, 5, new ItemListing[]{new EnchantedItemForEmeralds(Items.DIAMOND_SWORD, 8, 3, 30, 0.2f), new ItemsForEmeralds(OxygenTankItem.createOxygenatedTank(), 24, 1, 2, 40), new ItemsForEmeralds(ModItems.OXYGEN_BUCKET.get(), 32, 1, 1, 60), new ItemsForEmeralds(ModItems.OXYGEN_LOADER.get(), 48, 3, 1, 60)})));
        map.put(VillagerProfession.TOOLSMITH, copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.ICE_SHARD.get(), 12, 16, 2), new ItemsForEmeralds(new ItemStack(Items.SOUL_TORCH), 3, 16, 8, 1, 0.2f), new ItemsForEmeralds(new ItemStack(ModItems.HAMMER.get()), 3, 1, 8, 2, 0.2f), new ItemsForEmeralds(ModItems.WRENCH.get(), 14, 1, 2, 2), new ItemsForEmeralds(new ItemStack(Items.STONE_AXE), 1, 1, 12, 1, 0.2f), new ItemsForEmeralds(new ItemStack(Items.STONE_SHOVEL), 1, 1, 12, 1, 0.2f), new ItemsForEmeralds(new ItemStack(Items.STONE_PICKAXE), 1, 1, 12, 1, 0.2f), new ItemsForEmeralds(new ItemStack(Items.STONE_HOE), 1, 1, 12, 1, 0.2f)}, 2, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.DESH_INGOT.get(), 4, 12, 10), new ItemsForEmeralds(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2f)}, 3,
                new ItemListing[]{new BuyForOneEmeraldFactory(Items.FLINT, 30, 12, 20), new EnchantedItemForEmeralds(Items.IRON_AXE, 1, 3, 10, 0.2f), new EnchantedItemForEmeralds(Items.IRON_SHOVEL, 2, 3, 10, 0.2f), new EnchantedItemForEmeralds(Items.IRON_PICKAXE, 3, 3, 10, 0.2f), new ItemsForEmeralds(new ItemStack(Items.DIAMOND_HOE), 4, 1, 3, 10, 0.2f)}, 4, new ItemListing[]{new BuyForOneEmeraldFactory(Items.DIAMOND, 1, 12, 30), new EnchantedItemForEmeralds(Items.DIAMOND_AXE, 12, 3, 15, 0.2f), new EnchantedItemForEmeralds(Items.DIAMOND_SHOVEL, 5, 3, 15, 0.2f)}, 5, new ItemListing[]{new EnchantedItemForEmeralds(Items.DIAMOND_PICKAXE, 13, 3, 30, 0.2f), new ItemsForEmeralds(OxygenTankItem.createOxygenatedTank(), 24, 1, 2, 40), new ItemsForEmeralds(ModItems.OXYGEN_BUCKET.get(), 32, 1, 1, 60), new ItemsForEmeralds(ModItems.OXYGEN_LOADER.get(), 48, 1, 1, 60), new ItemsForEmeralds(ModItems.COAL_GENERATOR.get(), 32, 1, 1, 60)})));
        map.put(VillagerProfession.BUTCHER, copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.CHEESE.get(), 20, 20, 5), new BuyForOneEmeraldFactory(Items.CHICKEN, 14, 16, 2), new BuyForOneEmeraldFactory(Items.PORKCHOP, 7, 16, 2), new BuyForOneEmeraldFactory(Items.RABBIT, 4, 16, 2), new ItemsForEmeralds(Items.RABBIT_STEW, 1, 1, 1)}, 2, new ItemListing[]{new BuyForOneEmeraldFactory(Items.COAL, 15, 16, 2), new ItemsForEmeralds(Items.COOKED_PORKCHOP, 1, 5, 16, 5), new ItemsForEmeralds(Items.COOKED_CHICKEN, 1, 8, 16, 5)}, 3, new ItemListing[]{new BuyForOneEmeraldFactory(Items.MUTTON, 7, 16, 20), new BuyForOneEmeraldFactory(Items.BEEF, 10, 16, 20)}, 4, new ItemListing[]{new BuyForOneEmeraldFactory(Items.DRIED_KELP_BLOCK, 10, 12, 30)}, 5, new ItemListing[]{new BuyForOneEmeraldFactory(Items.SWEET_BERRIES, 10, 12, 30)})));
        map.put(VillagerProfession.LEATHERWORKER, copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(Items.LEATHER, 6, 16, 2), new DyedArmorForEmeralds(Items.LEATHER_LEGGINGS, 3), new DyedArmorForEmeralds(Items.LEATHER_CHESTPLATE, 7)}, 2, new ItemListing[]{new BuyForOneEmeraldFactory(Items.FLINT, 26, 12, 10), new DyedArmorForEmeralds(Items.LEATHER_HELMET, 5, 12, 5), new DyedArmorForEmeralds(Items.LEATHER_BOOTS, 4, 12, 5)}, 3, new ItemListing[]{new BuyForOneEmeraldFactory(Items.RABBIT_HIDE, 9, 12, 20), new DyedArmorForEmeralds(Items.LEATHER_CHESTPLATE, 7)}, 4, new ItemListing[]{new BuyForOneEmeraldFactory(Items.SCUTE, 4, 12, 30), new DyedArmorForEmeralds(Items.LEATHER_HORSE_ARMOR, 6, 12, 15)}, 5, new ItemListing[]{new ItemsForEmeralds(new ItemStack(Items.SADDLE), 6, 1, 12, 30, 0.2f), new DyedArmorForEmeralds(Items.LEATHER_HELMET, 5, 12, 30)})));
        map.put(VillagerProfession.MASON,
                copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.CONGLOMERATE.get(), 10, 16, 2), new BuyForOneEmeraldFactory(ModItems.MOON_SAND.get(), 32, 32, 1), new ItemsForEmeralds(ModItems.MOON_STONE_BRICKS.get(), 1, 10, 16, 1)}, 2, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.MOON_STONE.get(), 20, 16, 10), new ItemsForEmeralds(ModItems.CHISELED_MOON_STONE_BRICKS.get(), 1, 4, 16, 5)}, 3, new ItemListing[]{new BuyForOneEmeraldFactory(ModItems.MARS_STONE.get(), 16, 16, 20), new BuyForOneEmeraldFactory(ModItems.VENUS_STONE.get(), 16, 16, 20), new BuyForOneEmeraldFactory(ModItems.MERCURY_STONE.get(), 16, 16, 20), new ItemsForEmeralds(Blocks.DRIPSTONE_BLOCK, 1, 4, 16, 10), new ItemsForEmeralds(ModItems.POLISHED_MARS_STONE.get(), 1, 4, 16, 10), new ItemsForEmeralds(ModItems.POLISHED_VENUS_STONE.get(), 1, 4, 16, 10), new ItemsForEmeralds(ModItems.POLISHED_MERCURY_STONE.get(), 1, 4, 16, 10)}, 4,
                        new ItemListing[]{new BuyForOneEmeraldFactory(Items.QUARTZ, 12, 12, 30), new ItemsForEmeralds(Blocks.ORANGE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.WHITE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.BLUE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.LIGHT_BLUE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.GRAY_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.LIGHT_GRAY_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.BLACK_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.RED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.PINK_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.MAGENTA_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.LIME_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.GREEN_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.CYAN_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.PURPLE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.YELLOW_TERRACOTTA, 1, 1, 12, 15),
                                new ItemsForEmeralds(Blocks.BROWN_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.ORANGE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.WHITE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.BLACK_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.RED_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.PINK_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.MAGENTA_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.LIME_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.GREEN_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.CYAN_GLAZED_TERRACOTTA, 1, 1, 12, 15),
                                new ItemsForEmeralds(Blocks.PURPLE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.YELLOW_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.BROWN_GLAZED_TERRACOTTA, 1, 1, 12, 15)},
                        5, new ItemListing[]{new ItemsForEmeralds(ModItems.STEEL_PLATING.get(), 1, 8, 12, 15), new ItemsForEmeralds(ModItems.DESH_PLATING.get(), 1, 8, 12, 15), new ItemsForEmeralds(ModItems.OSTRUM_PLATING.get(), 1, 4, 12, 15), new ItemsForEmeralds(ModItems.STEEL_DOOR.get(), 1, 3, 12, 15), new ItemsForEmeralds(Blocks.QUARTZ_PILLAR, 1, 1, 12, 30), new ItemsForEmeralds(Blocks.QUARTZ_BLOCK, 1, 1, 12, 30)})));
    });

    public static final Int2ObjectMap<ItemListing[]> WANDERING_TRADER_TRADES = copyToFastUtilMap(ImmutableMap.of(1, new ItemListing[]{new ItemsForEmeralds(ModItems.CHEESE.get(), 1, 3, 6, 1), new ItemsForEmeralds(ModItems.SPACE_PAINTING.get(), 48, 1, 2, 2), new ItemsForEmeralds(ModItems.OXYGEN_TANK.get(), 10, 1, 3, 1), new ItemsForEmeralds(Items.SOUL_TORCH, 1, 12, 12, 1), new ItemsForEmeralds(Items.SOUL_CAMPFIRE, 3, 1, 4, 1), new ItemsForEmeralds(ModItems.WRENCH.get(), 12, 1, 2, 2), new ItemsForEmeralds(ModItems.HAMMER.get(), 3, 1, 4, 1), new ItemsForEmeralds(ModItems.WHITE_FLAG.get(), 10, 1, 3, 2), new ItemsForEmeralds(ModItems.SPACE_HELMET.get(), 10, 1, 2, 1), new ItemsForEmeralds(ModItems.SPACE_SUIT.get(), 16, 1, 2, 1), new ItemsForEmeralds(ModItems.SPACE_PANTS.get(), 14, 1, 2, 1), new ItemsForEmeralds(ModItems.SPACE_BOOTS.get(), 8, 1, 2, 1), new ItemsForEmeralds(Items.COAL, 1, 5, 4, 1), new ItemsForEmeralds(ModItems.OXYGEN_BUCKET.get(), 10, 1, 2, 2), new ItemsForEmeralds(Items.COPPER_INGOT, 1, 4, 12, 1),
            new ItemsForEmeralds(Items.WATER_BUCKET, 5, 1, 4, 1), new ItemsForEmeralds(Items.LAVA_BUCKET, 3, 1, 4, 1), new BuyForOneEmeraldFactory(ModItems.DESH_INGOT.get(), 4, 20, 1), new BuyForOneEmeraldFactory(ModItems.OSTRUM_INGOT.get(), 4, 20, 1), new BuyForOneEmeraldFactory(ModItems.CALORITE_INGOT.get(), 4, 20, 1)}, 2, new ItemListing[]{new ItemsForEmeralds(OxygenTankItem.createOxygenatedTank(), 16, 1, 3, 2), new ItemsForEmeralds(ModItems.LAUNCH_PAD.get(), 3, 1, 3, 1), new ItemsForEmeralds(ModItems.LAUNCH_PAD.get(), 3, 9, 3, 1), new ItemsForEmeralds(ModItems.FUEL_BUCKET.get(), 4, 1, 6, 1)}));

    private static Int2ObjectMap<ItemListing[]> copyToFastUtilMap(ImmutableMap<Integer, ItemListing[]> map) {
        return new Int2ObjectOpenHashMap<>(map);
    }

    static class BuyForOneEmeraldFactory implements ItemListing {
        private final Item buy;
        private final int price;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public BuyForOneEmeraldFactory(ItemLike item, int price, int maxUses, int experience) {
            this.buy = item.asItem();
            this.price = price;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05f;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            ItemStack itemStack = new ItemStack(this.buy, this.price);
            return new MerchantOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
        }
    }

    static class ItemsForEmeralds implements ItemListing {
        private final ItemStack itemStack;
        private final int emeraldCost;
        private final int numberOfItems;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsForEmeralds(Block block, int i, int j, int k, int l) {
            this(new ItemStack(block), i, j, k, l);
        }

        public ItemsForEmeralds(Item item, int i, int j, int k) {
            this(new ItemStack(item), i, j, 12, k);
        }

        public ItemsForEmeralds(Item item, int i, int j, int k, int l) {
            this(new ItemStack(item), i, j, k, l);
        }

        public ItemsForEmeralds(ItemStack itemStack, int i, int j, int k, int l) {
            this(itemStack, i, j, k, l, 0.05F);
        }

        public ItemsForEmeralds(ItemStack itemStack, int i, int j, int k, int l, float f) {
            this.itemStack = itemStack;
            this.emeraldCost = i;
            this.numberOfItems = j;
            this.maxUses = k;
            this.villagerXp = l;
            this.priceMultiplier = f;
        }

        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            ItemStack stack = this.itemStack.copy();
            stack.setCount(numberOfItems);
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), stack, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    private static class SuspiciousStewForEmerald implements ItemListing {
        final MobEffect effect;
        final int duration;
        final int xp;
        private final float priceMultiplier;

        public SuspiciousStewForEmerald(MobEffect mobEffect, int i, int j) {
            this.effect = mobEffect;
            this.duration = i;
            this.xp = j;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            ItemStack itemStack = new ItemStack(Items.SUSPICIOUS_STEW, 1);
            SuspiciousStewItem.saveMobEffect(itemStack, this.effect, this.duration);
            return new MerchantOffer(new ItemStack(Items.EMERALD, 1), itemStack, 12, this.xp, this.priceMultiplier);
        }
    }

    private static class ItemsAndEmeraldsToItems implements ItemListing {
        private final ItemStack fromItem;
        private final int fromCount;
        private final int emeraldCost;
        private final ItemStack toItem;
        private final int toCount;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsAndEmeraldsToItems(ItemLike itemLike, int i, Item item, int j, int k, int l) {
            this(itemLike, i, 1, item, j, k, l);
        }

        public ItemsAndEmeraldsToItems(ItemLike itemLike, int i, int j, Item item, int k, int l, int m) {
            this.fromItem = new ItemStack(itemLike);
            this.fromCount = i;
            this.emeraldCost = j;
            this.toItem = new ItemStack(item);
            this.toCount = k;
            this.maxUses = l;
            this.villagerXp = m;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.fromItem.getItem(), this.fromCount), new ItemStack(this.toItem.getItem(), this.toCount), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    private static class EnchantedItemForEmeralds implements ItemListing {
        private final ItemStack itemStack;
        private final int baseEmeraldCost;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public EnchantedItemForEmeralds(Item item, int i, int j, int k) {
            this(item, i, j, k, 0.05F);
        }

        public EnchantedItemForEmeralds(Item item, int i, int j, int k, float f) {
            this.itemStack = new ItemStack(item);
            this.baseEmeraldCost = i;
            this.maxUses = j;
            this.villagerXp = k;
            this.priceMultiplier = f;
        }

        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            int i = 5 + random.nextInt(15);
            ItemStack itemStack = EnchantmentHelper.enchantItem(random, new ItemStack(this.itemStack.getItem()), i, false);
            int j = Math.min(this.baseEmeraldCost + i, 64);
            ItemStack itemStack2 = new ItemStack(Items.EMERALD, j);
            return new MerchantOffer(itemStack2, itemStack, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    private static class EmeraldsForVillagerTypeItem implements ItemListing {
        private final Map<VillagerType, Item> trades;
        private final int cost;
        private final int maxUses;
        private final int villagerXp;

        public EmeraldsForVillagerTypeItem(int i, int j, int k, Map<VillagerType, Item> map) {
            BuiltInRegistries.VILLAGER_TYPE.stream().filter((villagerType) -> {
                return !map.containsKey(villagerType);
            }).findAny().ifPresent((villagerType) -> {
                throw new IllegalStateException("Missing trade for villager type: " + BuiltInRegistries.VILLAGER_TYPE.getKey(villagerType));
            });
            this.trades = map;
            this.cost = i;
            this.maxUses = j;
            this.villagerXp = k;
        }

        @Nullable
        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            if (trader instanceof VillagerDataHolder) {
                ItemStack itemStack = new ItemStack((ItemLike) this.trades.get(((VillagerDataHolder) trader).getVillagerData().getType()), this.cost);
                return new MerchantOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.villagerXp, 0.05F);
            } else {
                return null;
            }
        }
    }

    private static class TippedArrowForItemsAndEmeralds implements ItemListing {
        /**
         * An ItemStack that can have potion effects written to it.
         */
        private final ItemStack toItem;
        private final int toCount;
        private final int emeraldCost;
        private final int maxUses;
        private final int villagerXp;
        private final Item fromItem;
        private final int fromCount;
        private final float priceMultiplier;

        public TippedArrowForItemsAndEmeralds(Item item, int i, Item item2, int j, int k, int l, int m) {
            this.toItem = new ItemStack(item2);
            this.emeraldCost = k;
            this.maxUses = l;
            this.villagerXp = m;
            this.fromItem = item;
            this.fromCount = i;
            this.toCount = j;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            ItemStack itemStack = new ItemStack(Items.EMERALD, this.emeraldCost);
            List<Potion> list = BuiltInRegistries.POTION.stream().filter((potionx) -> !potionx.getEffects().isEmpty() && PotionBrewing.isBrewablePotion(potionx)).toList();
            Potion potion = list.get(random.nextInt(list.size()));
            ItemStack itemStack2 = PotionUtils.setPotion(new ItemStack(this.toItem.getItem(), this.toCount), potion);
            return new MerchantOffer(itemStack, new ItemStack(this.fromItem, this.fromCount), itemStack2, this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    private static class EnchantBookForEmeralds implements ItemListing {
        private final int villagerXp;

        public EnchantBookForEmeralds(int i) {
            this.villagerXp = i;
        }

        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            List<Enchantment> list = BuiltInRegistries.ENCHANTMENT.stream().filter(Enchantment::isTradeable).toList();
            Enchantment enchantment = list.get(random.nextInt(list.size()));
            int i = Mth.nextInt(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
            ItemStack itemStack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i));
            int j = 2 + random.nextInt(5 + i * 10) + 3 * i;
            if (enchantment.isTreasureOnly()) {
                j *= 2;
            }

            if (j > 64) {
                j = 64;
            }

            return new MerchantOffer(new ItemStack(Items.EMERALD, j), new ItemStack(Items.BOOK), itemStack, 12, this.villagerXp, 0.2F);
        }
    }

    private static class DyedArmorForEmeralds implements ItemListing {
        private final Item item;
        private final int value;
        private final int maxUses;
        private final int villagerXp;

        public DyedArmorForEmeralds(Item item, int i) {
            this(item, i, 12, 1);
        }

        public DyedArmorForEmeralds(Item item, int i, int j, int k) {
            this.item = item;
            this.value = i;
            this.maxUses = j;
            this.villagerXp = k;
        }

        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            ItemStack itemStack = new ItemStack(Items.EMERALD, this.value);
            ItemStack itemStack2 = new ItemStack(this.item);
            if (this.item instanceof DyeableArmorItem) {
                List<DyeItem> list = Lists.newArrayList();
                list.add(getRandomDye(random));
                if (random.nextFloat() > 0.7F) {
                    list.add(getRandomDye(random));
                }

                if (random.nextFloat() > 0.8F) {
                    list.add(getRandomDye(random));
                }

                itemStack2 = DyeableLeatherItem.dyeArmor(itemStack2, list);
            }

            return new MerchantOffer(itemStack, itemStack2, this.maxUses, this.villagerXp, 0.2F);
        }

        private static DyeItem getRandomDye(RandomSource random) {
            return DyeItem.byColor(DyeColor.byId(random.nextInt(16)));
        }
    }
}
