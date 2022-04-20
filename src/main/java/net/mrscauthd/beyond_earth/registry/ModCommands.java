package net.mrscauthd.beyond_earth.registry;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.mrscauthd.beyond_earth.commands.PlanetGuiCommand;

public class ModCommands {

    public static void register() {

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            PlanetGuiCommand.register(dispatcher);
        });
    }
}
