package net.mrscauthd.boss_tools.entity.alien;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.mrscauthd.boss_tools.ModInnet;

public class AlienTrade {
    public static int maxUseIn = 9999999;

    public static final Map<VillagerProfession, Int2ObjectMap<AlienTrade.ITrade[]>> VILLAGER_DEFAULT_TRADES = Util.make(Maps.newHashMap(), (p_221237_0_) -> {
        p_221237_0_.put(VillagerProfession.FARMER, gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.WHEAT, 20, 16, 2), new AlienTrade.EmeraldForItemsTrade(Items.POTATO, 26, 16, 2), new AlienTrade.EmeraldForItemsTrade(Items.CARROT, 22, 16, 2), new AlienTrade.EmeraldForItemsTrade(Items.BEETROOT, 15, 16, 2), new AlienTrade.ItemsForEmeraldsTrade(ModInnet.CHESE.get(), 1, 6, 16, 1)}, 2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Blocks.PUMPKIN, 6, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Items.PUMPKIN_PIE, 1, 4, 5), new AlienTrade.ItemsForEmeraldsTrade(Items.APPLE, 1, 4, 16, 5)}, 3, new AlienTrade.ITrade[]{new AlienTrade.ItemsForEmeraldsTrade(Items.COOKIE, 3, 18, 10), new AlienTrade.EmeraldForItemsTrade(Blocks.MELON, 4, 12, 20)}, 4, new AlienTrade.ITrade[]{new AlienTrade.ItemsForEmeraldsTrade(Blocks.CAKE, 1, 1, 12, 15), new AlienTrade.SuspiciousStewForEmeraldTrade(Effects.NIGHT_VISION, 100, 15), new AlienTrade.SuspiciousStewForEmeraldTrade(Effects.JUMP_BOOST, 160, 15), new AlienTrade.SuspiciousStewForEmeraldTrade(Effects.WEAKNESS, 140, 15), new AlienTrade.SuspiciousStewForEmeraldTrade(Effects.BLINDNESS, 120, 15), new AlienTrade.SuspiciousStewForEmeraldTrade(Effects.POISON, 280, 15), new AlienTrade.SuspiciousStewForEmeraldTrade(Effects.SATURATION, 7, 15)}, 5, new AlienTrade.ITrade[]{new AlienTrade.ItemsForEmeraldsTrade(Items.GOLDEN_CARROT, 3, 3, 30), new AlienTrade.ItemsForEmeraldsTrade(Items.GLISTERING_MELON_SLICE, 4, 3, 30)})));
        p_221237_0_.put(VillagerProfession.FISHERMAN, gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.STRING, 20, 16, 2), new AlienTrade.EmeraldForItemsTrade(Items.COAL, 10, 16, 2), new AlienTrade.ItemsForEmeraldsAndItemsTrade(Items.COD, 6, Items.COOKED_COD, 6, 16, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.COD_BUCKET, 3, 1, 16, 1)}, 2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.COD, 15, 16, 10), new AlienTrade.ItemsForEmeraldsAndItemsTrade(Items.SALMON, 6, Items.COOKED_SALMON, 6, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Items.CAMPFIRE, 2, 1, 5)}, 3, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.SALMON, 13, 16, 20), new AlienTrade.EnchantedItemForEmeraldsTrade(Items.FISHING_ROD, 3, 3, 10, 0.2F)}, 4, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.TROPICAL_FISH, 6, 12, 30)}, 5, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.PUFFERFISH, 4, 12, 30), new AlienTrade.EmeraldForVillageTypeItemTrade(1, 12, 30, ImmutableMap.<VillagerType, Item>builder().put(VillagerType.PLAINS, Items.OAK_BOAT).put(VillagerType.TAIGA, Items.SPRUCE_BOAT).put(VillagerType.SNOW, Items.SPRUCE_BOAT).put(VillagerType.DESERT, Items.JUNGLE_BOAT).put(VillagerType.JUNGLE, Items.JUNGLE_BOAT).put(VillagerType.SAVANNA, Items.ACACIA_BOAT).put(VillagerType.SWAMP, Items.DARK_OAK_BOAT).build())})));
        p_221237_0_.put(VillagerProfession.SHEPHERD, gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Blocks.WHITE_WOOL, 18, 16, 2), new AlienTrade.EmeraldForItemsTrade(Blocks.BROWN_WOOL, 18, 16, 2), new AlienTrade.EmeraldForItemsTrade(Blocks.BLACK_WOOL, 18, 16, 2), new AlienTrade.EmeraldForItemsTrade(Blocks.GRAY_WOOL, 18, 16, 2), new AlienTrade.ItemsForEmeraldsTrade(ModInnet.FLAG_RED_ITEM.get(), 2, 1, 1)}, 2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.WHITE_DYE, 12, 16, 10), new AlienTrade.EmeraldForItemsTrade(Items.GRAY_DYE, 12, 16, 10), new AlienTrade.EmeraldForItemsTrade(Items.BLACK_DYE, 12, 16, 10), new AlienTrade.EmeraldForItemsTrade(Items.LIGHT_BLUE_DYE, 12, 16, 10), new AlienTrade.EmeraldForItemsTrade(Items.LIME_DYE, 12, 16, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.WHITE_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.ORANGE_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.MAGENTA_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.YELLOW_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIME_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.PINK_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.GRAY_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.CYAN_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.PURPLE_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BLUE_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BROWN_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.GREEN_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.RED_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BLACK_WOOL, 1, 1, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.WHITE_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.ORANGE_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.MAGENTA_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.YELLOW_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIME_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.PINK_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.GRAY_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.CYAN_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.PURPLE_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BLUE_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BROWN_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.GREEN_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.RED_CARPET, 1, 4, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BLACK_CARPET, 1, 4, 16, 5)}, 3, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.YELLOW_DYE, 12, 16, 20), new AlienTrade.EmeraldForItemsTrade(Items.LIGHT_GRAY_DYE, 12, 16, 20), new AlienTrade.EmeraldForItemsTrade(Items.ORANGE_DYE, 12, 16, 20), new AlienTrade.EmeraldForItemsTrade(Items.RED_DYE, 12, 16, 20), new AlienTrade.EmeraldForItemsTrade(Items.PINK_DYE, 12, 16, 20), new AlienTrade.ItemsForEmeraldsTrade(Blocks.WHITE_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.YELLOW_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.RED_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BLACK_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BLUE_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BROWN_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.CYAN_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.GRAY_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.GREEN_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIME_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.MAGENTA_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.ORANGE_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.PINK_BED, 3, 1, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.PURPLE_BED, 3, 1, 12, 10)}, 4, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.BROWN_DYE, 12, 16, 30), new AlienTrade.EmeraldForItemsTrade(Items.PURPLE_DYE, 12, 16, 30), new AlienTrade.EmeraldForItemsTrade(Items.BLUE_DYE, 12, 16, 30), new AlienTrade.EmeraldForItemsTrade(Items.GREEN_DYE, 12, 16, 30), new AlienTrade.EmeraldForItemsTrade(Items.MAGENTA_DYE, 12, 16, 30), new AlienTrade.EmeraldForItemsTrade(Items.CYAN_DYE, 12, 16, 30), new AlienTrade.ItemsForEmeraldsTrade(Items.WHITE_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.BLUE_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.LIGHT_BLUE_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.RED_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.PINK_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.GREEN_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.LIME_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.GRAY_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.BLACK_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.PURPLE_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.MAGENTA_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.CYAN_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.BROWN_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.YELLOW_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.ORANGE_BANNER, 3, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.LIGHT_GRAY_BANNER, 3, 1, 12, 15)}, 5, new AlienTrade.ITrade[]{new AlienTrade.ItemsForEmeraldsTrade(Items.PAINTING, 2, 3, 30)})));
        p_221237_0_.put(VillagerProfession.FLETCHER, gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.STICK, 32, 16, 2), new AlienTrade.ItemsForEmeraldsTrade(Items.ARROW, 1, 16, 1), new AlienTrade.ItemsForEmeraldsAndItemsTrade(Blocks.GRAVEL, 10, Items.FLINT, 10, 12, 1)}, 2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.FLINT, 26, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Items.BOW, 2, 1, 5)}, 3, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.STRING, 14, 16, 20), new AlienTrade.ItemsForEmeraldsTrade(Items.CROSSBOW, 3, 1, 10)}, 4, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.FEATHER, 24, 16, 30), new AlienTrade.EnchantedItemForEmeraldsTrade(Items.BOW, 2, 3, 15)}, 5, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.TRIPWIRE_HOOK, 8, 12, 30), new AlienTrade.EnchantedItemForEmeraldsTrade(Items.CROSSBOW, 3, 3, 15), new AlienTrade.ItemWithPotionForEmeraldsAndItemsTrade(Items.ARROW, 5, Items.TIPPED_ARROW, 5, 2, 12, 30)})));
        p_221237_0_.put(VillagerProfession.LIBRARIAN, gatAsIntMap(ImmutableMap.<Integer, AlienTrade.ITrade[]>builder().put(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.PAPER, 24, 16, 2), new AlienTrade.EnchantedBookForEmeraldsTrade(1), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BOOKSHELF, 9, 1, 12, 1)}).put(2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.BOOK, 4, 12, 10), new AlienTrade.EnchantedBookForEmeraldsTrade(5), new AlienTrade.ItemsForEmeraldsTrade(Items.LANTERN, 1, 1, 5)}).put(3, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.INK_SAC, 5, 12, 20), new AlienTrade.EnchantedBookForEmeraldsTrade(10), new AlienTrade.ItemsForEmeraldsTrade(Items.GLASS, 1, 4, 10)}).put(4, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.WRITABLE_BOOK, 2, 12, 30), new AlienTrade.EnchantedBookForEmeraldsTrade(15), new AlienTrade.ItemsForEmeraldsTrade(Items.CLOCK, 5, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.COMPASS, 4, 1, 15)}).put(5, new AlienTrade.ITrade[]{new AlienTrade.ItemsForEmeraldsTrade(Items.NAME_TAG, 20, 1, 30)}).build()));
        p_221237_0_.put(VillagerProfession.CARTOGRAPHER, gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.PAPER, 24, 16, 2), new AlienTrade.ItemsForEmeraldsTrade(Items.MAP, 7, 1, 1)}, 2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.GLASS_PANE, 11, 16, 10), new AlienTrade.EmeraldForMapTrade(13, Structure.MONUMENT, MapDecoration.Type.MONUMENT, 12, 5)}, 3, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.COMPASS, 1, 12, 20), new AlienTrade.EmeraldForMapTrade(14, Structure.WOODLAND_MANSION, MapDecoration.Type.MANSION, 12, 10)}, 4, new AlienTrade.ITrade[]{new AlienTrade.ItemsForEmeraldsTrade(Items.ITEM_FRAME, 7, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.WHITE_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.BLUE_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.LIGHT_BLUE_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.RED_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.PINK_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.GREEN_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.LIME_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.GRAY_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.BLACK_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.PURPLE_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.MAGENTA_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.CYAN_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.BROWN_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.YELLOW_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.ORANGE_BANNER, 3, 1, 15), new AlienTrade.ItemsForEmeraldsTrade(Items.LIGHT_GRAY_BANNER, 3, 1, 15)}, 5, new AlienTrade.ITrade[]{new AlienTrade.ItemsForEmeraldsTrade(Items.GLOBE_BANNER_PATTERN, 8, 1, 30)})));
        p_221237_0_.put(VillagerProfession.CLERIC, gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.ROTTEN_FLESH, 32, 16, 2), new AlienTrade.ItemsForEmeraldsTrade(Items.REDSTONE, 1, 2, 1)}, 2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.GOLD_INGOT, 3, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(Items.LAPIS_LAZULI, 1, 1, 5)}, 3, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.RABBIT_FOOT, 2, 12, 20), new AlienTrade.ItemsForEmeraldsTrade(Blocks.GLOWSTONE, 4, 1, 12, 10)}, 4, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.SCUTE, 4, 12, 30), new AlienTrade.EmeraldForItemsTrade(Items.GLASS_BOTTLE, 9, 12, 30), new AlienTrade.ItemsForEmeraldsTrade(Items.ENDER_PEARL, 5, 1, 15)}, 5, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.NETHER_WART, 22, 12, 30), new AlienTrade.ItemsForEmeraldsTrade(Items.EXPERIENCE_BOTTLE, 3, 1, 30)})));
        p_221237_0_.put(VillagerProfession.ARMORER, gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(ModInnet.STEEL_INGOT.get(), 15, 16, 2), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_CHESTPLATE), 7, 1, 12, 1, 0.2F), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.IRON_BOOTS), 4, 1, 12, 1, 0.2F), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.IRON_HELMET), 5, 1, 12, 1, 0.2F), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.IRON_CHESTPLATE), 9, 1, 12, 1, 0.2F)}, 2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(ModInnet.STEEL_INGOT.get(), 4, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2F), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_BOOTS), 1, 1, 12, 5, 0.2F), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_LEGGINGS), 3, 1, 12, 5, 0.2F)}, 3, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.LAVA_BUCKET, 1, 12, 20), new AlienTrade.EmeraldForItemsTrade(Items.DIAMOND, 1, 12, 20), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_HELMET), 1, 1, 12, 10, 0.2F), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.CHAINMAIL_CHESTPLATE), 4, 1, 12, 10, 0.2F), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.SHIELD), 5, 1, 12, 10, 0.2F)}, 4, new AlienTrade.ITrade[]{new AlienTrade.EnchantedItemForEmeraldsTrade(Items.DIAMOND_LEGGINGS, 14, 3, 15, 0.2F), new AlienTrade.EnchantedItemForEmeraldsTrade(Items.DIAMOND_BOOTS, 8, 3, 15, 0.2F)}, 5, new AlienTrade.ITrade[]{new AlienTrade.EnchantedItemForEmeraldsTrade(Items.DIAMOND_HELMET, 8, 3, 30, 0.2F), new AlienTrade.EnchantedItemForEmeraldsTrade(Items.DIAMOND_CHESTPLATE, 16, 3, 30, 0.2F)})));
        p_221237_0_.put(VillagerProfession.WEAPONSMITH, gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(ModInnet.STEEL_SWORD.get(), 15, 16, 2), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.IRON_AXE), 3, 1, 12, 1, 0.2F), new AlienTrade.EnchantedItemForEmeraldsTrade(Items.IRON_SWORD, 2, 3, 1)}, 2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.IRON_INGOT, 4, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2F)}, 3, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.FLINT, 24, 12, 20)}, 4, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.DIAMOND, 1, 12, 30), new AlienTrade.EnchantedItemForEmeraldsTrade(Items.DIAMOND_AXE, 12, 3, 15, 0.2F)}, 5, new AlienTrade.ITrade[]{new AlienTrade.EnchantedItemForEmeraldsTrade(ModInnet.STEEL_AXE.get(), 8, 3, 30, 0.2F)})));
        p_221237_0_.put(VillagerProfession.TOOLSMITH, gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.COAL, 15, 16, 2), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.STONE_AXE), 1, 1, 12, 1, 0.2F), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.STONE_SHOVEL), 1, 1, 12, 1, 0.2F), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.STONE_PICKAXE), 1, 1, 12, 1, 0.2F), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.STONE_HOE), 1, 1, 12, 1, 0.2F)}, 2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(ModInnet.STEEL_INGOT.get(), 4, 12, 10), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.BELL), 36, 1, 12, 5, 0.2F)}, 3, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.FLINT, 30, 12, 20), new AlienTrade.EnchantedItemForEmeraldsTrade(Items.IRON_AXE, 1, 3, 10, 0.2F), new AlienTrade.EnchantedItemForEmeraldsTrade(Items.IRON_SHOVEL, 2, 3, 10, 0.2F), new AlienTrade.EnchantedItemForEmeraldsTrade(Items.IRON_PICKAXE, 3, 3, 10, 0.2F), new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.DIAMOND_HOE), 4, 1, 3, 10, 0.2F)}, 4, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.DIAMOND, 1, 12, 30), new AlienTrade.EnchantedItemForEmeraldsTrade(Items.DIAMOND_AXE, 12, 3, 15, 0.2F), new AlienTrade.EnchantedItemForEmeraldsTrade(Items.DIAMOND_SHOVEL, 5, 3, 15, 0.2F)}, 5, new AlienTrade.ITrade[]{new AlienTrade.EnchantedItemForEmeraldsTrade(Items.DIAMOND_PICKAXE, 13, 3, 30, 0.2F)})));
        p_221237_0_.put(VillagerProfession.BUTCHER, gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.CHICKEN, 14, 16, 2), new AlienTrade.EmeraldForItemsTrade(Items.PORKCHOP, 7, 16, 2), new AlienTrade.EmeraldForItemsTrade(Items.RABBIT, 4, 16, 2), new AlienTrade.ItemsForEmeraldsTrade(Items.RABBIT_STEW, 1, 1, 1)}, 2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.COAL, 15, 16, 2), new AlienTrade.ItemsForEmeraldsTrade(Items.COOKED_PORKCHOP, 1, 5, 16, 5), new AlienTrade.ItemsForEmeraldsTrade(Items.COOKED_CHICKEN, 1, 8, 16, 5)}, 3, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.MUTTON, 7, 16, 20), new AlienTrade.EmeraldForItemsTrade(Items.BEEF, 10, 16, 20)}, 4, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.DRIED_KELP_BLOCK, 10, 12, 30)}, 5, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.SWEET_BERRIES, 10, 12, 30)})));
        p_221237_0_.put(VillagerProfession.LEATHERWORKER, gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.LEATHER, 6, 16, 2), new AlienTrade.DyedArmorForEmeraldsTrade(Items.LEATHER_LEGGINGS, 3), new AlienTrade.DyedArmorForEmeraldsTrade(Items.LEATHER_CHESTPLATE, 7)}, 2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.FLINT, 26, 12, 10), new AlienTrade.DyedArmorForEmeraldsTrade(Items.LEATHER_HELMET, 5, 12, 5), new AlienTrade.DyedArmorForEmeraldsTrade(Items.LEATHER_BOOTS, 4, 12, 5)}, 3, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.RABBIT_HIDE, 9, 12, 20), new AlienTrade.DyedArmorForEmeraldsTrade(Items.LEATHER_CHESTPLATE, 7)}, 4, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.SCUTE, 4, 12, 30), new AlienTrade.DyedArmorForEmeraldsTrade(Items.LEATHER_HORSE_ARMOR, 6, 12, 15)}, 5, new AlienTrade.ITrade[]{new AlienTrade.ItemsForEmeraldsTrade(new ItemStack(Items.SADDLE), 6, 1, 12, 30, 0.2F), new AlienTrade.DyedArmorForEmeraldsTrade(Items.LEATHER_HELMET, 5, 12, 30)})));
        p_221237_0_.put(VillagerProfession.MASON, gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.CLAY_BALL, 10, 16, 2), new AlienTrade.ItemsForEmeraldsTrade(Items.BRICK, 1, 10, 16, 1)}, 2, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Blocks.STONE, 20, 16, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.CHISELED_STONE_BRICKS, 1, 4, 16, 5)}, 3, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Blocks.GRANITE, 16, 16, 20), new AlienTrade.EmeraldForItemsTrade(Blocks.ANDESITE, 16, 16, 20), new AlienTrade.EmeraldForItemsTrade(Blocks.DIORITE, 16, 16, 20), new AlienTrade.ItemsForEmeraldsTrade(Blocks.POLISHED_ANDESITE, 1, 4, 16, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.POLISHED_DIORITE, 1, 4, 16, 10), new AlienTrade.ItemsForEmeraldsTrade(Blocks.POLISHED_GRANITE, 1, 4, 16, 10)}, 4, new AlienTrade.ITrade[]{new AlienTrade.EmeraldForItemsTrade(Items.QUARTZ, 12, 12, 30), new AlienTrade.ItemsForEmeraldsTrade(Blocks.ORANGE_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.WHITE_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BLUE_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.GRAY_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BLACK_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.RED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.PINK_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.MAGENTA_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIME_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.GREEN_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.CYAN_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.PURPLE_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.YELLOW_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BROWN_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.ORANGE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.WHITE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BLACK_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.RED_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.PINK_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.MAGENTA_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.LIME_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.GREEN_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.CYAN_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.PURPLE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.YELLOW_GLAZED_TERRACOTTA, 1, 1, 12, 15), new AlienTrade.ItemsForEmeraldsTrade(Blocks.BROWN_GLAZED_TERRACOTTA, 1, 1, 12, 15)}, 5, new AlienTrade.ITrade[]{new AlienTrade.ItemsForEmeraldsTrade(Blocks.QUARTZ_PILLAR, 1, 1, 12, 30), new AlienTrade.ItemsForEmeraldsTrade(Blocks.QUARTZ_BLOCK, 1, 1, 12, 30)})));
    });
    public static final Int2ObjectMap<AlienTrade.ITrade[]> field_221240_b = gatAsIntMap(ImmutableMap.of(1, new AlienTrade.ITrade[]{new AlienTrade.ItemsForEmeraldsTrade(Items.SEA_PICKLE, 2, 1, 5, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.SLIME_BALL, 4, 1, 5, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.GLOWSTONE, 2, 1, 5, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.NAUTILUS_SHELL, 5, 1, 5, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.FERN, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.SUGAR_CANE, 1, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.PUMPKIN, 1, 1, 4, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.KELP, 3, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.CACTUS, 3, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.DANDELION, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.POPPY, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.BLUE_ORCHID, 1, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.ALLIUM, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.AZURE_BLUET, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.RED_TULIP, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.ORANGE_TULIP, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.WHITE_TULIP, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.PINK_TULIP, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.OXEYE_DAISY, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.CORNFLOWER, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.LILY_OF_THE_VALLEY, 1, 1, 7, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.WHEAT_SEEDS, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.BEETROOT_SEEDS, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.PUMPKIN_SEEDS, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.MELON_SEEDS, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.ACACIA_SAPLING, 5, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.BIRCH_SAPLING, 5, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.DARK_OAK_SAPLING, 5, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.JUNGLE_SAPLING, 5, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.OAK_SAPLING, 5, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.SPRUCE_SAPLING, 5, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.RED_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.WHITE_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.BLUE_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.PINK_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.BLACK_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.GREEN_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.LIGHT_GRAY_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.MAGENTA_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.YELLOW_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.GRAY_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.PURPLE_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.LIGHT_BLUE_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.LIME_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.ORANGE_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.BROWN_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.CYAN_DYE, 1, 3, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.BRAIN_CORAL_BLOCK, 3, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.BUBBLE_CORAL_BLOCK, 3, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.FIRE_CORAL_BLOCK, 3, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.HORN_CORAL_BLOCK, 3, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.TUBE_CORAL_BLOCK, 3, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.VINE, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.BROWN_MUSHROOM, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.RED_MUSHROOM, 1, 1, 12, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.LILY_PAD, 1, 2, 5, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.SAND, 1, 8, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.RED_SAND, 1, 4, 6, 1)}, 2, new AlienTrade.ITrade[]{new AlienTrade.ItemsForEmeraldsTrade(Items.TROPICAL_FISH_BUCKET, 5, 1, 4, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.PUFFERFISH_BUCKET, 5, 1, 4, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.PACKED_ICE, 3, 1, 6, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.BLUE_ICE, 6, 1, 6, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.GUNPOWDER, 1, 1, 8, 1), new AlienTrade.ItemsForEmeraldsTrade(Items.PODZOL, 3, 3, 6, 1)}));

    private static Int2ObjectMap<AlienTrade.ITrade[]> gatAsIntMap(ImmutableMap<Integer, AlienTrade.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    static class DyedArmorForEmeraldsTrade implements AlienTrade.ITrade {
        private final Item tradeItem;
        private final int price;
        private final int maxUses;
        private final int xpValue;

        public DyedArmorForEmeraldsTrade(Item itemIn, int priceIn) {
            this(itemIn, priceIn, maxUseIn, 1);
        }

        public DyedArmorForEmeraldsTrade(Item tradeItemIn, int priceIn, int maxUsesIn, int xpValueIn) {
            this.tradeItem = tradeItemIn;
            this.price = priceIn;
            this.maxUses = maxUsesIn;
            this.xpValue = xpValueIn;
        }

        public MerchantOffer getOffer(AlienEntity trader, Random rand) {
            ItemStack itemstack = new ItemStack(Items.EMERALD, this.price);
            ItemStack itemstack1 = new ItemStack(this.tradeItem);
            if (this.tradeItem instanceof DyeableArmorItem) {
                List<DyeItem> list = Lists.newArrayList();
                list.add(getRandomDyeItem(rand));
                if (rand.nextFloat() > 0.7F) {
                    list.add(getRandomDyeItem(rand));
                }

                if (rand.nextFloat() > 0.8F) {
                    list.add(getRandomDyeItem(rand));
                }

                itemstack1 = IDyeableArmorItem.dyeItem(itemstack1, list);
            }

            return new MerchantOffer(itemstack, itemstack1, maxUseIn, this.xpValue, 0.2F);
        }

        private static DyeItem getRandomDyeItem(Random p_221232_0_) {
            return DyeItem.getItem(DyeColor.byId(p_221232_0_.nextInt(16)));
        }
    }

    static class EmeraldForItemsTrade implements AlienTrade.ITrade {
        private final Item tradeItem;
        private final int count;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public EmeraldForItemsTrade(IItemProvider tradeItemIn, int countIn, int maxUsesIn, int xpValueIn) {
            this.tradeItem = tradeItemIn.asItem();
            this.count = countIn;
            this.maxUses = maxUsesIn;
            this.xpValue = xpValueIn;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer getOffer(AlienEntity trader, Random rand) {
            ItemStack itemstack = new ItemStack(this.tradeItem, this.count);
            return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), maxUseIn, this.xpValue, this.priceMultiplier);
        }
    }

    static class EmeraldForMapTrade implements AlienTrade.ITrade {
        private final int count;
        private final Structure<?> structureName;
        private final MapDecoration.Type mapDecorationType;
        private final int maxUses;
        private final int xpValue;

        public EmeraldForMapTrade(int count, Structure<?> structureName, MapDecoration.Type mapDecorationType, int maxUses, int xpValue) {
            this.count = count;
            this.structureName = structureName;
            this.mapDecorationType = mapDecorationType;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
        }

        @Nullable
        public MerchantOffer getOffer(AlienEntity trader, Random rand) {
            if (!(trader.world instanceof ServerWorld)) {
                return null;
            } else {
                ServerWorld serverworld = (ServerWorld)trader.world;
                BlockPos blockpos = serverworld.func_241117_a_(this.structureName, trader.getPosition(), 100, true);
                if (blockpos != null) {
                    ItemStack itemstack = FilledMapItem.setupNewMap(serverworld, blockpos.getX(), blockpos.getZ(), (byte)2, true, true);
                    FilledMapItem.func_226642_a_(serverworld, itemstack);
                    MapData.addTargetDecoration(itemstack, blockpos, "+", this.mapDecorationType);
                    itemstack.setDisplayName(new TranslationTextComponent("filled_map." + this.structureName.getStructureName().toLowerCase(Locale.ROOT)));
                    return new MerchantOffer(new ItemStack(Items.EMERALD, this.count), new ItemStack(Items.COMPASS), itemstack, maxUseIn, this.xpValue, 0.2F);
                } else {
                    return null;
                }
            }
        }
    }

    static class EmeraldForVillageTypeItemTrade implements AlienTrade.ITrade {
        private final Map<VillagerType, Item> villagerTypeItems;
        private final int count;
        private final int maxUses;
        private final int xpValue;

        public EmeraldForVillageTypeItemTrade(int count, int maxUsesIn, int xpValueIn, Map<VillagerType, Item> villagerTypeItemsIn) {
            Registry.VILLAGER_TYPE.stream().filter((villagerType) -> {
                return !villagerTypeItemsIn.containsKey(villagerType);
            }).findAny().ifPresent((villagerType) -> {

            });
            this.villagerTypeItems = villagerTypeItemsIn;
            this.count = count;
            this.maxUses = maxUsesIn;
            this.xpValue = xpValueIn;
        }

        @Nullable
        public MerchantOffer getOffer(AlienEntity trader, Random rand) {
            if (trader instanceof IVillagerDataHolder) {
                ItemStack itemstack = new ItemStack(this.villagerTypeItems.get(((IVillagerDataHolder)trader).getVillagerData().getType()), this.count);
                return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), maxUseIn, this.xpValue, 0.05F);
            } else {
                return null;
            }
        }
    }

    static class EnchantedBookForEmeraldsTrade implements AlienTrade.ITrade {
        private final int xpValue;

        public EnchantedBookForEmeraldsTrade(int xpValueIn) {
            this.xpValue = xpValueIn;
        }

        public MerchantOffer getOffer(AlienEntity trader, Random rand) {
            List<Enchantment> list = Registry.ENCHANTMENT.stream().filter(Enchantment::canVillagerTrade).collect(Collectors.toList());
            Enchantment enchantment = list.get(rand.nextInt(list.size()));
            int i = MathHelper.nextInt(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
            ItemStack itemstack = EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(enchantment, i));
            int j = 2 + rand.nextInt(5 + i * 10) + 3 * i;
            if (enchantment.isTreasureEnchantment()) {
                j *= 2;
            }

            if (j > 64) {
                j = 64;
            }

            return new MerchantOffer(new ItemStack(Items.EMERALD, j), new ItemStack(Items.BOOK), itemstack, maxUseIn, this.xpValue, 0.2F);
        }
    }

    static class EnchantedItemForEmeraldsTrade implements AlienTrade.ITrade {
        private final ItemStack sellingStack;
        private final int emeraldCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public EnchantedItemForEmeraldsTrade(Item p_i50535_1_, int emeraldCount, int maxUses, int xpValue) {
            this(p_i50535_1_, emeraldCount, maxUseIn, xpValue, 0.05F);
        }

        public EnchantedItemForEmeraldsTrade(Item sellItem, int emeraldCount, int maxUses, int xpValue, float priceMultiplier) {
            this.sellingStack = new ItemStack(sellItem);
            this.emeraldCount = emeraldCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(AlienEntity trader, Random rand) {
            int i = 5 + rand.nextInt(15);
            ItemStack itemstack = EnchantmentHelper.addRandomEnchantment(rand, new ItemStack(this.sellingStack.getItem()), i, false);
            int j = Math.min(this.emeraldCount + i, 64);
            ItemStack itemstack1 = new ItemStack(Items.EMERALD, j);
            return new MerchantOffer(itemstack1, itemstack, maxUseIn, this.xpValue, this.priceMultiplier);
        }
    }

    public interface ITrade {
        @Nullable
        MerchantOffer getOffer(AlienEntity trader, Random rand);
    }

    static class ItemWithPotionForEmeraldsAndItemsTrade implements AlienTrade.ITrade {
        /** An ItemStack that can have potion effects written to it. */
        private final ItemStack potionStack;
        private final int potionCount;
        private final int emeraldCount;
        private final int maxUses;
        private final int xpValue;
        private final Item buyingItem;
        private final int buyingItemCount;
        private final float priceMultiplier;

        public ItemWithPotionForEmeraldsAndItemsTrade(Item buyingItem, int buyingItemCount, Item p_i50526_3_, int p_i50526_4_, int emeralds, int maxUses, int xpValue) {
            this.potionStack = new ItemStack(p_i50526_3_);
            this.emeraldCount = emeralds;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.buyingItem = buyingItem;
            this.buyingItemCount = buyingItemCount;
            this.potionCount = p_i50526_4_;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer getOffer(AlienEntity trader, Random rand) {
            ItemStack itemstack = new ItemStack(Items.EMERALD, this.emeraldCount);
            List<Potion> list = Registry.POTION.stream().filter((potion) -> {
                return !potion.getEffects().isEmpty() && PotionBrewing.isBrewablePotion(potion);
            }).collect(Collectors.toList());
            Potion potion = list.get(rand.nextInt(list.size()));
            ItemStack itemstack1 = PotionUtils.addPotionToItemStack(new ItemStack(this.potionStack.getItem(), this.potionCount), potion);
            return new MerchantOffer(itemstack, new ItemStack(this.buyingItem, this.buyingItemCount), itemstack1, maxUseIn, this.xpValue, this.priceMultiplier);
        }
    }

    static class ItemsForEmeraldsAndItemsTrade implements AlienTrade.ITrade {
        private final ItemStack buyingItem;
        private final int buyingItemCount;
        private final int emeraldCount;
        private final ItemStack sellingItem;
        private final int sellingItemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public ItemsForEmeraldsAndItemsTrade(IItemProvider buyingItem, int buyingItemCount, Item sellingItem, int sellingItemCount, int maxUses, int xpValue) {
            this(buyingItem, buyingItemCount, 1, sellingItem, sellingItemCount, maxUseIn, xpValue);
        }

        public ItemsForEmeraldsAndItemsTrade(IItemProvider buyingItem, int buyingItemCount, int emeraldCount, Item sellingItem, int sellingItemCount, int maxUses, int xpValue) {
            this.buyingItem = new ItemStack(buyingItem);
            this.buyingItemCount = buyingItemCount;
            this.emeraldCount = emeraldCount;
            this.sellingItem = new ItemStack(sellingItem);
            this.sellingItemCount = sellingItemCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        public MerchantOffer getOffer(AlienEntity trader, Random rand) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.buyingItem.getItem(), this.buyingItemCount), new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), maxUseIn, this.xpValue, this.priceMultiplier);
        }
    }

    static class ItemsForEmeraldsTrade implements AlienTrade.ITrade {
        private final ItemStack sellingItem;
        private final int emeraldCount;
        private final int sellingItemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public ItemsForEmeraldsTrade(Block sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue) {
            this(new ItemStack(sellingItem), emeraldCount, sellingItemCount, maxUseIn, xpValue);
        }

        public ItemsForEmeraldsTrade(Item sellingItem, int emeraldCount, int sellingItemCount, int xpValue) {
            this(new ItemStack(sellingItem), emeraldCount, sellingItemCount, maxUseIn, xpValue);
        }

        public ItemsForEmeraldsTrade(Item sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue) {
            this(new ItemStack(sellingItem), emeraldCount, sellingItemCount, maxUseIn, xpValue);
        }

        public ItemsForEmeraldsTrade(ItemStack sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue) {
            this(sellingItem, emeraldCount, sellingItemCount, maxUseIn, xpValue, 0.05F);
        }

        public ItemsForEmeraldsTrade(ItemStack sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue, float priceMultiplier) {
            this.sellingItem = sellingItem;
            this.emeraldCount = emeraldCount;
            this.sellingItemCount = sellingItemCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(AlienEntity trader, Random rand) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCount), new ItemStack(this.sellingItem.getItem(), this.sellingItemCount), maxUseIn, this.xpValue, this.priceMultiplier);
        }
    }

    static class SuspiciousStewForEmeraldTrade implements AlienTrade.ITrade {
        final Effect effect;
        final int duration;
        final int xpValue;
        private final float priceMultiplier;

        public SuspiciousStewForEmeraldTrade(Effect effectIn, int durationIn, int xpValue) {
            this.effect = effectIn;
            this.duration = durationIn;
            this.xpValue = xpValue;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        public MerchantOffer getOffer(AlienEntity trader, Random rand) {
            ItemStack itemstack = new ItemStack(Items.SUSPICIOUS_STEW, 1);
            SuspiciousStewItem.addEffect(itemstack, this.effect, this.duration);
            return new MerchantOffer(new ItemStack(Items.EMERALD, 1), itemstack, maxUseIn, this.xpValue, this.priceMultiplier);
        }
    }
}