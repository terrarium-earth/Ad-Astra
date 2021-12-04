package net.mrscauthd.boss_tools.entity.renderer.rockettier3;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;

@OnlyIn(Dist.CLIENT)
public class RocketTier3Renderer extends MobRenderer<RocketTier3Entity, RocketTier3Model<RocketTier3Entity>> {
    public RocketTier3Renderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new RocketTier3Model(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(RocketTier3Entity entity) {
        return new ResourceLocation(BossToolsMod.ModId, "textures/vehicles/rocket_t3.png");
    }
}