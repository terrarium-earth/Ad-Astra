package com.github.alexnijjar.ad_astra.client;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.github.alexnijjar.ad_astra.client.registry.ClientModEntities;
import com.github.alexnijjar.ad_astra.client.registry.ClientModKeybindings;
import com.github.alexnijjar.ad_astra.client.registry.ClientModParticles;
import com.github.alexnijjar.ad_astra.client.registry.ClientModScreens;
import com.github.alexnijjar.ad_astra.client.renderer.block.ChestItemRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.EnergizerBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.LaunchPadBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.SlidingDoorBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.flag.FlagBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.flag.FlagItemRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.globe.GlobeBlockEntityRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.block.globe.GlobeItemRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_1.RocketItemRendererTier1;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_2.RocketItemRendererTier2;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_3.RocketItemRendererTier3;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rockets.tier_4.RocketItemRendererTier4;
import com.github.alexnijjar.ad_astra.client.renderer.entity.vehicles.rover.RoverItemRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.spacesuit.SpaceSuitRenderer;
import com.github.alexnijjar.ad_astra.client.resourcepack.Galaxy;
import com.github.alexnijjar.ad_astra.client.resourcepack.PlanetResources;
import com.github.alexnijjar.ad_astra.client.resourcepack.PlanetRing;
import com.github.alexnijjar.ad_astra.client.resourcepack.SkyRenderer;
import com.github.alexnijjar.ad_astra.client.resourcepack.SolarSystem;
import com.github.alexnijjar.ad_astra.client.screens.PlayerOverlayScreen;
import com.github.alexnijjar.ad_astra.data.Planet;
import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class AdAstraClient {

    public static List<Planet> planets = new ArrayList<>();
    public static List<SolarSystem> solarSystems = new ArrayList<>();
    public static List<SkyRenderer> skyRenderers = new ArrayList<>();
    public static List<PlanetRing> planetRings = new ArrayList<>();
    public static List<Galaxy> galaxies = new ArrayList<>();

    public void onInitializeClient() {

        PlanetResources.register();
        ClientModScreens.register();
        ClientModEntities.register();
        ClientModParticles.register();
        ClientModKeybindings.register();
        ClientGuiEvent.RENDER_HUD.register(PlayerOverlayScreen::render);




        BlockEntityRendererRegistry.register(ModBlockEntities.FLAG_BLOCK_ENTITY.get(), FlagBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.LAUNCH_PAD.get(), LaunchPadBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.GLOBE_BLOCK_ENTITY.get(), GlobeBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.ENERGIZER.get(), EnergizerBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.SLIDING_DOOR.get(), SlidingDoorBlockEntityRenderer::new);



        SpaceSuitRenderer.register();

        RenderTypeRegistry.register(RenderLayer.getCutout(), ModBlocks.WATER_PUMP.get(), ModBlocks.ENERGIZER.get(), ModBlocks.STEEL_DOOR.get(), ModBlocks.GLACIAN_DOOR.get(), ModBlocks.GLACIAN_TRAPDOOR.get(), ModBlocks.AERONOS_DOOR.get(), ModBlocks.AERONOS_TRAPDOOR.get(), ModBlocks.STROPHAR_DOOR.get(), ModBlocks.STROPHAR_TRAPDOOR.get(), ModBlocks.EXTINGUISHED_TORCH.get(), ModBlocks.WALL_EXTINGUISHED_TORCH.get());
        RenderTypeRegistry.register(RenderLayer.getTranslucent(), ModBlocks.EXTINGUISHED_LANTERN.get(), ModBlocks.GLACIAN_LEAVES.get());
        RenderTypeRegistry.register(RenderLayer.getCutout(), ModBlocks.NASA_WORKBENCH.get(), ModBlocks.AERONOS_MUSHROOM.get(), ModBlocks.STROPHAR_MUSHROOM.get(), ModBlocks.AERONOS_LADDER.get(), ModBlocks.STROPHAR_LADDER.get(), ModBlocks.AERONOS_CHEST.get(), ModBlocks.STROPHAR_CHEST.get());

        // Sign textures
        TexturedRenderLayers.WOOD_TYPE_TEXTURES.put(ModBlocks.GLACIAN, new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, new ModIdentifier("entity/signs/" + ModBlocks.GLACIAN.getName())));
    }

    public static void onRegisterItemRenderers(BiConsumer<ItemConvertible, BuiltinModelItemRenderer> register) {
        register.accept(ModItems.TIER_1_ROCKET.get(), new RocketItemRendererTier1());
        register.accept(ModItems.TIER_2_ROCKET.get(), new RocketItemRendererTier2());
        register.accept(ModItems.TIER_3_ROCKET.get(), new RocketItemRendererTier3());
        register.accept(ModItems.TIER_4_ROCKET.get(), new RocketItemRendererTier4());
        register.accept(ModItems.TIER_1_ROVER.get(), new RoverItemRenderer());

        register.accept(ModBlocks.AERONOS_CHEST.get(), new ChestItemRenderer(ModBlocks.AERONOS_CHEST.get()));
        register.accept(ModBlocks.STROPHAR_CHEST.get(), new ChestItemRenderer(ModBlocks.STROPHAR_CHEST.get()));

        for (Item item : new Item[]{ModItems.EARTH_GLOBE.get(), ModItems.MOON_GLOBE.get(), ModItems.MARS_GLOBE.get(), ModItems.MERCURY_GLOBE.get(), ModItems.VENUS_GLOBE.get(), ModItems.GLACIO_GLOBE.get()}) {
            register.accept(item, new GlobeItemRenderer());
        }

        for (Item item : new Item[]{ModItems.WHITE_FLAG.get(), ModItems.BLACK_FLAG.get(), ModItems.BLUE_FLAG.get(), ModItems.BROWN_FLAG.get(), ModItems.CYAN_FLAG.get(), ModItems.GRAY_FLAG.get(), ModItems.GREEN_FLAG.get(), ModItems.LIGHT_BLUE_FLAG.get(), ModItems.LIGHT_GRAY_FLAG.get(), ModItems.LIME_FLAG.get(), ModItems.MAGENTA_FLAG.get(), ModItems.ORANGE_FLAG.get(), ModItems.PINK_FLAG.get(), ModItems.PURPLE_FLAG.get(), ModItems.RED_FLAG.get(), ModItems.YELLOW_FLAG.get()}) {
            register.accept(item, new FlagItemRenderer());
        }

        register.accept(ModItems.LAUNCH_PAD.get(), new LaunchPadBlockEntityRenderer());
    }

    public static void onRegisterChestSprites(Consumer<Identifier> register) {
        register.accept(new ModIdentifier("entity/chest/aeronos_chest"));
        register.accept(new ModIdentifier("entity/chest/aeronos_chest_right"));
        register.accept(new ModIdentifier("entity/chest/aeronos_chest_left"));
        register.accept(new ModIdentifier("entity/chest/strophar_chest"));
        register.accept(new ModIdentifier("entity/chest/strophar_chest_right"));
        register.accept(new ModIdentifier("entity/chest/strophar_chest_left"));
    }

    public static void onRegisterModels(Consumer<Identifier> register) {
        for (Item item : new Item[]{ModItems.WHITE_FLAG.get(), ModItems.BLACK_FLAG.get(), ModItems.BLUE_FLAG.get(), ModItems.BROWN_FLAG.get(), ModItems.CYAN_FLAG.get(), ModItems.GRAY_FLAG.get(), ModItems.GREEN_FLAG.get(), ModItems.LIGHT_BLUE_FLAG.get(), ModItems.LIGHT_GRAY_FLAG.get(), ModItems.LIME_FLAG.get(), ModItems.MAGENTA_FLAG.get(), ModItems.ORANGE_FLAG.get(), ModItems.PINK_FLAG.get(), ModItems.PURPLE_FLAG.get(), ModItems.RED_FLAG.get(), ModItems.YELLOW_FLAG.get()}) {
            register.accept(new ModIdentifier("block/flag/" + Registry.ITEM.getId(item).getPath()));
        }
        for (Identifier id : new Identifier[]{SlidingDoorBlockEntityRenderer.IRON_SLIDING_DOOR_MODEL, SlidingDoorBlockEntityRenderer.STEEL_SLIDING_DOOR_MODEL, SlidingDoorBlockEntityRenderer.DESH_SLIDING_DOOR_MODEL, SlidingDoorBlockEntityRenderer.OSTRUM_SLIDING_DOOR_MODEL, SlidingDoorBlockEntityRenderer.CALORITE_SLIDING_DOOR_MODEL, SlidingDoorBlockEntityRenderer.AIRLOCK_MODEL, SlidingDoorBlockEntityRenderer.REINFORCED_DOOR_MODEL, SlidingDoorBlockEntityRenderer.IRON_SLIDING_DOOR_MODEL_FLIPPED, SlidingDoorBlockEntityRenderer.STEEL_SLIDING_DOOR_MODEL_FLIPPED, SlidingDoorBlockEntityRenderer.DESH_SLIDING_DOOR_MODEL_FLIPPED, SlidingDoorBlockEntityRenderer.OSTRUM_SLIDING_DOOR_MODEL_FLIPPED, SlidingDoorBlockEntityRenderer.CALORITE_SLIDING_MODEL_FLIPPED, SlidingDoorBlockEntityRenderer.AIRLOCK_MODEL_FLIPPED, SlidingDoorBlockEntityRenderer.REINFORCED_DOOR_MODEL_FLIPPED}) {
            register.accept(id);
        }
        register.accept(LaunchPadBlockEntityRenderer.LAUNCH_PAD_MODEL);
    }

    public static void renderBlock(Identifier texture, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        MinecraftClient client = MinecraftClient.getInstance();
        BakedModelManager manager = client.getBakedModelManager();
        BakedModel model = ClientUtils.getModel(manager, texture);

        VertexConsumer vertexConsumer1 = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));
        List<BakedQuad> quads1 = model.getQuads(null, null, client.world.random);
        MatrixStack.Entry entry1 = matrices.peek();

        for (BakedQuad quad : quads1) {
            vertexConsumer1.bakedQuad(entry1, quad, 1, 1, 1, light, overlay);
        }
    }
}
