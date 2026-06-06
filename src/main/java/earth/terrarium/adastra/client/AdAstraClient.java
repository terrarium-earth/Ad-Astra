package earth.terrarium.adastra.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.client.dimension.AdAstraPlanetRenderers;
import earth.terrarium.adastra.client.models.armor.SpaceSuitModel;
import earth.terrarium.adastra.client.models.entities.mobs.*;
import earth.terrarium.adastra.client.models.entities.vehicles.LanderModel;
import earth.terrarium.adastra.client.models.entities.vehicles.RocketModel;
import earth.terrarium.adastra.client.models.entities.vehicles.RoverModel;
import earth.terrarium.adastra.client.particle.LargeFlameParticle;
import earth.terrarium.adastra.client.particle.OxygenBubbleParticle;
import earth.terrarium.adastra.client.radio.audio.RadioHandler;
import earth.terrarium.adastra.client.renderers.blocks.*;
import earth.terrarium.adastra.client.renderers.entities.mobs.*;
import earth.terrarium.adastra.client.renderers.entities.vehicles.LanderRenderer;
import earth.terrarium.adastra.client.renderers.entities.vehicles.RocketRenderer;
import earth.terrarium.adastra.client.renderers.entities.vehicles.RoverRenderer;
import earth.terrarium.adastra.client.renderers.world.OverlayRenderer;
import earth.terrarium.adastra.client.screens.PlanetsScreen;
import earth.terrarium.adastra.client.screens.machines.*;
import earth.terrarium.adastra.client.screens.player.OverlayScreen;
import earth.terrarium.adastra.client.screens.vehicles.LanderScreen;
import earth.terrarium.adastra.client.screens.vehicles.RocketScreen;
import earth.terrarium.adastra.client.screens.vehicles.RoverScreen;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.items.EtrionicCapacitorItem;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.packets.ServerboundSyncKeybindPacket;
import earth.terrarium.adastra.common.registry.*;
import earth.terrarium.adastra.common.tags.ModItemTags;
import earth.terrarium.adastra.common.utils.KeybindManager;
import earth.terrarium.adastra.common.utils.radio.RadioHolder;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AdAstraClient {

    public static final OverlayRenderer OXYGEN_OVERLAY_RENDERER = new OverlayRenderer(0x4099ccff, () -> AdAstraConfigClient.showOxygenDistributorArea, ModBlocks.OXYGEN_DISTRIBUTOR);
    public static final OverlayRenderer GRAVITY_OVERLAY_RENDERER = new OverlayRenderer(0x40DE2F14, () -> AdAstraConfigClient.showGravityNormalizerArea, ModBlocks.GRAVITY_NORMALIZER);

    public static final KeyMapping KEY_TOGGLE_SUIT_FLIGHT = new KeyMapping(
        ConstantComponents.TOGGLE_SUIT_FLIGHT_KEY.getString(),
        InputConstants.KEY_V,
        ConstantComponents.AD_ASTRA_CATEGORY.getString());

    public static final KeyMapping KEY_OPEN_RADIO = new KeyMapping(
        ConstantComponents.OPEN_RADIO_KEY.getString(),
        InputConstants.KEY_R,
        ConstantComponents.AD_ASTRA_CATEGORY.getString());

    public static void init() {
        AdAstra.CONFIGURATOR.register(AdAstraConfigClient.class);
        registerArmor();

        AdAstra.setRegistryAccess(() -> {
            var connection = Minecraft.getInstance().getConnection();
            if (connection == null) return RegistryAccess.EMPTY;
            return connection.registryAccess();
        });

        ModClientFluids.CLIENT_FLUIDS.init();
    }

    public static void registerScreens(ScreenRegistrar registrar) {
        registrar.register(ModMenus.COAL_GENERATOR.get(), CoalGeneratorScreen::new);
        registrar.register(ModMenus.COMPRESSOR.get(), CompressorScreen::new);
        registrar.register(ModMenus.ETRIONIC_BLAST_FURNACE.get(), EtrionicBlastFurnaceScreen::new);
        registrar.register(ModMenus.OXYGEN_LOADER.get(), OxygenLoaderScreen::new);
        registrar.register(ModMenus.FUEL_REFINERY.get(), FuelRefineryScreen::new);
        registrar.register(ModMenus.WATER_PUMP.get(), WaterPumpScreen::new);
        registrar.register(ModMenus.SOLAR_PANEL.get(), SolarPanelScreen::new);
        registrar.register(ModMenus.OXYGEN_DISTRIBUTOR.get(), OxygenDistributorScreen::new);
        registrar.register(ModMenus.GRAVITY_NORMALIZER.get(), GravityNormalizerScreen::new);
        registrar.register(ModMenus.CRYO_FREEZER.get(), CryoFreezerScreen::new);
        registrar.register(ModMenus.NASA_WORKBENCH.get(), NasaWorkbenchScreen::new);

        registrar.register(ModMenus.ROCKET.get(), RocketScreen::new);
        registrar.register(ModMenus.ROVER.get(), RoverScreen::new);
        registrar.register(ModMenus.LANDER.get(), LanderScreen::new);

        registrar.register(ModMenus.PLANETS.get(), PlanetsScreen::new);
    }

    public interface ScreenRegistrar {
        <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(
            MenuType<? extends M> type, MenuScreens.ScreenConstructor<M, U> factory
        );
    }

    public static void registerBlockEntityRenderers(BlockEntityRegistrar registrar) {
        registrar.register(ModBlockEntityTypes.ENERGIZER.get(), c -> new EnergizerBlockEntityRenderer());
        registrar.register(ModBlockEntityTypes.GLOBE.get(), c -> new GlobeBlockEntityRenderer());
        registrar.register(ModBlockEntityTypes.OXYGEN_DISTRIBUTOR.get(), c -> new OxygenDistributorBlockEntityRenderer());
        registrar.register(ModBlockEntityTypes.GRAVITY_NORMALIZER.get(), c -> new GravityNormalizerBlockEntityRenderer());
        registrar.register(ModBlockEntityTypes.FLAG.get(), c -> new FlagBlockEntityRenderer());
        registrar.register(ModBlockEntityTypes.SLIDING_DOOR.get(), c -> new SlidingDoorBlockEntityRenderer());
    }

    public interface BlockEntityRegistrar {
        <T extends BlockEntity, R extends BlockEntityRendererProvider<T>> void register(
            BlockEntityType<T> type, R factory
        );
    }

    public static void registerEntityRenderers(EntityRegistrar registrar) {
        registrar.register(ModEntityTypes.AIR_VORTEX.get(), NoopRenderer::new);
        registrar.register(ModEntityTypes.ROVER.get(), RoverRenderer::new);
        registrar.register(ModEntityTypes.TIER_1_ROCKET.get(), c -> new RocketRenderer(c, RocketModel.TIER_1_LAYER, RocketRenderer.TIER_1_TEXTURE));
        registrar.register(ModEntityTypes.TIER_2_ROCKET.get(), c -> new RocketRenderer(c, RocketModel.TIER_2_LAYER, RocketRenderer.TIER_2_TEXTURE));
        registrar.register(ModEntityTypes.TIER_3_ROCKET.get(), c -> new RocketRenderer(c, RocketModel.TIER_3_LAYER, RocketRenderer.TIER_3_TEXTURE));
        registrar.register(ModEntityTypes.TIER_4_ROCKET.get(), c -> new RocketRenderer(c, RocketModel.TIER_4_LAYER, RocketRenderer.TIER_4_TEXTURE));
        registrar.register(ModEntityTypes.LANDER.get(), c -> new LanderRenderer(c, LanderModel.LAYER));

        registrar.register(ModEntityTypes.LUNARIAN.get(), LunarianRenderer::new);
        registrar.register(ModEntityTypes.CORRUPTED_LUNARIAN.get(), CorruptedLunarianRenderer::new);
        registrar.register(ModEntityTypes.STAR_CRAWLER.get(), StarCrawlerRenderer::new);
        registrar.register(ModEntityTypes.MARTIAN_RAPTOR.get(), MartianRaptorRenderer::new);
        registrar.register(ModEntityTypes.PYGRO.get(), PygroRenderer::new);
        registrar.register(ModEntityTypes.ZOMBIFIED_PYGRO.get(), ZombifiedPygroRenderer::new);
        registrar.register(ModEntityTypes.PYGRO_BRUTE.get(), PygroBruteRenderer::new);
        registrar.register(ModEntityTypes.MOGLER.get(), MoglerRenderer::new);
        registrar.register(ModEntityTypes.ZOMBIFIED_MOGLER.get(), ZombifiedMoglerRenderer::new);
        registrar.register(ModEntityTypes.SULFUR_CREEPER.get(), SulfurCreeperRenderer::new);
        registrar.register(ModEntityTypes.LUNARIAN_WANDERING_TRADER.get(), LunarianWanderingTraderRenderer::new);
        registrar.register(ModEntityTypes.GLACIAN_RAM.get(), GlacianRamRenderer::new);
        registrar.register(ModEntityTypes.ICE_SPIT.get(), ThrownItemRenderer::new);
    }

    public interface EntityRegistrar {
        <T extends Entity, R extends EntityRendererProvider<T>> void register(
            EntityType<? extends T> entityType, R factory
        );
    }

    public static void registerArmor() {
        ClientPlatformUtils.registerArmor(SpaceSuitModel.SPACE_SUIT_TEXTURE, SpaceSuitModel.SPACE_SUIT_LAYER, SpaceSuitModel::new,
            ModItems.SPACE_HELMET.get(), ModItems.SPACE_SUIT.get(),
            ModItems.SPACE_PANTS.get(), ModItems.SPACE_BOOTS.get());
        ClientPlatformUtils.registerArmor(SpaceSuitModel.NETHERITE_SPACE_SUIT_TEXTURE, SpaceSuitModel.NETHERITE_SPACE_SUIT_LAYER, SpaceSuitModel::new,
            ModItems.NETHERITE_SPACE_HELMET.get(), ModItems.NETHERITE_SPACE_SUIT.get(),
            ModItems.NETHERITE_SPACE_PANTS.get(), ModItems.NETHERITE_SPACE_BOOTS.get());
        ClientPlatformUtils.registerArmor(SpaceSuitModel.JET_SUIT_TEXTURE, SpaceSuitModel.JET_SUIT_LAYER, SpaceSuitModel::new,
            ModItems.JET_SUIT_HELMET.get(), ModItems.JET_SUIT.get(),
            ModItems.JET_SUIT_PANTS.get(), ModItems.JET_SUIT_BOOTS.get());
    }

    public static void onRegisterEntityLayers(ClientPlatformUtils.LayerDefinitionRegistry consumer) {
        consumer.register(RoverModel.LAYER, RoverModel::createBodyLayer);
        RocketModel.register(consumer);
        consumer.register(LanderModel.LAYER, LanderModel::createBodyLayer);
        SpaceSuitModel.register(consumer);

        consumer.register(LunarianModel.LAYER_LOCATION, LunarianModel::createBodyLayer);
        consumer.register(CorruptedLunarianModel.LAYER_LOCATION, CorruptedLunarianModel::createBodyLayer);
        consumer.register(StarCrawlerModel.LAYER_LOCATION, StarCrawlerModel::createBodyLayer);
        consumer.register(MartianRaptorModel.LAYER_LOCATION, MartianRaptorModel::createBodyLayer);
        consumer.register(PygroModel.LAYER_LOCATION, PygroModel::createBodyLayer);
        consumer.register(PygroBruteModel.LAYER_LOCATION, PygroBruteModel::createBodyLayer);
        consumer.register(ZombifiedPygroModel.LAYER_LOCATION, ZombifiedPygroModel::createBodyLayer);
        consumer.register(MoglerModel.LAYER_LOCATION, MoglerModel::createBodyLayer);
        consumer.register(SulfurCreeperModel.LAYER_LOCATION, SulfurCreeperModel::createBodyLayer);
        consumer.register(GlacianRamModel.LAYER_LOCATION, GlacianRamModel::createBodyLayer);
    }

    public static void registerItemProperties(ItemPropertyRegistrar registrar) {
        registrar.register(ModItems.ETRIONIC_CAPACITOR.get(), ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, "toggled"), (stack, level, entity, i) -> EtrionicCapacitorItem.active(stack) ? 0 : 1);
    }

    public interface ItemPropertyRegistrar {
        <I extends Item> void register(
            I item, ResourceLocation identifier, ClampedItemPropertyFunction propertyFunction
        );
    }

    public static void registerRenderLayers(RenderLayerSetter setter) {
        setter.setRenderLayer(ModBlocks.VENT.get(), RenderType.cutout());
        setter.setRenderLayer(ModBlocks.STEEL_DOOR.get(), RenderType.cutout());
        setter.setRenderLayer(ModBlocks.STEEL_TRAPDOOR.get(), RenderType.cutout());
        setter.setRenderLayer(ModBlocks.AERONOS_LADDER.get(), RenderType.cutout());
        setter.setRenderLayer(ModBlocks.STROPHAR_LADDER.get(), RenderType.cutout());
        setter.setRenderLayer(ModBlocks.GLACIAN_TRAPDOOR.get(), RenderType.cutout());
    }

    public interface RenderLayerSetter {
        <T extends Block> void setRenderLayer(
            T block, RenderType type
        );
    }

    public static void onRegisterParticles(BiConsumer<ParticleType<SimpleParticleType>, ClientPlatformUtils.SpriteParticleRegistration<SimpleParticleType>> consumer) {
        consumer.accept(ModParticleTypes.ACID_RAIN.get(), SplashParticle.Provider::new);
        consumer.accept(ModParticleTypes.LARGE_FLAME.get(), LargeFlameParticle.Provider::new);
        consumer.accept(ModParticleTypes.LARGE_SMOKE.get(), LargeFlameParticle.Provider::new);
        consumer.accept(ModParticleTypes.OXYGEN_BUBBLE.get(), OxygenBubbleParticle.Provider::new);
    }

    public static void onRegisterModels(Consumer<ResourceLocation> consumer) {
        ModBlocks.GLOBES.stream().forEach(b -> consumer.accept(ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, "block/%s_cube".formatted(b.getId().getPath()))));
        consumer.accept(ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, "block/%s_flipped".formatted(ModBlocks.AIRLOCK.getId().getPath())));
        consumer.accept(ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, "block/%s_flipped".formatted(ModBlocks.REINFORCED_DOOR.getId().getPath())));
        consumer.accept(OxygenDistributorBlockEntityRenderer.TOP);
        consumer.accept(GravityNormalizerBlockEntityRenderer.TOP);
        consumer.accept(GravityNormalizerBlockEntityRenderer.TOE);
    }

    public static void onRegisterItemRenderers(BiConsumer<Item, BlockEntityWithoutLevelRenderer> consumer) {
        ModItems.GLOBES.stream().forEach(item -> consumer.accept(item.get(), new GlobeBlockEntityRenderer.ItemRenderer()));
        consumer.accept(ModItems.OXYGEN_DISTRIBUTOR.get(), new OxygenDistributorBlockEntityRenderer.ItemRenderer());
        consumer.accept(ModItems.GRAVITY_NORMALIZER.get(), new GravityNormalizerBlockEntityRenderer.ItemRenderer());
        consumer.accept(ModItems.ROVER.get(), new RoverRenderer.ItemRenderer());
        consumer.accept(ModItems.TIER_1_ROCKET.get(), new RocketRenderer.ItemRenderer(RocketModel.TIER_1_LAYER, RocketRenderer.TIER_1_TEXTURE));
        consumer.accept(ModItems.TIER_2_ROCKET.get(), new RocketRenderer.ItemRenderer(RocketModel.TIER_2_LAYER, RocketRenderer.TIER_2_TEXTURE));
        consumer.accept(ModItems.TIER_3_ROCKET.get(), new RocketRenderer.ItemRenderer(RocketModel.TIER_3_LAYER, RocketRenderer.TIER_3_TEXTURE));
        consumer.accept(ModItems.TIER_4_ROCKET.get(), new RocketRenderer.ItemRenderer(RocketModel.TIER_4_LAYER, RocketRenderer.TIER_4_TEXTURE));
    }

    public static void onRegisterHud(Consumer<ClientPlatformUtils.RenderHud> consumer) {
        consumer.accept(OverlayScreen::render);
    }

    public static void onAddItemColors(BiConsumer<ItemColor, ItemLike[]> consumer) {
        consumer.accept((stack, i) -> i > 0 ? -1 : DyedItemColor.getOrDefault(stack, 0xFFFFFFFF), new ItemLike[]{ModItems.SPACE_HELMET.get(), ModItems.SPACE_SUIT.get(), ModItems.SPACE_PANTS.get(), ModItems.SPACE_BOOTS.get()});
        consumer.accept((stack, i) -> i > 0 ? -1 : DyedItemColor.getOrDefault(stack, 0xFFFFFFFF), new ItemLike[]{ModItems.NETHERITE_SPACE_HELMET.get(), ModItems.NETHERITE_SPACE_SUIT.get(), ModItems.NETHERITE_SPACE_PANTS.get(), ModItems.NETHERITE_SPACE_BOOTS.get()});
        consumer.accept((stack, i) -> i > 0 ? -1 : DyedItemColor.getOrDefault(stack, 0xFFFFFFFF), new ItemLike[]{ModItems.JET_SUIT_HELMET.get(), ModItems.JET_SUIT.get(), ModItems.JET_SUIT_PANTS.get(), ModItems.JET_SUIT_BOOTS.get()});
    }

    public static void renderOverlays(PoseStack stack, Camera camera) {
        OXYGEN_OVERLAY_RENDERER.render(stack, camera);
        GRAVITY_OVERLAY_RENDERER.render(stack, camera);
    }

    public static void onAddReloadListener(BiConsumer<ResourceLocation, PreparableReloadListener> consumer) {
        consumer.accept(ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, "planet_renderers"), new AdAstraPlanetRenderers());
    }

    public static void clientTick(Minecraft minecraft) {
        LocalPlayer player = minecraft.player;
        if (player == null) return;

        if (KEY_OPEN_RADIO.consumeClick() && player.getVehicle() instanceof RadioHolder) {
            RadioHandler.open(null);
        }

        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItemTags.JET_SUITS)) {
            Options options = minecraft.options;

            if (KEY_TOGGLE_SUIT_FLIGHT.consumeClick()) {
                AdAstraConfigClient.jetSuitEnabled = !AdAstraConfigClient.jetSuitEnabled;
                Minecraft.getInstance().tell(() -> AdAstra.CONFIGURATOR.saveConfig(AdAstraConfigClient.class));
                player.displayClientMessage(AdAstraConfigClient.jetSuitEnabled ? ConstantComponents.SUIT_FLIGHT_ENABLED : ConstantComponents.SUIT_FLIGHT_DISABLED, true);
            }

            KeybindManager.set(player,
                options.keyJump.isDown(),
                options.keySprint.isDown(),
                AdAstraConfigClient.jetSuitEnabled);

            NetworkHandler.CHANNEL.sendToServer(new ServerboundSyncKeybindPacket(
                options.keyJump.isDown(),
                options.keySprint.isDown(),
                AdAstraConfigClient.jetSuitEnabled
            ));
        }
    }
}