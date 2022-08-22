package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs;

import com.github.alexnijjar.ad_astra.entities.mobs.AlienWanderingTraderEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AlienWanderingTraderEntityRenderer extends MobEntityRenderer<AlienWanderingTraderEntity, VillagerResemblingModel<AlienWanderingTraderEntity>> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entities/alien_wandering_trader.png");

    public AlienWanderingTraderEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new VillagerResemblingModel<>(context.getPart(EntityModelLayers.WANDERING_TRADER)), 0.5f);
        this.addFeature(new HeadFeatureRenderer<AlienWanderingTraderEntity, VillagerResemblingModel<AlienWanderingTraderEntity>>(this, context.getModelLoader(), context.getHeldItemRenderer()));
        this.addFeature(new VillagerHeldItemFeatureRenderer<AlienWanderingTraderEntity, VillagerResemblingModel<AlienWanderingTraderEntity>>(this, context.getHeldItemRenderer()));
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