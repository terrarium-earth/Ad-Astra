package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import com.google.common.collect.ImmutableMap;
import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.PygroModel;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class PygroRenderer extends PiglinRenderer {
    private static final Map<EntityType<?>, ResourceLocation> TEXTURES = ImmutableMap.of(ModEntityTypes.ZOMBIFIED_PYGRO.get(), new ModResourceLocation("textures/entity/zombified_pygro.png"), ModEntityTypes.PYGRO.get(), new ModResourceLocation("textures/entity/pygro.png"), ModEntityTypes.PYGRO_BRUTE.get(), new ModResourceLocation("textures/entity/pygro_brute.png"));

    public PygroRenderer(EntityRendererProvider.Context context) {
        super(context, PygroModel.LAYER_LOCATION, ModelLayers.PIGLIN_INNER_ARMOR, ModelLayers.PIGLIN_OUTER_ARMOR, false);
    }

    @Override
    public ResourceLocation getTextureLocation(Mob mobEntity) {
        ResourceLocation resourceLocation = TEXTURES.get(mobEntity.getType());
        if (resourceLocation == null) {
            throw new IllegalArgumentException("I don't know what texture to use for " + mobEntity.getType());
        }
        return resourceLocation;
    }
}
