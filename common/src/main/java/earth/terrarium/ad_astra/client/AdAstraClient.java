package earth.terrarium.ad_astra.client;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.renderer.block.generators.SolarPanelBlockRenderer;
import earth.terrarium.ad_astra.client.renderer.block.generators.SteamGeneratorBlockRenderer;
import earth.terrarium.ad_astra.client.renderer.block.SlidingDoorBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.flag.FlagBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.globe.GlobeRenderer;
import earth.terrarium.ad_astra.client.renderer.block.machines.EtrionicPressBlockRenderer;
import earth.terrarium.ad_astra.client.screen.machine.TestScreen;
import earth.terrarium.ad_astra.client.util.ClientPlatformUtils;
import earth.terrarium.ad_astra.common.item.EtrionicCapacitorItem;
import earth.terrarium.ad_astra.common.registry.*;
import earth.terrarium.botarium.client.ClientHooks;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AdAstraClient {

    public static void init() {
        registerScreens();
        registerEntityRenderers();
        registerBlockRenderTypes();
        registerBlockEntityRenderers();
        registerItemProperties();
    }

    private static void registerScreens() {
        ClientPlatformUtils.registerScreen(ModMenus.ETRIONIC_GENERATOR.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.COMBUSTION_GENERATOR.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.GEOTHERMAL_GENERATING.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.SOLAR_PANEL.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.ETRIONIC_BLAST_FURNACE.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.HYDRAULIC_PRESS.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.ELECTROLYZER.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.OIL_REFINERY.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.CRYOGENIC_FREEZER.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.RECYCLER.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.OXYGEN_DISTRIBUTOR.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.TEMPERATURE_REGULATOR.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.GRAVITY_NORMALIZER.get(), TestScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.WATER_PUMP.get(), TestScreen::new);
    }

    public static void registerEntityRenderers() {
        ClientHooks.registerEntityRenderer(ModEntityTypes.AIR_VORTEX, NoopRenderer::new);
    }

    public static void registerBlockRenderTypes() {
        ModBlocks.GLOBES.stream().forEach(b -> ClientHooks.setRenderLayer(b.get(), RenderType.cutout()));
        ClientHooks.setRenderLayer(ModBlocks.VENT.get(), RenderType.cutout());
        ClientHooks.setRenderLayer(ModBlocks.ETRIONIC_BLAST_FURNACE.get(), RenderType.cutout());
    }

    public static void onRegisterFluidRenderTypes(BiConsumer<RenderType, Fluid> register) {
        ModFluids.FLUIDS.stream().forEach(fluid -> register.accept(RenderType.translucent(), fluid.get()));
    }

    private static void registerBlockEntityRenderers() {
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.FLAG.get(), FlagBlockEntityRenderer::new);
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.GLOBE.get(), GlobeRenderer::new);
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.SLIDING_DOOR.get(), SlidingDoorBlockEntityRenderer::new);
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.ETRIONIC_GENERATOR.get(), context -> new SteamGeneratorBlockRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.SOLAR_PANEL.get(), context -> new SolarPanelBlockRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.ETRIONIC_PRESS.get(), EtrionicPressBlockRenderer::new);
    }

    public static void onRegisterModels(Consumer<ResourceLocation> register) {
        ModBlocks.GLOBES.stream().forEach(b -> register.accept(new ResourceLocation(AdAstra.MOD_ID, "block/" + b.getId().getPath() + "_cube")));
        ModBlocks.SLIDING_DOORS.stream().forEach(block -> register.accept(new ResourceLocation(AdAstra.MOD_ID, "block/" + block.getId().getPath())));
        ModBlocks.SLIDING_DOORS.stream().forEach(block -> register.accept(new ResourceLocation(AdAstra.MOD_ID, "block/" + block.getId().getPath() + "_flipped")));
    }

    public static void onRegisterItemRenderers(BiConsumer<ItemLike, BlockEntityWithoutLevelRenderer> register) {
        ModItems.GLOBES.stream().forEach(item -> register.accept(item.get(), new GlobeRenderer.ItemRenderer()));
    }

    private static void registerItemProperties() {
        ClientPlatformUtils.registerItemProperty(ModItems.ETRIONIC_CAPACITOR.get(), new ResourceLocation(AdAstra.MOD_ID, "toggled"), EtrionicCapacitorItem::itemProperty);
    }

    public static void onRegisterHud(Consumer<ClientPlatformUtils.RenderHud> register) {
    }

    public static void onRegisterReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
    }
}
