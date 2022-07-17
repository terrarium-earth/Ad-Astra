package com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs;

import com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs.models.AlienWanderingTraderEntityModel;
import com.github.alexnijjar.beyond_earth.entities.mobs.AlienWanderingTraderEntity;
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
public class AlienWanderingTraderEntityRenderer extends MobEntityRenderer<AlienWanderingTraderEntity, AlienWanderingTraderEntityModel> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/alien_wandering_trader.png");

    public AlienWanderingTraderEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new AlienWanderingTraderEntityModel(context.getPart(AlienWanderingTraderEntityModel.LAYER_LOCATION)), 0.5f);
        this.addFeature(new HeadFeatureRenderer<AlienWanderingTraderEntity, AlienWanderingTraderEntityModel>(this, context.getModelLoader()));
        this.addFeature(new VillagerHeldItemFeatureRenderer<AlienWanderingTraderEntity, AlienWanderingTraderEntityModel>(this));
    }

    @Override
    public Identifier getTexture(AlienWanderingTraderEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(AlienWanderingTraderEntity entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
    }
}