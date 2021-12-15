package net.mrscauthd.astrocraft.entity.renderer.rockettier2;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.astrocraft.AstroCraftMod;
import net.mrscauthd.astrocraft.entity.RocketTier2Entity;

@OnlyIn(Dist.CLIENT)
public class RocketTier2Renderer extends MobRenderer<RocketTier2Entity, RocketTier2Model<RocketTier2Entity>> {
    public RocketTier2Renderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new RocketTier2Model<>(renderManagerIn.bakeLayer(RocketTier2Model.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketTier2Entity p_114482_) {
        return new ResourceLocation(AstroCraftMod.MODID, "textures/vehicles/rocket_t2.png");
    }
}