package io.github.ytg1234.manhunt.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.ytg1234.manhunt.ManhuntUtils;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SpeedrunnerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("speedrunner").requires(source -> source.hasPermissionLevel(2))
                                                  .then(literal("set").then(argument("target", EntityArgumentType.player()).executes(
                                                          SpeedrunnerCommand::executeSet)))
                                                  .then(literal("get").executes(SpeedrunnerCommand::executeGet))
                                                  .then(literal("clear").executes(SpeedrunnerCommand::executeClear)));
    }


    private static int executeSet(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        boolean playerHasMod = ManhuntUtils.playerHasMod(context);
        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "target");

        if (ManhuntUtils.hunters.contains(target.getUuid())) {
            if (playerHasMod) {
                context.getSource().sendError(new TranslatableText("text.manhunt.command.speedrunner.error.hunter", target.getDisplayName()));
            } else {
                context.getSource()
                       .sendError(new LiteralText("Cannot set speedrunner to ").append(target.getDisplayName())
                                                                               .append(new LiteralText(" because they are a hunter!")));
            }
            return 1;
        }

        ManhuntUtils.speedrunner = target.getUuid();
        if (playerHasMod) {
            context.getSource()
                   .sendFeedback(new TranslatableText("text.manhunt.command.speedrunner.set",
                                                      ManhuntUtils.fromCmdContext(context, ManhuntUtils.speedrunner).getDisplayName()
                   ), true);
        } else {
            context.getSource()
                   .sendFeedback(new LiteralText("Set speedrunner to ").append(target.getDisplayName()).append(new LiteralText("!")), true);
        }
        return 1;
    }

    private static int executeGet(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        boolean playerHasMod = ManhuntUtils.playerHasMod(context);
        if (ManhuntUtils.speedrunner == null) return 1;

        if (playerHasMod) {
            context.getSource()
                   .sendFeedback(new TranslatableText("text.manhunt.command.speedrunner.get",
                                                      ManhuntUtils.fromCmdContext(context, ManhuntUtils.speedrunner).getDisplayName()
                   ), false);
        } else {
            context.getSource()
                   .sendFeedback(new LiteralText("Speedrunner is currently: ").append(ManhuntUtils.fromCmdContext(context, ManhuntUtils.speedrunner)
                                                                                                  .getDisplayName()), true);
        }
        return 1;
    }

    private static int executeClear(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        boolean playerHasMod = ManhuntUtils.playerHasMod(context);
        ManhuntUtils.speedrunner = null;
        if (playerHasMod) {
            context.getSource().sendFeedback(new TranslatableText("text.manhunt.command.speedrunner.clear"), true);
        } else {
            context.getSource().sendFeedback(new LiteralText("Speedrunner Cleared!"), true);
        }
        return 1;
    }
}
