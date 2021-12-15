package net.mrscauthd.astrocraft.entity.renderer.alienzombie;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.astrocraft.AstroCraftMod;
import net.mrscauthd.astrocraft.entity.AlienZombieEntity;

@OnlyIn(Dist.CLIENT)
public class AlienZombieRenderer extends MobRenderer<AlienZombieEntity, AlienZombieModel<AlienZombieEntity>> {
    public AlienZombieRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AlienZombieModel<>(renderManagerIn.bakeLayer(AlienZombieModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(AlienZombieEntity p_114482_) {
        return new ResourceLocation(AstroCraftMod.MODID, "textures/entities/alien_zombie.png");
    }
}