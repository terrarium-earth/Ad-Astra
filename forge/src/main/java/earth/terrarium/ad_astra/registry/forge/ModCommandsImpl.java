package earth.terrarium.ad_astra.registry.forge;

import com.mojang.brigadier.CommandDispatcher;
import earth.terrarium.ad_astra.forge.AdAstraForge;
import net.minecraft.server.command.ServerCommandSource;

import java.util.function.Consumer;

public class ModCommandsImpl {
    public static void registerCommand(Consumer<CommandDispatcher<ServerCommandSource>> command) {
        AdAstraForge.COMMANDS.add(command);
    }
}
