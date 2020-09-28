package com.ytg123.manhunt.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ytg123.manhunt.Utils;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraft.server.command.CommandManager.*;

public class HuntersCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                literal("hunters").requires(source -> source.hasPermissionLevel(2))
                        .then(
                                literal("clear").executes(HuntersCommand::executeClear)
                        )
                        .then(
                                literal("add").then(
                                        argument("target", EntityArgumentType.player()).executes(HuntersCommand::executeAdd)
                                )
                        )
                        .then(
                                literal("get").executes(HuntersCommand::executeGet)
                        )
        );
    }

    private static int executeClear(CommandContext<ServerCommandSource> context) {
        Utils.hunters.clear();
        context.getSource().sendFeedback(new TranslatableText("text.manhunt.command.hunters.clear"), true);
        return 1;
    }

    private static int executeAdd(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");
        if (target.equals(Utils.speedrunner)) {
            context.getSource().sendError(new TranslatableText("text.manhunt.command.hunters.error.speedrunner", target.getDisplayName()));
            return 1;
        }
        Utils.hunters.add(target);
        context.getSource().sendFeedback(new TranslatableText("text.manhunt.command.hunters.add", target.getDisplayName()), true);
        return 1;
    }

    private static int executeGet(CommandContext<ServerCommandSource> context) {
        if (Utils.hunters.isEmpty()) return 1;
        List<String> hunterNames = new ArrayList<>();
        Utils.hunters.forEach(element -> {
            hunterNames.add(element.getDisplayName().asString());
        });
        context.getSource().sendFeedback(new TranslatableText("text.manhunt.command.hunters.get", String.join(",", hunterNames)), false);
        return 1;
    }
}
