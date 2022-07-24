package com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs;

import java.util.Map;

import com.github.alexnijjar.beyond_earth.client.renderer.entity.mobs.models.PygroEntityModel;
import com.github.alexnijjar.beyond_earth.registry.ModEntityTypes;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
import com.google.common.collect.ImmutableMap;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PiglinEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PygroEntityRenderer extends PiglinEntityRenderer {
    private static final Map<EntityType<?>, Identifier> TEXTURES = ImmutableMap.of(ModEntityTypes.ZOMBIFIED_PYGRO, new ModIdentifier("textures/entities/zombified_pygro.png"), ModEntityTypes.PYGRO, new ModIdentifier("textures/entities/pygro.png"), ModEntityTypes.PYGRO_BRUTE, new ModIdentifier("textures/entities/pygro_brute.png"));

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
