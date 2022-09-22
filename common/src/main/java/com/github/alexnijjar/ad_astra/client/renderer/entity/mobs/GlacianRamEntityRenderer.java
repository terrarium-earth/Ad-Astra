package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs;

import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.GlacianRamEntityModel;
import com.github.alexnijjar.ad_astra.entities.mobs.GlacianRamEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class GlacianRamEntityRenderer extends MobEntityRenderer<GlacianRamEntity, GlacianRamEntityModel<GlacianRamEntity>> {
	private static final Identifier TEXTURE = new ModIdentifier("textures/entity/glacian_ram/glacian_ram.png");
	private static final Identifier SHEARED_TEXTURE = new ModIdentifier("textures/entity/glacian_ram/sheared_glacian_ram.png");

	public GlacianRamEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new GlacianRamEntityModel<>(context.getPart(GlacianRamEntityModel.LAYER_LOCATION)), 0.7f);
	}

	public Identifier getTexture(GlacianRamEntity entity) {
		return entity.isSheared() ? SHEARED_TEXTURE : TEXTURE;
	}
}
