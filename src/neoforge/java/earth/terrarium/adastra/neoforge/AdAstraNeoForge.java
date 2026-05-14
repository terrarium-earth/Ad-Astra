package earth.terrarium.adastra.neoforge;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.client.neoforge.AdAstraClientNeoForge;
import earth.terrarium.adastra.common.commands.AdAstraCommands;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import earth.terrarium.adastra.common.tags.ModBlockTags;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

@Mod(AdAstra.MOD_ID)
public class AdAstraNeoForge {

    public AdAstraNeoForge(IEventBus bus) {
        AdAstra.init();
        NeoForge.EVENT_BUS.addListener(AdAstraNeoForge::onAddReloadListener);
        NeoForge.EVENT_BUS.addListener(AdAstraNeoForge::onDatapackSync);
        NeoForge.EVENT_BUS.addListener(AdAstraNeoForge::onServerTick);
        NeoForge.EVENT_BUS.addListener(AdAstraNeoForge::registerCommands);
        NeoForge.EVENT_BUS.addListener(AdAstraNeoForge::onBlockPlace);
        NeoForge.EVENT_BUS.addListener(AdAstraNeoForge::onServerStarted);
        bus.addListener(AdAstraNeoForge::onAttributes);
        bus.addListener(AdAstraNeoForge::commonSetup);
        if (FMLEnvironment.dist.isClient()) {
            AdAstraClientNeoForge.init(bus);
        }
    }

    public static void onAddReloadListener(AddReloadListenerEvent event) {
        AdAstra.onAddReloadListener((id, listener) -> event.addListener(listener));
    }

    public static void onDatapackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() != null) {
            AdAstra.onDatapackSync(event.getPlayer());
        } else {
            for (var player : event.getPlayerList().getPlayers()) {
                AdAstra.onDatapackSync(player);
            }
        }
    }

    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            AdAstra.onServerTick(event.getServer());
        }
    }

    public static void onAttributes(EntityAttributeCreationEvent event) {
        ModEntityTypes.registerAttributes((entityType, attribute) -> event.put(entityType.get(), attribute.get().build()));
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        AdAstra.postInit();
    }

    private static void registerCommands(RegisterCommandsEvent event) {
        AdAstraCommands.register(event.getDispatcher());
    }

    private static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().is(ModBlockTags.DESTROYED_IN_SPACE)
            && !event.getPlacedBlock().isRandomlyTicking()
            && (event.getLevel() instanceof Level level)
            && !OxygenApi.API.hasOxygen(level, event.getPos())) {
            event.setCanceled(true);
        }
    }

    private static void onServerStarted(ServerAboutToStartEvent event) {
        AdAstra.onServerStarted(event.getServer());
    }
}
