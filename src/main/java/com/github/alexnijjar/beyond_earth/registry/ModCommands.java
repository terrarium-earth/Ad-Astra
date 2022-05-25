package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.commands.PlanetGuiCommand;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class ModCommands {

    public static void register() {

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            PlanetGuiCommand.register(dispatcher);
        });
    }
}