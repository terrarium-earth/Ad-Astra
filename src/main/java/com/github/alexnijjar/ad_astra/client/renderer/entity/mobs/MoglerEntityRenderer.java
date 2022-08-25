package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs;

import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.MoglerEntityModel;
import com.github.alexnijjar.ad_astra.entities.mobs.MoglerEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MoglerEntityRenderer extends MobEntityRenderer<MoglerEntity, MoglerEntityModel<MoglerEntity>> {
	public static final Identifier TEXTURE = new ModIdentifier("textures/entity/mogler.png");

	public MoglerEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new MoglerEntityModel<>(context.getPart(MoglerEntityModel.LAYER_LOCATION)), 0.7f);
	}

	@Override
	public Identifier getTexture(MoglerEntity entity) {
		return TEXTURE;
	}

	@Override
	protected boolean isShaking(MoglerEntity entity) {
		return super.isShaking(entity) || entity.canConvert();
	}
}
