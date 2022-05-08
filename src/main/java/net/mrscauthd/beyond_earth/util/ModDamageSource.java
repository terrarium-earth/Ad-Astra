package net.mrscauthd.beyond_earth.util;

import net.minecraft.entity.damage.DamageSource;

public class ModDamageSource extends DamageSource {

    // Custom death sources.
    public static final DamageSource OXYGEN = new ModDamageSource("oxygen").setBypassesArmor();
    public static final DamageSource ACID_RAIN = new ModDamageSource("acid_rain");

    public ModDamageSource(String name) {
        super(name);
    }
}
