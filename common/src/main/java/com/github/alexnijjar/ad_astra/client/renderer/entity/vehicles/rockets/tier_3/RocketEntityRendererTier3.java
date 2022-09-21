package com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_3;

import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier3;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RocketEntityRendererTier3 extends VehicleEntityRenderer<RocketEntityTier3, RocketEntityModelTier3> {
	public static final Identifier TEXTURE = new ModIdentifier("textures/vehicles/tier_3_rocket.png");

	public RocketEntityRendererTier3(EntityRendererFactory.Context context) {
		super(context, new RocketEntityModelTier3(context.getPart(RocketEntityModelTier3.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public Identifier getTexture(RocketEntityTier3 entity) {
		return TEXTURE;
	}
}
