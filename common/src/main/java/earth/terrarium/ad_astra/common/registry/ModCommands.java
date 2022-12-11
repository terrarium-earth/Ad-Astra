package earth.terrarium.ad_astra.common.registry;

import com.mojang.brigadier.CommandDispatcher;
import earth.terrarium.ad_astra.common.command.PlanetGuiCommand;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.Consumer;

public class ModCommands {
    public static void registerCommands(Consumer<Consumer<CommandDispatcher<CommandSourceStack>>> command) {
        command.accept(PlanetGuiCommand::register);
    }
}