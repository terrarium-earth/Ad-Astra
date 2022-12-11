package earth.terrarium.ad_astra.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.function.Supplier;

public class ModPaintings {
    public static final Supplier<PaintingVariant> MERCURY = register("mercury", () -> new PaintingVariant(16, 16));
    public static final Supplier<PaintingVariant> MOON = register("moon", () -> new PaintingVariant(16, 16));
    public static final Supplier<PaintingVariant> PLUTO = register("pluto", () -> new PaintingVariant(16, 16));
    public static final Supplier<PaintingVariant> EARTH = register("earth", () -> new PaintingVariant(32, 32));
    public static final Supplier<PaintingVariant> GLACIO = register("glacio", () -> new PaintingVariant(32, 32));
    public static final Supplier<PaintingVariant> MARS = register("mars", () -> new PaintingVariant(32, 32));
    public static final Supplier<PaintingVariant> VENUS = register("venus", () -> new PaintingVariant(32, 32));
    public static final Supplier<PaintingVariant> JUPITER = register("jupiter", () -> new PaintingVariant(48, 48));
    public static final Supplier<PaintingVariant> NEPTUNE = register("neptune", () -> new PaintingVariant(48, 48));
    public static final Supplier<PaintingVariant> URANUS = register("uranus", () -> new PaintingVariant(48, 48));
    public static final Supplier<PaintingVariant> SATURN = register("saturn", () -> new PaintingVariant(64, 48));
    public static final Supplier<PaintingVariant> THE_MILKY_WAY = register("the_milky_way", () -> new PaintingVariant(64, 48));
    public static final Supplier<PaintingVariant> ALPHA_CENTAURI = register("alpha_centauri", () -> new PaintingVariant(64, 64));
    public static final Supplier<PaintingVariant> SUN = register("sun", () -> new PaintingVariant(80, 80));

    private static <T extends PaintingVariant> Supplier<T> register(String id, Supplier<T> object) {
        return ModRegistryHelpers.register(Registry.PAINTING_VARIANT, id, object);
    }

    public static void init() {
    }
}
