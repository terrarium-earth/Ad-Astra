package net.mrscauthd.boss_tools.entity.renderer.rover;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.entity.renderer.rockettier3.RocketTier3Model;

@OnlyIn(Dist.CLIENT)
public class RoverRenderer extends MobRenderer<RoverEntity, RoverModel<RoverEntity>> {
    public RoverRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new RoverModel<>(renderManagerIn.bakeLayer(RoverModel.LAYER_LOCATION)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(RoverEntity p_114482_) {
        return new ResourceLocation(BossToolsMod.ModId, "textures/vehicles/rover.png");
    }
}