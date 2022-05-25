package com.github.alexnijjar.beyond_earth.client.renderer.entity.vehicles.rockets.tier_1;

import com.github.alexnijjar.beyond_earth.client.renderer.entity.vehicles.VehicleEntityRenderer;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier1;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RocketEntityRendererTier1 extends VehicleEntityRenderer<RocketEntityTier1, RocketEntityModelTier1> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/vehicles/rocket_t1.png");

    public RocketEntityRendererTier1(EntityRendererFactory.Context context) {
        super(context, new RocketEntityModelTier1(context.getPart(RocketEntityModelTier1.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public Identifier getTexture(RocketEntityTier1 entity) {
        return TEXTURE;
    }
}
