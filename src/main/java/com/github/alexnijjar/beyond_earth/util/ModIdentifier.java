package com.github.alexnijjar.beyond_earth.util;

import com.github.alexnijjar.beyond_earth.BeyondEarth;

import net.minecraft.util.Identifier;

public class ModIdentifier extends Identifier {
    public ModIdentifier(String path) {
        super(BeyondEarth.MOD_ID, path);
    }
}