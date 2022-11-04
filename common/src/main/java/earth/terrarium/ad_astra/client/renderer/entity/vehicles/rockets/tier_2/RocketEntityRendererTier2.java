package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_2;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;
import earth.terrarium.ad_astra.entities.vehicles.RocketTier2;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RocketEntityRendererTier2 extends VehicleEntityRenderer<RocketTier2, RocketEntityModelTier2> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/vehicles/tier_2_rocket.png");

    public RocketEntityRendererTier2(EntityRendererProvider.Context context) {
        super(context, new RocketEntityModelTier2(context.bakeLayer(RocketEntityModelTier2.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketTier2 entity) {
        return TEXTURE;
    }
}
