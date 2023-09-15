package earth.terrarium.adastra.common.tags;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public final class ModFluidTags {
    public static final TagKey<Fluid> OXYGEN = tag("oxygen");
    public static final TagKey<Fluid> HYDROGEN = tag("hydrogen");
    public static final TagKey<Fluid> OIL = tag("oil");
    public static final TagKey<Fluid> FUEL = tag("fuel");

    public static final TagKey<Fluid> ZIP_GUN_PROPELLANTS = tag("zip_gun_propellants");

    private static TagKey<Fluid> tag(String name) {
        return TagKey.create(Registries.FLUID, new ResourceLocation(AdAstra.MOD_ID, name));
    }
}
