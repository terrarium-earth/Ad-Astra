package earth.terrarium.ad_astra.registry.forge;

import com.mojang.brigadier.CommandDispatcher;
import earth.terrarium.ad_astra.forge.AdAstraForge;
import java.util.function.Consumer;
import net.minecraft.commands.CommandSourceStack;

public class ModCommandsImpl {
    public static void registerCommand(Consumer<CommandDispatcher<CommandSourceStack>> command) {
        AdAstraForge.COMMANDS.add(command);
    }
}
