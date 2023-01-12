package earth.terrarium.ad_astra.common.registry;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.Consumer;

public class ModCommands {
    public static void registerCommands(Consumer<Consumer<CommandDispatcher<CommandSourceStack>>> command) {
    }
}