package earth.terrarium.ad_astra.client.renderer.entity.vehicle.lander;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.VehicleRenderer;
import earth.terrarium.ad_astra.common.entity.vehicle.Lander;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public class LanderRenderer extends VehicleRenderer<Lander, LanderModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/vehicles/lander.png");

    public LanderRenderer(EntityRendererProvider.Context context) {
        super(context, new LanderModel(context.bakeLayer(LanderModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Lander entity) {
        return TEXTURE;
    }
}
