package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.PygroEntityModel;
import com.github.alexnijjar.ad_astra.entities.mobs.PygroEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PygroEntityRenderer extends MobEntityRenderer<PygroEntity, PygroEntityModel> {
	public static final Identifier TEXTURE = new Identifier(AdAstra.MOD_ID, "textures/entity/pygro.png");

	public PygroEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new PygroEntityModel(context.getPart(PygroEntityModel.LAYER_LOCATION)), 0.5f);
		this.addFeature(new HeldItemFeatureRenderer<>(this));
        this.addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel<>(context.getPart(EntityModelLayers.PIGLIN_INNER_ARMOR)), new BipedEntityModel<>(context.getPart(EntityModelLayers.PIGLIN_OUTER_ARMOR))));
	}

	@Override
	public Identifier getTexture(PygroEntity entity) {
		return TEXTURE;
	}
}
