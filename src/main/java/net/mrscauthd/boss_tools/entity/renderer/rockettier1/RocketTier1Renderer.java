package net.mrscauthd.boss_tools.entity.renderer.rockettier1;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;

@OnlyIn(Dist.CLIENT)
public class RocketTier1Renderer extends MobRenderer<RocketTier1Entity, RocketTier1Model<RocketTier1Entity>> {
    public RocketTier1Renderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new RocketTier1Model<>(renderManagerIn.bakeLayer(RocketTier1Model.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(RocketTier1Entity p_114482_) {
        return new ResourceLocation(BossToolsMod.ModId, "textures/vehicles/rocket_t1.png");
    }
}