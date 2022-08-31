package com.github.alexnijjar.ad_astra.client.registry;

import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.CorruptedLunarianEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.GlacianRamEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.LunarianEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.LunarianWanderingTraderEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.MartianRaptorEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.MoglerEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.PygroEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.StarCrawlerEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.SulfurCreeperEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.ZombifiedMoglerEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.CorruptedLunarianEntityModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.GlacianRamEntityModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.LunarianEntityModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.MartianRaptorEntityModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.MoglerEntityModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.PygroEntityModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.StarCrawlerEntityModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.mobs.models.SulfurCreeperEntityModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.lander.LanderEntityModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.lander.LanderEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_1.RocketEntityModelTier1;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_1.RocketEntityRendererTier1;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_2.RocketEntityModelTier2;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_2.RocketEntityRendererTier2;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_3.RocketEntityModelTier3;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_3.RocketEntityRendererTier3;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_4.RocketEntityModelTier4;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_4.RocketEntityRendererTier4;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rover.RoverEntityModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rover.RoverEntityRenderer;
import com.github.alexnijjar.ad_astra.registry.ModEntityTypes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.PaintingEntityRenderer;

@Environment(EnvType.CLIENT)
public class ClientModEntities {

	public static void register() {

		// Mobs
		EntityRendererRegistry.register(ModEntityTypes.LUNARIAN, LunarianEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.CORRUPTED_LUNARIAN, CorruptedLunarianEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.STAR_CRAWLER, StarCrawlerEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.MARTIAN_RAPTOR, MartianRaptorEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.PYGRO, PygroEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.ZOMBIFIED_PYGRO, PygroEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.PYGRO_BRUTE, PygroEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.MOGLER, MoglerEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.ZOMBIFIED_MOGLER, ZombifiedMoglerEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.SULFUR_CREEPER, SulfurCreeperEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.LUNARIAN_WANDERING_TRADER, LunarianWanderingTraderEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.GLACIAN_RAM, GlacianRamEntityRenderer::new);

		// Machines
		EntityRendererRegistry.register(ModEntityTypes.ROCKET_TIER_1, RocketEntityRendererTier1::new);
		EntityRendererRegistry.register(ModEntityTypes.ROCKET_TIER_2, RocketEntityRendererTier2::new);
		EntityRendererRegistry.register(ModEntityTypes.ROCKET_TIER_3, RocketEntityRendererTier3::new);
		EntityRendererRegistry.register(ModEntityTypes.ROCKET_TIER_4, RocketEntityRendererTier4::new);
		EntityRendererRegistry.register(ModEntityTypes.ROVER_TIER_1, RoverEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.LANDER, LanderEntityRenderer::new);

		// Painting
		EntityRendererRegistry.register(ModEntityTypes.SPACE_PAINTING, PaintingEntityRenderer::new);

		// Projectiles
		EntityRendererRegistry.register(ModEntityTypes.ICE_SPIT_ENTITY, FlyingItemEntityRenderer::new);

		// Layers
		EntityModelLayerRegistry.registerModelLayer(LunarianEntityModel.LAYER_LOCATION, LunarianEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(CorruptedLunarianEntityModel.LAYER_LOCATION, CorruptedLunarianEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(StarCrawlerEntityModel.LAYER_LOCATION, StarCrawlerEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(MartianRaptorEntityModel.LAYER_LOCATION, MartianRaptorEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(PygroEntityModel.LAYER_LOCATION, PygroEntityModel::getModelData);
		EntityModelLayerRegistry.registerModelLayer(MoglerEntityModel.LAYER_LOCATION, MoglerEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(SulfurCreeperEntityModel.LAYER_LOCATION, SulfurCreeperEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(GlacianRamEntityModel.LAYER_LOCATION, GlacianRamEntityModel::getTexturedModelData);

		// Machine Layers
		EntityModelLayerRegistry.registerModelLayer(RocketEntityModelTier1.LAYER_LOCATION, RocketEntityModelTier1::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(RocketEntityModelTier2.LAYER_LOCATION, RocketEntityModelTier2::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(RocketEntityModelTier3.LAYER_LOCATION, RocketEntityModelTier3::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(RocketEntityModelTier4.LAYER_LOCATION, RocketEntityModelTier4::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(RoverEntityModel.LAYER_LOCATION, RoverEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(LanderEntityModel.LAYER_LOCATION, LanderEntityModel::getTexturedModelData);
	}
}