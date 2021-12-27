package net.mrscauthd.beyond_earth.entity.renderer.martianraptor;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.MartianRaptor;

@OnlyIn(Dist.CLIENT)
public class MartianRaptorRenderer extends MobRenderer<MartianRaptor, MartianRaptorModel<MartianRaptor>> {
    public MartianRaptorRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new MartianRaptorModel<>(renderManagerIn.bakeLayer(MartianRaptorModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MartianRaptor p_114482_) {
        return new ResourceLocation(BeyondEarthMod.MODID, "textures/entities/martian_raptor.png");
    }
}