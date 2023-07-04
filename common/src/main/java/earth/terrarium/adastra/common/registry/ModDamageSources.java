package earth.terrarium.adastra.common.registry;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class ModDamageSources {
    private static final Map<ResourceKey<DamageType>, DamageSource> DAMAGE_SOURCES = new HashMap<>();

    public static final ResourceKey<DamageType> OXYGEN = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(AdAstra.MOD_ID, "oxygen"));

    public static DamageSource getOrCreate(Level level, ResourceKey<DamageType> key) {
        return DAMAGE_SOURCES.computeIfAbsent(key, k -> new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key)));
    }

    public static void clear() {
        DAMAGE_SOURCES.clear();
    }
}
