package com.github.alexnijjar.ad_astra.util;

import com.github.alexnijjar.ad_astra.AdAstra;

import net.minecraft.util.Identifier;

public class ModIdentifier extends Identifier {
    public ModIdentifier(String path) {
        super(AdAstra.MOD_ID, path);
    }
}