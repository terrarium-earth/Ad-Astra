package earth.terrarium.adastra.client.fabric;

import earth.terrarium.adastra.client.AdAstraClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class AdAstraFabricClient {

    public static void init() {
        AdAstraClient.init();
        ClientTickEvents.START_CLIENT_TICK.register(AdAstraClient::clientTick);
    }
}
