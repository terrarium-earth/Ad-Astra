package net.mrscauthd.beyond_earth.client.renderer.entity.mobs;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PiglinEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.PygroModel;
import net.mrscauthd.beyond_earth.registry.ModEntities;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class PygroRenderer extends PiglinEntityRenderer {
    private static final Map<EntityType<?>, Identifier> TEXTURES = ImmutableMap.of(ModEntities.PYGRO, new ModIdentifier("textures/entities/pygro.png"), ModEntities.PYGRO_BRUTE, new ModIdentifier("textures/entities/pygro_brute.png"));

    public PygroRenderer(EntityRendererFactory.Context context) {
        super(context, PygroModel.LAYER_LOCATION, EntityModelLayers.PIGLIN_INNER_ARMOR, EntityModelLayers.PIGLIN_OUTER_ARMOR, false);
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
