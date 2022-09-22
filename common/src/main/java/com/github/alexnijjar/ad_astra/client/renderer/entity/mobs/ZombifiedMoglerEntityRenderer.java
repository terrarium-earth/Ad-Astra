package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs;

import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.MoglerEntityModel;
import com.github.alexnijjar.ad_astra.entities.mobs.ZombifiedMoglerEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ZombifiedMoglerEntityRenderer extends MobEntityRenderer<ZombifiedMoglerEntity, MoglerEntityModel<ZombifiedMoglerEntity>> {
	public static final Identifier TEXTURE = new ModIdentifier("textures/entity/zombified_mogler.png");

	public ZombifiedMoglerEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new MoglerEntityModel<>(context.getPart(MoglerEntityModel.LAYER_LOCATION)), 0.7f);
	}

	@Override
	public Identifier getTexture(ZombifiedMoglerEntity entity) {
		return TEXTURE;
	}
}
