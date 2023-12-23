package earth.terrarium.adastra.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import earth.terrarium.adastra.common.utils.radio.StationLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class RadioCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        String stationsFile = System.getProperty("adastra.stations");
        if (stationsFile == null) return;

        dispatcher.register(Commands.literal("adastra")
            .then(Commands.literal("radio")
                .then(Commands.literal("refresh")
                    .executes(context -> {
                        StationLoader.init();
                        return 1;
                    })
                )
            )
        );
    }
}
