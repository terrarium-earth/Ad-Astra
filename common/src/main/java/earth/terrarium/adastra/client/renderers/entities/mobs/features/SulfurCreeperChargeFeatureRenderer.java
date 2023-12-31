package earth.terrarium.adastra.client.renderers.entities.mobs.features;

import earth.terrarium.adastra.client.models.entities.mobs.SulfurCreeperModel;
import earth.terrarium.adastra.common.entities.mob.SulfurCreeper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

// LEGACY ENTITY. WILL BE REPLACED IN THE FUTURE.
public class SulfurCreeperChargeFeatureRenderer extends EnergySwirlLayer<SulfurCreeper, SulfurCreeperModel<SulfurCreeper>> {
    private static final ResourceLocation SKIN = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final SulfurCreeperModel<SulfurCreeper> model;

    public SulfurCreeperChargeFeatureRenderer(RenderLayerParent<SulfurCreeper, SulfurCreeperModel<SulfurCreeper>> context, EntityModelSet loader) {
        super(context);
        this.model = new SulfurCreeperModel<>(loader.bakeLayer(SulfurCreeperModel.LAYER_LOCATION));
    }

    @Override
    protected float xOffset(float partialAge) {
        return partialAge * 0.01f;
    }

    @Override
    protected ResourceLocation getTextureLocation() {
        return SKIN;
    }

    @Override
    protected EntityModel<SulfurCreeper> model() {
        return this.model;
    }
}
