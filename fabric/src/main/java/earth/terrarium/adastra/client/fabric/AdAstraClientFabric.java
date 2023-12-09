package earth.terrarium.adastra.client.fabric;

import earth.terrarium.adastra.client.AdAstraClient;
import earth.terrarium.adastra.client.radio.screen.RadioScreen;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Minecraft;

public class AdAstraClientFabric {

    public static void init() {
        AdAstraClient.init();
        ClientTickEvents.START_CLIENT_TICK.register(AdAstraClient::clientTick);
        KeyBindingHelper.registerKeyBinding(AdAstraClient.KEY_TOGGLE_SUIT_FLIGHT);

        //TODO REMOVE THIS
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(ClientCommandManager.literal("adastra-sound").executes(context -> {
                Minecraft.getInstance().tell(() -> {
                    Minecraft.getInstance().setScreen(new RadioScreen());
                });
                return 1;
            }));
        });
    }
}
