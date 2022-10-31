package earth.terrarium.ad_astra.registry;

import com.mojang.brigadier.CommandDispatcher;
import dev.architectury.injectables.annotations.ExpectPlatform;
import earth.terrarium.ad_astra.commands.PlanetGuiCommand;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.Consumer;
import net.minecraft.commands.CommandSourceStack;

public class ModCommands {
    public static void register() {
        registerCommand(PlanetGuiCommand::register);
    }

    @ExpectPlatform
    public static void registerCommand(Consumer<CommandDispatcher<CommandSourceStack>> command) {
        throw new NotImplementedException();
    }
}