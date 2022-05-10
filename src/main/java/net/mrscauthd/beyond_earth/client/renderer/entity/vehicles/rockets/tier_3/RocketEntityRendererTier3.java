package net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.rockets.tier_3;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.VehicleEntityRenderer;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntityTier3;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class RocketEntityRendererTier3 extends VehicleEntityRenderer<RocketEntityTier3, RocketEntityModelTier3> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/vehicles/rocket_t3.png");

    public RocketEntityRendererTier3(EntityRendererFactory.Context context) {
        super(context, new RocketEntityModelTier3(context.getPart(RocketEntityModelTier3.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(RocketEntityTier3 entity) {
        return TEXTURE;
    }
}
