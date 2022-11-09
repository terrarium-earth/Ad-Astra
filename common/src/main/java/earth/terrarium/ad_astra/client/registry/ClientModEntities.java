package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.client.renderer.armour.JetSuitModel;
import earth.terrarium.ad_astra.client.renderer.armour.NetheriteSpaceSuitModel;
import earth.terrarium.ad_astra.client.renderer.armour.SpaceSuitModel;
import earth.terrarium.ad_astra.client.renderer.block.globe.GlobeModel;
import earth.terrarium.ad_astra.client.renderer.entity.mobs.*;
import earth.terrarium.ad_astra.client.renderer.entity.mobs.models.*;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.lander.LanderModel;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.lander.LanderRenderer;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_1.RocketModelTier1;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_1.RocketRendererTier1;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_2.RocketModelTier2;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_2.RocketRendererTier2;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_3.RocketModelTier3;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_3.RocketRendererTier3;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_4.RocketModelTier4;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rockets.tier_4.RocketRendererTier4;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rover.RoverModel;
import earth.terrarium.ad_astra.client.renderer.entity.vehicles.rover.RoverRenderer;
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
        registry.register(ModEntityTypes.LUNARIAN, LunarianRenderer::new);
        registry.register(ModEntityTypes.CORRUPTED_LUNARIAN, CorruptedLunarianRenderer::new);
        registry.register(ModEntityTypes.STAR_CRAWLER, StarCrawlerRenderer::new);
        registry.register(ModEntityTypes.MARTIAN_RAPTOR, MartianRaptorRenderer::new);
        registry.register(ModEntityTypes.PYGRO, PygroRenderer::new);
        registry.register(ModEntityTypes.ZOMBIFIED_PYGRO, PygroRenderer::new);
        registry.register(ModEntityTypes.PYGRO_BRUTE, PygroRenderer::new);
        registry.register(ModEntityTypes.MOGLER, MoglerRenderer::new);
        registry.register(ModEntityTypes.ZOMBIFIED_MOGLER, ZombifiedMoglerRenderer::new);
        registry.register(ModEntityTypes.SULFUR_CREEPER, SulfurCreeperRenderer::new);
        registry.register(ModEntityTypes.LUNARIAN_WANDERING_TRADER, LunarianWanderingTraderRenderer::new);
        registry.register(ModEntityTypes.GLACIAN_RAM, GlacianRamRenderer::new);
        registry.register(ModEntityTypes.TIER_1_ROCKET, RocketRendererTier1::new);
        registry.register(ModEntityTypes.TIER_2_ROCKET, RocketRendererTier2::new);
        registry.register(ModEntityTypes.TIER_3_ROCKET, RocketRendererTier3::new);
        registry.register(ModEntityTypes.TIER_4_ROCKET, RocketRendererTier4::new);
        registry.register(ModEntityTypes.TIER_1_ROVER, RoverRenderer::new);
        registry.register(ModEntityTypes.LANDER, LanderRenderer::new);
        registry.register(ModEntityTypes.SPACE_PAINTING, PaintingRenderer::new);
        registry.register(ModEntityTypes.ICE_SPIT, ThrownItemRenderer::new);
    }

    public static void registerEntityLayers(LayerDefinitionRegistry registry) {
        // Layers
        registry.register(LunarianModel.LAYER_LOCATION, LunarianModel::createBodyLayer);
        registry.register(CorruptedLunarianModel.LAYER_LOCATION, CorruptedLunarianModel::createBodyLayer);
        registry.register(StarCrawlerModel.LAYER_LOCATION, StarCrawlerModel::createBodyLayer);
        registry.register(MartianRaptorModel.LAYER_LOCATION, MartianRaptorModel::createBodyLayer);
        registry.register(PygroModel.LAYER_LOCATION, PygroModel::createBodyLayer);
        registry.register(MoglerModel.LAYER_LOCATION, MoglerModel::createBodyLayer);
        registry.register(SulfurCreeperModel.LAYER_LOCATION, SulfurCreeperModel::createBodyLayer);
        registry.register(GlacianRamModel.LAYER_LOCATION, GlacianRamModel::createBodyLayer);

        registry.register(GlobeModel.LAYER_LOCATION, GlobeModel::createBodyLayer);

        // Machine Layers
        registry.register(RocketModelTier1.LAYER_LOCATION, RocketModelTier1::createBodyLayer);
        registry.register(RocketModelTier2.LAYER_LOCATION, RocketModelTier2::createBodyLayer);
        registry.register(RocketModelTier3.LAYER_LOCATION, RocketModelTier3::createBodyLayer);
        registry.register(RocketModelTier4.LAYER_LOCATION, RocketModelTier4::createBodyLayer);
        registry.register(RoverModel.LAYER_LOCATION, RoverModel::createBodyLayer);
        registry.register(LanderModel.LAYER_LOCATION, LanderModel::createBodyLayer);

        // Armour Layers
        registry.register(SpaceSuitModel.LAYER_LOCATION, SpaceSuitModel::createBodyLayer);
        registry.register(NetheriteSpaceSuitModel.LAYER_LOCATION, NetheriteSpaceSuitModel::createBodyLayer);
        registry.register(JetSuitModel.LAYER_LOCATION, JetSuitModel::createBodyLayer);
    }

    public static abstract class EntityRendererRegistry {
        protected abstract <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> factory);
    }

    public static abstract class LayerDefinitionRegistry {
        public abstract void register(ModelLayerLocation location, Supplier<LayerDefinition> definition);
    }


}