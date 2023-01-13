package earth.terrarium.ad_astra.client;

import earth.terrarium.ad_astra.client.screen.machine.TestScreen;
import earth.terrarium.ad_astra.client.util.ClientPlatformUtils;
import earth.terrarium.ad_astra.common.registry.ModFluids;
import earth.terrarium.ad_astra.common.registry.ModMenus;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AdAstraClient {

    public static void init() {
        registerScreens();
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
    }

    public static void onRegisterHud(Consumer<ClientPlatformUtils.RenderHud> register) {
    }

    public static void onRegisterFluidRenderTypes(BiConsumer<RenderType, Fluid> register) {
        ModFluids.FLUIDS.stream().forEach(fluid -> register.accept(RenderType.translucent(), fluid.get()));
    }

    public static void onRegisterBlockRenderTypes(BiConsumer<RenderType, List<Block>> register) {
    }

    public static void onRegisterItemRenderers(BiConsumer<ItemLike, BlockEntityWithoutLevelRenderer> register) {
    }

    public static void onRegisterReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
    }

    public static void onRegisterModels(Consumer<ResourceLocation> register) {
    }
}
