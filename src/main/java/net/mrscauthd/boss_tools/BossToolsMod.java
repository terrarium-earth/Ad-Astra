package net.mrscauthd.boss_tools;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.gui.screens.planetselection.PlanetSelectionGui;
import net.mrscauthd.boss_tools.keybind.KeyBindings;
import net.mrscauthd.boss_tools.machines.OxygenBubbleDistributorBlock;
import net.mrscauthd.boss_tools.world.oregen.OreGeneration;
import net.mrscauthd.boss_tools.world.structure.configuration.STStructures;
import net.mrscauthd.boss_tools.world.structure.configuration.STStructures2;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.MinecraftForge;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod(BossToolsMod.ModId)
public class BossToolsMod {

	public static final Logger LOGGER = LogManager.getLogger(BossToolsMod.class);

	public static final String ModId = "boss_tools";

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(BossToolsMod.ModId, BossToolsMod.ModId), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID;

	public BossToolsMod() {

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
		ModInnet.ENTITYS.register(bus);
		ModInnet.ITEMS.register(bus);
		ModInnet.BLOCKS.register(bus);
		ModInnet.TILE_ENTITYS.register(bus);
		ModInnet.SOUNDS.register(bus);
		ModInnet.SENSOR.register(bus);
		ModInnet.FLUIDS.register(bus);
		ModInnet.RECIPE_SERIALIZERS.register(bus);
		ModInnet.ROCKET_PARTS.register(bus);
		ModInnet.EFFECTS.register(bus);
		ModInnet.GUIS.register(bus);
		ModInnet.PARTICLES.register(bus);
        
		//bus.addListener(ModInnet::setup);
		//forgeBus.addListener(EventPriority.NORMAL, ModInnet::addDimensionalSpacing);
		//forgeBus.addListener(EventPriority.HIGH, ModInnet::biomeModification);

		// Structures
		STStructures2.DEFERRED_REGISTRY_STRUCTURE.register(bus);
		STStructures.DEFERRED_REGISTRY_STRUCTURE.register(bus);

		//forgeBus.addListener(EventPriority.HIGH, ModInnet::biomesLoading);
		forgeBus.addListener(EventPriority.HIGH, OreGeneration::biomesLoading);

		// KeyBindings
		KeyBindings.registerMessages();

		//Networker
		BossToolsMod.addNetworkMessage(OxygenBubbleDistributorBlock.ChangeRangeMessage.class, OxygenBubbleDistributorBlock.ChangeRangeMessage::encode, OxygenBubbleDistributorBlock.ChangeRangeMessage::decode, OxygenBubbleDistributorBlock.ChangeRangeMessage::handle);
		BossToolsMod.addNetworkMessage(OxygenBubbleDistributorBlock.ChangeWorkingAreaVisibleMessage.class, OxygenBubbleDistributorBlock.ChangeWorkingAreaVisibleMessage::encode, OxygenBubbleDistributorBlock.ChangeWorkingAreaVisibleMessage::decode, OxygenBubbleDistributorBlock.ChangeWorkingAreaVisibleMessage::handle);
		BossToolsMod.addNetworkMessage(PlanetSelectionGui.NetworkMessage.class, PlanetSelectionGui.NetworkMessage::encode, PlanetSelectionGui.NetworkMessage::decode, PlanetSelectionGui.NetworkMessage::handle);

		//Capability
		bus.addListener(CapabilityOxygen::register);

		//CompatibleManager.loadAll();
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
