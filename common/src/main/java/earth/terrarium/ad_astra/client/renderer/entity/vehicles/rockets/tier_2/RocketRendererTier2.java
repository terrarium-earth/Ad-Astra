package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_2;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleRenderer;
import earth.terrarium.ad_astra.entities.vehicles.RocketTier2;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RocketRendererTier2 extends VehicleRenderer<RocketTier2, RocketModelTier2> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/vehicles/tier_2_rocket.png");

    public RocketRendererTier2(EntityRendererProvider.Context context) {
        super(context, new RocketModelTier2(context.bakeLayer(RocketModelTier2.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketTier2 entity) {
        return TEXTURE;
    }
}
