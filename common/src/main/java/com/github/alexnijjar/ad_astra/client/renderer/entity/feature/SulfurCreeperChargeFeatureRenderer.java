package com.github.alexnijjar.ad_astra.client.renderer.entity.feature;

import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.SulfurCreeperEntityModel;
import com.github.alexnijjar.ad_astra.entities.mobs.SulfurCreeperEntity;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.util.Identifier;

public class SulfurCreeperChargeFeatureRenderer extends EnergySwirlOverlayFeatureRenderer<SulfurCreeperEntity, SulfurCreeperEntityModel<SulfurCreeperEntity>> {
	private static final Identifier SKIN = new Identifier("textures/entity/creeper/creeper_armor.png");
	private final SulfurCreeperEntityModel<SulfurCreeperEntity> model;

	public SulfurCreeperChargeFeatureRenderer(FeatureRendererContext<SulfurCreeperEntity, SulfurCreeperEntityModel<SulfurCreeperEntity>> context, EntityModelLoader loader) {
		super(context);
		this.model = new SulfurCreeperEntityModel<>(loader.getModelPart(SulfurCreeperEntityModel.LAYER_LOCATION));
	}

	@Override
	protected float getEnergySwirlX(float partialAge) {
		return partialAge * 0.01f;
	}

	@Override
	protected Identifier getEnergySwirlTexture() {
		return SKIN;
	}

	@Override
	protected EntityModel<SulfurCreeperEntity> getEnergySwirlModel() {
		return this.model;
	}
}
