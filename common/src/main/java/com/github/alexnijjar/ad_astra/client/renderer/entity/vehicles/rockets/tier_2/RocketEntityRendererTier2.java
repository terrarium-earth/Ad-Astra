package com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_2;

import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.VehicleEntityRenderer;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier2;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RocketEntityRendererTier2 extends VehicleEntityRenderer<RocketEntityTier2, RocketEntityModelTier2> {
	public static final Identifier TEXTURE = new ModIdentifier("textures/vehicles/tier_2_rocket.png");

	public RocketEntityRendererTier2(EntityRendererFactory.Context context) {
		super(context, new RocketEntityModelTier2(context.getPart(RocketEntityModelTier2.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public Identifier getTexture(RocketEntityTier2 entity) {
		return TEXTURE;
	}
}
