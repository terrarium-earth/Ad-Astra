package net.mrscauthd.beyond_earth.entity.renderer.rockettier2;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.RocketTier2Entity;
import net.mrscauthd.beyond_earth.entity.renderer.VehicleRenderer;

@OnlyIn(Dist.CLIENT)
public class RocketTier2Renderer extends VehicleRenderer<RocketTier2Entity, RocketTier2Model<RocketTier2Entity>> {
    public RocketTier2Renderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new RocketTier2Model<>(renderManagerIn.bakeLayer(RocketTier2Model.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketTier2Entity p_114482_) {
        return new ResourceLocation(BeyondEarthMod.MODID, "textures/vehicles/rocket_t2.png");
    }
}