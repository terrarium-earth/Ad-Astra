package com.github.alexnijjar.ad_astra.client.renderer.entity.mobs;

import com.github.alexnijjar.ad_astra.client.renderer.entity.feature.SulfurCreeperChargeFeatureRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.SulfurCreeperEntityModel;
import com.github.alexnijjar.ad_astra.entities.mobs.SulfurCreeperEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SulfurCreeperEntityRenderer extends MobEntityRenderer<SulfurCreeperEntity, SulfurCreeperEntityModel<SulfurCreeperEntity>> {
	public static final Identifier TEXTURE = new ModIdentifier("textures/entities/sulfur_creeper.png");

	public SulfurCreeperEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SulfurCreeperEntityModel<>(context.getPart(SulfurCreeperEntityModel.LAYER_LOCATION)), 0.7f);
		this.addFeature(new SulfurCreeperChargeFeatureRenderer(this, context.getModelLoader()));
	}

	@Override
	protected void scale(SulfurCreeperEntity creeperEntity, MatrixStack matrixStack, float f) {
		float g = creeperEntity.getClientFuseTime(f);
		float h = 1.0f + MathHelper.sin(g * 100.0f) * g * 0.01f;
		g = MathHelper.clamp(g, 0.0f, 1.0f);
		g *= g;
		g *= g;
		float i = (1.0f + g * 0.4f) * h;
		float j = (1.0f + g * 0.1f) / h;
		matrixStack.scale(i, j, i);
	}

	@Override
	protected float getAnimationCounter(SulfurCreeperEntity creeperEntity, float f) {
		float g = creeperEntity.getClientFuseTime(f);
		if ((int) (g * 10.0f) % 2 == 0) {
			return 0.0f;
		}
		return MathHelper.clamp(g, 0.5f, 1.0f);
	}

	@Override
	public Identifier getTexture(SulfurCreeperEntity entity) {
		return TEXTURE;
	}
}
