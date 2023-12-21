package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.common.registry.ModDamageSources;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypeProvider {

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(ModDamageSources.OXYGEN, new DamageType("oxygen", 0.0f));
        context.register(ModDamageSources.CRYO_FUEL, new DamageType("cryo_fuel", 0.1f, DamageEffects.BURNING));
        context.register(ModDamageSources.ROCKET_FLAMES, new DamageType("rocket_flames", 0.1f, DamageEffects.BURNING));
        context.register(ModDamageSources.RAN_OVER, new DamageType("ran_over", 0.1f));
    }
}
