package earth.terrarium.ad_astra.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.registry.ClientModEntities;
import earth.terrarium.ad_astra.client.registry.ClientModKeybindings;
import earth.terrarium.ad_astra.client.registry.ClientModScreens;
import earth.terrarium.ad_astra.client.renderer.armor.ArmourRenderers;
import earth.terrarium.ad_astra.client.renderer.block.ChestItemRenderer;
import earth.terrarium.ad_astra.client.renderer.block.EnergizerBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.SlidingDoorBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.flag.FlagBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.globe.GlobeBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.globe.GlobeItemRenderer;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_1.RocketItemRendererTier1;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_2.RocketItemRendererTier2;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_3.RocketItemRendererTier3;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rocket.tier_4.RocketItemRendererTier4;
import earth.terrarium.ad_astra.client.renderer.entity.vehicle.rover.RoverItemRenderer;
import earth.terrarium.ad_astra.client.resourcepack.*;
import earth.terrarium.ad_astra.client.screen.PlayerOverlayScreen;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.registry.ModFluids;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.botarium.client.ClientHooks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class AdAstraClient {

    public static List<SolarSystem> solarSystems = new ArrayList<>();
    public static List<PlanetSkyRenderer> skyRenderers = new ArrayList<>();
    public static List<PlanetRing> planetRings = new ArrayList<>();
    public static List<Galaxy> galaxies = new ArrayList<>();

    public static void init() {
        ClientModScreens.init();
        ClientModKeybindings.init();
        ArmourRenderers.init();
        ClientModEntities.registerEntityRenderers();

        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.FLAG.get(), FlagBlockEntityRenderer::new);
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.GLOBE.get(), GlobeBlockEntityRenderer::new);
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.ENERGIZER.get(), EnergizerBlockEntityRenderer::new);
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.SLIDING_DOOR.get(), SlidingDoorBlockEntityRenderer::new);
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.SIGN.get(), SignRenderer::new);
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.CHEST.get(), ChestRenderer::new);

        // Sign textures
        Sheets.SIGN_MATERIALS.put(ModBlocks.GLACIAN_SIGN_TYPE, new Material(Sheets.SIGN_SHEET, new ResourceLocation(AdAstra.MOD_ID, "entity/signs/glacian")));
    }

    public static void onRegisterHud(Consumer<RenderHud> register) {
        register.accept(PlayerOverlayScreen::render);
    }

    public static void onRegisterFluidRenderTypes(TriConsumer<RenderType, Fluid, Fluid> register) {
        register.accept(RenderType.translucent(), ModFluids.FUEL.get(), ModFluids.FLOWING_FUEL.get());
        register.accept(RenderType.translucent(), ModFluids.CRYO_FUEL.get(), ModFluids.FLOWING_CRYO_FUEL.get());
        register.accept(RenderType.translucent(), ModFluids.OIL.get(), ModFluids.FLOWING_OIL.get());
        register.accept(RenderType.translucent(), ModFluids.OXYGEN.get(), ModFluids.FLOWING_OXYGEN.get());
    }

    public static void onRegisterBlockRenderTypes(BiConsumer<RenderType, List<Block>> register) {
        ModBlocks.GLOBES.stream().forEach(block -> register.accept(RenderType.cutout(), List.of(block.get())));
        register.accept(RenderType.cutout(), List.of(
                ModBlocks.WATER_PUMP.get(), ModBlocks.ENERGIZER.get(), ModBlocks.STEEL_DOOR.get(), ModBlocks.STEEL_TRAPDOOR.get(), ModBlocks.GLACIAN_DOOR.get(),
                ModBlocks.GLACIAN_TRAPDOOR.get(), ModBlocks.AERONOS_DOOR.get(), ModBlocks.AERONOS_TRAPDOOR.get(), ModBlocks.STROPHAR_DOOR.get(), ModBlocks.STROPHAR_TRAPDOOR.get(),
                ModBlocks.EXTINGUISHED_TORCH.get(), ModBlocks.WALL_EXTINGUISHED_TORCH.get(), ModBlocks.EXTINGUISHED_LANTERN.get(), ModBlocks.GLACIAN_LEAVES.get(),
                ModBlocks.NASA_WORKBENCH.get(), ModBlocks.AERONOS_MUSHROOM.get(), ModBlocks.STROPHAR_MUSHROOM.get(), ModBlocks.AERONOS_LADDER.get(), ModBlocks.STROPHAR_LADDER.get(),
                ModBlocks.AERONOS_CHEST.get(), ModBlocks.STROPHAR_CHEST.get()
        ));
    }

    public static void onRegisterItemRenderers(BiConsumer<ItemLike, BlockEntityWithoutLevelRenderer> register) {
        register.accept(ModItems.TIER_1_ROCKET.get(), new RocketItemRendererTier1());
        register.accept(ModItems.TIER_2_ROCKET.get(), new RocketItemRendererTier2());
        register.accept(ModItems.TIER_3_ROCKET.get(), new RocketItemRendererTier3());
        register.accept(ModItems.TIER_4_ROCKET.get(), new RocketItemRendererTier4());
        register.accept(ModItems.TIER_1_ROVER.get(), new RoverItemRenderer());

        register.accept(ModBlocks.AERONOS_CHEST.get(), new ChestItemRenderer(ModBlocks.AERONOS_CHEST.get()));
        register.accept(ModBlocks.STROPHAR_CHEST.get(), new ChestItemRenderer(ModBlocks.STROPHAR_CHEST.get()));

        ModItems.GLOBES.stream().forEach(item -> register.accept(item.get(), new GlobeItemRenderer()));
        ModItems.FLAGS.stream().forEach(item -> register.accept(item.get(), new GlobeItemRenderer()));
    }

    public static void onRegisterReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        registry.accept(new ResourceLocation(AdAstra.MOD_ID, "planet_resources"), new PlanetResources());
    }

    public static void onRegisterModels(Consumer<ResourceLocation> register) {
        ModBlocks.FLAGS.stream().forEach(block -> register.accept(new ResourceLocation(AdAstra.MOD_ID, "block/flag/" + block.getId().getPath())));
        ModBlocks.SLIDING_DOORS.stream().forEach(block -> register.accept(new ResourceLocation(AdAstra.MOD_ID, "block/door/" + block.getId().getPath())));
        ModBlocks.SLIDING_DOORS.stream().forEach(block -> register.accept(new ResourceLocation(AdAstra.MOD_ID, "block/door/" + block.getId().getPath() + "_flipped")));
    }

    public static void renderBlock(ResourceLocation model, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Minecraft minecraft = Minecraft.getInstance();
        ModelManager manager = minecraft.getModelManager();
        BakedModel baked = ClientPlatformUtils.getModel(manager, model);

        VertexConsumer vertexConsumer1 = buffer.getBuffer(RenderType.entityCutout(InventoryMenu.BLOCK_ATLAS));
        List<BakedQuad> quads1 = baked.getQuads(null, null, minecraft.level.random);
        PoseStack.Pose entry1 = poseStack.last();

        for (BakedQuad quad : quads1) {
            vertexConsumer1.putBulkData(entry1, quad, 1, 1, 1, packedLight, packedOverlay);
        }
    }

    @FunctionalInterface
    public interface RenderHud {
        void renderHud(PoseStack poseStack, float partialTick);
    }
}
