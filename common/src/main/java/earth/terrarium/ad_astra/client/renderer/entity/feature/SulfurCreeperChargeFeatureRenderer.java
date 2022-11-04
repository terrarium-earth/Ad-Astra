package earth.terrarium.ad_astra.client.renderer.entity.feature;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.SulfurCreeperEntityModel;
import earth.terrarium.ad_astra.entities.mobs.SulfurCreeper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

public class SulfurCreeperChargeFeatureRenderer extends EnergySwirlLayer<SulfurCreeper, SulfurCreeperEntityModel<SulfurCreeper>> {
    private static final ResourceLocation SKIN = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final SulfurCreeperEntityModel<SulfurCreeper> model;

    public SulfurCreeperChargeFeatureRenderer(RenderLayerParent<SulfurCreeper, SulfurCreeperEntityModel<SulfurCreeper>> context, EntityModelSet loader) {
        super(context);
        this.model = new SulfurCreeperEntityModel<>(loader.bakeLayer(SulfurCreeperEntityModel.LAYER_LOCATION));
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
