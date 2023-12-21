package earth.terrarium.adastra.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.Supplier;

public class ClientPlatformUtils {

    @FunctionalInterface
    public interface SpriteParticleRegistration<T extends ParticleOptions> {
        ParticleProvider<T> create(SpriteSet spriteSet);
    }

    @FunctionalInterface
    public interface LayerDefinitionRegistry {
        void register(ModelLayerLocation location, Supplier<LayerDefinition> definition);
    }

    @FunctionalInterface
    public interface RenderHud {
        void renderHud(GuiGraphics graphics, float partialTick);
    }

    @ExpectPlatform
    public static BakedModel getModel(ModelManager dispatcher, ResourceLocation id) {
        throw new NotImplementedException();
    }
}
