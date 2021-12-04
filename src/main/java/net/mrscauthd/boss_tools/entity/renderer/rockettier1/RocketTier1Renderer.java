package net.mrscauthd.boss_tools.entity.renderer.rockettier1;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;

@OnlyIn(Dist.CLIENT)
public class RocketTier1Renderer extends MobRenderer<RocketTier1Entity, RocketTier1Model<RocketTier1Entity>> {
    public RocketTier1Renderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new RocketTier1Model(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(RocketTier1Entity entity) {
        return new ResourceLocation(BossToolsMod.ModId, "textures/vehicles/rocket_t1.png");
    }
}