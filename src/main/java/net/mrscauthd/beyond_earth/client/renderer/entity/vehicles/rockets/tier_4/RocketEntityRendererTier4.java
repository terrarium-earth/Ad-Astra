package net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.rockets.tier_4;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.VehicleEntityRenderer;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntityTier4;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class RocketEntityRendererTier4 extends VehicleEntityRenderer<RocketEntityTier4, RocketEntityModelTier4> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/vehicles/rocket_t4.png");

    public RocketEntityRendererTier4(EntityRendererFactory.Context context) {
        super(context, new RocketEntityModelTier4(context.getPart(RocketEntityModelTier4.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(RocketEntityTier4 entity) {
        return TEXTURE;
    }
}
