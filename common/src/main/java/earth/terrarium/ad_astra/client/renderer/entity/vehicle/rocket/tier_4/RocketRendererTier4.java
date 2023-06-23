package earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_4;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.VehicleRenderer;
import earth.terrarium.ad_astra.common.entity.vehicle.RocketTier4;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public class RocketRendererTier4 extends VehicleRenderer<RocketTier4, RocketModelTier4> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/vehicles/tier_4_rocket.png");

    public RocketRendererTier4(EntityRendererProvider.Context context) {
        super(context, new RocketModelTier4(context.bakeLayer(RocketModelTier4.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(RocketTier4 entity) {
        return TEXTURE;
    }
}
