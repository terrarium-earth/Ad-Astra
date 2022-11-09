package earth.terrarium.ad_astra.client.renderer.entity.vehicles.lander;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleRenderer;
import earth.terrarium.ad_astra.entities.vehicles.Lander;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class LanderRenderer extends VehicleRenderer<Lander, LanderModel> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/vehicles/lander.png");

    public LanderRenderer(EntityRendererProvider.Context context) {
        super(context, new LanderModel(context.bakeLayer(LanderModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Lander entity) {
        return TEXTURE;
    }
}
