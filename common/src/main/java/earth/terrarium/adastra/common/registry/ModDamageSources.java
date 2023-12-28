package earth.terrarium.adastra.common.registry;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public final class ModDamageSources {
    public static final ResourceKey<DamageType> OXYGEN = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(AdAstra.MOD_ID, "oxygen"));
    public static final ResourceKey<DamageType> CRYO_FUEL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(AdAstra.MOD_ID, "cryo_fuel"));
    public static final ResourceKey<DamageType> RAN_OVER = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(AdAstra.MOD_ID, "ran_over"));
    public static final ResourceKey<DamageType> ROCKET_FLAMES = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(AdAstra.MOD_ID, "rocket_flames"));
    public static final ResourceKey<DamageType> ACID_RAIN = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(AdAstra.MOD_ID, "acid_rain"));

    public static DamageSource create(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }

    public static DamageSource ranOver(Level level, @Nullable Entity vehicle, @Nullable Entity directEntity) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(RAN_OVER), vehicle, directEntity);
    }
}
