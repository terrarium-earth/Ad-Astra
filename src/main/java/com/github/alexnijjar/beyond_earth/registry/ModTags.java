package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class ModTags {
	public static final TagKey<EntityType<?>> FIRE_IMMUNE_TAG = TagKey.of(Registry.ENTITY_TYPE_KEY, new ModIdentifier("entities/fire_immune"));
}
