package earth.terrarium.ad_astra.client.renderer.entity.vehicles.lander;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;
import earth.terrarium.ad_astra.entities.vehicles.LanderEntity;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class LanderEntityRenderer extends VehicleEntityRenderer<LanderEntity, LanderEntityModel> {
    public static final ResourceLocation TEXTURE = new ModResourceLocation("textures/vehicles/lander.png");

    public LanderEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new LanderEntityModel(context.bakeLayer(LanderEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(LanderEntity entity) {
        return TEXTURE;
    }
}
