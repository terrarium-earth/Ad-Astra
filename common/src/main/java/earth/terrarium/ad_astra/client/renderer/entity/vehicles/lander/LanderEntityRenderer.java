package earth.terrarium.ad_astra.client.renderer.entity.vehicles.lander;

import earth.terrarium.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;
import earth.terrarium.ad_astra.entities.vehicles.LanderEntity;
import earth.terrarium.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LanderEntityRenderer extends VehicleEntityRenderer<LanderEntity, LanderEntityModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/vehicles/lander.png");

    public LanderEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new LanderEntityModel(context.getPart(LanderEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(LanderEntity entity) {
        return TEXTURE;
    }
}
