package net.mrscauthd.beyond_earth.util;

import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.BeyondEarth;

public class ModIdentifier extends Identifier {

    public ModIdentifier(String path) {
        super(BeyondEarth.MOD_ID, path);
    }
}

