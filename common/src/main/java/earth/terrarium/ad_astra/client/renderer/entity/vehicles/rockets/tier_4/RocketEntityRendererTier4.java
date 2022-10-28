package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_4;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;
import earth.terrarium.ad_astra.entities.vehicles.RocketEntityTier4;
import earth.terrarium.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RocketEntityRendererTier4 extends VehicleEntityRenderer<RocketEntityTier4, RocketEntityModelTier4> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/vehicles/tier_4_rocket.png");

    public RocketEntityRendererTier4(EntityRendererFactory.Context context) {
        super(context, new RocketEntityModelTier4(context.getPart(RocketEntityModelTier4.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(RocketEntityTier4 entity) {
        return TEXTURE;
    }
}
