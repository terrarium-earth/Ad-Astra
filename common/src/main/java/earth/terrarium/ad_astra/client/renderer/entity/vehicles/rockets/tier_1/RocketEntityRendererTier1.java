package earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_1;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;
import earth.terrarium.ad_astra.entities.vehicles.RocketTier1;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RocketEntityRendererTier1 extends VehicleEntityRenderer<RocketTier1, RocketEntityModelTier1> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/vehicles/tier_1_rocket.png");

    public RocketEntityRendererTier1(EntityRendererProvider.Context context) {
        super(context, new RocketEntityModelTier1(context.bakeLayer(RocketEntityModelTier1.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketTier1 entity) {
        return TEXTURE;
    }
}
