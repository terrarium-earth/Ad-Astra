package earth.terrarium.ad_astra.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import earth.terrarium.ad_astra.screen.PlanetSelectionMenuProvider;
import earth.terrarium.botarium.api.menu.MenuHooks;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;
import java.util.List;

import static net.minecraft.commands.Commands.argument;

public class PlanetGuiCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("planetgui")
                .requires(source -> source.hasPermission(2))
                .executes(PlanetGuiCommand::executeSimple)
                .then(argument("target", EntityArgument.entities())
                        .executes(PlanetGuiCommand::executePartial)
                        .then(argument("tier", IntegerArgumentType.integer())
                                .executes(PlanetGuiCommand::executeFull))));
    }

    private static int executeSimple(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return execute(List.of(context.getSource().getPlayerOrException()), 4);
    }

    private static int executePartial(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return execute(EntityArgument.getEntities(context, "target"), 4);
    }

    private static int executeFull(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        Collection<? extends Entity> entities = EntityArgument.getEntities(context, "target");
        int tier = IntegerArgumentType.getInteger(context, "tier");

        return execute(entities, tier);
    }

    private static int execute(Collection<? extends Entity> entities, int tier) {

        entities.forEach((entity) -> {
            if (entity instanceof Player player) {
                player.inventoryMenu.removed(player);
                MenuHooks.openMenu((ServerPlayer) player, new PlanetSelectionMenuProvider(tier));
            }
        });

        return 1;
    }
}