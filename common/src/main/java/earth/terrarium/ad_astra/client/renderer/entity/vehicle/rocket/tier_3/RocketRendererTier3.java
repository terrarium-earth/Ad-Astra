package earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_3;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.VehicleRenderer;
import earth.terrarium.ad_astra.common.entity.vehicle.RocketTier3;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public class RocketRendererTier3 extends VehicleRenderer<RocketTier3, RocketModelTier3> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/vehicles/tier_3_rocket.png");

    public RocketRendererTier3(EntityRendererProvider.Context context) {
        super(context, new RocketModelTier3(context.bakeLayer(RocketModelTier3.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(RocketTier3 entity) {
        return TEXTURE;
    }
}
