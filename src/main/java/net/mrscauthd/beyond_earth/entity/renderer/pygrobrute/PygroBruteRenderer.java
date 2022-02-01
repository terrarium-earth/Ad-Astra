package net.mrscauthd.beyond_earth.entity.renderer.pygrobrute;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.renderer.pygro.PygroModel;

@OnlyIn(Dist.CLIENT)
public class PygroBruteRenderer extends HumanoidMobRenderer<Mob, PygroModel<Mob>> {

    public PygroBruteRenderer(EntityRendererProvider.Context p_174344_, ModelLayerLocation p_174345_, ModelLayerLocation p_174346_, ModelLayerLocation p_174347_) {
        super(p_174344_, createModel(p_174344_.getModelSet(), p_174345_), 0.5F, 1.0019531F, 1.0F, 1.0019531F);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel(p_174344_.bakeLayer(p_174346_)), new HumanoidModel(p_174344_.bakeLayer(p_174347_))));
    }

    private static PygroModel<Mob> createModel(EntityModelSet p_174350_, ModelLayerLocation p_174351_) {
        PygroModel<Mob> pygroModel = new PygroModel<>(p_174350_.bakeLayer(p_174351_));

        return pygroModel;
    }

    public ResourceLocation getTextureLocation(Mob p_115708_) {
        return new ResourceLocation(BeyondEarthMod.MODID, "textures/entities/pygro_brute.png");
    }

    protected boolean isShaking(Mob p_115712_) {
        return super.isShaking(p_115712_) || p_115712_ instanceof AbstractPiglin && ((AbstractPiglin)p_115712_).isConverting();
    }
}