package earth.terrarium.ad_astra.registry.fabric;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

import java.util.function.Consumer;

public class ModCommandsImpl {
    public static void registerCommand(Consumer<CommandDispatcher<ServerCommandSource>> command) {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> command.accept(dispatcher));
    }
}
