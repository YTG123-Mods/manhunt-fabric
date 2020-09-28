package com.ytg123.manhunt.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ytg123.manhunt.Utils;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import static net.minecraft.server.command.CommandManager.*;

public class SpeedrunnerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("speedrunner").then(
                        argument("target", EntityArgumentType.player()).executes(SpeedrunnerCommand::execute)
                ).then(
                        literal("get").executes(SpeedrunnerCommand::executeGet)
                )
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");
        if (Utils.hunters.contains(target)) {
            context.getSource().sendError(new TranslatableText("text.manhunt.command.speedrunner.error.hunter", target.getDisplayName()));
            return 1;
        }
        Utils.speedrunner = target;
        context.getSource().sendFeedback(new TranslatableText("text.manhunt.command.speedrunner.set", Utils.speedrunner.getDisplayName()), true);
        return 1;
    }

    private static int executeGet(CommandContext<ServerCommandSource> context) {
        if (Utils.speedrunner == null) return 1;
        context.getSource().sendFeedback(new TranslatableText("text.manhunt.command.speedrunner.get", Utils.speedrunner.getDisplayName()), false);
        return 1;
    }
}
