package net.mrscauthd.beyond_earth.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.screen.PlayerScreenHandler;
import net.mrscauthd.beyond_earth.client.registry.ModScreens;
import net.mrscauthd.beyond_earth.client.registry.ModSkies;
import net.mrscauthd.beyond_earth.client.renderer.FlagBlockEntityRenderer;
import net.mrscauthd.beyond_earth.client.renderer.globe.GlobeBlockEntityRenderer;
import net.mrscauthd.beyond_earth.client.renderer.globe.GlobeItemRenderer;
import net.mrscauthd.beyond_earth.client.renderer.globe.GlobeModel;
import net.mrscauthd.beyond_earth.client.renderer.spacesuit.SpaceSuitModel;
import net.mrscauthd.beyond_earth.client.renderer.spacesuit.SpaceSuitRenderer;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;
import net.mrscauthd.beyond_earth.registry.ModFluids;
import net.mrscauthd.beyond_earth.registry.ModItems;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class BeyondEarthClient implements ClientModInitializer {

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {

        // GUI.
        ModScreens.register();

        // Dimension sky
        ModSkies.register();

        // Flag entity rendering.
        BlockEntityRendererRegistry.register(ModBlockEntities.FLAG_BLOCK_ENTITY, FlagBlockEntityRenderer::new);

        // Globe entity rendering.
        BlockEntityRendererRegistry.register(ModBlockEntities.GLOBE_BLOCK_ENTITY, GlobeBlockEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(GlobeModel.LAYER_LOCATION, GlobeModel::createLayer);

        // Globe item rendering.
        for (Item item : new Item[]{
                ModItems.EARTH_GLOBE,
                ModItems.MOON_GLOBE,
                ModItems.MARS_GLOBE,
                ModItems.MERCURY_GLOBE,
                ModItems.VENUS_GLOBE,
                ModItems.GLACIO_GLOBE
        }) {
            BuiltinItemRendererRegistry.INSTANCE.register(item, new GlobeItemRenderer());
        }

        // Custom space suit rendering.
        EntityModelLayerRegistry.registerModelLayer(SpaceSuitModel.SPACE_SUIT_P1.LAYER_LOCATION, SpaceSuitModel.SPACE_SUIT_P1::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpaceSuitModel.SPACE_SUIT_P2.LAYER_LOCATION, SpaceSuitModel.SPACE_SUIT_P2::createBodyLayer);
        SpaceSuitRenderer.register();

        // Fluids.
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.FUEL_STILL, ModFluids.FLOWING_FUEL, new SimpleFluidRenderHandler(
                new ModIdentifier("blocks/fluid_fuel_still"),
                new ModIdentifier("blocks/fluid_fuel_flow"),
                new ModIdentifier("blocks/fuel_overlay")
        ));
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.OIL_STILL, ModFluids.FLOWING_OIL, new SimpleFluidRenderHandler(
                new ModIdentifier("blocks/fluid_oil_still"),
                new ModIdentifier("blocks/fluid_oil_flow"),
                new ModIdentifier("blocks/oil_overlay")
        ));

        // Fluid textures.
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(new ModIdentifier("blocks/fluid_fuel_still"));
            registry.register(new ModIdentifier("blocks/fluid_fuel_flow"));
            registry.register(new ModIdentifier("blocks/fuel_overlay"));

            registry.register(new ModIdentifier("blocks/fluid_oil_still"));
            registry.register(new ModIdentifier("blocks/fluid_oil_flow"));
            registry.register(new ModIdentifier("blocks/oil_overlay"));
        });

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.FUEL_STILL, ModFluids.FLOWING_FUEL);
    }
}
