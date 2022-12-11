package earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_1;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.VehicleRenderer;
import earth.terrarium.ad_astra.common.entity.vehicle.RocketTier1;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class RocketRendererTier1 extends VehicleRenderer<RocketTier1, RocketModelTier1> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/vehicles/tier_1_rocket.png");

    public RocketRendererTier1(EntityRendererProvider.Context context) {
        super(context, new RocketModelTier1(context.bakeLayer(RocketModelTier1.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(RocketTier1 entity) {
        return TEXTURE;
    }
}
