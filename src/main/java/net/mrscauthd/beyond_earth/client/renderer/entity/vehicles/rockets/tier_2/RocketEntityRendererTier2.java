package net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.rockets.tier_2;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.VehicleEntityRenderer;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntityTier2;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class RocketEntityRendererTier2 extends VehicleEntityRenderer<RocketEntityTier2, RocketEntityModelTier2> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/vehicles/rocket_t2.png");

    public RocketEntityRendererTier2(EntityRendererFactory.Context context) {
        super(context, new RocketEntityModelTier2(context.getPart(RocketEntityModelTier2.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(RocketEntityTier2 entity) {
        return TEXTURE;
    }
}
