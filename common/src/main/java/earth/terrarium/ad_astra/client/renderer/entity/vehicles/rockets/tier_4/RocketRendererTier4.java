package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_4;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleRenderer;
import earth.terrarium.ad_astra.entities.vehicles.RocketTier4;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RocketRendererTier4 extends VehicleRenderer<RocketTier4, RocketModelTier4> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/vehicles/tier_4_rocket.png");

    public RocketRendererTier4(EntityRendererProvider.Context context) {
        super(context, new RocketModelTier4(context.bakeLayer(RocketModelTier4.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketTier4 entity) {
        return TEXTURE;
    }
}
