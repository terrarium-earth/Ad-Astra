package net.mrscauthd.astrocraft.entity.renderer.lander;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.astrocraft.AstroCraftMod;
import net.mrscauthd.astrocraft.entity.LanderEntity;

@OnlyIn(Dist.CLIENT)
public class LanderRenderer extends MobRenderer<LanderEntity, LanderModel<LanderEntity>> {
    public LanderRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new LanderModel<>(renderManagerIn.bakeLayer(LanderModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(LanderEntity p_114482_) {
        return new ResourceLocation(AstroCraftMod.MODID, "textures/vehicles/lander.png");
    }
}