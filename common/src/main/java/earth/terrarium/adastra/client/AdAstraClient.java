package earth.terrarium.adastra.client;

import earth.terrarium.adastra.client.renderers.blocks.machines.OxygenDistributorRenderer;
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
        ClientHooks.setRenderLayer(ModBlocks.OXYGEN_DISTRIBUTOR.get(), RenderType.cutout());
    }

    private static void registerBlockEntityRenderers() {
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.OXYGEN_DISTRIBUTOR.get(), context -> new OxygenDistributorRenderer());
    }

    private static void registerItemRenderers() {
        ITEM_RENDERERS.put(ModBlocks.OXYGEN_DISTRIBUTOR.get().asItem(), new CustomGeoItemRenderer(new DefaultedBlockGeoModel<>(OxygenDistributorRenderer.MODEL)));
    }

    public static BlockEntityWithoutLevelRenderer getItemRenderer(ItemLike item) {
        return ITEM_RENDERERS.get(item.asItem());
    }
}
