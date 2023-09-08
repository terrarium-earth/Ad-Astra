package earth.terrarium.adastra.fabric;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class AdAstraFabric {

    public static void init() {
        AdAstra.init();
        onAddReloadListener();
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> AdAstra.onDatapackSync(player));
        ServerTickEvents.END_SERVER_TICK.register(AdAstra::onServerTick);
        ModEntityTypes.registerAttributes((type, builder) -> FabricDefaultAttributeRegistry.register(type.get(), builder.get()));
    }

    public static void onAddReloadListener() {
        AdAstra.onAddReloadListener((id, listener) -> ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return id;
            }

            @Override
            public @NotNull CompletableFuture<Void> reload(@NotNull PreparationBarrier synchronizer, @NotNull ResourceManager manager, @NotNull ProfilerFiller prepareProfiler, @NotNull ProfilerFiller applyProfiler, @NotNull Executor prepareExecutor, @NotNull Executor applyExecutor) {
                return listener.reload(synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
            }
        }));
    }
}
