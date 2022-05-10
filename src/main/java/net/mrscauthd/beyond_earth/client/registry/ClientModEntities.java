package net.mrscauthd.beyond_earth.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.AlienEntityRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.AlienZombieEntityRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.MartianRaptorEntityRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.MoglerEntityRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.PygroEntityRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.StarCrawlerEntityRenderer;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.AlienEntityModel;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.AlienZombieEntityModel;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.MartianRaptorEntityModel;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.MoglerEntityModel;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.PygroEntityModel;
import net.mrscauthd.beyond_earth.client.renderer.entity.mobs.models.StarCrawlerEntityModel;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.rockets.tier_1.RocketEntityModelTier1;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.rockets.tier_1.RocketEntityRendererTier1;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.rockets.tier_2.RocketEntityModelTier2;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.rockets.tier_2.RocketEntityRendererTier2;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.rockets.tier_3.RocketEntityModelTier3;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.rockets.tier_3.RocketEntityRendererTier3;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.rockets.tier_4.RocketEntityModelTier4;
import net.mrscauthd.beyond_earth.client.renderer.entity.vehicles.rockets.tier_4.RocketEntityRendererTier4;
import net.mrscauthd.beyond_earth.registry.ModEntities;

@Environment(EnvType.CLIENT)
public class ClientModEntities {

    public static void register() {

        // Mobs.
        EntityRendererRegistry.register(ModEntities.ALIEN, AlienEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.ALIEN_ZOMBIE, AlienZombieEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.STAR_CRAWLER, StarCrawlerEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.MARTIAN_RAPTOR, MartianRaptorEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.PYGRO, PygroEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.PYGRO_BRUTE, PygroEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.MOGLER, MoglerEntityRenderer::new);

        // Machines.
        EntityRendererRegistry.register(ModEntities.TIER_1_ROCKET, RocketEntityRendererTier1::new);
        EntityRendererRegistry.register(ModEntities.TIER_2_ROCKET, RocketEntityRendererTier2::new);
        EntityRendererRegistry.register(ModEntities.TIER_3_ROCKET, RocketEntityRendererTier3::new);
        EntityRendererRegistry.register(ModEntities.TIER_4_ROCKET, RocketEntityRendererTier4::new);

        // Projectiles.
        EntityRendererRegistry.register(ModEntities.ICE_SPIT_ENTITY, FlyingItemEntityRenderer::new);

        // Layers.
        EntityModelLayerRegistry.registerModelLayer(AlienEntityModel.LAYER_LOCATION, AlienEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(AlienZombieEntityModel.LAYER_LOCATION, AlienZombieEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(StarCrawlerEntityModel.LAYER_LOCATION, StarCrawlerEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MartianRaptorEntityModel.LAYER_LOCATION, MartianRaptorEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(PygroEntityModel.LAYER_LOCATION, PygroEntityModel::getModelData);
        EntityModelLayerRegistry.registerModelLayer(MoglerEntityModel.LAYER_LOCATION, MoglerEntityModel::getTexturedModelData);

        // Machine Layers.
        EntityModelLayerRegistry.registerModelLayer(RocketEntityModelTier1.LAYER_LOCATION, RocketEntityModelTier1::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(RocketEntityModelTier2.LAYER_LOCATION, RocketEntityModelTier2::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(RocketEntityModelTier3.LAYER_LOCATION, RocketEntityModelTier3::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(RocketEntityModelTier4.LAYER_LOCATION, RocketEntityModelTier4::getTexturedModelData);
    }
}