package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_3;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleRenderer;
import earth.terrarium.ad_astra.entities.vehicles.RocketTier3;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RocketRendererTier3 extends VehicleRenderer<RocketTier3, RocketModelTier3> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/vehicles/tier_3_rocket.png");

    public RocketRendererTier3(EntityRendererProvider.Context context) {
        super(context, new RocketModelTier3(context.bakeLayer(RocketModelTier3.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketTier3 entity) {
        return TEXTURE;
    }
}
