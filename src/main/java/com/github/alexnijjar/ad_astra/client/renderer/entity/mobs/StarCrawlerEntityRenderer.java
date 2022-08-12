package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs;

import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.StarCrawlerEntityModel;
import com.github.alexnijjar.ad_astra.entities.mobs.StarCrawlerEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class StarCrawlerEntityRenderer extends MobEntityRenderer<StarCrawlerEntity, StarCrawlerEntityModel> {
	public static final Identifier TEXTURE = new ModIdentifier("textures/entities/star_crawler.png");

	public StarCrawlerEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new StarCrawlerEntityModel(context.getPart(StarCrawlerEntityModel.LAYER_LOCATION)), 0.0f);
	}

	@Override
	public Identifier getTexture(StarCrawlerEntity entity) {
		return TEXTURE;
	}

}
