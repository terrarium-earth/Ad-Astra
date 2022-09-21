package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.commands.PlanetGuiCommand;

import dev.architectury.event.events.common.CommandRegistrationEvent;

public class ModCommands {

    public static void register() {

        CommandRegistrationEvent.EVENT.register((dispatcher, selection) -> {
            PlanetGuiCommand.register(dispatcher);
        });
    }
}