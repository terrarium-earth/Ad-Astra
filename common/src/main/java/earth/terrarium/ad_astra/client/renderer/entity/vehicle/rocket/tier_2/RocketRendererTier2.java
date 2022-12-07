package earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_2;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.VehicleRenderer;
import earth.terrarium.ad_astra.entity.vehicle.RocketTier2;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class RocketRendererTier2 extends VehicleRenderer<RocketTier2, RocketModelTier2> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/vehicles/tier_2_rocket.png");

    public RocketRendererTier2(EntityRendererProvider.Context context) {
        super(context, new RocketModelTier2(context.bakeLayer(RocketModelTier2.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(RocketTier2 entity) {
        return TEXTURE;
    }
}
