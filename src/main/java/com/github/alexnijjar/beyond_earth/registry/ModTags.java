package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class ModTags {
	public static final TagKey<EntityType<?>> FIRE_IMMUNE = TagKey.of(Registry.ENTITY_TYPE_KEY, new ModIdentifier("entities/fire_immune"));
	public static final TagKey<EntityType<?>> LIVES_WITHOUT_OXYGEN = TagKey.of(Registry.ENTITY_TYPE_KEY, new ModIdentifier("entities/lives_without_oxygen"));
}
