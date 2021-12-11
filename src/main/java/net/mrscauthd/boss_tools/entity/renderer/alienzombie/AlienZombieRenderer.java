package net.mrscauthd.boss_tools.entity.renderer.alienzombie;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.entity.AlienZombieEntity;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.entity.renderer.rockettier1.RocketTier1Model;

@OnlyIn(Dist.CLIENT)
public class AlienZombieRenderer extends MobRenderer<AlienZombieEntity, AlienZombieModel<AlienZombieEntity>> {
    public AlienZombieRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AlienZombieModel<>(renderManagerIn.bakeLayer(AlienZombieModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(AlienZombieEntity p_114482_) {
        return new ResourceLocation(BossToolsMod.ModId, "textures/entities/alien_zombie.png");
    }
}