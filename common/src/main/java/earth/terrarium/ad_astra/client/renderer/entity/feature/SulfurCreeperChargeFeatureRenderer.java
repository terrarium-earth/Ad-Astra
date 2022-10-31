package earth.terrarium.ad_astra.client.renderer.entity.feature;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.SulfurCreeperEntityModel;
import earth.terrarium.ad_astra.entities.mobs.SulfurCreeperEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

public class SulfurCreeperChargeFeatureRenderer extends EnergySwirlLayer<SulfurCreeperEntity, SulfurCreeperEntityModel<SulfurCreeperEntity>> {
    private static final ResourceLocation SKIN = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final SulfurCreeperEntityModel<SulfurCreeperEntity> model;

    public SulfurCreeperChargeFeatureRenderer(RenderLayerParent<SulfurCreeperEntity, SulfurCreeperEntityModel<SulfurCreeperEntity>> context, EntityModelSet loader) {
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
    protected EntityModel<SulfurCreeperEntity> model() {
        return this.model;
    }
}
