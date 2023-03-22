package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.PygroBruteEntityModel;
import com.github.alexnijjar.ad_astra.entities.mobs.PygroBruteEntity;
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
public class PygroBruteEntityRenderer extends MobEntityRenderer<PygroBruteEntity, PygroBruteEntityModel> {
	public static final Identifier TEXTURE = new Identifier(AdAstra.MOD_ID, "textures/entity/pygro_brute.png");

	public PygroBruteEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new PygroBruteEntityModel(context.getPart(PygroBruteEntityModel.LAYER_LOCATION)), 0.5f);
		this.addFeature(new HeldItemFeatureRenderer<>(this));
        this.addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel<>(context.getPart(EntityModelLayers.PIGLIN_INNER_ARMOR)), new BipedEntityModel<>(context.getPart(EntityModelLayers.PIGLIN_OUTER_ARMOR))));
	}

	@Override
	public Identifier getTexture(PygroBruteEntity mobEntity) {
		return TEXTURE;
	}
}
