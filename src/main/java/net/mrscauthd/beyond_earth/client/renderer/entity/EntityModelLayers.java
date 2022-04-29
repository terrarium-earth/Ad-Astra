package net.mrscauthd.beyond_earth.client.renderer.entity;

import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class EntityModelLayers {

    // public static final EntityModelLayer MODEL_ROCKET_LAYER = new EntityModelLayer(new ModIdentifier("rocket_t1")), "main");

    public static void register() {
        // EntityModelLayerRegistry.registerModelLayer(MODEL_ROCKET_LAYER, RocketModel::getTexturedModelData);
    }
}
