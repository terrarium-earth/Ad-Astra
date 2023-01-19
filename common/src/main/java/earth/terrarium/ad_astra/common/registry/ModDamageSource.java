package earth.terrarium.ad_astra.common.registry;

import net.minecraft.world.damagesource.DamageSource;

public class ModDamageSource extends DamageSource {

    // Custom death sources
    public static final DamageSource OXYGEN = new ModDamageSource("oxygen").bypassArmor();
    public static final DamageSource ROCKET_FLAMES = new ModDamageSource("rocket_flames").setIsFire();
    public static final DamageSource CRYOGENIC_FUEL = new ModDamageSource("cryo_fuel");
    public static final DamageSource ACID_RAIN = new ModDamageSource("acid_rain");

    public ModDamageSource(String name) {
        super(name);
    }
}
