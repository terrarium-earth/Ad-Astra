package net.mrscauthd.boss_tools.events;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.entity.EntityType;
import net.minecraft.client.Minecraft;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
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

import KeyBinding;

import javax.swing.text.JTextComponent;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
	public static JTextComponent.KeyBinding key1;
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(ModInnet.ALIEN.get(), ((IRenderFactory) AlienRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.PYGRO.get(), ((IRenderFactory) PygroRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.MOGLER.get(), ((IRenderFactory) MoglerRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.ALIEN_ZOMBIE.get(), ((IRenderFactory) AlienZombieRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.STAR_CRAWLER.get(), ((IRenderFactory) StarCrawlerRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.TIER_1_ROCKET.get(), ((IRenderFactory) RocketTier1Renderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.TIER_2_ROCKET.get(), ((IRenderFactory) RocketTier2Renderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.TIER_3_ROCKET.get(), ((IRenderFactory) RocketTier3Renderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.LANDER.get(), ((IRenderFactory) LanderRenderer::new));

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.ROVER.get(), ((IRenderFactory) RoverRenderer::new));

		ClientRegistry.bindTileEntityRenderer(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR.get(), TileEntityBoxRenderer::new);

		ClientRegistry.bindTileEntityRenderer(ModInnet.FLAG.get(), TileEntityHeadRenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(ModInnet.ALIEN_SPIT_ENTITY.get(), renderManager -> new SpriteRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));

		//GUIS
		ScreenManager.registerFactory(ModInnet.ROCKET_GUI.get(), RocketGuiWindow::new);
		ScreenManager.registerFactory(ModInnet.BLAST_FURNACE_GUI.get(), BlastFurnaceGuiWindow::new);
		ScreenManager.registerFactory(ModInnet.COMPRESSOR_GUI.get(), CompressorGuiWindow::new);
		ScreenManager.registerFactory(ModInnet.FUEL_REFINERY_GUI.get(), FuelRefineryGuiWindow::new);
		ScreenManager.registerFactory(ModInnet.COAL_GENERATOR_GUI.get(), CoalGeneratorGuiWindow::new);
		ScreenManager.registerFactory(ModInnet.NASA_WORKBENCH_GUI.get(), NasaWorkbenchGuiWindow::new);
		ScreenManager.registerFactory(ModInnet.OXYGEN_LOADER_GUI.get(), OxygenLoaderGuiWindow::new);
		ScreenManager.registerFactory(ModInnet.SOLAR_PANEL_GUI.get(), SolarPanelGuiWindow::new);
		ScreenManager.registerFactory(ModInnet.WATER_PUMP_GUI.get(), WaterPumpGuiWindow::new);
		ScreenManager.registerFactory(ModInnet.OXYGEN_BUBBLE_DISTRIBUTOR_GUI.get(), OxygenBubbleDistributorGuiWindow::new);
		ScreenManager.registerFactory(ModInnet.LANDER_GUI.get(), LanderGuiWindow::new);
		ScreenManager.registerFactory(ModInnet.ROVER_GUI.get(), RoverGuiWindow::new);
		ScreenManager.registerFactory(ModInnet.PLANET_SELECTION_GUI.get(), PlanetSelectionGuiWindow::new);

		//Key Binding Registrys
		key1 = new KeyBinding("key." + BossToolsMod.ModId + ".rocket_start", GLFW.GLFW_KEY_SPACE, "key.categories." + BossToolsMod.ModId);
		ClientRegistry.registerKeyBinding(key1);

		//Fluid Translucent Renderer
		RenderTypeLookup.setRenderLayer(ModInnet.FLOWING_FUEL.get(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModInnet.FUEL_STILL.get(), RenderType.getTranslucent());

		//Block Translucent Renderer
		RenderTypeLookup.setRenderLayer(ModInnet.COAL_LANTERN_BLOCK.get(), RenderType.getTranslucent());

		//Cutout
		RenderTypeLookup.setRenderLayer(ModInnet.NASA_WORKBENCH_BLOCK.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(ModInnet.WATER_PUMP_BLOCK.get(), RenderType.getCutout());
	}

	@SubscribeEvent
	public static void onRegistrerEntities(final RegistryEvent.Register<EntityType<?>> event) {
		ModSpawnEggs.initSpawnEggs();
	}

	@SubscribeEvent
	public static void registerParticlesFactory(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particles.registerFactory(ModInnet.VENUS_RAIN_PARTICLE.get(), VenusRainParticle.ParticleFactory::new);
		Minecraft.getInstance().particles.registerFactory(ModInnet.LARGE_FLAME_PARTICLE.get(), LargeFlameParticle.ParticleFactory::new);
		Minecraft.getInstance().particles.registerFactory(ModInnet.SMOKE_PARTICLE.get(), SmokeParticle.ParticleFactory::new);
	}
}
