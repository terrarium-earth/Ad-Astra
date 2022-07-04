package com.github.alexnijjar.beyond_earth.client.screens.utils;

import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public record Category(Identifier id, Category parent) {

        public static final Category BACK = new Category(new ModIdentifier("back"), null);

        public static final Category MILKY_WAY_CATEGORY = new Category(new ModIdentifier("milky_way"), null);
        public static final Category SOLAR_SYSTEM_CATEGORY = new Category(new ModIdentifier("solar_system"), MILKY_WAY_CATEGORY);
        public static final Category PROXIMA_CENTAURI_CATEGORY = new Category(new ModIdentifier("proxima_centauri"), MILKY_WAY_CATEGORY);

        public static final Category EARTH_CATEGORY = new Category(new ModIdentifier("earth"), SOLAR_SYSTEM_CATEGORY);
        public static final Category MARS_CATEGORY = new Category(new ModIdentifier("mars"), SOLAR_SYSTEM_CATEGORY);
        public static final Category VENUS_CATEGORY = new Category(new ModIdentifier("venus"), SOLAR_SYSTEM_CATEGORY);
        public static final Category MERCURY_CATEGORY = new Category(new ModIdentifier("mercury"), SOLAR_SYSTEM_CATEGORY);
        public static final Category GLACIO_CATEGORY = new Category(new ModIdentifier("glacio"), PROXIMA_CENTAURI_CATEGORY);
}