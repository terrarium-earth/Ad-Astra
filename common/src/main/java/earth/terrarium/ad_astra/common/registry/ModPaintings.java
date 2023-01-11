package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.decoration.PaintingVariant;

@SuppressWarnings("unused")
public class ModPaintings {
    public static final ResourcefulRegistry<PaintingVariant> PAINTING_VARIANTS = ResourcefulRegistries.create(BuiltInRegistries.PAINTING_VARIANT, AdAstra.MOD_ID);

    public static final RegistryEntry<PaintingVariant> MERCURY = PAINTING_VARIANTS.register("mercury", () -> new PaintingVariant(16, 16));
    public static final RegistryEntry<PaintingVariant> MOON = PAINTING_VARIANTS.register("moon", () -> new PaintingVariant(16, 16));
    public static final RegistryEntry<PaintingVariant> PLUTO = PAINTING_VARIANTS.register("pluto", () -> new PaintingVariant(16, 16));
    public static final RegistryEntry<PaintingVariant> EARTH = PAINTING_VARIANTS.register("earth", () -> new PaintingVariant(32, 32));
    public static final RegistryEntry<PaintingVariant> GLACIO = PAINTING_VARIANTS.register("glacio", () -> new PaintingVariant(32, 32));
    public static final RegistryEntry<PaintingVariant> MARS = PAINTING_VARIANTS.register("mars", () -> new PaintingVariant(32, 32));
    public static final RegistryEntry<PaintingVariant> VENUS = PAINTING_VARIANTS.register("venus", () -> new PaintingVariant(32, 32));
    public static final RegistryEntry<PaintingVariant> JUPITER = PAINTING_VARIANTS.register("jupiter", () -> new PaintingVariant(48, 48));
    public static final RegistryEntry<PaintingVariant> NEPTUNE = PAINTING_VARIANTS.register("neptune", () -> new PaintingVariant(48, 48));
    public static final RegistryEntry<PaintingVariant> URANUS = PAINTING_VARIANTS.register("uranus", () -> new PaintingVariant(48, 48));
    public static final RegistryEntry<PaintingVariant> SATURN = PAINTING_VARIANTS.register("saturn", () -> new PaintingVariant(64, 48));
    public static final RegistryEntry<PaintingVariant> THE_MILKY_WAY = PAINTING_VARIANTS.register("the_milky_way", () -> new PaintingVariant(64, 48));
    public static final RegistryEntry<PaintingVariant> ALPHA_CENTAURI = PAINTING_VARIANTS.register("alpha_centauri", () -> new PaintingVariant(64, 64));
    public static final RegistryEntry<PaintingVariant> SUN = PAINTING_VARIANTS.register("sun", () -> new PaintingVariant(80, 80));
}
