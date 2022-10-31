package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.client.renderer.armour.JetSuitModel;
import earth.terrarium.ad_astra.client.renderer.armour.NetheriteSpaceSuitModel;
import earth.terrarium.ad_astra.client.renderer.armour.SpaceSuitModel;
import earth.terrarium.ad_astra.client.renderer.block.globe.GlobeModel;
import earth.terrarium.ad_astra.client.renderer.entity.mobs.*;
import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.*;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.lander.LanderEntityModel;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.lander.LanderEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_1.RocketEntityModelTier1;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_1.RocketEntityRendererTier1;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_2.RocketEntityModelTier2;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_2.RocketEntityRendererTier2;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_3.RocketEntityModelTier3;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_3.RocketEntityRendererTier3;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_4.RocketEntityModelTier4;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_4.RocketEntityRendererTier4;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rover.RoverEntityModel;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rover.RoverEntityRenderer;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ClientModEntities {

    public static void registerEntityRenderers(EntityRendererRegistry registry) {
        registry.register(ModEntityTypes.LUNARIAN, LunarianEntityRenderer::new);
        registry.register(ModEntityTypes.CORRUPTED_LUNARIAN, CorruptedLunarianEntityRenderer::new);
        registry.register(ModEntityTypes.STAR_CRAWLER, StarCrawlerEntityRenderer::new);
        registry.register(ModEntityTypes.MARTIAN_RAPTOR, MartianRaptorEntityRenderer::new);
        registry.register(ModEntityTypes.PYGRO, PygroEntityRenderer::new);
        registry.register(ModEntityTypes.ZOMBIFIED_PYGRO, PygroEntityRenderer::new);
        registry.register(ModEntityTypes.PYGRO_BRUTE, PygroEntityRenderer::new);
        registry.register(ModEntityTypes.MOGLER, MoglerEntityRenderer::new);
        registry.register(ModEntityTypes.ZOMBIFIED_MOGLER, ZombifiedMoglerEntityRenderer::new);
        registry.register(ModEntityTypes.SULFUR_CREEPER, SulfurCreeperEntityRenderer::new);
        registry.register(ModEntityTypes.LUNARIAN_WANDERING_TRADER, LunarianWanderingTraderEntityRenderer::new);
        registry.register(ModEntityTypes.GLACIAN_RAM, GlacianRamEntityRenderer::new);
        registry.register(ModEntityTypes.TIER_1_ROCKET, RocketEntityRendererTier1::new);
        registry.register(ModEntityTypes.TIER_2_ROCKET, RocketEntityRendererTier2::new);
        registry.register(ModEntityTypes.TIER_3_ROCKET, RocketEntityRendererTier3::new);
        registry.register(ModEntityTypes.TIER_4_ROCKET, RocketEntityRendererTier4::new);
        registry.register(ModEntityTypes.TIER_1_ROVER, RoverEntityRenderer::new);
        registry.register(ModEntityTypes.LANDER, LanderEntityRenderer::new);
        registry.register(ModEntityTypes.SPACE_PAINTING, PaintingRenderer::new);
        registry.register(ModEntityTypes.ICE_SPIT, ThrownItemRenderer::new);
    }

    public static void registerEntityLayers(LayerDefinitionRegistry registry) {
        // Layers
        registry.register(LunarianEntityModel.LAYER_LOCATION, LunarianEntityModel::getTexturedModelData);
        registry.register(CorruptedLunarianEntityModel.LAYER_LOCATION, CorruptedLunarianEntityModel::getTexturedModelData);
        registry.register(StarCrawlerEntityModel.LAYER_LOCATION, StarCrawlerEntityModel::getTexturedModelData);
        registry.register(MartianRaptorEntityModel.LAYER_LOCATION, MartianRaptorEntityModel::getTexturedModelData);
        registry.register(PygroEntityModel.LAYER_LOCATION, PygroEntityModel::getModelData);
        registry.register(MoglerEntityModel.LAYER_LOCATION, MoglerEntityModel::getTexturedModelData);
        registry.register(SulfurCreeperEntityModel.LAYER_LOCATION, SulfurCreeperEntityModel::getTexturedModelData);
        registry.register(GlacianRamEntityModel.LAYER_LOCATION, GlacianRamEntityModel::getTexturedModelData);

        registry.register(GlobeModel.LAYER_LOCATION, GlobeModel::getTexturedModelData);

        // Machine Layers
        registry.register(RocketEntityModelTier1.LAYER_LOCATION, RocketEntityModelTier1::getTexturedModelData);
        registry.register(RocketEntityModelTier2.LAYER_LOCATION, RocketEntityModelTier2::getTexturedModelData);
        registry.register(RocketEntityModelTier3.LAYER_LOCATION, RocketEntityModelTier3::getTexturedModelData);
        registry.register(RocketEntityModelTier4.LAYER_LOCATION, RocketEntityModelTier4::getTexturedModelData);
        registry.register(RoverEntityModel.LAYER_LOCATION, RoverEntityModel::getTexturedModelData);
        registry.register(LanderEntityModel.LAYER_LOCATION, LanderEntityModel::getTexturedModelData);

        // Armour Layers
        registry.register(SpaceSuitModel.LAYER_LOCATION, SpaceSuitModel::getTexturedModelData);
        registry.register(NetheriteSpaceSuitModel.LAYER_LOCATION, NetheriteSpaceSuitModel::getTexturedModelData);
        registry.register(JetSuitModel.LAYER_LOCATION, JetSuitModel::getTexturedModelData);
    }

    public static abstract class EntityRendererRegistry {
        protected abstract <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> factory);
    }

    public static abstract class LayerDefinitionRegistry {
        public abstract void register(ModelLayerLocation location, Supplier<LayerDefinition> definition);
    }


}