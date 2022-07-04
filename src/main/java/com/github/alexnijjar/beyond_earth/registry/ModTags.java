package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModTags {
	public static final TagKey<EntityType<?>> FIRE_IMMUNE = TagKey.of(Registry.ENTITY_TYPE_KEY, new ModIdentifier("entities/fire_immune"));
	public static final TagKey<EntityType<?>> LIVES_WITHOUT_OXYGEN = TagKey.of(Registry.ENTITY_TYPE_KEY, new ModIdentifier("entities/lives_without_oxygen"));

	public static final TagKey<Item> CALORITE_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:calorite_ingots"));
    public static final TagKey<Item> CALORITE_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:calorite_nuggets"));
    public static final TagKey<Item> CHEESES = TagKey.of(Registry.ITEM_KEY, new Identifier("c:cheeses"));
    public static final TagKey<Item> COMPRESSED_CALORITE = TagKey.of(Registry.ITEM_KEY, new Identifier("c:compressed_calorite"));
    public static final TagKey<Item> COMPRESSED_DESH = TagKey.of(Registry.ITEM_KEY, new Identifier("c:compressed_desh"));
    public static final TagKey<Item> COMPRESSED_OSTRUM = TagKey.of(Registry.ITEM_KEY, new Identifier("c:compressed_ostrum"));
    public static final TagKey<Item> COMPRESSED_STEEL = TagKey.of(Registry.ITEM_KEY, new Identifier("c:compressed_steel"));
    public static final TagKey<Item> DESH_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:desh_ingots"));
    public static final TagKey<Item> DESH_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:desh_nuggets"));
    public static final TagKey<Item> DESH_PLATES = TagKey.of(Registry.ITEM_KEY, new Identifier("c:desh_plates"));
    public static final TagKey<Item> IRON_PLATES = TagKey.of(Registry.ITEM_KEY, new Identifier("c:iron_plates"));
    public static final TagKey<Item> IRON_RODS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:iron_rods"));
    public static final TagKey<Item> OSTRUM_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:ostrum_ingots"));
    public static final TagKey<Item> OSTRUM_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:ostrum_nuggets"));
    public static final TagKey<Item> RAW_CALORITE_ORES = TagKey.of(Registry.ITEM_KEY, new Identifier("c:raw_calorite_ores"));
    public static final TagKey<Item> RAW_DESH_ORES = TagKey.of(Registry.ITEM_KEY, new Identifier("c:raw_desh_ores"));
    public static final TagKey<Item> RAW_OSTRUM_ORES = TagKey.of(Registry.ITEM_KEY, new Identifier("c:raw_ostrum_ores"));
    public static final TagKey<Item> STEEL_INGOTS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:steel_ingots"));
	public static final TagKey<Item> STEEL_NUGGETS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:steel_nuggets"));
	
    public static final TagKey<Item> STEEL_BLOCKS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:steel_blocks"));
    public static final TagKey<Item> DESH_BLOCKS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:desh_blocks"));
    public static final TagKey<Item> OSTRUM_BLOCKS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:ostrum_blocks"));
    public static final TagKey<Item> CALORITE_BLOCKS = TagKey.of(Registry.ITEM_KEY, new Identifier("c:calorite_blocks"));
}
