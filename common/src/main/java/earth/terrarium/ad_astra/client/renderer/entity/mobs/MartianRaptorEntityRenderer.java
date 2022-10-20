package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.MartianRaptorEntityModel;
import earth.terrarium.ad_astra.entities.mobs.MartianRaptorEntity;
import earth.terrarium.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MartianRaptorEntityRenderer extends MobEntityRenderer<MartianRaptorEntity, MartianRaptorEntityModel> {
	public static final Identifier TEXTURE = new ModIdentifier("textures/entity/martian_raptor.png");

	public MartianRaptorEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new MartianRaptorEntityModel(context.getPart(MartianRaptorEntityModel.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public Identifier getTexture(MartianRaptorEntity entity) {
		return TEXTURE;
	}

}
