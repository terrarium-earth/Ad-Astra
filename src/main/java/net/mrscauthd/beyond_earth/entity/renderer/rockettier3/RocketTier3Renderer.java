package net.mrscauthd.beyond_earth.entity.renderer.rockettier3;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.RocketTier3Entity;

@OnlyIn(Dist.CLIENT)
public class RocketTier3Renderer extends MobRenderer<RocketTier3Entity, RocketTier3Model<RocketTier3Entity>> {
    public RocketTier3Renderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new RocketTier3Model<>(renderManagerIn.bakeLayer(RocketTier3Model.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketTier3Entity p_114482_) {
        return new ResourceLocation(BeyondEarthMod.MODID, "textures/vehicles/rocket_t3.png");
    }
}