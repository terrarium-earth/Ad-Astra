package earth.terrarium.adastra.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;

public class AdAstraCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        RadioCommand.register(dispatcher);
        PlanetsCommand.register(dispatcher);
    }
}
