package earth.terrarium.adastra.client;

import earth.terrarium.adastra.client.renderers.blocks.machines.*;
import earth.terrarium.adastra.client.renderers.items.base.CustomGeoItemRenderer;
import earth.terrarium.adastra.common.handlers.PlanetData;
import earth.terrarium.adastra.common.registry.ModBlockEntityTypes;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.botarium.client.ClientHooks;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

import java.util.HashMap;
import java.util.Map;

public class AdAstraClient {
    private static final Map<Item, BlockEntityWithoutLevelRenderer> ITEM_RENDERERS = new HashMap<>();

    @Nullable
    public static PlanetData localData;

    public static void init() {
        registerBlockRenderTypes();
        registerBlockEntityRenderers();
        registerItemRenderers();
    }

    private static void registerBlockRenderTypes() {
        ClientHooks.setRenderLayer(ModBlocks.BATTERY.get(), RenderType.cutout());
        ClientHooks.setRenderLayer(ModBlocks.ETRIONIC_BLAST_FURNACE.get(), RenderType.cutout());
    }

    private static void registerBlockEntityRenderers() {
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.OXYGEN_DISTRIBUTOR.get(), context -> new OxygenDistributorBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.SOLAR_PANEL.get(), context -> new SolarPanelBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.HYDRAULIC_PRESS.get(), context -> new HydraulicPressBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.OIL_REFINERY.get(), context -> new OilRefineryBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.STEAM_GENERATOR.get(), context -> new SteamGeneratorBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.EMITTER.get(), context -> new EmitterBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.RECEIVER.get(), context -> new ReceiverBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.VESNIUM_COIL.get(), context -> new VesniumCoilBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.TINKERERS_WORKBENCH.get(), context -> new TinkerersWorkbenchBlockEntityRenderer());
    }

    private static void registerItemRenderers() {
        ITEM_RENDERERS.put(ModBlocks.OXYGEN_DISTRIBUTOR.get().asItem(), new CustomGeoItemRenderer(new DefaultedBlockGeoModel<>(OxygenDistributorBlockEntityRenderer.MODEL)));
        ITEM_RENDERERS.put(ModBlocks.ETRIONIC_SOLAR_PANEL.get().asItem(), new CustomGeoItemRenderer(new DefaultedBlockGeoModel<>(SolarPanelBlockEntityRenderer.MODEL)));
        ITEM_RENDERERS.put(ModBlocks.VESNIUM_SOLAR_PANEL.get().asItem(), new CustomGeoItemRenderer(new DefaultedBlockGeoModel<>(SolarPanelBlockEntityRenderer.MODEL)));
        ITEM_RENDERERS.put(ModBlocks.HYDRAULIC_PRESS.get().asItem(), new CustomGeoItemRenderer(new DefaultedBlockGeoModel<>(HydraulicPressBlockEntityRenderer.MODEL)));
        ITEM_RENDERERS.put(ModBlocks.OIL_REFINERY.get().asItem(), new CustomGeoItemRenderer(new DefaultedBlockGeoModel<>(OilRefineryBlockEntityRenderer.MODEL)));
        ITEM_RENDERERS.put(ModBlocks.STEAM_GENERATOR.get().asItem(), new SteamGeneratorBlockEntityRenderer.ItemRender(new DefaultedBlockGeoModel<>(SteamGeneratorBlockEntityRenderer.MODEL)));
        ITEM_RENDERERS.put(ModBlocks.EMITTER.get().asItem(), new CustomGeoItemRenderer(new DefaultedBlockGeoModel<>(EmitterBlockEntityRenderer.MODEL)));
        ITEM_RENDERERS.put(ModBlocks.RECEIVER.get().asItem(), new CustomGeoItemRenderer(new DefaultedBlockGeoModel<>(ReceiverBlockEntityRenderer.MODEL)));
        ITEM_RENDERERS.put(ModBlocks.VESNIUM_COIL.get().asItem(), new CustomGeoItemRenderer(new DefaultedBlockGeoModel<>(VesniumCoilBlockEntityRenderer.MODEL)));
        ITEM_RENDERERS.put(ModBlocks.TINKERERS_WORKBENCH.get().asItem(), new CustomGeoItemRenderer(new DefaultedBlockGeoModel<>(TinkerersWorkbenchBlockEntityRenderer.MODEL)));
    }

    public static BlockEntityWithoutLevelRenderer getItemRenderer(ItemLike item) {
        return ITEM_RENDERERS.get(item.asItem());
    }
}
