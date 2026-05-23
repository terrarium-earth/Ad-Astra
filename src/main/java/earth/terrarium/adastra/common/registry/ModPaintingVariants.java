package earth.terrarium.adastra.common.registry;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ModPaintingVariants {
    public static final List<ResourceKey<PaintingVariant>> PAINTING_VARIANTS = new ArrayList<>();

    public static final ResourceKey<PaintingVariant> MERCURY = create("mercury");
    public static final ResourceKey<PaintingVariant> MOON = create("moon");
    public static final ResourceKey<PaintingVariant> PLUTO = create("pluto");
    public static final ResourceKey<PaintingVariant> EARTH = create("earth");
    public static final ResourceKey<PaintingVariant> GLACIO = create("glacio");
    public static final ResourceKey<PaintingVariant> MARS = create("mars");
    public static final ResourceKey<PaintingVariant> VENUS = create("venus");
    public static final ResourceKey<PaintingVariant> JUPITER = create("jupiter");
    public static final ResourceKey<PaintingVariant> NEPTUNE = create("neptune");
    public static final ResourceKey<PaintingVariant> URANUS = create("uranus");
    public static final ResourceKey<PaintingVariant> SATURN = create("saturn");
    public static final ResourceKey<PaintingVariant> THE_MILKY_WAY = create("the_milky_way");
    public static final ResourceKey<PaintingVariant> ALPHA_CENTAURI = create("alpha_centauri");
    public static final ResourceKey<PaintingVariant> SUN = create("sun");

    public static void bootstrap(BootstrapContext<PaintingVariant> context) {
        register(context, MERCURY, 1, 1);
        register(context, MOON, 1, 1);
        register(context, PLUTO, 1, 1);
        register(context, EARTH, 2, 2);
        register(context, GLACIO, 2, 2);
        register(context, MARS, 2, 2);
        register(context, VENUS, 2, 2);
        register(context, JUPITER, 3, 3);
        register(context, NEPTUNE, 3, 3);
        register(context, URANUS, 3, 3);
        register(context, SATURN, 4, 3);
        register(context, THE_MILKY_WAY, 4, 3);
        register(context, ALPHA_CENTAURI, 4, 4);
        register(context, SUN, 5, 5);
    }

    private static void register(BootstrapContext<PaintingVariant> context, ResourceKey<PaintingVariant> key, int width, int height) {
        context.register(key, new PaintingVariant(width, height, key.location()));
    }

    private static ResourceKey<PaintingVariant> create(String name) {
        ResourceKey<PaintingVariant> key = ResourceKey.create(Registries.PAINTING_VARIANT, ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, name));
        PAINTING_VARIANTS.add(key);
        return key;
    }
}
