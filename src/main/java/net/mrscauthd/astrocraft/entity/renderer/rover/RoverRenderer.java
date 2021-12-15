package net.mrscauthd.astrocraft.entity.renderer.rover;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.astrocraft.AstroCraftMod;
import net.mrscauthd.astrocraft.entity.RoverEntity;

@OnlyIn(Dist.CLIENT)
public class RoverRenderer extends MobRenderer<RoverEntity, RoverModel<RoverEntity>> {
    public RoverRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new RoverModel<>(renderManagerIn.bakeLayer(RoverModel.LAYER_LOCATION)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(RoverEntity p_114482_) {
        return new ResourceLocation(AstroCraftMod.MODID, "textures/vehicles/rover.png");
    }
}