package net.mrscauthd.beyond_earth.entity.renderer.rockettier1;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.RocketTier1Entity;
import net.mrscauthd.beyond_earth.entity.renderer.VehicleRenderer;

@OnlyIn(Dist.CLIENT)
public class RocketTier1Renderer extends VehicleRenderer<RocketTier1Entity, RocketTier1Model<RocketTier1Entity>> {
    public RocketTier1Renderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new RocketTier1Model<>(renderManagerIn.bakeLayer(RocketTier1Model.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketTier1Entity p_114482_) {
        return new ResourceLocation(BeyondEarthMod.MODID, "textures/vehicles/rocket_t1.png");
    }
}