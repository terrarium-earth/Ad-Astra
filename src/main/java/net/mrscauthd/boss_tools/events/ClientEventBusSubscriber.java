package net.mrscauthd.boss_tools.events;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.alien.AlienEntity;
import net.mrscauthd.boss_tools.entity.renderer.TileEntityBoxRenderer;
import net.mrscauthd.boss_tools.entity.renderer.flagtileentity.TileEntityHeadRenderer;
import net.mrscauthd.boss_tools.entity.renderer.alienzombie.AlienZombieRenderer;
import net.mrscauthd.boss_tools.entity.renderer.lander.LanderRenderer;
import net.mrscauthd.boss_tools.entity.renderer.rockettier1.RocketTier1Renderer;
import net.mrscauthd.boss_tools.entity.renderer.rockettier2.RocketTier2Renderer;
import net.mrscauthd.boss_tools.entity.renderer.rockettier3.RocketTier3Renderer;
import net.mrscauthd.boss_tools.entity.renderer.rover.RoverRenderer;
import net.mrscauthd.boss_tools.entity.renderer.starcrawler.StarCrawlerRenderer;
import net.mrscauthd.boss_tools.gui.screens.blastfurnace.BlastFurnaceGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.coalgenerator.CoalGeneratorGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.compressor.CompressorGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.fuelrefinery.FuelRefineryGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.lander.LanderGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.nasaworkbench.NasaWorkbenchGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.oxygenbubbledistributor.OxygenBubbleDistributorGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.oxygenloader.OxygenLoaderGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.planetselection.PlanetSelectionGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.rocket.RocketGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.rover.RoverGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.solarpanel.SolarPanelGuiWindow;
import net.mrscauthd.boss_tools.gui.screens.waterpump.WaterPumpGuiWindow;
import net.mrscauthd.boss_tools.particle.LargeFlameParticle;
import net.mrscauthd.boss_tools.particle.SmokeParticle;
import net.mrscauthd.boss_tools.particle.VenusRainParticle;
import net.mrscauthd.boss_tools.spawneggs.ModSpawnEggs;
import net.mrscauthd.boss_tools.entity.renderer.alien.AlienRenderer;
import net.mrscauthd.boss_tools.entity.renderer.mogler.MoglerRenderer;
import net.mrscauthd.boss_tools.entity.renderer.pygro.PygroRenderer;
import org.lwjgl.glfw.GLFW;

import javax.swing.text.JTextComponent;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
	public static JTextComponent.KeyBinding key1;

	@SubscribeEvent
	public static void registerEntityRenderingHandler(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModInnet.ALIEN.get(), AlienRenderer::new);
		event.registerEntityRenderer(ModInnet.PYGRO.get(), PygroRenderer::new);
		event.registerEntityRenderer(ModInnet.MOGLER.get(), MoglerRenderer::new);
		event.registerEntityRenderer(ModInnet.ALIEN_ZOMBIE.get(), AlienZombieRenderer::new);
		event.registerEntityRenderer(ModInnet.STAR_CRAWLER.get(), StarCrawlerRenderer::new);
		event.registerEntityRenderer(ModInnet.TIER_1_ROCKET.get(), RocketTier1Renderer::new);
		event.registerEntityRenderer(ModInnet.TIER_2_ROCKET.get(), RocketTier2Renderer::new);
		event.registerEntityRenderer(ModInnet.TIER_3_ROCKET.get(), RocketTier3Renderer::new);
		event.registerEntityRenderer(ModInnet.LANDER.get(), LanderRenderer::new);
		event.registerEntityRenderer(ModInnet.ROVER.get(), RoverRenderer::new);

		event.registerBlockEntityRenderer(null, TileEntityBoxRenderer::new);
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {

		ClientRegistry.bindTileEntityRenderer(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR.get(), TileEntityBoxRenderer::new);

		ClientRegistry.bindTileEntityRenderer(ModInnet.FLAG.get(), TileEntityHeadRenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.ALIEN_SPIT_ENTITY.get(), renderManager -> new SpriteRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));

		//GUIS
		MenuScreens.register(ModInnet.ROCKET_GUI.get(), RocketGuiWindow::new);
		MenuScreens.register(ModInnet.BLAST_FURNACE_GUI.get(), BlastFurnaceGuiWindow::new);
		MenuScreens.register(ModInnet.COMPRESSOR_GUI.get(), CompressorGuiWindow::new);
		MenuScreens.register(ModInnet.FUEL_REFINERY_GUI.get(), FuelRefineryGuiWindow::new);
		MenuScreens.register(ModInnet.COAL_GENERATOR_GUI.get(), CoalGeneratorGuiWindow::new);
		MenuScreens.register(ModInnet.NASA_WORKBENCH_GUI.get(), NasaWorkbenchGuiWindow::new);
		MenuScreens.register(ModInnet.OXYGEN_LOADER_GUI.get(), OxygenLoaderGuiWindow::new);
		MenuScreens.register(ModInnet.SOLAR_PANEL_GUI.get(), SolarPanelGuiWindow::new);
		MenuScreens.register(ModInnet.WATER_PUMP_GUI.get(), WaterPumpGuiWindow::new);
		MenuScreens.register(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR_GUI.get(), OxygenBubbleDistributorGuiWindow::new);
		MenuScreens.register(ModInnet.LANDER_GUI.get(), LanderGuiWindow::new);
		MenuScreens.register(ModInnet.ROVER_GUI.get(), RoverGuiWindow::new);
		MenuScreens.register(ModInnet.PLANET_SELECTION_GUI.get(), PlanetSelectionGuiWindow::new);

		//Key Binding Registrys
		key1 = new JTextComponent.KeyBinding("key." + BossToolsMod.ModId + ".rocket_start", GLFW.GLFW_KEY_SPACE, "key.categories." + BossToolsMod.ModId);
		ClientRegistry.registerKeyBinding(key1);

		//Fluid Translucent Renderer
		ItemBlockRenderTypes.setRenderLayer(ModInnet.FLOWING_FUEL.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(ModInnet.FUEL_STILL.get(), RenderType.translucent());

		//Block Translucent Renderer
		ItemBlockRenderTypes.setRenderLayer(ModInnet.COAL_LANTERN_BLOCK.get(), RenderType.translucent());

		//Cutout
		ItemBlockRenderTypes.setRenderLayer(ModInnet.NASA_WORKBENCH_BLOCK.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(ModInnet.WATER_PUMP_BLOCK.get(), RenderType.cutout());
	}


	@SubscribeEvent
	public static void onRegistrerEntities(final RegistryEvent.Register<EntityType<?>> event) {
		ModSpawnEggs.initSpawnEggs();
	}

	@SubscribeEvent
	public static void registerParticlesFactory(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particleEngine.register(ModInnet.VENUS_RAIN_PARTICLE.get(), VenusRainParticle.ParticleFactory::new);
		Minecraft.getInstance().particleEngine.register(ModInnet.LARGE_FLAME_PARTICLE.get(), LargeFlameParticle.ParticleFactory::new);
		Minecraft.getInstance().particleEngine.register(ModInnet.SMOKE_PARTICLE.get(), SmokeParticle.ParticleFactory::new);
	}
}
