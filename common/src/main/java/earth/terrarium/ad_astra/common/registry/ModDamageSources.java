package earth.terrarium.ad_astra.common.registry;

import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class ModDamageSources {
    public static final ResourceKey<DamageType> OXYGEN = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(AdAstra.MOD_ID, "oxygen"));
    public static final ResourceKey<DamageType> ROCKET_FLAMES = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(AdAstra.MOD_ID, "rocket_flames"));
    public static final ResourceKey<DamageType> CRYO_FUEL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(AdAstra.MOD_ID, "cryo_fuel"));
    public static final ResourceKey<DamageType> ACID_RAIN = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(AdAstra.MOD_ID, "acid_rain"));

    public static DamageSource of(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }
}
