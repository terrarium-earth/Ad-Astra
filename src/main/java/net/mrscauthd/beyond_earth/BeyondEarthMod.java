package net.mrscauthd.beyond_earth;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.mrscauthd.beyond_earth.capability.CapabilityOxygen;
import net.mrscauthd.beyond_earth.compat.CompatibleManager;
import net.mrscauthd.beyond_earth.entity.alien.AlienTrade;
import net.mrscauthd.beyond_earth.gui.screens.planetselection.PlanetSelectionGui;
import net.mrscauthd.beyond_earth.keybind.KeyBindings;
import net.mrscauthd.beyond_earth.machines.tile.OxygenBubbleDistributorBlockEntity;

import net.mrscauthd.beyond_earth.world.oregen.OreGeneration;
import net.mrscauthd.beyond_earth.world.structure.configuration.STStructures;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.MinecraftForge;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod(BeyondEarthMod.MODID)
public class BeyondEarthMod {

	public static final Logger LOGGER = LogManager.getLogger(BeyondEarthMod.class);

	public static final String MODID = "beyond_earth";

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(BeyondEarthMod.MODID, BeyondEarthMod.MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID;

	public BeyondEarthMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;

		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		// Register the enqueueIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);

		// Register the processIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

		bus.register(this);

		//MobInnet
		ModInit.ENTITYS.register(bus);
		ModInit.ITEMS.register(bus);
		ModInit.BLOCKS.register(bus);
		ModInit.TILE_ENTITYS.register(bus);
		ModInit.SOUNDS.register(bus);
		ModInit.SENSOR.register(bus);
		ModInit.FLUIDS.register(bus);
		ModInit.RECIPE_SERIALIZERS.register(bus);
		ModInit.ROCKET_PARTS.register(bus);
		ModInit.EFFECTS.register(bus);
		ModInit.GUIS.register(bus);
		ModInit.PARTICLES.register(bus);

		forgeBus.addListener(EventPriority.HIGH, ModInit::biomesLoading);
		forgeBus.addListener(EventPriority.HIGH, OreGeneration::biomesLoading);
		forgeBus.addListener(EventPriority.LOWEST, AlienTrade::onAddReloadListener);

		// KeyBindings
		KeyBindings.registerMessages();

		//Networker
		BeyondEarthMod.addNetworkMessage(OxygenBubbleDistributorBlockEntity.ChangeRangeMessage.class, OxygenBubbleDistributorBlockEntity.ChangeRangeMessage::encode, OxygenBubbleDistributorBlockEntity.ChangeRangeMessage::decode, OxygenBubbleDistributorBlockEntity.ChangeRangeMessage::handle);
		BeyondEarthMod.addNetworkMessage(OxygenBubbleDistributorBlockEntity.ChangeWorkingAreaVisibleMessage.class, OxygenBubbleDistributorBlockEntity.ChangeWorkingAreaVisibleMessage::encode, OxygenBubbleDistributorBlockEntity.ChangeWorkingAreaVisibleMessage::decode, OxygenBubbleDistributorBlockEntity.ChangeWorkingAreaVisibleMessage::handle);
		BeyondEarthMod.addNetworkMessage(PlanetSelectionGui.NetworkMessage.class, PlanetSelectionGui.NetworkMessage::encode, PlanetSelectionGui.NetworkMessage::decode, PlanetSelectionGui.NetworkMessage::handle);

		//Capability
		bus.addListener(CapabilityOxygen::register);

		CompatibleManager.visit();

		//Structure Registry
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		STStructures.DEFERRED_REGISTRY_STRUCTURE.register(modEventBus);
		modEventBus.addListener(this::setup);

		forgeBus.addListener(EventPriority.NORMAL, ModInit::addDimensionalSpacing);

		// For events that happen after initialization. This is probably going to be use a lot.
		//forgeBus.addListener(EventPriority.NORMAL, AlienVillageStructure::setupStructureSpawns);
	}

	private void setup(final FMLCommonSetupEvent event) {
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
	}

	private void processIMC(final InterModProcessEvent event) {
	}

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}
}
