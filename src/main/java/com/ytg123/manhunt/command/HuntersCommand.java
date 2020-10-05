package com.ytg123.manhunt.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ytg123.manhunt.Manhunt;
import com.ytg123.manhunt.ManhuntUtils;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
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

    private static int executeClear(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        boolean playerHasMod = ManhuntUtils.playerHasMod(context);
        ManhuntUtils.hunters.clear();
        if (playerHasMod)
            context.getSource().sendFeedback(new TranslatableText("text.manhunt.command.hunters.clear"), true);
        else
            context.getSource().sendFeedback(new LiteralText("Cleared hunter list!"), false);
        return 1;
    }

    private static int executeAdd(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");
        boolean playerHasMod = ManhuntUtils.playerHasMod(context);

        // Target is speedrunner
        if (target.equals(ManhuntUtils.speedrunner)) {
            // We check if the source is a player and the player has the mod
            if (playerHasMod) {
                // We send error message using TranslatableText
                context.getSource().sendError(new TranslatableText("text.manhunt.command.hunters.error.speedrunner", target.getDisplayName()));

            // Source is not player or doesn't have mod, sending error message using LiteralText
            } else {
                context.getSource().sendError(new LiteralText("Cannot add ").append(target.getDisplayName()).append(new LiteralText(" as a hunter because they are the speedrunner!")));
            }
            return 1;
        }

        // Check if target is already a hunter
        if (ManhuntUtils.hunters.contains(target)) {
            if (playerHasMod) {
                context.getSource().sendError(new TranslatableText("text.manhunt.command.hunters.error.hunter", target.getDisplayName()));
            } else {
                context.getSource().sendError(new LiteralText("Cannot add ").append(target.getDisplayName()).append(new LiteralText(" as a hunter because they are already a hunter!")));
            }
            return 1;
        }
        ManhuntUtils.hunters.add(target);
        if (playerHasMod)
            context.getSource().sendFeedback(new TranslatableText("text.manhunt.command.hunters.add", target.getDisplayName()), true);
        else
            context.getSource().sendFeedback(new LiteralText("Added ").append(target.getDisplayName()).append(new LiteralText(" to the hunters list!")), true);
        return 1;
    }

    private static int executeGet(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        boolean playerHasMod = ManhuntUtils.playerHasMod(context);
        if (ManhuntUtils.hunters.isEmpty()) return 1;
        List<String> hunterNames = new ArrayList<>();
        ManhuntUtils.hunters.forEach(element -> {
            hunterNames.add(element.getDisplayName().asString());
        });
        if (playerHasMod)
            context.getSource().sendFeedback(new TranslatableText("text.manhunt.command.hunters.get", String.join(", ", hunterNames)), false);
        else
            context.getSource().sendFeedback(new LiteralText("Hunters are: " + String.join(", ", hunterNames)), false);
        return 1;
    }
}
