package earth.terrarium.ad_astra.registry;

import dev.architectury.registry.registries.DeferredRegister;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ModPaintings {

    public static final DeferredRegister<PaintingVariant> PAINTINGS = DeferredRegister.create(AdAstra.MOD_ID, Registry.PAINTING_VARIANT_KEY);

    public static void register() {
        register("mercury", 16, 16);
        register("moon", 16, 16);
        register("pluto", 16, 16);
        register("earth", 32, 32);
        register("glacio", 32, 32);
        register("mars", 32, 32);
        register("venus", 32, 32);
        register("jupiter", 48, 48);
        register("neptune", 48, 48);
        register("uranus", 48, 48);
        register("saturn", 64, 48);
        register("the_milky_way", 64, 48);
        register("alpha_centaury_c", 64, 64);
        register("sun", 80, 80);
        PAINTINGS.register();
    }

    private static Supplier<PaintingVariant> register(String id, int width, int height) {
        return PAINTINGS.register(id, () -> new PaintingVariant(width, height));
    }
}
