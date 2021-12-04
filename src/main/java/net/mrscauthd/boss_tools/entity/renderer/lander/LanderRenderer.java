package net.mrscauthd.boss_tools.entity.renderer.lander;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.entity.LanderEntity;

@OnlyIn(Dist.CLIENT)
public class LanderRenderer extends MobRenderer<LanderEntity, LanderModel<LanderEntity>> {
    public LanderRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new LanderModel(), 0f);
    }

    @Override
    public ResourceLocation getEntityTexture(LanderEntity entity) {
        return new ResourceLocation(BossToolsMod.ModId, "textures/vehicles/lander.png");
    }
}