package com.github.alexnijjar.ad_astra.client;

import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.ad_astra.client.registry.ClientModEntities;
import com.github.alexnijjar.ad_astra.client.registry.ClientModKeybindings;
import com.github.alexnijjar.ad_astra.client.registry.ClientModParticles;
import com.github.alexnijjar.ad_astra.client.registry.ClientModScreens;
import com.github.alexnijjar.ad_astra.client.registry.ClientModSkies;
import com.github.alexnijjar.ad_astra.client.renderer.block.EnergizerBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.FlagBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.globe.GlobeBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.globe.GlobeItemRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.globe.GlobeModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_1.RocketItemRendererTier1;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_2.RocketItemRendererTier2;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_3.RocketItemRendererTier3;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_4.RocketItemRendererTier4;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rover.RoverItemRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.JetSuitModel;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.SpaceSuitLegsModel;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.SpaceSuitModel;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.SpaceSuitRenderer;
import com.github.alexnijjar.ad_astra.client.resourcepack.Galaxy;
import com.github.alexnijjar.ad_astra.client.resourcepack.PlanetResources;
import com.github.alexnijjar.ad_astra.client.resourcepack.PlanetRing;
import com.github.alexnijjar.ad_astra.client.resourcepack.SkyRenderer;
import com.github.alexnijjar.ad_astra.client.resourcepack.SolarSystem;
import com.github.alexnijjar.ad_astra.client.screens.PlayerOverlayScreen;
import com.github.alexnijjar.ad_astra.data.Planet;
import com.github.alexnijjar.ad_astra.networking.ModS2CPackets;
import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.registry.ModFluids;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.screen.PlayerScreenHandler;

public class AdAstraClient implements ClientModInitializer {

        @Environment(EnvType.CLIENT)
        public static List<Planet> planets = new ArrayList<>();
        @Environment(EnvType.CLIENT)
        public static List<SolarSystem> solarSystems = new ArrayList<>();
        @Environment(EnvType.CLIENT)
        public static List<SkyRenderer> skyRenderers = new ArrayList<>();
        @Environment(EnvType.CLIENT)
        public static List<PlanetRing> planetRings = new ArrayList<>();
        @Environment(EnvType.CLIENT)
        public static List<Galaxy> galaxies = new ArrayList<>();

        @Override
        @Environment(EnvType.CLIENT)
        public void onInitializeClient() {

                // Assets
                PlanetResources.register();

                // Packets
                ModS2CPackets.register();

                // GUI
                ClientModScreens.register();

                // Entities
                ClientModEntities.register();

                // Particles
                ClientModParticles.register();

                // Keybindings
                ClientModKeybindings.register();

                // Overlays
                HudRenderCallback.EVENT.register(PlayerOverlayScreen::render);

                // Rocket item
                BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TIER_1_ROCKET, new RocketItemRendererTier1());
                BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TIER_2_ROCKET, new RocketItemRendererTier2());
                BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TIER_3_ROCKET, new RocketItemRendererTier3());
                BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TIER_4_ROCKET, new RocketItemRendererTier4());

                // Rover item
                BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TIER_1_ROVER, new RoverItemRenderer());

                // Flag entity rendering
                BlockEntityRendererRegistry.register(ModBlockEntities.FLAG_BLOCK_ENTITY, FlagBlockEntityRenderer::new);

                // Globe entity rendering
                BlockEntityRendererRegistry.register(ModBlockEntities.GLOBE_BLOCK_ENTITY, GlobeBlockEntityRenderer::new);
                EntityModelLayerRegistry.registerModelLayer(GlobeModel.LAYER_LOCATION, GlobeModel::getTexturedModelData);

                // Energizer block entity
                BlockEntityRendererRegistry.register(ModBlockEntities.ENERGIZER, EnergizerBlockEntityRenderer::new);

                // Globe item rendering
                for (Item item : new Item[] { ModItems.EARTH_GLOBE, ModItems.MOON_GLOBE, ModItems.MARS_GLOBE, ModItems.MERCURY_GLOBE, ModItems.VENUS_GLOBE, ModItems.GLACIO_GLOBE }) {
                        BuiltinItemRendererRegistry.INSTANCE.register(item, new GlobeItemRenderer());
                }

                // Custom space suit rendering
                EntityModelLayerRegistry.registerModelLayer(SpaceSuitModel.LAYER_LOCATION, SpaceSuitModel::getTexturedModelData);
                EntityModelLayerRegistry.registerModelLayer(SpaceSuitLegsModel.LAYER_LOCATION, SpaceSuitLegsModel::getTexturedModelData);
                EntityModelLayerRegistry.registerModelLayer(JetSuitModel.LAYER_LOCATION, JetSuitModel::getTexturedModelData);
                SpaceSuitRenderer.register();

                // Fluids
                FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.FUEL_STILL, ModFluids.FLOWING_FUEL,
                                new SimpleFluidRenderHandler(new ModIdentifier("block/fluid_fuel_still"), new ModIdentifier("block/fluid_fuel_flow"), new ModIdentifier("block/fuel_overlay")));
                FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.CRYO_FUEL_STILL, ModFluids.FLOWING_CRYO_FUEL,
                                new SimpleFluidRenderHandler(new ModIdentifier("block/fluid_cryo_fuel_still"), new ModIdentifier("block/fluid_cryo_fuel_flow"), new ModIdentifier("block/cryo_fuel_overlay")));
                FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.OIL_STILL, ModFluids.FLOWING_OIL,
                                new SimpleFluidRenderHandler(new ModIdentifier("block/fluid_oil_still"), new ModIdentifier("block/fluid_oil_flow"), new ModIdentifier("block/oil_overlay")));
                FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.OXYGEN_STILL, new SimpleFluidRenderHandler(new ModIdentifier("block/fluid_oxygen_still"), new ModIdentifier("block/fluid_oxygen_still"), new ModIdentifier("block/fluid_oxygen_still")));

                // Fluid textures
                ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
                        registry.register(new ModIdentifier("block/fluid_fuel_still"));
                        registry.register(new ModIdentifier("block/fluid_fuel_flow"));
                        registry.register(new ModIdentifier("block/fuel_overlay"));

                        registry.register(new ModIdentifier("block/fluid_cryo_fuel_still"));
                        registry.register(new ModIdentifier("block/fluid_cryo_fuel_flow"));
                        registry.register(new ModIdentifier("block/cryo_fuel_overlay"));

                        registry.register(new ModIdentifier("block/fluid_oil_still"));
                        registry.register(new ModIdentifier("block/fluid_oil_flow"));
                        registry.register(new ModIdentifier("block/oil_overlay"));

                        registry.register(new ModIdentifier("block/fluid_oxygen_still"));
                });

                BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.FUEL_STILL, ModFluids.FLOWING_FUEL);
                BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.CRYO_FUEL_STILL, ModFluids.FLOWING_CRYO_FUEL);
                BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.OIL_STILL, ModFluids.FLOWING_OIL);
                BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.OXYGEN_STILL);

                BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.WATER_PUMP, ModBlocks.ENERGIZER, ModBlocks.STEEL_DOOR, ModBlocks.COAL_TORCH, ModBlocks.WALL_COAL_TORCH);
                BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ModBlocks.COAL_LANTERN);
                BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.NASA_WORKBENCH);
        }

        // Register after the Resource packs have been loaded
        @Environment(EnvType.CLIENT)
        public static void postAssetRegister() {
                // World sky
                ClientModSkies.register();
        }
}
