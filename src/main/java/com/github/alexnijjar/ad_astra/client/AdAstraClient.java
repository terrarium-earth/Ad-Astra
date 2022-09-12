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
import com.github.alexnijjar.ad_astra.client.renderer.block.SlidingDoorBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.globe.GlobeBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.globe.GlobeItemRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.globe.GlobeModel;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_1.RocketItemRendererTier1;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_2.RocketItemRendererTier2;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_3.RocketItemRendererTier3;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_4.RocketItemRendererTier4;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rover.RoverItemRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.JetSuitModel;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.NetheriteSpaceSuitModel;
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
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.Item;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.BlockPos;

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

		BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.AERONOS_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
			BlockEntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getBlockEntityRenderDispatcher();
			dispatcher.renderEntity(new ChestBlockEntity(BlockPos.ORIGIN, ModBlocks.AERONOS_CHEST.getDefaultState()), matrices, vertexConsumers, light, overlay);
		});

		BuiltinItemRendererRegistry.INSTANCE.register(ModBlocks.STROPHAR_CHEST, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
			BlockEntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getBlockEntityRenderDispatcher();
			dispatcher.renderEntity(new ChestBlockEntity(BlockPos.ORIGIN, ModBlocks.STROPHAR_CHEST.getDefaultState()), matrices, vertexConsumers, light, overlay);
		});

		// Rover item
		BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TIER_1_ROVER, new RoverItemRenderer());

		// Flag entity rendering
		BlockEntityRendererRegistry.register(ModBlockEntities.FLAG_BLOCK_ENTITY, FlagBlockEntityRenderer::new);

		// Globe entity rendering
		BlockEntityRendererRegistry.register(ModBlockEntities.GLOBE_BLOCK_ENTITY, GlobeBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(GlobeModel.LAYER_LOCATION, GlobeModel::getTexturedModelData);

		// Energizer block entity
		BlockEntityRendererRegistry.register(ModBlockEntities.ENERGIZER, EnergizerBlockEntityRenderer::new);

		// Sliding door
		BlockEntityRendererRegistry.register(ModBlockEntities.SLIDING_DOOR, SlidingDoorBlockEntityRenderer::new);

		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.IRON_SLIDING_DOOR_MODEL));
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.STEEL_SLIDING_DOOR_MODEL));
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.DESH_SLIDING_DOOR_MODEL));
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.OSTRUM_SLIDING_DOOR_MODEL));
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.CALORITE_SLIDING_DOOR_MODEL));
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.AIRLOCK_MODEL));
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.REINFORCED_DOOR_MODEL));

		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.IRON_SLIDING_DOOR_MODEL_FLIPPED));
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.STEEL_SLIDING_DOOR_MODEL_FLIPPED));
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.DESH_SLIDING_DOOR_MODEL_FLIPPED));
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.OSTRUM_SLIDING_DOOR_MODEL_FLIPPED));
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.CALORITE_SLIDING_MODEL_FLIPPED));
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.AIRLOCK_MODEL_FLIPPED));
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SlidingDoorBlockEntityRenderer.REINFORCED_DOOR_MODEL_FLIPPED));

		// Globe item rendering
		for (Item item : new Item[] { ModItems.EARTH_GLOBE, ModItems.MOON_GLOBE, ModItems.MARS_GLOBE, ModItems.MERCURY_GLOBE, ModItems.VENUS_GLOBE, ModItems.GLACIO_GLOBE }) {
			BuiltinItemRendererRegistry.INSTANCE.register(item, new GlobeItemRenderer());
		}

		// Custom space suit rendering
		EntityModelLayerRegistry.registerModelLayer(SpaceSuitModel.LAYER_LOCATION, SpaceSuitModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(NetheriteSpaceSuitModel.LAYER_LOCATION, NetheriteSpaceSuitModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(JetSuitModel.LAYER_LOCATION, JetSuitModel::getTexturedModelData);
		SpaceSuitRenderer.register();

		// Fluids
		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.FUEL_STILL, ModFluids.FLOWING_FUEL,
				new SimpleFluidRenderHandler(new ModIdentifier("block/fluid_fuel_still"), new ModIdentifier("block/fluid_fuel_flow"), new ModIdentifier("block/fuel_overlay")));
		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.CRYO_FUEL_STILL, ModFluids.FLOWING_CRYO_FUEL,
				new SimpleFluidRenderHandler(new ModIdentifier("block/fluid_cryo_fuel_still"), new ModIdentifier("block/fluid_cryo_fuel_flow"), new ModIdentifier("block/cryo_fuel_overlay")));
		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.OIL_STILL, ModFluids.FLOWING_OIL, new SimpleFluidRenderHandler(new ModIdentifier("block/fluid_oil_still"), new ModIdentifier("block/fluid_oil_flow"), new ModIdentifier("block/oil_overlay")));
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

		// Chest textures
		ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
			registry.register(new ModIdentifier("entity/chest/aeronos_chest"));
			registry.register(new ModIdentifier("entity/chest/aeronos_chest_right"));
			registry.register(new ModIdentifier("entity/chest/aeronos_chest_left"));
			registry.register(new ModIdentifier("entity/chest/strophar_chest"));
			registry.register(new ModIdentifier("entity/chest/strophar_chest_right"));
			registry.register(new ModIdentifier("entity/chest/strophar_chest_left"));
		});

		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.FUEL_STILL, ModFluids.FLOWING_FUEL);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.CRYO_FUEL_STILL, ModFluids.FLOWING_CRYO_FUEL);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.OIL_STILL, ModFluids.FLOWING_OIL);
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.OXYGEN_STILL);

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.WATER_PUMP, ModBlocks.ENERGIZER, ModBlocks.STEEL_DOOR, ModBlocks.STEEL_TRAPDOOR, ModBlocks.GLACIAN_DOOR, ModBlocks.GLACIAN_TRAPDOOR, ModBlocks.AERONOS_DOOR,
				ModBlocks.AERONOS_TRAPDOOR, ModBlocks.STROPHAR_DOOR, ModBlocks.STROPHAR_TRAPDOOR, ModBlocks.EXTINGUISHED_TORCH, ModBlocks.WALL_EXTINGUISHED_TORCH);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ModBlocks.EXTINGUISHED_LANTERN, ModBlocks.GLACIAN_LEAVES);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.NASA_WORKBENCH, ModBlocks.AERONOS_MUSHROOM, ModBlocks.STROPHAR_MUSHROOM, ModBlocks.AERONOS_LADDER, ModBlocks.STROPHAR_LADDER, ModBlocks.AERONOS_CHEST,
				ModBlocks.STROPHAR_CHEST);

		// Sign textures
		TexturedRenderLayers.WOOD_TYPE_TEXTURES.put(ModBlocks.GLACIAN, new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, new ModIdentifier("entity/signs/" + ModBlocks.GLACIAN.getName())));
	}

	// Register after the Resource packs have been loaded
	@Environment(EnvType.CLIENT)
	public static void postAssetRegister() {
		// World sky
		ClientModSkies.register();
	}
}
