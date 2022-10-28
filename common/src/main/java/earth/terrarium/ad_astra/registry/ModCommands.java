package earth.terrarium.ad_astra.registry;

import com.mojang.brigadier.CommandDispatcher;
import dev.architectury.injectables.annotations.ExpectPlatform;
import earth.terrarium.ad_astra.commands.PlanetGuiCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.Consumer;

public class ModCommands {
    public static void register() {
        registerCommand(PlanetGuiCommand::register);
    }

    @ExpectPlatform
    public static void registerCommand(Consumer<CommandDispatcher<ServerCommandSource>> command) {
        throw new NotImplementedException();
    }
}