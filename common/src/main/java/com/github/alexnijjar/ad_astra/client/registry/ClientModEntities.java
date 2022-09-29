package com.github.alexnijjar.ad_astra.client.registry;

import com.github.alexnijjar.ad_astra.client.renderer.block.globe.GlobeModel;
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
import com.github.alexnijjar.ad_astra.client.renderer.armour.JetSuitModel;
import com.github.alexnijjar.ad_astra.client.renderer.armour.NetheriteSpaceSuitModel;
import com.github.alexnijjar.ad_astra.client.renderer.armour.SpaceSuitModel;
import com.github.alexnijjar.ad_astra.registry.ModEntityTypes;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
		EntityRendererRegistry.register(ModEntityTypes.TIER_1_ROCKET, RocketEntityRendererTier1::new);
		EntityRendererRegistry.register(ModEntityTypes.TIER_2_ROCKET, RocketEntityRendererTier2::new);
		EntityRendererRegistry.register(ModEntityTypes.TIER_3_ROCKET, RocketEntityRendererTier3::new);
		EntityRendererRegistry.register(ModEntityTypes.TIER_4_ROCKET, RocketEntityRendererTier4::new);
		EntityRendererRegistry.register(ModEntityTypes.TIER_1_ROVER, RoverEntityRenderer::new);
		EntityRendererRegistry.register(ModEntityTypes.LANDER, LanderEntityRenderer::new);

		// Painting
		EntityRendererRegistry.register(ModEntityTypes.SPACE_PAINTING, PaintingEntityRenderer::new);

		// Projectiles
		EntityRendererRegistry.register(ModEntityTypes.ICE_SPIT, FlyingItemEntityRenderer::new);

		// Layers
		EntityModelLayerRegistry.register(LunarianEntityModel.LAYER_LOCATION, LunarianEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.register(CorruptedLunarianEntityModel.LAYER_LOCATION, CorruptedLunarianEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.register(StarCrawlerEntityModel.LAYER_LOCATION, StarCrawlerEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.register(MartianRaptorEntityModel.LAYER_LOCATION, MartianRaptorEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.register(PygroEntityModel.LAYER_LOCATION, PygroEntityModel::getModelData);
		EntityModelLayerRegistry.register(MoglerEntityModel.LAYER_LOCATION, MoglerEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.register(SulfurCreeperEntityModel.LAYER_LOCATION, SulfurCreeperEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.register(GlacianRamEntityModel.LAYER_LOCATION, GlacianRamEntityModel::getTexturedModelData);

		EntityModelLayerRegistry.register(GlobeModel.LAYER_LOCATION, GlobeModel::getTexturedModelData);

		// Machine Layers
		EntityModelLayerRegistry.register(RocketEntityModelTier1.LAYER_LOCATION, RocketEntityModelTier1::getTexturedModelData);
		EntityModelLayerRegistry.register(RocketEntityModelTier2.LAYER_LOCATION, RocketEntityModelTier2::getTexturedModelData);
		EntityModelLayerRegistry.register(RocketEntityModelTier3.LAYER_LOCATION, RocketEntityModelTier3::getTexturedModelData);
		EntityModelLayerRegistry.register(RocketEntityModelTier4.LAYER_LOCATION, RocketEntityModelTier4::getTexturedModelData);
		EntityModelLayerRegistry.register(RoverEntityModel.LAYER_LOCATION, RoverEntityModel::getTexturedModelData);
		EntityModelLayerRegistry.register(LanderEntityModel.LAYER_LOCATION, LanderEntityModel::getTexturedModelData);

		// Armour Layers
		EntityModelLayerRegistry.register(SpaceSuitModel.LAYER_LOCATION, SpaceSuitModel::getTexturedModelData);
		EntityModelLayerRegistry.register(NetheriteSpaceSuitModel.LAYER_LOCATION, NetheriteSpaceSuitModel::getTexturedModelData);
		EntityModelLayerRegistry.register(JetSuitModel.LAYER_LOCATION, JetSuitModel::getTexturedModelData);
	}
}