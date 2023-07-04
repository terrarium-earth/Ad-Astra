package earth.terrarium.adastra.forge;

import earth.terrarium.adastra.AdAstra;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(AdAstra.MOD_ID)
public class AdAstraForge {

    public AdAstraForge() {
        AdAstra.init();
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onAddReloadListener);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onDatapackSync);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onServerTick);
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
}
