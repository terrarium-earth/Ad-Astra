package earth.terrarium.adastra.neoforge;

import com.teamresourceful.resourcefullib.common.fluid.ResourcefulBucketItem;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.client.neoforge.AdAstraClientNeoForge;
import earth.terrarium.adastra.common.commands.AdAstraCommands;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.adastra.common.tags.ModBlockTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;

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
        bus.addListener(AdAstraNeoForge::registerSpawnPlacements);
        bus.addListener(AdAstraNeoForge::commonSetup);
        bus.addListener(AdAstraNeoForge::registerCapabilities);
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

    public static void onServerTick(ServerTickEvent.Post event) {
        AdAstra.onServerTick(event.getServer());
    }

    public static void onAttributes(EntityAttributeCreationEvent event) {
        ModEntityTypes.registerAttributes((entityType, attribute) -> event.put(entityType.get(), attribute.get().build()));
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        AdAstra.postInit();
    }

    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        ModEntityTypes.registerSpawnPlacements(new ModEntityTypes.SpawnPlacementRegistrar() {
            @Override
            public <T extends Mob> void register(EntityType<T> entityType, SpawnPlacementType placementType,
                                                 Heightmap.Types height, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
                event.register(entityType, placementType, height, spawnPredicate, RegisterSpawnPlacementsEvent.Operation.AND);
            }
        });
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

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        for (var item : ModItems.ITEMS.getEntries()) {
            if (item.get() instanceof ResourcefulBucketItem) {
                event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new FluidBucketWrapper(stack), item.get());
            }
        }
    }
}
