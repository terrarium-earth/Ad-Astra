package com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs;

import com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs.models.LunarianWanderingTraderEntityModel;
import com.github.alexnijjar.beyond_earth.entities.mobs.LunarianWanderingTraderEntity;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LunarianWanderingTraderEntityRenderer extends MobEntityRenderer<LunarianWanderingTraderEntity, LunarianWanderingTraderEntityModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/lunarian_wandering_trader.png");

    public LunarianWanderingTraderEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new LunarianWanderingTraderEntityModel(context.getPart(LunarianWanderingTraderEntityModel.LAYER_LOCATION)), 0.5f);
        this.addFeature(new HeadFeatureRenderer<LunarianWanderingTraderEntity, LunarianWanderingTraderEntityModel>(this, context.getModelLoader()));
        this.addFeature(new VillagerHeldItemFeatureRenderer<LunarianWanderingTraderEntity, LunarianWanderingTraderEntityModel>(this));
    }

    @Override
    public Identifier getTexture(LunarianWanderingTraderEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(LunarianWanderingTraderEntity entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
    }
}