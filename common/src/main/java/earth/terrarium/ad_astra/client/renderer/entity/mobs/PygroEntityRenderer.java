package earth.terrarium.ad_astra.client.renderer.entity.mobs;

import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.PygroEntityModel;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import earth.terrarium.ad_astra.util.ModIdentifier;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PiglinEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class PygroEntityRenderer extends PiglinEntityRenderer {
	private static final Map<EntityType<?>, Identifier> TEXTURES = ImmutableMap.of(ModEntityTypes.ZOMBIFIED_PYGRO.get(), new ModIdentifier("textures/entity/zombified_pygro.png"), ModEntityTypes.PYGRO.get(), new ModIdentifier("textures/entity/pygro.png"), ModEntityTypes.PYGRO_BRUTE.get(), new ModIdentifier("textures/entity/pygro_brute.png"));

	public PygroEntityRenderer(EntityRendererFactory.Context context) {
		super(context, PygroEntityModel.LAYER_LOCATION, EntityModelLayers.PIGLIN_INNER_ARMOR, EntityModelLayers.PIGLIN_OUTER_ARMOR, false);
	}

	@Override
	public Identifier getTexture(MobEntity mobEntity) {
		Identifier identifier = TEXTURES.get(mobEntity.getType());
		if (identifier == null) {
			throw new IllegalArgumentException("I don't know what texture to use for " + mobEntity.getType());
		}
		return identifier;
	}
}
