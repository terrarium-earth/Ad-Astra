package earth.terrarium.adastra.client.fabric;

import earth.terrarium.adastra.client.AdAstraClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class AdAstraFabricClient {

    public static void init() {
        AdAstraClient.init();
        ClientTickEvents.START_CLIENT_TICK.register(AdAstraClient::clientTick);
        KeyBindingHelper.registerKeyBinding(AdAstraClient.KEY_TOGGLE_SUIT_FLIGHT);
    }
}
