package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.commands.PlanetGuiCommand;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommands {

    public static void register() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registry, environment) -> {
            PlanetGuiCommand.register(dispatcher);
        });
    }
}