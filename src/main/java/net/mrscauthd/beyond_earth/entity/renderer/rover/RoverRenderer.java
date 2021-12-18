package net.mrscauthd.beyond_earth.entity.renderer.rover;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.RoverEntity;

@OnlyIn(Dist.CLIENT)
public class RoverRenderer extends MobRenderer<RoverEntity, RoverModel<RoverEntity>> {
    public RoverRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new RoverModel<>(renderManagerIn.bakeLayer(RoverModel.LAYER_LOCATION)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(RoverEntity p_114482_) {
        return new ResourceLocation(BeyondEarthMod.MODID, "textures/vehicles/rover.png");
    }
}