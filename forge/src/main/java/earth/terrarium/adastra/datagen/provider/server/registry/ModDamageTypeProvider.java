package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.common.registry.ModDamageSources;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypeProvider {

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(ModDamageSources.OXYGEN, new DamageType("oxygen", 0.0f));
    }
}
