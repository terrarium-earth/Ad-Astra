package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_4;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;
import earth.terrarium.ad_astra.entities.vehicles.RocketEntityTier4;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RocketEntityRendererTier4 extends VehicleEntityRenderer<RocketEntityTier4, RocketEntityModelTier4> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/vehicles/tier_4_rocket.png");

    public RocketEntityRendererTier4(EntityRendererProvider.Context context) {
        super(context, new RocketEntityModelTier4(context.bakeLayer(RocketEntityModelTier4.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketEntityTier4 entity) {
        return TEXTURE;
    }
}
