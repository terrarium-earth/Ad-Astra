package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_3;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;
import earth.terrarium.ad_astra.entities.vehicles.RocketEntityTier3;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RocketEntityRendererTier3 extends VehicleEntityRenderer<RocketEntityTier3, RocketEntityModelTier3> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/vehicles/tier_3_rocket.png");

    public RocketEntityRendererTier3(EntityRendererProvider.Context context) {
        super(context, new RocketEntityModelTier3(context.bakeLayer(RocketEntityModelTier3.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketEntityTier3 entity) {
        return TEXTURE;
    }
}
