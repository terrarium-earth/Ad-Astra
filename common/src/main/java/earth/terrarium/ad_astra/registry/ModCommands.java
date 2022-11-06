package earth.terrarium.ad_astra.registry;

import com.mojang.brigadier.CommandDispatcher;
import earth.terrarium.ad_astra.commands.PlanetGuiCommand;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.Consumer;

public class ModCommands {
    public static void registerCommands(Consumer<Consumer<CommandDispatcher<CommandSourceStack>>> command) {
        command.accept(PlanetGuiCommand::register);
    }
}