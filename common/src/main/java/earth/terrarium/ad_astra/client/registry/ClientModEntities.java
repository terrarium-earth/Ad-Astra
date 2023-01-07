package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.client.renderer.armor.JetSuitModel;
import earth.terrarium.ad_astra.client.renderer.armor.NetheriteSpaceSuitModel;
import earth.terrarium.ad_astra.client.renderer.armor.SpaceSuitModel;
import earth.terrarium.ad_astra.client.renderer.block.globe.GlobeModel;
import earth.terrarium.ad_astra.client.renderer.entity.mob.*;
import earth.terrarium.ad_astra.client.renderer.entity.mob.model.*;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.lander.LanderModel;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.lander.LanderRenderer;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_1.RocketModelTier1;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_1.RocketRendererTier1;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_2.RocketModelTier2;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_2.RocketRendererTier2;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_3.RocketModelTier3;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_3.RocketRendererTier3;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_4.RocketModelTier4;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_4.RocketRendererTier4;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rover.RoverModel;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rover.RoverRenderer;
import earth.terrarium.ad_astra.common.registry.ModEntityTypes;
import earth.terrarium.botarium.client.ClientHooks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ClientModEntities {

    public static void registerEntityRenderers() {
        ClientHooks.registerEntityRenderer(ModEntityTypes.LUNARIAN, LunarianRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.CORRUPTED_LUNARIAN, CorruptedLunarianRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.STAR_CRAWLER, StarCrawlerRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.MARTIAN_RAPTOR, MartianRaptorRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.PYGRO, PygroRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.ZOMBIFIED_PYGRO, ZombifiedPygroRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.PYGRO_BRUTE, PygroBruteRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.MOGLER, MoglerRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.ZOMBIFIED_MOGLER, ZombifiedMoglerRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.SULFUR_CREEPER, SulfurCreeperRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.LUNARIAN_WANDERING_TRADER, LunarianWanderingTraderRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.GLACIAN_RAM, GlacianRamRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.TIER_1_ROCKET, RocketRendererTier1::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.TIER_2_ROCKET, RocketRendererTier2::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.TIER_3_ROCKET, RocketRendererTier3::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.TIER_4_ROCKET, RocketRendererTier4::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.TIER_1_ROVER, RoverRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.LANDER, LanderRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.SPACE_PAINTING, PaintingRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.ICE_SPIT, ThrownItemRenderer::new);
    }

    public static void registerEntityLayers(LayerDefinitionRegistry registry) {
        // Layers
        registry.register(LunarianModel.LAYER_LOCATION, LunarianModel::createBodyLayer);
        registry.register(CorruptedLunarianModel.LAYER_LOCATION, CorruptedLunarianModel::createBodyLayer);
        registry.register(StarCrawlerModel.LAYER_LOCATION, StarCrawlerModel::createBodyLayer);
        registry.register(MartianRaptorModel.LAYER_LOCATION, MartianRaptorModel::createBodyLayer);
        registry.register(PygroModel.LAYER_LOCATION, PygroModel::createBodyLayer);
        registry.register(PygroBruteModel.LAYER_LOCATION, PygroBruteModel::createBodyLayer);
        registry.register(ZombifiedPygroModel.LAYER_LOCATION, ZombifiedPygroModel::createBodyLayer);
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

    public static abstract class LayerDefinitionRegistry {
        public abstract void register(ModelLayerLocation location, Supplier<LayerDefinition> definition);
    }
}