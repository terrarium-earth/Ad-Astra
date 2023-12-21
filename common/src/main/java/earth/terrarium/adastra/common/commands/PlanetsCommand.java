package earth.terrarium.adastra.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class PlanetsCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("adastra")
            .then(Commands.literal("planets")
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    player.openMenu(new PlanetsMenu.Provider());
                    return 1;
                })
            )
        );
    }
}
