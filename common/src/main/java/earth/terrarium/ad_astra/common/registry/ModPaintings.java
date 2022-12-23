package earth.terrarium.ad_astra.common.registry;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.botarium.api.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModPaintings {
    public static final RegistryHolder<PaintingVariant> PAINTING_VARIENTS = new RegistryHolder<>(Registry.PAINTING_VARIANT, AdAstra.MOD_ID);

    public static final Supplier<PaintingVariant> MERCURY = PAINTING_VARIENTS.register("mercury", () -> new PaintingVariant(16, 16));
    public static final Supplier<PaintingVariant> MOON = PAINTING_VARIENTS.register("moon", () -> new PaintingVariant(16, 16));
    public static final Supplier<PaintingVariant> PLUTO = PAINTING_VARIENTS.register("pluto", () -> new PaintingVariant(16, 16));
    public static final Supplier<PaintingVariant> EARTH = PAINTING_VARIENTS.register("earth", () -> new PaintingVariant(32, 32));
    public static final Supplier<PaintingVariant> GLACIO = PAINTING_VARIENTS.register("glacio", () -> new PaintingVariant(32, 32));
    public static final Supplier<PaintingVariant> MARS = PAINTING_VARIENTS.register("mars", () -> new PaintingVariant(32, 32));
    public static final Supplier<PaintingVariant> VENUS = PAINTING_VARIENTS.register("venus", () -> new PaintingVariant(32, 32));
    public static final Supplier<PaintingVariant> JUPITER = PAINTING_VARIENTS.register("jupiter", () -> new PaintingVariant(48, 48));
    public static final Supplier<PaintingVariant> NEPTUNE = PAINTING_VARIENTS.register("neptune", () -> new PaintingVariant(48, 48));
    public static final Supplier<PaintingVariant> URANUS = PAINTING_VARIENTS.register("uranus", () -> new PaintingVariant(48, 48));
    public static final Supplier<PaintingVariant> SATURN = PAINTING_VARIENTS.register("saturn", () -> new PaintingVariant(64, 48));
    public static final Supplier<PaintingVariant> THE_MILKY_WAY = PAINTING_VARIENTS.register("the_milky_way", () -> new PaintingVariant(64, 48));
    public static final Supplier<PaintingVariant> ALPHA_CENTAURI = PAINTING_VARIENTS.register("alpha_centauri", () -> new PaintingVariant(64, 64));
    public static final Supplier<PaintingVariant> SUN = PAINTING_VARIENTS.register("sun", () -> new PaintingVariant(80, 80));
}
