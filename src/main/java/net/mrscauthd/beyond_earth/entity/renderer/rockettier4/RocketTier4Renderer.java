package net.mrscauthd.beyond_earth.entity.renderer.rockettier4;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.RocketTier4Entity;

public class RocketTier4Renderer extends MobRenderer<RocketTier4Entity, RocketTier4Model<RocketTier4Entity>> {
    public RocketTier4Renderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new RocketTier4Model<>(renderManagerIn.bakeLayer(RocketTier4Model.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketTier4Entity p_114482_) {
        return new ResourceLocation(BeyondEarthMod.MODID, "textures/vehicles/rocket_t4.png");
    }
}