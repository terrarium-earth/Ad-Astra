package net.mrscauthd.beyond_earth.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.AlienZombieRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.MartianRaptorRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.MoglerRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.PygroRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.StarCrawlerRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.AlienZombieModel;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.MartianRaptorModel;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.MoglerModel;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.PygroModel;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.StarCrawlerModel;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.RocketEntityRenderer;
import net.mrscauthd.beyond_earth.registry.ModEntities;

@Environment(EnvType.CLIENT)
public class ClientModEntities {

    public static void register() {

        // Mobs.
        EntityRendererRegistry.register(ModEntities.ALIEN_ZOMBIE, AlienZombieRenderer::new);
        EntityRendererRegistry.register(ModEntities.STAR_CRAWLER, StarCrawlerRenderer::new);
        EntityRendererRegistry.register(ModEntities.MARTIAN_RAPTOR, MartianRaptorRenderer::new);
        EntityRendererRegistry.register(ModEntities.PYGRO, PygroRenderer::new);
        EntityRendererRegistry.register(ModEntities.PYGRO_BRUTE, PygroRenderer::new);
        EntityRendererRegistry.register(ModEntities.MOGLER, MoglerRenderer::new);

        // Machines.
        EntityRendererRegistry.register(ModEntities.TIER_1_ROCKET, RocketEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.TIER_2_ROCKET, RocketEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.TIER_3_ROCKET, RocketEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.TIER_4_ROCKET, RocketEntityRenderer::new);

        // Projectiles.
        EntityRendererRegistry.register(ModEntities.ICE_SPIT_ENTITY, FlyingItemEntityRenderer::new);

        // Layers.
        EntityModelLayerRegistry.registerModelLayer(AlienZombieModel.LAYER_LOCATION, AlienZombieModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(StarCrawlerModel.LAYER_LOCATION, StarCrawlerModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MartianRaptorModel.LAYER_LOCATION, MartianRaptorModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(PygroModel.LAYER_LOCATION, PygroModel::getModelData);
        EntityModelLayerRegistry.registerModelLayer(MoglerModel.LAYER_LOCATION, MoglerModel::getTexturedModelData);
    }
}